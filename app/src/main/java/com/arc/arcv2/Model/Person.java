package com.arc.arcv2.Model;

import android.graphics.Bitmap;

/**
 * Created by kodetr on 25/12/2017.
 */

public class Person {

    String name;
    Bitmap image;

    public Person(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}