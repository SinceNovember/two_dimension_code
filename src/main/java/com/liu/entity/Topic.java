package com.liu.entity;

 /** 
 * @ClassName: Topic 
 * @author: lyd
 * @date: 2019��4��8�� ����6:03:54 
 * @describe:����������ӵ�еñ�ǩ
 */
public class Topic {
	String tocId; //��ǩid
	String tocName;	//��ǩ��
	Integer tocIndex;//��ǩ����
	public String getTocId() {
		return tocId;
	}
	public void setTocId(String tocId) {
		this.tocId = tocId;
	}

	public String getTocName() {
		return tocName;
	}
	public void setTocName(String tocName) {
		this.tocName = tocName;
	}
	public Integer getTocIndex() {
		return tocIndex;
	}
	public void setTocIndex(Integer tocIndex) {
		this.tocIndex = tocIndex;
	}
	
}
