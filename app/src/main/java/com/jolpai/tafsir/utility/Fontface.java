package com.jolpai.tafsir.utility;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by Tanim Reja on 9/1/2015.
 */
public class Fontface {

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface get(Context c, String name) {
        if (name != null) {
            synchronized (cache) {
                if (!cache.containsKey(name)) {
                    String n = String.format("fonts/%s.ttf", name);
                    Typeface t = Typeface.createFromAsset(c.getAssets(), n);
                    cache.put(name, t);
                }
                return cache.get(name);
            }
        }
        return null;
    }

}
