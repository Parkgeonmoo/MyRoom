 # 💡 Topic

- **숙박 예약 플랫폼 서비스 API 서버 개발**

# 📝 Summary

- 사용자가 원활하게 숙소를 예약할 수 있도록 하는 플랫폼을 개발하였다.
- 이 플랫폼은 사용자에게 각 숙소의 상세 정보를 제공함으로써, 실제 숙소 이용시 예상과 차이가 없도록 돕는다는 것을 중점적으로 고려하여 개발하였다. 
- 이를 통해 정보의 불일치로 인한 불편함을 최소화하고, 사용자가 만족하는 숙소를 찾을 수 있도록 도와 사용자의 만족도를 높이는데 효과적으로 기여하고 있다.

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

`JAVA 17`,`Spring Boot 3.1.2`,`Spring Security`,`Docker`,`MySQL`, `Github`,`Git`,`Slack`,`Redis`

# 🧑🏻‍💻 Team

- 백엔드 개발자 4명,프론트 개발자 5명

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

 # ⚙ API 문서
    
  ### [Swagger](http://api.gamsung.xyz/swagger-ui/index.html)
    
  : Swagger를 이용한 API 문서 작업
    


# 📷 Screenshot

# 실행 영상
https://github.com/Parkgeonmoo/Traveler/assets/50697545/b05c0ea8-50b6-4530-b659-39105759c1c4

|                           로그인 페이지                            |                                              회원가입 페이지                                             |
| :-------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------: |
|![image](https://github.com/FAST-gamsungcoding/FE_MiniProject/assets/122848687/eb98e4b6-1cef-4b89-9353-c6a122f18668)|![image](https://github.com/FAST-gamsungcoding/FE_MiniProject/assets/122848687/55902cff-f5db-4a33-b7d7-50618803a627)|
|       로그인 페이지이다. 각 Input 태그의 하단에 위치한 label로 유효성 검사 여부를 알 수 있다.       |                   이메일 중복 여부와 더불어 각 Input 태그의 하단에 위치한 label로 유효성 검사 여부를 알 수 있다.                     |

|                          메인 페이지                           |                                   상세 상품 페이지                                          |
| :------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------: |
| ![image](https://github.com/FAST-gamsungcoding/FE_MiniProject/assets/122848687/c55e2292-d978-4e1d-89df-44fd1ed9e931) |         ![image](https://github.com/FAST-gamsungcoding/FE_MiniProject/assets/122848687/74f1c873-f463-4ad1-b0bc-04efd62ffa23)|
|   메인 페이지에서는 지역별로 숙박 상품을 무한 스크롤을 통해 조회가 가능하다.          |                   개별 상품을 조회하며 숙박을 위한 날짜 및 인원수를 설정하여, 장바구니에 담거나 바로 결제를 할 수 있다.                    |



|                           장바구니 페이지                             |                                             예약진행 페이지                                             |
| :-------------------------------------------------------------------------------------------------------: | :-------------------------------------------------------------------------------------------------------: |
| ![image](https://github.com/FAST-gamsungcoding/FE_MiniProject/assets/122848687/3c245806-94b6-4b47-a894-678ba3ead1fa) |    ![image](https://github.com/FAST-gamsungcoding/FE_MiniProject/assets/122848687/438eeeeb-e486-457b-b1b9-b68c71976dfe) |
|       개별 상품 페이지에서 설정했던 날짜와 인원수대로, 최대 10개까지 저장이 가능하다.       |                  한번에 여러 상품을 결제 할 수 있으며, 예약자 정보를 로그인한 유저의 정보에서 가져와 저장한다.                  |

|                           예약내역 페이지                             |                        
| :-------------------------------------------------------------------------------------------------------: | 
| ![image](https://github.com/FAST-gamsungcoding/FE_MiniProject/assets/122848687/38dcc9de-bf52-426f-82e9-6ef66ccd8bf3)   
|       결제를 진행했던 숙박 상품들을 모두 조회할 수 있다.     |          



