package com.liu.entity;


public class ArticleTagRef {
		private Integer articleId;//����id
		
		private Integer tagId;//��ǩid
		
		private Article article;//��������
		
		public Integer getArticleId() {
			return articleId;
		}
		public void setArticleId(Integer articleId) {
			this.articleId = articleId;
		}
		public Integer getTagId() {
			return tagId;
		}
		public void setTagId(Integer tagId) {
			this.tagId = tagId;
		}
		public Article getArticle() {
			return article;
		}
		public void setArticle(Article article) {
			this.article = article;
		}

		
}
