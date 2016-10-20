package com.ws.image;

import android.content.Context;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * Author: 30453
 * Date: 2016/10/19 13:47
 */
public interface ImageLoader extends Serializable {

    void displayImage(Context context, String path, ImageView imageView);

}
