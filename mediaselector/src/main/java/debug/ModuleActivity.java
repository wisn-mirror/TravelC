package debug;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.commons.bean.mediaselector.MediaInfo;
import com.travel.library.commons.common.CommonActivity;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.commons.constants.Constants;
import com.travel.library.utils.ToastUtils;
import com.travel.mediaselector.R;

import java.util.ArrayList;

/**
 * Created by Wisn on 2018/5/5 下午8:56.
 */

public class ModuleActivity extends CommonActivity {

    private Button selector;

    @Override
    public int bindLayout() {
        return R.layout.mudule_fragment;
    }


    @Override
    public void initView(Activity activity) {
        selector = activity.findViewById(R.id.selector);
        selector.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v == selector) {
            ARouter.getInstance().build(ARoutePath.mediaselector.MediaInfoSelectorActivity)
                    .withInt(Constants.mediaselector.MAX_SELECT_COUNT, 2)
                    .withBoolean(Constants.mediaselector.isSingleVideo, true)
                    .withBoolean(Constants.mediaselector.IS_SINGLE, false)
                    .withBoolean(Constants.mediaselector.USE_CAMERA, true)
                    .withBoolean(Constants.mediaselector.ContainsVideo, true)
                    .withParcelableArrayList(Constants.mediaselector.SELECTED, null)
                    .navigation(this, Constants.mediaselector.PHOTO_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.mediaselector.REQUEST_CODE && data != null) {
            ArrayList<MediaInfo> selectImages = data.getParcelableArrayListExtra(Constants.mediaselector.SELECT_RESULT);
            ToastUtils.show(""+selectImages.size());
        }
    }
}
