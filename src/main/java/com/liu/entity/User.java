package com.liu.entity;


import org.springframework.web.multipart.MultipartFile;

public class User {
	  	private Integer userId;

	    private String userName;//�û���

	    private String userPass;//�û�����

	    private String userNickname;//�û���ʾ��
	    
	    private String	pageNickname;//ҳ����ʾ��
	    
	    private String userSignature;//�û�����ǩ��

	    private String userEmail;//�û�����

	    private String avatarPath;//ͷ��·��

	    private MultipartFile avatarImage;//ͷ��
	    
	    private String markdownProfile;//markdown���
	    
	    private String htmlProfile;//html���
	    
	    private String personTag;//���˱�ǩ
	    
	    private String[] tags;//�����˱�ǩ�ָ�
		public Integer getUserId() {
			return userId;
		}

		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getUserPass() {
			return userPass;
		}

		public void setUserPass(String userPass) {
			this.userPass = userPass;
		}

		public String getUserNickname() {
			return userNickname;
		}

		public void setUserNickname(String userNickname) {
			this.userNickname = userNickname;
		}

		public String getPageNickname() {
			return pageNickname;
		}

		public void setPageNickname(String pageNickname) {
			this.pageNickname = pageNickname;
		}


		public String getUserSignature() {
			return userSignature;
		}

		public void setUserSignature(String userSignature) {
			this.userSignature = userSignature;
		}

		public String getUserEmail() {
			return userEmail;
		}

		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}


		public String getAvatarPath() {
			return avatarPath;
		}

		public void setAvatarPath(String avatarPath) {
			this.avatarPath = avatarPath;
		}

		public MultipartFile getAvatarImage() {
			return avatarImage;
		}

		public void setAvatarImage(MultipartFile avatarImage) {
			this.avatarImage = avatarImage;
		}

		public String getMarkdownProfile() {
			return markdownProfile;
		}

		public void setMarkdownProfile(String markdownProfile) {
			this.markdownProfile = markdownProfile;
		}

		public String getHtmlProfile() {
			return htmlProfile;
		}

		public void setHtmlProfile(String htmlProfile) {
			this.htmlProfile = htmlProfile;
		}

		public String getPersonTag() {
			return personTag;
		}

		public void setPersonTag(String personTag) {
			this.personTag = personTag;
		}

		public String[] getTags() {
			return tags;
		}

		public void setTags(String[] tags) {
			this.tags = tags;
		}

	    
}
