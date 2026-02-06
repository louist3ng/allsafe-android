############################################
# PROGUARD / R8 - HEAVIER OBFUSCATION (AGGRESSIVE)
############################################

# 1) Strip debug + metadata as much as possible
# Keep only what is commonly required at runtime.
-keepattributes *Annotation*
# Remove these if you can (they help reverse engineering):
# (Signature can be needed for some reflection use cases; if something breaks, re-add Signature.)
# -keepattributes Signature
# -keepattributes EnclosingMethod
# -keepattributes InnerClasses

# Remove line numbers + source file info (you already rename source file; this pushes further)
-renamesourcefileattribute a
# Do NOT keep SourceFile, LineNumberTable anywhere in this heavy config.
# (So: do NOT add them back in)

# 2) Obfuscation knobs
-overloadaggressively
-dontusemixedcaseclassnames
-useuniqueclassmembernames
-repackageclasses
-flattenpackagehierarchy
# If you want everything to collapse into a tiny package:
# -flattenpackagehierarchy 'a'

# 3) Shrinking/optimization aggressiveness
# R8 already optimizes; these push ProGuard-style aggressiveness
-optimizationpasses 5
-allowaccessmodification
-mergeinterfacesaggressively
# (If something breaks, comment out -mergeinterfacesaggressively first.)

# 4) Remove logging calls (common in "hardening" builds)
# You can add more logging frameworks you use.
-assumenosideeffects class android.util.Log {
  public static *** d(...);
  public static *** v(...);
  public static *** i(...);
  public static *** w(...);
  public static *** e(...);
}

# 5) Keep only what's required by Android component model.
# Usually manifest keeps are sufficient, but if you remove these, some projects break.
# If you want maximum obfuscation, you can REMOVE these and rely on manifest keep,
# but it's riskier. This is a middle ground:
-keep class * extends android.app.Application

# For Activities/Services/Receivers/Providers: better is to keep only those declared in manifest.
# If you can, do NOT keep all subclasses globally, because that preserves too much.
# (Remove these if you have many components and want them renamed; manifest will still keep entry points.)
# -keep class * extends android.app.Activity
# -keep class * extends android.app.Service
# -keep class * extends android.content.BroadcastReceiver
# -keep class * extends android.content.ContentProvider

# 6) WebView JS interface methods must be kept (runtime reflection)
-keepclassmembers class * {
  @android.webkit.JavascriptInterface <methods>;
}

# 7) Keep R fields (safe)
-keepclassmembers class **.R$* { public static <fields>; }

# 8) Enums (safe)
-keepclassmembers enum * {
  public static **[] values();
  public static ** valueOf(java.lang.String);
}

# 9) Optional: name obfuscation dictionaries (makes output less “patterned”)
# Provide your own dictionary files if you want deterministic ugly names.
# -obfuscationdictionary obfuscation-dict.txt
# -classobfuscationdictionary obfuscation-dict.txt
# -packageobfuscationdictionary obfuscation-dict.txt

# 10) OkHttp optional TLS providers (suppress missing-type warnings)
-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
