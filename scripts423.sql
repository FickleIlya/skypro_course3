select s.name, s.age, f.name faculty_name
from student s
left join faculty f on s.faculty_id = f.id;

select s.*, a.id avatar_id
from student s
inner join avatar a on a.student_id = s.id;