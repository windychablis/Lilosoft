package com.chablis.lilosoft.db;
public class dbConfig {

	public static final String DB_NAME = "db_td.db"; // 数据库名
	public static final int DB_VERSION = 1; //数据库版本
	public static final String DB_TABLE_TabType_PK = "AREA_CODE";
	public static final String PUBLISH_TYPE = "PUBLISH_TYPE";

	public static final String EGS_TD_DEPT = "EGS_TD_DEPT";
	public static final String EGS_TD_FORM = "EGS_TD_FORM";
	public static final String EGS_TD_MATERIALS = "EGS_TD_FORM_MATERIALS";
	public static final String EGS_TD_CLIENTINFO = "EGS_TD_CLIENTINFO";

	public static final String CreateSql_EGS_TD_DEPT = "create table if not exists EGS_TD_DEPT" +
			"(DEPT_ID VARCHAR2(36) not null primary key," +
			"DEPT_NAME VARCHAR2(250) not null," +
			"ICO_PATH VARCHAR2(500)," +
			"DIR_URL VARCHAR2(500)," +
			"FLOORNUM INTEGER not null," +
			"AREA VARCHAR2(4) not null," +
			"IS_SHOW INTEGER not null," +
			"IS_ORDER INTEGER not null," +
			"AREA_CODE VARCHAR2(12) not null)";

	public static final String CreateSql_EGS_TD_FORM = "create table if not exists EGS_TD_FORM" +
			"(FORM_ID VARCHAR2(36) not null primary key," +
			"FORM_NAME VARCHAR2(250) not null," +
			"ICO_PATH VARCHAR2(500)," +
			"DIR_URL VARCHAR2(500)," +
			"DEPT_ID  VARCHAR2(36) not null," +
			"IS_ORDER INTEGER not null," +
			"AREA_CODE VARCHAR2(12) not null)";

	public static final String CreateSql_EGS_TD_MATERIALS = "create table if not exists EGS_TD_FORM_MATERIALS" +
			"(MATERIAL_ID VARCHAR2(36) not null primary key," +
			"MATERIAL_NAME VARCHAR2(250) not null," +
			"FORM_ID VARCHAR2(36) not null," +
			"PIC_PATH VARCHAR2(500)," +
			"DIR_URL VARCHAR2(500)," +
			"IS_ORDER INTEGER not null," +
			"AREA_CODE VARCHAR2(12) not null)";

	public static final String CreateSql_EGS_TD_CLIENTINFO = "create table if not exists EGS_TD_CLIENTINFO" +
			"(AREA VARCHAR2(20)," +
			"FLOORNUM VARCHAR2(10)," +
			"CLIENT_TYPE VARCHAR2(30),"+
			"PUBLISH_TYPE VARCHAR2(10))";
}
