
package com.devilsoftware.healthy.Models.Organisations;

import java.util.List;

import com.devilsoftware.healthy.Models.Organisations.Category;
import com.devilsoftware.healthy.Models.Organisations.Hours;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyMetaData {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("Categories")
    @Expose
    public List<Category> categories = null;
    @SerializedName("Phones")
    @Expose
    public List<Phone> phones = null;
    @SerializedName("Hours")
    @Expose
    public Hours hours;

}
