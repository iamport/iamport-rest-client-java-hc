package com.siot.IamportRestHttpClientJava.response;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("customer_uid")
    private String customer_uid;

    @SerializedName("merchant_uid")
    private String merchant_uid;

    @SerializedName("imp_uid")
    private String imp_uid;

    @SerializedName("schedule_at")
    private long schedule_at;

    @SerializedName("executed_at")
    private long executed_at;

    @SerializedName("revoked_at")
    private long revoked_at;

    @SerializedName("amount")
    private BigDecimal amount;

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

    @SerializedName("schedule_status")
    private String schedule_status;

    @SerializedName("payment_status")
    private String payment_status;

    @SerializedName("fail_reason")
    private String fail_reason;

    public String getCustomerUid() {
        return customer_uid;
    }

    public String getMerchantUid() {
        return merchant_uid;
    }

    public String getImpUid() {
        return imp_uid;
    }

    public Date getScheduleAt() {
        return new Date( schedule_at * 1000L );
    }

    public Date getExecutedAt() {
        return new Date( executed_at * 1000L );
    }

    public Date getRevokedAt() {
        return new Date( revoked_at * 1000L );
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getBuyerName() {
        return buyer_name;
    }

    public String getBuyerEmail() {
        return buyer_email;
    }

    public String getBuyerTel() {
        return buyer_tel;
    }

    public String getBuyerAddr() {
        return buyer_addr;
    }

    public String getBuyerPostcode() {
        return buyer_postcode;
    }

    public String getCustomData() {
        return custom_data;
    }

    public String getScheduleStatus() {
        return schedule_status;
    }

    public String getPaymentStatus() {
        return payment_status;
    }

    public String getFailReason() {
        return fail_reason;
    }
}
