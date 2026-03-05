package com.siot.IamportRestHttpClientJava.response.escrow;

import com.google.gson.annotations.SerializedName;

public class EscrowLogisInvoice {

	@SerializedName("company")
	private String company;

	@SerializedName("invoice")
	private String invoice;

	@SerializedName("sent_at")
	private long sent_at;

	@SerializedName("applied_at")
	private long applied_at;

	public String getCompany() {
		return company;
	}

	public String getInvoice() {
		return invoice;
	}

	public long getSentAt() {
		return sent_at;
	}

	public long getAppliedAt() {
		return applied_at;
	}
}
