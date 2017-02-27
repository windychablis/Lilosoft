package com.chablis.lilosoft.model;
public class TDDept {

	public String DEPT_ID; // 部门id
	public String DEPT_NAME; // 部门名称
	public String ICO_PATH; // 图标路径
	public String DIR_URL; // 图标路径
	public int FLOORNUM; // 楼层
	public String AREA; // 区域
	public int IS_SHOW; // 显示
	public int IS_ORDER; // 排序
	public String AREA_CODE; // 区号

	public String getDIR_URL() {
		return DIR_URL;
	}

	public void setDIR_URL(String dIR_URL) {
		DIR_URL = dIR_URL;
	}

	public String getDEPT_ID() {
		return DEPT_ID;
	}

	public void setDEPT_ID(String dEPT_ID) {
		DEPT_ID = dEPT_ID;
	}

	public String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}

	public String getICO_PATH() {
		return ICO_PATH;
	}

	public void setICO_PATH(String iCO_PATH) {
		ICO_PATH = iCO_PATH;
	}

	public int getFLOORNUM() {
		return FLOORNUM;
	}

	public void setFLOORNUM(int fLOORNUM) {
		FLOORNUM = fLOORNUM;
	}

	public String getAREA() {
		return AREA;
	}

	public void setAREA(String aREA) {
		AREA = aREA;
	}

	public int getIS_SHOW() {
		return IS_SHOW;
	}

	public void setIS_SHOW(int iS_SHOW) {
		IS_SHOW = iS_SHOW;
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
