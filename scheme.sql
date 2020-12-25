SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `test`
--

-- --------------------------------------------------------

--
-- Table structure for table `channel`
--

CREATE TABLE IF NOT EXISTS `channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(65) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- Dumping data for table `channel`
--

INSERT INTO `channel` (`id`, `title`) VALUES
(1, 'Первый канал'),
(2, 'Второй канал'),
(3, 'newTest'),
(6, 'Test2'),
(7, 'Test3'),
(8, 'NEW'),
(9, 'лол');

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE IF NOT EXISTS `genre` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `genreName` varchar(65) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=21 ;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`id`, `genreName`) VALUES
(17, 'Отечественный фильм'),
(18, 'Комедия'),
(20, 'Трагедия');

-- --------------------------------------------------------

--
-- Table structure for table `tvprogram`
--

CREATE TABLE IF NOT EXISTS `tvprogram` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` int(11) NOT NULL,
  `title` varchar(65) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `beginTime` date NOT NULL,
  `genre_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1` (`channel_id`),
  KEY `fk2` (`genre_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `tvprogram`
--

INSERT INTO `tvprogram` (`id`, `channel_id`, `title`, `beginTime`, `genre_id`) VALUES
(1, 1, 'Program 1', '2020-12-26', 17),
(2, 2, 'Program 2', '2020-12-27', 18),
(3, 1, 'Test_UPD', '2020-12-02', 18),
(4, 1, 'еуые', '2012-12-30', 17);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tvprogram`
--
ALTER TABLE `tvprogram`
  ADD CONSTRAINT `tvprogram_ibfk_1` FOREIGN KEY (`channel_id`) REFERENCES `channel` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `tvprogram`
  ADD CONSTRAINT `tvprogram_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;