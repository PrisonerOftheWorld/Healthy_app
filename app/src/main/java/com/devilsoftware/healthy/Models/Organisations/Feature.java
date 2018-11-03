
package com.devilsoftware.healthy.Models.Organisations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feature {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("properties")
    @Expose
    public Properties_ properties;
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;

}
