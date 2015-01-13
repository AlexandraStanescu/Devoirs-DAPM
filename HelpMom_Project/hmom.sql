-- phpMyAdmin SQL Dump
-- version 4.0.10.6
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 13, 2015 at 11:58 AM
-- Server version: 5.5.35-cll-lve
-- PHP Version: 5.4.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `hmom`
--

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE IF NOT EXISTS `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `title` text NOT NULL,
  `body` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`id`, `topic_id`, `user_id`, `title`, `body`) VALUES
(1, 1, 5, 'hhhhhh', 'bgkjhfsghjhytcvg'),
(2, 1, 5, 'horea', 'bla'),
(3, 1, 5, 'hh', 'dsfgj'),
(4, 2, 5, 'hdhjkjd', 'do ghjkfdfhtfbjy'),
(5, 2, 7, 'bla bla bla', 'bhdbdjdkkdbhks'),
(6, 2, 5, 'hjhgfg', 'ghjkggh'),
(7, 2, 5, 'test post', 'bls bla bla'),
(8, 2, 5, 'testu lu hirea', 'bka bla bla'),
(9, 2, 5, 'hehe', 'hahaha\n'),
(10, 2, 5, 'horea', 'hfdghhiinbgnhujbgjbh');

-- --------------------------------------------------------

--
-- Table structure for table `topics`
--

CREATE TABLE IF NOT EXISTS `topics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `topics`
--

INSERT INTO `topics` (`id`, `title`) VALUES
(1, 'Psychology'),
(2, 'Baby'),
(3, 'Family'),
(4, 'Job'),
(5, 'Beauty'),
(6, 'Nutrition'),
(7, 'Others');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`) VALUES
(1, 'h@c.com', 'bla'),
(2, 'h@c.com', 'bla'),
(3, 'h@c.com', 'bla'),
(5, 'horea@mail.com', 'hhhhhh'),
(6, 'h@x.com', 'hhhhhhh'),
(7, 'test@mail.com', 'asdfgh'),
(8, 'hhhhh', '123456789'),
(9, 'h@h.com', 'qwerty'),
(10, 'x@y.com', 'ggg'),
(11, 'mada', 'mmmmmm');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
