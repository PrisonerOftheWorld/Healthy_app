
package com.devilsoftware.healthy.Models.Organisations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Availability {

    @SerializedName("Everyday")
    @Expose
    public Boolean everyday;
    @SerializedName("TwentyFourHours")
    @Expose
    public Boolean twentyFourHours;

}
