
package com.devilsoftware.healthy.Models.Organisations;

import java.util.List;

import com.devilsoftware.healthy.Models.Organisations.Availability;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hours {

    @SerializedName("Availabilities")
    @Expose
    public List<Availability> availabilities = null;
    @SerializedName("text")
    @Expose
    public String text;

}
