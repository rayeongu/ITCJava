# ITC(Inha Technical College) 2학년 1학기 JAVA프로그래밍 응용 기말 프로젝트

**팀원**

* 나영우
* 조민수

**개발환경**

* Eclipse IDE (WindowBuilder)
* MySQL

**DB 스키마 요약 (MySQL: itc\_db)**

* `student_info`: 학번(ID, PK), 비밀번호, 이름, 학과, 학년
* `timetable`: 고유번호(no, PK), 학번(ID), 학기, 교시, 요일, 과목명, 장소, 교수명, 이메일
* `note`: 고유번호(num, PK), 학번(ID), 유형(과제/노트), 제출기간, 내용, 제출여부

**프로젝트 구조**

```
src/
├── db/
│   └── DBConn.java                # DB 연결
├── gui/
│   ├── Timetable.java             # 시간표 메인화면
│   ├── StudentInfo.java           # 회원(학생)정보 조회 및 수정
│   └── login/
│       ├── Login.java             # 로그인
│       └── SignUp.java            # 회원가입
├── gui/course/
│   ├── AddCourseDialog.java       # 강의 추가
│   └── EditCourseDialog.java      # 강의 수정
├── gui/note/
│   ├── Note.java                  # 메모/과제 리스트
│   ├── AddNoteDialog.java         # 메모/과제 추가
│   └── EditNoteDialog.java        # 메모/과제 수정
└── images/
    └── (아이콘 등 리소스)
```

**기능 요약**

* 로그인 및 회원가입 기능 (중복 확인 포함)
* 학생 본인 정보 조회 및 비밀번호 변경 기능
* 시간표 조회, 강의 추가/수정/삭제 기능
* 강의별 메모 및 과제 작성, 수정 기능
* 제출여부/유형/기간 등 조건 검색 기능 포함





https://github.com/user-attachments/assets/08f9557c-95d6-4ca6-83c0-628a492000d0

