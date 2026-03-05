package com.siot.IamportRestHttpClientJava.response;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class PaymentBalanceEntry {

	@SerializedName("cash_receipt")
	Balance cash_receipt;

	@SerializedName("primary")
	Balance primary;

	@SerializedName("secondary")
	Balance secondary;

	@SerializedName("discount")
	Balance discount;

	@SerializedName("created")
	long created;

	public Balance getCashReceipt() {
		return cash_receipt;
	}

	public Balance getPrimary() {
		return primary;
	}

	public Balance getSecondary() {
		return secondary;
	}

	public Balance getDiscount() {
		return discount;
	}

	public Date getCreated() {
		return new Date( created * 1000L );
	}
}
