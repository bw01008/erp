select user(), database();

select * from title;
select * from department;
select * from employee;


-- 모두 검색
select empno, empname, title as title_no, manager as manager_no ,salary,dept as deptNO from employee;

-- 해당직책을 가지고 있는 사원목록을 검색
select empno, empname from employee e join title t on e.title = t.tno where tno = 3;

update department set deptName = '비상', floor = 11 where deptNo = 4;


select * from title;
update title set tname = '인턴', tno = 6 where tno = 7;

-- 해당부서에 속하는 사원목록을 검색
select empno, empname from employee e join department d on e.dept = d.deptNo where dept = 1;


select * from employee;

select * from employee where empno = 1003;

select * from employee;

select empno, empname from employee e join title t on e.title = t.tno where tno = 1;