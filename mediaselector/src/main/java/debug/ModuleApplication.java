package debug;


import com.travel.library.commons.common.CommonApplication;
import com.travel.library.debug.TokenDebug;

/**
  * Create by jinxuefen on 2018/7/29
  * 功能描述：
  */
public class ModuleApplication extends CommonApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        TokenDebug.getToken();

    }

}
