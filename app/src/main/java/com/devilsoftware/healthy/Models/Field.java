package com.devilsoftware.healthy.Models;

import java.util.List;

public class Field {

    public String title;
    public int id;
    public List<String> symptoms;
    public List<Float> ids;

    public Field(String title, int id, List<String> symptoms, List<Float> ids) {
        this.title = title;
        this.id = id;
        this.symptoms = symptoms;
        this.ids = ids;
    }
}
