# I'lab Android
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.22-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/gradle-8.6-green.svg)](https://gradle.org/)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-2023.1.1%20%28Hedgehog%29-green)](https://developer.android.com/studio)
[![minSdkVersion](https://img.shields.io/badge/minSdkVersion-26-red)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
[![targetSdkVersion](https://img.shields.io/badge/targetSdkVersion-34-orange)](https://developer.android.com/distribute/best-practices/develop/target-sdk)
<br/>

I'lab(아이랩) - 내 취향대로 만드는 AI 프로필 사진 [PlayStore](https://play.google.com/store/apps/details?id=com.nexters.ilab.android)
<br/>
![img-playstore-thumnail (1)](https://github.com/Nexters/ilab-android/assets/51016231/167ab845-5a60-45ed-989d-ff7c34a8baae)
<br/>

<p align="center">
<img src="https://github.com/Nexters/ilab-android/assets/51016231/3501b3e6-66fe-4328-a968-9a9a75656fe1" width="30%"/>
<img src="https://github.com/Nexters/ilab-android/assets/51016231/30b1a7f8-aca5-4b72-8ed2-fc6b7c209c25" width="30%"/>
</p>

<p align="center">
<img src="https://github.com/Nexters/ilab-android/assets/51016231/b21797a4-6a80-482b-ab39-d7e0f6e1fc83" width="30%"/>
<img src="https://github.com/Nexters/ilab-android/assets/51016231/e50bd8f4-9746-4fcd-b08f-15ecbe601167" width="30%"/>
<img src="https://github.com/Nexters/ilab-android/assets/51016231/83fbdec2-a81c-4578-b516-0529177f2216" width="30%"/>
</p>

## Features

|홈 화면 1|홈 화면 2|사진 가이드 화면|
|:-----:|:-----:|:-----:|
|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/48bd1084-afcd-4274-aba0-5881d8262902">|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/f7a3b3e1-7ad1-4c75-8f1d-da3495a0b241">|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/1a2deddd-52b8-4ef5-a270-ddb7fc9a34ff">

|스타일 입력 화면|이미지 생성 화면|이미지 생성 완료 화면|
|:-----:|:-----:|:-----:|
|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/cdbbb877-65c0-4214-9943-84b13439e33e">|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/d81ac303-a42d-40a1-a278-7c8b6626e44b">|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/b992170f-4db0-4ff7-8f58-cac1df54c43e">

|마이 페이지 화면|마이 앨범 화면|설정 화면|
|:-----:|:-----:|:-----:|
|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/bd10a483-8a95-4641-a5cb-b08118404076">|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/69f90c35-194a-405d-b910-c57353b347b4">|<img width="240" src="https://github.com/Nexters/ilab-android/assets/51016231/eb4054ec-7fcf-4e3b-a6b0-5f4a5ec92d6a">

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
│   ├── myalbum
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

## Issue
- [ ] proguard 활성화시 compose-navigation 관련 함수들을 인식할 수 없는 문제 

