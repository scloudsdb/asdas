# Orion Music ProGuard Rules

# Obfuscation for security
-repackageclasses 'o'
-allowaccessmodification
-optimizationpasses 5

# Strip all logging in release
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}

# Keep security classes
-keep class site.orionhub.music.security.** { *; }

# EncryptedSharedPreferences
-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Hilt
-keep class dagger.hilt.** { *; }

# Kotlin Serialization
-keepattributes *Annotation*
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

# Media3
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**

# Coil
-dontwarn coil.**

# Kotlin Coroutines
-keepclassmembers class kotlinx.coroutines.** { volatile <fields>; }

# Keep line numbers for crash reports
-keepattributes SourceFile,LineNumberTable
