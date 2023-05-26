/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 1.14.58.8:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 26/05/2023 23:52:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (1, 'conversation-dev.yaml', 'DEV_GROUP', 'spring:\n  redis:\n    database: 0\n    host:: 1.14.58.8\n    port: 6379\n    password: root\n    lettuce:\n      pool:\n        max-active: 8\n        max-wait: -1\n        max-idle: 10\n        min-idle: 5\n      shutdown-timeout: 10000ms', '8141ccdb1f853686c34b71f8e458bf1c', '2023-03-15 10:18:12', '2023-04-14 06:49:02', 'nacos', '223.104.220.170', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '聊天模块的开发环境配置信息', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (2, 'common.yaml', 'DEV_GROUP', 'spring:\n  jackson:\n    date-format: yyyy-MM-dd hh:mm:ss\n    time-zone: GMT+8\n  zipkin:\n    # zipkin server的请求地址\n    base-url: http://39.108.117.49:9411/\n    # 让nacos把它当成一个URL，而不要当做服务名\n    discovery-client-enabled: false\n  sleuth:\n    sampler:\n      # 日志数据采样百分比，默认0.1(10%)，这里为了测试设置成了100%，生产环境只需要0.1即可\n      probability: 0.5\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://1.14.58.8:3306/yunke?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&&useSSL=false\n    username: root\n    password: root\n  cloud:\n    sentinel:\n      transport:\n        # 指定sentinel-dashboard控制台的连接地址\n        dashboard: 162.14.77.43:8080\n        # 指定微服务与sentinel-dashboard控制台的连接端口\n        # 默认8719端口，假如被占用会自动从8719开始依次+1扫描\n        # 直至找到未被占用的端口\n        port: 8719\n    # 基于nacos配置中心进行规则持久化(利用nacos配置中心持久化流控规则)\n      datasource:\n        # 设置Sentinel Nacos数据源配置；其中flow是数据源名，可以自行随意修改\n        # Nacos数据源地址(需要启动一台Nacos Server)\n        # nacos地址\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: flow # 还可以是：degrade、authority、param-flow\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-degrade-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: degrade # 还可以是：degrade、authority、param-flow\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-param-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: param-flow # 还可以是：degrade、authority、param-flow\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-system-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: system # 还可以是：degrade、authority、param-flow\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-authority-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: authority # 还可以是：degrade、authority、param-flow\n      # true表示饥饿加载\n      eager: true\nfeign:\n  sentinel:\n    # true开启sentinel对feign的支持，false则关闭\n    enabled: true\nYK:\n  sys-conf:\n    enable-mail-register: true\n    front-end-url: 162.14.77.43:8010\n    front-host-url: 39.108.117.49:80\n    mail-sender: titos_yunke@qq.com\n  token:\n    secretKey: YUNKE\n', '69410fcb9c68a46601174871415f03cd', '2023-03-15 10:18:50', '2023-05-26 06:32:41', 'nacos', '171.210.50.221', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '共享的配置文件', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (3, 'gateway-dev.yaml', 'DEV_GROUP', 'spring:\n  # redis配置\n  redis:\n    host: 1.14.58.8\n    port: 6379\n    password: root\n    timeout: 10000\n  cloud:\n    sentinel:\n      datasource:\n        gw-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-gw-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: gw-flow # 还可以是：degrade、authority、param-flow\n        gw-api-group:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-gw-api-group-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: gw-api-group # 还可以是：degrade、authority、param-flow\n    gateway:\n      httpclient:\n        connect-timeout: 10000\n      routes: # 网关路由配置\n        - id: conversation\n          uri: lb://conversation\n          predicates:\n            - Path=/conversation/**\n        - id: normal\n          uri: lb://normal\n          predicates:\n            - Path=/common/**\n        - id: personManagement\n          uri: lb://personManagement\n          predicates:\n            - Path=/personManagement/**\n        - id: sharePlatform\n          uri: lb://sharePlatform\n          predicates:\n            - Path=/sharePlatform/**\n        - id: technicalArchive\n          uri: lb://technicalArchive\n          predicates:\n            - Path=/technicalArchive/**\n        - id: userService\n          uri: lb://userService\n          predicates:\n            - Path=/user/**\n        - id: admin\n          uri: lb://admin\n          predicates:\n            - Path=/admin/**,/druid/**    ', '3661b6657dbaa66d3bb0c6d725c96882', '2023-03-15 10:19:35', '2023-04-26 14:05:56', 'nacos', '223.104.219.231', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '网关的开发环境配置信息', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (4, 'normal-dev.yaml', 'DEV_GROUP', 'file:\n  fdfs:\n    groupName: group1\n    soTimeout: 1500 # 上传超时时间\n    connectTimeout: 600 # 连接超时时间\n    thumbImage: # 缩略图生成参数\n      width: 150\n      height: 150\n    trackerList: # TrackerList参数，支持多个\n      - 124.223.38.131:22122\n  aliyun:\n    endpoint: oss-cn-chengdu.aliyuncs.com # oss的地域节点\n    bucketName: tianyufighter # oss的Bucket名称（相当于磁盘）\n    accessKeyId: LTAI5tB8GzEiiJQdH8Q33SUm # oss的AccessKeyId(相当于阿里云的账号)\n    accessKeySecret: EZ5Vtq4X4QkrJyuRk9ogGZaXxenOJc # oss的AccessKeySecret(相当于阿里云的密码)\n    fileHost: images # oss的目标文件夹\n  method: 2 # 存储文件的方式（1，表示本地存储；2表示阿里云存储；3表示fastdfs存储）\nspring:\n  # redis配置\n  redis:\n    host: 1.14.58.8\n    port: 6379\n    password: root\n    timeout: 10000', '832026d0f195fb7103ee182656874ef6', '2023-03-15 10:20:50', '2023-04-13 12:33:16', 'nacos', '171.210.137.198', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '常用的功能模块的开发环境配置信息', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (5, 'personManagement-dev.yaml', 'DEV_GROUP', 'spring:\n  redis:\n    host: 1.14.58.8\n    port: 6379\n    password: root\n  mail:\n    host: smtp.qq.com\n    port: 465\n    username: titos_yunke@qq.com\n    password: xbayvkiinblkdcee\n    default-encoding: UTF-8\n    properties:\n      mail:\n        smtp:\n          socketFactory:\n            class: javax.net.ssl.SSLSocketFactory\n\nmybatis:\n  mapper-locations: classpath:mapper/*.xml\n  type-aliases-package: com.titos.personalmanagement\nYK:\n  sys-conf:\n    # enable-mail-register: true\n    front-end-url: 162.14.77.43:8010\n    front-host-url: 39.108.117.49:80\n    mail-sender: titos_yunke@qq.com\n  token:\n    secretKey: YUNKE\n', 'e8cc45bad7c7cea5ba926c34e005991e', '2023-03-15 10:21:51', '2023-04-15 05:18:47', 'nacos', '223.104.220.170', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '个人信息管理模块的开发环境配置', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (6, 'sharePlatform-dev.yaml', 'DEV_GROUP', 'spring:\n  # redis配置\n  redis:\n    host: 1.14.58.8\n    port: 6379\n    password: root\n    timeout: 10000', 'd0f9f8ee4002bab0ab39b61ce38c057d', '2023-03-15 10:22:42', '2023-03-18 13:13:24', 'nacos', '171.210.39.51', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '分享模块的开发环境配置信息', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (7, 'technicalArchive-dev.yaml', 'DEV_GROUP', '# spring:\n#   redis:\n#     database: 0\n#     host: 1.14.58.8\n#     port: 6379\n#     password: root\n#     lettuce:\n#       pool:\n#         max-active: 8\n#         max-wait: -1\n#         max-idle: 10\n#         min-idle: 5\n#       shutdown-timeout: 100ms\n\n# # mybatis 配置\n# mybatis:\n#   mapper-locations:\n#     - classpath:mappers/*.xml\n\n# # 开启mybatis 的日志记录\n# logging:\n#   level:\n#     com:\n#       titos:\n#         conversation:\n#           dao: debug\n', '81037690be6dae1efd667c6db84a0125', '2023-03-15 10:24:22', '2023-03-18 13:13:09', 'nacos', '171.210.39.51', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '博客模块的开发环境配置信息', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (18, 'userService-dev.yaml', 'DEV_GROUP', 'df', 'eff7d5dba32b4da32d9a67a519434d3f', '2023-03-15 10:32:41', '2023-03-15 10:32:41', NULL, '118.115.59.90', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '用户服务的开发环境配置信息', NULL, NULL, 'yaml', NULL);
INSERT INTO `config_info` VALUES (20, 'admin-dev.yaml', 'DEV_GROUP', 'spring:\n  # redis配置\n  redis:\n    host: 1.14.58.8\n    port: 6379\n    password: root\n    timeout: 10000', 'd0f9f8ee4002bab0ab39b61ce38c057d', '2023-03-15 10:59:49', '2023-04-15 00:43:43', 'nacos', '223.104.220.170', 'YUNKE', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', '后台管理的开发环境配置信息', '', '', 'yaml', '');
INSERT INTO `config_info` VALUES (81, 'personManagement-system-rules', 'SENTINEL_GROUP', '[]', 'd751713988987e9331980363e24189ce', '2023-05-26 02:01:06', '2023-05-26 02:04:01', NULL, '162.14.77.43', '', '', NULL, NULL, NULL, 'text', NULL);

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `datum_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '增加租户字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_aggr
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_beta' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_beta
-- ----------------------------

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id` ASC, `group_id` ASC, `tenant_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_info_tag' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_info_tag
-- ----------------------------

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id` ASC, `tag_name` ASC, `tag_type` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'config_tag_relation' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of config_tags_relation
-- ----------------------------

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of group_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint UNSIGNED NOT NULL,
  `nid` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `data_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `src_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create` ASC) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified` ASC) USING BTREE,
  INDEX `idx_did`(`data_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 137 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '多租户改造' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (2, 127, 'common.yaml', 'DEV_GROUP', 'YUNKE', 'spring:\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://1.14.58.8:3306/yunke?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&&useSSL=false\n    username: root\n    password: root\n  cloud:\n    sentinel:\n      transport:\n        # 指定sentinel-dashboard控制台的连接地址\n        dashboard: 162.14.77.43:8080\n        # 指定微服务与sentinel-dashboard控制台的连接端口\n        # 默认8719端口，假如被占用会自动从8719开始依次+1扫描\n        # 直至找到未被占用的端口\n        port: 8719\n    # 基于nacos配置中心进行规则持久化(利用nacos配置中心持久化流控规则)\n      datasource:\n        # 设置Sentinel Nacos数据源配置；其中flow是数据源名，可以自行随意修改\n        # Nacos数据源地址(需要启动一台Nacos Server)\n        # nacos地址\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: flow # 还可以是：degrade、authority、param-flow\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-degrade-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: degrade # 还可以是：degrade、authority、param-flow\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-param-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: param-flow # 还可以是：degrade、authority、param-flow\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-system-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: system # 还可以是：degrade、authority、param-flow\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-authority-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: authority # 还可以是：degrade、authority、param-flow\n      # true表示饥饿加载\n      eager: true\nfeign:\n  sentinel:\n    # true开启sentinel对feign的支持，false则关闭\n    enabled: true\nYK:\n  sys-conf:\n    enable-mail-register: true\n    front-end-url: 162.14.77.43:8010\n    front-host-url: 39.108.117.49:80\n    mail-sender: titos_yunke@qq.com\n  token:\n    secretKey: YUNKE\n', '93820834a4469237bea5237565e3981a', '2023-05-26 00:12:22', '2023-05-25 16:12:22', 'nacos', '171.210.50.221', 'U', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f');
INSERT INTO `his_config_info` VALUES (2, 128, 'common.yaml', 'DEV_GROUP', 'YUNKE', 'spring:\n  zipkin:\n    base-url: http://localhost:9411\n    sleuth:\n      sampler:\n        # 采样率介于0到1之间，1则表示全部采集\n        probability: 1\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://1.14.58.8:3306/yunke?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&&useSSL=false\n    username: root\n    password: root\n  cloud:\n    sentinel:\n      transport:\n        # 指定sentinel-dashboard控制台的连接地址\n        dashboard: 162.14.77.43:8080\n        # 指定微服务与sentinel-dashboard控制台的连接端口\n        # 默认8719端口，假如被占用会自动从8719开始依次+1扫描\n        # 直至找到未被占用的端口\n        port: 8719\n    # 基于nacos配置中心进行规则持久化(利用nacos配置中心持久化流控规则)\n      datasource:\n        # 设置Sentinel Nacos数据源配置；其中flow是数据源名，可以自行随意修改\n        # Nacos数据源地址(需要启动一台Nacos Server)\n        # nacos地址\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: flow # 还可以是：degrade、authority、param-flow\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-degrade-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: degrade # 还可以是：degrade、authority、param-flow\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-param-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: param-flow # 还可以是：degrade、authority、param-flow\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-system-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: system # 还可以是：degrade、authority、param-flow\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-authority-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: authority # 还可以是：degrade、authority、param-flow\n      # true表示饥饿加载\n      eager: true\nfeign:\n  sentinel:\n    # true开启sentinel对feign的支持，false则关闭\n    enabled: true\nYK:\n  sys-conf:\n    enable-mail-register: true\n    front-end-url: 162.14.77.43:8010\n    front-host-url: 39.108.117.49:80\n    mail-sender: titos_yunke@qq.com\n  token:\n    secretKey: YUNKE\n', '0bac229760242381f4c668e4f298daf5', '2023-05-26 00:13:21', '2023-05-25 16:13:21', 'nacos', '171.210.50.221', 'U', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f');
INSERT INTO `his_config_info` VALUES (2, 129, 'common.yaml', 'DEV_GROUP', 'YUNKE', 'spring:\n  zipkin:\n    base-url: http://localhost:9411\n  sleuth:\n    sampler:\n      # 采样率介于0到1之间，1则表示全部采集\n      probability: 1\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://1.14.58.8:3306/yunke?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&&useSSL=false\n    username: root\n    password: root\n  cloud:\n    sentinel:\n      transport:\n        # 指定sentinel-dashboard控制台的连接地址\n        dashboard: 162.14.77.43:8080\n        # 指定微服务与sentinel-dashboard控制台的连接端口\n        # 默认8719端口，假如被占用会自动从8719开始依次+1扫描\n        # 直至找到未被占用的端口\n        port: 8719\n    # 基于nacos配置中心进行规则持久化(利用nacos配置中心持久化流控规则)\n      datasource:\n        # 设置Sentinel Nacos数据源配置；其中flow是数据源名，可以自行随意修改\n        # Nacos数据源地址(需要启动一台Nacos Server)\n        # nacos地址\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: flow # 还可以是：degrade、authority、param-flow\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-degrade-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: degrade # 还可以是：degrade、authority、param-flow\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-param-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: param-flow # 还可以是：degrade、authority、param-flow\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-system-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: system # 还可以是：degrade、authority、param-flow\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-authority-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: authority # 还可以是：degrade、authority、param-flow\n      # true表示饥饿加载\n      eager: true\nfeign:\n  sentinel:\n    # true开启sentinel对feign的支持，false则关闭\n    enabled: true\nYK:\n  sys-conf:\n    enable-mail-register: true\n    front-end-url: 162.14.77.43:8010\n    front-host-url: 39.108.117.49:80\n    mail-sender: titos_yunke@qq.com\n  token:\n    secretKey: YUNKE\n', 'aaf86dde115bf917d24e454a7df5a84d', '2023-05-26 00:16:37', '2023-05-25 16:16:37', 'nacos', '171.210.50.221', 'U', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f');
INSERT INTO `his_config_info` VALUES (2, 130, 'common.yaml', 'DEV_GROUP', 'YUNKE', 'spring:\n  zipkin:\n    base-url: http://39.108.117.49:9411/\n  sleuth:\n    sampler:\n      # 采样率介于0到1之间，1则表示全部采集\n      probability: 1\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://1.14.58.8:3306/yunke?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&&useSSL=false\n    username: root\n    password: root\n  cloud:\n    sentinel:\n      transport:\n        # 指定sentinel-dashboard控制台的连接地址\n        dashboard: 162.14.77.43:8080\n        # 指定微服务与sentinel-dashboard控制台的连接端口\n        # 默认8719端口，假如被占用会自动从8719开始依次+1扫描\n        # 直至找到未被占用的端口\n        port: 8719\n    # 基于nacos配置中心进行规则持久化(利用nacos配置中心持久化流控规则)\n      datasource:\n        # 设置Sentinel Nacos数据源配置；其中flow是数据源名，可以自行随意修改\n        # Nacos数据源地址(需要启动一台Nacos Server)\n        # nacos地址\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: flow # 还可以是：degrade、authority、param-flow\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-degrade-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: degrade # 还可以是：degrade、authority、param-flow\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-param-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: param-flow # 还可以是：degrade、authority、param-flow\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-system-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: system # 还可以是：degrade、authority、param-flow\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-authority-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: authority # 还可以是：degrade、authority、param-flow\n      # true表示饥饿加载\n      eager: true\nfeign:\n  sentinel:\n    # true开启sentinel对feign的支持，false则关闭\n    enabled: true\nYK:\n  sys-conf:\n    enable-mail-register: true\n    front-end-url: 162.14.77.43:8010\n    front-host-url: 39.108.117.49:80\n    mail-sender: titos_yunke@qq.com\n  token:\n    secretKey: YUNKE\n', '03e5da2ffa8b273111182864df0aa7fe', '2023-05-26 00:30:56', '2023-05-25 16:30:57', 'nacos', '171.210.50.221', 'U', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f');
INSERT INTO `his_config_info` VALUES (2, 131, 'common.yaml', 'DEV_GROUP', 'YUNKE', 'spring:\n  zipkin:\n    base-url: http://39.108.117.49:9411/\n  sleuth:\n    sampler:\n      # 采样率介于0到1之间，1则表示全部采集\n      probability: 0.5\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://1.14.58.8:3306/yunke?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&&useSSL=false\n    username: root\n    password: root\n  cloud:\n    sentinel:\n      transport:\n        # 指定sentinel-dashboard控制台的连接地址\n        dashboard: 162.14.77.43:8080\n        # 指定微服务与sentinel-dashboard控制台的连接端口\n        # 默认8719端口，假如被占用会自动从8719开始依次+1扫描\n        # 直至找到未被占用的端口\n        port: 8719\n    # 基于nacos配置中心进行规则持久化(利用nacos配置中心持久化流控规则)\n      datasource:\n        # 设置Sentinel Nacos数据源配置；其中flow是数据源名，可以自行随意修改\n        # Nacos数据源地址(需要启动一台Nacos Server)\n        # nacos地址\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: flow # 还可以是：degrade、authority、param-flow\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-degrade-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: degrade # 还可以是：degrade、authority、param-flow\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-param-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: param-flow # 还可以是：degrade、authority、param-flow\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-system-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: system # 还可以是：degrade、authority、param-flow\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-authority-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: authority # 还可以是：degrade、authority、param-flow\n      # true表示饥饿加载\n      eager: true\nfeign:\n  sentinel:\n    # true开启sentinel对feign的支持，false则关闭\n    enabled: true\nYK:\n  sys-conf:\n    enable-mail-register: true\n    front-end-url: 162.14.77.43:8010\n    front-host-url: 39.108.117.49:80\n    mail-sender: titos_yunke@qq.com\n  token:\n    secretKey: YUNKE\n', 'c783a7f799195bebe886f9587364812c', '2023-05-26 09:32:49', '2023-05-26 01:32:49', 'nacos', '171.210.50.221', 'U', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f');
INSERT INTO `his_config_info` VALUES (0, 132, 'personManagement-system-rules', 'SENTINEL_GROUP', '', '[{\"app\":\"personManagement\",\"avgRt\":-1,\"gmtCreate\":1685066465841,\"gmtModified\":1685066465841,\"highestCpuUsage\":-1.0,\"highestSystemLoad\":-1.0,\"id\":1,\"ip\":\"10.0.0.2\",\"maxThread\":-1,\"port\":8725,\"qps\":1.0}]', '626114fe4a9b9e6d9c02cfd6337c3fd6', '2023-05-26 10:01:06', '2023-05-26 02:01:06', NULL, '162.14.77.43', 'I', '');
INSERT INTO `his_config_info` VALUES (81, 133, 'personManagement-system-rules', 'SENTINEL_GROUP', '', '[{\"app\":\"personManagement\",\"avgRt\":-1,\"gmtCreate\":1685066465841,\"gmtModified\":1685066465841,\"highestCpuUsage\":-1.0,\"highestSystemLoad\":-1.0,\"id\":1,\"ip\":\"10.0.0.2\",\"maxThread\":-1,\"port\":8725,\"qps\":1.0}]', '626114fe4a9b9e6d9c02cfd6337c3fd6', '2023-05-26 10:01:54', '2023-05-26 02:01:55', NULL, '162.14.77.43', 'U', '');
INSERT INTO `his_config_info` VALUES (81, 134, 'personManagement-system-rules', 'SENTINEL_GROUP', '', '[]', 'd751713988987e9331980363e24189ce', '2023-05-26 10:02:18', '2023-05-26 02:02:19', NULL, '162.14.77.43', 'U', '');
INSERT INTO `his_config_info` VALUES (81, 135, 'personManagement-system-rules', 'SENTINEL_GROUP', '', '[{\"app\":\"personManagement\",\"avgRt\":-1,\"gmtCreate\":1685066538550,\"gmtModified\":1685066538550,\"highestCpuUsage\":-1.0,\"highestSystemLoad\":-1.0,\"id\":2,\"ip\":\"10.0.0.2\",\"maxThread\":-1,\"port\":8725,\"qps\":1.0}]', '991ccd59ff651b1b63e747486dd28f86', '2023-05-26 10:04:01', '2023-05-26 02:04:01', NULL, '162.14.77.43', 'U', '');
INSERT INTO `his_config_info` VALUES (2, 136, 'common.yaml', 'DEV_GROUP', 'YUNKE', 'spring:\n  zipkin:\n    # zipkin server的请求地址\n    base-url: http://39.108.117.49:9411/\n    # 让nacos把它当成一个URL，而不要当做服务名\n    discovery-client-enabled: false\n  sleuth:\n    sampler:\n      # 日志数据采样百分比，默认0.1(10%)，这里为了测试设置成了100%，生产环境只需要0.1即可\n      probability: 0.5\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driver-class-name: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://1.14.58.8:3306/yunke?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&&useSSL=false\n    username: root\n    password: root\n  cloud:\n    sentinel:\n      transport:\n        # 指定sentinel-dashboard控制台的连接地址\n        dashboard: 162.14.77.43:8080\n        # 指定微服务与sentinel-dashboard控制台的连接端口\n        # 默认8719端口，假如被占用会自动从8719开始依次+1扫描\n        # 直至找到未被占用的端口\n        port: 8719\n    # 基于nacos配置中心进行规则持久化(利用nacos配置中心持久化流控规则)\n      datasource:\n        # 设置Sentinel Nacos数据源配置；其中flow是数据源名，可以自行随意修改\n        # Nacos数据源地址(需要启动一台Nacos Server)\n        # nacos地址\n        flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: flow # 还可以是：degrade、authority、param-flow\n        degrade:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-degrade-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: degrade # 还可以是：degrade、authority、param-flow\n        param-flow:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-param-flow-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: param-flow # 还可以是：degrade、authority、param-flow\n        system:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-system-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: system # 还可以是：degrade、authority、param-flow\n        authority:\n          nacos:\n            server-addr: ${spring.cloud.nacos.server-addr}\n            data-id: ${spring.application.name}-authority-rules\n            group-id: SENTINEL_GROUP\n            # 数据类型\n            data-type: json\n            # 规则类型\n            rule-type: authority # 还可以是：degrade、authority、param-flow\n      # true表示饥饿加载\n      eager: true\nfeign:\n  sentinel:\n    # true开启sentinel对feign的支持，false则关闭\n    enabled: true\nYK:\n  sys-conf:\n    enable-mail-register: true\n    front-end-url: 162.14.77.43:8010\n    front-host-url: 39.108.117.49:80\n    mail-sender: titos_yunke@qq.com\n  token:\n    secretKey: YUNKE\n', '58fdd16d1b0cd7c976c3d04d134cc172', '2023-05-26 14:32:40', '2023-05-26 06:32:41', 'nacos', '171.210.50.221', 'U', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role` ASC, `resource` ASC, `action` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username` ASC, `role` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '配额，0表示使用默认值',
  `usage` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '使用量',
  `max_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '聚合子配置最大个数',
  `max_aggr_size` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大变更历史数量',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '租户容量信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_capacity
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp` ASC, `tenant_id` ASC) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'tenant_info' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tenant_info
-- ----------------------------
INSERT INTO `tenant_info` VALUES (1, '1', 'f30cdd99-f7f3-4c24-adc4-1c99101b544f', 'dev', 'dev命名空间', 'nacos', 1678875294899, 1678875401987);

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('myworld6578', '$2a$10$8UB/d7Y6ZDEsJe0Xg/eXBecJaznR/LBGiELm3toGClRolmFdTDRAu', 1);
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
