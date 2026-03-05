package com.siot.IamportRestHttpClientJava;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siot.IamportRestHttpClientJava.request.*;
import com.siot.IamportRestHttpClientJava.request.escrow.EscrowLogisData;
import com.siot.IamportRestHttpClientJava.request.naver.*;
import com.siot.IamportRestHttpClientJava.response.*;
import com.siot.IamportRestHttpClientJava.response.escrow.EscrowLogisInvoice;
import com.siot.IamportRestHttpClientJava.response.naver.NaverCashAmount;
import com.siot.IamportRestHttpClientJava.response.naver.NaverProductOrder;
import com.siot.IamportRestHttpClientJava.response.naver.NaverReview;

public class IamportClient {
	private static final String API_URL = "https://api.iamport.kr";
	private String api_key = null;
	private String api_secret = null;
	private HttpClient client = null;
	private Gson gson = new Gson();

	public IamportClient(String api_key, String api_secret) {
		this.api_key = api_key;
		this.api_secret = api_secret;
		this.client = HttpClientBuilder.create().build();
	}

	/* ============================================================
	 *  Internal HTTP helpers
	 * ============================================================ */

	private IamportResponse<AccessToken> getAuth() throws Exception {
		AuthData authData = new AuthData(api_key, api_secret);

		String authJsonData = gson.toJson(authData);

		try {
			StringEntity data = new StringEntity(authJsonData);

			HttpPost postRequest = new HttpPost(API_URL + "/users/getToken");
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection", "keep-alive");
			postRequest.setHeader("Content-Type", "application/json");

			postRequest.setEntity(data);

			HttpResponse response = client.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
			}

			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			Type listType = new TypeToken<IamportResponse<AccessToken>>(){}.getType();
			IamportResponse<AccessToken> auth = gson.fromJson(body, listType);

			return auth;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String postRequest(String path, String token, StringEntity postData) throws URISyntaxException {

		try {

			HttpPost postRequest = new HttpPost(API_URL + path);
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection", "keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.addHeader("Authorization", token);

			postRequest.setEntity(postData);

			HttpResponse response = client.execute(postRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
			}

			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			return body;

		} catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

		return null;
	}

	private String getRequest(String path, String token) throws URISyntaxException {

		try {

			HttpGet getRequest = new HttpGet(API_URL + path);
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", token);

			HttpResponse response = client.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
			}

			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			return body;

		  } catch (ClientProtocolException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();
		  }

		return null;
	}

	private String putRequest(String path, String token, StringEntity putData) throws URISyntaxException {

		try {

			HttpPut putRequest = new HttpPut(API_URL + path);
			putRequest.setHeader("Accept", "application/json");
			putRequest.setHeader("Connection", "keep-alive");
			putRequest.setHeader("Content-Type", "application/json");
			putRequest.addHeader("Authorization", token);

			putRequest.setEntity(putData);

			HttpResponse response = client.execute(putRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
			}

			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			return body;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private String deleteRequest(String path, String token) throws URISyntaxException {

		try {

			HttpDelete deleteRequest = new HttpDelete(API_URL + path);
			deleteRequest.addHeader("Accept", "application/json");
			deleteRequest.addHeader("Authorization", token);

			HttpResponse response = client.execute(deleteRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatusLine().getStatusCode());
			}

			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);

			return body;

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/* ============================================================
	 *  Public API: Auth
	 * ============================================================ */

	public String getToken() throws Exception {
		IamportResponse<AccessToken> auth = this.getAuth();

		if (auth != null) {
			String token = auth.getResponse().getToken();
			return token;
		}

		return null;
	}

	/* ============================================================
	 *  PAYMENTS
	 * ============================================================ */

	public IamportResponse<Payment> paymentByImpUid(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/" + impUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);

			return payment;
		}
		return null;
	}

	public IamportResponse<List<Payment>> paymentsByImpUid(List<String> impUids) throws Exception {
		String token = this.getToken();

		if (token != null) {
			StringBuilder sb = new StringBuilder("/payments?");
			for (int i = 0; i < impUids.size(); i++) {
				if (i > 0) sb.append("&");
				sb.append("imp_uid[]=").append(encode(impUids.get(i)));
			}
			String response = this.getRequest(sb.toString(), token);

			Type listType = new TypeToken<IamportResponse<List<Payment>>>(){}.getType();
			IamportResponse<List<Payment>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> paymentByMerchantUid(String merchantUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/find/" + merchantUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);

			return payment;
		}

		return null;
	}

	public IamportResponse<Payment> paymentByMerchantUid(String merchantUid, String paymentStatus) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/find/" + merchantUid + "/" + paymentStatus;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);

			return payment;
		}
		return null;
	}

