package com.ws.image;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ws.image.adapter.ImageListAdapter;
import com.ws.image.common.Constant;
import com.ws.image.data.CursorImageLoader;
import com.ws.image.entity.Folder;
import com.ws.image.entity.Image;
import com.ws.image.widget.DividerGridItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ImgSelActivity extends AppCompatActivity {

    private static final int STORAGE_REQUEST_CODE = 100;
    public static final String RESULT = "result";

    private boolean hasFolderGened = false;

    private Toolbar toolbar;
    private TextView tvTitle;
    private TextView tvConfirm;
    private RecyclerView rvImageList;

    private ArrayList<String> resultList = new ArrayList<>();
    private List<Image> imageList = new ArrayList<>();
    private List<Folder> folderList = new ArrayList<>();


    private static ImgSelConfig config = new ImgSelConfig.Builder((act, path, iv) ->
            Glide.with(act).load(path).into(iv)).multiSelect(true).build();
    private ImageListAdapter imageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selector);
        findViews();
        initView();
        initData();
    }

    public static void start(Activity activity, ImgSelConfig config, int requestCode) {
        ImgSelActivity.config = config;
        activity.startActivityForResult(new Intent(activity, ImgSelActivity.class), requestCode);
    }

    public static void start(Activity activity, ImageLoader loader, int requestCode) {
        ImgSelActivity.config = new ImgSelConfig.Builder(loader).build();
        activity.startActivityForResult(new Intent(activity, ImgSelActivity.class), requestCode);
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        rvImageList = (RecyclerView) findViewById(R.id.recycleView);

    }

    private void initView() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
        tvTitle.setText(config.title);
        tvConfirm.setText(config.multiSelect ? "完成(0/" + config.maxNum + ")" : "");
        tvConfirm.setOnClickListener((view) -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(RESULT, resultList);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        });
        rvImageList.setLayoutManager(new GridLayoutManager(this, 3));
        rvImageList.addItemDecoration(new DividerGridItemDecoration(this));
        imageListAdapter = new ImageListAdapter(this, imageList, config.loader);
        rvImageList.setAdapter(imageListAdapter);
        if (config.needCamera) {
            imageList.add(new Image());
        }
    }

    private void initData() {
        getSupportLoaderManager().initLoader(Constant.LOADER_ALL, null, new CursorImageLoader(this, (images, folders) -> {
            imageList.addAll(images);
            imageListAdapter.notifyDataSetChanged();
        }));
    }

}
