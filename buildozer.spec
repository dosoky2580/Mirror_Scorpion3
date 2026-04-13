[app]
title = Mirror
package.name = mirror
package.domain = org.adham
source.dir = .
source.include_exts = py,png,jpg,kv,atlas
version = 0.1
requirements = python3,kivy==2.3.0,kivymd==1.2.0,pillow,requests,urllib3,certifi,chardet,idna
orientation = portrait
osx.python_version = 3
osx.kivy_version = 1.9.1
fullscreen = 0
# إضافة صلاحيات الكاميرا والذاكرة والإنترنت
android.permissions = INTERNET, CAMERA, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, RECORD_AUDIO
android.api = 33
android.minapi = 21
android.sdk = 33
android.ndk = 25b
android.archs = arm64-v8a, armeabi-v7a
android.allow_backup = True
# لضمان استقرار الواجهة بالإنجليزية مؤقتاً
android.lang = en
p4a.branch = master

[buildozer]
log_level = 2
warn_on_root = 1
