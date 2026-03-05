# iamport-rest-client-java-hc

> **⚠️ DEPRECATED**: 이 레포지토리는 2026년 3월 4일부로 deprecated 되었으며, 공식 지원이 되지 않습니다.
> 본 클라이언트는 함께 포함된 [openapi.json](./openapi.json) Swagger spec 기준으로 동작합니다.
> 추가/수정이 필요한 경우 본 레포지토리를 fork하여 수정하거나,
> [PortOne V1 REST API 문서](https://developers.portone.io/api/rest-v1?v=v1)를 참고하여 REST client를 직접 구현해주세요.

JAVA 1.5, 1.6버전 사용자들을 위한 Apache HttpClient기반의 java용 아임포트 REST API클라이언트입니다.

기존에 제공된 JAVA 1.7 이상 버전용 Client - [https://github.com/iamport/iamport-rest-client-java](https://github.com/iamport/iamport-rest-client-java)

## 설치
[JitPack](https://jitpack.io/#iamport/iamport-rest-client-java-hc) 을 통해 maven설정을 하실 수 있습니다.

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
    <version>0.1.0</version>
</dependency>
```


## 구현된 API

### 인증
- POST /users/getToken

### 결제
- GET /payments/{imp_uid}
- GET /payments/find/{merchant_uid}
- GET /payments/findAll/{merchant_uid}/{payment_status}/{sorting}
- GET /payments
- GET /payments/status/{payment_status}
- POST /payments/cancel
- POST /payments/prepare
- GET /payments/prepare/{merchant_uid}
- GET /payments/balance/{imp_uid}

### 빌링키
- POST /subscribe/customers/{customer_uid}
- DELETE /subscribe/customers/{customer_uid}
- GET /subscribe/customers/{customer_uid}
- GET /subscribe/customers

### 비인증 결제
- POST /subscribe/payments/onetime
- POST /subscribe/payments/again
- POST /subscribe/payments/schedule
- POST /subscribe/payments/unschedule

### 예약 결제
- GET /subscribe/payments/schedule/{customer_uid}
- GET /subscribe/payments/schedule
- PUT /subscribe/payments/schedule/{merchant_uid}
- POST /subscribe/payments/schedule/{merchant_uid}/retry
- POST /subscribe/payments/schedule/{merchant_uid}/reschedule

### 본인인증
- GET /certifications/{imp_uid}
- DELETE /certifications/{imp_uid}

### 현금영수증
- POST /receipts/{imp_uid}
- DELETE /receipts/{imp_uid}
- POST /receipts/external/{merchant_uid}
- DELETE /receipts/external/{merchant_uid}
- POST /receipts/partner
- GET /receipts/{imp_uid}/issued

### 가상계좌
- POST /vbanks
- PUT /vbanks/{imp_uid}
- GET /vbanks/holder

### 에스크로
- POST /escrows/logis/{imp_uid}
- PUT /escrows/logis/{imp_uid}

### PG 정보
- GET /payments/pg

### 코드
- GET /codes

### 베네피아
- POST /benepia/point
- POST /benepia/payments

### CVS (편의점)
- POST /cvs/payments

### OTP (본인인증)
- POST /otp/request
- POST /otp/confirm

### KCP 퀵페이
- POST /payments/quickpay

### Paymentwall
- POST /paymentwall/delivery

### 네이버페이
- POST /naver/confirm
- GET /naver/product-orders
- GET /naver/product-orders/{product_order_id}
- POST /naver/cancel
- POST /naver/ship
- POST /naver/place
- POST /naver/request-return
- POST /naver/approve-return
- POST /naver/reject-return
- POST /naver/withhold-return
- POST /naver/resolve-return
- POST /naver/approve-cancel
- POST /naver/collect-exchanged
- POST /naver/ship-exchanged
- GET /naver/reviews
- POST /naver/cash-amount
- PUT /naver/cfm

### PAYCO
- POST /payco/orders/status/{imp_uid}

### 기타
- GET /payments/tier

## 예제
src/test/java 의 IamportClientTest.java를 참조해주세요
