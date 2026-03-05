package com.siot.IamportRestHttpClientJava.request;

import com.google.gson.annotations.SerializedName;

public class ReceiptData {

	@SerializedName("identifier")
	private String identifier;

	@SerializedName("identifier_type")
	private String identifier_type;

	@SerializedName("type")
	private String type;

	@SerializedName("buyer_name")
	private String buyer_name;

	@SerializedName("buyer_email")
	private String buyer_email;

	@SerializedName("buyer_tel")
	private String buyer_tel;

	@SerializedName("tax_free")
	private Integer tax_free;

	@SerializedName("vat_amount")
	private Integer vat_amount;

	public ReceiptData(String identifier) {
		this.identifier = identifier;
	}

	public void setIdentifier_type(String identifier_type) {
		this.identifier_type = identifier_type;
	}

	public void setType(String type) {
		this.type = type;
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

	public void setTax_free(Integer tax_free) {
		this.tax_free = tax_free;
	}

	public void setVat_amount(Integer vat_amount) {
		this.vat_amount = vat_amount;
	}
}
