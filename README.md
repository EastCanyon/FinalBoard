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

## 6. 페이징 유틸리티

FinalBoard 프로젝트는 데이터의 양이 많을 때 사용자가 쉽게 데이터를 탐색할 수 있도록 페이지네이션 기능을 포함하고 있습니다. 이를 위해 `PagingUtils` 클래스를 직접 구현하여 사용했습니다.

### PagingUtils 클래스

`PagingUtils` 클래스는 페이지네이션을 위한 유틸리티 클래스입니다. 이 클래스는 페이지 블록, 시작 및 끝 페이지, 데이터 범위 등을 계산하는 기능을 제공합니다. 

#### PagingUtils 클래스 설계 및 구현

`PagingUtils` 클래스는 다음과 같은 주요 메서드와 내부 클래스를 포함하고 있습니다.

1. **getPagingInfo**: 페이징 정보를 계산하여 반환하는 메서드입니다. 이 메서드는 다음과 같은 계산 과정을 포함합니다.
   - 페이지 블록의 수, 페이지 크기, 총 페이지 블록 수, 현재 페이지를 검증 및 계산합니다.
   - 시작 및 끝 페이지 블록을 계산하고, 이전 및 다음 페이지가 존재하는지 여부를 결정합니다.
   - 시작 및 끝 데이터 번호를 계산합니다.

2. **checkPageSizeValidation**: 주어진 페이지 크기를 검증하여 유효한 값으로 변환합니다.

3. **checkCurrentPageValidation**: 주어진 현재 페이지를 검증하여 유효한 값으로 변환합니다.

4. **calTotalPageBlockCnt**: 전체 데이터 수와 페이지 크기를 기반으로 총 페이지 블록 수를 계산합니다.

5. **calEndPageBlock**: 현재 페이지와 페이지 블록 수를 기반으로 끝 페이지 블록을 계산합니다.

6. **checkEndPageBlockValidation**: 끝 페이지 블록이 유효한지 검증합니다.

7. **calStartPageBlock**: 끝 페이지 블록과 페이지 블록 수를 기반으로 시작 페이지 블록을 계산합니다.

8. **checkStartPageBlockValidation**: 시작 페이지 블록이 유효한지 검증합니다.

9. **PagingInfo**: 페이징 정보를 담는 내부 클래스입니다. 페이지 크기, 현재 페이지, 시작 및 끝 페이지 블록, 시작 및 끝 데이터 번호, 총 페이지 블록 수, 총 데이터 수, 이전 및 다음 페이지 존재 여부를 포함합니다.

```java
package com.example.finalboard.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PagingUtils {

    public static PagingInfo getPagingInfo(String currentPage_, String pageSize_, long totalRowDataCnt) {
        int pageBlockCnt = 5;
        int pageSize = checkPageSizeValidation(pageSize_);
        int totalPageBlockCnt = calTotalPageBlockCnt(totalRowDataCnt, pageSize);
        int currentPage = checkCurrentPageValidation(Integer.parseInt(currentPage_), totalPageBlockCnt);

        int endPageBlock = calEndPageBlock(currentPage, pageBlockCnt);
        int startPageBlock = calStartPageBlock(endPageBlock, pageBlockCnt);
        
        endPageBlock = checkEndPageBlockValidation(endPageBlock, totalPageBlockCnt);
        boolean next = (currentPage != totalPageBlockCnt);

        startPageBlock = checkStartPageBlockValidation(startPageBlock);
        boolean prev = (currentPage != 1);

        int startRowDataNum = (currentPage - 1) * pageSize + 1;
        int endRowDataNum = startRowDataNum + pageSize - 1;

        return new PagingInfo(
                pageSize,
                currentPage,
                startPageBlock,
                endPageBlock,
                startRowDataNum,
                endRowDataNum,
                totalPageBlockCnt,
                totalRowDataCnt,
                prev,
                next
        );
    }

    private static int checkPageSizeValidation(String pageSize) {
        if ("5".equals(pageSize) || "10".equals(pageSize) || "30".equals(pageSize) || "50".equals(pageSize)) {
            return Integer.parseInt(pageSize);
        }
        return 10;
    }

    private static int checkCurrentPageValidation(int currentPage, int totalPageBlockCnt) {
        if (currentPage > totalPageBlockCnt)
            currentPage = totalPageBlockCnt;
        else if (currentPage < 1)
            currentPage = 1;

        return currentPage;
    }

    private static int calTotalPageBlockCnt(long totalRowDataCnt, int pageSize) {
        if (totalRowDataCnt == 0) {
            return 1;
        }
        return (int)(totalRowDataCnt + pageSize - 1) / pageSize;
    }

    private static int calEndPageBlock(int currentPage, int pageBlockCnt) {
        return ((currentPage + pageBlockCnt - 1) / pageBlockCnt) * pageBlockCnt;
    }

    private static int checkEndPageBlockValidation(int endPageBlock, int totalPageBlockCnt) {
        return Math.min(endPageBlock, totalPageBlockCnt);
    }

    private static int calStartPageBlock(int endPageBlock, int pageBlockCnt) {
        return endPageBlock - pageBlockCnt + 1;
    }

    private static int checkStartPageBlockValidation(int startPageBlock) {
        return Math.max(startPageBlock, 1);
    }

    @Getter
    @AllArgsConstructor
    public static class PagingInfo {
        private int pageSize;
        private int currentPage;
        private int startPageBlock;
        private int endPageBlock;
        private int startRowDataNum;
        private int endRowDataNum;
        private int totalPageBlockCnt;
        private long totalRowDataCnt;
        private boolean prev;
        private boolean next;
    }
}
