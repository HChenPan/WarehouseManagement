CREATE USER wmdb IDENTIFIED BY 1475369;
GRANT CONNECT,
RESOURCE TO wmdb;
ALTER USER wmdb QUOTA UNLIMITED ON "USERS";


-- ----------------------------
-- table structure for test
-- ----------------------------
CREATE TABLE test ( 
	id VARCHAR2 ( 255 byte ) NOT NULL primary key, 
	name VARCHAR2 ( 255 byte ), 
	password VARCHAR2 ( 255 byte ) 
);

-- ----------------------------
-- table structure for wm_applytransfer
-- ----------------------------
CREATE TABLE wm_applytransfer (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	applytransfercode VARCHAR2 ( 255 byte ),
	dcck VARCHAR2 ( 255 byte ),
	dcckcode VARCHAR2 ( 255 byte ),
	dcckid VARCHAR2 ( 255 byte ),
	drck VARCHAR2 ( 255 byte ),
	drckcode VARCHAR2 ( 255 byte ),
	drckid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	realmoney VARCHAR2 ( 255 byte ),
	sbdate VARCHAR2 ( 255 byte ),
	sbmoney VARCHAR2 ( 255 byte ),
	sbstatus VARCHAR2 ( 255 byte ),
	sbunit VARCHAR2 ( 255 byte ),
	sbunitid VARCHAR2 ( 255 byte ),
	sqrid VARCHAR2 ( 255 byte ),
	sqrname VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_approvalrecord
-- ----------------------------
CREATE TABLE wm_approvalrecord (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	spadvice VARCHAR2 ( 255 byte ),
	spid VARCHAR2 ( 255 byte ),
	spnodecode VARCHAR2 ( 255 byte ),
	spresult VARCHAR2 ( 255 byte ),
	sptime VARCHAR2 ( 255 byte ),
	sptypecode VARCHAR2 ( 255 byte ),
	spuser VARCHAR2 ( 255 byte ),
	spuserid VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_buy
-- ----------------------------
CREATE TABLE wm_buy (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	buycode VARCHAR2 ( 255 byte ),
	buydate VARCHAR2 ( 255 byte ),
	buyname VARCHAR2 ( 255 byte ),
	buysqr VARCHAR2 ( 255 byte ),
	buysqrid VARCHAR2 ( 255 byte ),
	buysummoney VARCHAR2 ( 255 byte ),
	buytype VARCHAR2 ( 255 byte ),
	buyunit VARCHAR2 ( 255 byte ),
	buyunitid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	spcode VARCHAR2 ( 255 byte ),
	spjsdate VARCHAR2 ( 255 byte ),
	spstatus VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_buyer
-- ----------------------------
CREATE TABLE wm_buyer (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	account VARCHAR2 ( 255 byte ),
	address VARCHAR2 ( 255 byte ),
	bank VARCHAR2 ( 255 byte ),
	buyercode VARCHAR2 ( 255 byte ),
	buyername VARCHAR2 ( 255 byte ),
	fax VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	legalrepresentative VARCHAR2 ( 255 byte ),
	phone VARCHAR2 ( 255 byte ),
	postcode VARCHAR2 ( 255 byte ),
	registeredcapital VARCHAR2 ( 255 byte ),
	remark VARCHAR2 ( 255 byte ),
	supplyscope VARCHAR2 ( 255 byte ),
	taxid VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_buylist
-- ----------------------------
CREATE TABLE wm_buylist (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	buycode VARCHAR2 ( 255 byte ),
	buymoney VARCHAR2 ( 255 byte ),
	buyname VARCHAR2 ( 255 byte ),
	buynum VARCHAR2 ( 255 byte ),
	buyprice VARCHAR2 ( 255 byte ),
	buytype VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	plancode VARCHAR2 ( 255 byte ),
	synum VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_callslip
-- ----------------------------
CREATE TABLE wm_callslip (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	application VARCHAR2 ( 255 byte ),
	applydate VARCHAR2 ( 255 byte ),
	callslipcode VARCHAR2 ( 255 byte ),
	callsliptype VARCHAR2 ( 255 byte ),
	department VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	llcode VARCHAR2 ( 255 byte ),
	money VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	outtime VARCHAR2 ( 255 byte ),
	outuserid VARCHAR2 ( 255 byte ),
	outusername VARCHAR2 ( 255 byte ),
	projectname VARCHAR2 ( 255 byte ),
	projectno VARCHAR2 ( 255 byte ),
	realname VARCHAR2 ( 255 byte ),
	spcode VARCHAR2 ( 255 byte ),
	spjsdate VARCHAR2 ( 255 byte ),
	status VARCHAR2 ( 255 byte ),
	storehouse VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_callslipgoods
-- ----------------------------
CREATE TABLE wm_callslipgoods (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	buycode VARCHAR2 ( 255 byte ),
	callslipcode VARCHAR2 ( 255 byte ),
	comefrom VARCHAR2 ( 255 byte ),
	contractbasicid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	plancode VARCHAR2 ( 255 byte ),
	price VARCHAR2 ( 255 byte ),
	rkcode VARCHAR2 ( 255 byte ),
	stockcode VARCHAR2 ( 255 byte ),
	stockname VARCHAR2 ( 255 byte ),
	sum VARCHAR2 ( 255 byte ),
	summoney VARCHAR2 ( 255 byte ),
	sysum VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_cancellingstockssq
-- ----------------------------
CREATE TABLE wm_cancellingstockssq (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	tkcode VARCHAR2 ( 255 byte ),
	tktype VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	realname VARCHAR2 ( 255 byte ),
	sqdate VARCHAR2 ( 255 byte ),
	sqr VARCHAR2 ( 255 byte ),
	tkstatus VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_cancellingstockswz
-- ----------------------------
CREATE TABLE wm_cancellingstockswz (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	tkcode VARCHAR2 ( 255 byte ),
	tkid VARCHAR2 ( 255 byte ),
	callslipcode VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	modelspecification VARCHAR2 ( 255 byte ),
	price VARCHAR2 ( 255 byte ),
	stockcode VARCHAR2 ( 255 byte ),
	stockname VARCHAR2 ( 255 byte ),
	sum VARCHAR2 ( 255 byte ),
	sysum VARCHAR2 ( 255 byte ),
	tkprice VARCHAR2 ( 255 byte ),
	tksum VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_contractbasic
-- ----------------------------
CREATE TABLE wm_contractbasic (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	auditingstatus VARCHAR2 ( 255 byte ),
	backreason VARCHAR2 ( 255 byte ),
	backtime VARCHAR2 ( 255 byte ),
	backuserid VARCHAR2 ( 255 byte ),
	bjf1 VARCHAR2 ( 255 byte ),
	bjf2 VARCHAR2 ( 255 byte ),
	bjf3 VARCHAR2 ( 255 byte ),
	bjreasons VARCHAR2 ( 255 byte ),
	buyerid VARCHAR2 ( 255 byte ),
	buyername VARCHAR2 ( 255 byte ),
	buyerwt VARCHAR2 ( 255 byte ),
	contractarea VARCHAR2 ( 255 byte ),
	contractauditingtypcode VARCHAR2 ( 255 byte ),
	contractauditingtype VARCHAR2 ( 255 byte ),
	contractauditingtypename VARCHAR2 ( 255 byte ),
	contractid VARCHAR2 ( 255 byte ),
	contractmethod VARCHAR2 ( 255 byte ),
	contractstatus VARCHAR2 ( 255 byte ),
	contracttax VARCHAR2 ( 255 byte ),
	contracttemp VARCHAR2 ( 255 byte ),
	contracttype VARCHAR2 ( 255 byte ),
	creatuserid VARCHAR2 ( 255 byte ),
	enddate VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	freight VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	paymentmethod VARCHAR2 ( 255 byte ),
	serialsnumber VARCHAR2 ( 255 byte ),
	spcode VARCHAR2 ( 255 byte ),
	startdate VARCHAR2 ( 255 byte ),
	summoney VARCHAR2 ( 255 byte ),
	supplierreasons VARCHAR2 ( 255 byte ),
	venditorid VARCHAR2 ( 255 byte ),
	venditorname VARCHAR2 ( 255 byte ),
	venditorwt VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_contractgoods
-- ----------------------------
CREATE TABLE wm_contractgoods (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	buycode VARCHAR2 ( 255 byte ),
	buymoney VARCHAR2 ( 255 byte ),
	buynum VARCHAR2 ( 255 byte ),
	buyprice VARCHAR2 ( 255 byte ),
	contractbasicid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	planbum VARCHAR2 ( 255 byte ),
	plancode VARCHAR2 ( 255 byte ),
	planprice VARCHAR2 ( 255 byte ),
	summoney VARCHAR2 ( 255 byte ),
	syrksum VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_contracttempcontent
-- ----------------------------
CREATE TABLE wm_contracttempcontent (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	content VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	sn VARCHAR2 ( 255 byte ),
	tempname_id VARCHAR2 ( 255 byte ) 
);

-- ----------------------------
-- table structure for wm_contracttempname
-- ----------------------------
CREATE TABLE wm_contracttempname (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	contractempname VARCHAR2 ( 255 byte ),
	createuserid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	introduce VARCHAR2 ( 255 byte ) 
);

-- ----------------------------
-- table structure for wm_contractterms
-- ----------------------------
CREATE TABLE wm_contractterms (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	content VARCHAR2 ( 255 byte ),
	contractbasicid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	sn VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_department
-- ----------------------------
CREATE TABLE wm_department (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	name VARCHAR2 ( 255 byte ),
	tel VARCHAR2 ( 255 byte ),
	deptnumber VARCHAR2 ( 255 byte ),
	parentid VARCHAR2 ( 255 byte ) 
);

-- ----------------------------
-- table structure for wm_dictionarys
-- ----------------------------
CREATE TABLE wm_dictionarys (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	dcode VARCHAR2 ( 255 byte ),
	dname VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_dictionaryschild
-- ----------------------------
CREATE TABLE wm_dictionaryschild (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	code VARCHAR2 ( 255 byte ),
	dcode VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	name VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	dictionarys_id VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_employee
-- ----------------------------
CREATE TABLE wm_employee (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	departid VARCHAR2 ( 255 byte ),
	departname VARCHAR2 ( 255 byte ),
	employeename VARCHAR2 ( 255 byte ),
	employeenum VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	phonenum VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_logs
-- ----------------------------
CREATE TABLE wm_logs (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	description VARCHAR2 ( 255 byte ),
	flagid VARCHAR2 ( 255 byte ),
	ipaddress VARCHAR2 ( 255 byte ),
	name VARCHAR2 ( 255 byte ),
	oldcontent VARCHAR2 ( 2048 byte ),
	optcontent VARCHAR2 ( 2048 byte ),
	params VARCHAR2 ( 255 byte ),
	realname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_outgoing
-- ----------------------------
CREATE TABLE wm_outgoing (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	ckcode VARCHAR2 ( 255 byte ),
	ckrid VARCHAR2 ( 255 byte ),
	ckrname VARCHAR2 ( 255 byte ),
	ckstatus VARCHAR2 ( 255 byte ),
	cktime VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_outgoingwz
-- ----------------------------
CREATE TABLE wm_outgoingwz (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	application VARCHAR2 ( 255 byte ),
	bcnum VARCHAR2 ( 255 byte ),
	callslipcode VARCHAR2 ( 255 byte ),
	callslipid VARCHAR2 ( 255 byte ),
	callsliptype VARCHAR2 ( 255 byte ),
	ckcode VARCHAR2 ( 255 byte ),
	ckid VARCHAR2 ( 255 byte ),
	department VARCHAR2 ( 255 byte ),
	fhstatus VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	llrid VARCHAR2 ( 255 byte ),
	llrname VARCHAR2 ( 255 byte ),
	modelspecification VARCHAR2 ( 255 byte ),
	price VARCHAR2 ( 255 byte ),
	sqnum VARCHAR2 ( 255 byte ),
	stockcode VARCHAR2 ( 255 byte ),
	stockid VARCHAR2 ( 255 byte ),
	stockname VARCHAR2 ( 255 byte ),
	storehouse VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wfnum VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzid VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_permission
-- ----------------------------
CREATE TABLE wm_permission (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	name VARCHAR2 ( 255 byte ),
	namevalue VARCHAR2 ( 255 byte ),
	description VARCHAR2 ( 255 byte ),
	modular VARCHAR2 ( 255 byte ),
	indexorder VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_permissions
-- ----------------------------
CREATE TABLE wm_permissions (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	pid VARCHAR2 ( 255 byte ) NOT NULL,
	description VARCHAR2 ( 255 byte ),
	indexorder NUMBER NOT NULL,
	modular VARCHAR2 ( 255 byte ),
	name VARCHAR2 ( 255 byte ),
	namevalue VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_pexcel
-- ----------------------------
CREATE TABLE wm_pexcel (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_plan
-- ----------------------------
CREATE TABLE wm_plan (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	plancode VARCHAR2 ( 255 byte ),
	planid VARCHAR2 ( 255 byte ),
	planmoney VARCHAR2 ( 255 byte ),
	planname VARCHAR2 ( 255 byte ),
	planspmoney VARCHAR2 ( 255 byte ),
	plantype VARCHAR2 ( 255 byte ),
	projectcode VARCHAR2 ( 255 byte ),
	projectid VARCHAR2 ( 255 byte ),
	sbdate VARCHAR2 ( 255 byte ),
	sbstatus VARCHAR2 ( 255 byte ),
	sbunit VARCHAR2 ( 255 byte ),
	sbunitid VARCHAR2 ( 255 byte ),
	spcode VARCHAR2 ( 255 byte ),
	spjsdate VARCHAR2 ( 255 byte ),
	spstatus VARCHAR2 ( 255 byte ),
	sqrid VARCHAR2 ( 255 byte ),
	sqrname VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_planlist
-- ----------------------------
CREATE TABLE wm_planlist (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	hostname VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	plancode VARCHAR2 ( 255 byte ),
	plancodeid VARCHAR2 ( 255 byte ),
	planmoney VARCHAR2 ( 255 byte ),
	planname VARCHAR2 ( 255 byte ),
	plannum VARCHAR2 ( 255 byte ),
	plantype VARCHAR2 ( 255 byte ),
	price VARCHAR2 ( 255 byte ),
	spmoney VARCHAR2 ( 255 byte ),
	spnum VARCHAR2 ( 255 byte ),
	spprice VARCHAR2 ( 255 byte ),
	synum VARCHAR2 ( 255 byte ),
	tuhao VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzid VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_projectnomanage
-- ----------------------------
CREATE TABLE wm_projectnomanage (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	createperson VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	projectname VARCHAR2 ( 255 byte ),
	projectno VARCHAR2 ( 255 byte ),
	remark VARCHAR2 ( 255 byte ),
	updateperson VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_returntreasury
-- ----------------------------
CREATE TABLE wm_returntreasury (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	sqdate VARCHAR2 ( 255 byte ),
	sqr VARCHAR2 ( 255 byte ),
	tkcode VARCHAR2 ( 255 byte ),
	tkczr VARCHAR2 ( 255 byte ),
	tkczrid VARCHAR2 ( 255 byte ),
	tkreason VARCHAR2 ( 255 byte ),
	tkstatus VARCHAR2 ( 255 byte ),
	tktype VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_returntreasurylist
-- ----------------------------
CREATE TABLE wm_returntreasurylist (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	buycode VARCHAR2 ( 255 byte ),
	contractbasicid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	planbum VARCHAR2 ( 255 byte ),
	plancode VARCHAR2 ( 255 byte ),
	planprice VARCHAR2 ( 255 byte ),
	rkcode VARCHAR2 ( 255 byte ),
	sjmoney VARCHAR2 ( 255 byte ),
	sjnum VARCHAR2 ( 255 byte ),
	sjthmoney VARCHAR2 ( 255 byte ),
	sjthsl VARCHAR2 ( 255 byte ),
	sjthslcs VARCHAR2 ( 255 byte ),
	sjthsljs VARCHAR2 ( 255 byte ),
	storehousecode VARCHAR2 ( 255 byte ),
	storehousename VARCHAR2 ( 255 byte ),
	sycknum VARCHAR2 ( 255 byte ),
	tkcode VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_roles
-- ----------------------------
CREATE TABLE wm_roles (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	name VARCHAR2 ( 255 byte ),
	description VARCHAR2 ( 255 byte ),
	TYPE VARCHAR2 ( 255 byte ),
	indexorder VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_sparepartcode
-- ----------------------------
CREATE TABLE wm_sparepartcode (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	parentid VARCHAR2 ( 255 byte ),
	parentcode VARCHAR2 ( 255 byte ),
	code VARCHAR2 ( 255 byte ),
	currencytype VARCHAR2 ( 255 byte ),
	currencyunit VARCHAR2 ( 255 byte ),
	devicecode VARCHAR2 ( 255 byte ),
	hostname VARCHAR2 ( 255 byte ),
	modelspecification VARCHAR2 ( 255 byte ),
	name VARCHAR2 ( 255 byte ),
	planprice VARCHAR2 ( 255 byte ),
	purchasetime VARCHAR2 ( 255 byte ),
	remark VARCHAR2 ( 255 byte ),
	spareparttype VARCHAR2 ( 255 byte ),
	spareparttypecode VARCHAR2 ( 255 byte ),
	stockmin VARCHAR2 ( 255 byte ),
	supplycycle VARCHAR2 ( 255 byte ),
	tuhao VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	ywname VARCHAR2 ( 255 byte ),
	description VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_sptypesplevel
-- ----------------------------
CREATE TABLE wm_sptypesplevel (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	splevelcode VARCHAR2 ( 255 byte ),
	spnote VARCHAR2 ( 255 byte ),
	sptypecode VARCHAR2 ( 255 byte ),
	spusersid VARCHAR2 ( 255 byte ),
	spuserszw VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_sptypesplevel_user
-- ----------------------------
CREATE TABLE wm_sptypesplevel_user (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	sptypesplevelid VARCHAR2 ( 255 byte ) NOT NULL,
	userid VARCHAR2 ( 255 byte ) NOT NULL 
);

-- ----------------------------
-- table structure for wm_stock
-- ----------------------------
CREATE TABLE wm_stock (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	bqend VARCHAR2 ( 255 byte ),
	bqendmoney VARCHAR2 ( 255 byte ),
	bqin VARCHAR2 ( 255 byte ),
	bqinmoney VARCHAR2 ( 255 byte ),
	bqout VARCHAR2 ( 255 byte ),
	bqoutmoney VARCHAR2 ( 255 byte ),
	bqstart VARCHAR2 ( 255 byte ),
	bqstartmoney VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	price VARCHAR2 ( 255 byte ),
	stockcode VARCHAR2 ( 255 byte ),
	stockname VARCHAR2 ( 255 byte ),
	stockyearmon VARCHAR2 ( 255 byte ),
	tranflag VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_subcontractingsq
-- ----------------------------
CREATE TABLE wm_subcontractingsq (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	fbphone VARCHAR2 ( 255 byte ),
	fbprincipal VARCHAR2 ( 255 byte ),
	fbunit VARCHAR2 ( 255 byte ),
	fjid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	isnewcom VARCHAR2 ( 255 byte ),
	jgtime VARCHAR2 ( 255 byte ),
	kgtime VARCHAR2 ( 255 byte ),
	spcode VARCHAR2 ( 255 byte ),
	spjsdate VARCHAR2 ( 255 byte ),
	spstatus VARCHAR2 ( 255 byte ),
	sqtime VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ),
	wwcode VARCHAR2 ( 255 byte ),
	wwcontent VARCHAR2 ( 255 byte ),
	wwmoney VARCHAR2 ( 255 byte ),
	wwname VARCHAR2 ( 255 byte ),
	wwphone VARCHAR2 ( 255 byte ),
	wwprincipal VARCHAR2 ( 255 byte ),
	wwreason VARCHAR2 ( 255 byte ),
	wzmoney VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_subcontractingwz
-- ----------------------------
CREATE TABLE wm_subcontractingwz (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	bcnum VARCHAR2 ( 255 byte ),
	dcck VARCHAR2 ( 255 byte ),
	dcckcode VARCHAR2 ( 255 byte ),
	dcckid VARCHAR2 ( 255 byte ),
	fhstatus VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	price VARCHAR2 ( 255 byte ),
	sqnum VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wfnum VARCHAR2 ( 255 byte ),
	wwcode VARCHAR2 ( 255 byte ),
	wwid VARCHAR2 ( 255 byte ),
	wwname VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzid VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_supplier
-- ----------------------------
CREATE TABLE wm_supplier (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	account VARCHAR2 ( 255 byte ),
	address VARCHAR2 ( 255 byte ),
	bank VARCHAR2 ( 255 byte ),
	fax VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	legalrepresentative VARCHAR2 ( 255 byte ),
	phone VARCHAR2 ( 255 byte ),
	postcode VARCHAR2 ( 255 byte ),
	registeredcapital VARCHAR2 ( 255 byte ),
	remark VARCHAR2 ( 255 byte ),
	suppliercode VARCHAR2 ( 255 byte ),
	suppliername VARCHAR2 ( 255 byte ),
	supplyscope VARCHAR2 ( 255 byte ),
	taxid VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_transferlist
-- ----------------------------
CREATE TABLE wm_transferlist (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	applytransfercode VARCHAR2 ( 255 byte ),
	applytransfercodeid VARCHAR2 ( 255 byte ),
	dcck VARCHAR2 ( 255 byte ),
	dcckcode VARCHAR2 ( 255 byte ),
	dcckid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	iscorrect VARCHAR2 ( 255 byte ),
	ljnum VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	price VARCHAR2 ( 255 byte ),
	realnum VARCHAR2 ( 255 byte ),
	realprice VARCHAR2 ( 255 byte ),
	sbunit VARCHAR2 ( 255 byte ),
	sbunitid VARCHAR2 ( 255 byte ),
	sqnum VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzid VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_transportmanage
-- ----------------------------
CREATE TABLE wm_transportmanage (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	accounttype VARCHAR2 ( 255 byte ),
	applyid VARCHAR2 ( 255 byte ),
	applyname VARCHAR2 ( 255 byte ),
	carnum VARCHAR2 ( 255 byte ),
	cartype VARCHAR2 ( 255 byte ),
	cost VARCHAR2 ( 255 byte ),
	coststatus VARCHAR2 ( 255 byte ),
	departid VARCHAR2 ( 255 byte ),
	destination VARCHAR2 ( 255 byte ),
	driver VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	goodsname VARCHAR2 ( 255 byte ),
	note1 VARCHAR2 ( 255 byte ),
	ordersid VARCHAR2 ( 255 byte ),
	orderstatus VARCHAR2 ( 255 byte ),
	origin VARCHAR2 ( 255 byte ),
	outcardepart VARCHAR2 ( 255 byte ),
	outcartime VARCHAR2 ( 255 byte ),
	outcartype VARCHAR2 ( 255 byte ),
	plantime VARCHAR2 ( 255 byte ),
	platenum VARCHAR2 ( 255 byte ),
	principal VARCHAR2 ( 255 byte ),
	projectname VARCHAR2 ( 255 byte ),
	purpose VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ),
	weight VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_user
-- ----------------------------
CREATE TABLE wm_user (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	username VARCHAR2 ( 255 byte ),
	password VARCHAR2 ( 255 byte ),
	realname VARCHAR2 ( 255 byte ),
	department VARCHAR2 ( 255 byte ),
	tel VARCHAR2 ( 255 byte ),
	issuper VARCHAR2 ( 255 byte ),
	lastlogintime VARCHAR2 ( 255 byte ),
	applogin VARCHAR2 ( 255 byte ),
	departmentid VARCHAR2 ( 255 byte ),
	state VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_user_roles
-- ----------------------------
CREATE TABLE wm_user_roles (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	roleid VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_warehousenum
-- ----------------------------
CREATE TABLE wm_warehousenum (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	fhr VARCHAR2 ( 255 byte ),
	fhrzw VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	ssunitid VARCHAR2 ( 255 byte ),
	stockcode VARCHAR2 ( 255 byte ),
	stockname VARCHAR2 ( 255 byte ),
	stocktype VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_warehousenum_user
-- ----------------------------
CREATE TABLE wm_warehousenum_user (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	warehouseid VARCHAR2 ( 255 byte ) NOT NULL,
	userid VARCHAR2 ( 255 byte ) NOT NULL 
);

-- ----------------------------
-- table structure for wm_warehousing
-- ----------------------------
CREATE TABLE wm_warehousing (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	consignee VARCHAR2 ( 255 byte ),
	entrydate VARCHAR2 ( 255 byte ),
	entryinfotype VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	notecode VARCHAR2 ( 255 byte ),
	rkstatus VARCHAR2 ( 255 byte ),
	storehousecode VARCHAR2 ( 255 byte ),
	storehouseid VARCHAR2 ( 255 byte ),
	storehousename VARCHAR2 ( 255 byte ),
	storeman VARCHAR2 ( 255 byte ),
	summoney VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_warehousinglist
-- ----------------------------
CREATE TABLE wm_warehousinglist (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	buycode VARCHAR2 ( 255 byte ),
	contractbasicid VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	planbum VARCHAR2 ( 255 byte ),
	plancode VARCHAR2 ( 255 byte ),
	planprice VARCHAR2 ( 255 byte ),
	rkcode VARCHAR2 ( 255 byte ),
	sjmoney VARCHAR2 ( 255 byte ),
	sjnum VARCHAR2 ( 255 byte ),
	sycknum VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_whtwarehousing
-- ----------------------------
CREATE TABLE wm_whtwarehousing (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	consignee VARCHAR2 ( 255 byte ),
	entrydate VARCHAR2 ( 255 byte ),
	entryinfotype VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	note VARCHAR2 ( 255 byte ),
	notecode VARCHAR2 ( 255 byte ),
	rkstatus VARCHAR2 ( 255 byte ),
	storehousecode VARCHAR2 ( 255 byte ),
	storehouseid VARCHAR2 ( 255 byte ),
	storehousename VARCHAR2 ( 255 byte ),
	storeman VARCHAR2 ( 255 byte ),
	summoney VARCHAR2 ( 255 byte ),
	userid VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_whtwarehousinglist
-- ----------------------------
CREATE TABLE wm_whtwarehousinglist (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	modelspcification VARCHAR2 ( 255 byte ),
	planprice VARCHAR2 ( 255 byte ),
	rkcode VARCHAR2 ( 255 byte ),
	sjmoney VARCHAR2 ( 255 byte ),
	sjnum VARCHAR2 ( 255 byte ),
	unit VARCHAR2 ( 255 byte ),
	wzcode VARCHAR2 ( 255 byte ),
	wzid VARCHAR2 ( 255 byte ),
	wzname VARCHAR2 ( 255 byte ),
	zjcode VARCHAR2 ( 255 byte ),
	zjname VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_workflow
-- ----------------------------
CREATE TABLE wm_workflow (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	backnode VARCHAR2 ( 255 byte ),
	nextnode VARCHAR2 ( 255 byte ),
	spmoneylowlimit VARCHAR2 ( 255 byte ),
	spmoneyuplimit VARCHAR2 ( 255 byte ),
	spnode VARCHAR2 ( 255 byte ),
	sptypecode VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_wzqx
-- ----------------------------
CREATE TABLE wm_wzqx (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	czr VARCHAR2 ( 255 byte ),
	czrzw VARCHAR2 ( 255 byte ),
	flag VARCHAR2 ( 255 byte ),
	wzqz VARCHAR2 ( 255 byte ) 
);
-- ----------------------------
-- table structure for wm_wzqx_user
-- ----------------------------
CREATE TABLE wm_wzqx_user (
	id VARCHAR2 ( 255 byte ) NOT NULL primary key,
	creatorid VARCHAR2 ( 255 byte ),
	creator VARCHAR2 ( 255 byte ),
	createtime VARCHAR2 ( 255 byte ),
	updaterid VARCHAR2 ( 255 byte ),
	updater VARCHAR2 ( 255 byte ),
	updatetime VARCHAR2 ( 255 byte ),
	warehouseid VARCHAR2 ( 255 byte ) NOT NULL,
	userid VARCHAR2 ( 255 byte ) NOT NULL 
);
-- ----------------------------
-- foreign keys structure for table wm_contracttempcontent
-- ----------------------------
alter table wm_contracttempcontent add constraint tempname_id foreign key ( tempname_id ) references wm_contracttempname ( id ) on delete cascade not deferrable initially immediate norely validate;
-- ----------------------------
-- foreign keys structure for table wm_dictionaryschild
-- ----------------------------
ALTER TABLE wm_dictionaryschild ADD constraint dictionarysid foreign key ( dictionarys_id ) references wm_dictionarys ( id ) ON DELETE cascade NOT deferrable initially IMMEDIATE norely VALIDATE;
-- ----------------------------
-- foreign keys structure for table wm_sptypesplevel_user
-- ----------------------------
alter table wm_sptypesplevel_user add constraint sptypesplevelid foreign key ( sptypesplevelid ) references wm_sptypesplevel ( id ) on delete cascade not deferrable initially immediate norely validate;
alter table wm_sptypesplevel_user add constraint wm_sptypesplevel_user_userid foreign key ( userid ) references wm_user ( id ) on delete cascade not deferrable initially immediate norely validate;
-- ----------------------------
-- foreign keys structure for table wm_user
-- ----------------------------
alter table wm_user add constraint departmentid foreign key ( departmentid ) references wm_department ( id ) on delete cascade not deferrable initially immediate norely validate;
-- ----------------------------
-- foreign keys structure for table wm_user_roles
-- ----------------------------
ALTER TABLE wm_user_roles ADD constraint roleid foreign key ( roleid ) references wm_roles ( id ) ON DELETE cascade NOT deferrable initially IMMEDIATE norely VALIDATE;
ALTER TABLE wm_user_roles ADD constraint userid foreign key ( userid ) references wm_user ( id ) ON DELETE cascade NOT deferrable initially IMMEDIATE norely VALIDATE;
-- ----------------------------
-- foreign keys structure for table wm_warehousenum_user
-- ----------------------------
alter table wm_warehousenum_user add constraint warehouseid foreign key ( warehouseid ) references wm_warehousenum ( id ) on delete cascade not deferrable initially immediate norely validate;
alter table wm_warehousenum_user add constraint wm_warehousenum_user_userid foreign key ( userid ) references wm_user ( id ) on delete cascade not deferrable initially immediate norely validate;
-- ----------------------------
-- foreign keys structure for table wm_wzqx_user
-- ----------------------------
alter table wm_wzqx_user add constraint wm_wzqx_user_userid foreign key ( userid ) references wm_user ( id ) on delete cascade not deferrable initially immediate norely validate;
alter table wm_wzqx_user add constraint wm_wzqx_user_warehouseid foreign key ( warehouseid ) references wm_wzqx ( id ) on delete cascade not deferrable initially immediate norely validate;
-- ----------------------------
-- foreign keys structure for table wm_transferlist
-- ----------------------------
alter table wm_transferlist add constraint applytransfercodeid foreign key ( applytransfercodeid ) references wm_applytransfer ( id ) on delete cascade not deferrable initially immediate norely validate;

--插入初始值
INSERT INTO wm_department ( id, creatorid, creator, createtime, updaterid, updater, updatetime, name, tel, deptnumber, parentid )
VALUES	( 'c2b632dc87637a5fd03fdf9b61693d17', NULL, NULL, NULL, NULL, NULL, NULL, '全厂', '15552224', '001', NULL );
INSERT INTO wm_user ( id, creatorid,creator,createtime,updaterid,updater,updatetime,username,password,realname,department,tel,issuper,lastlogintime,applogin,departmentid,state )
VALUES	('005afb46a05d42a6802400c74ff6e84e','001','admin','2019-03-20',NULL,NULL,NULL,'admin','c2b632dc87637a5fd03fdf9b61693d17','管理员','全厂','17764347918','1','2019-03-20','0','c2b632dc87637a5fd03fdf9b61693d17','normal');
