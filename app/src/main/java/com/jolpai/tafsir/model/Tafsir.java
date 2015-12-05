package com.jolpai.tafsir.model;

/**
 * Created by Tanim reja on 7/14/2015.
 */
public class Tafsir {

    private  String id,
                    name;
    private boolean isChecked;



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

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    @Override
    public String toString(){
        return "TAFSIR";
    }
}
