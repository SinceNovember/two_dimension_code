package com.liu.entity;

import java.util.Date;

public class UserLog {
		private Integer id;      //��־���
	    private Date time;    //ʱ��
	    private String type;    //����
	    private String detail;  //����
	    private String ip;      //ip��ַ

		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Date getTime() {
			return time;
		}
		public void setTime(Date time) {
			this.time = time;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDetail() {
			return detail;
		}
		public void setDetail(String detail) {
			this.detail = detail;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
	    
}
