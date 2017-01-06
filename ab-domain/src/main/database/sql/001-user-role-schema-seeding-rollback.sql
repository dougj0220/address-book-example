DELETE FROM user_role WHERE user_id = 1 AND role_id = 1;
DELETE FROM user_role WHERE user_id = 1 AND role_id = 2;
DELETE FROM user_role WHERE user_id = 1 AND role_id = 3;

DROP TABLE user_role;

DELETE FROM role WHERE id = 1;
DELETE FROM role WHERE id = 2;
DELETE FROM role WHERE id = 3;

DROP TABLE role;

DELETE FROM `user` WHERE id = 1;

DROP TABLE `user`;