package com.liu.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.liu.Utils.ManageLog;
import com.liu.Utils.ResponseUtil;
import com.liu.Utils.UploadUtil;
import com.liu.entity.PageBean;
import com.liu.entity.User;
import com.liu.entity.UserLog;
import com.liu.service.UserLogService;
import com.liu.service.UserService;

import net.sf.json.JSONObject;

 /** 
 * @ClassName: BackUserController 
 * @author: lyd
 * @date: 2019��4��17�� ����2:53:19 
 * @describe:��̨�û�������
 */
@Controller
@RequestMapping("/admin")
public class BackUserController {
	@Resource
	private UserService userService;
	@Autowired
	private UserLogService userLogService;
	ManageLog manageLog=BackArticleController.manageLog;
	/**
	 * 
	* @Title: getUser  
	* @Description: ��ת���û�����
	 */
	@RequestMapping("/user")
	public ModelAndView getUser()
	{
		ModelAndView modelAndView=new ModelAndView();
		User user=userService.getUser(1);
		modelAndView.addObject("user", user);
		modelAndView.setViewName("/admin/user_page");
		return modelAndView;
	}
	/**
	 * 
	* @Title: passowrd  
	* @Description:��ת���޸�����ҳ��
	 */
	@RequestMapping("/password")
	public ModelAndView passowrd()
	{
		ModelAndView modelAndView=new ModelAndView();
		User user=userService.getUser(1);
		modelAndView.addObject("userName", user.getUserName());
		modelAndView.addObject("userPass", user.getUserPass());
		modelAndView.setViewName("/admin/editor_password");
		return modelAndView;
	}
	/**
	 * 
	* @Title: update_password  
	* @Description: �޸�����
	 */
	@RequestMapping("/update_password")
	public String update_password(@RequestParam("userName")String userName,@RequestParam("newPass")String newPass,HttpServletRequest request,HttpServletResponse response) throws Exception
	{	
		JSONObject jsonObject=new JSONObject();
		User user=new User();
		user.setUserId(1);
		user.setUserName(userName);
		user.setUserPass(newPass);
		if(userService.updateUser(user)!=null)
		{
			userLogService.insertLog(manageLog.insertLog("�޸�����",newPass));
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
	/**
	 * �޸ĸ�������
	 * @throws Exception 
	 */
	@RequestMapping("/update_info")
	@ResponseBody
	public String update_info(User user,HttpServletRequest request,HttpServletResponse response) throws Exception
	{		JSONObject jsonObject=new JSONObject();
		String absolutePath;
		String lastPath="static/images/";
		String originPath=userService.getUser(1).getAvatarPath();//ԭ������ͼ��ͼƬ·��
		if(user.getAvatarImage().getOriginalFilename().length()!=0)
		{		
			absolutePath=UploadUtil.UploadbgImage(user.getAvatarImage(), lastPath, request);
			UploadUtil.deleteImage(originPath, request);//ɾ��ԭ�ȵ�ͼƬ
			user.setAvatarPath(absolutePath);
			userLogService.insertLog(manageLog.insertLog("�޸�","�޸��˸�������"));
		}
		if(userService.updateUser(user)!=null)
		{
			jsonObject.put("success", true);
			jsonObject.put("msg", "�޸ĳɹ�");
			user=userService.getUser(1);
			user.setTags(user.getPersonTag().split(" "));
			request.getSession().getServletContext().setAttribute("inform", user);//����ɾ�������ǰ̨����ʾ��

		}else
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "�޸�ʧ��");
		}
		ResponseUtil.write(response, jsonObject);
		return null;	
	}
	/**
	 * 
	* @Title: logs  
	* @Description: ����ҳ��ת����־ҳ��
	 */
	@RequestMapping("/logs/{nowPage}")
	public ModelAndView logs(@PathVariable("nowPage")Integer nowPage)
	{
		Integer pageSize=10;
		ModelAndView modelAndView=new ModelAndView();
		Map<String, Object> map=new HashMap<String, Object>();
		int logCount=userLogService.getLogCount();//��ȡ��־����
		PageBean pageBean=new PageBean(nowPage, pageSize);
		map.put("start", pageBean.getPage());
		map.put("pageSize", pageBean.getPageSize());
		List<UserLog> logs=userLogService.listLog(map);
		int totalPage=(int)Math.ceil(logCount*1.0/pageSize);//��ȡ�ܵ�ҳ��
		modelAndView.addObject("logs", logs);
		modelAndView.addObject("type", "logs");
		modelAndView.addObject("firsttotalPage", totalPage);
		modelAndView.addObject("firstnowPage", nowPage);
		modelAndView.setViewName("/admin/logs");
		return modelAndView;
	}
	/**
	 * 
	* @Title: delete_log  
	* @Description: ɾ����־
	 */
	@RequestMapping("/delete_log")
	public String delete_log(@RequestParam("id")Integer id,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		if((userLogService.deleteLog(id))!=null)		
			{
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
	//�˳�
	@RequestMapping("/logout")
	public String logout()throws Exception
	{
		userLogService.insertLog(manageLog.insertLog("�˳�","�˳���½"));
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
}
