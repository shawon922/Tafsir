package com.jolpai.tafsir.entity;

/**
 * Created by Tanim reja on 7/14/2015.
 */
public class Audio {

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
        return "AUDIO";
    }
}
