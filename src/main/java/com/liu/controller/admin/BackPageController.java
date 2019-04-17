package com.liu.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.liu.Utils.ManageLog;
import com.liu.Utils.ResponseUtil;
import com.liu.Utils.UploadUtil;
import com.liu.entity.Page;
import com.liu.service.PageService;
import com.liu.service.UserLogService;

import net.sf.json.JSONObject;

 /** 
 * @ClassName: BackPageController 
 * @author: lyd
 * @date: 2019��4��17�� ����2:49:18 
 * @describe:��̨�޸�ǰ̨ҳ�������
 */
@Controller
@RequestMapping("/admin")
public class BackPageController {
	@Autowired
	private PageService pageService;
	@Autowired
	private  UserLogService userLogService;
	ManageLog manageLog=BackArticleController.manageLog;//������־����
	/**
	 * ��ת���޸�ҳ��
	* @Title: page_setup  
	* @Description: TODO
	 */
	@RequestMapping("/page_setup")
	public ModelAndView	page_setup()
	{
		ModelAndView modelAndView=new ModelAndView();
		Page index=pageService.getPage(1);//��ҳ
		Page detail=pageService.getPage(2);//��ϸҳ
		Page archive=pageService.getPage(3);//�鵵ҳ
		Page category=pageService.getPage(4);//����ҳ
		Page tag=pageService.getPage(5);//��ǩҳ
		Page about=pageService.getPage(6);//����ҳ
		modelAndView.addObject("pageIndex", index);
		modelAndView.addObject("pageDetail", detail);
		modelAndView.addObject("pageArchive", archive);
		modelAndView.addObject("pageCategory", category);
		modelAndView.addObject("pageTag", tag);
		modelAndView.addObject("pageAbout", about);
		modelAndView.setViewName("/admin/page_setup");
		return modelAndView;
	}
	/**
	 * 
	* @Title: update_page  
	* @Description: �޸���ҳͼƬ�ͱ�ǩ
	 */
	@RequestMapping("/update_page")
	public String	update_page(Page page,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		String absolutePath;
		String lastPath="static/images/";
		String originPath=pageService.getPage(page.getPageFlag()).getImagePath();//ԭ������ͼ��ͼƬ·��
		if(page.getPageImage().getOriginalFilename().length()!=0)
		{		
			absolutePath=UploadUtil.UploadbgImage(page.getPageImage(), lastPath, request);//�ϴ�ͼƬ
			UploadUtil.deleteImage(originPath, request);//ɾ��ԭ�ȵ�ͼƬ;
			page.setImagePath(absolutePath);
			userLogService.insertLog(manageLog.insertLog("�޸�","�޸�ҳ�汳��ͼ" ));//������־
		}
		if(pageService.updatePage(page)!=null)
		{
			jsonObject.put("success", true);
			jsonObject.put("msg", "�޸ĳɹ�");
		}else
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "�޸�ʧ��");
		}
		ResponseUtil.write(response, jsonObject);
		return null;
	}
}
