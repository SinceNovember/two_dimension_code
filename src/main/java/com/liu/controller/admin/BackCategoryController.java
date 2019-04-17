package com.liu.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.liu.Utils.ManageLog;
import com.liu.Utils.ResponseUtil;
import com.liu.entity.Category;
import com.liu.service.CategoryNodeService;
import com.liu.service.CategoryService;
import com.liu.service.UserLogService;
import com.liu.service.UserService;

import net.sf.json.JSONObject;

 /** 
 * @ClassName: BackCategoryController 
 * @author: lyd
 * @date: 2019��4��17�� ����2:31:03 
 * @describe:��̨���������
 */
@Controller
@RequestMapping("/admin")
public class BackCategoryController {
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private CategoryNodeService categoryNodeService;
	@Autowired
	private UserLogService userLogService;
	ManageLog manageLog=BackArticleController.manageLog;
	/**
	 * 
	* @Title: jump_category  
	* @Description: ��ת����ҳ��
	 */
	@RequestMapping("/jump_category")
	public ModelAndView jump_category()
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Category> categories=categoryNodeService.getAllParent();//��ȡ���з���
		for(Category category1:categories){//�������з����µ�������
			category1.setArticleCount(categoryService.getCategoryArticleCount(category1.getCategoryId()));
			for(Category category2:category1.getChildrenCategory())
				category2.setArticleCount(categoryService.getCategoryArticleCount(category2.getCategoryId()));
		}
		modelAndView.addObject("categories", categories);
		modelAndView.setViewName("/admin/category");
		return modelAndView;
	}
	/**
	 * ��ת�޸ķ���ҳ��
	* @Title: jump_editor_category  
	* @Description: TODO
	 */
	@RequestMapping("/jump_editor_category")
	public ModelAndView jump_editor_category(@RequestParam("cid")Integer cid)
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Category> list=new ArrayList<Category>();
		List<Category> parentCategories=categoryNodeService.getAllParent();//��ȡ���Ը��׽��
		Category category=categoryService.getCategoryByCid(cid);
		Category category2=new Category();//����޸��ڵ�
		category2.setCategoryName("�޸��ڵ�");
		category2.setCategoryId(0);
		if(category.getCategoryPid()==0)//�������Ϊ���ڵ�
		{

			list.add(category2);//����Ϊ�޸��׽ڵ�
			if(categoryService.getCategoryByPid(category.getCategoryId()).size()==0)
			{
			list.addAll(parentCategories);//������е÷��ุ���
			}
		}
		else
		{
			list.add(category.getParentCategory());//����丸�׽������ѡ��
			list.add(category2);//����޸��׽ڵ�
			for(Category tempcategory:parentCategories)
			{
				if(tempcategory.getCategoryId()!=category.getCategoryPid())//����������ุ�׽��
					list.add(tempcategory);
			}
			
		}
		modelAndView.addObject("parentCategories", list);
		modelAndView.addObject("category",category);
		modelAndView.setViewName("/admin/editor_category");
		return modelAndView;
	}
	/**
	 * 
	* @Title: insert_category  
	* @Description: �������
	 */
	@RequestMapping("/insert_category")
	public String insert_category(Category category,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		if((categoryService.insertCategory(category))!=null)		
		{
			request.getSession().getServletContext().setAttribute("categoryCount", categoryService.listCategory().size());//������ӷ����ǰ̨����ʾ��
			userLogService.insertLog(manageLog.insertLog("��ӷ���", category.getCategoryName()));
			jsonObject.put("success", true);
			jsonObject.put("msg", "��ӳɹ�");
		}
		else
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "���ʧ��");
		}
		ResponseUtil.write(response,jsonObject);
		return null;
	}
	/**
	 * �޸ķ���
	* @Title: update_category  
	* @Description: TODO
	 */
	@RequestMapping("/update_category")
	@ResponseBody
	public String update_category(Category category,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		if((categoryService.updateCategory(category))!=null)		
			{
			userLogService.insertLog(manageLog.insertLog("�޸ķ���", category.getCategoryName()));
			jsonObject.put("success", true);
			jsonObject.put("msg","�޸ĳɹ�");
			}
		else
			jsonObject.put("success", false);
		ResponseUtil.write(response,jsonObject);
		return null;
	}
	/**
	 * ɾ������
	* @Title: delete_tag  
	* @Description: TODO
	 */
	@RequestMapping("/delete_category")
	public String delete_tag(@RequestParam("cid")Integer cid,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		Category category=categoryService.getCategoryByCid(cid);
		if(category.getCategoryPid()==0&&(categoryService.findChildCategory(cid).size()!=0))//���Ϊ���׽���ҷ�����������
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "�÷���������������,����ɾ��");
		}
		else
		{
			Integer articleCount=categoryService.getCategoryArticleCount(cid);
			if(articleCount==0)//���������Ϊ0ʱ
			{
			if(categoryService.deleteCategoryByCid(cid)!=null)
			{
				request.getSession().getServletContext().setAttribute("categoryCount", categoryService.listCategory().size());//����ɾ�������ǰ̨����ʾ��
				userLogService.insertLog(manageLog.insertLog("ɾ������", category.getCategoryName()));//�����־
				jsonObject.put("success", true);
				jsonObject.put("msg", "ɾ���ɹ�");
				jsonObject.put("categoryParent", categoryNodeService.getAllParent());
			}
			else
			{
				jsonObject.put("success", false);
				jsonObject.put("msg", "�÷����������²���ɾ��");
			}
			}else
			{
				jsonObject.put("success", false);
				jsonObject.put("msg", "�÷����������²���ɾ��");
			}
		}
		ResponseUtil.write(response,jsonObject);
		return null;
	}
}
