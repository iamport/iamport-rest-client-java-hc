package com.siot.IamportRestHttpClientJava.request.naver;

import com.google.gson.annotations.SerializedName;

public class NaverWithholdReturnData {

    @SerializedName("product_order_id")
    private String[] product_order_id;

    @SerializedName("reason")
    private String reason;

    @SerializedName("memo")
    private String memo;

    @SerializedName("extra_charge")
    private int extra_charge;

    public NaverWithholdReturnData(String memo) {
        this.memo = memo;
    }

    public void setProductOrderId(String[] product_order_id) {
        this.product_order_id = product_order_id;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setExtraCharge(int extra_charge) {
        this.extra_charge = extra_charge;
    }
}
