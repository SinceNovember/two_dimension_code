package com.liu.entity;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Comment {
	  private Integer commentId;//����id

	    private Integer commentPid;//���۸���id

	    private String commentPname;//���۸�����

	    private Integer commentArticleId;//��������id

	    private String commentAuthorName;//������������

	    private String commentAuthorEmail;//��������
	    
	    private Integer commentLikeCount;//����ϲ����
	    
	    private MultipartFile commentAvatarImage;//����ͷ��
	    
	    private String commentAvatarPath;//ͷ��·��

	    private String commentContent;//��������

	    private String commentIp;//������ip
	    
	    private String originalContent;//���ۻظ���ԭ�ظ�����
	    
	    private Date commentCreateTime;//����ʱ��
	    
	    private List<Comment> childComment;//������

	    /**
	     * ��ɫ(����Ա1���ÿ�0)
	     */
	    private Integer commentRole;

	    /**
	     * �����ݿ��ֶ�
	     */
	    private Article article;

		public Integer getCommentId() {
			return commentId;
		}

		public void setCommentId(Integer commentId) {
			this.commentId = commentId;
		}

		public Integer getCommentPid() {
			return commentPid;
		}

		public void setCommentPid(Integer commentPid) {
			this.commentPid = commentPid;
		}

		public String getCommentPname() {
			return commentPname;
		}

		public void setCommentPname(String commentPname) {
			this.commentPname = commentPname;
		}

		public Integer getCommentArticleId() {
			return commentArticleId;
		}

		public void setCommentArticleId(Integer commentArticleId) {
			this.commentArticleId = commentArticleId;
		}

		public String getCommentAuthorName() {
			return commentAuthorName;
		}

		public void setCommentAuthorName(String commentAuthorName) {
			this.commentAuthorName = commentAuthorName;
		}

		public String getCommentAuthorEmail() {
			return commentAuthorEmail;
		}
		public void setCommentAuthorEmail(String commentAuthorEmail) {
			this.commentAuthorEmail = commentAuthorEmail;
		}

		public Integer getCommentLikeCount() {
			return commentLikeCount;
		}

		public void setCommentLikeCount(Integer commentLikeCount) {
			this.commentLikeCount = commentLikeCount;
		}

		public MultipartFile getCommentAvatarImage() {
			return commentAvatarImage;
		}

		public void setCommentAvatarImage(MultipartFile commentAvatarImage) {
			this.commentAvatarImage = commentAvatarImage;
		}

		public String getCommentAvatarPath() {
			return commentAvatarPath;
		}

		public void setCommentAvatarPath(String commentAvatarPath) {
			this.commentAvatarPath = commentAvatarPath;
		}

		public String getCommentContent() {
			return commentContent;
		}

		public void setCommentContent(String commentContent) {
			this.commentContent = commentContent;
		}

		public String getCommentIp() {
			return commentIp;
		}

		public void setCommentIp(String commentIp) {
			this.commentIp = commentIp;
		}

		public Date getCommentCreateTime() {
			return commentCreateTime;
		}

		public void setCommentCreateTime(Date commentCreateTime) {
			this.commentCreateTime = commentCreateTime;
		}

		public Integer getCommentRole() {
			return commentRole;
		}

		public void setCommentRole(Integer commentRole) {
			this.commentRole = commentRole;
		}

		public Article getArticle() {
			return article;
		}

		public void setArticle(Article article) {
			this.article = article;
		}

		public String getOriginalContent() {
			return originalContent;
		}

		public void setOriginalContent(String originalContent) {
			this.originalContent = originalContent;
		}

		public List<Comment> getChildComment() {
			return childComment;
		}

		public void setChildComment(List<Comment> childComment) {
			this.childComment = childComment;
		}


	    
	    
}
