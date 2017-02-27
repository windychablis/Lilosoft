package com.chablis.lilosoft.model;
public class TDMaterials {

	public String MATERIAL_ID; //资料id
	public String MATERIAL_NAME; //资料名称
	public String FORM_ID; //表单id
	public String PIC_PATH; //图片路径
	public String DIR_URL; //图片路径
	public int IS_ORDER; //排序
	public String AREA_CODE; //区号


	public String getDIR_URL() {
		return DIR_URL;
	}
	public void setDIR_URL(String dIR_URL) {
		DIR_URL = dIR_URL;
	}
	public String getMATERIAL_ID() {
		return MATERIAL_ID;
	}
	public void setMATERIAL_ID(String mATERIAL_ID) {
		MATERIAL_ID = mATERIAL_ID;
	}
	public String getMATERIAL_NAME() {
		return MATERIAL_NAME;
	}
	public void setMATERIAL_NAME(String mATERIAL_NAME) {
		MATERIAL_NAME = mATERIAL_NAME;
	}
	public String getFORM_ID() {
		return FORM_ID;
	}
	public void setFORM_ID(String fORM_ID) {
		FORM_ID = fORM_ID;
	}
	public String getPIC_PATH() {
		return PIC_PATH;
	}
	public void setPIC_PATH(String pIC_PATH) {
		PIC_PATH = pIC_PATH;
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
