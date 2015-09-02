package com.jolpai.tafsir.custom.view;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Tanim Reja on 9/1/2015.
 */
public class Typefaces {

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String name){
        synchronized(cache){
            if(!cache.containsKey(name)){
                String n = String.format("fonts/%s.ttf", name);
                Typeface t = Typeface.createFromAsset(c.getAssets(),n);
                cache.put(name, t);
            }
            return cache.get(name);
        }
    }
}
