
package com.devilsoftware.healthy.Models.Organisations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("class")
    @Expose
    public String _class;
    @SerializedName("name")
    @Expose
    public String name;

}
