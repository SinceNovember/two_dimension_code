package com.liu.entity;

/**
 * 
 * @ClassName: Tag 
 * @author: lyd
 * @date: 2019��4��17�� ����3:02:39 
 * @describe:��ǩʵ��
 */
public class Tag {
    private Integer tagId;

    private String tagName;//��ǩ��
    
    private Integer articleCount;//������
    
    private String fontSize;//�����С
    
	private String fontColor;//������ɫ

	public Integer getTagId() {
		return tagId;
	}
	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public Integer getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(Integer articleCount) {
		this.articleCount = articleCount;
	}
    public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
}
