[app]
title = Mirror Scorpion
package.name = mirror_scorpion
package.domain = org.tetocollctionway
source.dir = .
source.include_exts = py,png,jpg,kv,atlas,db,ttf,json,wav,mp3,mp4
version = 1.0

# المكتبات المطلوبة للذكاء الاصطناعي والوسائط المتعددة
requirements = python3,     kivy==2.3.0,     kivymd==1.2.0,     pillow,     requests,     urllib3,     certifi,     chardet,     idna,     openai,     google-cloud-translate,     google-cloud-speech,     google-cloud-texttospeech,     plyer,     ffpyplayer

orientation = portrait
fullscreen = 0
android.archs = arm64-v8a, armeabi-v7a

# الصلاحيات الكاملة (كاميرا للعدسة، مايك للترجمة، تخزين للمستندات)
android.permissions = INTERNET, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, RECORD_AUDIO, CAMERA, ACCESS_NETWORK_STATE

android.api = 33
android.minapi = 21
android.sdk = 33
android.ndk = 25b

# دعم جافا والتبعيات الخاصة بالواجهة والعدسة
android.gradle_dependencies = 'androidx.appcompat:appcompat:1.6.1', 'com.google.android.gms:play-services-ads:22.6.0'

# تفعيل ميزة الشفافية (مهمة لعلامة "ترجم بواسطة ميرور" المائلة)
android.enable_androidx = True

[buildozer]
log_level = 2
warn_on_root = 1
