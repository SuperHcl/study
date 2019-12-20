package com.supergo.common.pojo.es;

/**
 * 查询使用的vo
 */
public class SearchRequestVo implements java.io.Serializable{
	 
	private static final long serialVersionUID = 1682986896598864270L;
	private String value;
	private String order;
	//品牌
	//网络模式
	//筛选条件
	private int sizÍe;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getSizÍe() {
		return sizÍe;
	}
	public void setSizÍe(int sizÍe) {
		this.sizÍe = sizÍe;
	}
}
