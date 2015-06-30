-- alter Sync Job table for add REST attributes
ALTER TABLE `zebraapi`.`syncJob` 
ADD COLUMN `localIdRESTAttribute` VARCHAR(45) NULL AFTER `remoteIdPosition`,
ADD COLUMN `remoteIdRESTAttribute` VARCHAR(45) NULL AFTER `localIdRESTAttribute`;

ALTER TABLE `zebraapi`.`syncJob` 
CHANGE COLUMN `path` `path` VARCHAR(255) NULL DEFAULT NULL ;

-- Insert American Barcode and RFID Partner Job
INSERT INTO `zebraapi`.`syncJob`
(`id`,
`format`,
`hostname`,
`dmar`,
`password`,
`path`,
`port`,
`protocol`,
`user`,
`localIdPosition`,
`remoteIdPosition`,
`localIdRESTAttribute`,
`remoteIdRESTAttribute`)
VALUES 
('AB_AND_RFID_REST', 
'REST', 
'forms.netsuite.com', 
'AB_RFID', 
NULL, 
'/app/site/hosting/scriptlet.nl?script=135&deploy=1&compid=838870&h=b5df9b3816badf2660cf&action=get_items', 
'443', 
'HTTPS', 
NULL, '0', 
'0', 
'id', 
'partnerId');
