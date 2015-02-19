DELIMITER $$

drop function if exists `testbankkey` $$

/*
 * Gets the bank key for the given test
 */

create function `testbankkey` ( 
	v_testkey varchar(250) 
) returns bigint
sql security invoker
begin
	
  declare v_bankkey bigint;
    
  select _efk_itembank into v_bankkey 
  from tblsetofadminsubjects S, tblsetofadminitems A, tblitem I
  where S._key = v_testkey and A._fk_adminsubject = S._key and I._key = A._fk_item
  limit 1;

  if (v_bankkey is null) then 
		select efk_itembank into @bankkey 
        from tblsetofadminsubjects S, tblsetofadminitems A, tblitem I
        where S.virtualtest = v_testkey and A._fk_adminsubject = S._key and I._key = A._fk_item
		limit 1;
	end if;

  return  v_bankkey;
  
end $$

DELIMITER ;