package com.siot.IamportRestHttpClientJava.response;

import com.google.gson.annotations.SerializedName;

public class AccessToken {
	@SerializedName("access_token")
	String token;
	
	@SerializedName("expired_at")
	int expired_at;
	
	@SerializedName("now")
	int now;

	public String getToken() {
		return this.token;
	}
}