## MSA 1st

### 구현 패키지
Eureka
Auth
Order
Product
Gateway


### Auth
- 회원가입
- 로그인
- 권한 변경
- 토큰 검증
- 토큰 검증 (admin 권한 검증)
- 기타 <br>
  값객체 (username)

### Order
- 주문 생성  <br>
  RabbitMQ  <br>
  보상 트랜잭션 (재고 감소로 인한 주문 실패 시)
- 주문 상태 변경 <br>
- 주문 조회 <br>
  레디스 캐싱
- 기타 <br>
  값객체 (price, quantity)  <br>
  루트 애그리거트 (Order 엔티티)

### Product
- 모든 권한 admin
- 상품 생성  <br>
  레디스 캐싱
- 상품 단일 조회
- 상품 목록 조회 <br>
  레디스 캐싱
- 기타  <br>
  값객체 (price, quantity)  <br>
  루트 애그리거트 (Product 엔티티)

### Gateway
- 인증 필터
- 인가 필터
- 커스텀 로드밸런싱 필터
- 서버 포트 필터

<br>
<br>

### 사용 기술

#### 백엔드
- JDK 17
- SpringBoot 3.4.0
- Spring Security
- RabbitMQ
- Resilience4j

#### 데이터베이스
- JPA
- mySQL
- Redis

#### 인프라
- Eureka
- Docker
- Docker Compose


  
