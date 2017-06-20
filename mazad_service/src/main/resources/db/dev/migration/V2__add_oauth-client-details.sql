create table IF NOT EXISTS oauth_client_details (
  client_id VARCHAR(255) PRIMARY KEY,
  resource_ids VARCHAR(255),
  client_secret VARCHAR(255),
  scope VARCHAR(255),
  authorized_grant_types VARCHAR(255),
  web_server_redirect_uri VARCHAR(255),
  authorities VARCHAR(255),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(255)
);

INSERT INTO oauth_client_details
	(client_id, client_secret, scope, authorized_grant_types,access_token_validity)
VALUES
	("mazad", "mazadesecret", "read,write","authorization_code,refresh_token,password,implicit", 3600),
	("mazad-communication", "mazad-communication-secret", "server","client_credentials,refresh_token", null),
	("mazad-service", "mazad-service-secret", "server","client_credentials,refresh_token", null);