
package com.devilsoftware.healthy.Models.Organisations;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResponse {

    @SerializedName("found")
    @Expose
    public Integer found;
    @SerializedName("boundedBy")
    @Expose
    public List<List<Float>> boundedBy = null;
    @SerializedName("display")
    @Expose
    public String display;

}
