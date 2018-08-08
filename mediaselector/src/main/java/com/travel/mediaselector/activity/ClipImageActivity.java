package com.travel.mediaselector.activity;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.travel.library.utils.ImageUtils;
import com.travel.library.view.widget.ClipImageView;
import com.travel.mediaselector.ImageSelector;
import com.travel.library.commons.bean.mediaselector.MediaInfo;
import com.travel.mediaselector.R;

import java.io.File;
import java.util.ArrayList;

public class ClipImageActivity extends Activity {

    private FrameLayout btnConfirm;
    private FrameLayout btnBack;
    private ClipImageView imageView;
    private int mRequestCode;

    public static void openActivity(Activity context, int requestCode, boolean useCamera, ArrayList<MediaInfo> selected) {
        Intent intent = new Intent(context, ClipImageActivity.class);
        intent.putExtra("requestCode", requestCode);
        intent.putExtra(ImageSelector.USE_CAMERA, useCamera);
        intent.putParcelableArrayListExtra(ImageSelector.SELECTED, selected);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.photoselect_activity_clip_image);

        Intent intent = getIntent();
        mRequestCode = intent.getIntExtra("requestCode", 0);

        setStatusBarColor();
        MediaInfoSelectorActivity.openActivity(this, mRequestCode, true,
                intent.getBooleanExtra(ImageSelector.USE_CAMERA, true), 0,
                intent.<MediaInfo>getParcelableArrayListExtra(ImageSelector.SELECTED), intent.getBooleanExtra(ImageSelector.ContainsVideo, false),intent.getBooleanExtra(ImageSelector.isSingleVideo, false));
        initView();
    }

    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#373c3d"));
        }
    }

    private void initView() {
        imageView = (ClipImageView) findViewById(R.id.process_img);
        btnConfirm = (FrameLayout) findViewById(R.id.btn_confirm);
        btnBack = (FrameLayout) findViewById(R.id.btn_back);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable() != null) {
                    btnConfirm.setEnabled(false);
                    confirm(imageView.clipImage());
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && requestCode == mRequestCode) {
            ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Bitmap bitmap = ImageUtils.decodeSampledBitmapFromFile(images.get(0), 720, 1080);
            if (bitmap != null) {
                imageView.setBitmapData(bitmap);
            } else {
                finish();
            }
        } else {
            finish();
        }
    }

    private void confirm(Bitmap bitmap) {
        String imagePath = null;
        if (bitmap != null) {
            imagePath = ImageUtils.saveImage(bitmap, getCacheDir().getPath() + File.separator + "image_select");
            bitmap.recycle();
            bitmap = null;
        }

        if (!TextUtils.isEmpty(imagePath)) {
            ArrayList<String> selectImages = new ArrayList<>();
            selectImages.add(imagePath);
            Intent intent = new Intent();
            intent.putStringArrayListExtra(ImageSelector.SELECT_RESULT, selectImages);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

}
