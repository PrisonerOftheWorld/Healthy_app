
package com.devilsoftware.healthy.Models.Organisations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties_ {

    @SerializedName("CompanyMetaData")
    @Expose
    public CompanyMetaData companyMetaData;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("name")
    @Expose
    public String name;

}
