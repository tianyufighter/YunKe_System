/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : 1.14.58.8:3306
 Source Schema         : yunke

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 26/04/2023 23:34:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '作者id',
  `article_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `article_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `article_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态值 1表示公开，2表示私密',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `article_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章纯文本内容',
  `violation` int(11) NULL DEFAULT NULL COMMENT '是否违反规则(0表示未违反规则，1表示违反规则，2表示待审核)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES (10, 2, '最美的太阳', '<h1><a id=\"_0\"></a>我是文章标题</h1>\n<h2><a id=\"_1\"></a>我有一个梦，像雨后彩虹</h2>\n', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/19/8a83df46-7599-4352-9a4d-a10ab3bc9822.jpg', '文学', 1, '2023-03-19 13:01:22', '# 我是文章标题\n## 我有一个梦，像雨后彩虹', 2);
INSERT INTO `blog` VALUES (14, 2, '标题最新版', '<h1><a id=\"_0\"></a>我是文章标题</h1>\n<h2><a id=\"_1\"></a>我是内容</h2>\n', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/19/8a83df46-7599-4352-9a4d-a10ab3bc9822.jpg', '标签2', 1, '2023-03-19 16:10:55', '# 我是文章标题\n## 我是内容', 0);
INSERT INTO `blog` VALUES (13, 2, '标题4', '<h1><a id=\"_0\"></a>我是文章标题</h1>\n<h2><a id=\"1_1\"></a>我是内容1</h2>\n', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/19/8a83df46-7599-4352-9a4d-a10ab3bc9822.jpg', '标签1', 1, '2023-03-17 16:10:24', '# 我是文章标题\n## 我是内容1', 2);
INSERT INTO `blog` VALUES (11, 2, '标题2', '<h1><a id=\"_0\"></a>我是文章标题</h1>\n<h2><a id=\"_1\"></a>我是内容</h2>\n', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/19/8a83df46-7599-4352-9a4d-a10ab3bc9822.jpg', '标签1', 1, '2023-03-19 13:02:41', '# 我是文章标题\n## 我是内容', 0);
INSERT INTO `blog` VALUES (12, 2, '标题3', '<h1><a id=\"_0\"></a>我是文章标题</h1>\n<h2><a id=\"_1\"></a>我是内容</h2>\n', NULL, '标签3', 1, '2023-03-19 16:08:58', '# 我是文章标题\n## 我是内容', 2);
INSERT INTO `blog` VALUES (15, 2, '标题6', '# 我是文章标题\n## 我是内容', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/19/8a83df46-7599-4352-9a4d-a10ab3bc9822.jpg', '标签4', 1, '2023-03-19 16:11:43', '# 我是文章标题\n## 我是内容', 0);

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '评论用户id',
  `blog_id` int(11) NULL DEFAULT NULL COMMENT '评论帖子id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
INSERT INTO `blog_comment` VALUES (1, 2, 10, '牛啊，老铁', '2023-03-19 18:18:13');
INSERT INTO `blog_comment` VALUES (2, 2, 10, '666', '2023-03-19 18:18:27');
INSERT INTO `blog_comment` VALUES (3, 2, 10, '你是天秀吗?', '2023-03-19 18:19:50');
INSERT INTO `blog_comment` VALUES (4, 2, 10, '不愧是你', '2023-03-19 18:19:52');
INSERT INTO `blog_comment` VALUES (5, 2, 10, '你干嘛', '2023-03-19 18:19:56');
INSERT INTO `blog_comment` VALUES (6, 2, 10, '你这个问题有点不对呀', '2023-03-19 18:19:59');
INSERT INTO `blog_comment` VALUES (7, 2, 10, '大哥，写得太好了，赞赞赞', '2023-03-19 18:20:01');
INSERT INTO `blog_comment` VALUES (8, 2, 10, '厉害', '2023-03-19 19:41:32');
INSERT INTO `blog_comment` VALUES (9, 2, 10, '牛逼', '2023-03-19 19:42:15');
INSERT INTO `blog_comment` VALUES (10, 2, 10, '牛啊', '2023-03-19 19:43:03');
INSERT INTO `blog_comment` VALUES (11, 2, 11, '不错不错', '2023-04-14 23:01:42');
INSERT INTO `blog_comment` VALUES (12, 2, 15, 'hello', '2023-04-15 21:09:19');
INSERT INTO `blog_comment` VALUES (13, 2, 14, '不错不错', '2023-04-24 10:57:54');

-- ----------------------------
-- Table structure for calendar
-- ----------------------------
DROP TABLE IF EXISTS `calendar`;
CREATE TABLE `calendar`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calendar_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历事件标题',
  `calendar_desc` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历事件内容',
  `user_id` int(11) NOT NULL COMMENT '日历事件发布者',
  `start_time` datetime NOT NULL COMMENT '日历事件开始时间',
  `end_time` datetime NOT NULL COMMENT '日历时间结束时间',
  `create_time` datetime NOT NULL COMMENT '日历时间创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of calendar
-- ----------------------------
INSERT INTO `calendar` VALUES (8, 'dfadfd', 'dafdsafd', 2, '2023-03-11 00:00:00', '2023-03-15 00:00:00', '2023-03-11 22:36:21');
INSERT INTO `calendar` VALUES (12, '工作学习', '进行工作学习', 2, '2023-04-24 00:00:00', '2023-04-26 00:00:00', '2023-04-24 10:55:58');
INSERT INTO `calendar` VALUES (10, '工作学习', '进行工作学习', 2, '2023-04-24 00:00:00', '2023-04-26 00:00:00', '2023-04-24 10:55:03');
INSERT INTO `calendar` VALUES (11, '完善登录和注册模块', '完善登录和注册模块', 2, '2023-04-24 00:00:00', '2023-04-25 00:00:00', '2023-04-24 10:55:44');
INSERT INTO `calendar` VALUES (13, '完善登录和注册模块', '完善登录和注册模块', 2, '2023-04-24 00:00:00', '2023-04-25 00:00:00', '2023-04-24 10:56:03');

-- ----------------------------
-- Table structure for calendar_tag
-- ----------------------------
DROP TABLE IF EXISTS `calendar_tag`;
CREATE TABLE `calendar_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `calendar_id` int(11) NOT NULL COMMENT '日历id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of calendar_tag
-- ----------------------------
INSERT INTO `calendar_tag` VALUES (3, 8, 1);
INSERT INTO `calendar_tag` VALUES (7, 12, 1);
INSERT INTO `calendar_tag` VALUES (5, 10, 4);
INSERT INTO `calendar_tag` VALUES (6, 11, 4);
INSERT INTO `calendar_tag` VALUES (8, 13, 3);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) NOT NULL COMMENT '评论帖子ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '评论内容',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 9, '你好', '2023-03-10 00:08:34', 2);
INSERT INTO `comment` VALUES (2, 9, '牛ber', '2023-03-10 00:12:51', 2);
INSERT INTO `comment` VALUES (3, 10, '写得很好', '2023-03-10 00:19:48', 2);
INSERT INTO `comment` VALUES (4, 10, '哈哈哈', '2023-03-10 00:31:05', 2);
INSERT INTO `comment` VALUES (5, 10, '哈哈哈', '2023-03-10 00:32:45', 2);
INSERT INTO `comment` VALUES (6, 8, '牛ber', '2023-03-10 00:35:26', 2);
INSERT INTO `comment` VALUES (7, 9, '或哈哈', '2023-03-10 00:37:04', 2);
INSERT INTO `comment` VALUES (8, 8, '非常好', '2023-03-10 00:38:53', 2);
INSERT INTO `comment` VALUES (9, 7, '真的', '2023-03-10 08:24:57', 2);
INSERT INTO `comment` VALUES (10, 7, '哈哈哈', '2023-03-10 08:30:11', 2);
INSERT INTO `comment` VALUES (11, 7, '沪江经', '2023-03-10 08:35:51', 2);
INSERT INTO `comment` VALUES (12, 7, '牛是', '2023-03-10 08:43:37', 2);
INSERT INTO `comment` VALUES (13, 8, '哈哈哈', '2023-03-10 08:50:58', 2);
INSERT INTO `comment` VALUES (14, 10, '嘿嘿', '2023-03-10 08:52:16', 2);
INSERT INTO `comment` VALUES (15, 10, '呵呵', '2023-03-10 08:54:25', 2);
INSERT INTO `comment` VALUES (16, 10, '非得发', '2023-03-10 09:04:01', 2);
INSERT INTO `comment` VALUES (17, 10, '估分估', '2023-03-10 09:08:36', 2);
INSERT INTO `comment` VALUES (18, 17, 'CommonResult(code=200, data=不错不错, message=成功)', '2023-04-24 10:49:12', 2);

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `config_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键名',
  `config_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统内置(Y是 N否)',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config
-- ----------------------------
INSERT INTO `config` VALUES (1, '账号自助-验证码开关', 'sys.account.captchaEnabled', 'true', 'Y', 2, '2023-03-16 21:16:43', '是否开启验证码功能（true开启，false关闭）');
INSERT INTO `config` VALUES (2, '用户登录-黑名单列表', 'sys.login.blockUsernameList', 'zhangsan;lisi;wangwu', 'Y', 2, '2023-03-16 21:17:44', '设置登录的用户名黑名单限制，多个匹配项以;分隔，支持匹配（*通配）');
INSERT INTO `config` VALUES (3, '账号自助-是否开启邮箱验证功能', 'sys.account.emailValidate', 'false', 'Y', 2, '2023-03-16 21:18:22', '是否开启邮箱验证功能（true开启，false关闭）');
INSERT INTO `config` VALUES (4, '参数名称', 'keyName', 'hahaha', 'N', 2, '2023-03-17 10:03:08', '我是测试用的');
INSERT INTO `config` VALUES (5, '账号自助-是否开启邮箱验证功能哈哈哈', 'dad', 'ada', 'N', 2, '2023-03-17 10:05:13', 'sdfa');
INSERT INTO `config` VALUES (6, '账号自助-是否开启邮箱验证功能哈', 'dad2r', 'ada', 'N', 2, '2023-03-17 10:10:20', 'dafd');
INSERT INTO `config` VALUES (7, '账号自助-是否开启邮箱验证功能哈', 'dfdfsd', 'ada', 'N', 2, '2023-03-17 10:10:47', 'fadfad');
INSERT INTO `config` VALUES (8, 'dfadfadf', 'afdafd', 'sdfaf', 'N', 2, '2023-03-17 10:11:31', 'dfadfasdf');

-- ----------------------------
-- Table structure for contactperson
-- ----------------------------
DROP TABLE IF EXISTS `contactperson`;
CREATE TABLE `contactperson`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `friend_id` int(11) NULL DEFAULT NULL COMMENT '朋友id',
  `message_id` int(11) NULL DEFAULT NULL COMMENT '最后一次返送信息的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of contactperson
-- ----------------------------
INSERT INTO `contactperson` VALUES (4, 2, 2, 33);
INSERT INTO `contactperson` VALUES (6, 14, 2, 35);

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `post_id` int(11) NOT NULL COMMENT '帖子id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of likes
-- ----------------------------

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名字',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录状态(0成功 1失败)',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提示信息',
  `login_time` datetime NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES (1, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 09:30:17');
INSERT INTO `login_log` VALUES (2, 'Titos', 'Chrome 11', 'Windows 10', '1', '用户名或密码错误', '2023-04-15 09:31:26');
INSERT INTO `login_log` VALUES (3, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 09:31:35');
INSERT INTO `login_log` VALUES (4, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 14:01:38');
INSERT INTO `login_log` VALUES (5, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 14:02:55');
INSERT INTO `login_log` VALUES (6, 'zhangsan', 'Chrome 11', 'Windows 10', '1', '账号位于黑名单', '2023-04-15 14:07:31');
INSERT INTO `login_log` VALUES (7, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 14:35:49');
INSERT INTO `login_log` VALUES (8, '李治强', 'Chrome Mobile', 'Android 1.x', '0', '用户登录成功', '2023-04-15 17:19:45');
INSERT INTO `login_log` VALUES (9, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 17:24:09');
INSERT INTO `login_log` VALUES (10, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 17:27:37');
INSERT INTO `login_log` VALUES (11, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 20:30:57');
INSERT INTO `login_log` VALUES (12, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 21:06:39');
INSERT INTO `login_log` VALUES (13, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 21:10:59');
INSERT INTO `login_log` VALUES (14, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-15 21:40:39');
INSERT INTO `login_log` VALUES (15, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-24 10:41:19');
INSERT INTO `login_log` VALUES (16, 'Titos', 'Chrome 11', 'Windows 10', '0', '用户登录成功', '2023-04-26 23:32:18');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_id` int(11) NULL DEFAULT NULL COMMENT '发送用户id',
  `receive_id` int(11) NULL DEFAULT NULL COMMENT '接收用户id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `image_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送的图片地址',
  `release_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `is_complete` int(11) NULL DEFAULT NULL COMMENT '是否接收',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (5, 2, 2, 'nihao', NULL, '2023-04-14 14:56:10', 1);
INSERT INTO `message` VALUES (8, 14, 2, 'nihoa', NULL, '2023-04-14 15:44:52', 1);
INSERT INTO `message` VALUES (7, 14, 2, '张三', NULL, '2023-04-14 15:36:55', 1);
INSERT INTO `message` VALUES (9, 14, 2, 'hai', NULL, '2023-04-14 17:43:09', 1);
INSERT INTO `message` VALUES (10, 2, 14, 'nihao', NULL, '2023-04-14 17:43:27', 1);
INSERT INTO `message` VALUES (11, 14, 2, 'nihao', NULL, '2023-04-14 17:43:33', 1);
INSERT INTO `message` VALUES (12, 14, 2, '很高兴认识你', NULL, '2023-04-14 17:43:45', 1);
INSERT INTO `message` VALUES (13, 14, 2, '空间的理解😂', NULL, '2023-04-14 20:15:19', 1);
INSERT INTO `message` VALUES (14, 14, 14, '🇯🇵️', NULL, '2023-04-14 20:31:39', 1);
INSERT INTO `message` VALUES (15, 14, 14, '🇬🇪🏽‍♀️🏽', NULL, '2023-04-14 20:35:12', 1);
INSERT INTO `message` VALUES (16, 14, 14, '🈸🈴', NULL, '2023-04-14 20:35:20', 1);
INSERT INTO `message` VALUES (17, 14, 14, '💊🔧', NULL, '2023-04-14 20:35:29', 1);
INSERT INTO `message` VALUES (18, 14, 14, '🔈🔉', NULL, '2023-04-14 20:35:34', 1);
INSERT INTO `message` VALUES (19, 14, 14, '🎇🎑', NULL, '2023-04-14 20:35:40', 1);
INSERT INTO `message` VALUES (20, 14, 14, '😀😃', NULL, '2023-04-14 21:36:09', 1);
INSERT INTO `message` VALUES (21, 14, 14, '😀你好', NULL, '2023-04-14 21:37:03', 1);
INSERT INTO `message` VALUES (22, 14, 2, '你好', NULL, '2023-04-14 21:37:49', 1);
INSERT INTO `message` VALUES (23, 14, 2, '😀', NULL, '2023-04-14 21:37:53', 1);
INSERT INTO `message` VALUES (24, 14, 2, '😂', NULL, '2023-04-14 21:40:16', 1);
INSERT INTO `message` VALUES (25, 14, 14, '😀', NULL, '2023-04-14 21:42:40', 1);
INSERT INTO `message` VALUES (26, 14, 14, '😅你好🤣', NULL, '2023-04-14 21:58:51', 1);
INSERT INTO `message` VALUES (27, 14, 2, '你好', NULL, '2023-04-14 22:37:12', 1);
INSERT INTO `message` VALUES (28, 14, 2, 'nice to meet you😄', NULL, '2023-04-14 22:37:28', 1);
INSERT INTO `message` VALUES (29, 2, 14, '你谁啊', NULL, '2023-04-14 22:37:44', 1);
INSERT INTO `message` VALUES (30, 2, 2, '你好', NULL, '2023-04-14 22:38:00', 1);
INSERT INTO `message` VALUES (31, 2, 2, '2', NULL, '2023-04-14 22:39:07', 1);
INSERT INTO `message` VALUES (32, 2, 14, '2', NULL, '2023-04-14 22:39:15', 1);
INSERT INTO `message` VALUES (33, 2, 2, '你好', NULL, '2023-04-14 22:44:26', 1);
INSERT INTO `message` VALUES (34, 2, 14, '🤡', NULL, '2023-04-15 17:28:04', 0);
INSERT INTO `message` VALUES (35, 2, 14, '你好', NULL, '2023-04-24 10:51:55', 0);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻图片',
  `news_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '新闻标题',
  `news_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '新闻内容',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '发布人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (1, 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/04/23/aef8b480-d1a1-47fe-a153-656d0721ebcd.jpg', '我是新闻标题1呀', '哈哈哈哈，我是新闻的内容1', 2, '2023-03-01 22:49:49');
INSERT INTO `news` VALUES (2, NULL, '我是新闻标题2呀', '哈哈哈哈，我是新闻的内容2', 2, '2023-03-03 22:50:33');
INSERT INTO `news` VALUES (5, 'https://static.kurihada.com/yunke/titos.jpg', '我是新闻标题5呀', '哈哈哈哈，我是新闻的内容5', 2, '2023-03-20 18:53:29');
INSERT INTO `news` VALUES (6, NULL, '新的标题', '新的内容', 2, '2023-03-20 22:15:54');

-- ----------------------------
-- Table structure for news_tag
-- ----------------------------
DROP TABLE IF EXISTS `news_tag`;
CREATE TABLE `news_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news_tag
-- ----------------------------
INSERT INTO `news_tag` VALUES (1, '新闻标签', '2023-03-12 22:48:25');
INSERT INTO `news_tag` VALUES (2, '新闻标签2', '2023-03-13 22:48:42');
INSERT INTO `news_tag` VALUES (3, '科技', '2023-03-20 21:31:00');
INSERT INTO `news_tag` VALUES (4, '科技1', '2023-03-20 21:33:44');
INSERT INTO `news_tag` VALUES (5, '科技2', '2023-03-20 21:51:04');
INSERT INTO `news_tag` VALUES (6, '我是标签呀', '2023-03-20 21:51:14');
INSERT INTO `news_tag` VALUES (7, '新的标签', '2023-03-20 22:15:54');

-- ----------------------------
-- Table structure for news_tag_map
-- ----------------------------
DROP TABLE IF EXISTS `news_tag_map`;
CREATE TABLE `news_tag_map`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) NULL DEFAULT NULL COMMENT '新闻ID',
  `tag_id` int(11) NULL DEFAULT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of news_tag_map
-- ----------------------------
INSERT INTO `news_tag_map` VALUES (11, 1, 1);
INSERT INTO `news_tag_map` VALUES (9, 2, 6);
INSERT INTO `news_tag_map` VALUES (10, NULL, 7);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notice_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通告内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '通告时间',
  `status` int(11) NULL DEFAULT NULL COMMENT '1表示正常，0表示关闭',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '温馨提醒：2018-07-01 云客新版本发布啦', '2023-03-17 13:38:03', 1, 2);
INSERT INTO `notice` VALUES (2, '维护通知：2018-07-01 云客系统凌晨维护', '2023-03-17 13:38:42', 1, 2);
INSERT INTO `notice` VALUES (3, '云客系统还有一个月就要与大家见面了', '2023-03-17 14:31:19', 1, 2);

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `post_cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '发表的用户ID',
  `comments` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '评论量',
  `likes` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '点赞量',
  `is_violation` tinyint(4) NULL DEFAULT NULL COMMENT '是否违规',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (16, 'dfaf', 'dfadf', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/25/2a4176c7-8663-4299-9594-546cabe57d95.jpg', 2, 0, 0, 0, '2023-03-25 10:48:50');
INSERT INTO `post` VALUES (15, 'dfadfa', 'afdfadf', '', 2, 0, 0, 0, '2023-03-25 10:41:01');
INSERT INTO `post` VALUES (14, '我是内容', '我是内容', '', 2, 0, 0, 0, '2023-03-25 10:33:44');
INSERT INTO `post` VALUES (13, 'fdsf', 'dfadf', '', 2, 0, 0, 0, '2023-03-25 10:32:42');
INSERT INTO `post` VALUES (5, '我是标题', '我当然是内容了啦啦啦啦去啦啦啦啦', '', 2, 0, 0, 0, '2023-03-09 18:25:54');
INSERT INTO `post` VALUES (6, '我是标题嗷嗷嗷1', '我是内容奥嗷嗷哦', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/09/4ec23fcf-394a-4354-b7bf-c0f340f805c6.png', 2, 0, 0, 0, '2023-03-09 22:23:41');
INSERT INTO `post` VALUES (7, '风和日丽', '嗷嗷嗷嗷', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/09/e2d5d094-3a0e-4dc8-961a-41281fc05fc4.jpg', 2, 0, 0, 0, '2023-03-09 22:26:49');
INSERT INTO `post` VALUES (8, 'mysql数据库', '我们通过这个来操作数据库', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/09/49834ac1-1e42-4bb5-976a-c58d41472e57.jpg', 2, 0, 0, 0, '2023-03-09 23:18:20');
INSERT INTO `post` VALUES (9, '哈哈哈', '哈哈哈', '', 2, 0, 0, 0, '2023-03-09 23:29:15');
INSERT INTO `post` VALUES (10, '***', '***', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/09/44cd6f41-cac4-4afb-9ef3-023bfaa01db2.jpeg', 2, 3, 0, 0, '2023-03-09 23:38:51');
INSERT INTO `post` VALUES (11, '法国公司的分公司分管', '放大发啊打发打发', '', 2, 0, 0, 0, '2023-03-10 09:04:23');
INSERT INTO `post` VALUES (12, '安抚放大放大', '打发打发发我', '', 2, 0, 0, 0, '2023-03-10 09:06:49');
INSERT INTO `post` VALUES (17, '最美的太阳', '我是内容', 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/04/24/97be2838-ef1c-4ca4-a737-f8eb2ea9060a.jpeg', 2, 1, 0, 0, '2023-04-24 10:48:57');

-- ----------------------------
-- Table structure for sensitive_library
-- ----------------------------
DROP TABLE IF EXISTS `sensitive_library`;
CREATE TABLE `sensitive_library`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sensitive_word` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '敏感词',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sensitive_library
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务标签名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, '学习', '2023-03-11 17:41:56');
INSERT INTO `tag` VALUES (2, '生活', '2023-03-11 17:42:10');
INSERT INTO `tag` VALUES (3, '工作', '2023-03-11 17:42:22');
INSERT INTO `tag` VALUES (4, '其它', '2023-03-11 17:42:33');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务标题',
  `task_desc` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务内容',
  `user_id` int(11) NOT NULL COMMENT '发布任务的用户ID',
  `is_important` tinyint(1) NOT NULL COMMENT '是否为重要事项',
  `is_starred` tinyint(1) NOT NULL COMMENT '是否加星标',
  `is_done` tinyint(1) NOT NULL COMMENT '是否完成',
  `is_trashed` tinyint(1) NOT NULL COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (42, 'yhahha', 'dfadfdf', 2, 1, 1, 0, 0, '2023-03-11 23:46:29');

-- ----------------------------
-- Table structure for task_tag
-- ----------------------------
DROP TABLE IF EXISTS `task_tag`;
CREATE TABLE `task_tag`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL COMMENT '任务id',
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = FIXED;

-- ----------------------------
-- Records of task_tag
-- ----------------------------
INSERT INTO `task_tag` VALUES (33, 42, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `gender` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `person_type` int(11) NULL DEFAULT NULL COMMENT '用户类别(1是普通用户、2是管理员、3是超级管理员)',
  `head_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
  `registry_time` date NULL DEFAULT NULL COMMENT '注册时间',
  `job_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工作标签',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人描述',
  `is_ban` tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用(1表示是，0表示否)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1', '$10$/G7pclJLtXbjnNDOtVu5.eAn2jlQx.65zUNmmZbr8U6mJKMGj78uy', '男', '634521@qq.com', 1, NULL, '2023-02-02', '18482079914', '2023-03-01', NULL, NULL, 1);
INSERT INTO `user` VALUES (2, 'Titos', '$2a$10$m7TFGz5n3BKjSibKKnbQ4.vKWHY8PQaOKM/TmknKvFpVsEB9fWJCu', '男', '634522023@qq.com', 3, 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/08/f8f19c15-01ed-47cd-95d6-47193528d1d3.jpg', '2021-12-07', '18482079914', '2023-03-08', '学生', '世界那么大，我向去看看', 0);
INSERT INTO `user` VALUES (3, 'zhangsan123', '$2a$10$m7TFGz5n3BKjSibKKnbQ4.vKWHY8PQaOKM/TmknKvFpVsEB9fWJCu', '女', '23424234@gmail.com', 2, 'https://tianyufighter.oss-cn-chengdu.aliyuncs.com/images/2023/03/08/f8f19c15-01ed-47cd-95d6-47193528d1d3.jpg', '2023-03-09', '2341324124', '2023-03-09', '教师', '为理想而生', 0);
INSERT INTO `user` VALUES (6, 'lisi', '$2a$10$AXaUt3T45WPmebg63sjS2.l/azrT.iS6DW/fiXBcB.lqJFtrZQIeO', '男', '234223@qq.com', 1, NULL, NULL, NULL, '2023-03-15', '后端程序员', NULL, 0);
INSERT INTO `user` VALUES (7, 'wangwu', '$2a$10$oBdCrKDOZVkxu9gs5y22PORu1FyD/rCmHBgSANq0Db1suZzgRlCtK', '男', '63452202dfd3@qq.com', 1, NULL, NULL, NULL, '2023-03-16', '学生', NULL, 0);
INSERT INTO `user` VALUES (11, 'wangba', '$2a$10$Pmi8JaYd4JBeO5i.Bw6Exu3zbMe0VnV1Pq.giMxkZyI3YIs5fxtt6', '男', '18281665193@df.com', 2, NULL, NULL, NULL, '2023-03-16', '学生', NULL, 0);
INSERT INTO `user` VALUES (14, 'zhangsan', '$2a$10$tq5KGzolkbd9s6JUc0Osmu5vrZ3Jj7fgyIBbR5vWp2uPx2n9BU8ye', NULL, '6345220231@qq.com', 2, NULL, NULL, NULL, '2023-04-14', NULL, NULL, 0);
INSERT INTO `user` VALUES (15, '李治强', '$2a$10$1Ru/7CoscY4MyPKTMIo2OupZauDzImKpB436JEiykUEU4FzeX8Ple', NULL, '1945534512@qq.com', 1, NULL, NULL, NULL, '2023-04-15', NULL, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
