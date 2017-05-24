# iamport-rest-client-java-hc
JAVA 1.5, 1.6버전 사용자들을 위한 Apache HttpClient기반의 java용 아임포트 REST API클라이언트입니다.

기존에 제공된 JAVA 1.7 이상 버전용 Client - [https://github.com/iamport/iamport-rest-client-java](https://github.com/iamport/iamport-rest-client-java)

## 설치
[JitPack](https://jitpack.io/) 을 통해 maven설정을 하실 수 있습니다.  

pom.xml에 아래의 내용을 추가해주세요. 

```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.iamport</groupId>
    <artifactId>iamport-rest-client-java-hc</artifactId>
    <version>0.0.1</version>
</dependency>
```


## 구현된 API
- POST /users/getToken
- GET /payments/{imp_uid}
- GET /payments/{imp_uid}
- POST /payments/cancel

## 예제
src/test/java 의 IamportClientTest.java를 참조해주세요