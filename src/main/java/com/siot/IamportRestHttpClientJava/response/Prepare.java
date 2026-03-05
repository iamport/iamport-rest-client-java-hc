package com.siot.IamportRestHttpClientJava.response;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Prepare {

    @SerializedName("merchant_uid")
    String merchant_uid;

    @SerializedName("amount")
    BigDecimal amount;

    public String getMerchantUid() {
        return merchant_uid;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
