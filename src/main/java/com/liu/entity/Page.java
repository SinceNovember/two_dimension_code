package com.liu.entity;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @ClassName: Page 
 * @author: lyd
 * @date: 2019��4��17�� ����3:02:09 
 * @describe:ҳ��ʵ����
 */
public class Page {
	private Integer pageId;
	private Integer pageFlag;//1:��ҳ 2:����ҳ 3:�鵵ҳ 4:����ҳ 5:��ǩҳ 6:����ҳ
	private String pageTag;//ҳ���ǩ
	private String imagePath;//ͼƬ·��
	private MultipartFile pageImage;//���µı���ͼ
	public Integer getPageId() {
		return pageId;
	}
	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}
	public Integer getPageFlag() {
		return pageFlag;
	}
	public void setPageFlag(Integer pageFlag) {
		this.pageFlag = pageFlag;
	}
	public String getPageTag() {
		return pageTag;
	}
	public void setPageTag(String pageTag) {
		this.pageTag = pageTag;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public MultipartFile getPageImage() {
		return pageImage;
	}
	public void setPageImage(MultipartFile pageImage) {
		this.pageImage = pageImage;
	}

	
}
