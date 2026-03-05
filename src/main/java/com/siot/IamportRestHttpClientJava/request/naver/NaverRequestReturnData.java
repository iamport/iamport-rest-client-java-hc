package com.siot.IamportRestHttpClientJava.request.naver;

import com.google.gson.annotations.SerializedName;

public class NaverRequestReturnData {

    public static final String REASON_INTENT_CHANGED = "INTENT_CHANGED";
    public static final String REASON_COLOR_AND_SIZE = "COLOR_AND_SIZE";
    public static final String REASON_WRONG_ORDER = "WRONG_ORDER";
    public static final String REASON_PRODUCT_UNSATISFIED = "PRODUCT_UNSATISFIED";
    public static final String REASON_DELAYED_DELIVERY = "DELAYED_DELIVERY";
    public static final String REASON_SOLD_OUT = "SOLD_OUT";

    @SerializedName("product_order_id")
    private String[] product_order_id;

    @SerializedName("reason")
    private String reason;

    @SerializedName("delivery_method")
    private String delivery_method;

    @SerializedName("delivery_company")
    private String delivery_company;

    @SerializedName("tracking_number")
    private String tracking_number;

    public NaverRequestReturnData(String delivery_method) {
        this(NaverRequestReturnData.REASON_INTENT_CHANGED, delivery_method);
    }

    public NaverRequestReturnData(String reason, String delivery_method) {
        this.reason = reason;
        this.delivery_method = delivery_method;
    }

    public void setProductOrderId(String[] product_order_id) {
        this.product_order_id = product_order_id;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDeliveryMethod(String delivery_method) {
        this.delivery_method = delivery_method;
    }

    public void setDeliveryCompany(String delivery_company) {
        this.delivery_company = delivery_company;
    }

    public void setTrackingNumber(String tracking_number) {
        this.tracking_number = tracking_number;
    }
}
