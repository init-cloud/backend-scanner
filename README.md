
<div align="center">  
 <img src="https://raw.githubusercontent.com/init-cloud/backend-scanner/main/docs/logo.png" width="500">

# Init Cloud IaC 정적 스캔 및 시각화 도구
</div>
 
## 개요
많은 정적 스캔 도구들은 국내 클라우드 벤더를 대상으로 하는 정적 스캔을 지원하지 않습니다.

액세스 키와 비밀 키를 통한 클라우드 환경의 동적 점검은 많은 시간을 필요로 합니다.  
또한, 클라우드 인프라가 이미 프로비저닝 되었다면 이를 수정함에 많은 비용이 발생할 것입니다.  
이에, "Shift Left"를 모토로 인프라에 대한 사전점검을 수행하기 위해 개발하였습니다.

대표적인 국내 클라우드 벤더 NCloud에서 출발하여 글로벌 벤더 AWS에 대한 정적 스캔과 코드 기반 시각화를 지원합니다.  
또한, ISMS-P 기반의 컴플라이언스 매핑 보고서를 제공하여 사용 환경이 컴플라인스를 준수하는지 간단하게 확인할 수 있습니다.

## 다음의 클라우드 환경 및 IaC을 지원합니다.
* AWS : 명실상부한 클라우드 최대 강자로 넓은 범용성을 지닙니다. 
* NCP : 국내 클라우드의 높은 점유율을 가지며 2021년 국내 클라우드 점유율 3위에 위치하기도 하였습니다.
* Terraform : 코드형 인프라의 최대 점유율을 가집니다.

## 산출물

### 시연 영상
[유튜브](https://www.youtube.com/watch?v=xpXQ8eqIszg) 

### 대시보드 예시
<div align="center"> 
 <img src="https://github.com/init-cloud/backend-scanner/blob/main/docs/board.png?raw=true">
  </div>
 
### 시각화 예시 
 <div align="center"> 
 <img src="https://github.com/init-cloud/backend-scanner/blob/main/docs/infra_aws.png">
  </div>
 
### 보고서 예시
  <div align="center"> 
 <img src="https://github.com/init-cloud/backend-scanner/blob/main/docs/report.png">
  </div>

## 사용한 Git 브랜치 전략
* Main : 공식 릴리즈를 위한 브랜치
* Demo : 데모 시연을 위한 브랜치, 별도 시연 버전을 관리
* Dev : 개발 사항 관리를 위한 브랜치, Main으로 병합
* Feature : 특정 기능 개발을 위한 브랜치, Dev로 병합
* Fix : 긴급 상황에서 수정을 위한 브랜치, Main으로 병합

## Install
[한글 설치 가이드](https://github.com/init-cloud/backend-scanner/blob/main/docs/Install_Korean.md)  
[English Install Guide](https://github.com/init-cloud/backend-scanner/blob/main/docs/Install_English.md)

## 레이어드 아키텍처 기반 Rest API
<img src="https://github.com/init-cloud/backend-scanner/blob/main/docs/architecture.png?raw=true">

- 앱 서버에서 상태를 관리하지 않도록 클라이언트의 요청과 DB 기반으로 데이터를 가공하도록 했습니다.
- JWT를 이용한 토큰 기반 인증으로 앱 서버의 상태와 상관없이 인증을 수행하도록 했습니다.

## 개발 명세
- 패키지를 자원을 기반으로 구분했습니다. 궁극적으로 도메인 기반의 아키텍처를 지향하도록 분리했습니다.
- Swagger를 통해 API를 명세했습니다.
- ERD Cloud를 통해 DB 스키마를 명세했습니다.

## 참여 인원

### 1차 프로젝트
<div align="center">

| PM, 보안연구                        | 보안연구                            | FE                           | BE | 오픈소스 연구                            |
|---------------------------------|---------------------------------|---------|------------------------------------|------------------------------------|
| [배경석](https://github.com/cand0) | [차유담](https://github.com/nicedammy) | [임태인](https://github.com/taeng0204) | [정금종](https://github.com/Floodnut)  | [박병제](https://github.com/pj991207) |

</div>

### 2차 사업화

<div align="center">

| PM                         | 사업 기획                            | 사업 기획, FE                         | FE                              | BE      | BE                                 |
|---------------------------------| --- |-----------------------------------|---------------------------------|---------|------------------------------------|
| [차유담](https://github.com/nicedammy) | [임태인](https://github.com/taeng0204) | [박병제](https://github.com/pj991207) | [이본영](https://github.com/FoO-511) | [정금종](https://github.com/Floodnut) | [최영해](https://github.com/0-hae) | 

</div>
    
## 백엔드 주요 개발 기록
[Github OAuth의 적용](https://floodnut.tistory.com/88)  
[Azure 앱서비스 기반 외부 인가서버](https://floodnut.tistory.com/92)  
[Swagger 필수 속성이 없을 때의 NPE 처리](https://floodnut.tistory.com/95)  
[DB 인덱스 적용을 통한 성능 향상](https://floodnut.tistory.com/97)  
[리팩터링 과정에서의 DTO 수정](https://floodnut.tistory.com/103)  

