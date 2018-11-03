
package com.devilsoftware.healthy.Models.Organisations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseMetaData {

    @SerializedName("SearchRequest")
    @Expose
    public SearchRequest searchRequest;
    @SerializedName("SearchResponse")
    @Expose
    public SearchResponse searchResponse;

}
