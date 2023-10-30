![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/216b93fe-ead9-489e-b36f-e74362a1bf09)


# SNS_Feed_Service
- 사용자의 SNS 피드를 모아서 볼 수 있는 사이트

# 목차
- [개요](#개요)
- [Skils](#Skils)
- [프로젝트 진행 및 이슈 관리](#프로젝트-진행-및-이슈-관리)
- [구현 과정(설계 및 의도)](#구현-과정(설계-및-의도))
- [TIL 및 회고](#TIL-및-회고)
- [참여자](#참여자)

## 개요
- 본 서비스는 유저 계정의 해시태그를 기반으로 `인스타그램`, `스레드`, `페이스북`, `트위터` 등 복수의 SNS에 게시된 게시물 중 유저의 해시태그가 포함된 게시물들을 하나의 서비스에서 확인할 수 있는 통합 Feed 어플리케이션 입니다.
- 이를 통해 본 서비스의 고객은 하나의 채널로 유저`예시 : "#dani”`또는 브랜드`"예시 : #danishop”` 의 SNS 노출 게시물 및 통계를 확인할 수 있습니다.

## Skils 

- 언어 및 프레임워크: Java 17, Spring Boot 3.0
- 데이터베이스: MySQL
- 라이브러리 : Java-Mail-Sender, Bcrypt, Query DSL, Swagger, JWT

## 프로젝트 진행 및 이슈 관리

= [Notion](https://www.notion.so/1-SNS-Feed-5016005a9778436288ccb520dfabfa31)

## 구현 과정(설계 및 의도) - 작성중

### 회원가입 & 로그인

![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/8cb32c42-b565-44dc-b9b1-743eddb8ed42) 

#### 회원가입
- ID/PW/Email로 회원가입 요청 및 처리
  - PW 제약 조건 3가지 적용 및 회원 Data 생성
    - [상세 설명 및 코드 - PR 바로가기](https://github.com/Teemo-Wanted/SNS_Feed_Service/pull/11)
  - Email로 인증 코드 6자리 발송
    - [상세 설명 및 코드 - PR 바로가기](https://github.com/Teemo-Wanted/SNS_Feed_Service/pull/18)

#### 로그인

## TIL 및 회고

- PR을 꼼꼼히 확인하여 개선할 수 있는 부분에 대해 의견을 나눌 좋은 기회였다.
- 개발하며 겪은 에러나 구현 방법에 대한 고민, 참고 자료 등을 공유하며 본인이 맡은 기능 외에도 다른 부분까지 학습할 수 있도록 기회를 제공할 좋은 기회였다.

## 참여자 

- 김태형 : 매 요청마다 JWT 필터 적용
- 박철현 : 회원가입 및 로그인
- 정수현 : 게시물 목록 조회
- 이윤나 : 게시물 상세 조회
- 심형철 : 통계 정보 조회
