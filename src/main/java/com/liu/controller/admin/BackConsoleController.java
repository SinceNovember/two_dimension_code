package com.liu.controller.admin;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.liu.entity.Article;
import com.liu.entity.Comment;
import com.liu.entity.UserLog;
import com.liu.lucene.ArticleIndex;
import com.liu.service.ArticleService;
import com.liu.service.CommentService;
import com.liu.service.UserLogService;


 /** 
 * @ClassName: BackConsoleController 
 * @author: lyd
 * @date: 2019��4��17�� ����2:46:49 
 * @describe:��̨����̨������
 */
@Controller
@RequestMapping("/admin")
public class BackConsoleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentServie;
	@Autowired
	private UserLogService userLogService;
	ArticleIndex articleIndex=new ArticleIndex();//�������������ڼ�������
	/**
	 * 
	* @Title: console  
	* @Description:��ת������̨����
	 */
	@RequestMapping("/console")
	public ModelAndView console()
	{
		ModelAndView modelAndView=new ModelAndView();
		HashMap<String, Object>map=new HashMap<String, Object>();
		map.put("start", 0);
		map.put("pageSize", 8);
		List<Article> articles=articleService.listAdminArticle(map);
		List<UserLog> logs=userLogService.listLog(map);
		List<Comment> comments=commentServie.listRecentComment(map);//�������������
		modelAndView.addObject("comments", comments);
		modelAndView.addObject("commentCount", comments.size());
		modelAndView.addObject("recentArticles", articles);
		modelAndView.addObject("logs", logs);
		modelAndView.setViewName("/admin/console");
		return modelAndView;
	}
	/**
	 * 
	* @Title: search  
	* @Description:��̨����ҳ��
	 */
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam(value="q",required=false)String q,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		System.out.println(q);
		ModelAndView modelAndView=new ModelAndView();
		if(q!="")
		{
		List<Article> articleIndexList=articleIndex.searchArticle(q,1);//���в�ѯ����Ϊ1����Ϊ��̨��������
		modelAndView.addObject("articleIndexList", articleIndexList);
		modelAndView.addObject("resultCount",articleIndexList.size());
		}
		modelAndView.setViewName("/admin/search");
		return modelAndView;
	}

}
