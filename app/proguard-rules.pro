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

#---------------------------------基本指令区----------------------------------
-ignorewarnings
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志
-printmapping proguardMapping.txt
-optimizationpasses 5   # 指定代码的压缩级别
-dontskipnonpubliclibraryclassmembers


-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature # 避免混淆泛型
-keepattributes EnclosingMethod
-keepattributes SourceFile,LineNumberTable #运行抛出异常时保留代码行号
-keepattributes Exceptions # 解决AGPBI警告

#继承自activity,application,service,broadcastReceiver,contentprovider....不进行混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.support.multidex.MultiDexApplication
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep class android.support.** {*;}


 # support-v4
 -dontwarn android.support.v4.**
 -keep class android.support.v4.app.** { *; }
 -keep interface android.support.v4.app.** { *; }
 -keep class android.support.v4.** { *; }


 # support-v7
 -dontwarn android.support.v7.**
 -keep class android.support.v7.internal.** { *; }
 -keep interface android.support.v7.internal.** { *; }
 -keep class android.support.v7.** { *; }

 # support design
 -dontwarn android.support.design.**
 -keep class android.support.design.** { *; }
 -keep interface android.support.design.** { *; }
 -keep public class android.support.design.R$* { *; }

# 所有View的子类及其子类的get、set方法都不进行混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}


# 枚举类不能被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# natvie 方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


#不混淆Serializable接口的子类中指定的某些成员变量和方法
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}


-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}


# 不混淆R类里及其所有内部static类中的所有static变量字段，$是用来分割内嵌类与其母体的标志
-keep public class **.R$*{
   public static final int *;
}



#---------------------------------第三方库及jar包-------------------------------

#Glide不能被混淆
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

 # ARouter
 -keep public class com.alibaba.android.arouter.routes.**{*;}
 -keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
 # 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
 -keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
 # 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
 -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

#activityrouter
#-keep class com.github.mzule.activityrouter.router.** { *; }
#multistateview
#-keep class com.kennyc.view.** { *; }
#-dontwarn com.kennyc.view.*
# banner 的混淆代码
#-keep class com.youth.banner.** {
#    *;
# }
#recyclerview-animators
#-keep class jp.wasabeef.** {*;}
#-dontwarn jp.wasabeef.*
