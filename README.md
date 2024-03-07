# I'Lab Android
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-8.6-green.svg)](https://gradle.org/)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-2023.1.1%20%28Hedgehog%29-green)](https://developer.android.com/studio)
[![minSdkVersion](https://img.shields.io/badge/minSdkVersion-26-red)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
[![targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-34-orange)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
<br/>

I'Lab(아이랩) - 내 취향대로 만드는 AI 프로필 사진 [PlayStore](https://play.google.com/store/apps/details?id=com.nexters.ilab.android)
<br/>
![img-playstore-thumnail](https://github.com/Nexters/ilab-android/assets/51016231/4448ba0e-a2b0-47df-8f21-0d2b21c6e753)
<br/>

<p align="center">
<img src="https://github.com/Nexters/ilab-android/assets/51016231/3501b3e6-66fe-4328-a968-9a9a75656fe1" width="30%"/>
<img src="https://github.com/Nexters/ilab-android/assets/51016231/233f12ec-6b0a-489f-8b72-0599348b5ce4" width="30%"/>
</p>

<p align="center">
<img src="https://github.com/Nexters/ilab-android/assets/51016231/b21797a4-6a80-482b-ab39-d7e0f6e1fc83" width="30%"/>
<img src="https://github.com/Nexters/ilab-android/assets/51016231/e50bd8f4-9746-4fcd-b08f-15ecbe601167" width="30%"/>
<img src="https://github.com/Nexters/ilab-android/assets/51016231/83fbdec2-a81c-4578-b516-0529177f2216" width="30%"/>
</p>

## Features

## Development

### Required

- IDE : Android Studio Hedgehog
- JDK : Java 17을 실행할 수 있는 JDK
- Kotlin Language : 1.9.22

### Language

- Kotlin

### Libraries

- AndroidX
  - Activity Compose
  - Core
  - Lifecycle & ViewModel Compose
  - Navigation
  - DataStore
  - StartUp
  - Splash

- Kotlin Libraries (Coroutine, Serialization, Immutable Collection)
- Compose
  - Material3
  - Navigation

- Dagger Hilt
- Retrofit
- Timber
- [orbit-mvi](https://github.com/orbit-mvi/orbit-mvi)(compose, viewmodel)
- [compose-stable-marker](https://github.com/skydoves/compose-stable-marker)
- coil(compose, gif)
- kakao-auth
- [ComposeExtensions](https://github.com/taehwandev/ComposeExtensions)
- [ComposeInvestigator](https://github.com/jisungbin/ComposeInvestigator)
- Firebase(Analytics, Crashlytics)

#### Test & Code analysis

- Ktlint
- Detekt

#### Gradle Dependency

- Gradle Version Catalog

## Architecture
Based on [Now in Android](https://github.com/android/nowinandroid) with Clean Architecture

<img width="760" alt="image" src="https://github.com/easyhooon/watcha-assignment/assets/51016231/2837a3b6-32f8-46aa-a102-3fb5b3e3826a">

<img width="760" alt="image" src="https://github.com/easyhooon/watcha-assignment/assets/51016231/b29020a1-69aa-482b-8af4-ddb27122a440">

## Package Structure
```
├── app
│   └── Application
├── build-logic
├── core
│   ├── common
│   ├── data
│   ├── datastore
│   ├── designsystem
│   ├── domain
│   ├── network
│   └── ui
├── feature
│   ├── createimage
│   ├── home
│   ├── intro
│   ├── login
│   ├── main 
│   ├── mayalbum
│   ├── mypage
│   ├── navigator
│   ├── privacypolicy
│   ├── setting
│   └── uploadphoto
├── gradle
│   └── libs.versions.toml
└── report
    ├── compose-metrics
    └── compose-reports
```
<br/>

