# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile



#---------------------------------webview------------------------------------
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}

#Glide不能被混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#高德相关混淆文件
#3D 地图
-keep class com.amap.api.** {*;}
-keep class com.autonavi.** {*;}
-keep class com.a.a.** {*;}
-keep class com.loc.** {*;}
-dontwarn com.amap.api.**
-dontwarn com.autonavi.**
-dontwarn com.a.a.**
-dontwarn com.loc.**

# gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.qiancheng.carsmangersystem.**{*;}

# zxing
-keep class com.google.zxing.** {*;}
-dontwarn com.google.zxing.**

## okhttp
-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.{*;}

#okhttp3.x
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

#-ButterKnife 7.0
 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
  @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
 @butterknife.* <methods>;
 }

 # Retrofit
 -keep class retrofit2.** { *; }
 -dontwarn retrofit2.**
 -keepattributes Signature
 -keepattributes Exceptions
 -dontwarn okio.**
 -dontwarn javax.annotation.**

 # RxJava RxAndroid
 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }
 -dontnote rx.internal.util.PlatformDependent


-keep class com.laiyifen.library.**{*;}

 # bugly
 -dontwarn com.tencent.bugly.**
 -keep public class com.tencent.bugly.**{*;}


 #X5 内核
 -dontwarn com.tencent.smtt.**
 -keep public class com.tencent.smtt.**{*;}



#CymChad/BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** {
*;
}
-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
     <init>(...);
}


# # ARouter
# -keep public class com.alibaba.android.arouter.routes.**{*;}
# -keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
# # 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
# -keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
# # 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider