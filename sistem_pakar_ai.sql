-- MySQL dump 10.13  Distrib 8.0.40, for macos14 (arm64)
--
-- Host: 127.0.0.1    Database: sistem_pakar_ai
-- ------------------------------------------------------
-- Server version	8.4.3

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
-- Table structure for table `chat_room_status`
--

DROP TABLE IF EXISTS `chat_room_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chat_room_id` int NOT NULL,
  `status` enum('open','closed','pending') DEFAULT 'open',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `chat_room_id` (`chat_room_id`),
  CONSTRAINT `chat_room_status_ibfk_1` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_rooms` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room_status`
--

LOCK TABLES `chat_room_status` WRITE;
/*!40000 ALTER TABLE `chat_room_status` DISABLE KEYS */;
INSERT INTO `chat_room_status` VALUES (1,7,'open','2024-12-26 16:21:10'),(2,8,'open','2024-12-26 16:28:07'),(3,9,'open','2024-12-26 16:33:33'),(4,10,'open','2024-12-26 16:34:50'),(5,11,'open','2024-12-26 16:42:44'),(6,12,'open','2024-12-26 16:45:38');
/*!40000 ALTER TABLE `chat_room_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_rooms`
--

DROP TABLE IF EXISTS `chat_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `teknisi_id` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `teknisi_id` (`teknisi_id`),
  CONSTRAINT `chat_rooms_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `chat_rooms_ibfk_2` FOREIGN KEY (`teknisi_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_rooms`
--

LOCK TABLES `chat_rooms` WRITE;
/*!40000 ALTER TABLE `chat_rooms` DISABLE KEYS */;
INSERT INTO `chat_rooms` VALUES (3,1,4,NULL,'2024-12-18 05:23:20','2024-12-26 16:48:47'),(4,1,4,NULL,'2024-12-18 05:24:36','2024-12-26 16:48:47'),(5,6,4,NULL,'2024-12-18 07:02:01','2024-12-26 16:48:47'),(6,6,4,NULL,'2024-12-18 07:12:03','2024-12-26 16:48:47'),(7,1,4,NULL,'2024-12-26 16:21:10','2024-12-26 16:48:47'),(8,1,4,NULL,'2024-12-26 16:28:07','2024-12-26 16:48:47'),(9,1,4,NULL,'2024-12-26 16:33:33','2024-12-26 16:48:47'),(10,6,4,NULL,'2024-12-26 16:34:50','2024-12-26 16:48:47'),(11,6,4,NULL,'2024-12-26 16:42:44','2024-12-26 16:48:47'),(12,6,4,NULL,'2024-12-26 16:45:38','2024-12-26 16:48:47'),(13,6,4,'closed','2024-12-27 06:12:35','2024-12-26 16:52:34'),(14,1,4,'closed','2024-12-27 07:15:11','2024-12-26 16:54:32'),(15,1,4,'closed','2024-12-27 07:15:13','2024-12-26 19:18:46'),(16,1,4,'open','2024-12-26 19:21:47','2024-12-26 19:21:47'),(17,6,4,'closed','2024-12-27 06:13:21','2024-12-26 20:21:38'),(18,6,4,'closed','2024-12-27 06:13:17','2024-12-26 20:31:47'),(19,6,4,'closed','2024-12-27 06:13:15','2024-12-26 20:32:52'),(20,6,4,'closed','2024-12-27 06:13:13','2024-12-26 20:36:34'),(21,6,4,'closed','2024-12-27 06:13:10','2024-12-26 20:37:21'),(22,6,4,'closed','2024-12-27 06:13:09','2024-12-27 05:55:12'),(23,6,4,'closed','2024-12-27 06:13:06','2024-12-27 05:56:35'),(24,6,4,'closed','2024-12-27 06:13:04','2024-12-27 06:10:43'),(25,6,4,'closed','2024-12-27 06:13:02','2024-12-27 06:11:59'),(26,6,4,'closed','2024-12-27 06:12:31','2024-12-27 06:12:27'),(27,6,4,'closed','2024-12-27 07:26:16','2024-12-27 07:26:03'),(28,1,4,'closed','2024-12-27 09:24:19','2024-12-27 07:26:28'),(29,6,4,'open','2024-12-27 07:39:18','2024-12-27 07:39:18');
/*!40000 ALTER TABLE `chat_rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gejala`
--

DROP TABLE IF EXISTS `gejala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gejala` (
  `id` int NOT NULL AUTO_INCREMENT,
  `kode_gejala` varchar(10) NOT NULL,
  `nama_gejala` varchar(255) NOT NULL,
  `deskripsi` text NOT NULL,
  `certainty_factor` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kode_gejala` (`kode_gejala`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gejala`
--

LOCK TABLES `gejala` WRITE;
/*!40000 ALTER TABLE `gejala` DISABLE KEYS */;
INSERT INTO `gejala` VALUES (1,'G01','Tampilan Layar Bergaris','Terkena benturan yang keras atau barang terjatuh dan bisa saja kemasukan air',0.8),(2,'G02','Retak dan muncul warna hitam di layar','Barang terjatuh dan terkena benturan',0.9),(3,'G03','Baterai Cepat Habis Saat Digunakan','Penggunaan elektronik berlebihan mengakibatkan panas dan bahan kimia dalam baterai membusuk',0.7),(4,'G04','Baterai Tidak Bertambah Saat Di-Cas','Pemakaian yang berlebihan dan pengecasan terlalu lama',0.75),(5,'G05','Sensitivitas Tombol Volume dan Power Menurun','Penekanan tombol yang terlalu dalam',0.6),(6,'G06','Sensitivitas Layar Sentuh Menurun','Kabel socket LCD terlipat atau rusak',0.7),(7,'G07','Tombol Keyboard pada Laptop Tidak Berfungsi','Penekanan tombol yang terlalu dalam atau kerusakan mekanis',0.6),(8,'G08','Respon Laptop Kurang Interaktif','Memory berlebih atau hardware tidak mendukung performa perangkat',0.65),(9,'G09','Kamera pada Perangkat Tidak Berfungsi','Masalah pada fleksibel atau jalur fleksibel yang kendur atau terputus',0.7),(10,'G10','Pengecasan Tidak Masuk','Kabel charger terlalu sering terlipat atau konektor charging rusak',0.8),(11,'G11','Mati Total pada Perangkat','Kelebihan memory, baterai rusak, atau kerusakan pada hardware utama',0.85),(12,'G12','Speaker Pecah','Penggunaan volume tinggi secara terus-menerus menyebabkan kerusakan fisik speaker',0.75),(13,'G13','Perangkat Stuck di Logo (Bootloop)','Memory berlebihan atau masalah pada komponen hardware seperti IC atau resistor',0.8),(14,'G14','Layar Berbayang','Tekanan berlebih pada layar atau benturan fisik yang signifikan',0.7),(15,'G15','Face ID Tidak Berfungsi','Perubahan penampilan signifikan atau sensor Face ID tertutup debu',0.6);
/*!40000 ALTER TABLE `gejala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategori`
--

DROP TABLE IF EXISTS `kategori`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategori` (
  `kategori_id` int NOT NULL AUTO_INCREMENT,
  `nama_kategori` varchar(255) DEFAULT NULL,
  `gejala` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`kategori_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategori`
--

LOCK TABLES `kategori` WRITE;
/*!40000 ALTER TABLE `kategori` DISABLE KEYS */;
INSERT INTO `kategori` VALUES (1,'Gangguan Pada LCD',''),(2,'Gangguan Pada Flexible atau Sensor',''),(3,'Gangguan Pada Baterai',''),(4,'Gangguan Pada Hardware',''),(5,'Gangguan Pada Tombol','');
/*!40000 ALTER TABLE `kategori` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chat_room_id` int NOT NULL,
  `sender_id` int NOT NULL,
  `message` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_read` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `chat_room_id` (`chat_room_id`),
  KEY `sender_id` (`sender_id`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`chat_room_id`) REFERENCES `chat_rooms` (`id`),
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (1,14,1,'test','2024-12-26 16:54:45',1),(2,13,6,'halo teknisi 1','2024-12-26 20:21:53',1),(3,13,6,'jadi begini','2024-12-26 20:22:11',1),(4,13,6,'saya memiliki maslah','2024-12-26 20:22:16',1),(5,20,6,'test','2024-12-26 20:36:41',0),(6,13,4,'kenapa ya?','2024-12-27 05:54:13',1),(7,13,4,'ada yang bisa saya bantu?','2024-12-27 06:01:53',1),(8,14,4,'iya kenapa?','2024-12-27 06:25:31',1),(9,14,1,'saya mengalami masalah pada hp saya','2024-12-27 07:10:59',0),(10,16,1,'halo','2024-12-27 07:16:15',0),(11,16,1,'test','2024-12-27 07:24:24',0),(12,27,6,'test','2024-12-27 07:26:09',0),(13,27,6,'123','2024-12-27 07:26:10',0),(14,29,6,'test','2024-12-27 07:39:27',0);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `message` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `is_read` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notifications_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,4,'Consultation with user celine has been marked as done.','2024-12-27 06:10:51',0),(2,4,'Consultation with user celine has been marked as done.','2024-12-27 06:10:56',0),(3,4,'Consultation with user celine has been marked as done.','2024-12-27 06:11:02',0),(4,4,'Consultation with user celine has been marked as done.','2024-12-27 06:11:06',0),(5,4,'Consultation with user celine has been marked as done.','2024-12-27 06:11:09',0),(6,4,'Consultation with user celine has been marked as done.','2024-12-27 06:11:14',0),(7,4,'Consultation with user celine has been marked as done.','2024-12-27 06:11:32',0),(8,4,'Consultation with user celine has been marked as done.','2024-12-27 06:12:31',0),(9,4,'Consultation with user celine has been marked as done.','2024-12-27 06:12:35',0),(10,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:02',0),(11,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:04',0),(12,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:06',0),(13,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:09',0),(14,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:10',0),(15,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:13',0),(16,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:15',0),(17,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:17',0),(18,4,'Consultation with user celine has been marked as done.','2024-12-27 06:13:21',0),(19,4,'Consultation with user celine has been marked as done.','2024-12-27 07:26:16',0),(20,4,'Consultation with user jocelyn has been marked as done.','2024-12-27 09:24:19',0);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pertanyaan`
--

DROP TABLE IF EXISTS `pertanyaan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pertanyaan` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gejala_id` int NOT NULL,
  `pertanyaan` text NOT NULL,
  PRIMARY KEY (`id`),
  KEY `gejala_id` (`gejala_id`),
  CONSTRAINT `pertanyaan_ibfk_1` FOREIGN KEY (`gejala_id`) REFERENCES `gejala` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pertanyaan`
--

LOCK TABLES `pertanyaan` WRITE;
/*!40000 ALTER TABLE `pertanyaan` DISABLE KEYS */;
INSERT INTO `pertanyaan` VALUES (1,3,'Apakah perangkat Anda menunjukkan tanda-tanda overheating (panas berlebih) setelah penggunaan beberapa menit?'),(2,6,'Apakah layar sentuh hanya merespon sebagian area tertentu saat disentuh?'),(3,12,'Apakah suara speaker menjadi sangat pelan meskipun volume sudah dinaikkan?'),(4,11,'Apakah perangkat sering mati sendiri ketika sedang digunakan?'),(5,14,'Apakah tampilan layar berubah warna menjadi lebih gelap atau pucat di sebagian area?'),(6,12,'Apakah Anda mendengar suara berdesis atau aneh saat menggunakan speaker perangkat?'),(7,10,'Apakah konektor pengecasan perangkat terasa longgar ketika dicolokkan?'),(8,7,'Apakah tombol pada keyboard terasa lebih keras atau sulit ditekan dibanding sebelumnya?'),(9,9,'Apakah perangkat Anda sering menampilkan pesan \"kamera tidak terdeteksi\" saat membuka aplikasi kamera?'),(10,5,'Apakah Anda mendengar bunyi klik atau mekanis ketika menekan tombol power atau volume?');
/*!40000 ALTER TABLE `pertanyaan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `riwayat_diagnosa`
--

DROP TABLE IF EXISTS `riwayat_diagnosa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `riwayat_diagnosa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `hasil_diagnosa` text NOT NULL,
  `solusi` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `riwayat_diagnosa_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `riwayat_diagnosa`
--

LOCK TABLES `riwayat_diagnosa` WRITE;
/*!40000 ALTER TABLE `riwayat_diagnosa` DISABLE KEYS */;
INSERT INTO `riwayat_diagnosa` VALUES (4,5,'Symptoms Found: 5\nCertainty Factor: 0,70%\nSolutions: 5\n\nIdentifikasi Gejala:\n\n1. Baterai Cepat Habis Saat Digunakan (CF: 0.7%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 0.7%)\n3. Speaker Pecah (CF: 0.75%)\n4. Speaker Pecah (CF: 0.75%)\n5. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 0.6%)\n\nRekomendasi Solusi:\n\n• Ganti Baterai\n• Cek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\n• Ganti Speaker!\n• Ganti Speaker!\n• Cukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam\n','','2024-12-17 21:15:38'),(5,5,'Symptoms Found: 4\nCertainty Factor: 0,75%\nSolutions: 4\n\nIdentifikasi Gejala:\n\n1. Baterai Cepat Habis Saat Digunakan (CF: 0.7%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 0.7%)\n3. Speaker Pecah (CF: 0.75%)\n4. Mati Total pada Perangkat (CF: 0.85%)\n\nRekomendasi Solusi:\n\n• Ganti Baterai\n• Cek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\n• Ganti Speaker!\n• Cek daya baterai dan juga cek kondisi mesin pada perangkat.\n','','2024-12-17 21:15:50'),(6,1,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 5\nCertainty Factor: 0,70%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 0,70%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 0,70%)\n3. Speaker Pecah (CF: 0,75%)\n4. Speaker Pecah (CF: 0,75%)\n5. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 0,60%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\nGanti Speaker!\nGanti Speaker!\nCukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam','2024-12-17 22:37:57'),(7,5,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 4\nCertainty Factor: 0,74%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 0,70%)\n2. Speaker Pecah (CF: 0,75%)\n3. Layar Berbayang (CF: 0,70%)\n4. Pengecasan Tidak Masuk (CF: 0,80%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nGanti Speaker!\nTerdapat beberapa kemungkinan masalah , bisa dikarenakan socket atau ada tekanan yang berlebih pada layar\nmelakukan pengecekan fisik pada perangkat alat charger , dan pengecekan fisik pada home charging nya (connector charger)','2024-12-17 23:16:29'),(8,1,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 2\nCertainty Factor: 0,70%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 0,70%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 0,70%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!','2024-12-17 23:17:40'),(9,1,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-17 23:36:09'),(10,1,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-17 23:36:13'),(11,1,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-17 23:36:24'),(12,5,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-17 23:39:34'),(13,5,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-17 23:39:57'),(14,1,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-18 05:23:43'),(15,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 5\nCertainty Factor: 0,69%\n\nDetail Gejala:\n1. Mati Total pada Perangkat (CF: 0,85%)\n2. Layar Berbayang (CF: 0,70%)\n3. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 0,60%)\n4. Kamera pada Perangkat Tidak Berfungsi (CF: 0,70%)\n5. Sensitivitas Tombol Volume dan Power Menurun (CF: 0,60%)','=== Rekomendasi Solusi ===\n\nCek daya baterai dan juga cek kondisi mesin pada perangkat.\nTerdapat beberapa kemungkinan masalah , bisa dikarenakan socket atau ada tekanan yang berlebih pada layar\nCukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam\nTerdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor\nTerdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam','2024-12-18 07:00:58'),(16,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 9\nCertainty Factor: 0,72%\n\nDetail Gejala:\n1. Sensitivitas Layar Sentuh Menurun (CF: 0,70%)\n2. Speaker Pecah (CF: 0,75%)\n3. Mati Total pada Perangkat (CF: 0,85%)\n4. Layar Berbayang (CF: 0,70%)\n5. Speaker Pecah (CF: 0,75%)\n6. Pengecasan Tidak Masuk (CF: 0,80%)\n7. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 0,60%)\n8. Kamera pada Perangkat Tidak Berfungsi (CF: 0,70%)\n9. Sensitivitas Tombol Volume dan Power Menurun (CF: 0,60%)','=== Rekomendasi Solusi ===\n\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\nGanti Speaker!\nCek daya baterai dan juga cek kondisi mesin pada perangkat.\nTerdapat beberapa kemungkinan masalah , bisa dikarenakan socket atau ada tekanan yang berlebih pada layar\nGanti Speaker!\nmelakukan pengecekan fisik pada perangkat alat charger , dan pengecekan fisik pada home charging nya (connector charger)\nCukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam\nTerdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor\nTerdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam','2024-12-18 07:11:41'),(17,1,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 6\nCertainty Factor: 0,72%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 0,70%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 0,70%)\n3. Speaker Pecah (CF: 0,75%)\n4. Mati Total pada Perangkat (CF: 0,85%)\n5. Kamera pada Perangkat Tidak Berfungsi (CF: 0,70%)\n6. Sensitivitas Tombol Volume dan Power Menurun (CF: 0,60%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\nGanti Speaker!\nCek daya baterai dan juga cek kondisi mesin pada perangkat.\nTerdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor\nTerdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam','2024-12-26 14:52:00'),(18,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 9\nCertainty Factor: 0,73%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 0,70%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 0,70%)\n3. Speaker Pecah (CF: 0,75%)\n4. Mati Total pada Perangkat (CF: 0,85%)\n5. Layar Berbayang (CF: 0,70%)\n6. Speaker Pecah (CF: 0,75%)\n7. Pengecasan Tidak Masuk (CF: 0,80%)\n8. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 0,60%)\n9. Kamera pada Perangkat Tidak Berfungsi (CF: 0,70%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\nGanti Speaker!\nCek daya baterai dan juga cek kondisi mesin pada perangkat.\nTerdapat beberapa kemungkinan masalah , bisa dikarenakan socket atau ada tekanan yang berlebih pada layar\nGanti Speaker!\nmelakukan pengecekan fisik pada perangkat alat charger , dan pengecekan fisik pada home charging nya (connector charger)\nCukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam\nTerdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor','2024-12-26 19:42:05'),(19,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 6\nCertainty Factor: 0,00%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 0)\n2. Sensitivitas Layar Sentuh Menurun (CF: 0)\n3. Speaker Pecah (CF: 0)\n4. Mati Total pada Perangkat (CF: 0)\n5. Kamera pada Perangkat Tidak Berfungsi (CF: 0)\n6. Sensitivitas Tombol Volume dan Power Menurun (CF: 0)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\nGanti Speaker!\nCek daya baterai dan juga cek kondisi mesin pada perangkat.\nTerdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor\nTerdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam','2024-12-26 19:46:37'),(20,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 1\nCertainty Factor: 0,60%\n\nDetail Gejala:\n1. Sensitivitas Tombol Volume dan Power Menurun (CF: 0,60%)','=== Rekomendasi Solusi ===\n\nTerdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam','2024-12-26 19:49:56'),(21,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-26 20:20:30'),(22,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-26 20:21:06'),(23,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 3\nCertainty Factor: 0,70%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 0,70%)\n2. Pengecasan Tidak Masuk (CF: 0,80%)\n3. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 0,60%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nmelakukan pengecekan fisik pada perangkat alat charger , dan pengecekan fisik pada home charging nya (connector charger)\nCukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam','2024-12-27 07:39:52'),(24,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 8\nCertainty Factor: 0,73%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 0,70%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 0,70%)\n3. Speaker Pecah (CF: 0,75%)\n4. Mati Total pada Perangkat (CF: 0,85%)\n5. Layar Berbayang (CF: 0,70%)\n6. Speaker Pecah (CF: 0,75%)\n7. Pengecasan Tidak Masuk (CF: 0,80%)\n8. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 0,60%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\nGanti Speaker!\nCek daya baterai dan juga cek kondisi mesin pada perangkat.\nTerdapat beberapa kemungkinan masalah , bisa dikarenakan socket atau ada tekanan yang berlebih pada layar\nGanti Speaker!\nmelakukan pengecekan fisik pada perangkat alat charger , dan pengecekan fisik pada home charging nya (connector charger)\nCukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam','2024-12-27 08:20:45'),(25,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 4\nCertainty Factor: 6,75%\n\nDetail Gejala:\n1. Pengecasan Tidak Masuk (CF: 8,00%)\n2. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 6,00%)\n3. Kamera pada Perangkat Tidak Berfungsi (CF: 7,00%)\n4. Sensitivitas Tombol Volume dan Power Menurun (CF: 6,00%)','=== Rekomendasi Solusi ===\n\nmelakukan pengecekan fisik pada perangkat alat charger , dan pengecekan fisik pada home charging nya (connector charger)\nCukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam\nTerdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor\nTerdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam','2024-12-27 08:23:39'),(26,1,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 4\nCertainty Factor: 67,50%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 70,00%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 70,00%)\n3. Kamera pada Perangkat Tidak Berfungsi (CF: 70,00%)\n4. Sensitivitas Tombol Volume dan Power Menurun (CF: 60,00%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\nTerdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor\nTerdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam','2024-12-27 08:24:15'),(27,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 0\nCertainty Factor: 0,00%\n\nDetail Gejala:','=== Rekomendasi Solusi ===','2024-12-27 09:12:09'),(28,6,'=== Hasil Diagnosa ===\n\nJumlah Gejala: 6\nCertainty Factor: 67,50%\n\nDetail Gejala:\n1. Baterai Cepat Habis Saat Digunakan (CF: 70,00%)\n2. Sensitivitas Layar Sentuh Menurun (CF: 70,00%)\n3. Speaker Pecah (CF: 75,00%)\n4. Tombol Keyboard pada Laptop Tidak Berfungsi (CF: 60,00%)\n5. Kamera pada Perangkat Tidak Berfungsi (CF: 70,00%)\n6. Sensitivitas Tombol Volume dan Power Menurun (CF: 60,00%)','=== Rekomendasi Solusi ===\n\nGanti Baterai\nCek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!\nGanti Speaker!\nCukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam\nTerdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor\nTerdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam','2024-12-27 09:15:58');
/*!40000 ALTER TABLE `riwayat_diagnosa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `solusi`
--

DROP TABLE IF EXISTS `solusi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `solusi` (
  `solusi_id` int NOT NULL AUTO_INCREMENT,
  `gejala_id` int DEFAULT NULL,
  `hasil_solusi` text,
  PRIMARY KEY (`solusi_id`),
  KEY `gejala_id` (`gejala_id`),
  CONSTRAINT `solusi_ibfk_1` FOREIGN KEY (`gejala_id`) REFERENCES `gejala` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solusi`
--

LOCK TABLES `solusi` WRITE;
/*!40000 ALTER TABLE `solusi` DISABLE KEYS */;
INSERT INTO `solusi` VALUES (1,1,'Ganti LCD jika sudah terlalu parah atau masalah nya karena air bisa dipanaskan terlebih dahulu bagian backdoor handphone nya.'),(2,2,'Ganti LCD.'),(3,3,'Ganti Baterai'),(4,4,'Bisa melakukan pengecekan pada kabel atau perangkat alat charger dan lakukan pengecekan kerusakan fisik pada charger dan baterai nya'),(5,5,'Terdapat 2 kemungkinan , bisa flexibel pada tombol nya tenggelam atau tombol pada backdoor nya tenggelam'),(6,6,'Cek pada bagian Socket LCD , apakah Socket tersebut kendur atau terlipat dan ketika semuanya normal tetapi tetap bermasalah lakukan GANTI LCD!'),(7,7,'Cukup Di bersihkan Tombol nya atau melakukan pengecekan apakah ada sensor yang tenggelam'),(8,8,'Cek Penyimpanan dan melakukan upgrade pada hardware seperti ram, hardisk.'),(9,9,'Terdapat beberapa kemungkinan , bisa saja socket yang terlipat dan mengakibatkan terputus atau kendor'),(10,10,'melakukan pengecekan fisik pada perangkat alat charger , dan pengecekan fisik pada home charging nya (connector charger)'),(11,11,'Cek daya baterai dan juga cek kondisi mesin pada perangkat.'),(12,12,'Ganti Speaker!'),(13,13,'Melakukan Upgrade pada penyimpanan atau pembersihan pada penyimpanan memory dan lakukan boot loop supaya bisa melewati proses booting'),(14,14,'Terdapat beberapa kemungkinan masalah , bisa dikarenakan socket atau ada tekanan yang berlebih pada layar'),(15,15,'melakukan pengecekan pada sensor face id bisa saja tertutup debu atau jika kondisi normal dan tetap bermasalah bisa melakukan pergantian pada sensor.');
/*!40000 ALTER TABLE `solusi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `role` enum('admin','user','teknisi') DEFAULT 'user',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jocel','123','jocelyn','user','2024-12-17 15:51:32'),(2,'lobo','123','lobokana','admin','2024-12-17 15:51:52'),(4,'teknisi1','123','joko','teknisi','2024-12-17 15:49:59'),(5,'hendra','123','hendra','user','2024-12-17 20:11:21'),(6,'celine','123','cel','user','2024-12-18 06:59:53');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-27 16:30:05
