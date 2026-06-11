-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: viemp3_db
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `albums`
--

DROP TABLE IF EXISTS `albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `albums` (
  `id` varchar(255) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `artist_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `favorites` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72gqyi6l1j674radjyitcm86f` (`artist_id`),
  CONSTRAINT `FK72gqyi6l1j674radjyitcm86f` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `albums`
--

LOCK TABLES `albums` WRITE;
/*!40000 ALTER TABLE `albums` DISABLE KEYS */;
INSERT INTO `albums` VALUES ('201a05e3-3cda-4850-856a-5758a423e221','https://res.cloudinary.com/drlhghtqx/image/upload/v1773795911/albums/164b5f1e-a679-4623-8818-89b286390a1e.jpg','Đom Đóm','b46c5832-da9b-4c76-b173-44df88de0fd5','2026-03-18 01:05:26.269383',1),('357ed8df-f2fe-4ee0-b465-975eb749a934','https://res.cloudinary.com/drlhghtqx/image/upload/v1773890539/albums/22a56c28-7911-44ef-9412-cd3ff6f6f2c9.jpg','THE WXRDIES','a6dd2e8c-1aa0-4545-87e1-b0f7a71cb1ef','2026-03-19 03:22:20.894201',2),('49e937a5-217c-45c3-bccc-9c955cb8bbcb','https://res.cloudinary.com/drlhghtqx/image/upload/v1772539907/albums/8f4fb9ab-ddf8-4efe-8304-e3231db17f6a.jpg','Sky','fe9a1409-4f94-4246-902e-2e1ce22354a1','2026-03-03 12:11:48.783930',0),('89566bc1-2c5c-487a-924e-f44070b8d4e4','https://res.cloudinary.com/drlhghtqx/image/upload/v1773890665/albums/59258a09-3806-40ae-b4fa-13df488aea84.jpg','RPT MCK','c8ffdb22-4b48-42b3-8671-2d633fb3aff0','2026-03-19 03:24:27.045109',0);
/*!40000 ALTER TABLE `albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `artists`
--

