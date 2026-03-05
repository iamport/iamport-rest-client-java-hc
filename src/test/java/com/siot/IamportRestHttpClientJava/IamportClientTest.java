package com.siot.IamportRestHttpClientJava;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.siot.IamportRestHttpClientJava.request.*;
import com.siot.IamportRestHttpClientJava.request.escrow.*;
import com.siot.IamportRestHttpClientJava.request.naver.*;
import com.siot.IamportRestHttpClientJava.response.*;
import com.siot.IamportRestHttpClientJava.response.escrow.EscrowLogisInvoice;
import com.siot.IamportRestHttpClientJava.response.naver.NaverCashAmount;
import com.siot.IamportRestHttpClientJava.response.naver.NaverProductOrder;
import com.siot.IamportRestHttpClientJava.response.naver.NaverReview;

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

	/* PAYMENTS */

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
	public void testGetPaymentByMerchantUidWithStatus() throws Exception {
		IamportResponse<Payment> result = client.paymentByMerchantUid("merchant_1448280088556", "paid");
		if (result != null && result.getResponse() != null) {
			System.out.println(result.getResponse().getMerchantUid());
		}
	}

	@Test
	public void testGetPaymentsByImpUid() throws Exception {
		List<String> impUids = new ArrayList<String>();
		impUids.add("imp_448280090638");
		IamportResponse<List<Payment>> result = client.paymentsByImpUid(impUids);
		if (result != null && result.getResponse() != null) {
			System.out.println("payments count: " + result.getResponse().size());
		}
	}

	@Test
	public void testGetPaymentsByMerchantUid() throws Exception {
		IamportResponse<PagedDataList<Payment>> result = client.paymentsByMerchantUid("merchant_1448280088556", "paid");
		if (result != null && result.getResponse() != null) {
			System.out.println("total: " + result.getResponse().getTotal());
		}
	}

	@Test
	public void testGetPaymentsByStatus() throws Exception {
		IamportResponse<PagedDataList<Payment>> result = client.paymentsByStatus("paid");
		if (result != null && result.getResponse() != null) {
			System.out.println("total: " + result.getResponse().getTotal());
		}
	}

	@Test
	public void testPaymentBalanceByImpUid() throws Exception {
		IamportResponse<PaymentBalance> result = client.paymentBalanceByImpUid("imp_448280090638");
		if (result != null && result.getResponse() != null) {
			System.out.println("balance amount: " + result.getResponse().getAmount());
		}
	}

	@Test
	public void testCancelPaymentByImpUid() throws Exception {
		CancelData cancel1 = new CancelData("imp_448280090638", true);
		IamportResponse<Payment> cancelpayment1 = client.cancelPayment(cancel1);
		System.out.println(cancelpayment1.getMessage());
	}

	@Test
	public void testCancelPaymentByMerchantUid() throws Exception {
		CancelData cancel2 = new CancelData("merchant_1448280088556", false);
		IamportResponse<Payment> cancelpayment2 = client.cancelPayment(cancel2);
		System.out.println(cancelpayment2.getMessage());
	}

	/* PREPARE */

	@Test
	public void testPostPrepare() throws Exception {
		PrepareData prepareData = new PrepareData("merchant_test_prepare", new BigDecimal(1000));
		IamportResponse<Prepare> result = client.postPrepare(prepareData);
		if (result != null) {
			System.out.println("prepare code: " + result.getCode());
		}
	}

	@Test
	public void testGetPrepare() throws Exception {
		IamportResponse<Prepare> result = client.getPrepare("merchant_test_prepare");
		if (result != null && result.getResponse() != null) {
			System.out.println("prepare amount: " + result.getResponse().getAmount());
		}
	}

	@Test
	public void testPutPrepare() throws Exception {
		PrepareData prepareData = new PrepareData("merchant_test_prepare", new BigDecimal(2000));
		IamportResponse<Prepare> result = client.putPrepare(prepareData);
		if (result != null) {
			System.out.println("put prepare code: " + result.getCode());
		}
	}

	/* CERTIFICATIONS */

	@Test
	public void testCertificationByImpUid() throws Exception {
		IamportResponse<Certification> result = client.certificationByImpUid("imp_test_cert");
		if (result != null) {
			System.out.println("certification code: " + result.getCode());
		}
	}

	@Test
	public void testDeleteCertification() throws Exception {
		IamportResponse<Certification> result = client.deleteCertification("imp_test_cert");
		if (result != null) {
			System.out.println("delete certification code: " + result.getCode());
		}
	}

	@Test
	public void testOtpRequest() throws Exception {
		OtpRequestData otpData = new OtpRequestData("홍길동", "01012341234", "19900101", "1", "SKT", "channel_key_test");
		IamportResponse<Certification> result = client.otpRequest(otpData);
		if (result != null) {
			System.out.println("otp request code: " + result.getCode());
		}
	}

	@Test
	public void testOtpConfirm() throws Exception {
		OtpConfirmData otpData = new OtpConfirmData("123456");
		IamportResponse<Certification> result = client.otpConfirm("imp_test_otp", otpData);
		if (result != null) {
			System.out.println("otp confirm code: " + result.getCode());
		}
	}

	/* SUBSCRIBE - BILLING CUSTOMER */

	@Test
	public void testGetBillingCustomer() throws Exception {
		IamportResponse<BillingCustomer> result = client.getBillingCustomer("customer_uid_test");
		if (result != null) {
			System.out.println("billing customer code: " + result.getCode());
		}
	}

	@Test
	public void testGetBillingCustomers() throws Exception {
		List<String> customerUids = new ArrayList<String>();
		customerUids.add("customer_uid_test");
		IamportResponse<List<BillingCustomer>> result = client.getBillingCustomers(customerUids);
		if (result != null) {
			System.out.println("billing customers code: " + result.getCode());
		}
	}

	@Test
	public void testPostBillingCustomer() throws Exception {
		BillingCustomerData billingData = new BillingCustomerData("1234-1234-1234-1234", "2025-12", "900101");
		IamportResponse<BillingCustomer> result = client.postBillingCustomer("customer_uid_test", billingData);
		if (result != null) {
			System.out.println("post billing customer code: " + result.getCode());
		}
	}

	@Test
	public void testDeleteBillingCustomer() throws Exception {
		IamportResponse<BillingCustomer> result = client.deleteBillingCustomer("customer_uid_test", "test reason", "API");
		if (result != null) {
			System.out.println("delete billing customer code: " + result.getCode());
		}
	}

	@Test
	public void testGetCustomerPayments() throws Exception {
		IamportResponse<PagedDataList<Payment>> result = client.getCustomerPayments("customer_uid_test", 1);
		if (result != null) {
			System.out.println("customer payments code: " + result.getCode());
		}
	}

	@Test
	public void testGetCustomerSchedules() throws Exception {
		IamportResponse<ScheduleList> result = client.getCustomerSchedules("customer_uid_test", 1, 0, 0, null);
		if (result != null) {
			System.out.println("customer schedules code: " + result.getCode());
		}
	}

	/* SUBSCRIBE - PAYMENTS */

	@Test
	public void testOnetimePayment() throws Exception {
		CardInfo card = new CardInfo("1234-1234-1234-1234", "2025-12", "900101", "12");
		OnetimePaymentData onetimeData = new OnetimePaymentData("merchant_onetime_test", new BigDecimal(1000), card);
		IamportResponse<Payment> result = client.onetimePayment(onetimeData);
		if (result != null) {
			System.out.println("onetime payment code: " + result.getCode());
		}
	}

	@Test
	public void testAgainPayment() throws Exception {
		AgainPaymentData againData = new AgainPaymentData("customer_uid_test", "merchant_again_test", new BigDecimal(1000));
		IamportResponse<Payment> result = client.againPayment(againData);
		if (result != null) {
			System.out.println("again payment code: " + result.getCode());
		}
	}

	/* SUBSCRIBE - SCHEDULE */

	@Test
	public void testGetPaymentSchedule() throws Exception {
		GetScheduleData data = new GetScheduleData(0, 2000000000, null, 1, 20);
		IamportResponse<ScheduleList> result = client.getPaymentSchedule(data);
		if (result != null) {
			System.out.println("payment schedule code: " + result.getCode());
		}
	}

	@Test
	public void testSubscribeSchedule() throws Exception {
		ScheduleData scheduleData = new ScheduleData("customer_uid_test");
		ScheduleEntry entry = new ScheduleEntry("merchant_schedule_test", System.currentTimeMillis() / 1000 + 3600, new BigDecimal(1000));
		scheduleData.addSchedule(entry);
		IamportResponse<List<Schedule>> result = client.subscribeSchedule(scheduleData);
		if (result != null) {
			System.out.println("subscribe schedule code: " + result.getCode());
		}
	}

	@Test
	public void testUnsubscribeSchedule() throws Exception {
		UnscheduleData unscheduleData = new UnscheduleData("customer_uid_test");
		unscheduleData.addMerchantUid("merchant_schedule_test");
		IamportResponse<List<Schedule>> result = client.unsubscribeSchedule(unscheduleData);
		if (result != null) {
			System.out.println("unsubscribe schedule code: " + result.getCode());
		}
	}

	@Test
	public void testGetScheduleByMerchantUid() throws Exception {
		IamportResponse<Schedule> result = client.getScheduleByMerchantUid("merchant_schedule_test");
		if (result != null) {
			System.out.println("schedule by merchant uid code: " + result.getCode());
		}
	}

	@Test
	public void testPutScheduleByMerchantUid() throws Exception {
		ScheduleUpdateData updateData = new ScheduleUpdateData(System.currentTimeMillis() / 1000 + 7200);
		IamportResponse<Schedule> result = client.putScheduleByMerchantUid("merchant_schedule_test", updateData);
		if (result != null) {
			System.out.println("put schedule code: " + result.getCode());
		}
	}

	@Test
	public void testRetrySchedule() throws Exception {
		ScheduleRetryData retryData = new ScheduleRetryData();
		IamportResponse<Payment> result = client.retrySchedule("merchant_schedule_test", retryData);
		if (result != null) {
			System.out.println("retry schedule code: " + result.getCode());
		}
	}

	@Test
	public void testRescheduleSchedule() throws Exception {
		ScheduleRescheduleData rescheduleData = new ScheduleRescheduleData(System.currentTimeMillis() / 1000 + 10800);
		IamportResponse<Schedule> result = client.rescheduleSchedule("merchant_schedule_test", rescheduleData);
		if (result != null) {
			System.out.println("reschedule code: " + result.getCode());
		}
	}

	@Test
	public void testGetScheduleByCustomerUid() throws Exception {
		IamportResponse<ScheduleList> result = client.getScheduleByCustomerUid("customer_uid_test", 1, 0, 2000000000, null);
		if (result != null) {
			System.out.println("schedule by customer uid code: " + result.getCode());
		}
	}

	/* ESCROW */

	@Test
	public void testGetEscrowLogis() throws Exception {
		IamportResponse<EscrowLogisInvoice> result = client.getEscrowLogis("imp_test_escrow");
		if (result != null) {
			System.out.println("get escrow logis code: " + result.getCode());
		}
	}

	@Test
	public void testPostEscrowLogis() throws Exception {
		EscrowLogisInvoiceData invoiceData = new EscrowLogisInvoiceData("CJ", "1234567890", System.currentTimeMillis() / 1000);
		EscrowLogisPersonData receiver = new EscrowLogisPersonData("수령인", "01012341234", "서울시 강남구", "06000");
		EscrowLogisData logisData = new EscrowLogisData(invoiceData, receiver);
		IamportResponse<EscrowLogisInvoice> result = client.postEscrowLogis("imp_test_escrow", logisData);
		if (result != null) {
			System.out.println("post escrow logis code: " + result.getCode());
		}
	}

	@Test
	public void testPutEscrowLogis() throws Exception {
		EscrowLogisInvoiceData invoiceData = new EscrowLogisInvoiceData("CJ", "1234567890", System.currentTimeMillis() / 1000);
		EscrowLogisPersonData receiver = new EscrowLogisPersonData("수령인", "01012341234", "서울시 강남구", "06000");
		EscrowLogisData logisData = new EscrowLogisData(invoiceData, receiver);
		IamportResponse<EscrowLogisInvoice> result = client.putEscrowLogis("imp_test_escrow", logisData);
		if (result != null) {
			System.out.println("put escrow logis code: " + result.getCode());
		}
	}

	/* CODES */

	@Test
	public void testAllBankCodes() throws Exception {
		IamportResponse<List<StandardCode>> result = client.allBankCodes();
		if (result != null && result.getResponse() != null) {
			System.out.println("bank codes count: " + result.getResponse().size());
		}
	}

	@Test
	public void testBankCode() throws Exception {
		IamportResponse<StandardCode> result = client.bankCode("004");
		if (result != null && result.getResponse() != null) {
			System.out.println("bank: " + result.getResponse().getName());
		}
	}

	@Test
	public void testAllCardCodes() throws Exception {
		IamportResponse<List<StandardCode>> result = client.allCardCodes();
		if (result != null && result.getResponse() != null) {
			System.out.println("card codes count: " + result.getResponse().size());
		}
	}

	@Test
	public void testCardCode() throws Exception {
		IamportResponse<StandardCode> result = client.cardCode("361");
		if (result != null && result.getResponse() != null) {
			System.out.println("card: " + result.getResponse().getName());
		}
	}

	/* BENEPIA */

	@Test
	public void testBenepiaPoint() throws Exception {
		BenepiaPointData pointData = new BenepiaPointData("user", "password", "channel_key");
		IamportResponse<BenepiaPoint> result = client.benepiaPoint(pointData);
		if (result != null) {
			System.out.println("benepia point code: " + result.getCode());
		}
	}

	@Test
	public void testBenepiaPayment() throws Exception {
		BenepiaPaymentData paymentData = new BenepiaPaymentData("user", "password", "merchant_test", new BigDecimal(1000), "test", "channel_key");
		IamportResponse<Payment> result = client.benepiaPayment(paymentData);
		if (result != null) {
			System.out.println("benepia payment code: " + result.getCode());
		}
	}

	/* CVS */

	@Test
	public void testIssueCvsPayment() throws Exception {
		CvsPaymentData cvsData = new CvsPaymentData("channel_key", "merchant_cvs_test", new BigDecimal(1000));
		IamportResponse<Payment> result = client.issueCvsPayment(cvsData);
		if (result != null) {
			System.out.println("cvs payment code: " + result.getCode());
		}
	}

	@Test
	public void testRevokeCvsPayment() throws Exception {
		IamportResponse<Payment> result = client.revokeCvsPayment("imp_cvs_test");
		if (result != null) {
			System.out.println("revoke cvs code: " + result.getCode());
		}
	}

	/* KCPQUICK */

	@Test
	public void testDeleteKcpQuickMember() throws Exception {
		IamportResponse<EmptyResponse> result = client.deleteKcpQuickMember("member_test");
		if (result != null) {
			System.out.println("delete kcpquick member code: " + result.getCode());
		}
	}

	@Test
	public void testPayKcpQuickMoney() throws Exception {
		KcpQuickPaymentData paymentData = new KcpQuickPaymentData("member_test", "channel_key", "merchant_kcp_test", "test", 1000);
		IamportResponse<Payment> result = client.payKcpQuickMoney(paymentData);
		if (result != null) {
			System.out.println("kcp quick money code: " + result.getCode());
		}
	}

	/* NAVER */

	@Test
	public void testNaverProductOrders() throws Exception {
		IamportResponse<List<NaverProductOrder>> result = client.naverProductOrders("imp_naver_test");
		if (result != null) {
			System.out.println("naver product orders code: " + result.getCode());
		}
	}

	@Test
	public void testNaverProductOrderSingle() throws Exception {
		IamportResponse<NaverProductOrder> result = client.naverProductOrderSingle("product_order_test");
		if (result != null) {
			System.out.println("naver single product order code: " + result.getCode());
		}
	}

	@Test
	public void testNaverReviews() throws Exception {
		IamportResponse<List<NaverReview>> result = client.naverReviews();
		if (result != null) {
			System.out.println("naver reviews code: " + result.getCode());
		}
	}

	@Test
	public void testNaverCashAmount() throws Exception {
		IamportResponse<NaverCashAmount> result = client.naverCashAmount("imp_naver_test");
		if (result != null) {
			System.out.println("naver cash amount code: " + result.getCode());
		}
	}

	@Test
	public void testNaverCancelOrders() throws Exception {
		NaverCancelData cancelData = new NaverCancelData(NaverCancelData.REASON_PRODUCT_UNSATISFIED);
		IamportResponse<List<NaverProductOrder>> result = client.naverCancelOrders("imp_naver_test", cancelData);
		if (result != null) {
			System.out.println("naver cancel code: " + result.getCode());
		}
	}

	@Test
	public void testNaverShippingOrders() throws Exception {
		NaverShipData shipData = new NaverShipData(NaverShipData.METHOD_DELIVERY, System.currentTimeMillis() / 1000);
		IamportResponse<List<NaverProductOrder>> result = client.naverShippingOrders("imp_naver_test", shipData);
		if (result != null) {
			System.out.println("naver ship code: " + result.getCode());
		}
	}

	@Test
	public void testNaverPlaceOrders() throws Exception {
		NaverPlaceData placeData = new NaverPlaceData();
		IamportResponse<List<NaverProductOrder>> result = client.naverPlaceOrders("imp_naver_test", placeData);
		if (result != null) {
			System.out.println("naver place code: " + result.getCode());
		}
	}

	@Test
	public void testNaverConfirmOrders() throws Exception {
		IamportResponse<EmptyResponse> result = client.naverConfirmOrders("imp_naver_test");
		if (result != null) {
			System.out.println("naver confirm code: " + result.getCode());
		}
	}

	@Test
	public void testNaverRequestReturnOrders() throws Exception {
		NaverRequestReturnData returnData = new NaverRequestReturnData("RETURN_DELIVERY");
		IamportResponse<List<NaverProductOrder>> result = client.naverRequestReturnOrders("imp_naver_test", returnData);
		if (result != null) {
			System.out.println("naver request return code: " + result.getCode());
		}
	}

	@Test
	public void testNaverApproveReturnOrders() throws Exception {
		NaverApproveReturnData approveData = new NaverApproveReturnData();
		IamportResponse<List<NaverProductOrder>> result = client.naverApproveReturnOrders("imp_naver_test", approveData);
		if (result != null) {
			System.out.println("naver approve return code: " + result.getCode());
		}
	}

	@Test
	public void testNaverRejectReturnOrders() throws Exception {
		NaverRejectReturnData rejectData = new NaverRejectReturnData("reject memo");
		IamportResponse<List<NaverProductOrder>> result = client.naverRejectReturnOrders("imp_naver_test", rejectData);
		if (result != null) {
			System.out.println("naver reject return code: " + result.getCode());
		}
	}

	@Test
	public void testNaverWithholdReturnOrders() throws Exception {
		NaverWithholdReturnData withholdData = new NaverWithholdReturnData("withhold memo");
		IamportResponse<List<NaverProductOrder>> result = client.naverWithholdReturnOrders("imp_naver_test", withholdData);
		if (result != null) {
			System.out.println("naver withhold return code: " + result.getCode());
		}
	}

	@Test
	public void testNaverResolveReturnOrders() throws Exception {
		NaverResolveReturnData resolveData = new NaverResolveReturnData();
		IamportResponse<List<NaverProductOrder>> result = client.naverResolveReturnOrders("imp_naver_test", resolveData);
		if (result != null) {
			System.out.println("naver resolve return code: " + result.getCode());
		}
	}

	@Test
	public void testNaverPoint() throws Exception {
		IamportResponse<EmptyResponse> result = client.naverPoint("imp_naver_test");
		if (result != null) {
			System.out.println("naver point code: " + result.getCode());
		}
	}

	@Test
	public void testNaverApproveCancel() throws Exception {
		NaverApproveCancelData approveData = new NaverApproveCancelData();
		IamportResponse<List<NaverProductOrder>> result = client.naverApproveCancel("imp_naver_test", approveData);
		if (result != null) {
			System.out.println("naver approve cancel code: " + result.getCode());
		}
	}

	@Test
	public void testNaverCollectExchanged() throws Exception {
		NaverCollectExchangedData collectData = new NaverCollectExchangedData();
		IamportResponse<List<NaverProductOrder>> result = client.naverCollectExchanged("imp_naver_test", collectData);
		if (result != null) {
			System.out.println("naver collect exchanged code: " + result.getCode());
		}
	}

	@Test
	public void testNaverShipExchanged() throws Exception {
		NaverShipExchangedData shipData = new NaverShipExchangedData(NaverShipExchangedData.METHOD_DELIVERY);
		IamportResponse<List<NaverProductOrder>> result = client.naverShipExchanged("imp_naver_test", shipData);
		if (result != null) {
			System.out.println("naver ship exchanged code: " + result.getCode());
		}
	}

	/* PARTNERS */

	@Test
	public void testPartnerReceipt() throws Exception {
		List<PartnersReceiptData.PartnerEntry> entries = new ArrayList<PartnersReceiptData.PartnerEntry>();
		entries.add(new PartnersReceiptData.PartnerEntry("1234567890", "test company", new BigDecimal(1000)));
		PartnersReceiptData receiptData = new PartnersReceiptData(entries);
		IamportResponse<EmptyResponse> result = client.partnerReceipt("imp_test", receiptData);
		if (result != null) {
			System.out.println("partner receipt code: " + result.getCode());
		}
	}

	/* PAYMENTWALL */

	@Test
	public void testPaymentwallDelivery() throws Exception {
		PaymentwallDeliveryData deliveryData = new PaymentwallDeliveryData(
			"imp_test", "merchant_test", "physical", "delivering",
			System.currentTimeMillis() / 1000 + 86400, System.currentTimeMillis() / 1000,
			"yes", "test@test.com"
		);
		IamportResponse<EmptyResponse> result = client.paymentwallDelivery(deliveryData);
		if (result != null) {
			System.out.println("paymentwall delivery code: " + result.getCode());
		}
	}

	/* RECEIPTS */

	@Test
	public void testGetReceipt() throws Exception {
		IamportResponse<Receipt> result = client.getReceipt("imp_receipt_test");
		if (result != null) {
			System.out.println("get receipt code: " + result.getCode());
		}
	}

	@Test
	public void testIssueReceipt() throws Exception {
		ReceiptData receiptData = new ReceiptData("0101234567");
		IamportResponse<Receipt> result = client.issueReceipt("imp_receipt_test", receiptData);
		if (result != null) {
			System.out.println("issue receipt code: " + result.getCode());
		}
	}

	@Test
	public void testRevokeReceipt() throws Exception {
		IamportResponse<Receipt> result = client.revokeReceipt("imp_receipt_test");
		if (result != null) {
			System.out.println("revoke receipt code: " + result.getCode());
		}
	}

	@Test
	public void testGetExternalReceipt() throws Exception {
		IamportResponse<ExternalReceipt> result = client.getExternalReceipt("merchant_receipt_test");
		if (result != null) {
			System.out.println("get external receipt code: " + result.getCode());
		}
	}

	@Test
	public void testIssueExternalReceipt() throws Exception {
		ExternalReceiptData receiptData = new ExternalReceiptData("channel_key", "test", 1000, "0101234567");
		IamportResponse<ExternalReceipt> result = client.issueExternalReceipt("merchant_receipt_test", receiptData);
		if (result != null) {
			System.out.println("issue external receipt code: " + result.getCode());
		}
	}

	@Test
	public void testRevokeExternalReceipt() throws Exception {
		IamportResponse<ExternalReceipt> result = client.revokeExternalReceipt("merchant_receipt_test");
		if (result != null) {
			System.out.println("revoke external receipt code: " + result.getCode());
		}
	}

	/* TIERS */

	@Test
	public void testGetTier() throws Exception {
		IamportResponse<TierInfo> result = client.getTier("tier_test");
		if (result != null) {
			System.out.println("get tier code: " + result.getCode());
		}
	}

	/* USERS */

	@Test
	public void testGetPgSettings() throws Exception {
		IamportResponse<List<PgInfo>> result = client.getPgSettings();
		if (result != null && result.getResponse() != null) {
			System.out.println("pg settings count: " + result.getResponse().size());
		}
	}

	/* VBANKS */

	@Test
	public void testCreateVbank() throws Exception {
		VbankData vbankData = new VbankData("channel_key", "merchant_vbank_test", new BigDecimal(1000), "004", System.currentTimeMillis() / 1000 + 86400);
		IamportResponse<Payment> result = client.createVbank(vbankData);
		if (result != null) {
			System.out.println("create vbank code: " + result.getCode());
		}
	}

	@Test
	public void testGetVbankHolder() throws Exception {
		IamportResponse<VbankHolder> result = client.getVbankHolder("004", "12345678901234");
		if (result != null) {
			System.out.println("vbank holder code: " + result.getCode());
		}
	}

	@Test
	public void testModifyVbank() throws Exception {
		VbankEditData vbankEditData = new VbankEditData();
		vbankEditData.setAmount(2000);
		IamportResponse<Payment> result = client.modifyVbank("imp_vbank_test", vbankEditData);
		if (result != null) {
			System.out.println("modify vbank code: " + result.getCode());
		}
	}

	@Test
	public void testRevokeVbank() throws Exception {
		IamportResponse<Payment> result = client.revokeVbank("imp_vbank_test");
		if (result != null) {
			System.out.println("revoke vbank code: " + result.getCode());
		}
	}
}
