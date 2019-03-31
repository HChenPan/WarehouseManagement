CREATE USER wmdb IDENTIFIED BY 1475369;
GRANT CONNECT,RESOURCE TO wmdb;
ALTER USER wmdb QUOTA UNLIMITED ON "USERS";
-- ----------------------------
-- Table structure for TEST
-- ----------------------------
CREATE TABLE test ( 
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	name VARCHAR ( 255 ), 
	password VARCHAR ( 255 )
);
-- ----------------------------
-- Table structure for WM_USER
-- ----------------------------
CREATE TABLE wm_user (
	id VARCHAR (255) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),

	username VARCHAR ( 255 ),
	password VARCHAR ( 255 ),
	realname VARCHAR ( 255 ),
	department VARCHAR ( 255 ),
	tel VARCHAR ( 255 ),
	issuper VARCHAR ( 255 ),
	lastlogintime VARCHAR ( 255 ),
	applogin VARCHAR ( 255 ),
	departmentid VARCHAR ( 255 ) ,
	state VARCHAR ( 255 ) 
);

INSERT INTO "WMDB"."WM_USER"("ID", "CREATORID", "CREATOR", "CREATETIME", "UPDATERID", "UPDATER", "UPDATETIME", "USERNAME", "PASSWORD", "REALNAME", "DEPARTMENT", "TEL", "ISSUPER", "LASTLOGINTIME", "APPLOGIN", "DEPARTMENTID", "STATE") VALUES ('005afb46a05d42a6802400c74ff6e84e', '001', 'admin', '2019-03-20', NULL, NULL, NULL, 'admin', 'c2b632dc87637a5fd03fdf9b61693d17', '管理员', '全厂', '17764347918', '1', '2019-03-20', '0', '0001', 'normal');

-- ----------------------------
-- Table structure for WM_ROLES
-- ----------------------------
CREATE TABLE wm_roles (
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),

	name VARCHAR ( 255 ),
	description VARCHAR ( 255 ),
	TYPE VARCHAR ( 255 ),
	indexorder VARCHAR ( 255 )	
);
-- ----------------------------
-- Table structure for WM_USER_ROLES
-- ----------------------------
CREATE TABLE wm_user_roles (
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),

	roleid VARCHAR ( 255 ),
	userid VARCHAR ( 255 )
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
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),

	name VARCHAR ( 255 ),
	nameValue VARCHAR ( 255 ),
	description VARCHAR ( 255 ),
	modular VARCHAR ( 255 ),
	indexorder VARCHAR ( 255 ) 
);
-- ----------------------------
-- Table structure for WM_DEPARTMENT
-- ----------------------------
CREATE TABLE wm_department (
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),

	name VARCHAR ( 255 ),
	tel VARCHAR ( 255 ),
	deptnumber VARCHAR ( 255 ),
	parentid VARCHAR ( 255 )
);
-- ----------------------------
-- Table structure for WM_DICTIONARYS
-- ----------------------------
CREATE TABLE wm_dictionarys (
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),

	DCODE VARCHAR ( 255 ),
	DNAME VARCHAR ( 255 ),
	FLAG VARCHAR ( 255 )
);

-- ----------------------------
-- Table structure for WM_DICTIONARYSCHILD
-- ----------------------------
CREATE TABLE wm_dictionaryschild (
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),

	CODE VARCHAR ( 255 ),
	DCODE VARCHAR ( 255 ),
	FLAG VARCHAR ( 255 ),
	NAME VARCHAR ( 255 ),
	NOTE VARCHAR ( 255 ),
	DICTIONARYS_ID VARCHAR ( 255 )
);

-- ----------------------------
-- Foreign Keys structure for table WM_DICTIONARYSCHILD
-- ----------------------------
ALTER TABLE WM_DICTIONARYSCHILD ADD CONSTRAINT DICTIONARYSID FOREIGN KEY (DICTIONARYS_ID) REFERENCES WM_DICTIONARYS (ID) ON DELETE CASCADE NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Table structure for WM_LOGS
-- ----------------------------
CREATE TABLE wm_logs (
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),

	DESCRIPTION VARCHAR ( 255 ),
	FLAGID VARCHAR ( 255 ),
	IPADDRESS VARCHAR ( 255 ),
	NAME VARCHAR ( 255 ),
	OLDCONTENT VARCHAR2 ( 2048 ),
	OPTCONTENT VARCHAR2 ( 2048 ),
	PARAMS VARCHAR ( 255 ),
	REALNAME VARCHAR ( 255 )	
);
-- ----------------------------
-- Table structure for WM_SPAREPARTCODE
-- ----------------------------
CREATE TABLE wm_sparepartcode (
	id VARCHAR ( 255 ) NOT NULL PRIMARY KEY,
	creatorId VARCHAR ( 255 ),
	creator VARCHAR ( 255 ),
	createTime VARCHAR ( 255 ),
	updaterId VARCHAR ( 255 ),
	updater VARCHAR ( 255 ),
	updateTime VARCHAR ( 255 ),
 	
	parentid VARCHAR2(255),
	parentcode VARCHAR2(255), 	
	code VARCHAR2(255), 	
	currencytype VARCHAR2(255), 
	currencyunit VARCHAR2(255), 	
	devicecode VARCHAR2(255), 
	hostname VARCHAR2(255), 
	modelspecification VARCHAR2(255), 
	name VARCHAR2(255), 	
	planprice VARCHAR2(255), 
	purchasetime VARCHAR2(255), 
	remark VARCHAR2(255), 
	spareparttype VARCHAR2(255), 
	spareparttypecode VARCHAR2(255), 
	stockmin VARCHAR2(255), 
	supplycycle VARCHAR2(255), 
	tuhao VARCHAR2(255), 
	unit VARCHAR2(255), 
	ywname VARCHAR2(255), 	
	description VARCHAR2(255)
);