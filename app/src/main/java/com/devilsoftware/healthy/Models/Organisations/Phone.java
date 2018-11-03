
package com.devilsoftware.healthy.Models.Organisations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phone {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("formatted")
    @Expose
    public String formatted;

}
