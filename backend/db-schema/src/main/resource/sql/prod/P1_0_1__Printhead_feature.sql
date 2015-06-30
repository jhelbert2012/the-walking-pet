-- Alter Printer table for add printer type attribute
ALTER TABLE `printer` 
ADD COLUMN `printerType` VARCHAR(25) NULL DEFAULT NULL AFTER `name`;

-- Create new table printhead
CREATE TABLE `printHead` (
  `id` VARCHAR(255) NOT NULL,
  `dpi` VARCHAR(255) NULL,
  `listPrice` DOUBLE NULL DEFAULT NULL ,
  `partNumberDescription` VARCHAR(255) NULL,
  PRIMARY KEY (`id`));
  
-- Crete new table printers_printheads
CREATE TABLE `printers_printHeads` (
  `printerId` VARCHAR(255) NOT NULL,
  `printHeadId` VARCHAR(255) NOT NULL,
   PRIMARY KEY (`printerId`,`printHeadId`));

-- Generate the relationships between printer - printers_printerHeads - printerHead
ALTER TABLE `printers_printHeads` 
ADD INDEX `FK_ wjf2w3dhvaf9my7gvebww6zqf` (`printHeadId` ASC),
ADD INDEX `FK_u9dmwvccdy3tpparwqp5w5sqx` (`printerId` ASC);

ALTER TABLE `printers_printHeads` 
ADD CONSTRAINT `FK_u9dmwvccdy3tpparwqp5w5sqx`
  FOREIGN KEY (`printerId`)
  REFERENCES `printer` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT,
  
ADD CONSTRAINT `FK_ wjf2w3dhvaf9my7gvebww6zqf`
  FOREIGN KEY (`printHeadId`)
  REFERENCES `printHead` (`id`)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;
 