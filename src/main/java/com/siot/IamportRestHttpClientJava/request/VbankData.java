package com.siot.IamportRestHttpClientJava.request;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class VbankData {

	@SerializedName("channel_key")
	private String channel_key;

	@SerializedName("merchant_uid")
	private String merchant_uid;

	@SerializedName("amount")
	private BigDecimal amount;

	@SerializedName("vbank_code")
	private String vbank_code;

	@SerializedName("vbank_due")
	private long vbank_due;

	@SerializedName("vbank_holder")
	private String vbank_holder;

	@SerializedName("vbank_num")
	private String vbank_num;

	@SerializedName("name")
	private String name;

	@SerializedName("buyer_name")
	private String buyer_name;

	@SerializedName("buyer_email")
	private String buyer_email;

	@SerializedName("buyer_tel")
	private String buyer_tel;

	@SerializedName("buyer_addr")
	private String buyer_addr;

	@SerializedName("buyer_postcode")
	private String buyer_postcode;

	@SerializedName("custom_data")
	private String custom_data;

	public VbankData(String channel_key, String merchant_uid, BigDecimal amount, String vbank_code, long vbank_due) {
		this.channel_key = channel_key;
		this.merchant_uid = merchant_uid;
		this.amount = amount;
		this.vbank_code = vbank_code;
		this.vbank_due = vbank_due;
	}

	public void setVbank_holder(String vbank_holder) {
		this.vbank_holder = vbank_holder;
	}

	public void setVbank_num(String vbank_num) {
		this.vbank_num = vbank_num;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public void setBuyer_tel(String buyer_tel) {
		this.buyer_tel = buyer_tel;
	}

	public void setBuyer_addr(String buyer_addr) {
		this.buyer_addr = buyer_addr;
	}

	public void setBuyer_postcode(String buyer_postcode) {
		this.buyer_postcode = buyer_postcode;
	}

	public void setCustom_data(String custom_data) {
		this.custom_data = custom_data;
	}
}
