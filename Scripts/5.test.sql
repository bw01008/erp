select user(), database();

select * from title;
select * from department;
select * from employee;
select *  from emp_detail;

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




-- pass 길이 확인(비밀번호 password char(41))
-- 단방향 함수(Hash:MD5)
-- 해쉬함수의 특징은 키값을 찾아내는것이 힘들다. 결과값을 동일하지만 찾아가기가 힘들다.
-- 길이 상관없이 무조건 길이 41개로 출력된다.
select password ('aaa'), length(password ('*A02AA727CF2E8C5E6F07A382910C4028D65A053A')) from dual;	-- 해쉬함수(단방향함수)


/*
 * empno    INT        	NULL COMMENT '사원번호', -- 사원번호
	pic      LONGBLOB   	NULL COMMENT '증명사진', -- 증명사진
	gender   TINYINT(1) 	NULL COMMENT '성별', -- 성별
	hiredate DATETIME       NULL COMMENT '입사일', -- 입사일
	pass     CHAR(41)  		NULL COMMENT '비밀번호' -- 비밀번호 --비밀번호 타입의 경우 char타입의 41글자로 설정해준다.*/
INSERT INTO erp.emp_detail
(empno, pic, gender, hiredate, pass)
VALUES(?, ?, ?, ?, ?);

select empno, pic, gender, hiredate, pass from emp_detail;

delete from emp_detail where empno = 1003;
update emp_detail set gender = true where empno = 1003;

-- new EmployeeDetail(1003, true, new Date(), "1234", getImage("noimage.jpg"));
update emp_detail set empno = 1003 pic = ?, gender = ?, hiredate = ? where = 1003";


