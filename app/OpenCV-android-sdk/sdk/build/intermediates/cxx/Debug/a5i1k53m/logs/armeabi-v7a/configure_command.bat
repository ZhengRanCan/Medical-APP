@echo off
"C:\\Users\\lenovo\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\lenovo\\AndroidStudioProjects\\MyApplication\\app\\OpenCV-android-sdk\\sdk\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=27" ^
  "-DANDROID_PLATFORM=android-27" ^
  "-DANDROID_ABI=armeabi-v7a" ^
  "-DCMAKE_ANDROID_ARCH_ABI=armeabi-v7a" ^
  "-DANDROID_NDK=C:\\Users\\lenovo\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393" ^
  "-DCMAKE_ANDROID_NDK=C:\\Users\\lenovo\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393" ^
  "-DCMAKE_TOOLCHAIN_FILE=C:\\Users\\lenovo\\AppData\\Local\\Android\\Sdk\\ndk\\25.1.8937393\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=C:\\Users\\lenovo\\AppData\\Local\\Android\\Sdk\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\lenovo\\AndroidStudioProjects\\MyApplication\\app\\OpenCV-android-sdk\\sdk\\build\\intermediates\\cxx\\Debug\\a5i1k53m\\obj\\armeabi-v7a" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\lenovo\\AndroidStudioProjects\\MyApplication\\app\\OpenCV-android-sdk\\sdk\\build\\intermediates\\cxx\\Debug\\a5i1k53m\\obj\\armeabi-v7a" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BC:\\Users\\lenovo\\AndroidStudioProjects\\MyApplication\\app\\OpenCV-android-sdk\\sdk\\.cxx\\Debug\\a5i1k53m\\armeabi-v7a" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
