DROP TABLE IF EXISTS `tb_partner`;
CREATE TABLE `tb_partner` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `logic_delete` bit(1) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `config` text DEFAULT NULL,  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `tb_service`;
CREATE TABLE `tb_service` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `logic_delete` bit(1) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` bigint(10) DEFAULT 5 not null,
  `url` varchar(255),
  `is_idempotent` bigint(1),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `tb_partner_service`;
CREATE TABLE `tb_partner_service` (
  `partner_id` bigint(20) NOT NULL,
  `service_id` bigint(20) NOT NULL,
  `code` varchar(255),
  PRIMARY KEY (`partner_id`,`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;



DROP TABLE IF EXISTS `tb_interact`;
CREATE TABLE `tb_interact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) DEFAULT 0 not null,
  `create_time` datetime NOT NULL,
  `logic_delete` bit(1) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `partner_id` bigint(20) NOT NULL,
  `service_id` bigint(20) NOT NULL,
  `service_code` varchar(255),
  `is_idempotent` bigint(1),
  `opr_type` varchar(255),
  `trace_no` varchar(255),
  `notify_url` varchar(255),
  `ip` varchar(255),
  request_time datetime,
  request_header text,
  request_body text,
  response_header text,
  response_body text,
  response_code varchar(255),
  response_msg text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
