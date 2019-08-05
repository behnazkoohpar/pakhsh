
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-dontwarn com.google.errorprone.annotations.*

-keep public class com.koohpar.dstrbt.data.model.** { *;}
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.* { *; }