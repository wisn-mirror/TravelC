package debug;


import com.travel.library.commons.common.CommonApplication;
import com.travel.library.debug.TokenDebug;

/**
 * Created by Wisn on 2018/5/5 下午9:16.
 */

public class ModuleApplication extends CommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        TokenDebug.getToken();

    }

}
