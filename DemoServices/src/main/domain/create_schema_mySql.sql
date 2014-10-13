
DROP SCHEMA IF EXISTS `SolarSystem`;
CREATE SCHEMA `SolarSystem`;
USE `SolarSystem`;

-- DROP SCHEMA IF EXISTS `SolarSystemTest`;
-- CREATE SCHEMA `SolarSystemTest`;
-- USE `SolarSystemTest`;


CREATE TABLE `SolarBody` ( 
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` CHAR(40) NOT NULL,
  `orbitSolarBodyId` INT,
  `bodyType` enum('Sun','Planet') NOT NULL,
  `description` VARCHAR(4000) NOT NULL,
  `radius` BIGINT NOT NULL,
  `mass` FLOAT NOT NULL,
  `orbitDistance` BIGINT,
--  `lengthOfDay` TIME NOT NULL,
--  `lengthOfYear` TIME NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB;
ALTER TABLE `SolarBody` ADD CONSTRAINT `FK_SolarBody_OrbitSolarBodyId` FOREIGN KEY ( `orbitSolarBodyId`) REFERENCES `SolarBody` (`id`);

CREATE TABLE `SolarBodyImage` ( 
  `solarBodyId` INT NOT NULL,
  `image` MEDIUMBLOB NOT NULL,
  `filename` VARCHAR(255) NOT NULL,
  `contentType` VARCHAR(255) NOT NULL,
  PRIMARY KEY (solarBodyId),
  CONSTRAINT `FK_SolarBody_solarBodyImageId`
    FOREIGN KEY ( `solarBodyId`) REFERENCES `SolarBody` (`id`)  
) ENGINE=InnoDB;

CREATE TABLE `SolarBodyLink` ( 
  `id` INT NOT NULL AUTO_INCREMENT,
  `solarBodyId` INT NOT NULL,
  `linkType` enum('Wiki','NASA','Other') NOT NULL DEFAULT 'Other',
  `name` CHAR(40),
  `url` VARCHAR(256) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT `FK_SolarBodyLink_SolarBodyId`
    FOREIGN KEY ( `solarBodyId`) REFERENCES `SolarBody` (`id`)  
) ENGINE=InnoDB;

SET autocommit=0;

INSERT INTO `SolarBody` VALUES (1,'Sun',null,'Sun',
  'The Sun is the star at the center of the Solar System.',
  696342,1.989E30,null);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (1, 'sun.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/sun.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 1,'Wiki',null,'http://en.wikipedia.org/wiki/Sun'),
    ( 1,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Sun');

INSERT INTO `SolarBody` VALUES (2,'Mercury',1,'Planet',
  'Mercury is the smallest and closest to the Sun of the eight planets in the Solar System, with an orbital period of about 88 Earth days.',
  2440,328.55E21,5791000);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (2, 'mercury.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/mercury.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 2,'Wiki',null,'http://en.wikipedia.org/wiki/Mercury_(planet)'),
    ( 2,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Mercury');

INSERT INTO `SolarBody` VALUES (3,'Venus',1,'Planet',
  'Venus is the second planet from the Sun, orbiting it every 224.7 Earth days. It has no natural satellite. It is named after the Roman goddess of love and beauty.',
  6052,4.867E24,108200000);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (3, 'venus.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/venus.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 3,'Wiki',null,'http://en.wikipedia.org/wiki/Venus'),
    ( 3,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Venus');


INSERT INTO `SolarBody` VALUES (4,'Earth',1,'Planet',
  'Earth, also known as "the Earth" and "the World" and sometimes referred to as the "Blue Planet", the "Blue Marble", Terra or "Gaia", is the third-most distant planet from the Sun, the densest planet in the Solar System and the only celestial body known to accommodate life.',
  6371,5.972E24,149600000);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (4, 'earth.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/earth.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 4,'Wiki',null,'http://en.wikipedia.org/wiki/Earth'),
    ( 4,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Earth'),
    ( 4,'Other','Google Earth','http://www.google.com/earth/');


INSERT INTO `SolarBody` VALUES (5,'Mars',1,'Planet',
  'Mars is the fourth planet from the Sun and the second smallest planet in the Solar System, after Mercury.',
  3396,6.4185E23,227939100);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (5, 'mars.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/mars.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 5,'Wiki',null,'http://en.wikipedia.org/wiki/Mars'),
    ( 5,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Mars'),
    ( 5,'Other','Google Mars','http://www.google.com/mars/'),
    ( 5,'Other','NASA JPL','http://mars.jpl.nasa.gov/'),
    ( 5,'Other','Mars Rovers','http://marsrover.nasa.gov/home/index.html'),
    ( 5,'Other','Curiosity Rover','http://mars.jpl.nasa.gov/msl/');

    
INSERT INTO `SolarBody` VALUES (6,'Jupiter',1,'Planet',
  'Jupiter is the fifth planet from the Sun and the largest planet in the Solar System. It is a gas giant with mass one-thousandth of that of the Sun but is two and a half times the mass of all the other planets in the Solar System combined.',
  69911,1.898E27,778500000);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (6, 'jupiter.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/jupiter.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 6,'Wiki',null,'http://en.wikipedia.org/wiki/Jupiter'),
    ( 6,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Jupiter');

    
INSERT INTO `SolarBody` VALUES (7,'Saturn',1,'Planet',
  'Saturn is the sixth planet from the Sun and the second largest planet in the Solar System, after Jupiter. Named after the Roman god of agriculture, its astronomical symbol represents the god''s sickle.',
  58232,4.867E24,1433000000);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (7, 'saturn.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/saturn.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 7,'Wiki',null,'http://en.wikipedia.org/wiki/Saturn'),
    ( 7,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Saturn');

    
INSERT INTO `SolarBody` VALUES (8,'Uranus',1,'Planet',
  'Uranus is the seventh planet from the Sun. It has the third-largest planetary radius and fourth-largest planetary mass in the Solar System.',
  25362,86.81E24,2877200000);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (8, 'uranus.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/uranus.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 8,'Wiki',null,'http://en.wikipedia.org/wiki/Uranus'),
    ( 8,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Uranus');

    
INSERT INTO `SolarBody` VALUES (9,'Neptune',1,'Planet',
  'Neptune is the eighth and farthest planet from the Sun in the Solar System. It is the fourth-largest planet by diameter and the third-largest by mass. Among the gaseous planets in the solar system, Neptune is the most dense.',
  24622,102.4E24,3004400000);
INSERT INTO `SolarBodyImage` (solarBodyId, filename, contentType, image)
  VALUES (9, 'neptune.jpg', 'image/jpeg', LOAD_FILE('D:/Dev/Demo/DemoServices/src/main/domain/images/neptune.jpg'));
INSERT INTO `SolarBodyLink` ( `solarBodyId`, `linkType`, `name`, `url`)
  VALUES
    ( 9,'Wiki',null,'http://en.wikipedia.org/wiki/Neptune'),
    ( 9,'NASA',null,'http://solarsystem.nasa.gov/planets/profile.cfm?Object=Neptune');

    
COMMIT;


CREATE TABLE `User` ( 
  `username` CHAR(40) NOT NULL,
  `password` CHAR(40),
  `name` CHAR(40) NOT NULL,
  `role` enum('Admin','Guest') NOT NULL,
  PRIMARY KEY (username)
) ENGINE=InnoDB;

INSERT INTO `User` ( `username`, `password`, `name`, `role`)
  VALUES
    ( 'guest',null,'Guest','Guest'),
    ( 'admin','admin','God!','Admin');

COMMIT;