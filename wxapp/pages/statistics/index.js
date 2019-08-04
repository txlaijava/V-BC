import * as echarts from '../../ec-canvas/echarts';
const app = getApp();
let chart = null //定义变量接收echarts实例,方便改数据
Page({
  data: {
    menuTop: '',
    ec: {
      lazyLoad: true
    },
    tabs: ['今天', '明天'],
    curIndex: 0,
    totalNum: '0',
    bcRecordList: [],
    deptList: [],
    currentPage: 1,
    pageSize: 10,
    emptyText: '该日暂无员工报餐',
    hadEat: false,
  },
  // tab切换
  tabClick(e) {
    var that = this;
    wx.removeStorageSync('datas');
    that.setData({
      curIndex: e.currentTarget.id,
      bcRecordList: [],
      currentPage: 1,
      deptId: '',
      selectIndex: null
    });
    chart.clear(); //清空实例,防止数字闪烁
    that.initChart();
    that.getTotalNum(); //查询报餐人数
    that.getBcRecordList(); // 查询报餐列表
  },
  //页面加载
  onLoad: function(options) {
    // 获取组件
    this.ecComponent = this.selectComponent('#mychart-dom-pie');
  },
  // 菜单栏吸顶
  onShow: function() {
    var that = this;
    wx.removeStorageSync('datas');
    that.setData({
      currentPage: 1,
      bcRecordList: [],
      deptId: '',
      selectIndex:null
    })
    that.initChart(); //初始化环形图
    that.getTotalNum(); //查询报餐人数(默认查询当天的)
    that.getBcRecordList(); // 查询报餐列表（默认查询当天的）
   
  },
  // e-chart 环形图
  initChart: function() {
    const that = this;
    // 获取组件的 canvas、width、height 后的回调函数
    that.ecComponent.init((canvas, width, height) => {
      // 在这里初始化图表
      chart = echarts.init(canvas, null, {
        width: width,
        height: height
      });
      that.setOption(chart);
      // 注意这里一定要返回 chart 实例，否则会影响事件处理等
      return chart;
    });
  },
  //获取图表数据
  setOption: function(chart) {
    var that = this;
    var dataList = [];
    var seriesDataList = [];
    wx.request({
      url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcUserDepartment/countDinnerByDay',
      data: {
        curIndex: that.data.curIndex
      },
      header: app.globalData.header,
      success: function(res) {
        // console.log("res.data",res.data);
        var data = res.data.data;
        let deptArr = [];
        for (var i = 0; i < data.length; i++) {
          var arr = {
            text: data[i][1] + ' ' + data[i][0] + '人',
            id: data[i][2]
          }
          // var datas = i < 2 ? {
          //   name: data[i][1] + ' ' + data[i][0] + '人',
          //   icon: 'circle'
          // } : {
          //   name: data[i][1] + ' ' + data[i][0] + '人',
          //   icon: 'circle',
          //   textStyle: {
          //     fontSize: 13
          //   }
          // };
          var seriesData = {
            value: data[i][0] != 0 ? data[i][0] : '',
            name: data[i][1] + ' ' + data[i][0] + '人'
          }
          //  dataList.push(datas);
          deptArr.push(arr);
          seriesDataList.push(seriesData);
        }
        // console.log("dataList", JSON.stringify(dataList));
        // console.log("seriesDataList", JSON.stringify(seriesDataList));
        let option = {
          legend: {
            orient: 'vertical',
            right: 24,
            y: 'center',
            textStyle: {
              color: '#999999',
              fontSize: 12,
            },
            itemGap: 14,
            data: dataList,
          },
          color: ['#fe5d4d', '#737dde', '#3ba4ff', '#fe9e7f', '#feda5e', '#66dfe2', '#37a1d9'],
          series: [{
            name: '访问来源',
            type: 'pie',
            radius: ['44%', '70%'],
            center: ['33%', '50%'],
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: false,
              }
            },
            labelLine: {
              normal: {
                show: false
              }
            },
            data: seriesDataList
          }]
        }
        chart.setOption(option);
        that.setData({
          deptList: deptArr
        });
      }
    })



  },
  //获取报餐总人数(tab切换时需要调用)
  getTotalNum: function() {
    var that = this;
    wx.request({
      url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcRecord/getTotalRecordByDinTime',
      header: app.globalData.header,
      data: {
        curIndex: that.data.curIndex
      },
      success: function(res) {
        // console.log("num", res);
        if (res.data) {
          that.setData({
            totalNum: res.data.data
          })
        }
      },
      fail: function(res) {
        console.log("调用接口失败！");
      }
    })
  },
  //获取报餐列表(通过curentIndex)
  getBcRecordList: function() {
    var that = this
    wx.showLoading({
      title: '加载中',
      mask: true
    })
    // console.log("上次的长度", that.data.length);
    var addPage = true;
    wx.request({
      url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcRecord/getBcRecordListByDinTime',
      data: {
        curIndex: that.data.curIndex,
        currentPage: that.data.currentPage,
        pageSize: that.data.pageSize,
        deptId: that.data.deptId ? that.data.deptId : ''
      },
      header: app.globalData.header,
      success: function(res) {
        if (res.data) {
          // console.log("长度", that.data.bcRecordList.length);
          var list = res.data.data;
          // console.log("list", list);
          if (list.length < that.data.pageSize) {
            addPage = false;
            that.data.bcRecordList = [];
          } else {
            addPage = true;
          }
          var datas = wx.getStorageSync('datas');
          // console.log("datas", datas);
          if (datas.length != 0) {
            that.data.bcRecordList = datas;
          }
          for (var i = 0; i < list.length; i++) {
            that.data.bcRecordList.push(list[i]);
          }
          //储存上一页的数据
          if (addPage) {
            wx.setStorageSync('datas', that.data.bcRecordList);
          }
          that.setData({
            bcRecordList: that.data.bcRecordList,
            length: list.length,
            hasData: addPage
          })
          // console.log("bcRcordList", that.data.bcRecordList);
        }
        wx.hideLoading();

      },
      fail: function(res) {
        console.log("接口调用失败!")
      }
    })

  },
  /**
   * 下拉
   */
  onReachBottom: function() {
    var that = this;
    that.setData({
      currentPage: that.data.hasData ? that.data.currentPage + 1 : that.data.currentPage, //每次下拉 页码+1(有新数据就+1,没有就页码不变)
    })
    that.getBcRecordList();

  },
  /**
   * 选择部门
   */
  selectDept: function(e) {
    const that = this;
    wx.removeStorageSync('datas');
    let deptId = e.currentTarget.dataset.id;
    let index = e.currentTarget.dataset.index;
    if (that.data.selectIndex == index) {
      index = null,
        deptId = ''
    }
    that.setData({
      deptId: deptId,
      currentPage: 1,
      bcRecordList: [],
      selectIndex: index,
      emptyText: index == null ? '该日暂无员工报餐' : '该部门暂无员工报餐'
    });
    that.getBcRecordList();
  },
  /**
   * 确认就餐
   */
  hadEat: function(e) {
    const that = this;
    let id = e.currentTarget.dataset.id;
    let name = e.currentTarget.dataset.name;
    let index = e.currentTarget.dataset.index;
    if (that.data.bcRecordList[index].hadEat == 0) {
      wx.showModal({
        title: '确认就餐',
        content: '确认 ' + name + ' 就餐?',
        confirmText: '确认',
        confirmColor: '#ff0000',
        success(res) {
          if (res.confirm) {
            wx.request({
              url: app.globalData.web_path + '/bc/' + app.globalData.appId + '/BcRecord/confirmEat',
              data: {
                id: id
              },
              success: function(res) {
                if (res.data.data == 1) {
                  that.data.bcRecordList[index].hadEat = 1
                  that.setData({
                    bcRecordList: that.data.bcRecordList
                  })
                }
              }
            })
          }
        }
      })
    }

  }
});