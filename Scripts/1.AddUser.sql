-- 계정 권한 부여
	
 grant all
 		on erp.* 
		to 'user_erp'@'localhost'
		identified by 'rootroot';
		
-- file권한 (backup, load) --root만 권한 부여 가능
grant File
 on *.*
 to'user_erp'@'localhost';