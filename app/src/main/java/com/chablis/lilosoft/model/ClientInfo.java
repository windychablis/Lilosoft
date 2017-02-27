package com.chablis.lilosoft.model;

public class ClientInfo {

	public String area;			//区域
	public String floornum;		//楼层
	public String client_type;		//编号
	public String publish_type;	//显示方式　1区划（areaid） 2楼层（floornum） 3全部

	public String getPublish_type() {
		return publish_type;
	}
	public void setPublish_type(String publish_type) {
		this.publish_type = publish_type;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getFloornum() {
		return floornum;
	}
	public void setFloornum(String floornum) {
		this.floornum = floornum;
	}
	public String getClient_type() {
		return client_type;
	}
	public void setClient_type(String client_type) {
		this.client_type = client_type;
	}


}