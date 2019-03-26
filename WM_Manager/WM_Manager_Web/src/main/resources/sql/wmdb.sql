CREATE USER wmdb IDENTIFIED BY 1475369;
GRANT CONNECT,
RESOURCE TO wmdb;
-- ----------------------------
-- Table structure for TEST
-- ----------------------------
CREATE TABLE test ( id VARCHAR ( 20 ) NOT NULL PRIMARY KEY, name VARCHAR ( 50 ), password VARCHAR ( 50 ) );
-- ----------------------------
-- Table structure for WM_USER
-- ----------------------------
CREATE TABLE wm_user (
	id VARCHAR ( 20) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 50 ),
	creator VARCHAR ( 50 ),
	createTime VARCHAR ( 50 ),
	updaterId VARCHAR ( 50 ),
	updater VARCHAR ( 50 ),
	updateTime VARCHAR ( 50 ),
	
	username VARCHAR ( 50 ),
	password VARCHAR ( 50 ),
	realname VARCHAR ( 50 ),
	department VARCHAR ( 50 ),
	tel VARCHAR ( 50 ),
	issuper VARCHAR ( 50 ),
	lastlogintime VARCHAR ( 50 ),
	applogin VARCHAR ( 50 ),
	departmentid VARCHAR ( 50 ) ,
	state VARCHAR ( 50 ) 
);
-- ----------------------------
-- Table structure for WM_ROLES
-- ----------------------------
CREATE TABLE wm_roles (
	id VARCHAR ( 20 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 50 ),
	creator VARCHAR ( 50 ),
	createTime VARCHAR ( 50 ),
	updaterId VARCHAR ( 50 ),
	updater VARCHAR ( 50 ),
	updateTime VARCHAR ( 50 ),
	
	name VARCHAR ( 50 ),
	description VARCHAR ( 50 ),
	TYPE VARCHAR ( 50 ),
	indexorder VARCHAR ( 50 )	
);
-- ----------------------------
-- Table structure for WM_USER_ROLES
-- ----------------------------
CREATE TABLE wm_user_roles (
	id VARCHAR ( 20 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 50 ),
	creator VARCHAR ( 50 ),
	createTime VARCHAR ( 50 ),
	updaterId VARCHAR ( 50 ),
	updater VARCHAR ( 50 ),
	updateTime VARCHAR ( 50 ),
	
	roleid VARCHAR ( 20 ),
	userid VARCHAR ( 20 )
);
-- ----------------------------
-- Foreign Keys structure for table WM_USER_ROLES
-- ----------------------------
ALTER TABLE WM_USER_ROLES ADD CONSTRAINT ROLEID FOREIGN KEY (ROLEID) REFERENCES WM_ROLES (ID) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
ALTER TABLE WM_USER_ROLES ADD CONSTRAINT USERID FOREIGN KEY (USERID) REFERENCES WM_USER (ID) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Table structure for WM_PERMISSION
-- ----------------------------
CREATE TABLE wm_permission (
	id VARCHAR ( 20 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 50 ),
	creator VARCHAR ( 50 ),
	createTime VARCHAR ( 50 ),
	updaterId VARCHAR ( 50 ),
	updater VARCHAR ( 50 ),
	updateTime VARCHAR ( 50 ),
	
	name VARCHAR ( 50 ),
	nameValue VARCHAR ( 50 ),
	description VARCHAR ( 50 ),
	modular VARCHAR ( 50 ),
	indexorder VARCHAR ( 50 ) 
);
-- ----------------------------
-- Table structure for WM_DEPARTMENT
-- ----------------------------
CREATE TABLE wm_department (
	id VARCHAR ( 20 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 50 ),
	creator VARCHAR ( 50 ),
	createTime VARCHAR ( 50 ),
	updaterId VARCHAR ( 50 ),
	updater VARCHAR ( 50 ),
	updateTime VARCHAR ( 50 ),
	
	name VARCHAR ( 50 ),
	tel VARCHAR ( 50 ),
	deptnumber VARCHAR ( 50 ),
	parentid VARCHAR ( 50 )
);
