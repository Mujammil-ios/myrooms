package com.mj.myrooms.object.core;

import com.google.gson.annotations.SerializedName;

public class ResponceDataItem{

	@SerializedName("role_name")
	private String roleName;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("status")
	private String status;

	@SerializedName("updatedAt")
	private String updatedAt;

	public void setRoleName(String roleName){
		this.roleName = roleName;
	}

	public String getRoleName(){
		return roleName;
	}

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setUpdatedAt(String updatedAt){
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	@Override
 	public String toString(){
		return 
			"ResponceDataItem{" + 
			"role_name = '" + roleName + '\'' + 
			",createdAt = '" + createdAt + '\'' + 
			",id = '" + id + '\'' + 
			",status = '" + status + '\'' + 
			",updatedAt = '" + updatedAt + '\'' + 
			"}";
		}
}