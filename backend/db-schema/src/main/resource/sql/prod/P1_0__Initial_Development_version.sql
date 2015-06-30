create table applications (Partner varchar(255) not null, applications varchar(255));
create table cleaningKit (id varchar(255) not null, description text, dmarSku varchar(45), name varchar(255), primary key (id));
CREATE TABLE `material` (
  `id` varchar(255) NOT NULL,
  `additionalInfo` varchar(255) DEFAULT NULL,
  `adhesive` varchar(255) DEFAULT NULL,
  `amountInFeetPerRoll` int(11) DEFAULT NULL,
  `amountPerRoll` int(11) DEFAULT NULL,
  `cardTechnology` varchar(255) DEFAULT NULL,
  `cardThickness` int(11) DEFAULT NULL,
  `cardsPerBox` int(11) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `description` text,
  `detailedSpecs` varchar(255) DEFAULT NULL,
  `dmarDescription` varchar(255) DEFAULT NULL,
  `dmarSku` varchar(255) DEFAULT NULL,
  `featured` int(11) DEFAULT NULL,
  `industry` varchar(255) DEFAULT NULL,
  `length` double DEFAULT NULL,
  `materialType` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `perforated` int(11) DEFAULT NULL,
  `printTechnology` varchar(255) DEFAULT NULL,
  `productType` varchar(255) DEFAULT NULL,
  `quantityPerCarton` int(11) DEFAULT NULL,
  `width` double DEFAULT NULL,
  `partNumberDescription` varchar(255) DEFAULT NULL,
  `listPrice` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table material_highRibbons (materialId varchar(255) not null, ribbonId varchar(255) not null, primary key (materialId, ribbonId));
create table material_standardRibbons (materialId varchar(255) not null, ribbonId varchar(255) not null, primary key (materialId, ribbonId));
create table partner (id varchar(255) not null, description text, logo varchar(255), name varchar(255), type varchar(255), url varchar(255), validated integer, primary key (id)) DEFAULT CHARSET=utf8;
create table principal (name varchar(20) not null, primary key (name));
create table principalRights (Principal varchar(20) not null, rights varchar(255));
create table printer (id varchar(255) not null, imageLink varchar(255), name varchar(255), primary key (id));
create table printerPrintTechnologies (printerId varchar(255) not null, printTechnologies varchar(255), printTechnologies_ORDER integer not null, primary key (printerId, printTechnologies_ORDER));
create table printers_cleaningKits (printerId varchar(255) not null, cleaningKitId varchar(255) not null);
create table printers_materials (printerId varchar(255) not null, materialId varchar(255) not null);
create table printers_ribbons (printerId varchar(255) not null, ribbonId varchar(255) not null);
create table regions (Partner varchar(255) not null, regions varchar(255));
create table ribbon (id varchar(255) not null, description varchar(255), detailedSpecs varchar(255), dmarSku varchar(45), imagesPerRoll integer, length double precision, name varchar(255), printTechnology varchar(255), ribbonApplication varchar(255), ribbonColor varchar(255), width double precision, `listPrice` double DEFAULT NULL, `partNumberDescription` varchar(255), primary key (id));
create table syncData (jobId varchar(45) not null, localId varchar(45) not null, remoteId varchar(45) not null, primary key (jobId, localId, remoteId));
create table syncJob (id varchar(45) not null, format varchar(255), hostname varchar(255), dmar varchar(255), password varchar(255), path varchar(45), port integer, protocol varchar(255), user varchar(255), localIdPosition integer, remoteIdPosition integer, primary key (id));
create table user (username varchar(20) not null, password varchar(255), preferredLanguage varchar(5), sessionUUID varchar(255), principal varchar(20) not null, primary key (username));
create table verticalMarkets (Partner varchar(255) not null, verticalMarkets varchar(255));
alter table applications add constraint FK_fcc5xqr405r7mq7nmhnijknna foreign key (Partner) references partner (id);
alter table material_highRibbons add constraint FK_ryghvq0csn408htqnja5blvix foreign key (ribbonId) references ribbon (id);
alter table material_highRibbons add constraint FK_9a0o4d32bmks3f3hbgdt59781 foreign key (materialId) references material (id);
alter table material_standardRibbons add constraint FK_37gnjcidd6j378ysk2r6w7c1u foreign key (ribbonId) references ribbon (id);
alter table material_standardRibbons add constraint FK_f8svbn2i2mrf87xkxvhtfaan8 foreign key (materialId) references material (id);
alter table principalRights add constraint FK_mnf09cvfcsclmfyqa4pmu84lr foreign key (Principal) references principal (name);
alter table printerPrintTechnologies add constraint FK_e09vdidudy0dw5cv9sw7gbbgl foreign key (printerId) references printer (id);
alter table printers_cleaningKits add constraint FK_772kbva99f80x56sjhebuuobg foreign key (cleaningKitId) references cleaningKit (id);
alter table printers_cleaningKits add constraint FK_rwys2u8h2cu5odt8eobfhj4xp foreign key (printerId) references printer (id);
alter table printers_materials add constraint FK_fjftj9ctk6npohlgm6cg9yuaj foreign key (materialId) references material (id);
alter table printers_materials add constraint FK_tn5pgpal75eb03rrrv9x62blt foreign key (printerId) references printer (id);
alter table printers_ribbons add constraint FK_k4lwnjx31chlwinwnew55770x foreign key (ribbonId) references ribbon (id);
alter table printers_ribbons add constraint FK_7slei8bnl2wp50vr92m2lofbo foreign key (printerId) references printer (id);
alter table regions add constraint FK_t5eru3mwjtabyk1vi2nd5u5to foreign key (Partner) references partner (id);
alter table syncData add constraint FK_srtl851ul2ughbo12999l9mko foreign key (jobId) references syncJob (id);
alter table user add constraint FK_konrexqncmw6p8tc97r2f2nd3 foreign key (principal) references principal (name);
alter table verticalMarkets add constraint FK_88lhom64kjsqhedb6p0hvy7n7 foreign key (Partner) references partner (id);

CREATE VIEW `materialview` AS select `material`.`id` AS `id`,`material`.`additionalInfo` AS `additionalInfo`,`material`.`adhesive` AS `adhesive`,`material`.`amountInFeetPerRoll` AS `amountInFeetPerRoll`,`material`.`amountPerRoll` AS `amountPerRoll`,`material`.`cardTechnology` AS `cardTechnology`,`material`.`cardThickness` AS `cardThickness`,`material`.`cardsPerBox` AS `cardsPerBox`,`material`.`color` AS `color`,`material`.`description` AS `description`,`material`.`detailedSpecs` AS `detailedSpecs`,`material`.`dmarDescription` AS `dmarDescription`,`material`.`dmarSku` AS `dmarSku`,`material`.`featured` AS `featured`,`material`.`industry` AS `industry`,`material`.`length` AS `length`,`material`.`materialType` AS `materialType`,`material`.`name` AS `name`,`material`.`perforated` AS `perforated`,`material`.`printTechnology` AS `printTechnology`,`material`.`productType` AS `productType`,`material`.`quantityPerCarton` AS `quantityPerCarton`,`material`.`width` AS `width`,(select `d`.`remoteId` from (`syncJob` `j` join `syncData` `d`) where ((`d`.`localId` = `material`.`id`) and (`d`.`jobId` = `j`.`id`) and (`j`.`dmar` = 'PCC'))) AS `pccId` from `material`;
