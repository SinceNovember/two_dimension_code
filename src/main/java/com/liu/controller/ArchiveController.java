package com.liu.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.liu.entity.Article;
import com.liu.entity.ArticleCategoryRef;
import com.liu.entity.ArticleTagRef;
import com.liu.entity.Category;
import com.liu.entity.Page;
import com.liu.entity.Tag;
import com.liu.service.ArticleCategoryRefService;
import com.liu.service.ArticleService;
import com.liu.service.ArticleTagRefService;
import com.liu.service.CategoryService;
import com.liu.service.PageService;
import com.liu.service.TagService;

@Controller
public class ArchiveController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCategoryRefService articleCategoryRefService;
	@Autowired
	private ArticleTagRefService articleTagRefService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private TagService tagService;
	@Autowired
	private PageService pageService;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping("/archives")
	public ModelAndView archive()
	{
		ModelAndView modelAndView=new ModelAndView();
		Map<String, Object>map=new HashMap<String, Object>();
		List<Article> articles=articleService.listArticle(map);
		Page page=pageService.getPage(3);
		modelAndView.addObject("archiveType", "����");
		modelAndView.addObject("archiveTitle",  articles.size());
		modelAndView.addObject("articles", articles);
		modelAndView.addObject("articleCount", articles.size());
		modelAndView.addObject("page", page);
		modelAndView.setViewName("/home/archives");
		return modelAndView;
		
	}
	@RequestMapping("/category/archives/{cid}")
	public ModelAndView category_archive(@PathVariable("cid")Integer cid)
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Article> articles=new ArrayList<Article>();
		List<ArticleCategoryRef> articleRefs=articleCategoryRefService.getArticleByCid(cid);
		for(ArticleCategoryRef articleRef:articleRefs)
		{
			articles.add(articleRef.getArticle());
		}
		Collections.sort(articles, new Comparator<Article>() {
 
			private String getBirthday(Article m) {
				if (m == null || m.getArticleCreateTime() == null)
					return null;
				try {
					return sdf.format(m.getArticleCreateTime());
				} catch (Exception e) {
					return null;
				}
			}
 
			public int compare(Article o2, Article o1) {
				String d1 = getBirthday(o1);
				String d2 = getBirthday(o2);
				if (d1 == null && d2 == null)
					return 0;
				if (d1 == null)
					return -1;
				if (d2 == null)
					return 1;
				return d1.compareTo(d2);
			}
		});

		Category category=categoryService.getCategoryByCid(cid);
		Page page=pageService.getPage(3);
		modelAndView.addObject("archiveType", "����");
		modelAndView.addObject("archiveTitle", category.getCategoryName());
		modelAndView.addObject("articles", articles);
		modelAndView.addObject("articleCount", articles.size()-1);
		modelAndView.addObject("page", page);
		modelAndView.setViewName("/home/archives");
		return modelAndView;
	}
	@RequestMapping("/tag/archives/{tid}")
	public ModelAndView tag_archive(@PathVariable("tid")Integer tid)
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Article> articles=new ArrayList<Article>();
		List<ArticleTagRef> articleRefs=articleTagRefService.getArticleByTid(tid);
		for(ArticleTagRef articleRef:articleRefs)
		{
			articles.add(articleRef.getArticle());
		}
		Collections.sort(articles, new Comparator<Article>() {
 
			private String getBirthday(Article m) {
				if (m == null || m.getArticleCreateTime() == null)
					return null;
				try {
					return sdf.format(m.getArticleCreateTime());
				} catch (Exception e) {
					return null;
				}
			}
 
			public int compare(Article o2, Article o1) {
				String d1 = getBirthday(o1);
				String d2 = getBirthday(o2);
				if (d1 == null && d2 == null)
					return 0;
				if (d1 == null)
					return -1;
				if (d2 == null)
					return 1;
				return d1.compareTo(d2);
			}
		});

		Tag  tag=tagService.getTagById(tid);
		Page page=pageService.getPage(3);
		modelAndView.addObject("archiveType", "��ǩ");
		modelAndView.addObject("archiveTitle", tag.getTagName());
		modelAndView.addObject("articles", articles);
		modelAndView.addObject("articleCount", articles.size()-1);
		modelAndView.addObject("page", page);
		modelAndView.setViewName("/home/archives");
		return modelAndView;
	}
}
