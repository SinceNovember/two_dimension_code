package com.liu.entity;


public class ArticleCategoryRef {
		private Integer articleId;//����id
		
		private Integer categoryId;//����id
		
		private Article article;//��������
		
		public Integer getArticleId() {
			return articleId;
		}
		public void setArticleId(Integer articleId) {
			this.articleId = articleId;
		}
		public Integer getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(Integer categoryId) {
			this.categoryId = categoryId;
		}
		public Article getArticle() {
			return article;
		}
		public void setArticle(Article article) {
			this.article = article;
		}


		
}
