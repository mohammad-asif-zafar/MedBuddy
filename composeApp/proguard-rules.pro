# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/asifzafar/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add प्रोजेक्ट specific ProGuard rules here.

# Keep Firebase classes
-keep class com.google.firebase.** { *; }

# Keep Compose internal classes that might be accessed via reflection
-keep class androidx.compose.** { *; }

# Keep your model classes if you use them with Firestore or serialization
-keep class com.hathway.medbuddy.domain.model.** { *; }
-keep class com.hathway.medbuddy.CurrentUser { *; }

# Ktor rules
-keep class io.ktor.** { *; }
