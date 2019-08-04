/*
 Navicat MySQL Data Transfer

 Source Server         : 报餐
 Source Server Version : 50616
 Source Host           : rds11nz6ylt81e54w0a8o.mysql.rds.aliyuncs.com
 Source Database       : baocan

 Target Server Version : 50616
 File Encoding         : utf-8

 Date: 08/04/2019 15:46:39 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `bc_application`
-- ----------------------------
DROP TABLE IF EXISTS `bc_application`;
CREATE TABLE `bc_application` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id;id',
  `addtime` datetime DEFAULT NULL COMMENT '新增时间',
  `deletestatus` varchar(1) DEFAULT NULL COMMENT '删除状态',
  `REVISION` int(11) DEFAULT NULL COMMENT '乐观锁',
  `app_name` varchar(32) DEFAULT NULL COMMENT '应用名称',
  `app_id` varchar(32) DEFAULT NULL COMMENT '应用id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='应用表;';

-- ----------------------------
--  Table structure for `bc_banner`
-- ----------------------------
DROP TABLE IF EXISTS `bc_banner`;
CREATE TABLE `bc_banner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` datetime DEFAULT NULL,
  `deletestatus` varchar(3) DEFAULT NULL,
  `app_id` varchar(96) DEFAULT NULL,
  `img_url` varchar(300) DEFAULT NULL,
  `img_link` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `bc_config`
-- ----------------------------
DROP TABLE IF EXISTS `bc_config`;
CREATE TABLE `bc_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id;id',
  `user_need_approve` bit(1) DEFAULT NULL COMMENT '用户是否需要审核',
  `saturday_can_diner` bit(1) DEFAULT NULL COMMENT '周六是否可报餐',
  `sunday_can_diner` bit(1) DEFAULT NULL COMMENT '周日是否可报餐',
  `end_time` varchar(11) DEFAULT NULL COMMENT '每日报餐截止时间',
  `app_id` varchar(32) NOT NULL COMMENT '微信AppId',
  `lunch_order_time` varchar(32) DEFAULT NULL COMMENT '午餐报餐时间',
  `dinner_order_time` varchar(32) DEFAULT NULL COMMENT '晚餐报餐时间',
  `lunch_can_meal` bit(1) DEFAULT NULL COMMENT '午餐是否可报餐',
  `dinner_can_meal` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT=';';

-- ----------------------------
--  Table structure for `bc_record`
-- ----------------------------
DROP TABLE IF EXISTS `bc_record`;
CREATE TABLE `bc_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id;id',
  `addtime` datetime NOT NULL COMMENT '新增时间',
  `deletestatus` varchar(1) DEFAULT NULL COMMENT '删除状态',
  `REVISION` int(11) DEFAULT NULL COMMENT '乐观锁',
  `app_id` varchar(32) NOT NULL COMMENT '微信AppId',
  `user_id` bigint(20) NOT NULL COMMENT '报餐用户id',
  `bc_type` int(11) NOT NULL COMMENT '报餐类型;0：早餐  1：中餐 2：晚餐',
  `bc_channel` int(11) DEFAULT NULL COMMENT '报餐渠道;0:手动报餐  1:预约报餐  2:补报',
  `dintime` datetime NOT NULL COMMENT '就餐时间',
  `had_eat` int(11) DEFAULT NULL COMMENT '是否就餐;0:未就餐 1:就餐',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=252 DEFAULT CHARSET=utf8mb4 COMMENT=';';

-- ----------------------------
--  Table structure for `bc_reserve_record`
-- ----------------------------
DROP TABLE IF EXISTS `bc_reserve_record`;
CREATE TABLE `bc_reserve_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id;id',
  `addtime` datetime DEFAULT NULL COMMENT '新增时间',
  `deletestatus` varchar(1) DEFAULT NULL COMMENT '删除状态',
  `REVISION` int(11) DEFAULT NULL COMMENT '乐观锁',
  `app_id` varchar(32) NOT NULL COMMENT '微信AppId',
  `bc_user_id` bigint(20) DEFAULT NULL COMMENT '预定用户id',
  `reserve_time` date DEFAULT NULL COMMENT '预定时间',
  `reserve_time_week` varchar(32) DEFAULT NULL COMMENT '预定时间-周几',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COMMENT=';';

-- ----------------------------
--  Table structure for `bc_user`
-- ----------------------------
DROP TABLE IF EXISTS `bc_user`;
CREATE TABLE `bc_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id;id',
  `addtime` datetime DEFAULT NULL COMMENT '新增时间',
  `deletestatus` varchar(1) DEFAULT NULL COMMENT '删除状态',
  `REVISION` int(11) DEFAULT NULL COMMENT '乐观锁',
  `app_id` varchar(32) NOT NULL COMMENT '微信AppId',
  `name` varchar(32) DEFAULT NULL COMMENT '用户姓名',
  `user_department_id` bigint(20) DEFAULT NULL COMMENT '所属部门',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `status` int(11) DEFAULT NULL COMMENT '状态;0:未激活  1:激活',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=130 DEFAULT CHARSET=utf8mb4 COMMENT='报餐用户;';

-- ----------------------------
--  Table structure for `bc_user_department`
-- ----------------------------
DROP TABLE IF EXISTS `bc_user_department`;
CREATE TABLE `bc_user_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id;id',
  `name` varchar(32) DEFAULT NULL COMMENT '部门名称',
  `app_id` varchar(32) NOT NULL COMMENT '微信AppId',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT=';';

-- ----------------------------
--  Table structure for `bc_user_wx`
-- ----------------------------
DROP TABLE IF EXISTS `bc_user_wx`;
CREATE TABLE `bc_user_wx` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id;id',
  `addtime` datetime DEFAULT NULL COMMENT '新增时间',
  `deletestatus` varchar(1) DEFAULT NULL COMMENT '删除状态',
  `REVISION` int(11) DEFAULT NULL COMMENT '乐观锁',
  `app_id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '微信APPID',
  `bc_user_id` bigint(20) DEFAULT NULL COMMENT '关联用户主表',
  `openid` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT 'openid',
  `nickname` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '昵称',
  `gender` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别',
  `language` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '语言',
  `city` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '城市',
  `province` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '省份',
  `country` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '国家',
  `avatarurl` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '头像链接',
  `unionid` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT 'unionId',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNQ_USER_WX_OPENID` (`app_id`,`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COMMENT=';';

-- ----------------------------
--  Table structure for `shopping_template`
-- ----------------------------
DROP TABLE IF EXISTS `shopping_template`;
CREATE TABLE `shopping_template` (
  `id` bigint(20) DEFAULT NULL,
  `addTime` datetime DEFAULT NULL,
  `deleteStatus` bit(1) DEFAULT NULL,
  `content` text,
  `info` varchar(765) DEFAULT NULL,
  `mark` varchar(765) DEFAULT NULL,
  `open` bit(1) DEFAULT NULL,
  `title` varchar(765) DEFAULT NULL,
  `type` varchar(765) DEFAULT NULL,
  `code_id` varchar(765) DEFAULT NULL,
  `msg_url` varchar(765) DEFAULT NULL,
  `platform_type` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
