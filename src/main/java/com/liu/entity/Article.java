package com.liu.entity;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public  class Article implements Comparable<Article>{
	
    private Integer articleId;//文章id

    private Integer articleUserId;//文章用户Id

    private String articleTitle;//文章标题
    
    private String htmlContent;//html类型得内容

    private String markdownContent;//markdown内容
    
    private Integer articleViewCount;//访问量

    private Integer articleCommentCount;//评论数

    private Date articleCreateTime;//创建时间

    private Date articleUpdateTime;//修改时间
    
    private String createYear;
    
    private Integer issueType;//发布得类型
    
    private Integer articleType;//文章类型
    
    private String summary;//文章简介

    private User user;//用户
    
    private MultipartFile articleImage;//文章背景图片
    
    private String articleImagePath;//背景路径

	private List<Tag> tagList;//其下所有标签

    private List<Category> categoryList;//其下所有分类

    private Integer[] articleTagId;//标签Id
    
    private Integer[] articleCategoryId;//分类id
    
	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getArticleUserId() {
		return articleUserId;
	}

	public void setArticleUserId(Integer articleUserId) {
		this.articleUserId = articleUserId;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getMarkdownContent() {
		return markdownContent;
	}

	public void setMarkdownContent(String markdownContent) {
		this.markdownContent = markdownContent;
	}

	public String getCreateYear() {
		return createYear;
	}

	public void setCreateYear(String createYear) {
		this.createYear = createYear;
	}

	public Integer getArticleViewCount() {
		return articleViewCount;
	}

	public void setArticleViewCount(Integer articleViewCount) {
		this.articleViewCount = articleViewCount;
	}

	public Integer getArticleCommentCount() {
		return articleCommentCount;
	}

	public void setArticleCommentCount(Integer articleCommentCount) {
		this.articleCommentCount = articleCommentCount;
	}

	public Date getArticleCreateTime() {
		return articleCreateTime;
	}

	public void setArticleCreateTime(Date articleCreateTime) {
		this.articleCreateTime = articleCreateTime;
	}

	public Date getArticleUpdateTime() {
		return articleUpdateTime;
	}

	public void setArticleUpdateTime(Date articleUpdateTime) {
		this.articleUpdateTime = articleUpdateTime;
	}

	public Integer getIssueType() {
		return issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}

	public Integer getArticleType() {
		return articleType;
	}

	public void setArticleType(Integer articleType) {
		this.articleType = articleType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MultipartFile getArticleImage() {
		return articleImage;
	}

	public void setArticleImage(MultipartFile articleImage) {
		this.articleImage = articleImage;
	}

	public String getArticleImagePath() {
		return articleImagePath;
	}

	public void setArticleImagePath(String articleImagePath) {
		this.articleImagePath = articleImagePath;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Integer[] getArticleTagId() {
		return articleTagId;
	}

	public void setArticleTagId(Integer[] articleTagId) {
		this.articleTagId = articleTagId;
	}

	public Integer[] getArticleCategoryId() {
		return articleCategoryId;
	}

	public void setArticleCategoryId(Integer[] articleCategoryId) {
		this.articleCategoryId = articleCategoryId;
	}

	public int compareTo(Article o) {
		// TODO Auto-generated method stub
		return articleCreateTime.compareTo(o.articleCreateTime);
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
