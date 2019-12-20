package com.supergo.common.pojo.es;
public class TypeCountResponseVo implements java.io.Serializable{
	 
	
	private static final long serialVersionUID = 3266903170000232910L;
	private String id;
	private String type;
	private int count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}