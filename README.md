# QueryDSL 예제 프로젝트

이 프로젝트는 Spring Boot와 QueryDSL을 활용한 JPA 기반의 예제 애플리케이션입니다.

## 📋 프로젝트 개요

QueryDSL을 사용하여 타입 안전한 쿼리를 작성하는 방법을 학습할 수 있는 예제 프로젝트입니다.
다양한 엔티티와 Repository 패턴을 통해 QueryDSL의 다양한 기능을 실습할 수 있습니다.

## 🛠 기술 스택

-   **Java**: 1.8
-   **Spring Boot**: 2.5.12
-   **Spring Data JPA**: JPA 기반 데이터 액세스
-   **QueryDSL**: 타입 안전한 쿼리 작성
-   **H2 Database**: 인메모리 데이터베이스
-   **Lombok**: 코드 간소화
-   **P6spy**: SQL 로깅

## 📁 프로젝트 구조

```
src/main/java/com/example/querydsl/
├── QuerydslApplication.java          # 메인 애플리케이션 클래스
├── config/
│   └── DataBaseConfiguration.java    # 데이터베이스 설정
├── controller/
│   └── TestController.java           # 테스트 컨트롤러
├── entity/                           # 엔티티 클래스
│   ├── Company.java
│   ├── Member.java
│   ├── School.java
│   ├── Staff.java
│   ├── Store.java
│   └── Student.java
├── repository/                       # Repository 계층
│   ├── CompanyRepository.java
│   ├── MemberRepository.java
│   ├── SchoolRepository.java
│   ├── StaffRepository.java
│   ├── StoreRepository.java
│   ├── StudentRepository.java
│   └── support/                      # QueryDSL Repository 구현체
│       ├── CompanyRepositorySupport.java
│       ├── MemberRepositorySupport.java
│       └── StoreRepositorySupport.java
├── service/
│   └── TestService.java              # 서비스 계층
└── vo/                               # Value Object
    ├── CompanyVO.java
    ├── MemberVO.java
    └── StudentVo.java
```

## 🚀 시작하기

### 필수 조건

-   Java 8 이상
-   Gradle

### 설치 및 실행

1. 프로젝트 클론

```bash
git clone <repository-url>
cd querydsl
```

2. 의존성 설치 및 빌드

```bash
./gradlew build
```

3. Q클래스 생성 (QueryDSL)

```bash
./gradlew compileQuerydsl
```

4. 애플리케이션 실행

```bash
./gradlew bootRun
```

## 📝 주요 기능

### QueryDSL 설정

-   `build.gradle`에서 QueryDSL 플러그인 설정
-   Q클래스 자동 생성을 위한 Gradle 설정
-   JPA 기반 QueryDSL 설정

### 엔티티 설계

프로젝트는 다음과 같은 도메인 엔티티를 포함합니다:

-   **Member**: 회원 정보
-   **Company**: 회사 정보
-   **School**: 학교 정보
-   **Student**: 학생 정보
-   **Staff**: 직원 정보
-   **Store**: 매장 정보

### Repository 패턴

-   JPA Repository와 QueryDSL을 결합한 Repository 패턴
-   `RepositorySupport` 클래스를 통한 커스텀 쿼리 구현
-   타입 안전한 쿼리 작성

## 🔧 QueryDSL 설정 상세

### Gradle 설정

```gradle
// QueryDSL 플러그인
id 'com.ewerk.gradle.plugins.querydsl' version '1.0.10'

// 의존성
implementation 'com.querydsl:querydsl-jpa'
implementation 'com.querydsl:querydsl-apt'
```

### Q클래스 생성 경로

-   생성 경로: `$buildDir/generated/querydsl`
-   소스셋에 자동 추가

## 🧪 테스트

테스트 실행:

```bash
./gradlew test
```

프로젝트에는 다음과 같은 테스트가 포함되어 있습니다:

-   `CompanyRepositorySupportTest.java`
-   `JpaTest.java`
-   `SchoolRepositorySupportTest.java`
-   `StoreRepositoryTest.java`

## 📚 학습 포인트

1. **QueryDSL 기본 문법**: Q클래스를 활용한 쿼리 작성
2. **동적 쿼리**: BooleanBuilder를 활용한 조건부 쿼리
3. **조인 쿼리**: 연관관계 매핑을 활용한 조인 쿼리
4. **집계 함수**: count, sum, avg 등의 집계 함수 사용
5. **페이징**: Pageable을 활용한 페이징 처리
6. **서브쿼리**: JPAExpressions를 활용한 서브쿼리

## 🔗 참고 자료

-   [QueryDSL 공식 문서](http://www.querydsl.com/)
-   [Spring Data JPA 문서](https://spring.io/projects/spring-data-jpa)
-   [Spring Boot 문서](https://spring.io/projects/spring-boot)

## 📄 라이센스

이 프로젝트는 학습 목적으로 제작되었습니다.
