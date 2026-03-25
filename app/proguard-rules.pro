# Keep ViewModel classes
-keep class androidx.lifecycle.ViewModel { *; }
-keepclasseswithmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# Keep lifecycle classes
-keep class androidx.lifecycle.** { *; }

# Keep app classes
-keep class com.animecalc.app.** { *; }

# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}
