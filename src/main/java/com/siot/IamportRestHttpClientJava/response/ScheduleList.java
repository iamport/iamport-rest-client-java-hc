package com.siot.IamportRestHttpClientJava.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleList {
    @SerializedName("total")
    private int total;

    @SerializedName("previous")
    private int previous;

    @SerializedName("next")
    private int next;

    @SerializedName("list")
    private List<Schedule> list;

    public int getTotal() {
        return total;
    }

    public int getPrevious() {
        return previous;
    }

    public int getNext() {
        return next;
    }

    public List<Schedule> getList() {
        return list;
    }
}
