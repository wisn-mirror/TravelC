package debug;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.travel.library.commons.constants.ARoutePath;
import com.travel.library.commons.constants.Constants;
import com.travel.library.commons.common.CommonActivity;
import com.common.zxing.R;
import com.travel.library.utils.ToastUtils;

/**
 * Created by Wisn on 2018/5/14 下午6:49.
 */
public class ModuleActivity extends CommonActivity {

    @Override
    public int bindLayout() {
        return R.layout.activitymodule_test;
    }

    @Override
    public void initView(Activity activity) {
    }

    @Override
    public void initData(Context context) {
    }

    public void startScanCode(View v) {
        ARouter.getInstance()
                .build(ARoutePath.zxing.CaptureActivity)
                .withBoolean(Constants.zxing.Result, true)
                .withTransition(0, 0)
                .navigation(this,Constants.zxing.QR_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.zxing.QR_CODE && resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra(Constants.zxing.Result);
            ToastUtils.show(result + "  ");
        }
    }
}
