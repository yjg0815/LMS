insert into course (created_at, updated_at, classification, course_id, credits, dept_name, title)
values (now(), now(), 'required major course', '11708', '3', 'CSE', 'software engineering'),
       (now(), now(), 'major', '54617', '3', 'CSE', 'multicore computing'),
       (now(), now(), 'basic major course', '11934', '3', 'CSE', 'numeric analysis'),
       (now(), now(), 'general education course', '40553', '3', 'ALL', 'plants and culture'),
       (now(), now(), 'required major course', '13601', '3', 'CSE', 'algorithm'),
       (now(), now(), 'major', '52321', '3', 'CSE', 'compiler')
;
insert into section (course_id, year, semester, upload_day, created_at, updated_at, sec_num)
VALUES (19, '2024', 'SPRING', 'TUE', now(), now(), 1),
       (19, '2024', 'SPRING', 'WED', now(), now(), 2),
       (20, '2024', 'SPRING', 'MON', now(), now(), 1),
       (21, '2024', 'SPRING', 'THU', now(), now(), 1),
       (21, '2024', 'SPRING', 'FRI', now(), now(), 2),
       (22, '2024', 'SPRING', 'TUE', now(), now(), 1),
       (23, '2024', 'SPRING', 'TUE', now(), now(), 1),
       (23, '2024', 'SPRING', 'FRI', now(), now(), 2),
       (24, '2024', 'SPRING', 'MON', now(), now(), 1),
       (24, '2024', 'SPRING', 'WED', now(), now(), 1),
       (24, '2024', 'SPRING', 'THU', now(), now(), 1)
;
insert into role (name)
VALUES ('Student'),
       ('Instructor');

update section
set sec_num = 2
where id = 21;

update section
set sec_num = 3
where id = 22;

insert into notification (created_at, updated_at, description, title, section_id)
values (now(), now(), 'Term Project가 업로드 되어 있습니다.

과제 및 평가 탭을 이용해서 Term Project를 확인하기 바랍니다.

Mid Due: 5월 13일 월요일 오후 9시
Final Due: 6월 3일 월요일 오후 9시 ', 'Term Project 안내', 12);

insert into learning(created_at, updated_at, end, start, week_num, section_id)
VALUES (now(), now(), '2024-03-15 11:59', '2024-03-03 00:00', '1', 12);

insert into assignment(created_at, updated_at, deadline, description, point, title, sec_id)
VALUES (now(), now(), '2024-06-03 21:00', '첨부 문서를 참조하기 바랍니다.', 30, 'Term Project', 12);

insert into notification (created_at, updated_at, description, title, section_id)
values (now(), now(), '기말고사 일정과 범위는 다음과 같습니다.



시험일시: 6월 18일(화) 오후 6시 ~ 오후 7시 30분

범위: 전범위 (중간고사 시험범위를 포함하여 금번학기 배운 내용 + 텀프로젝트 모두 포함)



지난 중간고사보다 1시간 당겨서 보는 것에 유의해주기 바랍니다.', '기말고사 일정과 범위', 12);

insert into learning(created_at, updated_at, end, start, week_num, section_id)
VALUES (now(), now(), '2024-03-21 11:59', '2024-03-10 00:00', '2', 12);
