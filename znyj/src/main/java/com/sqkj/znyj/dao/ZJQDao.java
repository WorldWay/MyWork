package com.sqkj.znyj.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sqkj.znyj.model.ZJQ;

@Repository
public interface ZJQDao {

	public List<ZJQ> getZJQList(Map<String, Object> map);

	public ZJQ getZJQById(Map<String, Object> map);

	public int getZJQCount(Map<String, Object> map);

	public int addZJQ(Map<String, Object> map);

	public int updateZJQ(Map<String, Object> map);

	public int deleteZJQ(Map<String, Object> map);

}
