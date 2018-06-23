SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `t_blueprint`
-- ----------------------------
DROP TABLE IF EXISTS `t_blueprint`;
CREATE TABLE `t_blueprint` (
  `id` int(11) DEFAULT NULL COMMENT '试衣间等级',
  `name` char(255) DEFAULT '0' COMMENT '升级需要的金币',
  `goldsellprice` int(11) DEFAULT NULL COMMENT '店铺销售速度提升加成, etc: 0.3表示店铺销售速度提升30%',
  `desc` char(255) DEFAULT NULL COMMENT '升级需要的主角等级',
  `equip_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图纸表';
-- ----------------------------
-- Records of t_blueprint
-- ----------------------------
-- ----------------------------
-- Table structure for `t_cashregister_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_cashregister_level`;
CREATE TABLE `t_cashregister_level` (
  `id` int(11) NOT NULL DEFAULT '0',
  `level` int(11) DEFAULT '0' COMMENT '当前等级',
  `decrsecond` int(11) DEFAULT '0' COMMENT '该等级减少的秒数',
  `need_level` int(11) DEFAULT '0' COMMENT '升级需要的主角等级, 为0表示已最高级',
  `gold` int(11) DEFAULT '0' COMMENT '升级消耗的金币',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收银台升级表';
-- ----------------------------
-- Records of t_cashregister_level
-- ----------------------------
-- ----------------------------
-- Table structure for `t_dressroom_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_dressroom_level`;
CREATE TABLE `t_dressroom_level` (
  `level` int(11) DEFAULT NULL COMMENT '试衣间等级',
  `gold` int(11) DEFAULT '0' COMMENT '升级需要的金币',
  `buff` varchar(11) DEFAULT NULL COMMENT '店铺销售速度提升加成, etc: 0.3表示店铺销售速度提升30%',
  `need_level` int(11) DEFAULT NULL COMMENT '升级需要的主角等级'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试衣间升级表';
-- ----------------------------
-- Records of t_dressroom_level
-- ----------------------------
-- ----------------------------
-- Table structure for `t_equip`
-- ----------------------------
DROP TABLE IF EXISTS `t_equip`;
CREATE TABLE `t_equip` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '装备id',
  `type` int(11) DEFAULT NULL COMMENT '服装类型id',
  `res` char(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL COMMENT '装备名称',
  `quality` int(11) DEFAULT NULL COMMENT '品质等级',
  `goldbuyprice` int(11) DEFAULT NULL COMMENT '金币购买价格',
  `diamondbuyprice` int(11) DEFAULT NULL COMMENT '钻石购买价格',
  `goldsellprice` int(11) DEFAULT NULL COMMENT '金币出售价格',
  `sell_exp` int(11) DEFAULT '0' COMMENT '卖出可获得的主角经验',
  `score` int(11) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL COMMENT '物品描述',
  `overlapnum` int(11) DEFAULT NULL COMMENT '叠加数量',
  `styleid` int(11) DEFAULT NULL COMMENT '服装风格id 1:森女,3:英伦,4:复古,5:白领,6:嘻哈,7:哥特,8:摇滚,9:波西米亚,10:休闲运动,11:礼服,12:小清新',
  `colorid` int(11) DEFAULT NULL COMMENT '服装颜色id',
  `affixids` varchar(255) DEFAULT NULL COMMENT '词缀集',
  `unitid` int(11) DEFAULT NULL COMMENT '所属套装id',
  `gettips` varchar(255) DEFAULT NULL COMMENT '获取提示',
  `rarity` int(11) DEFAULT NULL COMMENT '稀有度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服装表';
-- ----------------------------
-- Records of t_equip
-- ----------------------------
-- ----------------------------
-- Table structure for `t_equip_affix`
-- ----------------------------
DROP TABLE IF EXISTS `t_equip_affix`;
CREATE TABLE `t_equip_affix` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '词缀id',
  `name` varchar(255) DEFAULT NULL COMMENT '词缀名称',
  `color` char(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服装词缀表';
-- ----------------------------
-- Records of t_equip_affix
-- ----------------------------
-- ----------------------------
-- Table structure for `t_equip_unit`
-- ----------------------------
DROP TABLE IF EXISTS `t_equip_unit`;
CREATE TABLE `t_equip_unit` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '套装id',
  `name` varchar(255) DEFAULT NULL COMMENT '套装名称',
  `equips` varchar(255) DEFAULT NULL COMMENT '套装组成',
  `icon` varchar(11) DEFAULT NULL COMMENT '展示图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='套装表';
-- ----------------------------
-- Records of t_equip_unit
-- ----------------------------
-- ----------------------------
-- Table structure for `t_equips_capacity_expand`
-- ----------------------------
DROP TABLE IF EXISTS `t_equips_capacity_expand`;
CREATE TABLE `t_equips_capacity_expand` (
  `expand_num` int(11) DEFAULT '0' COMMENT '扩展格子数',
  `goldbuyprice` bigint(11) DEFAULT '0' COMMENT '金币扩展价格',
  `diamondbuyprice` bigint(20) DEFAULT '0' COMMENT '钻石扩展价格'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='背包服饰栏扩展表';
-- ----------------------------
-- Records of t_equips_capacity_expand
-- ----------------------------
-- ----------------------------
-- Table structure for `t_face`
-- ----------------------------
DROP TABLE IF EXISTS `t_face`;
CREATE TABLE `t_face` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL COMMENT '妆容名称',
  `type` int(11) DEFAULT '0' COMMENT '2头发，3眼睛，4鼻子，5嘴巴，6眉毛，40肤色',
  `star` int(11) DEFAULT '0' COMMENT '星级',
  `combine_frag` int(11) DEFAULT '0' COMMENT '合成需要的碎片数',
  `exp` varchar(255) DEFAULT '[10,20]' COMMENT '升级经验',
  `add_exp` int(11) DEFAULT '1' COMMENT '1碎片增加的经验',
  `buff_effect_val` varchar(255) DEFAULT NULL COMMENT 'buff效果值',
  `buff_effect_styles` varchar(255) DEFAULT NULL COMMENT '影响的服饰风格集合',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='妆容表';
-- ----------------------------
-- Records of t_face
-- ----------------------------
-- ----------------------------
-- Table structure for `t_factory`
-- ----------------------------
DROP TABLE IF EXISTS `t_factory`;
CREATE TABLE `t_factory` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '物流id—第几条物流',
  `open_type` int(11) NOT NULL COMMENT '开启条件 0 默认开启 1 等级开启 2 任务开启',
  `open_para` int(11) NOT NULL COMMENT '开启参数 0对应0 1对应等级 2对应任务id',
  `gold` int(11) NOT NULL COMMENT '开启消耗金币',
  `diamond` int(11) NOT NULL COMMENT '开启消耗钻石',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工厂表';
-- ----------------------------
-- Records of t_factory
-- ----------------------------
-- ----------------------------
-- Table structure for `t_factory_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_factory_level`;
CREATE TABLE `t_factory_level` (
  `level` int(11) NOT NULL COMMENT '物流等级',
  `need_level` int(11) NOT NULL COMMENT '升级需要店铺等级',
  `gold` int(11) NOT NULL COMMENT '升级消耗金币',
  `time` int(11) NOT NULL COMMENT '单个货物运送时间,单位:秒',
  `num` int(11) NOT NULL COMMENT '运送数量上限',
  `icon` char(255) NOT NULL COMMENT '形象',
  `name` char(255) NOT NULL COMMENT '名字',
  PRIMARY KEY (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工厂升级表';
-- ----------------------------
-- Records of t_factory_level
-- ----------------------------
-- ----------------------------
-- Table structure for `t_factory_make`
-- ----------------------------
DROP TABLE IF EXISTS `t_factory_make`;
CREATE TABLE `t_factory_make` (
  `id` int(11) NOT NULL COMMENT '服饰id',
  `blueprintid` int(11) NOT NULL COMMENT '需要的图纸id',
  `price` int(11) NOT NULL COMMENT '定制价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工厂定制表';
-- ----------------------------
-- Records of t_factory_make
-- ----------------------------
-- ----------------------------
-- Table structure for `t_furniture`
-- ----------------------------
DROP TABLE IF EXISTS `t_furniture`;
CREATE TABLE `t_furniture` (
  `id` int(11) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '1-收银台, 2-货架,3-桌子,4-椅子,5花盆,6-地面植物,7-食物,10-地面摆件,21-地板,22-地毯,31-电子屏幕,32-植物墙,33-挂钟,34-墙面装饰,41-壁纸',
  `quality` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `goldbuyprice` int(11) DEFAULT NULL COMMENT '金币购买价格',
  `diamondbuyprice` int(11) DEFAULT NULL COMMENT '钻石购买价格',
  `goldsellprice` int(11) DEFAULT NULL COMMENT '金币出售价格',
  `desc` varchar(255) DEFAULT NULL COMMENT '物品描述',
  `minlevel` int(11) DEFAULT NULL COMMENT '初始等级',
  `maxlevel` int(11) DEFAULT NULL COMMENT '最高等级',
  `continuetime` int(11) DEFAULT NULL,
  `i_stackable` int(11) DEFAULT NULL,
  `i_stackable1` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='家具表';
-- ----------------------------
-- Records of t_furniture
-- ----------------------------
-- ----------------------------
-- Table structure for `t_garden`
-- ----------------------------
DROP TABLE IF EXISTS `t_garden`;
CREATE TABLE `t_garden` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '购买类型',
  `name` varchar(255) DEFAULT NULL,
  `gold` int(11) DEFAULT NULL COMMENT '金币价格',
  `diamond` int(11) DEFAULT NULL COMMENT '钻石价格',
  `apple` int(11) DEFAULT NULL COMMENT '金苹果奖励',
  `equips` text COMMENT '服饰奖励',
  `seeds` text COMMENT '随机种子奖励集合',
  `seedawardrate` text COMMENT '种子奖励概率',
  `extra` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='花园购买表';
-- ----------------------------
-- Records of t_garden
-- ----------------------------
-- ----------------------------
-- Table structure for `t_garden_store`
-- ----------------------------
DROP TABLE IF EXISTS `t_garden_store`;
CREATE TABLE `t_garden_store` (
  `id` int(11) NOT NULL DEFAULT '0',
  `price` int(11) DEFAULT NULL COMMENT '金苹果价格',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0-道具,1-服饰,2-图纸,3-脸,4-金币,5-钻石',
  PRIMARY KEY (`id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='花园苹果商店';
-- ----------------------------
-- Records of t_garden_store
-- ----------------------------
-- ----------------------------
-- Table structure for `t_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_item`;
CREATE TABLE `t_item` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '物品id',
  `name` varchar(255) DEFAULT NULL COMMENT '物品名称',
  `type` int(2) DEFAULT NULL COMMENT '物品类别:0-材料,1-物品,2-礼物,3-种子',
  `desc` varchar(255) DEFAULT NULL COMMENT '物品描述',
  `overlapnum` int(11) DEFAULT NULL COMMENT '叠加数量',
  `script` varchar(255) DEFAULT NULL COMMENT '物品脚本',
  `gettips` varchar(255) DEFAULT NULL COMMENT '获取提示',
  `rarity` int(11) DEFAULT NULL COMMENT '稀有度',
  `extra` text COMMENT '0-道具,1-服饰,2-图纸,3-脸,4-金币,5-钻石',
  `canuse` int(11) DEFAULT NULL COMMENT '可否使用,1-可,0-不可',
  `goldbuyprice` int(11) DEFAULT NULL,
  `goldsellprice` int(11) DEFAULT NULL,
  `icon` char(255) DEFAULT NULL,
  `quality` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='道具表';
-- ----------------------------
-- Records of t_item
-- ----------------------------
-- ----------------------------
-- Table structure for `t_level_selltype`
-- ----------------------------
DROP TABLE IF EXISTS `t_level_selltype`;
CREATE TABLE `t_level_selltype` (
  `level` int(11) DEFAULT NULL COMMENT '店铺等级',
  `selltype` varchar(255) DEFAULT NULL COMMENT '销售类型集合'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺等级-销售服饰类型 关系表';
-- ----------------------------
-- Records of t_level_selltype
-- ----------------------------
-- ----------------------------
-- Table structure for `t_logistics`
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics`;
CREATE TABLE `t_logistics` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '物流id—第几条物流',
  `open_type` int(11) NOT NULL COMMENT '开启条件 0 默认开启 1 等级开启 2 任务开启',
  `open_para` int(11) NOT NULL COMMENT '开启参数 0对应0 1对应等级 2对应任务id',
  `gold` int(11) NOT NULL COMMENT '开启消耗金币',
  `diamond` int(11) NOT NULL COMMENT '开启消耗钻石',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流表';
-- ----------------------------
-- Records of t_logistics
-- ----------------------------
-- ----------------------------
-- Table structure for `t_logistics_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_logistics_level`;
CREATE TABLE `t_logistics_level` (
  `level` int(11) NOT NULL COMMENT '物流等级',
  `need_level` int(11) NOT NULL COMMENT '升级需要店铺等级',
  `gold` int(11) NOT NULL COMMENT '升级消耗金币',
  `time` int(11) NOT NULL COMMENT '单个货物运送时间,单位:秒',
  `num` int(11) NOT NULL COMMENT '运送数量上限',
  `icon` char(255) NOT NULL COMMENT '形象',
  `name` char(255) NOT NULL COMMENT '名字',
  PRIMARY KEY (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流升级表';
-- ----------------------------
-- Records of t_logistics_level
-- ----------------------------
-- ----------------------------
-- Table structure for `t_office`
-- ----------------------------
DROP TABLE IF EXISTS `t_office`;
CREATE TABLE `t_office` (
  `id` int(11) NOT NULL DEFAULT '0',
  `type` int(11) DEFAULT NULL COMMENT '雇员类型,0-收银员',
  `need_level` int(11) DEFAULT NULL COMMENT '雇佣需要的主角等级',
  `time` int(11) DEFAULT NULL COMMENT '持续时间(秒)',
  `gold` int(11) DEFAULT NULL COMMENT '雇佣金币价格',
  `name` char(255) DEFAULT '' COMMENT '雇员名称',
  `body` char(255) DEFAULT NULL COMMENT '形象',
  `decrsecond` int(11) DEFAULT NULL COMMENT '销售buff(销售减少的秒数)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='雇员表';
-- ----------------------------
-- Records of t_office
-- ----------------------------
-- ----------------------------
-- Table structure for `t_player_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_player_level`;
CREATE TABLE `t_player_level` (
  `level` int(11) DEFAULT NULL,
  `exp` int(11) DEFAULT NULL COMMENT '升到下一级需要的经验',
  `awards` text COMMENT '升级后的奖励',
  `talk` int(11) DEFAULT '0' COMMENT '升级后的剧情对话,0表示无',
  `funcs` varchar(255) DEFAULT '[]' COMMENT '升级后解锁的功能点,FIXME 需不需要?'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='玩家升级表';
-- ----------------------------
-- Records of t_player_level
-- ----------------------------
-- ----------------------------
-- Table structure for `t_shelf`
-- ----------------------------
DROP TABLE IF EXISTS `t_shelf`;
CREATE TABLE `t_shelf` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '货架id=家具表中的家具id',
  `open_type` int(11) NOT NULL COMMENT '开启条件 1等级开启 2任务开启',
  `open_para` int(11) NOT NULL COMMENT '开启条件参数，等级/任务id',
  `gold` int(11) NOT NULL COMMENT '购买金币花费',
  `diamond` int(11) NOT NULL COMMENT '购买钻石花费',
  `type` char(255) NOT NULL COMMENT '货架类型 x,y 行数,列数',
  `sell_type` char(255) NOT NULL COMMENT '货架位出售货物类型:1,1,1,2,2,2,2,2,2  类型取自equip_type  3*4货架就填12个',
  `start_state` char(255) NOT NULL COMMENT '货架位初始激活状态 1,0,0,1,0,0,0,0,0   3*4货架写12个',
  `open_diamond` varchar(255) DEFAULT NULL COMMENT '开启消耗钻石',
  `open_gold` varchar(255) DEFAULT NULL COMMENT '开启消耗金币',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货架表';
-- ----------------------------
-- Records of t_shelf
-- ----------------------------
-- ----------------------------
-- Table structure for `t_shelf_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_shelf_level`;
CREATE TABLE `t_shelf_level` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '货架家具id*100+level',
  `level` int(11) NOT NULL COMMENT '货架等级',
  `gold` int(11) NOT NULL COMMENT '升级花费金币',
  `position_num` char(255) NOT NULL COMMENT '货架位上架数量：20,20,20,40,40,40,40,40,40',
  `icon` char(255) NOT NULL COMMENT '形,资源',
  `size` char(255) NOT NULL COMMENT '占地大小 3*3',
  PRIMARY KEY (`id`,`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='货架升级表';
-- ----------------------------
-- Records of t_shelf_level
-- ----------------------------
-- ----------------------------
-- Table structure for `t_sysconf`
-- ----------------------------
DROP TABLE IF EXISTS `t_sysconf`;
CREATE TABLE `t_sysconf` (
  `name` varchar(255) DEFAULT '5' COMMENT '心跳间隔，单位seconds',
  `value` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统参数表';
-- ----------------------------
-- Records of t_sysconf
-- ----------------------------
-- ----------------------------
-- Table structure for `t_system_shop`
-- ----------------------------
DROP TABLE IF EXISTS `t_system_shop`;
CREATE TABLE `t_system_shop` (
  `id` int(11) DEFAULT NULL COMMENT '物品id',
  `type` int(11) DEFAULT NULL COMMENT '0-道具,1-服饰,2-图纸,3-脸,4-金币,5-钻石,6-家具,7-脸碎片',
  `goldbuyprice` int(11) DEFAULT NULL,
  `diamondbuyprice` int(11) DEFAULT NULL,
  `need_level` int(11) DEFAULT NULL,
  `num` int(11) DEFAULT NULL COMMENT '商店里能买的上限,0-没有上限,否则就是上限',
  `itemid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统商城表';
-- ----------------------------
-- Records of t_system_shop
-- ----------------------------
-- ----------------------------
-- Table structure for `t_wholesale_market`
-- ----------------------------
DROP TABLE IF EXISTS `t_wholesale_market`;
CREATE TABLE `t_wholesale_market` (
  `id` int(11) NOT NULL COMMENT '服饰id',
  `need_level` int(11) NOT NULL COMMENT '解锁等级',
  `price` int(11) NOT NULL COMMENT '购买价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='批发市场表';
-- ----------------------------
-- Records of t_wholesale_market
-- ----------------------------
-- ----------------------------
-- Table structure for `t_window`
-- ----------------------------
DROP TABLE IF EXISTS `t_window`;
CREATE TABLE `t_window` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '橱窗编号',
  `buylevel` int(11) DEFAULT NULL COMMENT '购买等级要求',
  `gold` int(11) DEFAULT NULL COMMENT '金币价格',
  `diamond` int(11) DEFAULT NULL COMMENT '钻石价格',
  `face` varchar(255) DEFAULT NULL COMMENT '模特妆容',
  `equips` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='橱窗配置表';
-- ----------------------------
-- Records of t_window
-- ----------------------------
