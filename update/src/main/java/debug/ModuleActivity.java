package debug;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.travel.library.utils.LogUtilsLib;
import com.travel.update.R;
import com.travel.library.commons.common.CommonActivity;
/**
 * Created by Wisn on 2018/5/14 下午6:49.
 */
public class ModuleActivity extends CommonActivity {

    private String lastorderCode;

    @Override
    public int bindLayout() {
        return R.layout.activitymodule_db;
    }

    @Override
    public void initView(Activity activity) {
    }

    @Override
    public void initData(Context context) {
    }

    public void add(View v) {
        LogUtilsLib.d("add");
//        OrderDb instance = OrderDb.getInstance("orderDB", "ordertab");
//        instance.addOrder("434231432" + System.currentTimeMillis(), 2, 3,3,2,String.valueOf(System.currentTimeMillis()));
    }

    public void update(View v) {
        LogUtilsLib.d("update");
//        OrderDb instance = OrderDb.getInstance("orderDB", "ordertab");
//        instance.updateIsDeal(lastorderCode,1);

    }

    public void delete(View v) {
        LogUtilsLib.d("delete");
//        OrderDb instance = OrderDb.getInstance("orderDB", "ordertab");
//        instance.delete(lastorderCode);

    }

    public void select(View v) {
        LogUtilsLib.d("select");
//        OrderDb instance = OrderDb.getInstance("orderDB", "ordertab");
//        List<OrderDb.OrderSer> all = instance.getAll();
//        for (OrderDb.OrderSer orderSer : all) {
//            lastorderCode = orderSer.orderCode;
//            LogUtilsLib.d("" + orderSer);
//        }
//        LogUtilsLib.d("=====================");
//        OrderDb.OrderSer orderSer1 = instance.selectOrder(lastorderCode);
//        LogUtilsLib.d("" + orderSer1);
    }


}
