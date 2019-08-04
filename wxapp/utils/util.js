const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}
/**
 * 判断日期为星期几
 * @param date
 * @returns {string}
 */
let getMyDay = (date) =>{
    let week;
    if (date.getDay() === 0) {
        week = "周日"
    } else if (date.getDay() === 1) {
        week = "周一"
    } else if (date.getDay() === 2) {
        week = "周二"
    } else if (date.getDay() === 3) {
        week = "周三"
    } else if (date.getDay() === 4) {
        week = "周四"
    } else if (date.getDay() === 5) {
        week = "周五"
    } else {
        week = "周六"
    }
    return week;
}

/**
 * 打印日志信息
 * @param name
 * @param log
 */
let log = (name,log) =>{
   console.log(name,log);
}

/**
 * 弹出提示框
 * @param content
 * @param showCancel
 */
let showModel = (content) =>{
    return wx.showModal({
        content: content,
        showCancel: false
    });
}
/**
 * 长按弹出提示框
 * @param title
 * @param content
 * @param confirmText
 * @param cancelText
 */
let showConfirm = (title,content,confirmText = '撤销',cancelText = "取消") =>{
    return new Promise((resolve)=> {
        wx.showModal({
            title: title,
            content: content,
            confirmText, cancelText,
            success(res) {
                if (res.confirm) {
                    resolve();
                }
            }
        })
    })
}
module.exports = {
  formatTime: formatTime,
    getMyDay: getMyDay,
    log: log,
    showModel: showModel,
    showConfirm: showConfirm,
}
