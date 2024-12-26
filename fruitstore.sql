/*
 Navicat Premium Data Transfer

 Source Server         : HW
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : fruitstore

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 26/12/2024 14:30:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for good_cart
-- ----------------------------
DROP TABLE IF EXISTS `good_cart`;
CREATE TABLE `good_cart`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主id',
  `userId` int(0) NOT NULL,
  `goodId` int(0) NOT NULL,
  `count` int(0) NULL DEFAULT NULL,
  `price` float NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of good_cart
-- ----------------------------
INSERT INTO `good_cart` VALUES (12, 1, 3, 2, 20);
INSERT INTO `good_cart` VALUES (13, 1, 4, 1, 16);
INSERT INTO `good_cart` VALUES (14, 1, 5, 1, 17);
INSERT INTO `good_cart` VALUES (15, 1, 10, 1, 13);
INSERT INTO `good_cart` VALUES (19, -1, 10, 3, 13);
INSERT INTO `good_cart` VALUES (29, -1, 4, 1, 16);
INSERT INTO `good_cart` VALUES (38, -1, 5, 2, 17);
INSERT INTO `good_cart` VALUES (43, -1, 2, 3, 1);
INSERT INTO `good_cart` VALUES (47, -1, 6, 4, 10);
INSERT INTO `good_cart` VALUES (48, -1, 7, 1, 15);
INSERT INTO `good_cart` VALUES (49, -1, 8, 1, 13);
INSERT INTO `good_cart` VALUES (50, -1, 9, 2, 10);

-- ----------------------------
-- Table structure for goodleft
-- ----------------------------
DROP TABLE IF EXISTS `goodleft`;
CREATE TABLE `goodleft`  (
  `GoodClassifyId` int(0) NOT NULL AUTO_INCREMENT,
  `GoodClassify` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`GoodClassifyId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goodleft
-- ----------------------------
INSERT INTO `goodleft` VALUES (1, '限时折扣');
INSERT INTO `goodleft` VALUES (2, '新品');
INSERT INTO `goodleft` VALUES (3, '橙子系列');
INSERT INTO `goodleft` VALUES (4, '西瓜系列');
INSERT INTO `goodleft` VALUES (5, '雪梨系列');
INSERT INTO `goodleft` VALUES (6, '苹果系列');
INSERT INTO `goodleft` VALUES (7, '果昔系列');
INSERT INTO `goodleft` VALUES (8, '纯果汁及其他');

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goodId` int(0) NOT NULL AUTO_INCREMENT,
  `goodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `goodMsg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `goodPrice` decimal(10, 2) NULL DEFAULT NULL,
  `GoodClassifyId` int(0) NULL DEFAULT NULL,
  `goodHeat` enum('热','常温','冰') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '常温',
  `goodSugar` enum('不加糖','三分糖','五分糖','七分糖','正常甜','加糖') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '正常甜',
  `goodImage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`goodId`) USING BTREE,
  INDEX `GoodClassifyId`(`GoodClassifyId`) USING BTREE,
  CONSTRAINT `goods_ibfk_1` FOREIGN KEY (`GoodClassifyId`) REFERENCES `goodleft` (`GoodClassifyId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '阳光玫瑰汁', '饮料', 18.00, 1, '常温', '正常甜', 'p1');
INSERT INTO `goods` VALUES (2, '烤蜜薯', '吃', 1.00, 1, '常温', '正常甜', 'p2');
INSERT INTO `goods` VALUES (3, '草莓山楂雪梨汁', '草莓！', 20.00, 2, '常温', '正常甜', 'p3');
INSERT INTO `goods` VALUES (4, '杨桃苹果汁', '五角星', 16.00, 2, '常温', '正常甜', 'p4');
INSERT INTO `goods` VALUES (5, '鲜橙汁', 'vc', 17.00, 3, '常温', '正常甜', 'p5');
INSERT INTO `goods` VALUES (6, '西瓜汁', '红的', 10.00, 4, '常温', '正常甜', 'p6');
INSERT INTO `goods` VALUES (7, '西柚雪梨汁', '红的', 15.00, 5, '常温', '正常甜', 'p7');
INSERT INTO `goods` VALUES (8, '苹果汁', '酸甜的', 13.00, 6, '常温', '正常甜', 'p8');
INSERT INTO `goods` VALUES (9, '香蕉果昔', '相交的', 10.00, 7, '常温', '正常甜', 'p9');
INSERT INTO `goods` VALUES (10, '芒果果昔', '过命的', 13.00, 7, '常温', '正常甜', 'p10');
INSERT INTO `goods` VALUES (11, '哈密瓜汁', '哈哈哈', 14.00, 8, '常温', '正常甜', 'p11');

-- ----------------------------
-- Table structure for order_items
-- ----------------------------
DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items`  (
  `orderItemId` int(0) NOT NULL AUTO_INCREMENT,
  `orderId` int(0) NOT NULL,
  `goodId` int(0) NOT NULL,
  `goodPrice` decimal(10, 2) NOT NULL,
  `goodNum` int(0) NOT NULL,
  `goodSumPrice` decimal(10, 2) NOT NULL,
  `goodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `goodImage` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`orderItemId`) USING BTREE,
  INDEX `orderId`(`orderId`) USING BTREE,
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `orders` (`orderId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_items
-- ----------------------------
INSERT INTO `order_items` VALUES (1, 1, 1, 0.00, 1, 0.00, '111', '0');
INSERT INTO `order_items` VALUES (2, 6, 2, 2.00, 3, 5.00, '1', '1');
INSERT INTO `order_items` VALUES (3, 13, 1, 18.00, 1, 18.00, '阳光玫瑰汁', 'https://example.com/sunshine_rose_juice.jpg');
INSERT INTO `order_items` VALUES (4, 13, 1, 18.00, 1, 18.00, '烤蜜薯', 'https://example.com/roasted_sweet_potato.jpg');
INSERT INTO `order_items` VALUES (5, 13, 2, 100.00, 10, 1000.00, '草莓山楂雪梨汁', 'https://example.com/strawberry_hawthorn_pear_juice.jpg');
INSERT INTO `order_items` VALUES (6, 13, 2, 100.00, 10, 1000.00, '杨桃苹果汁', 'https://example.com/starfruit_apple_juice.jpg');
INSERT INTO `order_items` VALUES (7, 13, 3, 20.00, 1, 20.00, '鲜橙汁', 'https://example.com/fresh_orange_juice.jpg');
INSERT INTO `order_items` VALUES (8, 13, 4, 16.00, 6, 96.00, '西瓜汁', 'https://example.com/watermelon_juice.jpg');
INSERT INTO `order_items` VALUES (9, 13, 5, 17.00, 1, 17.00, '西柚雪梨汁', 'https://example.com/grapefruit_pear_juice.jpg');
INSERT INTO `order_items` VALUES (10, 13, 6, 10.00, 3, 30.00, '苹果汁', 'https://example.com/apple_juice.jpg');
INSERT INTO `order_items` VALUES (11, 14, 2, 1.00, 3, 3.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (12, 14, 2, 1.00, 3, 3.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (13, 14, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (14, 15, 2, 1.00, 3, 3.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (15, 15, 2, 1.00, 3, 3.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (16, 15, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (17, 16, 2, 1.00, 3, 3.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (18, 16, 2, 1.00, 3, 3.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (19, 16, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (20, 17, 2, 1.00, 19, 19.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (21, 17, 2, 1.00, 19, 19.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (22, 17, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (23, 18, 2, 1.00, 19, 19.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (24, 18, 2, 1.00, 19, 19.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (25, 18, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (26, 19, 2, 1.00, 19, 19.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (27, 19, 2, 1.00, 19, 19.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (28, 19, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (29, 20, 2, 1.00, 19, 19.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (30, 20, 2, 1.00, 19, 19.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (31, 20, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (32, 21, 2, 1.00, 19, 19.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (33, 21, 2, 1.00, 19, 19.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (34, 21, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (35, 22, 2, 1.00, 19, 19.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (36, 22, 2, 1.00, 19, 19.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (37, 22, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (38, 23, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (39, 24, 3, 20.00, 4, 80.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (40, 24, 4, 16.00, 1, 16.00, '西瓜汁', 'p6');
INSERT INTO `order_items` VALUES (41, 26, 1, 1.00, 1, 1.00, '阳光玫瑰汁', 'p1');
INSERT INTO `order_items` VALUES (42, 26, 1, 1.00, 1, 1.00, '烤蜜薯', 'p2');
INSERT INTO `order_items` VALUES (43, 26, 3, 20.00, 2, 40.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (44, 27, 3, 20.00, 3, 60.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (45, 30, 2, 1.00, 3, 3.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (46, 30, 2, 1.00, 3, 3.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (47, 30, 3, 20.00, 1, 20.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (48, 31, 5, 17.00, 3, 51.00, '西柚雪梨汁', 'p7');
INSERT INTO `order_items` VALUES (49, 31, 6, 10.00, 2, 20.00, '苹果汁', 'p8');
INSERT INTO `order_items` VALUES (50, 32, 3, 20.00, 13, 260.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (51, 34, 4, 16.00, 7, 112.00, '西瓜汁', 'p6');
INSERT INTO `order_items` VALUES (52, 34, 5, 17.00, 5, 85.00, '西柚雪梨汁', 'p7');
INSERT INTO `order_items` VALUES (53, 35, 5, 17.00, 15, 255.00, '西柚雪梨汁', 'p7');
INSERT INTO `order_items` VALUES (54, 36, 3, 20.00, 1, 20.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (55, 37, 5, 17.00, 5, 85.00, '西柚雪梨汁', 'p7');
INSERT INTO `order_items` VALUES (56, 38, 4, 16.00, 4, 64.00, '西瓜汁', 'p6');
INSERT INTO `order_items` VALUES (57, 39, 2, 1.00, 4, 4.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (58, 39, 2, 1.00, 4, 4.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (59, 39, 3, 20.00, 6, 120.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (60, 40, 8, 13.00, 1, 13.00, '哈密瓜汁', 'p11');
INSERT INTO `order_items` VALUES (61, 40, 4, 16.00, 4, 64.00, '西瓜汁', 'p6');
INSERT INTO `order_items` VALUES (62, 41, 2, 1.00, 1, 1.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (63, 41, 2, 1.00, 1, 1.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (64, 41, 3, 20.00, 2, 40.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (65, 42, 5, 17.00, 2, 34.00, '西柚雪梨汁', 'p7');
INSERT INTO `order_items` VALUES (66, 43, 5, 17.00, 4, 68.00, '西柚雪梨汁', 'p7');
INSERT INTO `order_items` VALUES (67, 43, 6, 10.00, 2, 20.00, '苹果汁', 'p8');
INSERT INTO `order_items` VALUES (68, 44, 6, 10.00, 1, 10.00, '苹果汁', 'p8');
INSERT INTO `order_items` VALUES (69, 45, 4, 16.00, 2, 32.00, '西瓜汁', 'p6');
INSERT INTO `order_items` VALUES (70, 45, 5, 17.00, 2, 34.00, '西柚雪梨汁', 'p7');
INSERT INTO `order_items` VALUES (71, 46, 4, 16.00, 2, 32.00, '西瓜汁', 'p6');
INSERT INTO `order_items` VALUES (72, 47, 3, 20.00, 2, 40.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (73, 47, 4, 16.00, 1, 16.00, '西瓜汁', 'p6');
INSERT INTO `order_items` VALUES (74, 48, 2, 1.00, 2, 2.00, '草莓山楂雪梨汁', 'p3');
INSERT INTO `order_items` VALUES (75, 48, 2, 1.00, 2, 2.00, '杨桃苹果汁', 'p4');
INSERT INTO `order_items` VALUES (76, 48, 3, 20.00, 2, 40.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (77, 49, 3, 20.00, 1, 20.00, '鲜橙汁', 'p5');
INSERT INTO `order_items` VALUES (78, 49, 4, 16.00, 2, 32.00, '西瓜汁', 'p6');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderId` int(0) NOT NULL AUTO_INCREMENT,
  `userId` int(0) NOT NULL,
  `orderData` date NOT NULL,
  `orderAllPrice` decimal(10, 2) NOT NULL,
  `discountPrice` decimal(10, 2) NOT NULL,
  `orderState` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `orderRemark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `createData` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `finishData` timestamp(0) NULL DEFAULT NULL,
  `shopName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`orderId`) USING BTREE,
  INDEX `userId`(`userId`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, 1, '2024-12-25', 0.00, 0.00, '你好', '0', '2024-12-25 13:48:30', '2024-12-25 13:48:07', 'a');
INSERT INTO `orders` VALUES (2, 1, '2024-12-25', 1.00, 0.00, '派送中', '1', '2024-12-25 18:48:51', NULL, 'a');
INSERT INTO `orders` VALUES (3, 1, '2024-12-25', 1.00, 0.00, '派送中', '1', '2024-12-25 18:49:22', NULL, 'a');
INSERT INTO `orders` VALUES (4, 1, '2024-12-25', 1.00, 0.00, '派送中', '1', '2024-12-25 18:53:35', NULL, 'a');
INSERT INTO `orders` VALUES (5, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:27:08', NULL, NULL);
INSERT INTO `orders` VALUES (6, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:27:09', NULL, NULL);
INSERT INTO `orders` VALUES (7, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:27:10', NULL, NULL);
INSERT INTO `orders` VALUES (8, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:27:10', NULL, NULL);
INSERT INTO `orders` VALUES (9, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:27:10', NULL, NULL);
INSERT INTO `orders` VALUES (10, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:27:10', NULL, NULL);
INSERT INTO `orders` VALUES (11, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:27:10', NULL, NULL);
INSERT INTO `orders` VALUES (12, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:30:41', NULL, NULL);
INSERT INTO `orders` VALUES (13, 1, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 08:39:26', NULL, NULL);
INSERT INTO `orders` VALUES (14, 3, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 09:35:20', NULL, NULL);
INSERT INTO `orders` VALUES (15, 3, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 09:36:10', NULL, NULL);
INSERT INTO `orders` VALUES (16, 3, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 09:38:47', NULL, NULL);
INSERT INTO `orders` VALUES (17, 3, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 09:41:56', NULL, NULL);
INSERT INTO `orders` VALUES (18, 3, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 09:46:57', NULL, NULL);
INSERT INTO `orders` VALUES (19, 3, '2024-12-26', 79.00, 0.00, '未付款', '备注', '2024-12-26 10:09:26', NULL, NULL);
INSERT INTO `orders` VALUES (20, 3, '2024-12-26', 79.00, 0.00, '未付款', '备注', '2024-12-26 10:10:26', NULL, NULL);
INSERT INTO `orders` VALUES (21, 3, '2024-12-26', 0.00, 0.00, '未付款', '备注', '2024-12-26 10:11:52', NULL, NULL);
INSERT INTO `orders` VALUES (22, 3, '2024-12-26', 79.00, 0.00, '未付款', '备注', '2024-12-26 10:11:54', NULL, NULL);
INSERT INTO `orders` VALUES (23, 3, '2024-12-26', 60.00, 0.00, '未付款', '备注', '2024-12-26 10:23:10', NULL, NULL);
INSERT INTO `orders` VALUES (24, 3, '2024-12-26', 96.00, 0.00, '未付款', '备注', '2024-12-26 10:23:52', NULL, NULL);
INSERT INTO `orders` VALUES (25, 3, '2024-12-26', 32.00, 0.00, '未付款', '备注', '2024-12-26 10:26:29', NULL, NULL);
INSERT INTO `orders` VALUES (26, 3, '2024-12-26', 41.00, 0.00, '未付款', '备注', '2024-12-26 10:34:58', NULL, NULL);
INSERT INTO `orders` VALUES (27, 4, '2024-12-26', 60.00, 0.00, '未付款', '备注', '2024-12-26 11:21:40', NULL, NULL);
INSERT INTO `orders` VALUES (29, 5, '2024-12-26', 23.00, 0.00, '未付款', '备注', '2024-12-26 11:48:42', NULL, NULL);
INSERT INTO `orders` VALUES (30, 5, '2024-12-26', 23.00, 0.00, '进行中', '备注', '2024-12-26 11:48:59', NULL, NULL);
INSERT INTO `orders` VALUES (31, 5, '2024-12-26', 71.00, 0.00, '进行中', '备注', '2024-12-26 11:49:20', NULL, NULL);
INSERT INTO `orders` VALUES (32, 5, '2024-12-26', 260.00, 0.00, '进行中', '备注', '2024-12-26 11:49:43', NULL, NULL);
INSERT INTO `orders` VALUES (33, 5, '2024-12-26', 112.00, 0.00, '未付款', '备注', '2024-12-26 11:49:53', NULL, NULL);
INSERT INTO `orders` VALUES (34, 5, '2024-12-26', 197.00, 0.00, '进行中', '备注', '2024-12-26 11:50:08', NULL, NULL);
INSERT INTO `orders` VALUES (35, 5, '2024-12-26', 255.00, 0.00, '未付款', '备注', '2024-12-26 11:51:33', NULL, NULL);
INSERT INTO `orders` VALUES (36, 5, '2024-12-26', 20.00, 0.00, '未付款', '备注', '2024-12-26 11:55:18', NULL, NULL);
INSERT INTO `orders` VALUES (37, 5, '2024-12-26', 85.00, 0.00, '未付款', '备注', '2024-12-26 11:55:22', NULL, NULL);
INSERT INTO `orders` VALUES (38, 5, '2024-12-26', 64.00, 0.00, '未付款', '备注', '2024-12-26 12:11:38', NULL, NULL);
INSERT INTO `orders` VALUES (39, 5, '2024-12-26', 124.00, 0.00, '进行中', '备注', '2024-12-26 12:11:55', NULL, NULL);
INSERT INTO `orders` VALUES (40, 6, '2024-12-26', 77.00, 0.00, '未付款', '备注', '2024-12-26 12:13:40', NULL, NULL);
INSERT INTO `orders` VALUES (41, 6, '2024-12-26', 41.00, 0.00, '进行中', '备注', '2024-12-26 12:13:56', NULL, NULL);
INSERT INTO `orders` VALUES (42, 5, '2024-12-26', 34.00, 0.00, '进行中', '备注', '2024-12-26 12:23:51', NULL, NULL);
INSERT INTO `orders` VALUES (43, 5, '2024-12-26', 88.00, 0.00, '进行中', '备注', '2024-12-26 12:24:01', NULL, NULL);
INSERT INTO `orders` VALUES (44, 5, '2024-12-26', 10.00, 0.00, '进行中', '备注', '2024-12-26 12:33:19', NULL, NULL);
INSERT INTO `orders` VALUES (45, 5, '2024-12-26', 66.00, 0.00, '未付款', '备注', '2024-12-26 12:33:26', NULL, NULL);
INSERT INTO `orders` VALUES (46, 5, '2024-12-26', 32.00, 0.00, '进行中', '备注', '2024-12-26 12:33:42', NULL, NULL);
INSERT INTO `orders` VALUES (47, 7, '2024-12-26', 56.00, 0.00, '未付款', '备注', '2024-12-26 13:58:05', NULL, NULL);
INSERT INTO `orders` VALUES (48, 7, '2024-12-26', 42.00, 0.00, '未付款', '备注', '2024-12-26 14:02:09', NULL, NULL);
INSERT INTO `orders` VALUES (49, 7, '2024-12-26', 52.00, 0.00, '进行中', '备注', '2024-12-26 14:02:35', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int(0) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userAccount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userPassword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userHead` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `userPhoneNumber` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userSex` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `userBalance` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '未命名', 'admin', '123456', 'test', '00000000000', '男', 2200.00);
INSERT INTO `user` VALUES (2, '未命名', '11', '111', 'none', '00000000000', '男', 0.00);
INSERT INTO `user` VALUES (3, '未命名', '1', '1', 'none', '00000000000', '男', 1400.00);
INSERT INTO `user` VALUES (4, '未命名', '123', '123', 'none', '00000000000', '男', 400.00);
INSERT INTO `user` VALUES (5, '未命名', '333', '333', 'none', '00000000000', '男', 501.00);
INSERT INTO `user` VALUES (6, '伟大的陈磊', '999', '999', 'none', '00000000000', '男', 259.00);
INSERT INTO `user` VALUES (7, '磊哥', '789', '789', 'p1', '00000000000', '男', 248.00);

SET FOREIGN_KEY_CHECKS = 1;
