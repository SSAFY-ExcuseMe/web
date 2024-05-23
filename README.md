# 🏞 프로젝트 소개
- **🖥PJT명**: **Enjoy Trip Project**
- **🚶‍♂️단계**: **Web Vue.js PJT**
- **📅진행일자**: **2024.05.10**
- **👩‍💻팀원:** **김지원, 신민경**

# ✔ Vue.js 구현 사진
## 메인화면
## 회원가입

 
## 로그인
<img src = "docs/image2.png">
- ## localStorage에 저장 -> navbar에 반영
- ## cookie에 저장 -> ID 저장

 
# ⚙ 기능 명세서

- ## 회원

- 회원은 유저, admin으로 구성되어 있다.
- 유저는 아이디, 비밀번호 입력을 통해 로그인이 가능하다.
- 아이디 저장 버튼을 누르면 아이디를 쿠키에 저장할 수 있다.
- 유저의 회원가입은 아이디, 비밀번호, 이메일, 휴대폰번호 입력이 필요하다.
- admin으로 회원가입은 불가능하다.
- 마이페이지 수정에서 이메일, 휴대폰번호 수정이 가능하다.
- 마이페이지 ‘내가 작성한 글 페이지’를 통해 작성한 글에 대한 관리가 가능하다.

## 게시판

- 게시판은 제목, 내용으로 구성되어 있다.
- 게시판에 댓글 작성이 가능하다.
- 게시판은 수정 삭제가 가능하다.
- 댓글은 수정 삭제가 가능하다.
- 게시판에 좋아요를 누를 수 있다.
- 게시판에 조회수 조회가 가능하다.
- 게시판 상세보기에서 제목, 내용, 작성자(아이디), 작성 시간, 좋아요 수, 조회수, 댓글 확인이 가능하다.
- 댓글은 내용, 작성 시간, 좋아요 수로 구성되어 있다.
- 댓글 아이디는 익명이다.
- admin은 모든 게시글, 댓글에 대한 삭제가 가능하다.
- 게시판 리스트는 최신순, 좋아요순, 조회순으로 정렬이 가능하다.
- 게시판 리스트는 페이지네이션으로 구분되어 있다.

## 여행지

- 여행지는 지역별, 테마별 필터링이 가능하다.
- 여행지 이름을 기준으로 키워드 검색이 가능하다.
- 여행지는 이름, 사진, 유형, 주소, 연락처를 확인할 수 있다.

## 여행지 경로

- 여행지 경로는 최단거리로 추천해준다.
- 여행지를 선택할 시, 지도에서 핀으로 표시한다.
- 여행지를 선택하고 취소가 가능하다.
- 생성 버튼을 누르면 최종 경로를 확인할 수 있다.
- 최종 경로를 확인 후 여행지 경로를 저장할 때 경로 이름을 저장해야 한다.
- 최종 경로를 확인 할 때 최단 거리 경로가 선으로 맵에 표시된다.