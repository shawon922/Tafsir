package com.jolpai.tafsir.model;

/**
 * Created by Tanim reja on 7/14/2015.
 */
public class Translation {
    private  String id,
            name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "TRANSLATION";
    }
}
