# podo-structure

Gateway, 인증 서버 구현 데모

- `gateway` - `auth` 간 jwt 키 공유

<br>

# Module

### podo-server

테스트용 리소스 서버

### podo-gateway

- spring-cloud-gateway
- spring-cloud-security
- jwt validate
- authorization, 보통 각 리소스 서버에서 진행하지만 gateway에서 진행 [관련영상](https://tv.kakao.com/channel/3150758/cliplink/391419714)

### podo-auth

인증 서버

- 로그인
- jwt 발급

### podo-member-service

회원관리 서비스

- 회원가입

### podo-internal-member

회원정보 내부망 서버 

- 회원정보 조회

