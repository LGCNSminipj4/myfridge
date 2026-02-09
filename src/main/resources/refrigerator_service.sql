CREATE TABLE users(
	user_id		VARCHAR(20) PRIMARY KEY NOT NULL,
	name 			VARCHAR(20) NOT NULL,
	password		VARCHAR(255) NOT NULL,
	birth_year	YEAR			NOT NULL
);

CREATE TABLE prefer_tag(
	tag_id		INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	tag_type		ENUM('CULTURE', 'COOK_METHOD', 'LIFESTYLE') NOT NULL,
	tag_name		VARCHAR(50) NOT NULL,
	UNIQUE		(tag_type, tag_name)
);

CREATE TABLE users_prefer(
	user_id		VARCHAR(20)	NOT NULL,
	tag_id		INT 			NOT NULL,
	PRIMARY KEY (user_id, tag_id),
	
	FOREIGN KEY(user_id) REFERENCES users (user_id)
	ON DELETE CASCADE,
	FOREIGN KEY(tag_id) REFERENCES prefer_tag(tag_id)
	ON UPDATE CASCADE 
	ON DELETE CASCADE
);

CREATE TABLE category(
	category_id			INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	category_name		VARCHAR(50)	NOT NULL,
	storage_condition	VARCHAR(50),
	UNIQUE	(category_name)
);

CREATE TABLE ingredients (
	ingredients_id		INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
	ingredients_name  VARCHAR(100) NOT NULL,
	amount				INT,
	storage_date		DATE,
	expiration_date	DATE,
	custom_date			DATE,
	category_id			INT,
	user_id				VARCHAR(20) NOT NULL,
	status				ENUM('ACTIVE','DELETED','CONSUMED','DISCARDED') NOT NULL DEFAULT 'ACTIVE',
	
	FOREIGN KEY(user_id) REFERENCES users (user_id)
	ON DELETE CASCADE,
	
	FOREIGN KEY(category_id) REFERENCES category (category_id)
	ON UPDATE CASCADE 
	ON DELETE SET NULL
);