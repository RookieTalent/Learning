/*
Navicat MySQL Data Transfer

Source Server         : 本阿里云
Source Server Version : 50730
Source Host           : 47.98.178.22:3306
Source Database       : auth_demo

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-01-05 14:58:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `article_name` varchar(255) DEFAULT NULL COMMENT '文章名字',
  `content` text COMMENT '文章内容',
  `total_like_count` bigint(255) DEFAULT NULL COMMENT '文章点赞总数',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1000', '《可能》', '没有可能', '369', '2021-01-05 10:49:06', '2021-01-05 10:49:09');
