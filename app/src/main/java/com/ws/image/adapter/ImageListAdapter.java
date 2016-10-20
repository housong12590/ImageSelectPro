package com.ws.image.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ws.image.ImageLoader;
import com.ws.image.R;
import com.ws.image.entity.Image;

import java.util.List;

/**
 * Author: 30453
 * Date: 2016/10/19 17:07
 */
public class ImageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Image> list;
    private ImageLoader imageLoader;
    private Context context;
    private static final int ITEM_CAMERA = 0;
    private static final int ITEM_NORMAL = 1;

    public ImageListAdapter(Context context, List<Image> list, ImageLoader imageLoader) {
        this.list = list;
        this.context = context;
        this.imageLoader = imageLoader;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_NORMAL:
                return new ImageItemHolder(getLayoutView(context, R.layout.item_image_layout));
            case ITEM_CAMERA:
                return new CameraItemHolder(getLayoutView(context, R.layout.item_camera_layout));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Image image = list.get(position);
        switch (viewHolder.getItemViewType()) {
            case ITEM_CAMERA:

                break;
            case ITEM_NORMAL:
                ImageItemHolder imageHolder = (ImageItemHolder) viewHolder;
                imageLoader.displayImage(context, image.path, imageHolder.image);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isCamera ? ITEM_CAMERA : ITEM_NORMAL;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private View getLayoutView(Context context, int layoutId) {
        return LayoutInflater.from(context).inflate(layoutId, null, false);
    }

    class ImageItemHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ImageItemHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    class CameraItemHolder extends RecyclerView.ViewHolder {

        public CameraItemHolder(View itemView) {
            super(itemView);
        }
    }
}
