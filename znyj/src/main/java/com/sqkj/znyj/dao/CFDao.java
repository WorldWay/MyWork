package com.sqkj.znyj.dao;

import java.util.Map;
import org.springframework.stereotype.Repository;
import com.sqkj.znyj.model.CFXX;

@Repository
public interface CFDao {

	public CFXX getQYCF(Map<String, Object> map);

	public int updateQYCF(Map<String, Object> map);

}
