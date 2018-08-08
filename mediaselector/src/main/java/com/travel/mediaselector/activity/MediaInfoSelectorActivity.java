package com.travel.mediaselector.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.utils.DateUtils;
import com.travel.mediaselector.ImageSelector;
import com.travel.mediaselector.R;
import com.travel.mediaselector.adapter.FolderAdapter;
import com.travel.mediaselector.adapter.MediaInfoAdapter;
import com.travel.mediaselector.entry.Folder;
import com.travel.library.commons.bean.mediaselector.MediaInfo;
import com.travel.mediaselector.model.MediaInfoModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Route(path = ARoutePath.mediaselector.MediaInfoSelectorActivity)
public class MediaInfoSelectorActivity extends AppCompatActivity {

    private static final String TAG = "MediaInfoSelectorActivity";
    private TextView tvTime;
    private TextView tvFolderName;
    private TextView tvConfirm;
    private TextView tvPreview;
    private FrameLayout btnConfirm;
    private FrameLayout btnPreview;
    private RecyclerView rvImage;
    private RecyclerView rvFolder;
    private View masking;

    private MediaInfoAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private ArrayList<Folder> mFolders;
    private Folder mFolder;
    private boolean applyLoadImage = false;
    private static final int PERMISSION_WRITE_EXTERNAL_REQUEST_CODE = 0x00000011;
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;

    private static final int CAMERA_REQUEST_CODE = 0x00000010;

    private boolean isOpenFolder;
    private boolean isShowTime;
    private boolean isInitFolder;
    private boolean isSingle;
    private boolean isContainsVideo;
    private boolean isSingleVideo;
    private int mMaxCount;

    private boolean useCamera = true;
    private String mPhotoPath;

    private Handler mHideHandler = new Handler();
    private Runnable mHide = new Runnable() {
        @Override
        public void run() {
            hideTime();
        }
    };

    //用于接收从外面传进来的已选择的图片列表。当用户原来已经有选择过图片，现在重新打开选择器，允许用
    // 户把先前选过的图片传进来，并把这些图片默认为选中状态。
    private ArrayList<MediaInfo> mSelectedImages;
    private TextView tv_size;
    private ImageView iv_arrow;

