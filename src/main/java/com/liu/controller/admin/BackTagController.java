package com.liu.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.liu.entity.Tag;
import com.liu.service.ArticleTagRefService;
import com.liu.service.TagService;
import com.liu.service.UserLogService;
import com.liu.service.UserService;
import com.liu.Utils.ManageLog;
import com.liu.Utils.ResponseUtil;

import net.sf.json.JSONObject;

 /** 
 * @ClassName: BackTagController 
 * @author: lyd
 * @date: 2019��4��17�� ����2:50:37 
 * @describe:��̨��ǩ������
 */
@Controller
@RequestMapping("/admin")
public class BackTagController {
	@Autowired
	private TagService tagService;
	@Autowired
	private ArticleTagRefService articleTagRefService;
	@Autowired
	private UserLogService userLogService;
	ManageLog manageLog=BackArticleController.manageLog;
	/**
	 * 
	* @Title: jump_tag  
	* @Description: ��ת����ǩҳ��
	 */
	@RequestMapping("/jump_tag")
	public ModelAndView jump_tag()
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Tag> tags=tagService.listTag();
		for(Tag tag:tags){
			tag.setArticleCount(tagService.getTagArticleCount(tag.getTagId()));
		}
		modelAndView.addObject("tags",tags);
		modelAndView.setViewName("/admin/tags");
		return modelAndView;
	}
	/**
	 * 
	* @Title: insert_tag  
	* @Description: �����ǩ
	 */
	@RequestMapping("/insert_tag")
	public String insert_tag(Tag tag,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		if((tagService.insertTag(tag))!=null)		
			{
			request.getSession().getServletContext().setAttribute("tagCount", tagService.listTag().size());//������ӱ�ǩ��ǰ̨����ʾ��
			userLogService.insertLog(manageLog.insertLog("��ӱ�ǩ", tag.getTagName()));
			jsonObject.put("success", true);
			jsonObject.put("tag", tag);
			}
		else
			jsonObject.put("success", false);
		ResponseUtil.write(response,jsonObject);
		return null;
	}
	/**
	 * ��ת���޸ı�ǩҳ
	* @Title: jump_editor_tag  
	* @Description: TODO
	 */
	@RequestMapping("/jump_editor_tag")
	public ModelAndView jump_editor_tag(@RequestParam("tid")Integer tid)
	{
		ModelAndView modelAndView=new ModelAndView();
		Tag tag=tagService.getTagById(tid);
		modelAndView.addObject("tag", tag);
		modelAndView.setViewName("/admin/editor_tag");
		return modelAndView;
	}
	/**
	 * 
	* @Title: update_tag  
	* @Description: �޸ı�ǩ
	 */
	@RequestMapping("/update_tag")
	@ResponseBody
	public String update_tag(Tag tag,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		if((tagService.updateTag(tag))!=null)		
			{
			userLogService.insertLog(manageLog.insertLog("�޸ı�ǩ", tag.getTagName()));
			jsonObject.put("success", true);
			jsonObject.put("tag", tag);
			}
		else
			jsonObject.put("success", false);
		ResponseUtil.write(response,jsonObject);
		return null;
	}
	/**
	 * 
	* @Title: delete_tag  
	* @Description: ɾ����ǩ
	 */
	@RequestMapping("/delete_tag")
	public String delete_tag(@RequestParam("tid")Integer tid,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		userLogService.insertLog(manageLog.insertLog("ɾ����ǩ",tagService.getTagById(tid).getTagName()));
		if(articleTagRefService.countArticleByTagId(tid)>0)
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "��ǩ��������,����ɾ��");
		}else
		{
		if((tagService.deleteById(tid))!=null)		
			{
			request.getSession().getServletContext().setAttribute("tagCount", tagService.listTag().size());//����ɾ����ǩ��ǰ̨����ʾ��
			jsonObject.put("success", true);
			jsonObject.put("msg", "ɾ���ɹ�");
			}
		else
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "ɾ��ʧ��");
		}
		}
		ResponseUtil.write(response,jsonObject);
		return null;
	}
}
