# RoomDemo_1

##안드로이드 스튜디오 java.lang.reflect.InvocationTargetException (no error message)

안드로이드 스튜디오 mac M1을 사용하는 중인데 Android 라이브러리중 하나인 Room에서 에러가 나타났다.
간략하게 해당현상을 사라지게 하려면 
dependency에 kapt "org.xerial:sqlite-jdbc:3.34.0" 을 추가해주면 간단하게 해결된다.
