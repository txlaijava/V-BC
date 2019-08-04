# V-BC
微报餐 - 企业报餐小程序前后端开源

## 简介
微报餐为企业内部解决报餐管控平台,前端使用微信小程序,后端服务使用SpringBoot,管理平台使用VUE构建。

## 功能
* 中餐、晚餐报餐、取消报餐、报餐截止时间
* 预约报餐
* 报餐统计、部门统计。
* 后台 - 用户审核
* 后台 - 报餐记录管理
* 后台 - 部门管理
* 后台 - 报餐统计
* 后台 - 报餐配置

## 界面
### 小程序界面
<center class="half">
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/WechatIMG797.jpeg" width="30%"/>
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/WechatIMG798.jpeg" width="30%" style="padding-left:2%"/>
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/WechatIMG799.jpeg" width="30%" style="padding-left:2%"/>
</center>

### 后端管理界面
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/%E5%91%98%E5%B7%A5.jpg" />
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/%E9%83%A8%E9%97%A8.jpg" />
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/%E6%8A%A5%E9%A4%90%E8%AE%B0%E5%BD%95.jpg" />
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/%E6%8A%A5%E9%A4%90%E7%BB%9F%E8%AE%A1.jpg" />
<img src="http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/%E7%B3%BB%E7%BB%9F%E9%85%8D%E7%BD%AE.jpg" />

## 技术栈
### 前端
* 微信小程序

### 后端
* SpringBoot
* [XXL-Job 分布式任务调度平台](https://github.com/xuxueli/xxl-job)
* 短信 - 阿里大于
* TOKEN - JWT
* [微信开发工具包](https://github.com/Wechat-Group/WxJava)
* swagger - RESTFUL接口的、基于YAML、JSON语言的文档在线自动生成、代码自动生成的工具。
* redis
* MySql

## 代码结构
* wxApp文件夹 - 微信小程序代码
* 管理端的代码 - 满200Start开源。
* server - 服务端代码，现在架构考虑到SaaS模式，有需要也可以直接连我的服务进行使用。

#### 服务端搭建
1. 导入数据库文件。SQL文件在doc文件夹下。
2. 配置短信接口
3. 配置微信授权数据

## 交流
使用沟通交流钉钉:
![](http://rblc.oss-cn-qingdao.aliyuncs.com/other/txl/WechatIMG801.jpeg)