	public IamportResponse<PagedDataList<Payment>> paymentsByMerchantUid(String merchantUid, String paymentStatus) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/findAll/" + merchantUid + "/" + paymentStatus;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<PagedDataList<Payment>>>(){}.getType();
			IamportResponse<PagedDataList<Payment>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<PagedDataList<Payment>> paymentsByStatus(String status) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/status/" + status;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<PagedDataList<Payment>>>(){}.getType();
			IamportResponse<PagedDataList<Payment>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<PaymentBalance> paymentBalanceByImpUid(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/" + impUid + "/balance";
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<PaymentBalance>>(){}.getType();
			IamportResponse<PaymentBalance> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> cancelPayment(CancelData cancelData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String cancelJsonData = gson.toJson(cancelData);
			StringEntity data = new StringEntity(cancelJsonData);

			String response = this.postRequest("/payments/cancel", token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);

			return payment;
		}
		return null;
	}

	/* ============================================================
	 *  PREPARE
	 * ============================================================ */

	public IamportResponse<Prepare> postPrepare(PrepareData prepareData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(prepareData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/payments/prepare", token, data);

			Type listType = new TypeToken<IamportResponse<Prepare>>(){}.getType();
			IamportResponse<Prepare> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Prepare> getPrepare(String merchantUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/prepare/" + merchantUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Prepare>>(){}.getType();
			IamportResponse<Prepare> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Prepare> putPrepare(PrepareData prepareData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(prepareData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.putRequest("/payments/prepare", token, data);

			Type listType = new TypeToken<IamportResponse<Prepare>>(){}.getType();
			IamportResponse<Prepare> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  CERTIFICATIONS
	 * ============================================================ */

	public IamportResponse<Certification> certificationByImpUid(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/certifications/" + impUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Certification>>(){}.getType();
			IamportResponse<Certification> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Certification> deleteCertification(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/certifications/" + impUid;
			String response = this.deleteRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Certification>>(){}.getType();
			IamportResponse<Certification> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Certification> otpRequest(OtpRequestData otpData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(otpData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/certifications/otp/request", token, data);

			Type listType = new TypeToken<IamportResponse<Certification>>(){}.getType();
			IamportResponse<Certification> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Certification> otpConfirm(String impUid, OtpConfirmData otpData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(otpData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/certifications/otp/confirm/" + impUid;
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<Certification>>(){}.getType();
			IamportResponse<Certification> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  SUBSCRIBE - Billing Customers
	 * ============================================================ */

	public IamportResponse<BillingCustomer> getBillingCustomer(String customerUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/subscribe/customers/" + customerUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<BillingCustomer>>(){}.getType();
			IamportResponse<BillingCustomer> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<BillingCustomer>> getBillingCustomers(List<String> customerUids) throws Exception {
		String token = this.getToken();

		if (token != null) {
			StringBuilder sb = new StringBuilder("/subscribe/customers?");
			for (int i = 0; i < customerUids.size(); i++) {
				if (i > 0) sb.append("&");
				sb.append("customer_uid[]=").append(encode(customerUids.get(i)));
			}
			String response = this.getRequest(sb.toString(), token);

			Type listType = new TypeToken<IamportResponse<List<BillingCustomer>>>(){}.getType();
			IamportResponse<List<BillingCustomer>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<BillingCustomer> postBillingCustomer(String customerUid, BillingCustomerData billingData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(billingData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/subscribe/customers/" + customerUid;
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<BillingCustomer>>(){}.getType();
			IamportResponse<BillingCustomer> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<BillingCustomer> deleteBillingCustomer(String customerUid, String reason, String extra) throws Exception {
		String token = this.getToken();

		if (token != null) {
			StringBuilder sb = new StringBuilder("/subscribe/customers/" + customerUid);
			sb.append("?");
			if (reason != null) sb.append("reason=").append(encode(reason)).append("&");
			if (extra != null) sb.append("extra[requester]=").append(encode(extra));
			String response = this.deleteRequest(sb.toString(), token);

			Type listType = new TypeToken<IamportResponse<BillingCustomer>>(){}.getType();
			IamportResponse<BillingCustomer> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<PagedDataList<Payment>> getCustomerPayments(String customerUid, int page) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/subscribe/customers/" + customerUid + "/payments?page=" + page;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<PagedDataList<Payment>>>(){}.getType();
			IamportResponse<PagedDataList<Payment>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<ScheduleList> getCustomerSchedules(String customerUid, int page, int scheduleFrom, int scheduleTo, String scheduleStatus) throws Exception {
		String token = this.getToken();

		if (token != null) {
			StringBuilder sb = new StringBuilder("/subscribe/customers/" + customerUid + "/schedules?");
			sb.append("page=").append(page);
			sb.append("&schedule_from=").append(scheduleFrom);
			sb.append("&schedule_to=").append(scheduleTo);
			if (scheduleStatus != null) sb.append("&schedule_status=").append(encode(scheduleStatus));
			String response = this.getRequest(sb.toString(), token);

			Type listType = new TypeToken<IamportResponse<ScheduleList>>(){}.getType();
			IamportResponse<ScheduleList> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  SUBSCRIBE - Payments
	 * ============================================================ */

	public IamportResponse<Payment> onetimePayment(OnetimePaymentData onetimeData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(onetimeData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/subscribe/payments/onetime", token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> againPayment(AgainPaymentData againData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(againData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/subscribe/payments/again", token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  SUBSCRIBE - Schedule
	 * ============================================================ */

	public IamportResponse<ScheduleList> getPaymentSchedule(GetScheduleData getScheduleData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			StringBuilder sb = new StringBuilder("/subscribe/payments/schedule?");
			sb.append("schedule_from=").append(getScheduleData.getSchedule_from());
			sb.append("&schedule_to=").append(getScheduleData.getSchedule_to());
			if (getScheduleData.getSchedule_status() != null) sb.append("&schedule_status=").append(encode(getScheduleData.getSchedule_status()));
			sb.append("&page=").append(getScheduleData.getPage());
			sb.append("&limit=").append(getScheduleData.getLimit());
			String response = this.getRequest(sb.toString(), token);

			Type listType = new TypeToken<IamportResponse<ScheduleList>>(){}.getType();
			IamportResponse<ScheduleList> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<Schedule>> subscribeSchedule(ScheduleData scheduleData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(scheduleData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/subscribe/payments/schedule", token, data);

			Type listType = new TypeToken<IamportResponse<List<Schedule>>>(){}.getType();
			IamportResponse<List<Schedule>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<Schedule>> unsubscribeSchedule(UnscheduleData unscheduleData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(unscheduleData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/subscribe/payments/unschedule", token, data);

			Type listType = new TypeToken<IamportResponse<List<Schedule>>>(){}.getType();
			IamportResponse<List<Schedule>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Schedule> getScheduleByMerchantUid(String merchantUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/subscribe/payments/schedule/" + merchantUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Schedule>>(){}.getType();
			IamportResponse<Schedule> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Schedule> putScheduleByMerchantUid(String merchantUid, ScheduleUpdateData updateData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(updateData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/subscribe/payments/schedule/" + merchantUid;
			String response = this.putRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<Schedule>>(){}.getType();
			IamportResponse<Schedule> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> retrySchedule(String merchantUid, ScheduleRetryData retryData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(retryData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/subscribe/payments/schedule/" + merchantUid + "/retry";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Schedule> rescheduleSchedule(String merchantUid, ScheduleRescheduleData rescheduleData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(rescheduleData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/subscribe/payments/schedule/" + merchantUid + "/reschedule";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<Schedule>>(){}.getType();
			IamportResponse<Schedule> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<ScheduleList> getScheduleByCustomerUid(String customerUid, int page, int scheduleFrom, int scheduleTo, String scheduleStatus) throws Exception {
		String token = this.getToken();

		if (token != null) {
			StringBuilder sb = new StringBuilder("/subscribe/payments/schedule/customers/" + customerUid + "?");
			sb.append("page=").append(page);
			sb.append("&schedule_from=").append(scheduleFrom);
			sb.append("&schedule_to=").append(scheduleTo);
			if (scheduleStatus != null) sb.append("&schedule_status=").append(encode(scheduleStatus));
			String response = this.getRequest(sb.toString(), token);

			Type listType = new TypeToken<IamportResponse<ScheduleList>>(){}.getType();
			IamportResponse<ScheduleList> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  ESCROW
	 * ============================================================ */

	public IamportResponse<EscrowLogisInvoice> getEscrowLogis(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/escrows/logis/" + impUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<EscrowLogisInvoice>>(){}.getType();
			IamportResponse<EscrowLogisInvoice> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<EscrowLogisInvoice> postEscrowLogis(String impUid, EscrowLogisData logisData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(logisData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/escrows/logis/" + impUid;
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<EscrowLogisInvoice>>(){}.getType();
			IamportResponse<EscrowLogisInvoice> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<EscrowLogisInvoice> putEscrowLogis(String impUid, EscrowLogisData logisData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(logisData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/escrows/logis/" + impUid;
			String response = this.putRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<EscrowLogisInvoice>>(){}.getType();
			IamportResponse<EscrowLogisInvoice> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  CODES (banks / cards)
	 * ============================================================ */

	public IamportResponse<List<StandardCode>> allBankCodes() throws Exception {
		String token = this.getToken();

		if (token != null) {
			String response = this.getRequest("/banks", token);

			Type listType = new TypeToken<IamportResponse<List<StandardCode>>>(){}.getType();
			IamportResponse<List<StandardCode>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<StandardCode> bankCode(String code) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/banks/" + code;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<StandardCode>>(){}.getType();
			IamportResponse<StandardCode> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<StandardCode>> allCardCodes() throws Exception {
		String token = this.getToken();

		if (token != null) {
			String response = this.getRequest("/cards", token);

			Type listType = new TypeToken<IamportResponse<List<StandardCode>>>(){}.getType();
			IamportResponse<List<StandardCode>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<StandardCode> cardCode(String code) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/cards/" + code;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<StandardCode>>(){}.getType();
			IamportResponse<StandardCode> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  BENEPIA
	 * ============================================================ */

	public IamportResponse<BenepiaPoint> benepiaPoint(BenepiaPointData pointData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(pointData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/benepia/point", token, data);

			Type listType = new TypeToken<IamportResponse<BenepiaPoint>>(){}.getType();
			IamportResponse<BenepiaPoint> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> benepiaPayment(BenepiaPaymentData paymentData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(paymentData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/benepia/payment", token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  CVS (convenience store payments)
	 * ============================================================ */

	public IamportResponse<Payment> issueCvsPayment(CvsPaymentData cvsData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(cvsData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/cvs", token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> revokeCvsPayment(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/cvs/" + impUid;
			String response = this.deleteRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  KCPQUICK
	 * ============================================================ */

	public IamportResponse<EmptyResponse> deleteKcpQuickMember(String memberId) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/kcpquick/members/" + memberId;
			String response = this.deleteRequest(path, token);

			Type listType = new TypeToken<IamportResponse<EmptyResponse>>(){}.getType();
			IamportResponse<EmptyResponse> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> payKcpQuickMoney(KcpQuickPaymentData paymentData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(paymentData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/kcpquick/payment/money", token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  NAVER
	 * ============================================================ */

	public IamportResponse<List<NaverProductOrder>> naverProductOrders(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/" + impUid + "/naver/product-orders";
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<NaverProductOrder> naverProductOrderSingle(String productOrderId) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/naver/product-orders/" + productOrderId;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<NaverProductOrder>>(){}.getType();
			IamportResponse<NaverProductOrder> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverReview>> naverReviews() throws Exception {
		String token = this.getToken();

		if (token != null) {
			String response = this.getRequest("/naver/reviews", token);

			Type listType = new TypeToken<IamportResponse<List<NaverReview>>>(){}.getType();
			IamportResponse<List<NaverReview>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<NaverCashAmount> naverCashAmount(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/payments/" + impUid + "/naver/cash-amount";
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<NaverCashAmount>>(){}.getType();
			IamportResponse<NaverCashAmount> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverCancelOrders(String impUid, NaverCancelData cancelData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(cancelData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/cancel";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverShippingOrders(String impUid, NaverShipData shippingData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(shippingData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/ship";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverPlaceOrders(String impUid, NaverPlaceData placeData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(placeData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/place";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<EmptyResponse> naverConfirmOrders(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			StringEntity data = new StringEntity("{}");

			String path = "/payments/" + impUid + "/naver/confirm";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<EmptyResponse>>(){}.getType();
			IamportResponse<EmptyResponse> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverRequestReturnOrders(String impUid, NaverRequestReturnData requestReturnData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(requestReturnData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/request-return";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverApproveReturnOrders(String impUid, NaverApproveReturnData approveReturnData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(approveReturnData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/approve-return";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverRejectReturnOrders(String impUid, NaverRejectReturnData rejectReturnData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(rejectReturnData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/reject-return";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverWithholdReturnOrders(String impUid, NaverWithholdReturnData withholdReturnData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(withholdReturnData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/withhold-return";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverResolveReturnOrders(String impUid, NaverResolveReturnData resolveReturnData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(resolveReturnData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/resolve-return";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<EmptyResponse> naverPoint(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			StringEntity data = new StringEntity("{}");

			String path = "/payments/" + impUid + "/naver/point";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<EmptyResponse>>(){}.getType();
			IamportResponse<EmptyResponse> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverApproveCancel(String impUid, NaverApproveCancelData approveData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(approveData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/approve-cancel";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverCollectExchanged(String impUid, NaverCollectExchangedData collectData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(collectData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/collect-exchanged";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<List<NaverProductOrder>> naverShipExchanged(String impUid, NaverShipExchangedData shipData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(shipData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/payments/" + impUid + "/naver/ship-exchanged";
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<List<NaverProductOrder>>>(){}.getType();
			IamportResponse<List<NaverProductOrder>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  PARTNERS
	 * ============================================================ */

	public IamportResponse<EmptyResponse> partnerReceipt(String impUid, PartnersReceiptData receiptData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(receiptData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/partners/receipts/" + impUid;
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<EmptyResponse>>(){}.getType();
			IamportResponse<EmptyResponse> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  PAYMENTWALL
	 * ============================================================ */

	public IamportResponse<EmptyResponse> paymentwallDelivery(PaymentwallDeliveryData deliveryData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(deliveryData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/paymentwall/delivery", token, data);

			Type listType = new TypeToken<IamportResponse<EmptyResponse>>(){}.getType();
			IamportResponse<EmptyResponse> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  RECEIPTS
	 * ============================================================ */

	public IamportResponse<Receipt> getReceipt(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/receipts/" + impUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Receipt>>(){}.getType();
			IamportResponse<Receipt> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Receipt> issueReceipt(String impUid, ReceiptData receiptData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(receiptData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/receipts/" + impUid;
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<Receipt>>(){}.getType();
			IamportResponse<Receipt> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Receipt> revokeReceipt(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/receipts/" + impUid;
			String response = this.deleteRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Receipt>>(){}.getType();
			IamportResponse<Receipt> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<ExternalReceipt> getExternalReceipt(String merchantUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/receipts/external/" + merchantUid;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<ExternalReceipt>>(){}.getType();
			IamportResponse<ExternalReceipt> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<ExternalReceipt> issueExternalReceipt(String merchantUid, ExternalReceiptData receiptData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(receiptData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/receipts/external/" + merchantUid;
			String response = this.postRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<ExternalReceipt>>(){}.getType();
			IamportResponse<ExternalReceipt> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<ExternalReceipt> revokeExternalReceipt(String merchantUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/receipts/external/" + merchantUid;
			String response = this.deleteRequest(path, token);

			Type listType = new TypeToken<IamportResponse<ExternalReceipt>>(){}.getType();
			IamportResponse<ExternalReceipt> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  TIERS
	 * ============================================================ */

	public IamportResponse<TierInfo> getTier(String tierCode) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/tiers/" + tierCode;
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<TierInfo>>(){}.getType();
			IamportResponse<TierInfo> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  USERS
	 * ============================================================ */

	public IamportResponse<List<PgInfo>> getPgSettings() throws Exception {
		String token = this.getToken();

		if (token != null) {
			String response = this.getRequest("/users/pg", token);

			Type listType = new TypeToken<IamportResponse<List<PgInfo>>>(){}.getType();
			IamportResponse<List<PgInfo>> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  VBANKS
	 * ============================================================ */

	public IamportResponse<Payment> createVbank(VbankData vbankData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(vbankData);
			StringEntity data = new StringEntity(jsonData);

			String response = this.postRequest("/vbanks", token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<VbankHolder> getVbankHolder(String bankCode, String bankNum) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/vbanks/holder?bank_code=" + encode(bankCode) + "&bank_num=" + encode(bankNum);
			String response = this.getRequest(path, token);

			Type listType = new TypeToken<IamportResponse<VbankHolder>>(){}.getType();
			IamportResponse<VbankHolder> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> modifyVbank(String impUid, VbankEditData vbankData) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String jsonData = gson.toJson(vbankData);
			StringEntity data = new StringEntity(jsonData);

			String path = "/vbanks/" + impUid;
			String response = this.putRequest(path, token, data);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	public IamportResponse<Payment> revokeVbank(String impUid) throws Exception {
		String token = this.getToken();

		if (token != null) {
			String path = "/vbanks/" + impUid;
			String response = this.deleteRequest(path, token);

			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> result = gson.fromJson(response, listType);

			return result;
		}
		return null;
	}

	/* ============================================================
	 *  Utility
	 * ============================================================ */

	private static String encode(String value) {
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}
}
