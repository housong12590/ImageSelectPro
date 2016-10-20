package com.ws.image;

import java.io.Serializable;

/**
 * Author: 30453
 * Date: 2016/10/19 13:43
 */
public class ImgSelConfig {

    public boolean needCrop;

    public boolean multiSelect;

    public int maxNum;

    public boolean needCamera;

    public String filePath;

    public String title;

    public int aspectX;
    public int aspectY;
    public int outputX;
    public int outputY;

    public ImageLoader loader;

    public ImgSelConfig(Builder builder) {
        this.needCrop = builder.needCrop;
        this.maxNum = builder.maxNum;
        this.multiSelect = builder.multiSelect;
        this.needCamera = builder.needCamera;
        this.title = builder.title;
        this.filePath = builder.filePath;
        this.loader = builder.loader;
        this.aspectX = builder.aspectX;
        this.aspectY = builder.aspectY;
        this.outputX = builder.outputX;
        this.outputY = builder.outputY;
    }

    public static class Builder implements Serializable {
        private boolean needCrop;
        private boolean multiSelect;
        private int maxNum;
        private boolean needCamera;
        private String title;
        private String filePath;
        private ImageLoader loader;

        private int aspectX = 1;
        private int aspectY = 1;
        private int outputX = 400;
        private int outputY = 400;

        public Builder(ImageLoader loader) {
            this.loader = loader;

            title = "图片";
            needCamera = true;
            multiSelect = false;
            maxNum = 9;

        }

        public Builder needCrop(boolean needCrop) {
            this.needCrop = needCrop;
            return this;
        }

        public Builder multiSelect(boolean multiSelect) {
            this.multiSelect = multiSelect;
            return this;
        }

        public Builder maxNum(int maxNum) {
            this.maxNum = maxNum;
            return this;
        }

        public Builder needCamera(boolean needCamera) {
            this.needCamera = needCamera;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        private Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder cropSize(int aspectX, int aspectY, int outputX, int outputY) {
            this.aspectX = aspectX;
            this.aspectY = aspectY;
            this.outputX = outputX;
            this.outputY = outputY;
            return this;
        }

        public ImgSelConfig build() {
            return new ImgSelConfig(this);
        }


    }
}
