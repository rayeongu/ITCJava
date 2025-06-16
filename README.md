#ITC(Inha Technical College) 2학년 1학기 JAVA프로그래밍 응용 기말 프로젝트

팀원
나영우
조민수

개발환경
Eclipse IDE + WindowBuilder
MySQL

DB 스키마 요약 (MySQL : itc_db)
student_info
학번(ID, PK), 비밀번호, 이름, 학과, 학년
timetable
고유번호(no, PK), 학번(ID), 학기, 교시, 요일, 과목명, 장소, 교수명, 이메일
note
고유번호(num, PK), 학번(ID), 유형(과제/노트), 제출기간, 내용, 제출여부

프로젝트 구조
src/
├── db/
│ └── DBConn.java                # DB연결
├── gui/
│ ├── Timetable.java             # 시간표
│ ├── StudentInfo.java           # 회원(학생)정보
│ └── login/
│ ├── Login.java                 # 로그인            
│ └── SignUp.java                # 회원가입
├── gui/course/
│ ├── AddCourseDialog.java       # 강의추가
│ └── EditCourseDialog.java      # 강의수정
├── gui/note/
│ ├── Note.java                  # 메모/과제 리스트
│ ├── AddNoteDialog.java         # 메모/과제 추가
│ └── EditNoteDialog.java        # 메모/과제 수정
└── images/
└── (아이콘 등 리소스)            # 로고 등 리소
