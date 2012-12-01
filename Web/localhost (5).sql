-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 01, 2012 at 08:37 AM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `helperdatabase`
--
CREATE DATABASE `helperdatabase` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `helperdatabase`;

-- --------------------------------------------------------

--
-- Table structure for table `twinning_questions`
--

CREATE TABLE IF NOT EXISTS `twinning_questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(20) NOT NULL,
  `phone_no` int(11) NOT NULL,
  `question` varchar(50) NOT NULL,
  `answer` varchar(50) NOT NULL,
  `flag` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `twinning_questions`
--

INSERT INTO `twinning_questions` (`id`, `email`, `phone_no`, `question`, `answer`, `flag`) VALUES
(2, 'kaidul@gmail.com', 3452352, 'Message', '', 1),
(4, '', 0, '', '', 0),
(5, 'ikaidul@hotmail.com', 0, 'Who are you?', 'dsfdsfdsfds', 1),
(6, '', 0, '', '', 0),
(7, 'ikaidul@hotmail.com', 0, 'What is your name?', '', 0),
(8, 'ikaidul@hotmail.com', 0, 'who', '', 0),
(9, '', 0, '', '', 0);
