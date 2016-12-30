# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/thekarlo95/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepattributes EnclosingMethod
-keepattributes InnerClasses
-dontwarn org.apache.commons.digester.**
-dontwarn org.apache.commons.beanutils.**
-dontwarn okio.Okio
-dontwarn okio.DeflaterSink
-keep class com.raizlabs.android.dbflow.config.GeneratedDatabaseHolder
