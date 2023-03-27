package com.mj.myrooms.object.wrapper;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.mj.myrooms.object.core.ResponceDataItem;

public class GetRoleResponse{

	@SerializedName("ResponceCode")
	private int responceCode;

	@SerializedName("ResponceMessage")
	private String responceMessage;

	@SerializedName("ResponceData")
	private List<ResponceDataItem> responceData;

	public void setResponceCode(int responceCode){
		this.responceCode = responceCode;
	}

	public int getResponceCode(){
		return responceCode;
	}

	public void setResponceMessage(String responceMessage){
		this.responceMessage = responceMessage;
	}

	public String getResponceMessage(){
		return responceMessage;
	}

	public void setResponceData(List<ResponceDataItem> responceData){
		this.responceData = responceData;
	}

	public List<ResponceDataItem> getResponceData(){
		return responceData;
	}

	@Override
 	public String toString(){
		return 
			"GetRoleResponse{" + 
			"responceCode = '" + responceCode + '\'' + 
			",responceMessage = '" + responceMessage + '\'' + 
			",responceData = '" + responceData + '\'' + 
			"}";
		}
}