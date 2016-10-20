package com.ws.image.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.ws.image.common.Constant;
import com.ws.image.entity.Folder;
import com.ws.image.entity.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: 30453
 * Date: 2016/10/19 15:49
 */
public class CursorImageLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private Context context;
    private List<Image> imageList;
    private List<Folder> folderList;
    private boolean hasFolderGened;
    private Callback callback;

    private final String[] IMAGE_PROJECTION = {MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID};

    public CursorImageLoader(Context context, Callback callback) {
        this.context = context;
        this.callback = callback;
        imageList = new ArrayList<>();
        folderList = new ArrayList<>();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        if (id == Constant.LOADER_ALL) {
            cursorLoader = new CursorLoader(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    null, null, IMAGE_PROJECTION[2] + " DESC");
        } else if (id == Constant.LOADER_CATEGORY) {
            cursorLoader = new CursorLoader(context,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[2] + " DESC");
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) {
            return;
        }
        int count = data.getCount();
        if (count <= 0) {
            return;
        }
        List<Image> tempList = new ArrayList<>();
        data.moveToFirst();
        do {
            String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
            String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
            long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
            Image image = new Image(path, name, dateTime);
            if (!image.path.endsWith(".gif")) {
                tempList.add(image);
            }
            if (!hasFolderGened) {
                File imageFile = new File(path);
                File folderFile = imageFile.getParentFile();
                Folder folder = new Folder();
                folder.name = folderFile.getName();
                folder.path = folderFile.getAbsolutePath();
                folder.cover = image;
                if (!folderList.contains(folder)) {
                    List<Image> imageList = new ArrayList<>();
                    imageList.add(image);
                    folder.images = imageList;
                    folderList.add(folder);
                } else {
                    Folder f = folderList.get(folderList.indexOf(folder));
                    f.images.add(image);
                }
            }
        } while (data.moveToNext());
        imageList.addAll(tempList);
        hasFolderGened = true;
        callback.onLoadFinished(imageList, folderList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public interface Callback {
        void onLoadFinished(List<Image> images, List<Folder> folders);
    }
}
