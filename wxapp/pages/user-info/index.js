// pages/user-info/index
import WxValidate from '../../utils/WxValidate.js'
const common = require("../../utils/util")
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    array: [],
    id: null,
    mobile: null,
    disabled:false,  //发送按钮默认启用
    currentTime:60,   //验证码倒计时
    smsText:'获取验证码',
    imgUrl: 'http://depot1.nipic.com/file/20131128/10593858_11043106759.gif'
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    that.getDepartment();
    that.initValidate();  //验证规则函数
    var url = wx.getStorageSync('imgUrl');
    if(url){
      that.setData({
        imgUrl:url
      })
    }
    // // 页面传值
    // that.setData({
    //   imgUrl:options.imgUrl
    // })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },
  //验证表单输入打印报错信息
  showModal(error) {
    wx.showModal({
        content: error.msg,
        showCancel: false
    })
  },
  //验证函数
  initValidate(){
    const rules = {
      name: {
        required: true
      },
      mobile: {
        required: true,
        tel:true
      },
      codeNo: {
        required: true
      }
    }
    const messages = {
      name: {
        required: '请填写姓名'
      },
      mobile: {
        required:'请填写手机号',
        tel:'请填写正确的手机号'
      },
      codeNo: {
        required: '请填写验证码'
      }
    }
    this.WxValidate = new WxValidate(rules,messages)
  },
  //得到部门信息
  getDepartment: function () {
    var that = this;
    wx.request({
      url: app.globalData.web_path + '/bc/'+app.globalData.appId+'/BcUserDepartment/getBcUserDepartmentList',
      success: function (res) {
        var list = res.data.data;
        that.setData({
          array: list
        })
      }
    })
  },
  //得到输入的手机号
  getPhone: function (e) {
    this.setData({
      mobile: e.detail.value
    })
  },
  //发送验证码
  sendCode: function () {
    var that = this;
    if(!that.data.mobile){
        common.showModel('请输入手机号');
        return;  
    }else{
        //将按钮禁止
        that.setData({
            disabled: true
        })
      wx.request({
        url: app.globalData.web_path + '/bc/'+app.globalData.appId+'/BcUser/bcUserBuyerCode',
        data: {
          mobile: that.data.mobile
        },
        success: function (res) {
          //验证码发送成功弹窗
          if (res.data.data.status=='1'){
              wx.showToast({
                title: res.data.data.message,
                icon:'none',
                duration:2000
              })

            //倒计时
            var currentTime = that.data.currentTime;
            //设置一分钟的倒计时
            var interval = setInterval(function () {
              currentTime--; //每执行一次让倒计时秒数减一
              that.setData({
                smsText: currentTime + 's', //按钮文字变成倒计时对应秒数
              })
              //如果当秒数小于等于0时 停止计时器 且按钮文字变成重新发送 且按钮变成可用状态 倒计时的秒数也要恢复成默认秒数 即让获取验证码的按钮恢复到初始化状态只改变按钮文字
              if (currentTime <= 0) {
                clearInterval(interval)
                that.setData({
                  smsText:'获取验证码',
                  currentTime: 60,
                  disabled: false,
                })
              }
            }, 1000);
          }else{
              wx.showToast({
                title: res.data.data.message,
                icon:'none',
                duration:2000
              })
          }
        },
        fail: function (res) {
        }
      })
    }
  },
  //选择部门
  bindPickerChange: function (e) {
    var that = this;
    //console.log('picker发送选择改变，携带值为', e.detail.value)
    that.setData({
      id: e.detail.value
    })
  },
  //提交表单
  formSubmit: function (e) {
    var that = this
    var userDepartmentName = e.detail.value.userDepartmentName
    //console.log('form发生了submit事件，携带的数据为：',e.detail.value)
    const params = e.detail.value
      //校验表单
      if(!that.WxValidate.checkForm(params)){
        const error = that.WxValidate.errorList[0]
          that.showModal(error)
          return false
      }
      if (!userDepartmentName){
        wx.showModal({
          content: '请选择部门信息',
          showCancel: false  //去掉取消按钮
        });
      }else{
          wx.showLoading({
              title: '注册中',
              mask:true
          });
          wx.request({
              url: app.globalData.web_path + '/bc/'+app.globalData.appId+'/BcUser/bcUserValiCode',
              data: {
                  mobile: e.detail.value.mobile,
                  codeNo: e.detail.value.codeNo
              },
              success: function (res) {
                  //console.log("验证结果：", res.data.data.message);
                  if (res.data.data.status == '1') {
                      wx.setStorageSync('userDepartmentName', userDepartmentName);
                      wx.request({
                          url: app.globalData.web_path + '/bc/'+app.globalData.appId+'/BcUser/save',
                          data: {
                              name: e.detail.value.name,
                              userDepartmentId: e.detail.value.userDepartmentId,
                              mobile: e.detail.value.mobile,
                              openid: wx.getStorageSync('openid')
                          },
                          header: app.globalData.header,
                          success: function (res) {
                              console.log('RES-->',res);
                              console.log('TOKEN:--->',res.data.data.Token);
                              wx.setStorageSync('nickName', e.detail.value.name);
                              //将Token 放入缓存中
                              if(res.data.data.Token){
                                  wx.setStorageSync('Token', res.data.data.Token);
                              }
                              wx.hideLoading();
                              wx.navigateBack({
                                  url: '/pages/index/index'
                              })
                          },
                          fail: function (res) {
                          }
                      })
                  } else { //弹出提示
                      wx.showToast({
                          title: res.data.data.message,
                          icon: 'none',
                          duration: 2000
                      })
                  }
              },
              fail: function (res) {

              }
          })
      }
  }
})