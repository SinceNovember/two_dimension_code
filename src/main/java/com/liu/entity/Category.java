package com.liu.entity;

import java.util.List;

public class Category {
    private Integer categoryId;//����id

    private Integer categoryPid;//���ุ��id

    private String categoryName;//��������

    private String categoryDescription;//��������

    private Integer categoryOrder;//����˳��
    
    private List<Category> childrenCategory;//����������ӷ���

    private Category parentCategory;//����ø��׷���
	/**
     * ��������(�����ݿ��ֶ�)
     */
    private Integer articleCount;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCategoryPid() {
		return categoryPid;
	}

	public void setCategoryPid(Integer categoryPid) {
		this.categoryPid = categoryPid;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public Integer getCategoryOrder() {
		return categoryOrder;
	}

	public void setCategoryOrder(Integer categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	public Integer getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}

	public List<Category> getChildrenCategory() {
		return childrenCategory;
	}

	public void setChildrenCategory(List<Category> childrenCategory) {
		this.childrenCategory = childrenCategory;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

}
