-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- 호스트: localhost
-- 처리한 시간: 14-07-10 17:10 
-- 서버 버전: 5.5.37
-- PHP 버전: 5.3.10-1ubuntu3.12

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 데이터베이스: `facebookbot`
--
CREATE DATABASE `facebookbot` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `facebookbot`;

-- --------------------------------------------------------

--
-- 테이블 구조 `ban`
--

CREATE TABLE IF NOT EXISTS `ban` (
  `index` int(11) NOT NULL AUTO_INCREMENT,
  `ban_id` text COLLATE utf8_unicode_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- 테이블 구조 `data`
--

CREATE TABLE IF NOT EXISTS `data` (
  `index` int(11) NOT NULL AUTO_INCREMENT,
  `adder_name` text COLLATE utf8_unicode_ci NOT NULL,
  `command` text COLLATE utf8_unicode_ci NOT NULL,
  `msg` text COLLATE utf8_unicode_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`index`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=9 ;

--
-- 테이블의 덤프 데이터 `data`
--

INSERT INTO `data` (`index`, `adder_name`, `command`, `msg`, `timestamp`) VALUES
(1, 'Jooyeon Lee', '바보', '네 권혁은 바보입니다 :)', '2014-04-24 06:57:06'),
(2, '권혁', 'ㅎㅇ', '바이', '2014-04-24 06:57:06'),
(3, '권혁', '배고파', '_NAME_아 나도 배고파 밥좀 사줘ㅠㅠ', '2014-04-24 06:57:06'),
(4, 'Jooyeon Lee', '밥사줘', '_NAME_아 뭐먹을래? 이 권혁님이 사줄께!', '2014-04-24 07:56:57'),
(5, '양효영', '빙수', '_NAME_아 덥지? 내가 빙수 쏠게!', '2014-04-24 10:21:34'),
(6, 'Jooyeon Lee', '심심해', '저도 참 심심한데요, _NAME_님 제가한번 놀아드리겠습니다.', '2014-04-24 12:17:43'),
(7, '조안나', '혁아 나 밥사줘', '그래 말만해 다 사줄게 월요일날 콜', '2014-04-24 17:37:19'),
(8, '권이지', '밥먹자', '_NAME_야 내가 밥 하러 지금 갈께', '2014-04-26 11:19:13');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
