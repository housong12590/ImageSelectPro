package com.ws.image.entity;

import java.util.List;

/**
 * Author: 30453
 * Date: 2016/10/19 15:11
 */
public class Folder {
    public String name;
    public String path;
    public Image cover;
    public List<Image> images;
    public boolean isAll;

    public Folder() {
    }

    public Folder(boolean isAll) {
        this.isAll = isAll;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Folder folder = (Folder) o;
            return this.path.equalsIgnoreCase(folder.path);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return super.equals(o);
    }
}
