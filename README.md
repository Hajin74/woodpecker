# 🪵 woodpecker
- [프로젝트 구조](#프로젝트-구조)
- [Quick Start](#quick-start)
- [설계](#설계)
  - [ERD](#erd)
- [사용 기술](#사용-기술)
- [API 명세서](#api-명세서)
- [구현 기능](#구현-기능)
- [트러블슈팅](#트러블슈팅)

<br>

## 프로젝트 구조

<details>
<summary>디렉토리 구조</summary>
  
```bash
src
└── main
   ├── java
   │   └── org
   │       └── example
   │           └── woodpeckerback
   │               ├── WoodpeckerBackApplication.java
   │               ├── config
   │               │   ├── CorsConfig.java
   │               │   ├── GraphQLConfig.java
   │               │   ├── JwtInterceptor.java
   │               │   └── SecurityConfig.java
   │               ├── dto
   │               │   ├── BookItemResponse.java
   │               │   ├── CustomOAuth2User.java
   │               │   ├── DeleteNoteResponse.java
   │               │   ├── KakaoResponse.java
   │               │   ├── LikeBookResponse.java
   │               │   ├── NaverBookApiResponse.java
   │               │   ├── NaverBookItem.java
   │               │   ├── NoteDetailResponse.java
   │               │   ├── ReadDetailNoteResponse.java
   │               │   ├── SaveBookInput.java
   │               │   ├── SaveBookResponse.java
   │               │   ├── SaveNoteInput.java
   │               │   ├── SaveNoteResponse.java
   │               │   └── ShareBookAndNoteResponse.java
   │               ├── entity
   │               │   ├── Book.java
   │               │   ├── LikeBook.java
   │               │   ├── Note.java
   │               │   ├── SaveBook.java
   │               │   └── User.java
   │               ├── exception
   │               │   ├── CustomException.java
   │               │   ├── ErrorCode.java
   │               │   ├── ErrorType.java
   │               │   └── GraphQLException.java
   │               ├── filter
   │               │   └── JwtFilter.java
   │               ├── graphql
   │               │   ├── BookResolver.java
   │               │   └── NoteResolver.java
   │               ├── handler
   │               │   └── CustomSuccessHandler.java
   │               ├── repository
   │               │   ├── BookRepository.java
   │               │   ├── LikeBookRepository.java
   │               │   ├── NoteRepository.java
   │               │   ├── SaveBookRepository.java
   │               │   └── UserRepository.java
   │               ├── rest
   │               │   ├── BookController.java
   │               │   └── UserController.java
   │               └── service
   │                   ├── BookService.java
   │                   ├── CustomOAuth2UserService.java
   │                   ├── JwtService.java
   │                   └── NoteService.java
   └── resources
       ├── application.properties
       ├── application.yml
       └── graphql
           └── schema.graphqls

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

### Postman
실행이 되면, Postman으로 API를 호출할 수 있습니다.
- [woodpecker Postman API 바로가기](https://documenter.getpostman.com/view/34589851/2sAXqs8Nqm)

</br>

## 설계

### ERD
![woodpecker ERD](https://github.com/user-attachments/assets/cce6032a-def9-4e04-a1a7-b441d4ebca59)

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

### Authentication
- 카카오 소셜 회원가입/로그인 기능 (REST)
  ![프론트](https://github.com/user-attachments/assets/9de947b7-1809-4b9a-b011-2265cfeed748)
  - localhost:3000 클라이언트에서 카카오 로그인을 할 수 있습니다.
  - Cookie에 Authorization을 Name으로 JWT 토큰이 발급됩니다.

### Book
로그인한 사용자는 책에 다음과 같은 기능을 이용할 수 있습니다.
- 책 검색 (REST)
    - 책 검색은 Naver API를 이용합니다.
- 저장 기능 (GraphQL)
    - 검색된 책 중 사용자가 선택한 책을 데이터베이스에 저장합니다.
    - 중복 저장은 불가능합니다.
- 좋아요 기능 (GraphQL)
    - 저장한 책에 좋아요를 할 수 있습니다.
    - 중복 좋아요는 불가능합니다.
- 저장한 책 정보를 외부 공유 기능 (REST)
    - JWT 토큰을 사용합니다.
    - 공유를 통해 타 사용자/비로그인 사용자는 공유자가 저장한 책 정보 및 해당 책에 공유자가 작성한 노트를 열람할 수 있습니다.
    - 열람 만료 기한은 공유 시점으로부터 10분 후 입니다.

### Note
로그인한 사용자는 자신이 저장한 책에 다음과 같은 기능을 이용할 수 있습니다.
- 노트 저장 (GraphQL)
    - 사용자는 자신이 저장한 책에 여러 개의 노트를 작성할 수 있습니다.
- 특정 노트 열람 (GraphQL)
    - 노트 열람 권한은 책을 저장한 사용자 당사자에게만 있습니다.
- 노트 목록 조회 (GraphQL)
    - 노트 열람 권한은 책을 저장한 사용자 당사자에게만 있습니다.
    - 노트는 최근 작성 순으로 정렬됩니다.
- 노트 삭제 (GraphQL)
    - 노트 삭제는 Soft Delete로 구현됩니다.
- 노트 외부 공유 기능 (REST)
    - 책 외부 공유와 함께, 공유를 한 사용자의 노트도 공유됩니다.

<br>

## 트러블슈팅
[✅ Kakao API 사용자 정보 호출 시 KakaoAccount가 null인 이슈](https://qwertyv.tistory.com/83)

[✅ Spring Security + JWT + 카카오 소셜 로그인 구현하기](https://qwertyv.tistory.com/82)

[✅ GraphQL에서 요청별로 인가를 어떻게 할 것인가?](https://qwertyv.tistory.com/81)
