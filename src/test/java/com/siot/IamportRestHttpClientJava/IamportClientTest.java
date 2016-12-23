package com.siot.IamportRestHttpClientJava;

public class IamportClientTest {
	IamportClient client;
	
	public void setup() {
		String test_api_key = "imp_apikey";
		String test_api_secret = "ekKoeW8RyKuT0zgaZsUtXXTLQ4AhPFW3ZGseDA6bkA5lamv9OqDMnxyeB9wqOsuO9W3Mx9YSJ4dTqJ3f";
		client = new IamportClient(test_api_key, test_api_secret);
	}
}
