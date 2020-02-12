/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50628
Source Host           : localhost:3306
Source Database       : wanart_bi

Target Server Type    : MYSQL
Target Server Version : 50628
File Encoding         : 65001

Date: 2020-02-12 16:56:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for groupauth
-- ----------------------------
DROP TABLE IF EXISTS `groupauth`;
CREATE TABLE `groupauth` (
  `groupId` int(11) NOT NULL,
  `queryUser` tinyint(4) NOT NULL DEFAULT '0' COMMENT '查询用户权限',
  `createUser` tinyint(4) NOT NULL DEFAULT '0' COMMENT '创建用户权限',
  `updateUser` tinyint(4) NOT NULL DEFAULT '0' COMMENT '更改用户权限',
  `deleteUser` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除用户权限',
  `queryProject` tinyint(4) NOT NULL DEFAULT '0' COMMENT '查询项目权限',
  `createProject` tinyint(4) NOT NULL DEFAULT '0' COMMENT '创建用户权限',
  `updateProject` tinyint(4) NOT NULL DEFAULT '0' COMMENT '更改项目权限',
  `deleteProject` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除项目权限',
  `queryData` tinyint(4) NOT NULL DEFAULT '0' COMMENT '查询数据权限',
  `desc` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupauth
-- ----------------------------
INSERT INTO `groupauth` VALUES ('1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '超管组 最高权限');
INSERT INTO `groupauth` VALUES ('2', '1', '1', '1', '1', '1', '0', '1', '0', '1', '管理员组 受限权限');
INSERT INTO `groupauth` VALUES ('3', '1', '0', '0', '0', '1', '0', '0', '0', '1', '分析师组 查询权限');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目id',
  `name` varchar(128) NOT NULL DEFAULT '' COMMENT '项目名称',
  `timeCreated` datetime NOT NULL COMMENT '创建时间',
  `desc` varchar(256) NOT NULL DEFAULT '' COMMENT '项目描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', 'mxzm', '2020-01-04 17:17:37', '冒险之门');
INSERT INTO `project` VALUES ('2', 'dldl', '2020-01-04 17:18:41', '斗罗大陆');
INSERT INTO `project` VALUES ('3', 'test', '2020-01-07 16:08:36', 'created by trail for test');

-- ----------------------------
-- Table structure for request_log
-- ----------------------------
DROP TABLE IF EXISTS `request_log`;
CREATE TABLE `request_log` (
  `userId` int(11) NOT NULL COMMENT '用户id',
  `userName` varchar(64) NOT NULL COMMENT '用户名',
  `url` varchar(1024) NOT NULL COMMENT '请求url',
  `requestData` text NOT NULL COMMENT '请求参数',
  `requestTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of request_log
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(64) NOT NULL COMMENT '用户名',
  `group` int(11) NOT NULL COMMENT '所属权限组',
  `projects` varchar(2048) NOT NULL DEFAULT '' COMMENT '所属项目集合',
  `createdTime` datetime NOT NULL COMMENT '创建时间',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '上次登录时间',
  `password` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '1', '', '2020-01-04 16:21:34', '2020-01-06 18:03:05', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES ('2', 'manager_mxzm', '2', '1', '2020-01-04 17:19:21', '2020-01-04 17:19:23', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES ('3', 'manager_dldl', '2', '2', '2020-01-04 17:19:51', '2020-01-04 17:19:53', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES ('4', 'manager_all', '2', '1:2', '2020-01-04 17:20:14', '2020-01-04 17:20:16', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES ('5', 'data_mxzm', '3', '1', '2020-01-04 17:20:45', '2020-01-04 17:20:47', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES ('6', 'data_dldl', '3', '2', '2020-01-04 17:21:09', '2020-01-04 17:21:13', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES ('7', 'data_all', '3', '1:2', '2020-01-04 17:21:43', '2020-01-04 17:21:47', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `user` VALUES ('9', 'trail', '2', '', '2020-01-06 18:08:46', '2020-01-06 18:09:11', 'e10adc3949ba59abbe56e057f20f883e');
