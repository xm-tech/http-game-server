DROP TABLE IF EXISTS `t_cfg_activity_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_cfg_activity_group` (
  `groupId` int(11) NOT NULL,
  `name` text NOT NULL,
  `intro` text NOT NULL,
  `startTime` int(11) NOT NULL,
  `endTime` int(11) NOT NULL,
  `lastTime` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_cfg_activity_task`
--

DROP TABLE IF EXISTS `t_cfg_activity_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_cfg_activity_task` (
  `aid` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  `preAid` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `intro` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `rewards` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `tasks` text COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`aid`,`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_apple`
--

DROP TABLE IF EXISTS `t_log_apple`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_apple` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_crystal`
--

DROP TABLE IF EXISTS `t_log_crystal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_crystal` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_diamond`
--

DROP TABLE IF EXISTS `t_log_diamond`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_diamond` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_draw`
--

DROP TABLE IF EXISTS `t_log_draw`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_draw` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_equip`
--

DROP TABLE IF EXISTS `t_log_equip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_equip` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_exp`
--

DROP TABLE IF EXISTS `t_log_exp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_exp` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_face`
--

DROP TABLE IF EXISTS `t_log_face`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_face` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_facefrag`
--

DROP TABLE IF EXISTS `t_log_facefrag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_facefrag` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_furniture`
--

DROP TABLE IF EXISTS `t_log_furniture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_furniture` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_gold`
--

DROP TABLE IF EXISTS `t_log_gold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_gold` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_item`
--

DROP TABLE IF EXISTS `t_log_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_item` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_pay`
--

DROP TABLE IF EXISTS `t_log_pay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) DEFAULT NULL,
  `oid` varchar(255) DEFAULT NULL,
  `ch` varchar(64) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `adddiamond` int(11) DEFAULT NULL,
  `senddiamond` int(11) DEFAULT NULL,
  `paytime` int(11) DEFAULT NULL,
  `productid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_log_stamp`
--

DROP TABLE IF EXISTS `t_log_stamp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_log_stamp` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `itemid` int(2) DEFAULT NULL COMMENT '物品id. 钻: -1,金币: -2,经验: -3,苹果: -4,水晶: -5',
  `num` int(11) DEFAULT NULL COMMENT '负数为消耗,正数为新增',
  `time` int(11) DEFAULT NULL,
  `source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_mail`
--

DROP TABLE IF EXISTS `t_mail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_mail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '邮件ID',
  `playerid` bigint(20) DEFAULT NULL COMMENT '收件人ID',
  `senderid` bigint(20) DEFAULT NULL COMMENT '发送者ID',
  `sendername` text COMMENT '发送者名字',
  `title` text COMMENT '标题',
  `detail` text COMMENT '邮件内容',
  `status` tinyint(4) DEFAULT NULL COMMENT '邮件状态',
  `attached` text COMMENT '邮件内容',
  `createtime` int(11) DEFAULT NULL COMMENT '创建时间',
  `mailtype` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`) USING BTREE,
  KEY `senderid` (`senderid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_passport`
--

DROP TABLE IF EXISTS `t_passport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_passport` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passPort` text NOT NULL,
  `serverId` text NOT NULL,
  `pid` bigint(20) NOT NULL,
  `isBinding` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_player`
--

DROP TABLE IF EXISTS `t_player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `logo` text COMMENT '头像',
  `name` varchar(20) DEFAULT NULL COMMENT '名称',
  `level` tinyint(4) DEFAULT '0' COMMENT '等级',
  `head` tinyint(2) DEFAULT '0' COMMENT '初始头像',
  `exp` int(11) DEFAULT '0' COMMENT '经验',
  `gold` bigint(20) DEFAULT '0' COMMENT '金币',
  `diamond` int(11) DEFAULT '0' COMMENT '钻石',
  `health` tinyint(4) DEFAULT '0' COMMENT '体力',
  `charm` smallint(6) DEFAULT '0' COMMENT '魅力',
  `totalpay` int(11) DEFAULT '0' COMMENT '总充值',
  `viplevel` tinyint(4) DEFAULT '0' COMMENT 'vip等级',
  `vipexp` mediumint(9) DEFAULT '0' COMMENT 'vip积分',
  `regtime` int(11) DEFAULT '0' COMMENT '注册时间',
  `logintime` int(11) DEFAULT '0' COMMENT '登陆时间',
  `shops` text COMMENT '店铺信息',
  `equips` text COMMENT '服饰',
  `equipscapacity` int(11) DEFAULT NULL,
  `items` text,
  `furnitures` text,
  `decoratefurnitures` text COMMENT '已装修家具,etc: {"1000":4} 表示 服饰1000, 4件',
  `settletime` int(11) DEFAULT '0' COMMENT '上次结算时间戳',
  `settleperiod` int(11) DEFAULT '0' COMMENT '结算周期',
  `logistics` text COMMENT '物流',
  `faces` text COMMENT '妆容',
  `facefrags` text COMMENT '妆容碎片',
  `gotequips` text COMMENT '获得过的服饰id集合',
  `dressroom` varchar(255) DEFAULT '{"level":1,"equips":[]}' COMMENT '试衣间',
  `windows` varchar(255) DEFAULT '' COMMENT '橱窗',
  `garden` varchar(50) DEFAULT NULL,
  `apple` int(11) DEFAULT '0' COMMENT '金苹果数量',
  `draws` text,
  `flowerpots` text,
  `factorys` text COMMENT '工厂集',
  `factoryequips` text COMMENT '工厂可定制服饰集',
  `cashregisters` text COMMENT '收银台列表',
  `employees` text COMMENT '当前雇员列表',
  `renamechance` tinyint(1) DEFAULT '1' COMMENT '第一次改名免费',
  `checkin` text COMMENT '签到状态',
  `friendlist` text,
  `friendrequestlist` text,
  `pvp` text,
  `msgreadtime` int(11) DEFAULT '0' COMMENT '读取到的最后1条聊天消息的时间戳',
  `npcfriendlist` text,
  `stamp` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_player_count_tag`
--

DROP TABLE IF EXISTS `t_player_count_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_player_count_tag` (
  `player_id` bigint(20) NOT NULL,
  `tag_id` int(11) NOT NULL,
  `time` int(11) NOT NULL,
  `count` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_player_time_tag`
--

DROP TABLE IF EXISTS `t_player_time_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_player_time_tag` (
  `player_id` bigint(20) NOT NULL,
  `tag_id` int(11) NOT NULL,
  `time` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_quest`
--

DROP TABLE IF EXISTS `t_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_quest` (
  `pid` bigint(20) NOT NULL,
  `mainline` text NOT NULL,
  `activeValue` text NOT NULL,
  `customer` text NOT NULL,
  `npc` text NOT NULL,
  `sale` text NOT NULL,
  `activity` text NOT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_server`
--

DROP TABLE IF EXISTS `t_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_server` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-08-10 16:37:59
