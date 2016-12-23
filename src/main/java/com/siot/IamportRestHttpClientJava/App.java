package com.siot.IamportRestHttpClientJava;

import com.siot.IamportRestHttpClientJava.IamportClient;
import com.siot.IamportRestHttpClientJava.request.CancelData;
import com.siot.IamportRestHttpClientJava.response.IamportResponse;
import com.siot.IamportRestHttpClientJava.response.Payment;

public class App 
{
	static IamportClient client;
	
    public static void main( String[] args ) throws Exception
    {
    	System.out.println(System.getProperty("java.runtime.version"));
    	
    	String api_key = "imp_apikey";
		String api_secret = "ekKoeW8RyKuT0zgaZsUtXXTLQ4AhPFW3ZGseDA6bkA5lamv9OqDMnxyeB9wqOsuO9W3Mx9YSJ4dTqJ3f";

		client = new IamportClient(api_key, api_secret);
		
		String token = client.getToken();
		System.out.println("token : " + token);
		
		IamportResponse<Payment> paymentByimpuid = client.paymentByImpUid("imps_146154615717");
		System.out.println(paymentByimpuid.getResponse().getImpUid());
		
		IamportResponse<Payment> paymentByMerchantUid = client.paymentByMerchantUid("merchant_1448006668555");
		System.out.println(paymentByMerchantUid.getResponse().getMerchantUid());
		
		//이미 취소된 거래 imp_uid
		CancelData cancel1 = new CancelData("imp_1411648292162", true);
		IamportResponse<Payment> cancelpayment1 = client.cancelPayment(cancel1);
		System.out.println(cancelpayment1.getMessage());
		
		//이미 취소된 거래 merchant_uid
		CancelData cancel2 = new CancelData("iamport_1416218882181", false);
		IamportResponse<Payment> cancelpayment2 = client.cancelPayment(cancel2);
		System.out.println(cancelpayment2.getMessage());
    }
}