DROP TABLE IF EXISTS `artists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artists` (
  `id` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `favorites` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `artists`
--

LOCK TABLES `artists` WRITE;
/*!40000 ALTER TABLE `artists` DISABLE KEYS */;
INSERT INTO `artists` VALUES ('404db06e-319f-4acc-bdda-53f623d1f821','https://res.cloudinary.com/drlhghtqx/image/upload/v1775103027/artists/63ad9ba9-dca6-4259-ba30-d4461010f9f6.jpg','Nguyễn Thạc Bảo Ngọc','2026-04-02 04:10:28.347795',100001),('9bcd1c5a-b0be-4f10-b250-8e153e4cf864','https://res.cloudinary.com/drlhghtqx/image/upload/v1773890110/artists/7886dd80-b375-4570-8cf9-d70424dc8fe1.jpg','QNT','2026-03-19 03:15:11.712723',100000),('a6dd2e8c-1aa0-4545-87e1-b0f7a71cb1ef','https://res.cloudinary.com/drlhghtqx/image/upload/v1773890069/artists/201e505e-3b01-439e-97c8-6f0b062d0244.jpg','Wxrdie','2026-03-19 03:14:31.237954',100001),('b46c5832-da9b-4c76-b173-44df88de0fd5','https://res.cloudinary.com/drlhghtqx/image/upload/v1773719627/artists/aafe2303-6ad6-41df-a0c2-b86b8113c631.jpg','Jack - J97','2026-03-17 03:53:50.025643',100000),('ba427ec4-34f0-4507-9af6-022752f67a6e','https://res.cloudinary.com/drlhghtqx/image/upload/v1773890317/artists/e22d9ddf-97cf-475c-b9dd-9b69a79991f5.jpg','SOOBIN - Hoàng Sơn','2026-03-19 03:18:39.383311',100000),('c8ffdb22-4b48-42b3-8671-2d633fb3aff0','https://res.cloudinary.com/drlhghtqx/image/upload/v1773890238/artists/f9fbf6b0-7468-4c1f-9d66-33cbd6d90d40.jpg','MCK','2026-03-19 03:17:20.654264',100000),('fe9a1409-4f94-4246-902e-2e1ce22354a1','https://res.cloudinary.com/drlhghtqx/image/upload/v1772528088/artists/5e3aee0e-c031-4f51-82a7-c4e58ded036a.webp','Sơn Tùng - MTP','2026-03-03 03:06:31.739971',100002);
/*!40000 ALTER TABLE `artists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_albums`
--

DROP TABLE IF EXISTS `favorite_albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_albums` (
  `id` varchar(255) NOT NULL,
  `favorited_at` datetime(6) NOT NULL,
  `album_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm9upu58e9mcjmg2ecrfx9ym8b` (`album_id`),
  KEY `FKg5k7wwr1t5i8lrb6hohjlyqfg` (`user_id`),
  CONSTRAINT `FKg5k7wwr1t5i8lrb6hohjlyqfg` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKm9upu58e9mcjmg2ecrfx9ym8b` FOREIGN KEY (`album_id`) REFERENCES `albums` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_albums`
--

LOCK TABLES `favorite_albums` WRITE;
/*!40000 ALTER TABLE `favorite_albums` DISABLE KEYS */;
INSERT INTO `favorite_albums` VALUES ('054b7cf1-d906-46ec-9f76-f11c1471d4a9','2026-06-03 09:22:33.175589','357ed8df-f2fe-4ee0-b465-975eb749a934','74845b80-ed4c-4999-8ea0-94e8386a954e'),('1cddbb60-2a2a-4fce-80a8-dff7e7b80cab','2026-05-27 07:09:39.472253','357ed8df-f2fe-4ee0-b465-975eb749a934','66eef44d-d1e1-4547-907d-f9082bf7774c'),('edb62cfb-e3eb-47dc-ad07-0dc4357773dc','2026-05-18 14:30:38.775826','201a05e3-3cda-4850-856a-5758a423e221','74845b80-ed4c-4999-8ea0-94e8386a954e');
/*!40000 ALTER TABLE `favorite_albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_artists`
--

DROP TABLE IF EXISTS `favorite_artists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_artists` (
  `id` varchar(255) NOT NULL,
  `favorited_at` datetime(6) NOT NULL,
  `artist_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtqcw6ncoullp7mu4122g162di` (`artist_id`),
  KEY `FK465uufp4xvhlruxqta54mfa39` (`user_id`),
  CONSTRAINT `FK465uufp4xvhlruxqta54mfa39` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKtqcw6ncoullp7mu4122g162di` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_artists`
--

LOCK TABLES `favorite_artists` WRITE;
/*!40000 ALTER TABLE `favorite_artists` DISABLE KEYS */;
INSERT INTO `favorite_artists` VALUES ('065bd5e7-9bff-42cf-beb4-328991d0f26d','2026-03-05 01:46:17.844937','fe9a1409-4f94-4246-902e-2e1ce22354a1','74845b80-ed4c-4999-8ea0-94e8386a954e'),('3281fefc-9e7a-4a8c-bf7b-fbab78dc8710','2026-05-20 11:54:11.390398','404db06e-319f-4acc-bdda-53f623d1f821','74845b80-ed4c-4999-8ea0-94e8386a954e'),('8f14a3b9-c298-403e-a817-c2290f6ce0db','2026-05-20 11:54:23.284515','a6dd2e8c-1aa0-4545-87e1-b0f7a71cb1ef','74845b80-ed4c-4999-8ea0-94e8386a954e'),('d69217af-3199-4db7-97af-74246be4346a','2026-05-27 06:59:26.564694','fe9a1409-4f94-4246-902e-2e1ce22354a1','66eef44d-d1e1-4547-907d-f9082bf7774c');
/*!40000 ALTER TABLE `favorite_artists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_songs`
--

DROP TABLE IF EXISTS `favorite_songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_songs` (
  `id` varchar(255) NOT NULL,
  `favorited_at` datetime(6) NOT NULL,
  `song_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdixpgug10y626uql1erp9w8qm` (`song_id`),
  KEY `FKkeya6rbfrit3fdy766h4nhqfv` (`user_id`),
  CONSTRAINT `FKdixpgug10y626uql1erp9w8qm` FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`),
  CONSTRAINT `FKkeya6rbfrit3fdy766h4nhqfv` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_songs`
--

LOCK TABLES `favorite_songs` WRITE;
/*!40000 ALTER TABLE `favorite_songs` DISABLE KEYS */;
INSERT INTO `favorite_songs` VALUES ('32dabab9-61d3-4d68-8bc6-f935e60a2bce','2026-05-20 11:54:53.089540','8e305981-eb7c-466a-bbe6-f1713ab8ca97','74845b80-ed4c-4999-8ea0-94e8386a954e'),('35e4b639-7539-486c-9d07-21a439c4ebe4','2026-05-13 12:22:39.387532','b669a47c-3992-4148-b94d-63df0267e1ff','74845b80-ed4c-4999-8ea0-94e8386a954e'),('3a3d6820-86bd-41af-a384-14ffe29f21f3','2026-05-28 01:14:57.171360','2071a9c3-5b23-4743-bbcf-fc7cab53285d','66eef44d-d1e1-4547-907d-f9082bf7774c'),('7127910b-34e1-4c40-ac7b-c1f12c33fa31','2026-04-21 09:35:20.572334','b669a47c-3992-4148-b94d-63df0267e1ff','75975503-3c9a-42e6-8d0a-f01f17fe2f44'),('a91b0525-4069-445f-b0bc-d2cb7ab333ba','2026-03-26 15:28:49.582669','34c5410a-601d-40ba-a048-317c8b6879be','74845b80-ed4c-4999-8ea0-94e8386a954e');
/*!40000 ALTER TABLE `favorite_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genres` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpe1a9woik1k97l87cieguyhh4` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES ('47cd9c9e-5eb7-45fa-9a10-69c10fd10091','ACOUSTIC'),('718cec45-aad0-4101-89ba-16c671845f14','BALLAD'),('db66d3bd-006c-45f6-ae1c-01de9c8a6339','DANCE'),('5d266a6c-e375-4268-a63c-c4c3131f588f','FUNK'),('e263a2f6-eb41-4f58-bf93-001b69bb1e11','HIPHOP'),('69c2560a-5262-4dec-b9de-3de60dc6eff7','INDIE'),('cc0d19cc-b395-4960-9f1a-7146ad6ad16f','LOFI'),('a12eaec3-56cb-4c78-a131-600012fad5b4','POP'),('0335a443-93b2-4683-a1f4-ec3a555ec63f','RAP'),('7623ebc4-1cf5-4303-98fa-18567be09a9a','REMIX'),('d6dfed87-c187-40c5-bf15-7e4684da0b62','RNB');
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listen_history`
--

DROP TABLE IF EXISTS `listen_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `listen_history` (
  `id` varchar(255) NOT NULL,
  `listened_at` datetime(6) DEFAULT NULL,
  `song_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK213eie0aocewwrfq8kr5odxnx` (`song_id`),
  KEY `FKbvjojqmmexfoyfadsqwup4ps3` (`user_id`),
  CONSTRAINT `FK213eie0aocewwrfq8kr5odxnx` FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`),
  CONSTRAINT `FKbvjojqmmexfoyfadsqwup4ps3` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listen_history`
--

LOCK TABLES `listen_history` WRITE;
/*!40000 ALTER TABLE `listen_history` DISABLE KEYS */;
INSERT INTO `listen_history` VALUES ('018b5ea8-4694-4cbb-80cb-118f947d15d8','2026-03-18 08:20:43.206915','34da4954-0044-4f38-a55f-35746b97007f','74845b80-ed4c-4999-8ea0-94e8386a954e'),('0269e070-9fa5-44c7-ab8e-121bfd425c53','2026-03-11 10:19:54.060690','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('041eb6bd-409b-4b13-a62c-b3406c2e55ef','2026-03-16 09:28:15.860427','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('0c982bc6-4e91-48cd-8475-1fd5e6d5020a','2026-03-18 08:21:01.269108','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('11831b23-0e91-41e0-92ad-b7d8f5801063','2026-03-26 04:56:01.629366','0b663f77-f322-44ce-8df2-b203c262f5e5','74845b80-ed4c-4999-8ea0-94e8386a954e'),('1e19d2f4-6354-42f9-8edc-8dc2d8c54b1a','2026-05-09 02:15:07.005902','0b663f77-f322-44ce-8df2-b203c262f5e5','75975503-3c9a-42e6-8d0a-f01f17fe2f44'),('203212bc-3608-454d-bc5c-3b436f60b03c','2026-04-04 01:33:43.170873','b669a47c-3992-4148-b94d-63df0267e1ff','75975503-3c9a-42e6-8d0a-f01f17fe2f44'),('2097839c-e7fa-4476-a09e-1df64c363e66','2026-03-11 13:52:09.620513','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','66eef44d-d1e1-4547-907d-f9082bf7774c'),('23198ac7-37d3-42d6-a630-f2ddb8cac084','2026-03-11 13:52:24.220106','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('23f699b8-d7db-4c4d-b49f-d38a8dfdb721','2026-03-16 09:28:52.226463','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('29c33d6c-4e1d-42e3-8f8e-2250b46a32f4','2026-04-02 04:14:58.896679','b669a47c-3992-4148-b94d-63df0267e1ff','74845b80-ed4c-4999-8ea0-94e8386a954e'),('29f7d1c2-1241-4d0f-acff-3cacc2ddad13','2026-03-11 08:53:20.290829','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','66eef44d-d1e1-4547-907d-f9082bf7774c'),('3dca9e9b-82b2-4e5f-a5ac-a2574a993deb','2026-03-18 08:41:40.581327','34da4954-0044-4f38-a55f-35746b97007f','74845b80-ed4c-4999-8ea0-94e8386a954e'),('3dcfef35-47ef-4110-a61a-585e29fb824b','2026-03-16 08:01:26.136533','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('49682b48-04dd-4e6e-91c0-6afea61f6e89','2026-05-09 02:13:33.134542','0cff5749-ed4b-447f-9371-978ee49ee097','75975503-3c9a-42e6-8d0a-f01f17fe2f44'),('4b0e617f-4cb3-4548-a93c-9b409a058c97','2026-03-11 13:22:22.923078','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('4fca0c70-bb1b-4176-8032-552a32d1ae2c','2026-03-11 08:35:26.052811','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('539161ad-71d2-4de0-8d3f-6e20dc98972b','2026-05-09 02:14:06.004183','2071a9c3-5b23-4743-bbcf-fc7cab53285d','75975503-3c9a-42e6-8d0a-f01f17fe2f44'),('56de40c5-4f29-4dae-96cb-07261a6f90c0','2026-03-11 13:52:03.812778','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','66eef44d-d1e1-4547-907d-f9082bf7774c'),('5a1a6651-0c75-4bf4-8b68-79395e1995aa','2026-05-07 07:40:27.234196','2071a9c3-5b23-4743-bbcf-fc7cab53285d','74845b80-ed4c-4999-8ea0-94e8386a954e'),('65178993-0699-44b7-b784-ce2c8b9fae6a','2026-05-11 08:01:43.350891','0b663f77-f322-44ce-8df2-b203c262f5e5','66eef44d-d1e1-4547-907d-f9082bf7774c'),('665c672a-67e5-4ee6-b965-963fda929619','2026-05-27 07:37:52.439982','2071a9c3-5b23-4743-bbcf-fc7cab53285d','66eef44d-d1e1-4547-907d-f9082bf7774c'),('669f63e7-1b33-40e9-b777-2e847aca9d0b','2026-03-11 08:34:54.957162','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('66ed9803-ada5-4e70-be24-91dbe676e312','2026-03-26 03:55:52.886193','8e305981-eb7c-466a-bbe6-f1713ab8ca97','74845b80-ed4c-4999-8ea0-94e8386a954e'),('70762b17-7c14-495e-8fc6-a39fa876f890','2026-03-11 08:34:54.979246','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('717d7a52-0003-4f7a-96b4-cc5fb975d2cf','2026-05-11 08:02:40.930966','0cff5749-ed4b-447f-9371-978ee49ee097','66eef44d-d1e1-4547-907d-f9082bf7774c'),('78e3e546-6ac5-4054-959d-f27af1fe7bdd','2026-03-18 08:41:53.923843','34da4954-0044-4f38-a55f-35746b97007f','74845b80-ed4c-4999-8ea0-94e8386a954e'),('9ac042ce-bb43-48c0-8953-61985816a8e5','2026-03-18 08:21:25.209140','34da4954-0044-4f38-a55f-35746b97007f','74845b80-ed4c-4999-8ea0-94e8386a954e'),('a2cc5a48-bdfe-4407-8e4d-99fd4bc1591f','2026-03-19 12:50:28.788915','0cff5749-ed4b-447f-9371-978ee49ee097','74845b80-ed4c-4999-8ea0-94e8386a954e'),('a8d01e24-e79b-45c4-a01d-741b5f502a1c','2026-03-11 08:53:52.611093','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','66eef44d-d1e1-4547-907d-f9082bf7774c'),('aa8a82da-fd13-409b-ba2e-8bb85f20a197','2026-03-18 08:05:43.886905','ad15ec43-13d4-4792-92a8-d624bd9e4e02','74845b80-ed4c-4999-8ea0-94e8386a954e'),('aba37608-c425-49d0-9bba-7eabe15f01ae','2026-03-18 08:06:51.473474','ad15ec43-13d4-4792-92a8-d624bd9e4e02','66eef44d-d1e1-4547-907d-f9082bf7774c'),('acd3f1aa-1206-4c00-b434-d575d94a1878','2026-03-11 08:35:26.028944','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('b1f16d2b-e153-4a5b-951d-32ddc8035701','2026-03-11 08:35:59.926088','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('b8f7ec62-159a-4594-833b-beed76af77be','2026-03-18 01:29:40.589828','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('bbe42b29-0e80-4c17-ad73-f80accf0f475','2026-03-16 08:03:24.403057','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('d0cfb469-ba46-4990-9116-f8018a673629','2026-03-26 04:57:37.678527','34c5410a-601d-40ba-a048-317c8b6879be','74845b80-ed4c-4999-8ea0-94e8386a954e'),('e74d6daa-3135-4193-8be4-de542bd08c49','2026-03-18 08:07:38.059008','ad15ec43-13d4-4792-92a8-d624bd9e4e02','75975503-3c9a-42e6-8d0a-f01f17fe2f44'),('e9322f12-977c-4727-93df-9dcadb0dc0d3','2026-03-18 08:43:42.989160','34da4954-0044-4f38-a55f-35746b97007f','74845b80-ed4c-4999-8ea0-94e8386a954e'),('eba6a9a0-5cd4-48fd-ad5f-1cd9a4e6becb','2026-03-26 16:16:47.510646','5bd507da-8996-4572-b52d-fd4e2ee34873','74845b80-ed4c-4999-8ea0-94e8386a954e'),('ef3577a8-1354-4728-a0a8-3e9521327cfc','2026-03-11 13:42:55.007761','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('f61e546e-5e76-4ae3-9652-af84c1bea0ed','2026-03-26 03:18:27.003867','b165fa93-a8e3-4f65-986f-a8e678f1d4f2','74845b80-ed4c-4999-8ea0-94e8386a954e'),('f749ed7e-248b-47a1-b67e-80e57accd61f','2026-03-11 08:35:59.966843','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','74845b80-ed4c-4999-8ea0-94e8386a954e'),('fce36b4a-29c1-4c52-baa9-30c3ea3563e6','2026-03-11 08:53:49.136130','e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','66eef44d-d1e1-4547-907d-f9082bf7774c');
/*!40000 ALTER TABLE `listen_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` varchar(255) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `is_read` bit(1) NOT NULL,
  `notification_at` datetime(6) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`),
  CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES ('3e445786-74cf-475c-9cf8-07e3ed97185e','https://res.cloudinary.com/drlhghtqx/image/upload/v1773821473/songs/covers/574322af-8d74-45cf-82fa-6f49abd3ea26.jpg',_binary '\0','2026-03-18 08:11:21.536768','Sơn Tùng - MTP vừa ra mắt bài hát \"Chúng Ta Không Thuộc Về Nhau\"','74845b80-ed4c-4999-8ea0-94e8386a954e'),('47fe9807-4bfc-4281-adaf-6c43c8f0cad3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893498/songs/covers/e3c93de9-de1b-4e4b-b556-8882259f5621.jpg',_binary '\0','2026-03-19 04:11:46.706201','Sơn Tùng - MTP vừa ra mắt bài hát \"Nơi Này Có Anh\"','74845b80-ed4c-4999-8ea0-94e8386a954e'),('5e8b6dde-899b-49b4-bb83-4d851e28396d','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893325/songs/covers/dcace8ab-2697-4279-824d-443d1827434b.jpg',_binary '\0','2026-03-19 04:08:56.571675','Sơn Tùng - MTP vừa ra mắt bài hát \"Muộn Rồi Mà Sao Còn\"','74845b80-ed4c-4999-8ea0-94e8386a954e'),('a6275daf-3e94-45fa-8a85-25a069dbf6b9','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893404/songs/covers/8022125a-65c5-4484-9e9d-c0f6481cb4f0.jpg',_binary '\0','2026-03-19 04:10:17.234102','Sơn Tùng - MTP vừa ra mắt bài hát \"Lạc Trôi\"','74845b80-ed4c-4999-8ea0-94e8386a954e');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` varchar(255) NOT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `status` enum('COMPLETED','FAILED','PENDING') DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `vnp_txn_ref` varchar(255) DEFAULT NULL,
  `package_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `voucher_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6kb9u6u1oyil56npvm02vsvoo` (`package_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  KEY `FKdimvsocblb17f45ikjr6xn1wj` (`voucher_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK6kb9u6u1oyil56npvm02vsvoo` FOREIGN KEY (`package_id`) REFERENCES `packages` (`id`),
  CONSTRAINT `FKdimvsocblb17f45ikjr6xn1wj` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES ('3205b53b-f016-462a-a0b3-2fe983d8304b','2026-05-23 02:49:10.773692','2026-04-23 02:49:10.773692','COMPLETED',20000,'17989534','2b9c336a-3f46-4b54-a9d1-34958e78c53f','74845b80-ed4c-4999-8ea0-94e8386a954e',NULL),('370639b6-5d82-4c3e-80cf-5854bd080830','2026-05-23 08:53:14.871439','2026-04-23 08:53:14.871439','COMPLETED',10000,'65076293','3f31c4d9-f453-4ab4-987c-e820500495cf','66eef44d-d1e1-4547-907d-f9082bf7774c',NULL),('8a290b35-b58b-4156-9866-4fa845d2eefa',NULL,'2026-04-22 16:29:05.995856','PENDING',20000,'88495175','2b9c336a-3f46-4b54-a9d1-34958e78c53f','74845b80-ed4c-4999-8ea0-94e8386a954e',NULL),('99f9ef47-6a06-44b6-a9f6-4527e9f693ce','2026-08-29 09:03:00.819113','2026-05-29 09:03:00.818114','COMPLETED',24300,'51304883','b2f56ef6-ba9e-4900-beb3-753593941bb6','66eef44d-d1e1-4547-907d-f9082bf7774c','e6e46812-e486-4246-997e-c810cb356362'),('bd79c779-7ace-4360-ad43-3e5bc5cb4eac','2026-05-22 16:35:12.421327','2026-04-22 16:35:12.421327','COMPLETED',20000,'03727269','2b9c336a-3f46-4b54-a9d1-34958e78c53f','74845b80-ed4c-4999-8ea0-94e8386a954e',NULL),('dfad422a-d3cd-42d6-916b-5480f04148c3','2026-07-23 09:49:22.098024','2026-04-23 09:49:22.098024','COMPLETED',24300,'36157435','b2f56ef6-ba9e-4900-beb3-753593941bb6','66eef44d-d1e1-4547-907d-f9082bf7774c','e6e46812-e486-4246-997e-c810cb356362'),('eba5f408-fc7f-423f-b246-da5f193ca204',NULL,'2026-04-23 08:51:41.666041','FAILED',9000,'08325693','3f31c4d9-f453-4ab4-987c-e820500495cf','66eef44d-d1e1-4547-907d-f9082bf7774c','e6e46812-e486-4246-997e-c810cb356362'),('edd1c342-4c73-426f-8faf-807402e6e80d','2026-05-23 03:06:19.356096','2026-04-23 03:06:19.355098','COMPLETED',20000,'71877818','2b9c336a-3f46-4b54-a9d1-34958e78c53f','74845b80-ed4c-4999-8ea0-94e8386a954e',NULL),('f7188812-13e8-4c0d-bfeb-21ec42ceeceb','2026-06-11 08:01:08.489850','2026-05-11 08:01:08.489850','COMPLETED',10000,'73026634','3f31c4d9-f453-4ab4-987c-e820500495cf','66eef44d-d1e1-4547-907d-f9082bf7774c',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `packages`
--

DROP TABLE IF EXISTS `packages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packages` (
  `id` varchar(255) NOT NULL,
  `base_price` double DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `discount_percent` double DEFAULT NULL,
  `duration` enum('ONE_MONTH','SIX_MONTHS','THREE_MONTHS') DEFAULT NULL,
  `final_price` double DEFAULT NULL,
  `pkg` enum('INDIVIDUAL','STUDENT') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `packages`
--

LOCK TABLES `packages` WRITE;
/*!40000 ALTER TABLE `packages` DISABLE KEYS */;
INSERT INTO `packages` VALUES ('00793f44-e738-44f0-8a29-29e0035dbd00',10000,'2026-04-17 11:39:01.516927',30,'SIX_MONTHS',42000,'STUDENT'),('2b9c336a-3f46-4b54-a9d1-34958e78c53f',20000,'2026-04-17 11:37:58.287640',0,'ONE_MONTH',20000,'INDIVIDUAL'),('3f31c4d9-f453-4ab4-987c-e820500495cf',10000,'2026-04-17 11:38:43.180965',0,'ONE_MONTH',10000,'STUDENT'),('68225ad5-cf56-44ca-bbc8-72d4fb2ef0a9',20000,'2026-04-17 11:38:18.353802',10,'THREE_MONTHS',54000,'INDIVIDUAL'),('b2f56ef6-ba9e-4900-beb3-753593941bb6',10000,'2026-04-17 11:38:52.182043',10,'THREE_MONTHS',27000,'STUDENT'),('ba8137d4-c9f1-4e41-be98-3ecd2c2f5741',20000,'2026-04-17 11:38:27.049225',25,'SIX_MONTHS',90000,'INDIVIDUAL');
/*!40000 ALTER TABLE `packages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlist_songs`
--

DROP TABLE IF EXISTS `playlist_songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlist_songs` (
  `playlist_id` varchar(255) NOT NULL,
  `song_id` varchar(255) NOT NULL,
  KEY `FK5xu79gpgpc1p4tku7j6dv2skb` (`song_id`),
  KEY `FKqfutupgj870d2k31ldxqqwr8w` (`playlist_id`),
  CONSTRAINT `FK5xu79gpgpc1p4tku7j6dv2skb` FOREIGN KEY (`song_id`) REFERENCES `songs` (`id`),
  CONSTRAINT `FKqfutupgj870d2k31ldxqqwr8w` FOREIGN KEY (`playlist_id`) REFERENCES `playlists` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlist_songs`
--

LOCK TABLES `playlist_songs` WRITE;
/*!40000 ALTER TABLE `playlist_songs` DISABLE KEYS */;
INSERT INTO `playlist_songs` VALUES ('bbb7af8c-baa8-4804-a68a-5c3982e1b82b','b669a47c-3992-4148-b94d-63df0267e1ff'),('bbb7af8c-baa8-4804-a68a-5c3982e1b82b','8e305981-eb7c-466a-bbe6-f1713ab8ca97');
/*!40000 ALTER TABLE `playlist_songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playlists`
--

DROP TABLE IF EXISTS `playlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playlists` (
  `id` varchar(255) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtgjwvfg23v990xk7k0idmqbrj` (`user_id`),
  CONSTRAINT `FKtgjwvfg23v990xk7k0idmqbrj` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playlists`
--

LOCK TABLES `playlists` WRITE;
/*!40000 ALTER TABLE `playlists` DISABLE KEYS */;
INSERT INTO `playlists` VALUES ('0b97aa05-43b6-4129-b9b4-8df6ecc618e6',NULL,'gym','74845b80-ed4c-4999-8ea0-94e8386a954e','2026-03-09 08:15:46.621821'),('bbb7af8c-baa8-4804-a68a-5c3982e1b82b',NULL,'Chill','74845b80-ed4c-4999-8ea0-94e8386a954e','2026-03-09 09:23:14.269605'),('bc49dd60-ad7b-4181-bc85-14d2b0d73946',NULL,'Gojo','74845b80-ed4c-4999-8ea0-94e8386a954e','2026-03-09 09:24:40.190482');
/*!40000 ALTER TABLE `playlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_tokens`
--

DROP TABLE IF EXISTS `refresh_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_tokens` (
  `id` varchar(255) NOT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1lih5y2npsf8u5o3vhdb9y0os` (`user_id`),
  CONSTRAINT `FK1lih5y2npsf8u5o3vhdb9y0os` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_tokens`
--

LOCK TABLES `refresh_tokens` WRITE;
/*!40000 ALTER TABLE `refresh_tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `refresh_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `revenues`
--

DROP TABLE IF EXISTS `revenues`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `revenues` (
  `id` varchar(255) NOT NULL,
  `amount` double DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `subscription_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9sr721j8ld4h6d8s9cif8hlq3` (`subscription_id`),
  CONSTRAINT `FK9sr721j8ld4h6d8s9cif8hlq3` FOREIGN KEY (`subscription_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKg2upvhgwdy6uwjhkb091do8c1` FOREIGN KEY (`subscription_id`) REFERENCES `subscriptions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `revenues`
--

LOCK TABLES `revenues` WRITE;
/*!40000 ALTER TABLE `revenues` DISABLE KEYS */;
/*!40000 ALTER TABLE `revenues` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` varchar(255) NOT NULL,
  `name` enum('ADMIN','MOD','PREMIUM','USER') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKofx66keruapi6vyqpv6f2or37` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('98766c2d-0a61-4503-8270-784d367a0b14','ADMIN'),('e9409c40-d764-4f11-b75f-e4dedd573535','MOD'),('68d527f8-0c3e-4037-96ce-90e299cdf254','PREMIUM'),('67e945bd-189d-43ee-8492-10e10d57ae21','USER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `songs`
--

DROP TABLE IF EXISTS `songs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `songs` (
  `id` varchar(255) NOT NULL,
  `audio` varchar(255) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `album_id` varchar(255) DEFAULT NULL,
  `artist_id` varchar(255) DEFAULT NULL,
  `genre_id` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) NOT NULL,
  `favorites` int NOT NULL,
  `listen_count` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKte4gkb2cqtk2erfa87oopj2cj` (`album_id`),
  KEY `FKdjq2ujqovw5rc14q60f8p6b6e` (`artist_id`),
  KEY `FKd5mor9lg3wkqhn2tp0r75nkm` (`genre_id`),
  CONSTRAINT `FKd5mor9lg3wkqhn2tp0r75nkm` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
  CONSTRAINT `FKdjq2ujqovw5rc14q60f8p6b6e` FOREIGN KEY (`artist_id`) REFERENCES `artists` (`id`),
  CONSTRAINT `FKte4gkb2cqtk2erfa87oopj2cj` FOREIGN KEY (`album_id`) REFERENCES `albums` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `songs`
--

LOCK TABLES `songs` WRITE;
/*!40000 ALTER TABLE `songs` DISABLE KEYS */;
INSERT INTO `songs` VALUES ('0b663f77-f322-44ce-8df2-b203c262f5e5','https://res.cloudinary.com/drlhghtqx/video/upload/v1773893850/songs/audios/1f04df15-52bd-4850-a979-0c58f360ed53.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893844/songs/covers/26940693-9688-4ef5-a539-735dc1af695f.png','RPT MCK - TẠI VÌ SAO | Official Music Video','Tại Vì Sao','89566bc1-2c5c-487a-924e-f44070b8d4e4','c8ffdb22-4b48-42b3-8671-2d633fb3aff0','d6dfed87-c187-40c5-bf15-7e4684da0b62','2026-03-19 04:17:32.634432',0,12),('0cff5749-ed4b-447f-9371-978ee49ee097','https://res.cloudinary.com/drlhghtqx/video/upload/v1773892948/songs/audios/87c58014-bec1-4a8a-8b81-405c830bc882.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773892941/songs/covers/a4735a64-d41e-4133-811e-473ff1ab125e.png','SOOBIN, tlinh - Ai Mà Biết Được (ft. Touliver)','Ai Mà Biết Được',NULL,'ba427ec4-34f0-4507-9af6-022752f67a6e','d6dfed87-c187-40c5-bf15-7e4684da0b62','2026-03-19 04:02:31.222596',0,5),('2071a9c3-5b23-4743-bbcf-fc7cab53285d','https://res.cloudinary.com/drlhghtqx/video/upload/v1773893504/songs/audios/d1586f70-9911-40b1-9812-b4328446ba7c.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893498/songs/covers/e3c93de9-de1b-4e4b-b556-8882259f5621.jpg',' NƠI NÀY CÓ ANH | OFFICIAL MUSIC VIDEO | SƠN TÙNG M-TP','Nơi Này Có Anh','49e937a5-217c-45c3-bccc-9c955cb8bbcb','fe9a1409-4f94-4246-902e-2e1ce22354a1','a12eaec3-56cb-4c78-a131-600012fad5b4','2026-03-19 04:11:46.668369',1,19),('34c5410a-601d-40ba-a048-317c8b6879be','https://res.cloudinary.com/drlhghtqx/video/upload/v1773891753/songs/audios/7e8b312c-f561-4c0d-9a45-1fb1ddf92d2f.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773891730/songs/covers/79d8a6bc-cbcc-41c8-8f20-0425b68d0f62.png','Wxrdie - BĂNG QUA CẦU GIẤY (ft. ‪JasonDilla) [prod. by ‪Phongkhin]','Băng Qua Cầu Giấy','357ed8df-f2fe-4ee0-b465-975eb749a934','a6dd2e8c-1aa0-4545-87e1-b0f7a71cb1ef','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 03:42:35.168650',1,13),('34da4954-0044-4f38-a55f-35746b97007f','https://res.cloudinary.com/drlhghtqx/video/upload/v1773821478/songs/audios/810046a4-c1ca-4739-a60d-753662ae7af3.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773821473/songs/covers/574322af-8d74-45cf-82fa-6f49abd3ea26.jpg','Chúng Ta Không Thuộc Về Nhau | Official Music Video | Sơn Tùng M-TP','Chúng Ta Không Thuộc Về Nhau','49e937a5-217c-45c3-bccc-9c955cb8bbcb','fe9a1409-4f94-4246-902e-2e1ce22354a1','7623ebc4-1cf5-4303-98fa-18567be09a9a','2026-03-18 08:11:21.493848',0,0),('4e32b06e-d7fe-4dfd-9aec-68cbd90af898','https://res.cloudinary.com/drlhghtqx/video/upload/v1773894030/songs/audios/52fa9d4c-ec5b-46eb-b9c5-c5da7dde6bfc.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773894024/songs/covers/258be582-e803-47ea-9669-638ea26230e4.png','2323 - NGHIÊM TỔNG prod. MAI CẢNH DỊ & ĐẠI TRƯỢNG FU','2323','89566bc1-2c5c-487a-924e-f44070b8d4e4','c8ffdb22-4b48-42b3-8671-2d633fb3aff0','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 04:20:32.639468',0,0),('5620eb5b-d8de-4f09-84ea-9c18e72beaea','https://res.cloudinary.com/drlhghtqx/video/upload/v1773892688/songs/audios/d6040cfc-ffdf-4965-8300-77452e8b754c.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773892677/songs/covers/f79aa5bc-54c3-4a3f-8323-451d8ac8a2fa.png',' JACK - J97 | THIÊN LÝ ƠI | Official Music Video','Thiên Lý Ơi','201a05e3-3cda-4850-856a-5758a423e221','b46c5832-da9b-4c76-b173-44df88de0fd5','a12eaec3-56cb-4c78-a131-600012fad5b4','2026-03-19 03:58:10.182780',0,0),('5bd507da-8996-4572-b52d-fd4e2ee34873','https://res.cloudinary.com/drlhghtqx/video/upload/v1773892046/songs/audios/cace852b-0286-4406-8010-e46fc95d78fa.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773892040/songs/covers/61ffdf7b-bed3-4a19-996e-21010401c642.png','Wxrdie - THÈN CHOÁ (ft. KayC) [prod. by Marlykid]','Thèn Chóa','357ed8df-f2fe-4ee0-b465-975eb749a934','a6dd2e8c-1aa0-4545-87e1-b0f7a71cb1ef','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 03:47:28.339767',0,14),('60c9e805-acef-4fc1-a7c8-4fad8e223093','https://res.cloudinary.com/drlhghtqx/video/upload/v1773893333/songs/audios/6431f718-a148-4823-a209-c624c932a608.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893325/songs/covers/dcace8ab-2697-4279-824d-443d1827434b.jpg','SƠN TÙNG M-TP | MUỘN RỒI MÀ SAO CÒN ','Muộn Rồi Mà Sao Còn','49e937a5-217c-45c3-bccc-9c955cb8bbcb','fe9a1409-4f94-4246-902e-2e1ce22354a1','d6dfed87-c187-40c5-bf15-7e4684da0b62','2026-03-19 04:08:56.346732',0,0),('64b13317-4a1b-43f6-85cf-d6e29b1d4630','https://res.cloudinary.com/drlhghtqx/video/upload/v1773891575/songs/audios/d4a10e9e-9dec-41b1-9eab-d9483e98aafc.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773891555/songs/covers/47673ba6-7cf5-400b-ba7a-00bda25ed1bd.png','QNT - EM KHÔNG ĐI ĐÂU ft. Gii','Em Không Đi Đâu',NULL,'9bcd1c5a-b0be-4f10-b250-8e153e4cf864','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 03:39:37.684886',0,0),('65060dc8-de1d-4d45-a599-1a5f36f9a5f6','https://res.cloudinary.com/drlhghtqx/video/upload/v1773894176/songs/audios/a7318d19-90f6-4dda-838b-ee3afba32693.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773894169/songs/covers/ba6c33af-c261-456a-8970-ccdf11bab551.png',' SODA - MCK prod.GC','SODA','89566bc1-2c5c-487a-924e-f44070b8d4e4','c8ffdb22-4b48-42b3-8671-2d633fb3aff0','d6dfed87-c187-40c5-bf15-7e4684da0b62','2026-03-19 04:22:58.038369',0,0),('7d42d146-73af-4725-aed4-9c6ec78ca2e8','https://res.cloudinary.com/drlhghtqx/video/upload/v1773892501/songs/audios/8a5bb53f-b151-4509-baf1-fd0dc576d066.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773892495/songs/covers/7aa5ee05-0cdb-41e6-b56e-15b40f989f79.png','JACK - HỒNG NHAN [OFFICIAL MV] | G5R','Hồng Nhan','201a05e3-3cda-4850-856a-5758a423e221','b46c5832-da9b-4c76-b173-44df88de0fd5','a12eaec3-56cb-4c78-a131-600012fad5b4','2026-03-19 03:55:03.660826',0,0),('8a7df56a-6048-49f0-9d01-636fbddc10ac','https://res.cloudinary.com/drlhghtqx/video/upload/v1773891356/songs/audios/a36f2b89-c48a-44ad-95a0-77abdab24800.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773891350/songs/covers/ef1d11d0-110d-4db8-8751-a0feec65a20f.png','QNT - TAMKA (ft. WXRDIE & MASON NGUYEN) Prod. Phongkhin & Marlykid','TamKa',NULL,'9bcd1c5a-b0be-4f10-b250-8e153e4cf864','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 03:35:58.958280',0,0),('8e305981-eb7c-466a-bbe6-f1713ab8ca97','https://res.cloudinary.com/drlhghtqx/video/upload/v1773891857/songs/audios/2354f960-9fdd-49d5-81a3-df0a1092f4c5.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773891851/songs/covers/8248982c-a22d-443c-a905-f596b63527c3.png','Wxrdie - CẢ 2 (ft. ​⁠QNT, SpideyBoy) [prod. by Machiot]','Cả 2','357ed8df-f2fe-4ee0-b465-975eb749a934','a6dd2e8c-1aa0-4545-87e1-b0f7a71cb1ef','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 03:44:20.001937',1,6),('95441071-308a-41a3-b115-3d623e1a5f5e','https://res.cloudinary.com/drlhghtqx/video/upload/v1773893059/songs/audios/8008a908-c5fb-4010-90bb-ea9a06c882c2.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893052/songs/covers/0855e22d-683b-44be-913c-0e8239195d66.png',' Anh Đã Quen Với Cô Đơn - Soobin Hoàng Sơn','Anh Đã Quen Với Cô Đơn',NULL,'ba427ec4-34f0-4507-9af6-022752f67a6e','718cec45-aad0-4101-89ba-16c671845f14','2026-03-19 04:04:21.441222',0,0),('ad15ec43-13d4-4792-92a8-d624bd9e4e02','https://res.cloudinary.com/drlhghtqx/video/upload/v1773821124/songs/audios/81133517-6c67-4df3-80d1-28a487971c3c.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773821118/songs/covers/2771952c-7e14-4a7d-a1b3-b1b7a754dec3.png',' BẠC PHẬN | ICM x JACK | OFFICIAL MV','Bạc Phận','201a05e3-3cda-4850-856a-5758a423e221','b46c5832-da9b-4c76-b173-44df88de0fd5','a12eaec3-56cb-4c78-a131-600012fad5b4','2026-03-18 08:05:26.543519',0,0),('b165fa93-a8e3-4f65-986f-a8e678f1d4f2','https://res.cloudinary.com/drlhghtqx/video/upload/v1773892210/songs/audios/63e6d65f-565d-42b7-ae19-74a72761f4af.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773892205/songs/covers/7e4c917f-12d2-4157-ae67-d8e8529467f5.png','Wxrdie - CA KHÚC CUỐI [prod. by Machiot]','Ca Khúc Cuối','357ed8df-f2fe-4ee0-b465-975eb749a934','a6dd2e8c-1aa0-4545-87e1-b0f7a71cb1ef','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 03:50:12.484468',0,17),('b2c5f0fe-135a-4f63-9fbd-bda2f413b183','https://res.cloudinary.com/drlhghtqx/video/upload/v1773892601/songs/audios/a00faac6-8c7c-4595-b11f-d7c27c7409fe.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773892594/songs/covers/4f55a1d0-0c0b-49c6-bf1b-ee6af8a4da2f.png',' SÓNG GIÓ | ICM x JACK | OFFICIAL MUSIC VIDEO','Sóng Gió','201a05e3-3cda-4850-856a-5758a423e221','b46c5832-da9b-4c76-b173-44df88de0fd5','a12eaec3-56cb-4c78-a131-600012fad5b4','2026-03-19 03:56:43.719660',0,0),('b669a47c-3992-4148-b94d-63df0267e1ff','https://res.cloudinary.com/drlhghtqx/video/upload/v1775103229/songs/audios/4eb4a3b7-a3db-4592-b17a-82b103db9562.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1775103209/songs/covers/972a4947-d770-4a9f-b11c-3a268c64d833.jpg','Trả Cho Anh Remix (Bản Hot TikTok) - Nguyễn Thạc Bảo Ngọc ♫ Em Trả Cho Anh Tự Do','Trả Cho Anh',NULL,'404db06e-319f-4acc-bdda-53f623d1f821','7623ebc4-1cf5-4303-98fa-18567be09a9a','2026-04-02 04:13:51.046462',2,21),('b7dade72-fda4-4bed-8a40-64f00910bdcc','https://res.cloudinary.com/drlhghtqx/video/upload/v1773894093/songs/audios/1c626dbe-c3bd-4f51-809b-7d9e57a7bf05.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773894088/songs/covers/2671f6be-658e-48d7-9df4-486388079220.png','Chìm Sâu - RPT MCK (feat. Trung Trần)','Chìm Sâu','89566bc1-2c5c-487a-924e-f44070b8d4e4','c8ffdb22-4b48-42b3-8671-2d633fb3aff0','d6dfed87-c187-40c5-bf15-7e4684da0b62','2026-03-19 04:21:35.393200',0,0),('bc202228-4277-43bc-b455-3b221eb0abb4','https://res.cloudinary.com/drlhghtqx/video/upload/v1773892786/songs/audios/c030f3a0-44b5-4b6a-a8e3-f9cbd95d5835.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773892778/songs/covers/a01d7ce1-14b9-402c-947a-86c0f784bf12.png','Jack | Đom Đóm | Official Music Video','Đom Đóm','201a05e3-3cda-4850-856a-5758a423e221','b46c5832-da9b-4c76-b173-44df88de0fd5','718cec45-aad0-4101-89ba-16c671845f14','2026-03-19 03:59:48.079917',0,0),('c10a12b9-366e-4490-9c9d-4dd2bbe69a8e','https://res.cloudinary.com/drlhghtqx/video/upload/v1773893964/songs/audios/1c02958a-1d14-424c-8ffa-33fcd01dcd54.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893958/songs/covers/6cfc6734-db6f-4571-aa47-e7c287e9d55e.png','thap drill tu do - nghiem tong prod. gaz','Tháp Drill Tự Do','89566bc1-2c5c-487a-924e-f44070b8d4e4','c8ffdb22-4b48-42b3-8671-2d633fb3aff0','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 04:19:26.315764',0,0),('c9e96e3c-4134-4e57-9ed5-312ca72dd2e0','https://res.cloudinary.com/drlhghtqx/video/upload/v1773892291/songs/audios/f3618142-a435-40a9-a5ca-8811ddfa3ff5.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773892281/songs/covers/994340ca-278e-49cf-807c-430a0841c175.png',' Wxrdie - LONELY STONIE [prod. by Dustin Ngo]','LONELY STONIE','357ed8df-f2fe-4ee0-b465-975eb749a934','a6dd2e8c-1aa0-4545-87e1-b0f7a71cb1ef','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 03:51:33.061067',0,0),('dcce568d-d2e5-4d8f-8471-e89cc97cea44','https://res.cloudinary.com/drlhghtqx/video/upload/v1773891153/songs/audios/c762aa0e-3956-4d3f-9ef4-a604e647a70e.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773891056/songs/covers/4d944b77-c8af-41bd-a924-a8c9924624ce.jpg','QUERRY - QNT x TRUNG TRẦN ft RPT MCK (Prod. By RASTZ & MATTHEW MAY)','Querry',NULL,'9bcd1c5a-b0be-4f10-b250-8e153e4cf864','0335a443-93b2-4683-a1f4-ec3a555ec63f','2026-03-19 03:32:35.868375',0,0),('e6c3caa2-f66c-4a6b-b9ff-25a5e5cca556','https://res.cloudinary.com/drlhghtqx/video/upload/v1772529867/songs/audios/677dba7e-bd38-48f7-a6fe-33488b9485e6.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1772529859/songs/covers/4ea582f9-1fce-4028-8cb6-26cc85094fa7.jpg','SƠN TÙNG M-TP | CHÚNG TA CỦA HIỆN TẠI | OFFICIAL MUSIC VIDEO','Chúng Ta Của Hiện Tại','49e937a5-217c-45c3-bccc-9c955cb8bbcb','fe9a1409-4f94-4246-902e-2e1ce22354a1','a12eaec3-56cb-4c78-a131-600012fad5b4','2026-03-03 09:24:29.261164',0,0),('ea6db031-3492-444e-8ec3-4b9ffe441538','https://res.cloudinary.com/drlhghtqx/video/upload/v1773893415/songs/audios/e69cd23f-2425-4eb0-9238-331455a516c4.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893404/songs/covers/8022125a-65c5-4484-9e9d-c0f6481cb4f0.jpg','LẠC TRÔI | OFFICIAL MUSIC VIDEO | SƠN TÙNG M-TP','Lạc Trôi','49e937a5-217c-45c3-bccc-9c955cb8bbcb','fe9a1409-4f94-4246-902e-2e1ce22354a1','a12eaec3-56cb-4c78-a131-600012fad5b4','2026-03-19 04:10:17.204223',0,0),('fd305903-df3f-4c86-ace2-12d61475d828','https://res.cloudinary.com/drlhghtqx/video/upload/v1773893185/songs/audios/98dbf89f-3050-4183-8186-7d79a17ead37.mp3','https://res.cloudinary.com/drlhghtqx/image/upload/v1773893175/songs/covers/aefba7d3-98a0-4c74-b2ce-df6694043d1b.png','Phía Sau Một Cô Gái - Soobin Hoàng Sơn','Phía Sau Một Cô Gái',NULL,'ba427ec4-34f0-4507-9af6-022752f67a6e','718cec45-aad0-4101-89ba-16c671845f14','2026-03-19 04:06:27.974039',0,0);
/*!40000 ALTER TABLE `songs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscriptions`
--

DROP TABLE IF EXISTS `subscriptions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscriptions` (
  `id` varchar(255) NOT NULL,
  `name` enum('PREMIUM','VIP') NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` enum('ACTIVE','EXPIRED') DEFAULT NULL,
  `package_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhro52ohfqfbay9774bev0qinr` (`user_id`),
  CONSTRAINT `FKhro52ohfqfbay9774bev0qinr` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscriptions`
--

LOCK TABLES `subscriptions` WRITE;
/*!40000 ALTER TABLE `subscriptions` DISABLE KEYS */;
INSERT INTO `subscriptions` VALUES ('506403ed-e78a-497e-963d-196661386b0f','VIP',NULL,NULL,NULL,NULL,''),('dfff74c2-c4ce-41e8-8329-249bd0172cf9','PREMIUM',NULL,NULL,NULL,NULL,'');
/*!40000 ALTER TABLE `subscriptions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('28055786-9747-40ef-a14a-c9f86df90326','67e945bd-189d-43ee-8492-10e10d57ae21'),('2ef7071c-0394-4c80-a289-f3e23ee81467','67e945bd-189d-43ee-8492-10e10d57ae21'),('66eef44d-d1e1-4547-907d-f9082bf7774c','67e945bd-189d-43ee-8492-10e10d57ae21'),('74845b80-ed4c-4999-8ea0-94e8386a954e','67e945bd-189d-43ee-8492-10e10d57ae21'),('75975503-3c9a-42e6-8d0a-f01f17fe2f44','67e945bd-189d-43ee-8492-10e10d57ae21'),('66eef44d-d1e1-4547-907d-f9082bf7774c','68d527f8-0c3e-4037-96ce-90e299cdf254'),('75975503-3c9a-42e6-8d0a-f01f17fe2f44','98766c2d-0a61-4503-8270-784d367a0b14'),('74845b80-ed4c-4999-8ea0-94e8386a954e','e9409c40-d764-4f11-b75f-e4dedd573535'),('75975503-3c9a-42e6-8d0a-f01f17fe2f44','e9409c40-d764-4f11-b75f-e4dedd573535');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('28055786-9747-40ef-a14a-c9f86df90326','https://lh3.googleusercontent.com/a/ACg8ocK9G68Vd1r9MAf7pGD4Q8rwQs40IBpspOJ84CvFjqCs87E_dA=s96-c','trongphamtg05@gmail.com',_binary '','','Quang Trọng Phạm',NULL),('2ef7071c-0394-4c80-a289-f3e23ee81467','https://lh3.googleusercontent.com/a/ACg8ocLviOJZj5o-_-W_0jFTxlJ_P4wzf7a-Z7JyLB5MpXkaQ78Y3w=s96-c','dosaoms000@gmail.com',_binary '','','Sao Do',NULL),('66eef44d-d1e1-4547-907d-f9082bf7774c',NULL,'22130299@st.hcmuaf.edu.vn',_binary '','$2a$10$Rumit.lKI6n.3AZ8c4b82u8Ybc3OQM4LjEarDZcdN6OHCleQLUGAS','Quang Trọng',NULL),('74845b80-ed4c-4999-8ea0-94e8386a954e','https://res.cloudinary.com/drlhghtqx/image/upload/v1772458887/avatars/caccd33d-a894-414f-8933-d8ed05d9c625.png','modviemp3@gmail.com',_binary '','$2a$10$QX1Zt89jIaI.i3jZYzXK1e5lvM8G4GYt0GtEW7GHdyLXMRUNUU63i','Mod VieMp3',NULL),('75975503-3c9a-42e6-8d0a-f01f17fe2f44',NULL,'adminviemp3@gmail.com',_binary '','$2a$10$ijWWKidbXymANJNFv5ayRutv5hmYYntPNsri5S9.CXXz9wgyB7/qq','admin',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_tokens`
--

DROP TABLE IF EXISTS `verification_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_tokens` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `expiry_date` datetime(6) DEFAULT NULL,
  `otp` varchar(255) DEFAULT NULL,
  `used` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_tokens`
--

LOCK TABLES `verification_tokens` WRITE;
/*!40000 ALTER TABLE `verification_tokens` DISABLE KEYS */;
INSERT INTO `verification_tokens` VALUES ('1bfb7889-3917-4204-a07a-c0773439821f','dosaoms001@gmail.com','2026-05-26 15:48:19.104644','564506',_binary '\0'),('78c4b376-0e1d-48da-9ce9-88399b4e93ac','dosaoms001@gmail.com','2026-04-18 09:20:28.659205','214680',_binary '\0'),('8c7c9640-801e-43a6-880d-7edb55cc6f85','22130299@st.hcmuaf.edu.vn','2026-03-11 09:02:25.223815','113345',_binary ''),('da27d0f4-b33e-4507-ab14-79e2c7e3f9c1','dosaoms001@gmail.com','2026-04-21 03:09:55.259190','943205',_binary '\0'),('e85e62de-da55-42fb-8a55-287c4e3923eb','dosaoms001@gmail.com','2026-04-18 09:22:58.343693','947931',_binary '\0'),('f2cfc0cb-6530-40f6-b20b-e8eb68656c36','dosaoms001@gmail.com','2026-04-18 09:13:45.890663','111602',_binary '\0');
/*!40000 ALTER TABLE `verification_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vouchers`
--

DROP TABLE IF EXISTS `vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vouchers` (
  `id` varchar(255) NOT NULL,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `discount_percentage` double NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `max_discount_amount` double NOT NULL,
  `quantity` int NOT NULL,
  `start_date` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vouchers`
--

LOCK TABLES `vouchers` WRITE;
/*!40000 ALTER TABLE `vouchers` DISABLE KEYS */;
INSERT INTO `vouchers` VALUES ('e6e46812-e486-4246-997e-c810cb356362',_binary '','2026-04-17 03:36:51.995160',10,'2026-08-01 16:59:59.000000',5000,90,'2026-04-18 17:00:00.000000');
/*!40000 ALTER TABLE `vouchers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-06 11:32:35
