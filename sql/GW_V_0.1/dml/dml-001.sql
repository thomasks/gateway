INSERT INTO `tb_partner` (`id`, `create_time`, `logic_delete`, `update_time`, `name`, `parent_id`, `config`) 
VALUES 
(100000, '2018-12-11 14:19:12', 0, '2018-12-11 14:19:18', '演示商户', NULL, 
'REGISTRATION.verifycode.channel=MOCK
REGISTRATION.verifycode.mode=NUM
REGISTRATION.verifycode.length=6
REGISTRATION.verifycode.template=MOCK
LOGIN.verifycode.channel=MOCK
LOGIN.verifycode.mode=NUM
LOGIN.verifycode.length=6
LOGIN.verifycode.template=MOCK
sign.alg=SHA256
sign.key=pqbe211ctqfbqo8ks3p3o7b82p01dygd679xd6iwwi0kajcgvlwzyk3mgcm69w82');


insert into tb_service (ID, NAME, VERSION, CREATE_TIME, UPDATE_TIME, LOGIC_DELETE, TYPE, URL, IS_IDEMPOTENT)
values (1, '发送验证码', 0, '2018-09-13 15:01:15:211360', '2018-09-13 15:01:15:211360', 0, 5, 'http://localhost:9091/member/api/sendVerifyCode', 0);


insert into tb_partner_service (PARTNER_ID, SERVICE_ID, CODE)
values (100000, 1, 'sendVerifyCode');