    /**
     * 启动图片选择器
     *
     * @param activity
     * @param requestCode
     * @param isSingle
     * @param useCamera
     * @param maxSelectCount
     * @param selected
     */
    /**
     * @param activity
     * @param requestCode
     * @param isSingle        是否单选
     * @param useCamera       是否使用拍照功能
     * @param maxSelectCount  图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
     * @param selected        接收从外面传进来的已选择的图片列表。当用户原来已经有选择过图片，现在重新打开
     *                        选择器，允许用户把先前选过的图片传进来，并把这些图片默认为选中状态。
     * @param isContainsVideo 是否包含视频
     * @param isSingleVideo   是否选择视频后就只能选一个
     */
    public static void openActivity(Activity activity, int requestCode,
                                    boolean isSingle, boolean useCamera,
                                    int maxSelectCount, ArrayList<MediaInfo> selected, boolean isContainsVideo, boolean isSingleVideo) {
        Intent intent = new Intent(activity, MediaInfoSelectorActivity.class);
        intent.putExtra(ImageSelector.MAX_SELECT_COUNT, maxSelectCount);
        intent.putExtra(ImageSelector.IS_SINGLE, isSingle);
        intent.putExtra(ImageSelector.USE_CAMERA, useCamera);
        intent.putExtra(ImageSelector.ContainsVideo, isContainsVideo);
        intent.putExtra(ImageSelector.isSingleVideo, isSingleVideo);
        intent.putParcelableArrayListExtra(ImageSelector.SELECTED, selected);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoselect_activity_mediainfo_select);
        Intent intent = getIntent();
        mMaxCount = intent.getIntExtra(ImageSelector.MAX_SELECT_COUNT, 0);
        isSingle = intent.getBooleanExtra(ImageSelector.IS_SINGLE, false);
        useCamera = intent.getBooleanExtra(ImageSelector.USE_CAMERA, true);
        isContainsVideo = intent.getBooleanExtra(ImageSelector.ContainsVideo, false);
        isSingleVideo = intent.getBooleanExtra(ImageSelector.isSingleVideo, false);
        mSelectedImages = intent.getParcelableArrayListExtra(ImageSelector.SELECTED);
        setStatusBarColor();
        initView();
        initListener();
        initImageList();
        checkPermissionAndLoadImages();
        hideFolderList();
        setSelectImageCount(mAdapter.getSelectImages());
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
        iv_arrow = (ImageView) findViewById(R.id.iv_arrow);
        rvImage = (RecyclerView) findViewById(R.id.rv_image);
        rvFolder = (RecyclerView) findViewById(R.id.rv_folder);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        tvPreview = (TextView) findViewById(R.id.tv_preview);
        btnConfirm = (FrameLayout) findViewById(R.id.btn_confirm);
        btnPreview = (FrameLayout) findViewById(R.id.btn_preview);
        tvFolderName = (TextView) findViewById(R.id.tv_folder_name);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tv_size = (TextView) findViewById(R.id.tv_size);
        masking = findViewById(R.id.masking);
    }

    private void initListener() {
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MediaInfo> images = new ArrayList<>();
                images.addAll(mAdapter.getSelectImages());
                toPreviewActivity(images, 0);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        findViewById(R.id.btn_folder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInitFolder) {
                    if (isOpenFolder) {
                        closeFolder();
                    } else {
                        openFolder();
                    }
                }
            }
        });

        masking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFolder();
            }
        });

        rvImage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                changeTime();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                changeTime();
            }
        });
    }

    /**
     * 初始化图片列表
     */
    private void initImageList() {
        // 判断屏幕方向
        Configuration configuration = getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new GridLayoutManager(this, 4);
        } else {
            mLayoutManager = new GridLayoutManager(this, 6);
        }

        rvImage.setLayoutManager(mLayoutManager);
        mAdapter = new MediaInfoAdapter(this, mMaxCount, isSingle, isSingleVideo);
        rvImage.setAdapter(mAdapter);
        if (mSelectedImages != null && mAdapter != null) {
            mAdapter.setSelectedImages(mSelectedImages);
            mSelectedImages = null;
        }
        ((SimpleItemAnimator) rvImage.getItemAnimator()).setSupportsChangeAnimations(false);
        if (mFolders != null && !mFolders.isEmpty()) {
            setFolder(mFolders.get(0));
        }
        mAdapter.setOnImageSelectListener(new MediaInfoAdapter.OnImageSelectListener() {
            @Override
            public void OnImageSelect(List<MediaInfo> selectImage, boolean isSelect) {
                setSelectImageCount(selectImage);
            }
        });
        mAdapter.setOnItemClickListener(new MediaInfoAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(MediaInfo image, int position) {
                toPreviewActivity(mAdapter.getData(), position);
            }

            @Override
            public void OnCameraClick() {
                checkPermissionAndCamera();
            }
        });
    }

    /**
     * 初始化图片文件夹列表
     */
    private void initFolderList() {
        if (mFolders != null && !mFolders.isEmpty()) {
            isInitFolder = true;
            rvFolder.setLayoutManager(new LinearLayoutManager(MediaInfoSelectorActivity.this));
            FolderAdapter adapter = new FolderAdapter(MediaInfoSelectorActivity.this, mFolders);
            adapter.setOnFolderSelectListener(new FolderAdapter.OnFolderSelectListener() {
                @Override
                public void OnFolderSelect(Folder folder) {
                    setFolder(folder);
                    closeFolder();
                }
            });
            rvFolder.setAdapter(adapter);
        }
    }

    /**
     * 刚开始的时候文件夹列表默认是隐藏的
     */
    private void hideFolderList() {
        rvFolder.post(new Runnable() {
            @Override
            public void run() {
                rvFolder.setTranslationY(rvFolder.getHeight());
                rvFolder.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 设置选中的文件夹，同时刷新图片列表
     *
     * @param folder
     */
    private void setFolder(Folder folder) {
        if (folder != null && mAdapter != null && !folder.equals(mFolder)) {
            mFolder = folder;
            tvFolderName.setText(folder.getName());
            rvImage.scrollToPosition(0);
            mAdapter.refresh(folder.getImages(), folder.isUseCamera());
        }
    }

    private void setSelectImageCount(List<MediaInfo> selectImageList) {
        int count = selectImageList == null ? 0 : selectImageList.size();
        if (count == 0) {
//            btnConfirm.setEnabled(false);
//            btnPreview.setEnabled(false);
            tvConfirm.setText("确定");
            tvPreview.setText("预览");
        } else {
//            btnConfirm.setEnabled(true);
//            btnPreview.setEnabled(true);
            tvPreview.setText("预览(" + count + ")");
            if (isSingle) {
                tvConfirm.setText("确定");
            } else if (mMaxCount > 0) {
                tvConfirm.setText("确定(" + count + "/" + mMaxCount + ")");
            } else {
                tvConfirm.setText("确定(" + count + ")");
            }
        }
        long countSize = 0;
        for (MediaInfo mediaInfo : selectImageList) {
            countSize = countSize + mediaInfo.size;
        }
        tv_size.setText("原图(" + DateUtils.getNetFileSizeDescription(countSize) + ")");

    }

    /**
     * 弹出文件夹列表
     */
    private void openFolder() {
        if (!isOpenFolder) {
            iv_arrow.setImageResource(R.drawable.photoselect_ic_lyf_rrowup);
            masking.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(rvFolder, "translationY",
                    -rvFolder.getHeight(), 30, -30, 10, -10, 0).setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    rvFolder.setVisibility(View.VISIBLE);
                }
            });
            animator.start();
            isOpenFolder = true;
        }
    }

    /**
     * 收起文件夹列表
     */
    private void closeFolder() {
        if (isOpenFolder) {
            iv_arrow.setImageResource(R.drawable.photoselect_ic_lyf_arrowdown);
            masking.setVisibility(View.GONE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(rvFolder, "translationY",
                    0, 30, -30, -rvFolder.getHeight()).setDuration(300);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    rvFolder.setVisibility(View.GONE);
                }
            });
            animator.start();
            isOpenFolder = false;
        }
    }

    /**
     * 隐藏时间条
     */
    private void hideTime() {
        if (isShowTime) {
            ObjectAnimator.ofFloat(tvTime, "alpha", 1, 0).setDuration(300).start();
            isShowTime = false;
        }
    }

    /**
     * 显示时间条
     */
    private void showTime() {
        if (!isShowTime) {
            ObjectAnimator.ofFloat(tvTime, "alpha", 0, 1).setDuration(300).start();
            isShowTime = true;
        }
    }

    /**
     * 改变时间条显示的时间（显示图片列表中的第一个可见图片的时间）
     */
    private void changeTime() {
        int firstVisibleItem = getFirstVisibleItem();
        MediaInfo image = mAdapter.getFirstVisibleImage(firstVisibleItem);
        if (image != null) {
            String time = DateUtils.getImageTime(image.getTime() * 1000);
            tvTime.setText(time);
            showTime();
            mHideHandler.removeCallbacks(mHide);
            mHideHandler.postDelayed(mHide, 1500);
        }
    }

    private int getFirstVisibleItem() {
        return mLayoutManager.findFirstVisibleItemPosition();
    }

    private void confirm() {
        if (mAdapter == null) {
            return;
        }
        //因为图片的实体类是Image，而我们返回的是String数组，所以要进行转换。
        ArrayList<MediaInfo> selectImages = mAdapter.getSelectImages();
        if (selectImages == null || selectImages.size() == 0) {
            Toast.makeText(this, "请选择", Toast.LENGTH_SHORT).show();
            return;
        }
        //点击确定，把选中的图片通过Intent传给上一个Activity。
        setResult(selectImages);
        finish();
    }

    private void setResult(ArrayList<MediaInfo> images) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(ImageSelector.SELECT_RESULT, images);
        setResult(RESULT_OK, intent);
    }

    private void toPreviewActivity(ArrayList<MediaInfo> images, int position) {
        if (images != null && !images.isEmpty()) {
            PreviewActivity.openActivity(this, images,
                    mAdapter.getSelectImages(), isSingle, mMaxCount, position, isSingleVideo);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (applyLoadImage) {
            applyLoadImage = false;
            checkPermissionAndLoadImages();
        }
    }

    /**
     * 处理图片预览页返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.RESULT_CODE) {
            if (data != null && data.getBooleanExtra(ImageSelector.IS_CONFIRM, false)) {
                //如果用户在预览页点击了确定，就直接把用户选中的图片返回给用户。
                confirm();
            } else {
                //否则，就刷新当前页面。
                mAdapter.notifyDataSetChanged();
                setSelectImageCount(mAdapter.getSelectImages());
            }
        } else if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ArrayList<MediaInfo> selectImages = new ArrayList<>();
                selectImages.add(new MediaInfo(mPhotoPath, 0, null, null, 0));
                setResult(selectImages);
                finish();
            }
        }
    }

    /**
     * 横竖屏切换处理
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mLayoutManager != null && mAdapter != null) {
            //切换为竖屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                mLayoutManager.setSpanCount(3);
            }
            //切换为横屏
            else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mLayoutManager.setSpanCount(5);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 检查权限并加载SD卡里的图片。
     */
    private void checkPermissionAndLoadImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            Toast.makeText(this, "没有图片", Toast.LENGTH_LONG).show();
            return;
        }
        int hasWriteExternalPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteExternalPermission == PackageManager.PERMISSION_GRANTED) {
            //有权限，加载图片。
            loadImageForSDCard();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(MediaInfoSelectorActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_WRITE_EXTERNAL_REQUEST_CODE);
        }
    }

    /**
     * 检查权限并拍照。
     */
    private void checkPermissionAndCamera() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openCamera();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(MediaInfoSelectorActivity.this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 处理权限申请的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_WRITE_EXTERNAL_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，加载图片。
                loadImageForSDCard();
            } else {
                //拒绝权限，弹出提示框。
                showExceptionDialog(true);
            }
        } else if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
                openCamera();
            } else {
                //拒绝权限，弹出提示框。
                showExceptionDialog(false);
            }
        }
    }

    /**
     * 发生没有权限等异常时，显示一个提示dialog.
     */
    private void showExceptionDialog(final boolean applyLoad) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("提示")
                .setMessage("该相册需要赋予访问存储和拍照的权限，请到“设置”>“应用”>“权限”中配置权限。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        finish();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                startAppSettings();
                if (applyLoad) {
                    applyLoadImage = true;
                }
            }
        }).show();
    }

    /**
     * 从SDCard加载图片。
     */
    private void loadImageForSDCard() {
        MediaInfoModel.loadImageForSDCard(this, isContainsVideo, new MediaInfoModel.DataCallback() {
            @Override
            public void onSuccess(ArrayList<Folder> folders) {
                mFolders = folders;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mFolders != null && !mFolders.isEmpty()) {
                            initFolderList();
                            mFolders.get(0).setUseCamera(useCamera);
                            setFolder(mFolders.get(0));
                        }
                    }
                });
            }
        });
    }

    /**
     * 调起相机拍照
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                mPhotoPath = photoFile.getAbsolutePath();
                //通过FileProvider创建一个content类型的Uri
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageFileName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN && isOpenFolder) {
            closeFolder();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
