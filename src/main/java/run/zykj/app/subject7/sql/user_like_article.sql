/*
Navicat MySQL Data Transfer

Source Server         : 本阿里云
Source Server Version : 50730
Source Host           : 47.98.178.22:3306
Source Database       : auth_demo

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-01-05 14:58:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_like_article
-- ----------------------------
DROP TABLE IF EXISTS `user_like_article`;
CREATE TABLE `user_like_article` (
  `user_id` bigint(20) NOT NULL,
  `article_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_like_article
-- ----------------------------
