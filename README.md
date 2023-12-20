HeatIsland-APP
### ✅ 모바일프로그래밍 수업 - 열섬현상 커뮤니티 어플 제작
<hr>
도시열섬지도, 공유우산지도, 무더위쉼터지도, 사용자 커뮤니티 및 투표서비스가 제공되는 도시열섬 어플리케이션입니다.
<br><br>
<div align="center">
<img width="259" alt="스크린샷 2023-12-20 오전 11 23 01" src="https://github.com/ffe4el/HeatIsland-APP/assets/93892724/ba8e12d9-2b7c-4fa5-a43d-f2f2c9a12716"></div>

### 개발환경
---
- lang: Java 17
- IDE: Android Studio
- Android compileSdk 34
- Android tartgetSdk 34
- emulator: Pixel 7 Pro API 34
- external library : VectorChildFinder
- database: MySQL
<br>

### Setting
---
- 해당 프로젝트 파일을 다운로드하고 API 키를 설정하는 단계를 거쳐야 실행 가능합니다.
- 맵(지도)를 어플리케이션에 띄우기 위해 **google cloud console** 에서 프로젝트 및 API 키를 발급 받습니다.
- **서울시 열린 데이터 광장**에서 openAPI를 사용하여 필요한 데이터를 받아오기 위해 API 키를 발급 받습니다. 
- AndroidManifest.xml 파일에 다음과 같은 코드를 작성합니다.
```java
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <application>
        <!-- ... -->
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="MY_API_KEY_HERE" />
    </application> 
```

- HeatShelterAPI.java 클래스에 key 변수로 서울시 api키를 적어 넣습니다.

<br><br>




