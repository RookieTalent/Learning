/*
Navicat MySQL Data Transfer

Source Server         : 本阿里云
Source Server Version : 50730
Source Host           : 47.98.178.22:3306
Source Database       : auth_demo

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2021-01-05 16:30:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for music_table
-- ----------------------------
DROP TABLE IF EXISTS `music_table`;
CREATE TABLE `music_table` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `click_number` bigint(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of music_table
-- ----------------------------
INSERT INTO `music_table` VALUES ('1', 'QQ音乐', 'https://y.qq.com/', '158532145');
INSERT INTO `music_table` VALUES ('2', '网易云音乐', 'https://music.163.com/', '125898652');
INSERT INTO `music_table` VALUES ('3', '酷我音乐', 'https://www.kuwo.cn/', '10589875');
INSERT INTO `music_table` VALUES ('4', '虾米音乐', 'https://www.xiami.com/', '12000');
INSERT INTO `music_table` VALUES ('5', '天天动听', 'http://www.ttpod.com/', '4588');
