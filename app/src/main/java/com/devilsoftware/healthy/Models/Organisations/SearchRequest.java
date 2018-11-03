
package com.devilsoftware.healthy.Models.Organisations;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchRequest {

    @SerializedName("request")
    @Expose
    public String request;
    @SerializedName("results")
    @Expose
    public Integer results;
    @SerializedName("skip")
    @Expose
    public Integer skip;
    @SerializedName("boundedBy")
    @Expose
    public List<List<Float>> boundedBy = null;

}
