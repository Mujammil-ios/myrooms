package com.mj.myrooms.object.core;

import com.google.gson.annotations.SerializedName;

public class CreateUserResponse{

	@SerializedName("ResponceCode")
	private int responceCode;

	@SerializedName("ResponceMessage")
	private String responceMessage;

	@SerializedName("ResponceData")
	private ResponceData responceData;

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

	public void setResponceData(ResponceData responceData){
		this.responceData = responceData;
	}

	public ResponceData getResponceData(){
		return responceData;
	}

	@Override
 	public String toString(){
		return 
			"CreateUserResponse{" + 
			"responceCode = '" + responceCode + '\'' + 
			",responceMessage = '" + responceMessage + '\'' + 
			",responceData = '" + responceData + '\'' + 
			"}";
		}
}