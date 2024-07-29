insert into course (created_at, updated_at, classification, course_id, credits, dept_name, title)
values
    (now(), now(), 'required major course', '11708', '3', 'CSE', 'software engineering'),
    (now(), now(), 'major', '54617', '3', 'CSE', 'multicore computing'),
    (now(), now(), 'basic major course', '11934', '3', 'CSE', 'numeric analysis'),
    (now(), now(), 'general education course', '40553', '3', 'ALL', 'plants and culture'),
    (now(), now(), 'required major course', '13601', '3', 'CSE', 'algorithm'),
    (now(), now(), 'major', '52321', '3', 'CSE', 'compiler')
;
insert into section (course_id, year, semester, upload_day, created_at, updated_at, sec_num)
VALUES
    (19, '2024', 'SPRING', 'TUE', now(), now(), 1),
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
VALUES ('Student'), ('Instructor');

update section
set sec_num = 2
where id = 21;

update section
set sec_num = 3
where id = 22;
