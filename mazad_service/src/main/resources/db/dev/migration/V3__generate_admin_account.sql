REPLACE INTO `user` ( `id`, `activated`, `email_key`, `email`, `firstname`, `lang_key`, `lastname`, `password`, `reference`, `reset_password_key`,`sex`) VALUES
	( 1, b'1', NULL, 'admin@mazad.com', 'Admin', 'fr', 'Admin', '$2a$04$dA/H3TOPUuZ93BQIWReISOmidNcZJQ9UWL00sL.uSGFWD0h4L.QEW', MD5(1), NULL,'M') ;

	REPLACE INTO user_authority (`user_id`, `authority_id`) values (1,1) ;