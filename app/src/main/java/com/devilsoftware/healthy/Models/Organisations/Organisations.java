
package com.devilsoftware.healthy.Models.Organisations;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organisations {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("properties")
    @Expose
    public Properties properties;
    @SerializedName("features")
    @Expose
    public List<Feature> features = null;

}
