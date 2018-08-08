package com.travel.library.commons.constants;

/**
 * Created by Wisn on 2018/5/29 下午1:24.
 */
public class SPConstants {
    public static class App {
    }

    public static class homepager {
    }

    public static class update {
        public static final String needUpdateHintNum = "needUpdateHintNum";
        public static final String tipUpdateHintNum = "tipUpdateHintNum";
    }

    public static class account {
        public static final String Token = "token";
        //门店id
        public static final String id = "id";
        //商家id
        public static final String merchantId = "merchantId";
        //门店名称
        public static final String storeName = "storeName";
        //门店编码
        public static final String shopCode = "shopCode";
        //配送费用
        public static final String deliveryCost = "deliveryCost";
        //起送费用
        public static final String sendCost = "sendCost";
        //开始接单时间
        public static final String manualReceiveStart = "manualReceiveStart";
        //结束接单时间
        public static final String manualReceiveEnd = "manualReceiveEnd";
        //营业状态
        public static final String businessState = "businessState";
        //开始营业时间
        public static final String businessStartTime = "businessStartTime";
        //结束营业时间
        public static final String businessEndTime = "businessEndTime";
        //距离手动接单时间的秒数
        public static final String difftime = "difftime";

        public static class UserInfo {
            public static final String accountId = "accountId";
            public static final String mobile = "mobile";
            //用户头像
            public static final String imageUrl = "imageUrl";
            public static final String nickName = "nickName";
            public static final String sex = "sex";
        }

    }


}
