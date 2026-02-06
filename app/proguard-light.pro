############################################
# PROGUARD / R8 - LIGHT OBFUSCATION (SAMPLE)
############################################

# Keep useful info for readable stack traces
-keepattributes SourceFile,LineNumberTable

# Keep runtime annotations (commonly needed for DI/JSON)
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# Keep parameter names (sometimes needed for reflection / JSON libs)
-keepparameternames


# If you use JavaScript interfaces in WebView
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# If you use Android Views referenced by name (rare nowadays, but safe)
-keepclassmembers class **.R$* {
    public static <fields>;
}

# Keep your model/data classes names more stable (helps debugging / JSON reflection).
# Adjust package to your real one.
-keep class com.yourapp.model.** { *; }

# Keep Activities/Services/Receivers/Providers (usually not necessary because manifest keeps them,
# but it can reduce surprises in "light" builds)
-keep class * extends android.app.Activity
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider

# Keep custom Application (adjust package/class)
-keep class com.yourapp.MyApplication { *; }

# If you use reflection by string name anywhere, keep those targets explicitly (example placeholder)
# -keep class com.yourapp.somepackage.SomeReflectedClass { *; }

# Let R8 still do shrinking/obfuscation, but this file is more "keep-happy"
# (No -dontobfuscate here; still obfuscates)

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
