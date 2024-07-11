# FinalBoard

## 1. 프로젝트 내용
FinalBoard는 블로그와 게시판 기능을 통합한 웹 애플리케이션입니다. 사용자는 게시물을 작성, 수정, 삭제할 수 있으며, 댓글을 통해 상호작용할 수 있습니다. 또한, 로그인 및 권한 관리 기능을 통해 사용자별 접근 제어를 구현하였습니다. 이 프로젝트는 다양한 데이터베이스에서 동작할 수 있도록 설계되었으며, 유연한 페이지네이션과 검색 기능을 제공합니다.

## 2. 개발환경
- **언어**: Java 11
- **프레임워크**: Spring Boot 2.6.4
- **빌드 도구**: Gradle
- **데이터베이스**: H2, MySQL, MariaDB, PostgreSQL (지원 예정)
- **ORM**: Spring Data JPA
- **템플릿 엔진**: Thymeleaf
- **웹 서버**: Tomcat (Spring Boot 내장)
- **IDE**: IntelliJ IDEA
- **버전 관리**: Git, GitHub

## 3. 개발에 사용한 기술들을 사용한 이유
- **Spring Boot**: 신속한 애플리케이션 개발을 위해 Spring Boot를 사용하였습니다. Spring Boot는 설정이 간편하고, 개발 속도를 높일 수 있는 다양한 내장 기능을 제공합니다.
- **Spring Data JPA**: JPA를 사용하여 데이터베이스와 상호작용합니다. Spring Data JPA는 복잡한 SQL 쿼리 작성을 최소화하고, 엔티티 기반의 접근을 가능하게 합니다.
- **Thymeleaf**: 서버 사이드 템플릿 엔진으로 HTML을 쉽게 구성할 수 있으며, Spring과의 통합이 뛰어납니다.
- **H2 Database**: 테스트 환경에서 빠르고 가볍게 사용할 수 있는 메모리 기반의 데이터베이스입니다.
- **Gradle**: 빌드 및 의존성 관리를 위해 Gradle을 선택했습니다. 이는 기존의 Maven보다 더 유연하고 강력한 빌드 스크립트를 작성할 수 있습니다.

## 4. 프로젝트 구조
```plaintext
FinalBoard/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── finalboard/
│   │   │               ├── controller/          # 컨트롤러 레이어 (PostController 등)
│   │   │               ├── dto/                 # 데이터 전송 객체 (PostDto 등)
│   │   │               ├── model/               # 엔티티 클래스 (Post, MyUser 등)
│   │   │               ├── repository/          # 데이터 접근 레이어 (PostRepository 등)
│   │   │               ├── service/             # 비즈니스 로직 레이어 (PostService 등)
│   │   │               └── utils/               # 유틸리티 클래스 (PagingUtils 등)
│   │   ├── resources/
│   │   │   ├── templates/                      # Thymeleaf 템플릿 파일들
│   │   │   └── application.properties           # 애플리케이션 설정 파일
│   ├── test/                                   # 테스트 파일들
│   └── build.gradle                            # Gradle 빌드 스크립트
├── .gitignore                                  # Git 무시 파일 설정
└── README.md                                   # 프로젝트 설명 파일

