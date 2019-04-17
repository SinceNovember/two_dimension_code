package com.liu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.liu.entity.Category;
import com.liu.entity.Page;
import com.liu.service.CategoryNodeService;
import com.liu.service.CategoryService;
import com.liu.service.PageService;

 /** 
 * @ClassName: CategoryController 
 * @author: lyd
 * @date: 2019��4��17�� ����1:49:21 
 * @describe:���������
 */
@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryNodeService categoryNodeService;
	@Autowired
	private PageService pageService;
	@RequestMapping("/categories")
	public ModelAndView categories()
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Category> categories=categoryNodeService.getAllParent();//��ȡ���еĸ�����ڵ㣬���а������ӽڵ�
		for(Category category1:categories){//���ø������������
			category1.setArticleCount(categoryService.getCategoryArticleCount(category1.getCategoryId()));
			for(Category category2:category1.getChildrenCategory())
				category2.setArticleCount(categoryService.getCategoryArticleCount(category2.getCategoryId()));
		}
		Integer categoryCount=categoryService.listCategory().size();
		Page page=pageService.getPage(4);//��ʾ����ҳ��ҳ��ͼƬ�ͱ�ǩ
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("categoryCount", categoryCount);
		modelAndView.addObject("page", page);
		modelAndView.setViewName("/home/categories");
		return modelAndView;			
	}
}
