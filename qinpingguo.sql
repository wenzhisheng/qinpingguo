/*
 Navicat Premium Data Transfer

 Source Server         : 45.203.97.106
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 45.203.97.106:3306
 Source Schema         : qinpingguo

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 23/12/2018 13:08:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_user`;
CREATE TABLE `t_admin_user`  (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ACCOUNT` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员账号',
  `USER_PASSWORD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '管理员密码',
  `CREATION_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `LAST_UPDATED_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`USER_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_admin_user
-- ----------------------------
INSERT INTO `t_admin_user` VALUES (1, 'admin1', 'admin1', '2018-12-12 13:29:02', '2018-12-12 13:29:02');
INSERT INTO `t_admin_user` VALUES (2, 'leader1', 'leader1', '2018-12-12 13:29:14', '2018-12-12 13:29:14');

-- ----------------------------
-- Table structure for t_apple_account_info
-- ----------------------------
DROP TABLE IF EXISTS `t_apple_account_info`;
CREATE TABLE `t_apple_account_info`  (
  `APPLE_ACCOUNT_INFO_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `TEAM_ID` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合作号',
  `COOKIE_VALUE` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会话cookie',
  `DSESSIONID` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '会话Id(DSESSIONID)',
  `CSRF` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跨站请求伪造csrf',
  `CSRF_TS` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间戳csrf_ts',
  `FILE_PATH` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '保存文件路径',
  `SHELL_PATH` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '脚本路径',
  `IS_ENABLE` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用 0:禁用 1:启用',
  `IS_SESSION` tinyint(1) NULL DEFAULT NULL COMMENT '是否会话 0:过期 1:正常',
  `DEVICE_LENGTH` int(3) NULL DEFAULT 0 COMMENT '设备数量长度',
  `CREATION_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `LAST_UPDATED_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`APPLE_ACCOUNT_INFO_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '苹果账号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_apple_account_info
-- ----------------------------
INSERT INTO `t_apple_account_info` VALUES (1, 'B4FAS3593K', 's_fid=2060A7E5ACF25DE6-047F0DF543D3E10F; s_sq=awdappledeveloper%3D%2526pid%253Daccount%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fdeveloper.apple.com%25252Faccount%25252Fios%25252Fcertificate%25252F%25253FteamId%25253DB4FAS3593K%2526ot%253DA; s_cc=true; s_pathLength=developer%3D1%2C; s_vi=[CS]v1|2E0F866D052E5351-60002C4B400019B2[CE]; dslang=CN-ZH; myacinfo=DAWTKNV2f325b8c9bbe0d870de898db804d46df5927d36ddc726050cf3be28d70e35bec409ae3004f5d7fb2bd5088e59f172ee2149e4c4a84ac871c08d5141a83c42ddaaf77889f04665b74455585fcba3c694ff9b8f56e52ac2637e92b8b2488623427999b36b58a55ff7eff2ffbb608ef10780d1749f95bfb2f9aa11fcf2fd47f530a64eaffe2b28a77bf44be71b5cffb6ac0be37a036e81972e0424750e75a4d3559aa39652787ee03dd347d5c89f9570b6d112125a4d11793ae23efa450a8c19f8b40865290dddd3a0fb2f74c1e8ea56b46585514da5af61bca97861a01795802f99351529d345631f3b33e8c2faa50420a2753d9c7f3e3d25d8c59ea3ef9cc59dd734656134313364633731633134363166653865633265663335326365323465383334653564343037MVRYV2; site=CHN; ccl=25XkCJXHKIj6Tj7V3UWsZQ==; geo=PH;', 'DSESSIONID=s2cb1dl3t8l6sv7uros9a3nhek4j80bthn8hn7028ig7ijhu96f', '0b52151af082521f4974d717e90b97526b0a56543d0ddd874ad7979783870f47', '1545540383628', '/Users/macos/Desktop/ios/8bet/', '/Users/macos/Desktop/ios/8bet/com.xiaoji.number1/resign.sh', 1, 1, 34, '2018-12-12 21:03:44', '2018-12-23 13:07:52');
INSERT INTO `t_apple_account_info` VALUES (2, 'D9PCZHZYHQ', 's_fid=1A4AAAB1B15C6D1F-14BFF340A4326ECB; s_sq=awdappledeveloper%3D%2526pid%253Daccount%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fdeveloper.apple.com%25252Faccount%25252Fios%25252Fcertificate%25252F%25253FteamId%25253DD9PCZHZYHQ%2526ot%253DA; s_vi=[CS]v1|2E0F8694052E6329-40000C5160001146[CE]; s_cc=true; s_pathLength=developer%3D1%2C; dslang=CN-ZH; myacinfo=DAWTKNV22e9423fca57d89f9c4f485d5a9639da76c6874177a14ce5bcb9c800775ac0a9e0c6a14ea29ec4624d8670cf41c322e0d3135eb7eac0d0b3cc68ae239adf2ba71a2b5ec39ce4cdd9b2464f1d16a02945cdb120a47125c3bf69eb3002e6831ce71e5c829665d600013b66f54625c1b592129ebff671ec0cad15e2ab3947b8f6f89f6ff4b222ce7680549241ff56a48e82e43e9cfd767c4f8ddc0db9b4eb325454179ddb7bc6f0dd847344c0b8a0ed677854a2a81e832e21fe171dd658f2afa94fbbd7cfe43c78f42bbafb70ec6017f7ed3e231d8aac34d99c75869f0faa58671351f3eb8274be0b0ea16b29ee064927f4ae91561c0deffb4de38900b6989917b2563326639373766623764653536343036616564313234316231323331333862306138353562616266MVRYV2; site=CHN; ccl=95FCUTYYEdmME3y7tdCg4w==; geo=PH;', 'DSESSIONID=1d6jnh1eaih4fmembvh4pjc6h1vagemi47dq1a9rv262t20r1kdh', 'd61b2507a8b9f7a8258ecede8bf59b6e09df672cab5c1a6db77bf1e816e0c114', '1545540384422', '/Users/macos/Desktop/ios/8bet/', '/Users/macos/Desktop/ios/8bet/com.xiaoji.number2/resign.sh', 1, 1, 22, '2018-12-12 21:05:38', '2018-12-23 12:46:01');
INSERT INTO `t_apple_account_info` VALUES (3, 'Y97BGWDM5Q', 's_fid=57F6827A0EBB602D-2522C17ABB72AD02; s_sq=awdappledeveloper%3D%2526pid%253Daccount%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fdeveloper.apple.com%25252Faccount%25252Fios%25252Fcertificate%25252F%25253FteamId%25253DY97BGWDM5Q%2526ot%253DA; s_cc=true; s_pathLength=developer%3D1%2C; s_vi=[CS]v1|2E0F86BB852E585B-40000C5160004B4B[CE]; dslang=CN-ZH; myacinfo=DAWTKNV27f635ec85f3c8494692110d353d4556b4182aee4a036cde825d58dc24854489fc2c6415116febdd42155497d306e0900b11c0f80836555817eb46a6b25206d968cf89b910e5876b2081dd0ce4544cb34e213bdb1d3d30a46cb24472707fb40d52c90f7c3d20a49ba4fd1d1f897a901b0e3d57019c8baa7b1dc6a02b9b8da5ece3499a41c84a5265092bb6da388fafc1cd0316df29d175557a4e2c8c32242f0ed73841ea52240f80453efc60159c472bdc238b2a8ae5aed75a89d9301f1942a67dd3979488c62bc4c4a67c4d77318bef73411aefbbc1757710bcd007f9efcdd0b80db08fb032d1c2eac5a8f92a3c9042089be62c654ad31d7bf90e921442a116336333461393663623964666532323836333635646438316138633666623838386461333961313436MVRYV2; site=CHN; ccl=qnnY8r0F6DfGGrceVtJRkg==; geo=PH;', 'DSESSIONID=a9vrr3f1i0g28b36sircn5lqma0qhpgphkeuc7skb2eqe6qqngv', '6c4dadce6fc67342ebd683c033368b52d57345cda0988ba0406077d763eb3ed9', '1545540504155', '/Users/macos/Desktop/ios/8bet/', '/Users/macos/Desktop/ios/8bet/com.xiaoji.number3/resign.sh', 1, 1, 17, '2018-12-12 21:10:43', '2018-12-23 12:48:01');
INSERT INTO `t_apple_account_info` VALUES (4, 'SQM939L947', 's_fid=548CB09D7F0D1930-3CA07EEF3FC4FD40; s_sq=awdappledeveloper%3D%2526pid%253Daccount%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fdeveloper.apple.com%25252Faccount%25252Fios%25252Fcertificate%25252F%25253FteamId%25253DSQM939L947%2526ot%253DA; s_vi=[CS]v1|2E0F86E4852E41BA-60000C3C0000E649[CE]; s_cc=true; s_pathLength=developer%3D1%2C; dslang=CN-ZH; myacinfo=DAWTKNV2ee8d0edba582cfbc4711bab005aff93bf2be18636b95a42881f19f1d1b2a2c91c10d222e7d19aaede86a5a7173d0b6f1df2b6d0f3b5f863510ea48c6034a3c7ceaaa6f6c7a068568336dad3016ec36838f37dcd3592b8d94ca94c576fcb1ccd3bccd81e0d212d17d0afc9766d88655ddef8ede15e4ad8cee0d9cadab0f07f3c9cf85117b87db71fbd2250912a8734467f7c61ac88a9ff3b9d58fe4f7f28280f1d3b1ec786da0dded5e33fda52831a4fc6ee12be04f3e18b2a40dbde9e29ce88e67c8a2cb4c4429ca848d8f6da1bb84e2b83f9c8238306fa517b4a908d0ce3a89620234ae86dbb8894b4d26afb01382a46a004e4dba35dce9b9154b4245f74dec64396632306130376462343737623936303435633936396534643861666665626234323831633230MVRYV2; site=CHN; ccl=kWhPAThmHvx5dJ+MY311Dg==; geo=PH;', 'DSESSIONID=15fhh1s0l5gjs19b6l52hvbsfmd8jcpo4579aovu1v33q4va1miu', 'c6758ba23fc01387ae0b9a604b4eafbc2ea47774bb703788df2fa57fee84e419', '1545540623692', '/Users/macos/Desktop/ios/8bet/', '/Users/macos/Desktop/ios/8bet/com.xiaoji.number4/resign.sh', 1, 1, 20, '2018-12-12 21:12:11', '2018-12-23 12:50:00');
INSERT INTO `t_apple_account_info` VALUES (5, '9QT4D435RQ', 's_fid=7C7F4E8AC6BB2F12-2F4F3446FF88A472; s_sq=awdappledeveloper%3D%2526pid%253Daccount%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fdeveloper.apple.com%25252Faccount%25252Fios%25252Fcertificate%25252F%25253FteamId%25253D9QT4D435RQ%2526ot%253DA; s_cc=true; s_pathLength=developer%3D1%2C; s_vi=[CS]v1|2E0936FF052E180A-60002C3C000006A7[CE]; acn01=6FYXA1uMjFyJy6AwGke24i15PXycTGGh/nSVVHji/NylAALVnW+Kwe8=; dslang=US-EN; myacinfo=DAWTKNV2b04bb1bb1e750820879aa513c1af1fca7142cb1a24b235b21cdfb852e454abf79cfc40e5d7e9c3403790cf776b7525dfc0e9138bf23782cc79ad0957aed60f5ce9be547110bde5c390bc7192f8af3c53b449332cac9f147b494569519bd99eb6c66cb8bf9f1b73176294bea4b48793140983fbe38a65256bc4bf7c26c1f01613a51b214cf156cdfbf51ef16974bc42c7b7621f312bc7cdf96cc3894d609c86b391cb1a6df1cd67cc19630ca41513b37b536f5bd1268fd396bc2a11d7171d69520c4e4a65f5d4852a3a7572db8c6647b5019a10e138de30740fd9bb3623a40536d3a9916f5c8f1a7760fa8497398719fc87966991daed05dfaae4510d5b21846766393533383033613032363563376239343963353265373136326133343130663664626131633934MVRYV2; ccl=DrC7aiAZBGAbImaH5ehY8A==; geo=PH;', 'DSESSIONID=1ugjug20oucnkju1s7gjq3u37ph4rt273l3i8crm428tjik44pmn', '16ac9dcb9d225a53d0c6a34b853c5f4cc5f602d875f15f1bf4bbbeb5523e58df', '1544742002243', '/Users/macos/Desktop/ios/8bet/', '/Users/macos/Desktop/ios/8bet/com.xiaoji.number5/resign.sh', 0, 0, 8, '2018-12-13 14:06:39', '2018-12-14 20:34:10');
INSERT INTO `t_apple_account_info` VALUES (6, 'N4RGJDJ8S6', 's_fid=05BAE987BAA767D8-1511C50924FDE3EA; s_sq=awdappledeveloper%3D%2526pid%253Daccount%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fdeveloper.apple.com%25252Faccount%25252Fios%25252Fcertificate%25252F%25253FteamId%25253DN4RGJDJ8S6%2526ot%253DA; s_vi=[CS]v1|2E0F8714052E4418-40000C4FA0001B8A[CE]; s_cc=true; s_pathLength=developer%3D1%2C; dslang=CN-ZH; myacinfo=DAWTKNV2a582aae1d10d7999ff0e6c1499eea0ab3874c5c0197fb626456072ba7c8c61e1a48eb46a15e392751f56ff66094801166570bc83dae59f745c2a8615d41f2ec4f2478c1bdd40730022639b0175de6fbc5826012a36b7e863181d573ee466f7f041b1dc0c609ac776d299889ac60e584dbb24bb166c553566f0cb601565293e3839ed8c0b098040d2527ac6e1364506889ee563dc7132f506ef470434675e17a66468abfcbcf0f477f79a93bc56ddbfe2594d33b72a3c1e1eff19e6bac0a982fd29349115c4e93a8218a73d096726eb461b6860a84ec960fb4a5ff06b4565d7d8909e7125529528654362ec201f3b1b3852957d1e22c602b28ad865cda6222a3239616230643963626232326364323038326536396162313838663266646666616236663366356136MVRYV2; site=CHN; ccl=DZBFcjeOAPtAHOSxKmSsRQ==; geo=PH;', 'DSESSIONID=950t3hhhcmqhset34bl2l6d8jhmaghtbcdq8h532no5p7u99btk', '2215c7efea3286c43c21a5d110d7309c3f5cf74a83c879bbb3e38760ec1cebe0', '1545540624491', '/Users/macos/Desktop/ios/8bet/', '/Users/macos/Desktop/ios/8bet/com.xiaoji.number6/resign.sh', 1, 1, 15, '2018-12-14 13:23:24', '2018-12-23 12:50:01');

-- ----------------------------
-- Table structure for t_apple_appidid
-- ----------------------------
DROP TABLE IF EXISTS `t_apple_appidid`;
CREATE TABLE `t_apple_appidid`  (
  `APPLE_APPIDID_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '应用信息主键ID',
  `TEAM_ID` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合作号',
  `APPIDID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用编号',
  `APPIDID_PATH` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '应用下载路径',
  `CREATION_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `LAST_UPDATED_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `LAST_UPDATED_BY` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`APPLE_APPIDID_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '应用信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_apple_appidid
-- ----------------------------
INSERT INTO `t_apple_appidid` VALUES (10, 'B4FAS3593K', 'com.xiaoji.number1', 'https://appxz9.com/8bet/com.xiaoji.number1/downLoad.html', '2018-12-19 14:17:01', '2018-12-19 14:17:01', NULL);
INSERT INTO `t_apple_appidid` VALUES (11, 'D9PCZHZYHQ', 'com.xiaoji.number2', 'https://appxz9.com/8bet/com.xiaoji.number2/downLoad.html', '2018-12-19 14:20:02', '2018-12-19 14:20:02', NULL);
INSERT INTO `t_apple_appidid` VALUES (12, 'Y97BGWDM5Q', 'com.xiaoji.number3', 'https://appxz9.com/8bet/com.xiaoji.number3/downLoad.html', '2018-12-19 14:22:24', '2018-12-19 14:22:24', NULL);
INSERT INTO `t_apple_appidid` VALUES (13, 'SQM939L947', 'com.xiaoji.number4', 'https://appxz9.com/8bet/com.xiaoji.number4/downLoad.html', '2018-12-19 14:27:12', '2018-12-19 14:27:12', NULL);
INSERT INTO `t_apple_appidid` VALUES (14, 'N4RGJDJ8S6', 'com.xiaoji.number6', 'https://appxz9.com/8bet/com.xiaoji.number6/downLoad.html', '2018-12-19 14:29:17', '2018-12-19 14:29:17', NULL);

-- ----------------------------
-- Table structure for t_apple_udid
-- ----------------------------
DROP TABLE IF EXISTS `t_apple_udid`;
CREATE TABLE `t_apple_udid`  (
  `APPLE_UDID_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备主键ID',
  `TEAM_ID` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '合作号',
  `DEVICE_NUMBERS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'UDID',
  `CREATION_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `LAST_UPDATED_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`APPLE_UDID_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_apple_udid
-- ----------------------------
INSERT INTO `t_apple_udid` VALUES (1, 'B4FAS3593K', '0d799d84044d6daf960de3a7df378968cb23923b', '2018-12-20 18:12:59', '2018-12-20 18:12:59');
INSERT INTO `t_apple_udid` VALUES (2, 'B4FAS3593K', 'bf3a71af997e680d122734bd26afb313807db99e', '2018-12-20 19:17:08', '2018-12-20 19:17:08');
INSERT INTO `t_apple_udid` VALUES (3, 'D9PCZHZYHQ', 'bcff8422d51e40809d3457abb419c740f63c26e1', '2018-12-20 19:18:42', '2018-12-20 19:18:42');
INSERT INTO `t_apple_udid` VALUES (4, 'N4RGJDJ8S6', 'a0a6bc6eed4882de459e02ea6d9c7613cbd757f4', '2018-12-20 19:27:05', '2018-12-20 19:27:05');
INSERT INTO `t_apple_udid` VALUES (5, 'SQM939L947', '29ff17bcc5dbdaa886acffca484ac25f625785f7', '2018-12-20 19:27:05', '2018-12-20 19:27:05');
INSERT INTO `t_apple_udid` VALUES (6, 'B4FAS3593K', '728d28d9eff08a8c5436e521ef18c8018c30cd8e', '2018-12-20 19:27:35', '2018-12-20 19:27:35');
INSERT INTO `t_apple_udid` VALUES (7, 'Y97BGWDM5Q', '728d28d9eff08a8c5436e521ef18c8018c30cd8e', '2018-12-20 19:27:56', '2018-12-20 19:27:56');
INSERT INTO `t_apple_udid` VALUES (9, 'D9PCZHZYHQ', '00008020-00130DD41E78002E', '2018-12-20 22:02:50', '2018-12-20 22:02:50');
INSERT INTO `t_apple_udid` VALUES (10, 'B4FAS3593K', 'aaa31e451e89e4807231ea1ea6bcba52da50db83', '2018-12-21 12:42:25', '2018-12-21 12:42:25');

SET FOREIGN_KEY_CHECKS = 1;
