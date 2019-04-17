package com.liu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.liu.Utils.ResolveToc;
import com.liu.Utils.ResponseUtil;
import com.liu.entity.Article;
import com.liu.entity.Comment;
import com.liu.entity.Page;
import com.liu.entity.PageBean;
import com.liu.entity.Topic;
import com.liu.lucene.ArticleIndex;
import com.liu.service.ArticleService;
import com.liu.service.CommentService;
import com.liu.service.PageService;

 /** 
 * @ClassName: ArticleController 
 * @author: lyd
 * @date: 2019��4��17�� ����1:42:47 
 * @describe:���¿�����
 */
@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private PageService pageService;
	ResolveToc resolveToc=new ResolveToc();//������ȡ���¼����
	ArticleIndex articleIndex=new ArticleIndex();//��������������
	/**
	 * 
	* @Title: articleByPage  
	* @Description: ��ҳ��ȡ����
	 */
	@RequestMapping("/page/{nowPage}")
	public ModelAndView articleByPage(@PathVariable("nowPage")Integer nowPage)
	{
		
		Integer pageSize=5;//ÿҳ5������
		ModelAndView modelAndView=new ModelAndView();
		Map<String, Object> map=new HashMap<String, Object>();
		int articleCount=articleService.listArticle(map).size();//��ȡ��������
		PageBean pageBean=new PageBean(nowPage, pageSize);
		map.put("start", pageBean.getPage());
		map.put("pageSize", pageBean.getPageSize());
		List<Article> articles=articleService.listArticle(map);
		for(Article article:articles)
		{
			article.setSummary(resolveToc.summary(article.getHtmlContent()));//�������µļ��
		}
		int totalPage=(int)Math.ceil(articleCount*1.0/pageSize);//��ȡ�ܵ�ҳ��
		Page page=pageService.getPage(1);//��ȡ��ҳ�ñ�ǩ��ͼƬ
		modelAndView.addObject("articles", articles);
		modelAndView.addObject("firsttotalPage", totalPage);
		modelAndView.addObject("firstnowPage", nowPage);
		modelAndView.addObject("page", page);
		modelAndView.setViewName("/home/index");
		return modelAndView;
		
	}
	/**
	 * 
	* @Title: articleByAid  
	* @Description: ��ȡ���µ���ϸ����
	 */
	@RequestMapping("/{aid}")
	public ModelAndView articleByAid(@PathVariable("aid")Integer aid)
	{

		ModelAndView modelAndView=new ModelAndView();
		Article preArticle=articleService.getPreArticle(aid);//��ǰ���µ���һƪ
		Article nextArticle=articleService.getNextArticle(aid);//��ǰ���µ���һƪ
		Article article=articleService.getArticleByAid(aid);
		List<Topic> tocs=resolveToc.parse(article.getHtmlContent());//��ȡ���µ�Ŀ¼
		Article article1=new Article();
		article1.setArticleId(aid);
		article1.setArticleViewCount(article.getArticleViewCount()+1);//������+1
		if(articleService.updateArticle(article1)!=null)
		{
			article.setArticleViewCount(article.getArticleViewCount()+1);
		}
		List<Comment> comments=commentService.getCommentByAid(aid);
		for(Comment comment:comments)
		{
			comment.setChildComment(commentService.getChildComment(comment.getCommentId()));//��ȡ�����µ�����
		}
		Page page=pageService.getPage(2);//��ȡ����ҳ�ñ�ǩ��ͼƬ
		modelAndView.addObject("article", article);
		modelAndView.addObject("tocs", tocs);
		modelAndView.addObject("preArticle", preArticle);
		modelAndView.addObject("nextArticle", nextArticle);
		modelAndView.addObject("comments", comments);
		modelAndView.addObject("commentCount", comments.size());
		modelAndView.addObject("page", page);
		modelAndView.setViewName("/home/detail");
		return modelAndView;
	}
	
	//����ȫ��
	/**  
	* @Title: search  
	* @Description: ��������
	*/  
	@RequestMapping("/search")
	public String search(@RequestParam(value="q",required=false)String q,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		if(q!="")
		{
		JSONObject jsonObject=new JSONObject();
		List<Article> articleIndexList=articleIndex.searchArticle(q,0);//����������ݽ��м���
		jsonObject.put("articleIndexList", articleIndexList);
		ResponseUtil.write(response, jsonObject);
		}
		return null;
	}
}
