
alter table eam_asset modify label varchar(500);

alter table eam_asset add label2 varchar(500);
update sys_sequence set value='9000' where id='asset';

INSERT INTO `sys_config` (`code`, `name`, `type`, `type_desc`, `value`, `valid`, `notes`, `create_by`, `create_time`, `update_by`, `update_time`, `deleted`, `delete_by`, `delete_time`, `version`)
VALUES
	('eam.assetImportAssetCodeKeep', '资产导入保留编码', 'ENUM', 'org.github.foxnic.web.constants.enums.system.YesNo', '1', 1, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 1);



--