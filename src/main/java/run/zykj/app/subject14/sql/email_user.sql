/*
Navicat MySQL Data Transfer

Source Server         : 本阿里云
Source Server Version : 50730
Source Host           : 47.98.178.22:3306
Source Database       : auth_demo

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-01-05 15:03:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for email_user
-- ----------------------------
DROP TABLE IF EXISTS `email_user`;
CREATE TABLE `email_user` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of email_user
-- ----------------------------
INSERT INTO `email_user` VALUES ('103', '小林', '2456187741@qq.com');
