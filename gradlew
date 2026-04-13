#!/bin/sh
# تنزيل المحرك إذا لم يكن موجوداً
if [ ! -f "gradle/wrapper/gradle-wrapper.jar" ]; then
    mkdir -p gradle/wrapper
    curl -L https://github.com/gradle/gradle/raw/v8.2.2/gradle/wrapper/gradle-wrapper.jar -o gradle/wrapper/gradle-wrapper.jar
fi
# تشغيل المحرك
java -jar gradle/wrapper/gradle-wrapper.jar "$@"
