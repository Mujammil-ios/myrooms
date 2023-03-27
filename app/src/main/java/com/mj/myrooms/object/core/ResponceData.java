package com.mj.myrooms.object.core;

import com.google.gson.annotations.SerializedName;

public class ResponceData{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("password")
	private String password;

	@SerializedName("full_name")
	private String fullName;

	@SerializedName("role_id")
	private int roleId;

	@SerializedName("longitudes")
	private String longitudes;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("latitudes")
	private String latitudes;

	@SerializedName("updatedAt")
	private String updatedAt;

	public void setCreatedAt(String createdAt){
		this.createdAt = createdAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	public String getFullName(){
		return fullName;
	}

	public void setRoleId(int roleId){
		this.roleId = roleId;
	}

	public int getRoleId(){
		return roleId;
	}

	public void setLongitudes(String longitudes){
		this.longitudes = longitudes;
	}

	public String getLongitudes(){
		return longitudes;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setLatitudes(String latitudes){
		this.latitudes = latitudes;
	}

	public String getLatitudes(){
		return latitudes;
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
			"ResponceData{" + 
			"createdAt = '" + createdAt + '\'' + 
			",password = '" + password + '\'' + 
			",full_name = '" + fullName + '\'' + 
			",role_id = '" + roleId + '\'' + 
			",longitudes = '" + longitudes + '\'' + 
			",phone_number = '" + phoneNumber + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",latitudes = '" + latitudes + '\'' + 
			",updatedAt = '" + updatedAt + '\'' + 
			"}";
		}
}