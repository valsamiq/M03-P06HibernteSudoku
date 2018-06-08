-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 08-06-2018 a las 13:55:02
-- Versión del servidor: 10.1.25-MariaDB
-- Versión de PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sudoku`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `history`
--

CREATE TABLE `history` (
  `idhistory` int(11) NOT NULL,
  `time` int(11) DEFAULT NULL,
  `username` varchar(30) DEFAULT NULL,
  `idsudoku` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `history`
--

INSERT INTO `history` (`idhistory`, `time`, `username`, `idsudoku`) VALUES
(18, 666, 'Goering', 19),
(19, 666, 'Goering', 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sudoku`
--

CREATE TABLE `sudoku` (
  `idsudoku` int(11) NOT NULL,
  `level` int(11) DEFAULT NULL,
  `description` varchar(30) DEFAULT NULL,
  `problem` varchar(90) DEFAULT NULL,
  `solution` varchar(90) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sudoku`
--

INSERT INTO `sudoku` (`idsudoku`, `level`, `description`, `problem`, `solution`) VALUES
(1, 4, 'Medium', '27....4.6.687...915..61..2....8.791...7.6.2...821.4....5..83..214...685.8.3....69', '271395486368742591594618327635827914417569238982134675756983142149276853823451769'),
(19, 4, 'Medium', '27....4.6.687...915..61..2....8.791...7.6.2...821.4....5..83..214...685.8.3....69', '271395486368742591594618327635827914417569238982134675756983142149276853823451769'),
(20, 4, 'Medium', '27....4.6.687...915..61..2....8.791...7.6.2...821.4....5..83..214...685.8.3....69', '271395486368742591594618327635827914417569238982134675756983142149276853823451769');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `username` varchar(30) NOT NULL,
  `name` varchar(60) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`username`, `name`, `password`) VALUES
('Goering', 'Edward', 'admin');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`idhistory`),
  ADD KEY `fk_user` (`username`),
  ADD KEY `fk_sudoku` (`idsudoku`);

--
-- Indices de la tabla `sudoku`
--
ALTER TABLE `sudoku`
  ADD PRIMARY KEY (`idsudoku`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `history`
--
ALTER TABLE `history`
  MODIFY `idhistory` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT de la tabla `sudoku`
--
ALTER TABLE `sudoku`
  MODIFY `idsudoku` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `history`
--
ALTER TABLE `history`
  ADD CONSTRAINT `fk_sudoku` FOREIGN KEY (`idsudoku`) REFERENCES `sudoku` (`idsudoku`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_user` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
