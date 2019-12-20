package com.supergo.common.pojo.es;

import java.util.List;
import java.util.Map;

/**
 * 查询返回使用的vo
 */
public class SearchResponseVo implements java.io.Serializable{
	 
	private static final long serialVersionUID = -8051360974992649703L;
	private Long dataSize;
	private List<Map<String, Object>> goodsList;
	private List<List<TypeCountResponseVo>> typeCountList;
	
	public Long getDataSize() {
		return dataSize;
	}
	public void setDataSize(Long dataSize) {
		this.dataSize = dataSize;
	}
	public List<Map<String, Object>> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<Map<String, Object>> goodsList) {
		this.goodsList = goodsList;
	}
	public List<List<TypeCountResponseVo>> getTypeCountList() {
		return typeCountList;
	}
	public void setTypeCountList(List<List<TypeCountResponseVo>> typeCountList) {
		this.typeCountList = typeCountList;
	}
}

