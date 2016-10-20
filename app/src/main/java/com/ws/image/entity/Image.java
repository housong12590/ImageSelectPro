package com.ws.image.entity;

/**
 * Author: 30453
 * Date: 2016/10/19 15:05
 */
public class Image {

    public String path;
    public String name;
    public long time;

    public boolean isCamera;

    public Image(String path, String name, long time) {
        this.path = path;
        this.name = name;
        this.time = time;
    }

    public Image() {
        isCamera = true;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Image image = (Image) o;
            return this.path.equalsIgnoreCase(image.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }

}
