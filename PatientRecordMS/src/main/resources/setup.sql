CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


INSERT INTO `role` (`name`) VALUES ('ADMIN');
INSERT INTO `role` (`name`) VALUES ('DOCTOR');
INSERT INTO `role` (`name`) VALUES ('NURSE');


CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);


INSERT INTO `user` (`username`, `password`) VALUES ('admin', 'admin_password');
INSERT INTO `user` (`username`, `password`) VALUES ('mana', 'doc_password');
INSERT INTO `user` (`username`, `password`) VALUES ('bana', 'doc_password');
INSERT INTO `user` (`username`, `password`) VALUES ('rani', 'nurse_password');


CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,

);

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_DOCTOR'),
(3, 'ROLE_NURSE');