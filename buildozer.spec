[app]
title = Mirror
package.name = mirror
package.domain = org.adham
source.dir = .
source.include_exts = py,png,jpg,kv,atlas
version = 0.1
requirements = python3,kivy==2.3.0,kivymd==1.2.0
orientation = portrait
android.permissions = INTERNET, CAMERA
android.api = 33
android.archs = arm64-v8a
p4a.branch = master
[buildozer]
log_level = 2
