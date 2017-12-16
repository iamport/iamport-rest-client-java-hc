package com.siot.IamportRestHttpClientJava;

import org.junit.Before;
import org.junit.Test;

import com.siot.IamportRestHttpClientJava.request.CancelData;
import com.siot.IamportRestHttpClientJava.response.IamportResponse;
import com.siot.IamportRestHttpClientJava.response.Payment;

public class IamportClientTest {
	IamportClient client;
	
	@Before
	public void setup() throws Exception {
		String api_key = "imp_apikey";
		String api_secret = "ekKoeW8RyKuT0zgaZsUtXXTLQ4AhPFW3ZGseDA6bkA5lamv9OqDMnxyeB9wqOsuO9W3Mx9YSJ4dTqJ3f";

		client = new IamportClient(api_key, api_secret);
	}
	
	@Test
	public void testGetToken() throws Exception {
		String token = client.getToken();
		System.out.println("token : " + token);
	}
	
	@Test
	public void testGetPaymentByImpUid() throws Exception {		
		IamportResponse<Payment> paymentByimpuid = client.paymentByImpUid("imp_448280090638");
		System.out.println(paymentByimpuid.getResponse().getImpUid());
	}

	@Test
	public void testGetPaymentByMerchantUid() throws Exception {		
		IamportResponse<Payment> paymentByMerchantUid = client.paymentByMerchantUid("merchant_1448280088556");
		System.out.println(paymentByMerchantUid.getResponse().getMerchantUid());
	}
	
	@Test
	public void testCancelPaymentByImpUid() throws Exception {
		//이미 취소된 거래 imp_uid
		CancelData cancel1 = new CancelData("imp_448280090638", true);
		IamportResponse<Payment> cancelpayment1 = client.cancelPayment(cancel1);
		System.out.println(cancelpayment1.getMessage());
	}
	
	@Test
	public void testCancelPaymentByMerchantUid() throws Exception {
		//이미 취소된 거래 merchant_uid
		CancelData cancel2 = new CancelData("merchant_1448280088556", false);
		IamportResponse<Payment> cancelpayment2 = client.cancelPayment(cancel2);
		System.out.println(cancelpayment2.getMessage());
	}
}
