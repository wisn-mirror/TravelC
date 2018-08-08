package com.travel.library.router;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin.tian on 2017/4/18.
 * email 1076174020@qq.com
 */
public class LoginRouterManager {

    /**
     * 记录登录路由
     */
    private List<RouterObj> loginRouterObjList = new ArrayList<>();

    private LoginRouterManager(){}
    /**
     * 单例
     * @return
     */
    public static LoginRouterManager getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    /**
     * 注册
     * @param routerObj
     */
    public void register(RouterObj routerObj){
        loginRouterObjList.add(routerObj);
    }

    /**
     * 登录成功 接着执行路由
     */
    public void loginSuccess(){
        for(RouterObj routerObj : loginRouterObjList){
            if(routerObj.getPostcard()!=null && routerObj.getCallback()!=null){
                routerObj.getCallback().onContinue(routerObj.getPostcard());
            }
        }
        loginRouterObjList.clear();
    }

    /**
     * 登录失败 中断路由
     */
    public void loginFail(){
        for(RouterObj routerObj : loginRouterObjList){
            if(routerObj.getPostcard()!=null && routerObj.getCallback()!=null){
                routerObj.getCallback().onInterrupt(null);
            }
        }
        loginRouterObjList.clear();
    }

    /**
     * 清空登录相关路由
     */
    public void clearLoginRouter(){
        loginRouterObjList.clear();
    }

    /**
     * 单例 线程安全
     */
    private enum Singleton{
        INSTANCE;

        private LoginRouterManager singleton;

        Singleton(){
            singleton = new LoginRouterManager();
        }
        public LoginRouterManager getInstance(){
            return singleton;
        }
    }

}
