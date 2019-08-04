const app = getApp();
const common = require("../../utils/util")
Page({
    /**
     * 页面的初始数据
     */
    data: {
        cur_year: '2019',
        cur_month: '01',
        cur_day: '01',
        //显示授权
        isShowEmpower: false,
        animated: false,
        nickName: '红商家人',
        cur_hour: '00',
        cur_minute: '00',
        cur_second: '00',
        timeSlot: '早上好',
        clock: '',
        mealOffer: '开始报餐',
        userDepartmentName: '',
        //报餐状态 0：取消报餐 1：已报餐
        baoCan: 0,
        baoCanRecordId: '',
        //1：就餐时间在明天 2：就餐时间在今天
        orderMeal: 1,
        //截止时间状态
        deadlineStatus: '',
        saturdayCanDiner: false,
        sundayCanDiner: false,
        //禁用状态
        disabled: false,
        //0：未选中开始报餐的样式 1：选中已报餐的样式
        select: 0,
        //定时器任务
        timer: '',
        //图片list集合
        imgUrlList: []
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        this.setNowDate();
        this.isAuthorize();
    },
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {
        const that = this;
        that.searchImgList();
        that.getEndTime();
        //获取截止时间
        const name = wx.getStorageSync('nickName');
        if (name) {
            that.setData({
                nickName: name
            })
        }
        //延时加载时因为获取截止时间 获取慢
        // setTimeout(() => {
        //     that.compareTime();
        // }, 300);
        setTimeout(()=>{
            that.countDown(that)
        },300);
        setTimeout(()=> {
            that.valiBcRecord();
        },400);
        const userDepartmentName = wx.getStorageSync('userDepartmentName');
        if (userDepartmentName) {
            that.setData({
                userDepartmentName: userDepartmentName
            })
        }
    },
    onHide:function(){
        let e = this;
        clearInterval(e.data.timer);
    },
    /**
     * 设置当前时间
     */
    setNowDate: function() {
        let that = this;
        const date = new Date();
        const cur_year = date.getFullYear();
        const cur_month = date.getMonth() + 1;
        const cur_day = date.getDate();
        const cur_hour = date.getHours();
        const cur_minute = date.getMinutes();
        const cur_second = date.getSeconds();

        that.setData({
            cur_year: cur_year,
            cur_month: cur_month,
            cur_day: cur_day,
            cur_hour: cur_hour,
            cur_minute: cur_minute,
            cur_second: cur_second
        })
        if (parseInt(that.data.cur_hour) > 6 && parseInt(that.data.cur_hour) < 12) {
            that.setData({
                timeSlot: '早上好'
            })
        } else if (parseInt(that.data.cur_hour) >= 12 && parseInt(that.data.cur_hour) <= 13) {
            that.setData({
                timeSlot: '中午好'
            })
        } else if (parseInt(that.data.cur_hour) > 13 &&
            parseInt(that.data.cur_hour) < 18) {
            that.setData({
                timeSlot: '下午好'
            })
        } else if (parseInt(that.data.cur_hour) >= 18 && parseInt(that.data.cur_hour) <= 24) {
            that.setData({
                timeSlot: '晚上好'
            })
        } else {
            that.setData({
                timeSlot: '凌晨好'
            })
        }
    },
    /**
     * 引导用户授权
     */
    isAuthorize: function() {
        var that = this;
        var Token = wx.getStorageSync('Token');
        wx.getSetting({
            success(res) {
                //没有授权,引导用户授权
                if (!res.authSetting['scope.userInfo']) {
                    that.setData({
                        isShowEmpower: true
                    })
                    setTimeout(() => {
                        that.setData({
                            animated: true
                        })
                    }, 100)
                } else {
                    //已授权：1.已注册会自动登录 2.已授权但未注册则弹出注册页面
                    that.setData({
                        isShowEmpower: false
                    })
                    setTimeout(() => {
                        that.setData({
                            animated: false
                        })
                    }, 100)
                    wx.getUserInfo({
                        success: function(res) {
                            that.saveUserInfo(res.userInfo, res.iv, res.signature, res.encryptedData, res.rawData);
                        }
                    })
                }
            }
        })

    },
    /**
     * 点击授权
     * @param e
     */
    clickEmpower: function(e) {
        var that = this;
        that.setData({
            isShowEmpower: false
        })
        if (e.detail.userInfo) {
            wx.setStorageSync('imgUrl', e.detail.userInfo.avatarUrl);
            that.saveUserInfo(e.detail.userInfo, e.detail
                .iv, e.detail.signature, e.detail.encryptedData, e.detail.rawData);
        }
    },
    /**
     * 有用户授权信息向后台发送请求
     * @param userInfo
     * @param iv
     * @param signature
     * @param encryptedData
     * @param rawData
     */
    saveUserInfo: function(userInfo, iv, signature, encryptedData, rawData) {
        const that = this;
        wx.showLoading({
            title: '加载中',
            mask: true
        })
        wx.login({
            success: function(res) {
                //如果有状态码就向后台发送请求
                if (res.code) {
                    wx.request({
                        url: app.globalData.web_path + '/wx/user/' + app.globalData.appId + '/login',
                        data: {
                            code: res.code,
                            loginType: 'openid'
                        },
                        header: app.globalData.header,
                        success: function(res) {
                            //将openid  缓存
                            wx.setStorageSync('openid', res.data.data.openid);
                            wx.setStorageSync('sessionKey', res.data.data.sessionKey);
                            //判断是否存在token,不存在则说明该用户没有注册,需要弹出用户登录页面,有则不需要进行页面跳转
                            if (!res.data.data.Token && userInfo) {
                                //保存用户信息
                                wx.request({
                                    url: app.globalData.web_path + '/wx/user/' + app.globalData.appId + '/info',
                                    data: {
                                        sessionKey: res.data.data.sessionKey,
                                        iv: iv,
                                        signature: signature,
                                        encryptedData: encryptedData,
                                        rawData: rawData
                                    },
                                    header: app.globalData.header,
                                    success: function(res) {
                                        wx.hideLoading();
                                        that.openAlert();
                                    },
                                    fail: function(res) {
                                    }
                                })
                            } else {
                                //放在请求头里
                                app.globalData.header.Token = res.data.data.Token
                                wx.setStorageSync('Token', res.data.data.Token);
                                wx.setStorageSync('user', res.data.data.user);
                                wx.setStorageSync('userDepartmentName', res.data.data.userDepartmentName);
                                var name = wx.getStorageSync('user').name;
                                var deptName = wx.getStorageSync('userDepartmentName');
                                if (name) {
                                    that.setData({
                                        nickName: name
                                    })
                                }
                                if (deptName) {
                                    that.setData({
                                        userDepartmentName: deptName
                                    })
                                }
                                wx.hideLoading();
                                that.valiBcRecord();
                            }
                        },
                        error: function(res) {
                            console.log("-", "请求失败");
                        }
                    })
                }
            },
            fail: function(res) {
                console.log("error", "接口调用失败!");
            },
        })
    },
    /**
     * 如果授权登录并且未填写信息
     */
    openAlert: function() {
        wx.showModal({
            content: '为确保红商集团报餐小程序更好的使用，请完善个人信息',
            showCancel: false,
            success: function(res) {
                if (res.confirm) {
                    wx.navigateTo({
                        url: '/pages/user-info/index',
                    })
                }
            }
        });
    },
    /**
     * 查询广告图片列表
     */
    searchImgList: function() {
        const that = this;
        wx.request({
            url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcBanner/bcBannerList',
            header: app.globalData.header,
            success: function(res) {
                const list = res.data.data;
                for (let val of list) {
                    const imgUrl = val;
                    that.data.imgUrlList.push(imgUrl);
                }
                that.setData({
                    imgUrlList: that.data.imgUrlList
                })
            },
            fail: function(res) {
                console.log("e", res);
            }
        })
    },
    /**
     * 获取截止时间
     */
    getEndTime: function() {
        const that = this;
        wx.request({
            url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/config/getConfig',
            header: app.globalData.header,
            success: function(res) {
                let lunchTimeArr = res.data.data.lunchOrderTime.split("-");
                that.setData({
                    lunchOrderTime: res.data.data.lunchOrderTime,
                    saturdayCanDiner: res.data.data.saturdayCanDiner,
                    sundayCanDiner: res.data.data.sundayCanDiner,
                    lunchStartTime: lunchTimeArr[0],
                    lunchEndTime: lunchTimeArr[1]
                })
            }
        })
    },
    /**
     * 判断今天是否为当月的最后一天
     */
    isLastDayOfMonth:function(date){
        var flag = new Boolean(false);
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var today = date.getDate();

        var new_year = year; //取当前的年份
        var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）
        if (month > 12) {//如果当前大于12月，则年份转到下一年
            new_month -= 12; //月份减
            new_year++; //年份增
        }
        var new_date = new Date(new_year, new_month, 1); //取当年当月中的第一天

        var month_last_day = (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate();
        if (today == month_last_day) {
            flag = true;
        }
        return flag;
    },
    /**
     * 将截止时间转换成时间戳
     * @param time
     * @returns {number}
     */
    timeFormat: function(time,orderMeal) {
        const date = new Date();
        const cur_year = date.getFullYear();
        /**(1)如果今天是当月的最后一天
         *   1.orderMeal 为1时:截止时间的日期需要+1，月份+1
         *   2.orderMeal为2时:截止时间的日期为今日
         *(2)如果是当月的第一天
         * 1.orderMeal 为1时:截止时间的日期需要+1
         * 2.orderMeal为2时:截止时间的日期为今日
         */
        const isLastDay=this.isLastDayOfMonth(date);
        if (isLastDay==true && orderMeal==1){
            orderMeal=-1; //表示获取下个月的第一天
        }
        const cur_month = -1==orderMeal? date.getMonth() + 2:date.getMonth() + 1;
        const cur_day = orderMeal == 2 ? date.getDate() :orderMeal == 1? date.getDate()+1:1;
        const day = cur_year + "/" + cur_month + "/" + cur_day;
        const endDate = day + " " + time;
        return new Date(endDate).getTime();
    },
    /**
     * 倒计时
     */
    countDown() {
        const that = this;
        that.compareTime();
        let endTime = that.timeFormat(that.data.lunchEndTime,that.data.orderMeal);
        let  nowTime = new Date().getTime();
        let total_micro_second = endTime -nowTime || [];

        if(total_micro_second <= 0){
            if(wx.getStorageSync('Token')){
                that.setData({
                    disabled: true
                })
            }
        }
        // 渲染倒计时时钟 (若已结束，此处输出'0天0小时0分钟0秒')
        that.setData({
            clock: total_micro_second <= 0 ? that.data.deadlineStatus : that.dateformat(total_micro_second)
        });
        if(total_micro_second > 0){
            that.setData({
                disabled: false
            })
        }
        var timerTem = setTimeout(()=> {
            total_micro_second -= 1000;
            that.countDown(that);
        }, 500)
        that.setData({
            timer: timerTem
        })
    },
    /**
     * 时间格式化输出，如11天03小时25分钟19秒  每1s都会调用一次
     * @param micro_second
     * @returns {string}
     */
    dateformat: function(micro_second) {
        // 总秒数
        var second = Math.floor(micro_second / 1000);
        // 小时
        var hr = Math.floor(second / 3600 % 24) < 10 ? "0" + Math.floor(second / 3600 % 24) : Math.floor(second / 3600 % 24);
        // 分钟
        var min = Math.floor(second / 60 % 60) < 10 ? "0" + Math.floor(second / 60 % 60) : Math.floor(second / 60 % 60);
        // 秒
        var sec = Math.floor(second % 60) < 10 ? "0" + Math.floor(second % 60) : Math.floor(second % 60);
        return hr + " : " + min + " : " + sec;
    },
    /**
     *判断是否报餐
     */
    valiBcRecord: function() {
        var that = this;
        //从缓存中获取Token,有则查询报餐记录
        var Token = wx.getStorageSync('Token');
        var deadlineStatus = that.data.deadlineStatus;

        if (Token) {
            if(deadlineStatus == '' || deadlineStatus == '报餐截止'){
                app.globalData.header.Token = Token,
                    wx.showLoading({
                        title: '加载中',
                        mask: true
                    })
                    wx.request({
                        url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcRecord/BcRecordByUserIdAndDinTime',
                        data:{
                            orderMeal: that.data.orderMeal
                        },
                        header: app.globalData.header,
                        success: function(res) {
                            if (res.data.code == '0') {
                                that.setData({
                                    baoCanRecordId: res.data.data.id,
                                    mealOffer: '已报餐',
                                    baoCan: 1,
                                    select: 1
                                })
                            } else {
                                that.setData({
                                    mealOffer: '开始报餐',
                                    baoCan: 0,
                                    select: 0
                                })
                            }
                        },
                        fail: function(res) {
                        }
                    })
                wx.hideLoading();
            }

        }
    },
    /**
     * 点击报餐按钮
     */
    openToast: function() {
        let that = this;
        const Token = wx.getStorageSync('Token');
        if (Token) {
            app.globalData.header.Token = Token;
            let time = that.isWorkDate(new Date());
            if (time == '星期六' && that.data.saturdayCanDiner == false) {
                common.showModel('星期六不能报餐');
            }else if (time == '星期天' && that.data.sundayCanDiner == false) {
                common.showModel('星期天不能报餐');
            }else if (that.data.baoCan == 0){
                that.NewspaperMeal();
            } else {
                that.CancellMeal();
            }
        } else {
            that.isAuthorize();
        }
    },
    /**
     * 判断明天是星期六还是星期天
     * @param time
     * @returns {string}
     */
    isWorkDate: function(time) {
        let Time = time.getTime() + 24 * 60 * 60 * 1000;
        let Tomorrow = new Date(Time);
        if (Tomorrow.getDay() === 6) {
            return "星期六"
        }
        if (Tomorrow.getDay() === 0) {
            return "星期天"
        } else {
            return '工作日'
        }
    },


    /**
     * 判断当前时间是否在报餐时间范围内
     * @param sTime
     * @param eTime
     */
    compareTime: function(){
        let that = this;
        //中餐的开始时间
        let lunchStartTime=that.data.lunchStartTime;
        //中餐的结束时间
        let lunchEndTime=that.data.lunchEndTime;
        //当前时间
        let currentTime = new Date();
        let nowTime = currentTime.getHours()+':'+currentTime.getMinutes()+":"+currentTime.getSeconds();
        //零点
        let  zorePoint = '23:59:59';
        //中午12:00:00
        let noonDay = '12:00:00';
        let Token = wx.getStorageSync('Token');
        if(that.calculateSeconds(nowTime) <= that.calculateSeconds(noonDay) && that.calculateSeconds(nowTime) > that.calculateSeconds(lunchEndTime)){
            //点击报餐时间在结束时间和12：00之前的是报餐截至时间
            that.setData({
                orderMeal: 2,
                deadlineStatus: '报餐截止'
            })
            if(Token){
                that.setData({
                    disabled: true
                });
            }else{
                that.setData({
                    disabled: false
                });
            }
        }else if(that.calculateSeconds(nowTime) < that.calculateSeconds(lunchStartTime) && that.calculateSeconds(nowTime) > that.calculateSeconds(noonDay)){
            //点击报餐时间在开始时间和12：00之后的是未开始状态
            that.setData({
                orderMeal: 2,
                deadlineStatus: '未开始'
            })
            if(Token){
                app.globalData.header.Token = Token;
                that.setData({
                    disabled: true
                });
            }else{
                that.setData({
                    disabled: false
                });
            }
        }else if(that.calculateSeconds(nowTime) >= that.calculateSeconds(lunchStartTime) && that.calculateSeconds(nowTime) <= that.calculateSeconds(zorePoint)){
            //点击报餐时间在开始时间和零点内，就餐时间是明天
            that.setData({
                orderMeal: 1
            });
        }else if(that.calculateSeconds(nowTime) >= 0 && that.calculateSeconds(nowTime) <= that.calculateSeconds(lunchEndTime)){
            //点击报餐时间在零点和结束时间内，就餐时间是今天
            that.setData({
                orderMeal: 2
            })
        }
    },
    /**
     * 计算时间的总秒数
     * @param time
     * @returns {*}
     */
    calculateSeconds: function(time){
        if(time){
            let times = time.split(":");
            let timesHour = times[0];
            let timesMinute = times[1];
            let timesSecond = times[2];
            let countSecond = (parseInt(timesHour) * 60 * 60) + (parseInt(timesMinute) * 60 ) + parseInt(timesSecond)
            return countSecond;
        }
    },
    /**
     * 报餐
     * @constructor
     */
    NewspaperMeal: function() {
        var that = this;
        wx.request({
            url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcRecord/BcRecordMealSave',
            header: app.globalData.header,
            data:{
                orderMeal: that.data.orderMeal
            },
            success: function(res) {
                if(res.data.code == 2){
                    common.showModel('中午报餐功能未开启');
                }else if(res.data.code == 1){
                    common.showModel('请联系管理员给予激活');
                }else {
                    wx.showToast({
                        title: '报餐成功',
                        icon: 'success',
                        duration: 2000
                    });
                    that.setData({
                        mealOffer: '已报餐',
                        baoCanRecordId: res.data.data.id,
                        baoCan: 1,
                        select: 1
                    })
                }
            },
            fail: function(res) {
                console.log("接口调用失败", res.data)
            }
        });
    },
    /**
     * 取消报餐
     * @constructor
     */
    CancellMeal: function() {
        let that = this;
        common.showConfirm('取消报餐','确认取消报餐吗？').then((resolve) =>
            wx.request({
                url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcRecord/deleteBcRecordById',
                header: app.globalData.header,
                data: {
                    id: that.data.baoCanRecordId
                },
                success: function(res) {
                    wx.showToast({
                        title: '取消报餐成功',
                        icon: 'success',
                        duration: 2000
                    });
                    that.setData({
                        mealOffer: '开始报餐',
                        baoCanRecordId: '',
                        baoCan: 0,
                        select: 0
                    });
                }
            })
        );
    }
})