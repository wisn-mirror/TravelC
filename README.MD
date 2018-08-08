## 快速初始化一个项目

1. 组件化模式
2. 网络请求模块封装，x5内核集成
3. 下拉刷新，上拉加载模块
4. 手势返回
5. utils工具类
6. 打包对齐加固多渠道脚本
7. 证书信息安全本地化配置



### 组件化模式的问题解决方式：

#### 1 组件模式和集成模式的转换

```java
if (isModule.toBoolean()) {
    apply plugin: 'com.android.application'
} else {
    apply plugin: 'com.android.library'
}
```

#### 2 组件之间AndroidManifest合并问题

```Java
 sourceSets {
        main {
            if (isModule.toBoolean()) {
                manifest.srcFile 'src/main/module/AndroidManifest.xml'
            } else {
                manifest.srcFile 'src/main/AndroidManifest.xml'
                java {
                    exclude 'debug/**'
                }
            }
        }
    }
```



#### 3 全局Context的获取及组件数据初始化

​	module中初始化Application

#### 4 library依赖问题

​	统一依赖相同的版本

```groovy

ext {
    supportLibrary = "27.0.2"

    supportDependencies = [
            supportv4          : "com.android.support:support-v4:${supportLibrary}",
            appCompat          : "com.android.support:appcompat-v7:${supportLibrary}",
            recyclerview       : "com.android.support:recyclerview-v7:${supportLibrary}",
            design             : "com.android.support:design:${supportLibrary}",
            annotations        : "com.android.support:support-annotations:${supportLibrary}",
            constraint         : "com.android.support.constraint:constraint-layout:1.0.2",
            multidex            : "com.android.support:multidex:1.0.1",
            test               : "junit:junit:4.12",
            androidTestrunner  : "com.android.support.test:runner:1.0.1",
            androidTestespresso: "com.android.support.test.espresso:espresso-core:3.0.1",
    ]

    thirdDependencies = [
            rxjava              : "io.reactivex.rxjava2:rxjava:2.1.10",
            rxandroid           : "io.reactivex.rxjava2:rxandroid:2.0.2",
            rxbinding           : "com.jakewharton.rxbinding2:rxbinding:2.0.0",
            okhttp              : "com.squareup.okhttp3:okhttp:3.10.0",
            retrofit            : "com.squareup.retrofit2:retrofit:2.4.0",
            gsonConverter       : "com.squareup.retrofit2:converter-gson:2.4.0",
            rxjavaAdapter       : "com.squareup.retrofit2:adapter-rxjava2:2.4.0",
            loggingInterceptor  : "com.squareup.okhttp3:logging-interceptor:3.10.0",
            cardview            : "com.android.support:cardview-v7:26.1.0",
            glide               : "com.github.bumptech.glide:glide:4.1.1",
            glide_compiler      : "com.github.bumptech.glide:compiler:4.1.1",
            arouter               : "com.alibaba:arouter-api:1.2.4",
            arouter_compiler      : "com.alibaba:arouter-compiler:1.2.4",
            butterknife         : "com.jakewharton:butterknife:8.8.1",
            butterknife_compiler: "com.jakewharton:butterknife-compiler:8.8.1",
            CymChad             : "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.36",
            zxing               : "com.google.zxing:core:3.3.2",
            bugly               : "com.tencent.bugly:crashreport:latest.release",
            walle               : "com.meituan.android.walle:library:1.1.3",
            bugly               : "com.tencent.bugly:crashreport:latest.release",
            gson               : "com.google.code.gson:gson:2.8.1",
    ]

    app = [
            compileSdkVersion: 27,
            buildToolsVersion: "27.0.2",
            minSdkVersion    : 19,
            targetSdkVersion : 23,
            versionCode      : 1,
            versionName      : "1.0",
            applicationId    : "com.laiyifen"
    ]
}
```



#### 5 组件之间调用和通信

​    使用ARoute或者activityrouter

#### 6 组件之间资源名冲突

​	在项目中约定资源文件命名规约，比如强制使每个资源文件的名称以组件名开始，这个可以根据实际情况和开发人员制定规则

```groovy
    //设置了resourcePrefix值后，所有的资源名必须以指定的字符串做前缀，否则会报错。
    //但是resourcePrefix这个值只能限定xml里面的资源，并不能限定图片资源，所有图片资源仍然需要手动去修改资源名。
    resourcePrefix "module_"
```

#### 7单独组件涉及登录认证问题

```java

public class Module1Application extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        login();
    }

    public void login() {
        Net.<String>get("https://api.douban.com/v2/movie/top250?start=0&count=10")
                .execute( new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("test",response+response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }
}
```

