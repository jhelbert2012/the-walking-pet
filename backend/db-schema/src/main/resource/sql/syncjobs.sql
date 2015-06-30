
ALTER TABLE `zebraapi`.`syncJob` 
ADD COLUMN `localIdRESTAttribute` VARCHAR(45) NULL AFTER `remoteIdPosition`,
ADD COLUMN `remoteIdRESTAttribute` VARCHAR(45) NULL AFTER `localIdRESTAttribute`;

ALTER TABLE `zebraapi`.`syncJob` 
CHANGE COLUMN `path` `path` VARCHAR(255) NULL DEFAULT NULL ;

INSERT INTO `zebraapi`.`syncJob` (`id`, `format`, `hostname`, `dmar`, `password`, `path`, `port`, `protocol`, `user`, `localIdPosition`, `remoteIdPosition`, `localIdRESTAttribute`, `remoteIdRESTAttribute`) VALUES 
('PCC_FTP','TSV','ftp.pcconnection.com','PCC','w8bre=Rawa','/Zebra1.csv',21,'FTP','zebraftp',1,0,NULL,NULL),
('CDW_FTP','TSV_ZIP','pricefiles.cdw.com','CDW','jeztqNNy','/Zebra.zip',21,'FTP','Zebra',1,2,NULL,NULL),
('MOBILE_ID_FTP','CSV','69.160.54.200','MID','zebraselector','/zebra_supply_selector_test_csv.csv',21,'FTP','zebra@mobileidsolutions.com',1,0,NULL,NULL),
('AB_AND_RFID_REST','REST','forms.netsuite.com','AB_RFID',NULL,'/app/site/hosting/scriptlet.nl?script=135&deploy=1&compid=838870&h=b5df9b3816badf2660cf&action=get_items',443,'HTTPS',NULL,0,0,'id','partnerId'),
('ARROWHEAD_HTTP','CSV','integratedsolutions.mobi','AH',NULL,'/phx_zebra/phx-zebra-sku.csv',80,'HTTP',NULL,0,1,NULL,NULL);

