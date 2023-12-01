 # 💡 Topic

- **숙박 예약 서비스**
- 회원이 서비스를 로그인하여 상품의 숙소들을 확인할 수 있으며 주문하고 싶은 상품들을 장바구니에 담을 수도 있으며 ,주문을 할 수 있도록 만든 서비스

# 📝 Summary

실제 숙박 예약을 하는 어플로서 로그인하여 주문 및 장바구니 담기 등을 진행할 수 있으며, 상품 전체 리스트 조회와 숙소 하나의 상세 정보도 보여줄 수 있다.

비회원인 경우 어떤 숙소가 있는지 구경할 수 있다.

# ⭐️ Key Function

- 회원
    - 사람들이 자신의 아이디를 만들어 로그인할 수 있다.
    - 로그인 시간에 만료 시간을 두어 일정 시간이 지나면 서비스 사용을 위해 재로그인을 해야 한다.
    - 아이디와,비밀번호를 통하여 인증을 진행하며 일치하지 않을 경우 서비스 사용을 할 수 없다.
- 주문
    - 주문 내역을 저장할 수 있다.
    - 나의 주문 내역을 불러와 확인할 수 있다.
    - 현재 주문 내역이 주문이 가능한 날짜인지를 확인할 수 있다.
- 숙소
    - OPEN API를 통하여 데이터를 가져와 필요한 숙소의 정보들을 DATABASE에 파싱하여 저장한다.
- 장바구니
    - 장바구니에 숙소 내역을 담을 수 있다.
    - 장바구니에 담은 내역을 삭제할 수 있다.
    - 장바구니에 담은 내역이 품절이 된 지 아닌지를 내 장바구니 조회를 할 때 알 수 있다.

# 🛠 Tech Stack

`JAVA`,`Spring Boot`,`Spring Security`,`Docker`,`MySQL`, `Github`,`Git`,`Slack`,`Redis`

# ⚙️ Architecture

`Domain Design Architecture`

# 🧑🏻‍💻 Team

- 백엔드 개발자 4명,프론트 개발자 4명

# 🤚🏻 Part

- 장바구니 API 개발
- 공통 API Response 개발
- 공통 예외 처리 개발
- QueryDSL 설정
- SpringDoc을 이용한 Swagger 연동
- ERD 설계
- API 명세서 작성
- 장바구니 통합 테스트 코드 작성

# 🤔 Learned

- 여러 테이블이 조인된 부분에서 발생하는 순환참조를 @JsonIgnore를 통해 해결할 수 있는 것을 알게 되었다.
- SpringDoC에 있는 Swagger에서 Authorize가 필요한 부분이 입력받을 수 있는 부분이 Swagger내에 존재하지 않아서 프론트 쪽이 Authorize API 부분을 호출할 수 없었다.
해당 부분을 커스텀하여 Authorize를 넣어 API를 호출할 수 있도록 구현할 수 있게 되었다.
- ERD 설계를 진행하면서 테이블끼리의 엮이는 다대일,일대다 관계에 대해 깊이 알게 되었다.
- 프론트와 협업을 진행하면서 API 명세 및 서버 설정과 API Response를 어떻게 주고받아야 하는지 알게 되었다.
- 통합 테스트 코드 작성을 진행하면서 의존성 및 모의 객체 사용에 대해 이해할 수 있었다.
- QueryDSL을 사용하면서 JPA만으로 조인이 많이 발생하는 부분에 대한 쿼리 작성 어려움을 해결할 수 있게 되었다.


# 🔌 프로젝트 실행방법

- **`.env` 파일을 만들어야 한다.**
- env 파일은 다음 [env 예제 파일(`.env.example`](https://github.com/Parkgeonmoo/Traveler/blob/main/.env.example)[)](https://github.com/FAST-gamsungcoding/BE_MiniProject/blob/docs/readme/.env.example)의 형식을 참고
    
    ```
    # MYSQL 설정
    # !주의: USERNAME에 root를 입력하시면 안된다. root 외의 다른 이름을 입력.
    LOCAL_MYSQL_USERNAME=<MySQL의 DB username>
    LOCAL_MYSQL_PASSWORD=<MySQL의 DB password>
    LOCAL_MYSQL_VOLUME_PATH=./bin/mysql # MySQL 데이터를 저장할 본인 컴퓨터 경로
    LOCAL_MYSQL_URL=localhost # 컴퓨터에서 사용할 MySQL URL
    LOCAL_MYSQL_PORT=3306 # 컴퓨터에서 사용할 MySQL PORT
    
    # JWT 설정
    JWT_SECRET_KEY=<your-jwt-secret-key> # BASE64로 인코딩된 JWT 시크릿 키
    JWT_ACCESS_EXPIRATION=36000000 # 액세스 토큰의 만료시간 (기본 1시간)
    JWT_REFRESH_EXPIRATION=864000000 # 리프레시 토큰의 만료시간 (기본 24시간)
    
    # Redis 설정
    LOCAL_REDIS_VOLUME_PATH=./bin/redis # REDIS 데이터를 저장할 본인 컴퓨터 경로
    LOCAL_REDIS_PORT=6379 # 컴퓨터에서 사용할 REDIS PORT
    
    # Open API Key 설정
    PORTAL_API_KEY=<your-open-api-key> # 개인의 Open API key (/v1/accommodation/data 호출시에만 필요)
    ```
    
- **docker compose를 사용하여 mysql과 redis 데이터베이스 환경을 만들어야 한다.**
    - 인텔리제이의 docker plugin 기능을 사용하거나, 다음 명령어를 입력해서 데이터베이스 환경을 구성하면 된다.
    
    ```java
    docker compose up
    ```
    
 ## ⚙ API 문서
    
    ### Swagger
    
    API 문서는 **[실제 구동 테스트 서버](http://api.gamsung.xyz/swagger-ui/index.html)**에서 확인해 볼 수 있다.
    
    또는 프로젝트를 다운로드 받은 후 서버를 구동한 뒤, 다음 링크의 Swagger UI에서도 확인해 볼 수 있다.
    
    ```java
    http://localhost:8080/swagger-ui/index.html
    ```
    

# 📷 Screenshot

# 실행 영상
https://github.com/Parkgeonmoo/Traveler/assets/50697545/b05c0ea8-50b6-4530-b659-39105759c1c4

# API 응답 스크린샷

- **[API 응답 스크린샷](https://www.notion.so/geonmoo/API-cafe1d5fd1ae4d189b259463c0d6a105)**


