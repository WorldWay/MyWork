package com.sqkj.znyj.model;

import net.sf.json.JSONObject;

public class PM {
	private String PMID;
	private int PMDZ;
	private String ZJQIP;
	private String YPBH;
	private int KW1;
	private int KW2;

	public PM(){}
	
	public PM(JSONObject json){
		this.PMID = json.getString("PMID");
		this.PMDZ = json.getInt("PMDZ");
		this.YPBH = json.getString("YPBH");
		this.ZJQIP = json.getString("ZJQIP");
		this.KW1 = json.getInt("KW1");
		this.KW2 = json.getInt("KW2");
	}
	
	public String getPMID() {
		return PMID;
	}

	public void setPMID(String pMID) {
		PMID = pMID;
	}

	public int getPMDZ() {
		return PMDZ;
	}

	public void setPMDZ(int pMDZ) {
		PMDZ = pMDZ;
	}

	public String getZJQIP() {
		return ZJQIP;
	}

	public void setZJQIP(String zJQIP) {
		ZJQIP = zJQIP;
	}

	public String getYPBH() {
		return YPBH;
	}

	public void setYPBH(String yPBH) {
		YPBH = yPBH;
	}

	public int getKW1() {
		return KW1;
	}

	public void setKW1(int kW1) {
		KW1 = kW1;
	}

	public int getKW2() {
		return KW2;
	}

	public void setKW2(int kW2) {
		KW2 = kW2;
	}
		
}
