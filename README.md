# iamport-rest-client-java-hc
JAVA 1.5, 1.6버전 사용자들을 위한 Apache HttpClient기반의 java용 아임포트 REST API클라이언트입니다.

기존에 제공된 JAVA 1.7 이상 버전용 Client - [https://github.com/iamport/iamport-rest-client-java](https://github.com/iamport/iamport-rest-client-java)

## 설치
maven plugin을 다음과 같이 추가하시거나, build된 [jar](build/iamport-rest-client-java-hc-0.1.1-SNAPSHOT.jar) 파일을 다운로드하세요.

```xml
<dependency>
	<groupId>com.siot.iamport</groupId>
	<artifactId>iamport-rest-client-java-hc</artifactId>
	<version>0.1.1-SNAPSHOT</version>
</dependency>
```

## 구현된 API
- POST /users/getToken
- GET /payments/{imp_uid}
- GET /payments/{imp_uid}
- POST /payments/cancel

## 예제
src/test/java 의 IamportClientTest.java를 참조해주세요