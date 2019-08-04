const common = require("../../utils/util")
const app = getApp();
Page({
    data: {
        hasEmptyGrid: false,
        cur_year: '',
        cur_month: '',
        //选中的那天
        currentDay: '',
        //选中的时间
        dinTime: '',
        //数据
        dateList: [],
        //存放当前月份已预约的日期
        matchDateList: [],
        //存放当前月份已报餐的日期
        allMealRecordList: []
    },
    onLoad(options) {
        const that = this;
        that.setNowDate();
    },
    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        const that = this;
        that.viewAllReservation();
        that.AllMealRecords();
        that.getConfig();
    },
    /**
     * 获取配置信息
     */
    getConfig: function () {
        const that = this;
        wx.request({
            url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/config/getConfig',
            header: app.globalData.header,
            success: function (res) {
                that.setData({
                    saturdayCanDiner: res.data.data.saturdayCanDiner,
                    sundayCanDiner: res.data.data.sundayCanDiner
                })
            }
        })
    },
    /**
     * 将list 存放到预约记录 dateList中
     */
    pustData: function (data) {
        const that = this;
        const list = data;
        for (let i = 0; i < list.length; i++) {
            let bcReserveRecordId = list[i].id;
            let reserveTime = new Date(Date.parse(list[i].reserveTime.replace(/-/g, "/")));
            reserveTime = reserveTime.getFullYear() + "年" + (reserveTime.getMonth() + 1) + "月" + reserveTime.getDate() + "日";
            let reserveTimeWeek = list[i].reserveTimeWeek;
            let dateStr = reserveTime + ' ' + reserveTimeWeek;
            let dateArr = {date: dateStr, time: list[i].reserveTime, id: bcReserveRecordId};
            that.data.dateList.push(dateArr);
        }
        that.setData({
            dateList: that.data.dateList
        });
    },
    /**
     * 查看所有的预约记录
     */
    viewAllReservation: function () {
        const that = this;
        that.data.dateList=[];
        const Token = wx.getStorageSync('Token');
        if (Token) {
            wx.showLoading({
                title: '加载中',
                mask:true
            });
            app.globalData.header.Token = Token,
                wx.request({
                    url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/bcReserveRecord/bcReserveRecordList',
                    header: app.globalData.header,
                    success: res => {
                        that.pustData(res.data.data);
                        that.data.matchDateList=[];
                        that.MatchYearAndMonth();
                        wx.hideLoading();
                    },
                    fail: res => {
                    }
                })
        }
    },

    /**
     * 根据当前年份和月份查询当前年月有哪些日期被预约了
     */
    MatchYearAndMonth:function(){
        const that = this;
        const Token = wx.getStorageSync('Token');
        if(Token){
            const month = that.data.cur_month <10 ? "0" + that.data.cur_month : that.data.cur_month;
            const curYearMonth = that.data.cur_year+'-'+month;
            wx.request({
                url: app.globalData.web_path +'/bc/' + app.globalData.appId + '/bcReserveRecord/getBcReserveRecordListByCurYearAndCurMonth',
                header: app.globalData.header,
                data:{
                    curYearMonth:curYearMonth
                },
                success: function (res) {
                    let list = res.data.data;
                    for(let i = 0;i<list.length;i++){
                        let matchReserveTime = list[i].reserveTime;
                        let orderDay = matchReserveTime.substr(8,2);
                        orderDay = parseInt(orderDay) < 10 ?orderDay.substr(1,1):orderDay;
                        that.data.matchDateList.push(orderDay);
                    }
                    that.setData({
                        matchDateList:that.data.matchDateList,
                    })
                    that.onAppointment();
                }
            })
        }
    },
    /**
     * 根据当前年份和月份查询当前年月有哪些日期被报餐了
     */
    AllMealRecords: function(){
        const that = this;
        that.data.allMealRecordList = [];
        const Token = wx.getStorageSync('Token');
        if(Token){
            let month = that.data.cur_month <10 ? "0" + that.data.cur_month : that.data.cur_month;
            let dinTime = that.data.cur_year + '-' + month;
            wx.request({
                url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcRecord/bcBcRecordByMonth',
                header: app.globalData.header,
                data: {
                    dinTime: dinTime
                },
                success:function (res) {
                    let list = res.data.data;
                    for(let i = 0;i<list.length;i++){
                        let dinTime = list[i].day;
                        that.data.allMealRecordList.push(dinTime);
                    }
                    that.setData({
                        allMealRecordList: that.data.allMealRecordList,
                    })
                },
                fail:function (res) {
                }
            })
        }
    },
    /**
     * 匹配预约记录和日历有哪些是被预约的
     */
    onAppointment:function(){
        const that = this;
        const  days = wx.getStorageSync('days');
        const  orderArr = that.data.matchDateList;
        for (let i=0;i<days.length;i++){
            for(let j=0;j<orderArr.length;j++){
                if(orderArr[j]==days[i].date){
                    days[i].isOrder=true;
                }
            }
        }
        that.setData({days:days});
    },
    /**
     * 点击+号,新增一条记录
     * @param e
     */
    addNewReservation: function (e) {
        const that = this;
        const week = common.getMyDay(new Date(`${that.data.cur_year}/${that.data.cur_month}/${that.data.currentDay + 1}`));
        const Token = wx.getStorageSync('Token');
        const now = new Date();
        const nowYear = now.getFullYear();
        const nowMonth = now.getMonth() + 1;
        const nowYearAndMonth = nowYear+'-'+nowMonth;
        const todayYearAndMonth = that.data.cur_year+'-'+that.data.cur_month;
        const noSelectDay = new Date(Date.parse(that.data.dinTime.replace(/-/g,"/")));
        const selectIndex = that.data.selectIndex+1;
        if (Token) {
            app.globalData.header.Token = Token;
            if(that.data.allMealRecordList.length>0){
                for(let val of that.data.allMealRecordList){
                    if(val == selectIndex){
                        common.showModel('不能预订已报餐的日期');
                        return
                    }
                }
            }
            if(that.data.matchDateList.length>0){
                for(let i = 0;i<that.data.matchDateList.length;i++){
                    if(that.data.matchDateList[i] == selectIndex){
                        common.showModel('你已经预约了');
                        return;
                    }
                }
            }
            if(that.data.selectIndex == that.data.todayIndex && todayYearAndMonth === nowYearAndMonth){
                common.showModel('只能预约比今天大的日期');
            }else if(!that.data.dinTime){
                common.showModel('请选择日期');
            }else if (week == '周六' && that.data.saturdayCanDiner == false) {
                common.showModel('星期六不能预约');
            }else if (week == '周日' && that.data.sundayCanDiner == false) {
                common.showModel('星期天不能预约');
            }else if( noSelectDay < now){
                common.showModel('只能预约比今天大的日期');
            }else {
                wx.request({
                    url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/bcReserveRecord/bcReserveRecordSave',
                    header: app.globalData.header,
                    data: {
                        reserveTime: that.data.dinTime,
                        reserveTimeWeek: week
                    },
                    success: function (res) {
                        //返回的结果为1，说明用户未激活
                        if(res.data.code == 2){
                            common.showModel('中午报餐未开启');
                        }
                        else if (res.data.code == 1) {
                            common.showModel('请联系管理员给予激活');
                        }else{
                            that.viewAllReservation();
                        }
                    },
                    fail: function (res) {
                    }
                })
            }
        }else{
            wx.getSetting({
                success(res) {
                    //没有授权,引导用户授权
                    if (!res.authSetting['scope.userInfo']) {
                    //console.log('没有授权,引导用户授权',res)
                        wx.showModal({
                            title: '用户未授权',
                            content: '如需正常使用红商报餐，请按确定并在授权管理中选中“用户信息”，然后点按确定。最后再重新进入小程序即可正常使用。',
                            showCancel: false,
                            success: function (res) {
                                if (res.confirm) {
                                    wx.openSetting({
                                        success: function success(res) {
                                            console.log('调用openSetting方法success:', res);
                                            wx.getUserInfo({
                                                success: function(res) {
                                                    console.log("userInfo",res);
                                                    that.saveUserInfo(res.userInfo, res.iv, res.signature, res.encryptedData, res.rawData);
                                                }
                                            })
                                        },
                                        fail: function (res) {
                                            console.log('调用openSetting方法fail:', res);
                                        }
                                    });
                                }
                            }
                        })
                    } else {
                        wx.navigateTo({
                            url: '/pages/user-info/index',
                        })
                    }
                }
            })
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
                                        wx.showModal({
                                            content: '为确保红商集团报餐小程序更好的使用，请完善个人信息',
                                            showCancel: false,
                                            success: function(res) {
                                                if (res.confirm) {
                                                    let imgUrl = userInfo.avatarUrl;
                                                    wx.setStorageSync('imgUrl', imgUrl);
                                                    wx.navigateTo({
                                                        url: '/pages/user-info/index',
                                                    })
                                                }
                                            }
                                        });
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
            }
        })
    },
    /**
     * 选择日期
     * @param e
     */
    dateSelectAction: function (e) {
        const that = this;
        let cur_day = e.currentTarget.dataset.idx;
        let month = that.data.cur_month < 10 ? "0" + that.data.cur_month : that.data.cur_month;
        let day = (cur_day + 1) < 10 ? "0" + (cur_day + 1) : (cur_day + 1);
        let dinTime = that.data.cur_year + '-' + month + '-' + day;
        that.setData({
            dinTime: dinTime,
            selectIndex: cur_day,
            currentDay: cur_day
        })
    },
    /**
     * 设置当前时间
     */
    setNowDate: function () {
        const date = new Date();
        const cur_year = date.getFullYear();
        const cur_month = date.getMonth() + 1;
        const todayIndex = date.getDate() - 1;
        const selectIndex = date.getDate() - 1;
        const weeks_ch = ['日', '一', '二', '三', '四', '五', '六'];
        this.calculateEmptyGrids(cur_year, cur_month);
        this.calculateDays(cur_year, cur_month);
        this.setData({
            cur_year: cur_year,
            cur_month: cur_month,
            weeks_ch,
            todayIndex,
            selectIndex
        })
    },
    /**
     * 表示当前月份的前一天，就是year/month/30这天
     * @param year
     * @param month
     * @returns {number}
     */
    getThisMonthDays(year, month) {
        return new Date(year, month, 0).getDate();
    },
    /**
     * 获取当月的第一周第一天是周几
     * @param year
     * @param month
     * @returns {number}
     */
    getFirstDayOfWeek(year, month) {
        return new Date(Date.UTC(year, month - 1, 1)).getDay();
    },
    /**
     * 计算当前年月空的几天
     * @param year
     * @param month
     */
    calculateEmptyGrids(year, month) {
        const firstDayOfWeek = this.getFirstDayOfWeek(year, month);
        let empytGrids = [];
        if (firstDayOfWeek > 0) {
            for (let i = 0; i < firstDayOfWeek; i++) {
                empytGrids.push(i);
            }
            this.setData({
                hasEmptyGrid: true,
                empytGrids
            });
        } else {
            this.setData({
                hasEmptyGrid: false,
                empytGrids: []
            });
        }
    },
    /**
     * 计算当前月份的天数
     * @param year
     * @param month
     */
    calculateDays(year, month) {
        let days = [];
        const thisMonthDays = this.getThisMonthDays(year, month);
        for (let i = 1; i <= thisMonthDays; i++) {
            let obj = {
                date: i,
                isOrder: false
            }
             days.push(obj);
         }
        this.setData({
            days
        });
        wx.setStorageSync('days', days);
    },
    /**
     * 切换日历
     * @param e
     */
    handleCalendar(e) {
        let that = this;
        const handle = e.currentTarget.dataset.handle;
        const cur_year = that.data.cur_year;
        const cur_month = that.data.cur_month;
        if (handle === 'prev') {
            let newMonth = cur_month - 1;
            let newYear = cur_year;
            if (newMonth < 1) {
                newYear = cur_year - 1;
                newMonth = 12;
            }
            that.calculateDays(newYear, newMonth);
            that.calculateEmptyGrids(newYear, newMonth);
            that.setData({
                cur_year: newYear,
                cur_month: newMonth,
                selectIndex: null,
                dinTime: ''
            })

        } else {
            let newMonth = cur_month + 1;
            let newYear = cur_year;
            if (newMonth > 12) {
                newYear = cur_year + 1;
                newMonth = 1;
            }
            that.calculateDays(newYear, newMonth);
            that.calculateEmptyGrids(newYear, newMonth);
            that.setData({
                cur_year: newYear,
                cur_month: newMonth,
                selectIndex: null,
                dinTime: ''
            })
        }
        if(that.data.matchDateList.length>0){
            that.data.matchDateList=[];
        }
        that.MatchYearAndMonth();
        if(that.data.allMealRecordList.length>0){
            that.data.allMealRecordList=[];
        }
        that.AllMealRecords();
    },
    /**
     * 撤销已选择的日期
     * @param event
     */
    longTap(event) {
        var that = this;
        common.showConfirm('撤销报餐','确认撤销' + event.currentTarget.dataset.tipDate + '报餐吗？').then((resolve) =>
            wx.request({
                url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/bcReserveRecord/deleteBcReserveRecord',
                header: app.globalData.header,
                data: {
                    id: event.currentTarget.dataset.selectId
                },
                success: function (res) {
                    if (res.data.data.delResult != 0) {
                        that.viewAllReservation();
                    }
                },
                fail: function (res) {
                }
            })
        );
    }
})
