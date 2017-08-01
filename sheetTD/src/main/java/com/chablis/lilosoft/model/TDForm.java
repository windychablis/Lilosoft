package com.chablis.lilosoft.model;
public class TDForm {

	public String FORM_ID; //表单id
	public String FORM_NAME; //表单名称
	public String ICO_PATH; //图标路径
	public String DIR_URL; //图标路径
	public String DEPT_ID; //部门id
	public int IS_ORDER; //排序
	public String AREA_CODE; //区号



	public String getDIR_URL() {
		return DIR_URL;
	}
	public void setDIR_URL(String dIR_URL) {
		DIR_URL = dIR_URL;
	}
	public String getFORM_ID() {
		return FORM_ID;
	}
	public void setFORM_ID(String fORM_ID) {
		FORM_ID = fORM_ID;
	}
	public String getFORM_NAME() {
		return FORM_NAME;
	}
	public void setFORM_NAME(String fORM_NAME) {
		FORM_NAME = fORM_NAME;
	}
	public String getICO_PATH() {
		return ICO_PATH;
	}
	public void setICO_PATH(String iCO_PATH) {
		ICO_PATH = iCO_PATH;
	}
	public String getDEPT_ID() {
		return DEPT_ID;
	}
	public void setDEPT_ID(String dEPT_ID) {
		DEPT_ID = dEPT_ID;
	}
	public int getIS_ORDER() {
		return IS_ORDER;
	}
	public void setIS_ORDER(int iS_ORDER) {
		IS_ORDER = iS_ORDER;
	}
	public String getAREA_CODE() {
		return AREA_CODE;
	}
	public void setAREA_CODE(String aREA_CODE) {
		AREA_CODE = aREA_CODE;
	}




}
