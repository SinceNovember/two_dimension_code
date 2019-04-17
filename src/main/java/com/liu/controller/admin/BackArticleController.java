package com.liu.controller.admin;

import java.util.Date;
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

import com.liu.Utils.ManageLog;
import com.liu.Utils.ResponseUtil;
import com.liu.Utils.UploadUtil;
import com.liu.entity.Article;
import com.liu.entity.Category;
import com.liu.entity.PageBean;
import com.liu.entity.Tag;
import com.liu.lucene.ArticleIndex;
import com.liu.service.ArticleCategoryRefService;
import com.liu.service.ArticleService;
import com.liu.service.ArticleTagRefService;
import com.liu.service.CategoryNodeService;
import com.liu.service.CategoryService;
import com.liu.service.CommentService;
import com.liu.service.TagService;
import com.liu.service.UserLogService;
import com.liu.service.UserService;

import net.sf.json.JSONObject;


 /** 
 * @ClassName: BackArticleController 
 * @author: lyd
 * @date: 2019��4��17�� ����2:21:27 
 * @describe:��̨���¿�����
 */
@Controller
@RequestMapping("/admin")
public class BackArticleController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CategoryNodeService categoryNodeService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ArticleCategoryRefService articleCategoryRefService;
	@Autowired
	private ArticleTagRefService articleTagRefService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private  UserLogService userLogService;
	public static ManageLog manageLog=new ManageLog();//������־����
	ArticleIndex articleIndex=new ArticleIndex();//�������������ڼ�������
	/**
	 * 
	* @Title: ListAll  
	* @Description: ��ҳ��ʾ����
	 */
	@RequestMapping("/articles/{nowPage}")
	public ModelAndView ListAll(@PathVariable("nowPage")Integer nowPage)
	{
		ModelAndView modelAndView=new ModelAndView();
		Integer pageSize=10;//ÿҳ��ʾ10ƪ����
		Map<String, Object> map=new HashMap<String, Object>();
		int articleCount=articleService.countArticle();//��ȡ��־����
		PageBean pageBean=new PageBean(nowPage, pageSize);
		map.put("start", pageBean.getPage());
		map.put("pageSize", pageBean.getPageSize());
		List<Article> articles=articleService.listAdminArticle(map);
		int totalPage=(int)Math.ceil(articleCount*1.0/pageSize);//��ȡ�ܵ�ҳ��
		modelAndView.addObject("firsttotalPage", totalPage);
		modelAndView.addObject("firstnowPage", nowPage);
		modelAndView.addObject("articles", articles);
		modelAndView.addObject("type", "articles");//�������ͱ�־
		modelAndView.setViewName("/admin/all_article");
		return modelAndView;
	}
	/**
	 * ��ת��д����
	* @Title: jump_write  
	* @Description: TODO
	 */
	@RequestMapping("/jump_write")
	public ModelAndView jump_write()
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Category> categories=categoryNodeService.getAllParent();//��ȡ���з���
		List<Tag> tags=tagService.listTag();//��ȡ���б�ǩ
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("tags", tags);
		modelAndView.setViewName("/admin/write_article");
		return modelAndView;

	}
	/**
	 * 
	* @Title: write  
	* @Description: �ύ����
	 */
	@RequestMapping("/write_article")
	public String write(Article article,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		Date date=new Date();
		String lastPath="\\static\\bgpic\\";//��������ͼ���λ��
		JSONObject jsonObject=new JSONObject();
		if(article.getArticleImage().getOriginalFilename().length()!=0)
		{		
		String absolutePath=UploadUtil.UploadbgImage(article.getArticleImage(),lastPath, request);//�ϴ����·���ͼƬ
		article.setArticleImagePath(absolutePath);
		}else
		{
			article.setArticleImagePath("\\static\\bgpic\\default.jpg");//û�з���ͼƬ����ΪĬ��ͼƬ
		}
		article.setArticleCommentCount(0);
		article.setArticleViewCount(0);
		article.setArticleCreateTime(date);
		if(articleService.insertArticle(article)!=null)
		{
		request.getSession().getServletContext().setAttribute("articleCount", articleService.countArticle());//������Ӳ��ͺ�ǰ̨����ʾ��
		Integer articleId=articleService.getAidByTitle(article.getArticleTitle());
		articleIndex.addIndex(article);//�ύ���¼��뵽lunce�����У����ڲ�ѯ
		
		if(article.getArticleTagId()!=null)
		{
			for(Integer tagId:article.getArticleTagId())
			{
			articleTagRefService.insertTagByAid(articleId, tagId);//���ѡ�еı�ǩ��Ź�����
			}
		}
		if(article.getArticleCategoryId()!=null)
		{
			for(Integer categoryId:article.getArticleCategoryId())
			{
			articleCategoryRefService.insertCategoryByAid(articleId, categoryId);//���ѡ�е÷����ŵ���������
			}
		}
		userLogService.insertLog(manageLog.insertLog("��Ӳ���", article.getArticleTitle()));//�����־
		jsonObject.put("success", true);
		jsonObject.put("msg", "����ɹ�");
		}
		else
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "����ʧ��");
		}
		ResponseUtil.write(response, jsonObject);
		return null;
		
	}
	/**
	 * 
	* @Title: jump_editor_article  
	* @Description: ��ת���޸�ҳ��
	 */
	@RequestMapping("/jump_editor_article")
	public ModelAndView jump_editor_article(@RequestParam("aid")Integer aid)
	{
		ModelAndView modelAndView=new ModelAndView();
		Article article=articleService.getArticleByAid(aid);
		List<Category> categories=categoryNodeService.getAllParent();//��ʾ���з���
		List<Tag> tags=tagService.listTag();//��ʾ���б�ǩ
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("tags", tags);
		modelAndView.addObject("article", article);
		modelAndView.addObject("articletags",articleTagRefService.getTagIdByAid(aid));
		modelAndView.addObject("articlecategories", articleCategoryRefService.getCategoryIdByAid(aid));
		modelAndView.setViewName("/admin/editor_article");
		return modelAndView;
		
	}
	/**
	 * 
	* @Title: editor_article  
	* @Description: �޸�����
	 */
	@RequestMapping("/editor_article")
	public String editor_article(Article article,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		Date date=new Date();
		String lastPath="\\static\\bgpic\\";//����ͼλ��
		Integer articleId=article.getArticleId();
		if(article.getArticleImage().getOriginalFilename().length()!=0)//����޸���ͼƬ
		{		
		String originPath=articleService.getArticleByAid(article.getArticleId()).getArticleImagePath();//��ȡԭͼƬ·��
		String absolutePath=UploadUtil.UploadbgImage(article.getArticleImage(),lastPath, request);//�ϴ�����ͼƬ
		UploadUtil.deleteImage(originPath, request);//ɾ��ԭ�ȵ�ͼƬ;
		article.setArticleImagePath(absolutePath);
		}
		article.setArticleUpdateTime(date);
		articleTagRefService.deleteByArticleId(articleId);//��ɾ����ǩ�����������
		articleCategoryRefService.deleteByArticleId(articleId);//��ɾ����������������
		if(article.getArticleCategoryId()!=null)//�������
		{
			for(Integer tagId:article.getArticleTagId())
			{
			articleTagRefService.insertTagByAid(articleId, tagId);
			}
		}
		if(article.getArticleTagId()!=null)//�����ǩ��������
		{
			for(Integer categoryId:article.getArticleCategoryId())
			{
			articleCategoryRefService.insertCategoryByAid(articleId, categoryId);
			}

		}
		if(articleService.updateArticle(article)!=null)
		{
			userLogService.insertLog(manageLog.insertLog("�޸Ĳ���", article.getArticleTitle()));//������־
			request.getSession().getServletContext().setAttribute("articleCount", articleService.countArticle());//������Ӳ��ͺ�ǰ̨����ʾ��
			articleIndex.updateIndex(article);//�޸��ύ���޸�lucence����
			jsonObject.put("success", true);
			jsonObject.put("msg", "�޸ĳɹ�");
		}
		else
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "�޸�ʧ��");
		}
		ResponseUtil.write(response, jsonObject);
		return null;
		
	}
	/**
	 * 
	* @Title: delete_tag  
	* @Description: ɾ������
	 */
	@RequestMapping("/delete_article")
	public String delete_tag(@RequestParam("aid")Integer aid,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		userLogService.insertLog(manageLog.insertLog("ɾ������", articleService.getArticleByAid(aid).getArticleTitle()));
		if((articleService.deleteArticle(aid))!=null)		
			{
			articleCategoryRefService.deleteByArticleId(aid);//ɾ�����������
			articleTagRefService.deleteByArticleId(aid);//ɾ����ǩ������
			commentService.deleteCommentByAid(aid);
			request.getSession().getServletContext().setAttribute("articleCount", articleService.countArticle());//����ɾ�����ͺ�ǰ̨����ʾ��
			articleIndex.deleteIndex(aid.toString());//ɾ�����º�ɾ��ָ��������
			jsonObject.put("success", true);
			jsonObject.put("msg", "ɾ���ɹ�");
			}
		else
			{
			jsonObject.put("success", false);
			jsonObject.put("msg", "ɾ��ʧ��");
			}

		ResponseUtil.write(response,jsonObject);
		return null;
	}

}
