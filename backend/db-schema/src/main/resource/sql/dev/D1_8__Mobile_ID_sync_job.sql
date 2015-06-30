ALTER TABLE `syncData` 
CHANGE COLUMN `remoteId` `remoteId` VARCHAR(255) NOT NULL ;

INSERT INTO `zebraapi`.`syncJob` (`id`, `format`, `hostname`, `dmar`, `password`, `path`, `port`, `protocol`, `user`, `localIdPosition`, `remoteIdPosition`) VALUES ('MOBILE_ID_FTP', 'CSV', '69.160.54.200', 'MID', 'zebraselector', '/zebra_supply_selector_test_csv.csv', '21', 'FTP', 'zebra@mobileidsolutions.com', 0, 2);