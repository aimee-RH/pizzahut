-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: pizzahut
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `优惠券`
--

DROP TABLE IF EXISTS `优惠券`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `优惠券` (
  `编号` char(20) NOT NULL,
  `种类` enum('n选1','n选2') DEFAULT NULL,
  `金额` decimal(5,2) DEFAULT NULL,
  PRIMARY KEY (`编号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `优惠券`
--

LOCK TABLES `优惠券` WRITE;
/*!40000 ALTER TABLE `优惠券` DISABLE KEYS */;
INSERT INTO `优惠券` VALUES ('1','n选2',99.00),('2','n选2',30.00),('3','n选1',40.00);
/*!40000 ALTER TABLE `优惠券` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `会员等级`
--

DROP TABLE IF EXISTS `会员等级`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `会员等级` (
  `编号` char(20) NOT NULL,
  `名称` enum('普通','VIP','SVIP') NOT NULL,
  `折扣` decimal(3,2) NOT NULL,
  PRIMARY KEY (`编号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `会员等级`
--

LOCK TABLES `会员等级` WRITE;
/*!40000 ALTER TABLE `会员等级` DISABLE KEYS */;
INSERT INTO `会员等级` VALUES ('1','普通',1.00),('2','VIP',0.80),('3','SVIP',0.70);
/*!40000 ALTER TABLE `会员等级` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `商品`
--

DROP TABLE IF EXISTS `商品`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `商品` (
  `编号` char(20) NOT NULL,
  `名称` varchar(20) NOT NULL,
  `种类` enum('披萨','意面','牛排','饮料','沙拉') NOT NULL,
  `原价` decimal(5,2) NOT NULL,
  `描述` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`编号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `商品`
--

LOCK TABLES `商品` WRITE;
/*!40000 ALTER TABLE `商品` DISABLE KEYS */;
INSERT INTO `商品` VALUES ('1','超级至尊披萨','披萨',79.00,'芝士，手工制作，口感丰富'),('10','绚丽水果沙拉','沙拉',19.90,'新鲜清爽'),('11','美式凯撒沙拉','沙拉',30.00,'罗马绿生菜制作'),('12','鸡胸肉沙拉','沙拉',45.00,'\'\'\'\'\'\'\'\''),('13','大大牛排','牛排',89.00,'好吃到爆炸'),('2','意式肉酱牛肉披萨','披萨',59.00,'牛肉，手工制作'),('3','海鲜超级至尊披萨','披萨',79.00,'海鲜，好吃'),('4','麻酱鸡丝意面','意面',19.90,'鲜嫩，清爽'),('5','川香鸡丝意面','意面',29.00,'辣，鲜香入味'),('6','招牌肋眼牛排','牛排',85.00,'嫩中带腴'),('7','甄选西冷牛排','牛排',85.00,'原肉整切而非拼接'),('8','阳光橙橙','饮料',21.00,'浓缩橙汁'),('9','大红袍多肉杨梅','饮料',21.00,'大红袍乌龙茶，杨梅');
/*!40000 ALTER TABLE `商品` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `备份记录`
--

DROP TABLE IF EXISTS `备份记录`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `备份记录` (
  `编号` char(20) NOT NULL,
  `时间` datetime NOT NULL,
  `文件名` varchar(45) NOT NULL,
  PRIMARY KEY (`编号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `备份记录`
--

LOCK TABLES `备份记录` WRITE;
/*!40000 ALTER TABLE `备份记录` DISABLE KEYS */;
INSERT INTO `备份记录` VALUES ('1','2023-10-01 22:54:40','backup1');
/*!40000 ALTER TABLE `备份记录` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `用户`
--

DROP TABLE IF EXISTS `用户`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `用户` (
  `账号` char(20) NOT NULL,
  `姓名` varchar(20) NOT NULL,
  `性别` enum('男','女') NOT NULL,
  `电话` char(11) NOT NULL,
  `会员等级` char(20) NOT NULL,
  `会员期限` date DEFAULT NULL,
  `密码` varchar(30) NOT NULL,
  PRIMARY KEY (`账号`),
  KEY `会员等级` (`会员等级`),
  CONSTRAINT `用户_ibfk_1` FOREIGN KEY (`会员等级`) REFERENCES `会员等级` (`编号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `用户`
--

LOCK TABLES `用户` WRITE;
/*!40000 ALTER TABLE `用户` DISABLE KEYS */;
INSERT INTO `用户` VALUES ('1','Aimee','女','13878895139','3','2024-09-18','250125'),('2','Alice','女','12345670002','1','2024-09-18','000000'),('3','Tom','男','12345670003','3','2024-09-18','000000'),('4','Mike','男','12345670004','2','2024-09-18','000000'),('5','Sophie','女','12345670005','1',NULL,'000000');
/*!40000 ALTER TABLE `用户` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `用户优惠券`
--

DROP TABLE IF EXISTS `用户优惠券`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `用户优惠券` (
  `用户号` char(20) NOT NULL,
  `优惠券号` char(20) NOT NULL,
  `门店号` char(20) NOT NULL,
  PRIMARY KEY (`用户号`,`优惠券号`,`门店号`),
  KEY `优惠券号` (`优惠券号`),
  KEY `门店号` (`门店号`),
  CONSTRAINT `用户优惠券_ibfk_1` FOREIGN KEY (`优惠券号`) REFERENCES `优惠券` (`编号`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `用户优惠券_ibfk_2` FOREIGN KEY (`用户号`) REFERENCES `用户` (`账号`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `用户优惠券_ibfk_3` FOREIGN KEY (`门店号`) REFERENCES `门店` (`账号`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `用户优惠券`
--

LOCK TABLES `用户优惠券` WRITE;
/*!40000 ALTER TABLE `用户优惠券` DISABLE KEYS */;
INSERT INTO `用户优惠券` VALUES ('1','1','1'),('2','1','2'),('4','1','3'),('5','1','4'),('1','2','1'),('2','2','2'),('5','2','3'),('1','3','1'),('1','3','4'),('5','3','1');
/*!40000 ALTER TABLE `用户优惠券` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `用户地址`
--

DROP TABLE IF EXISTS `用户地址`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `用户地址` (
  `用户号` char(20) NOT NULL,
  `地址` varchar(100) NOT NULL,
  PRIMARY KEY (`用户号`,`地址`),
  CONSTRAINT `用户地址_ibfk_1` FOREIGN KEY (`用户号`) REFERENCES `用户` (`账号`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `用户地址`
--

LOCK TABLES `用户地址` WRITE;
/*!40000 ALTER TABLE `用户地址` DISABLE KEYS */;
INSERT INTO `用户地址` VALUES ('1','北京中央民族大学丰台校区'),('1','北京中央民族大学海淀校区'),('1','南宁二中'),('1','广西南宁佛子岭路'),('2','北京魏各庄路'),('3','北京云岗路'),('4','北京魏公村路');
/*!40000 ALTER TABLE `用户地址` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `管理员`
--

DROP TABLE IF EXISTS `管理员`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `管理员` (
  `账号` char(20) NOT NULL,
  `姓名` varchar(20) NOT NULL,
  `密码` varchar(30) NOT NULL,
  PRIMARY KEY (`账号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `管理员`
--

LOCK TABLES `管理员` WRITE;
/*!40000 ALTER TABLE `管理员` DISABLE KEYS */;
INSERT INTO `管理员` VALUES ('1','Josh','021127'),('2','Justina','000000'),('3','Tonny','000000');
/*!40000 ALTER TABLE `管理员` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `订单`
--

DROP TABLE IF EXISTS `订单`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `订单` (
  `编号` char(20) NOT NULL,
  `时间` datetime NOT NULL,
  `用户` char(20) NOT NULL,
  `地址` varchar(100) NOT NULL,
  `门店` char(20) NOT NULL,
  `配送员` char(20) NOT NULL,
  `总金额` decimal(6,2) NOT NULL,
  `配送状态` enum('未配送','配送中','已配送') NOT NULL,
  `备注` varchar(100) NOT NULL,
  `用券` enum('是','否') NOT NULL,
  `特权` enum('是','否') NOT NULL,
  PRIMARY KEY (`编号`),
  KEY `门店` (`门店`),
  KEY `配送员` (`配送员`),
  KEY `用户` (`用户`),
  KEY `时间` (`时间` DESC),
  CONSTRAINT `订单_ibfk_1` FOREIGN KEY (`用户`) REFERENCES `用户` (`账号`),
  CONSTRAINT `订单_ibfk_2` FOREIGN KEY (`门店`) REFERENCES `门店` (`账号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `订单`
--

LOCK TABLES `订单` WRITE;
/*!40000 ALTER TABLE `订单` DISABLE KEYS */;
INSERT INTO `订单` VALUES ('1','2023-09-22 21:51:11','1','北京中央民族大学丰台校区','3','0',79.90,'未配送','无','否','否'),('2','2023-09-22 22:02:42','1','北京中央民族大学海淀校区','3','0',135.83,'未配送','无','否','是'),('3','2023-09-23 09:45:55','1','北京中央民族大学丰台校区','2','1',79.90,'已配送','无','否','否'),('4','2023-09-23 10:23:00','1','北京中央民族大学海淀校区','1','0',19.89,'未配送','无','否','否'),('5','2023-09-23 10:26:28','2','北京魏各庄路','3','0',163.00,'未配送','无','否','否'),('6','2023-09-23 10:32:00','2','北京魏各庄路','2','1',21.00,'已配送','无','否','否'),('7','2023-09-23 10:53:11','3','北京云岗路','3','1',115.93,'已配送','无','否','是'),('8','2023-09-23 10:53:22','3','北京云岗路','3','1',30.00,'已配送','无','否','否');
/*!40000 ALTER TABLE `订单` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `订单商品`
--

DROP TABLE IF EXISTS `订单商品`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `订单商品` (
  `订单号` char(20) NOT NULL,
  `商品号` char(20) NOT NULL,
  `数量` smallint NOT NULL,
  `金额` decimal(5,2) NOT NULL,
  PRIMARY KEY (`订单号`,`商品号`),
  CONSTRAINT `订单号` FOREIGN KEY (`订单号`) REFERENCES `订单` (`编号`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `订单商品`
--

LOCK TABLES `订单商品` WRITE;
/*!40000 ALTER TABLE `订单商品` DISABLE KEYS */;
INSERT INTO `订单商品` VALUES ('1','1',1,79.90),('2','1',2,135.83),('3','1',1,79.90),('4','10',1,19.89),('5','12',1,45.00),('5','2',2,118.00),('6','9',1,21.00),('7','1',1,55.93),('7','11',2,60.00),('8','11',1,30.00);
/*!40000 ALTER TABLE `订单商品` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `配送员`
--

DROP TABLE IF EXISTS `配送员`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `配送员` (
  `账号` char(20) NOT NULL,
  `姓名` varchar(20) NOT NULL,
  `性别` enum('男','女') NOT NULL,
  `电话` char(11) NOT NULL,
  `密码` varchar(30) NOT NULL,
  PRIMARY KEY (`账号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `配送员`
--

LOCK TABLES `配送员` WRITE;
/*!40000 ALTER TABLE `配送员` DISABLE KEYS */;
INSERT INTO `配送员` VALUES ('0','暂未分配','男','00000000000','000000'),('1','Jackey','男','12345600001','000000'),('2','Betty','女','12345600002','000000'),('3','Hunter','男','12345600003','000000');
/*!40000 ALTER TABLE `配送员` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `门店`
--

DROP TABLE IF EXISTS `门店`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `门店` (
  `账号` char(20) NOT NULL,
  `名称` char(20) NOT NULL,
  `地址` varchar(100) NOT NULL,
  `电话` char(11) NOT NULL,
  `上班时间` time NOT NULL,
  `下班时间` time NOT NULL,
  `密码` varchar(30) NOT NULL,
  PRIMARY KEY (`账号`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `门店`
--

LOCK TABLES `门店` WRITE;
/*!40000 ALTER TABLE `门店` DISABLE KEYS */;
INSERT INTO `门店` VALUES ('1','魏各庄分店','魏各庄路','12345678001','07:17:41','16:17:41','111111'),('2','云岗分店','云岗路20号','12345678002','07:30:00','20:00:00','000000'),('3','魏公村分店','魏公村路30号','12345678003','09:00:00','23:00:00','000000'),('4','万寿寺分店','万寿寺路','12345678004','00:00:00','23:59:59','000000');
/*!40000 ALTER TABLE `门店` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `门店商品`
--

DROP TABLE IF EXISTS `门店商品`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `门店商品` (
  `门店号` char(20) NOT NULL,
  `商品号` char(20) NOT NULL,
  PRIMARY KEY (`门店号`,`商品号`),
  KEY `商品_idx` (`商品号`),
  CONSTRAINT `商品` FOREIGN KEY (`商品号`) REFERENCES `商品` (`编号`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `门店` FOREIGN KEY (`门店号`) REFERENCES `门店` (`账号`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `门店商品`
--

LOCK TABLES `门店商品` WRITE;
/*!40000 ALTER TABLE `门店商品` DISABLE KEYS */;
INSERT INTO `门店商品` VALUES ('2','1'),('3','1'),('4','1'),('2','10'),('3','10'),('1','11'),('2','11'),('3','11'),('4','11'),('1','12'),('2','12'),('3','12'),('4','12'),('1','2'),('2','2'),('3','2'),('4','2'),('1','3'),('2','3'),('4','3'),('1','4'),('2','4'),('3','4'),('4','4'),('1','5'),('2','5'),('3','5'),('4','5'),('1','6'),('3','6'),('4','6'),('1','7'),('2','7'),('3','7'),('4','7'),('2','8'),('3','8'),('4','8'),('1','9'),('2','9'),('3','9'),('4','9');
/*!40000 ALTER TABLE `门店商品` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-01 22:54:41
