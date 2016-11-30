package com.sqkj.znyj.model;

import net.sf.json.JSONObject;

public class PD {
	
	private String YPBH;
	private String YPMC;
	private String XQ;
	private int YPSL;
	private int isRight;
	
	public PD(){}
	
	public PD(JSONObject json){
		this.YPBH = json.getString("YPBH").trim();
		this.YPMC = json.getString("YPMC").trim();
		this.XQ = json.getString("XQ").trim();
		this.YPSL = json.getInt("YPSL");
		this.isRight = json.getInt("isRight");
	}

	public String getYPBH() {
		return YPBH;
	}

	public void setYPBH(String yPBH) {
		YPBH = yPBH;
	}

	public String getYPMC() {
		return YPMC;
	}

	public void setYPMC(String yPMC) {
		YPMC = yPMC;
	}

	public String getXQ() {
		return XQ;
	}

	public void setXQ(String xQ) {
		XQ = xQ;
	}

	public int getYPSL() {
		return YPSL;
	}

	public void setYPSL(int yPSL) {
		YPSL = yPSL;
	}

	public int getIsRight() {
		return isRight;
	}

	public void setIsRight(int isRight) {
		this.isRight = isRight;
	}	
	
}
