# 🪵 woodpecker
- [프로젝트 구조](#프로젝트-구조)
- [Quick Start](#quick-start)
- [설계](#설계)
  - [ERD](#erd)
  - [아키텍처](#아키텍처)
- [사용 기술](#사용-기술)
- [Swagger](#swagger)
- [구현 기능](#구현-기능)
- [트러블슈팅](#트러블슈팅)
- [TIL](#til)

<br>

## 프로젝트 구조

<details>
<summary>디렉토리 구조</summary>
  
```bash
tree
```
</details>


<br>

## Quick Start

**1. 프로젝트 클론**
  - 원하는 위치에서 프로젝트를 clone 합니다.
    ```sh
    $ git clone https://github.com/Hajin74/woodpecker.git
    $ cd woodpecker
    ```

 **2. application.properties 파일 추가**
 - `woodpecker/src/main/resources`에 제공된 `application.properties` 파일을 추가합니다.

 **3. 프로젝트 빌드**
 - 각각의 서버 디렉토리에서 다음 명령어를 실행하여 프로젝트를 빌드합니다
     ```sh
     $ ./gradlew build -x test
     ```

 **4. woodpecker 서버 실행**
   - `woodpecker` 서버 디렉토리로 이동한 후, 다음 명령어로 서버를 실행합니다.
     ```sh
     $ cd woodpecker/build/libs
     $ java -jar woodpecker-0.0.1-SNAPSHOT.jar

     ```
</br>

<br>

## API 명세서

### Swagger
실행이 되면, Swagger로 API 명세를 확인할 수 있습니다.
- [woodpecker API 바로가기](http://localhost:8888/swagger-ui/index.html#/)

### Postman
실행이 되면, Postman으로 API를 호출할 수 있습니다.
- [woodpecker Postman API 바로가기]()

</br>

## 설계

### ERD
(이미지 삽입 예정)

### 아키텍처
(이미지 삽입 예정)

<br>

## 사용 기술
- Java 17
- Spring Boot 3.X
- Spring Data JPA (ORM)
- GraphQL
- PostgreSQL 15.7.X
- KAKAO OAuth2
- Naver API

<br>

## 구현 기능

<br>

## 트러블슈팅
[✅ Kakao API 사용자 정보 호출 시 KakaoAccount가 null인 이슈](https://qwertyv.tistory.com/83)

<br>

## TIL
[▶️ Spring Security + JWT + 카카오 소셜 로그인 구현하기](https://qwertyv.tistory.com/82)

[▶️ GraphQL 도입기](https://qwertyv.tistory.com/81)
