REPLACE INTO `authority` (`id`, `name`) VALUES
	(1, 'ROLE_ADMIN'),
	(2, 'ROLE_USER');

INSERT INTO `user` (`id`, `activated`, `deleted`, `deletion_date`, `email`, `email_key`, `firstname`, `is_mail_verified`, `lang_key`, `lastname`, `password`, `reference`, `reset_password_key`, `sex`, `address_id`, `avatar_id`, `mobile_number`) VALUES
	(1, b'1', b'0', NULL, 'admin@mazad.com', NULL, 'Admin', 0, 'fr', 'Admin', '$2a$04$dA/H3TOPUuZ93BQIWReISOmidNcZJQ9UWL00sL.uSGFWD0h4L.QEW', 'c4ca4238a0b923820dcc509a6f75849b', NULL, 'M', NULL, NULL, NULL) ;

REPLACE INTO user_authority (`user_id`, `authority_id`) values (1,1) ;

INSERT INTO `category` (`id`, `name`, `reference`) VALUES
	(1, 'Inforamtique', 'tryjkdhjfdsefq'),
	(2, 'Clothing', 'fdwghkljsrtgserqfpq'),
	(3, 'Car and motorcycle', 'djfvhwdsfjndsgfjsdkgfqmf'),
	(4, 'Technology', 'sdhfmqjfdsjfkdf'),
	(5, 'Immovable', 'qS30cw0xHXLayuWA2Cwm');