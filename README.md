![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/216b93fe-ead9-489e-b36f-e74362a1bf09)


# SNS_Feed_Service
- 사용자의 SNS 피드를 모아서 볼 수 있는 사이트

# 목차
- [개요](#개요)
- [Skills](#skills)
- [프로젝트 진행 및 이슈 관리](#프로젝트-진행-및-이슈-관리)
- [API Swagger Docs](#api-swagger-docs)
- [Flow Chart](#flow-chart)
- [TIL(Today I Learn)](#til)
- [회고](#회고)
- [참여자](#참여자)

## 개요

- 본 서비스는 유저 계정의 해시태그를 기반으로 `인스타그램`, `스레드`, `페이스북`, `트위터` 등 복수의 SNS에 게시된 게시물 중 유저의 해시태그가 포함된 게시물들을 하나의 서비스에서 확인할 수 있는 통합 Feed 어플리케이션 입니다.
- 이를 통해 본 서비스의 고객은 하나의 채널로 유저`예시 : "#dani”`또는 브랜드`"예시 : #danishop”` 의 SNS 노출 게시물 및 통계를 확인할 수 있습니다.

## Skills

- 언어 및 프레임워크: Java 17, Spring Boot 3.0
- 데이터베이스: MySQL
- 라이브러리 : Java-Mail-Sender, Bcrypt, Query DSL, Swagger, JWT

## 프로젝트 진행 및 이슈 관리

- 매일 Discord 팀 회의를 통해 이슈 및 진행 상황 공유
    - 기능 구현 정도, 요구사항 분석 등
    - 월, 수, 금 : 09:00 ~ 10:00
    - 화, 목 : 17:00 ~ 18:00
- 팀 Notion 페이지를 활용해 개발 하며 학습한 내용 공유

= [Notion](https://www.notion.so/1-SNS-Feed-5016005a9778436288ccb520dfabfa31?pvs=21)

## API Swagger Docs

![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/1f2a3a6f-200c-4d2b-ba47-f24abfa81f96)

## Flow Chart

### 회원가입 & 로그인

![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/8cb32c42-b565-44dc-b9b1-743eddb8ed42) 

- ID/PW/Email로 회원가입 요청 및 JWT 발급
    - PW 제약 조건 3가지 적용 및 회원 Data 생성
        - 회원가입 기능 구현, PW 제약조건 3가지 설정
        - [상세 설명 및 코드 - PR 바로가기](https://github.com/Teemo-Wanted/SNS_Feed_Service/pull/11)
    - Email로 인증 코드 6자리 발송 및 JWT 발급 처리
        - 최초 로그인 시 인증코드 인증 필요
        - [상세 설명 및 코드 - PR 바로가기](https://github.com/Teemo-Wanted/SNS_Feed_Service/pull/18)

### 사용자 인증이 필요한 기능 동작 전 JWT 인증 방식

![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/32bf47f7-76a1-4d14-86d5-b43fb41af37b)

- 인증이 필요한 기능
    - 회원가입 / 로그인(JWT 발급)
- JWT를 활용한 화이트리스트 인증 방식 채택
    - 사용자 인증이 필요한 모든 요청 헤더에는 **JWT를 반드시 포함**해야 함
    - JWT가 유효한 토큰인지 확인
    - DB에 해당 사용자에 저장된 JWT가 맞는지 확인**(Refresh Token 방식이 아닌 화이트리스트 방식)**
        - JWT의  유효기간이 남았을 경우 탈취되었을 시 남은 일부 시간은 해당 사용자로 위장하여 사이트를 이용할 수 있는 단점 보안
        - 이러한 단점을 보완하였으나 매 요청마다 사용자에 할당된 JWT가 맞는지 확인해야 하기에 **DB에 접속하는 오버헤드 발생**
        - **Redis를 활용한 단점 보완 리팩토링(예정)**
    - [상세 설명 및 코드 - 인터셉터 구현 PR 바로가기](https://github.com/Teemo-Wanted/SNS_Feed_Service/pull/30)
    - [상세 설명 및 코드 - Swagger 문서 수정 및 예외처리 수정 PR 바로가기](https://github.com/Teemo-Wanted/SNS_Feed_Service/pull/32)

### 게시물 목록 조회

![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/9aa80eaf-96e5-4dd9-ac2d-42650ab9c7be)


- (JWT 인증 필요) 게시물 조회
    - SNS Feed에서 게시물을 사용자가 원하는 조건에 맞게 조회할 수 있습니다.

### 게시물 상세 조회

![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/e48b8c2d-60ad-4987-937e-663c0fba4f3d)

- (JWT 인증 필요) 게시물 상세 조회
    - SNS Feed에 있는 게시물 상세 내용을 확인합니다.
    - 조회 시 조회수는 1씩 계속 증가합니다.
    - 

### 게시물 좋아요, 공유

![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/e3c93956-ebb6-48cd-8ea8-d4eed947d669)

- (JWT 인증 필요) 게시물 좋아요, 공유
    - 실제 SNS 서버로 POST 요청을 보내고 성공 시 좋아요 또는 공유 수를 1 증가 시킵니다.
    - 실제 SNS 서버의 API 서버가 아닌 가상의 주소로 POST 요청을 보내기 때문에, 실패 응답일 경우에도 좋아요 또는 공유 수가 증가되도록 구현하였습니다.

### 통계

![image](https://github.com/Teemo-Wanted/SNS_Feed_Service/assets/126079049/7fe79d2c-c434-4429-afba-112a25eaf5bd)

- (JWT 인증 필요) 통계 정보 조회 기능 구현
    - 특정 해시태그 일자별, 시간별 게시물 갯수 통계 정보 확인 기능 구현
 
## 회고

- 박철현 : 개발하며 겪은 에러나 구현 방법에 대한 고민, 참고 자료 등을 공유하며 본인이 맡은 기능 외에도 다른 부분까지 학습할 수 있도록 기회를 제공할 좋은 기회였다.
- 이윤나 : 팀원들과 함께 프로젝트를 수행해 가면서 협업에서 중요하게 신경 써야 하는 것들에 대해 깨닫고 실천해볼 수 있는 좋은 기회였고, 팀원들의 도움으로 내가 부족한 부분이 무엇인지 파악하며 채울 수 있었다.
- 심형철: 팀원들의 코드를 살펴보며 새로운 부분들을 학습하고 정리하는 경험을 쌓았습니다.

## TIL

|작성자 | 키워드 | 링크 |
| :-----------: | :----: | :----: |
| **정수현** | QueryDSL 사용 | queryDSL (+SNS project 코드 설명) (https://www.notion.so/queryDSL-SNS-project-3a8706a3ba0441ea91a660352814755b?pvs=21)  |
| **박철현**| Async 비동기 멀티스레딩 | https://www.notion.so/e3c46a83efd144f3a0f270e46e09790d?pvs=21 |
| **이윤나** | WebClient 사용 | WebClient로 외부 API 요청하기 (https://www.notion.so/WebClient-API-8a8582bb1e37487d8c58296e7f6811a9?pvs=21)  |

## 참여자

- [김태형](https://github.com/johan1103) : 매 요청마다 JWT 인터셉터 적용
- [박철현](https://github.com/CheorHyeon) : 회원가입 및 로그인
- [정수현](https://github.com/walwaljj) : 게시물 목록 조회
- [이윤나](https://github.com/yoonnable) : 게시물 상세 조회 및 좋아요, 공유
- [심형철](https://github.com/HyungcheolSim) : 통계 정보 조회
