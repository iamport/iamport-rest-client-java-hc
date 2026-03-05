package com.siot.IamportRestHttpClientJava.request;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class AgainPaymentData {

    @SerializedName("customer_uid")
    private String customer_uid;

    @SerializedName("merchant_uid")
    private String merchant_uid;

    @SerializedName("amount")
    private BigDecimal amount;

    @SerializedName("tax_free")
    private BigDecimal tax_free;

    @SerializedName("vat")
    private BigDecimal vat;

    @SerializedName("currency")
    private String currency;

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

    @SerializedName("browser_ip")
    private String browser_ip;

    @SerializedName("card_quota")
    private int card_quota;

    @SerializedName("custom_data")
    private String custom_data;

    @SerializedName("notice_url")
    private String notice_url;

    public AgainPaymentData(String customer_uid, String merchant_uid, BigDecimal amount) {
        this.customer_uid = customer_uid;
        this.merchant_uid = merchant_uid;
        this.amount = amount;
    }

    public void setTaxFree(BigDecimal tax_free) {
        this.tax_free = tax_free;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBuyerName(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public void setBuyerEmail(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public void setBuyerTel(String buyer_tel) {
        this.buyer_tel = buyer_tel;
    }

    public void setBuyerAddr(String buyer_addr) {
        this.buyer_addr = buyer_addr;
    }

    public void setBuyerPostcode(String buyer_postcode) {
        this.buyer_postcode = buyer_postcode;
    }

    public void setCardQuota(int card_quota) {
        this.card_quota = card_quota;
    }

    public void setBrowser_ip(String browser_ip) {
        this.browser_ip = browser_ip;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setCustomData(String custom_data) {
        this.custom_data = custom_data;
    }

    public void setNoticeUrl(String notice_url) {
        this.notice_url = notice_url;
    }
}
