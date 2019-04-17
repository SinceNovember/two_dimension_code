package com.liu.controller.admin;

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
import com.liu.Utils.UploadUtil;
import com.liu.entity.Comment;
import com.liu.service.CommentService;
import com.liu.service.UserLogService;
import com.liu.service.UserService;

import net.sf.json.JSONObject;

 /** 
 * @ClassName: BackCommentController 
 * @author: lyd
 * @date: 2019��4��17�� ����2:42:40 
 * @describe:��̨���ۿ�����
 */
@Controller
@RequestMapping("/admin")
public class BackCommentController {
	@Autowired
	private CommentService commentService;
	@Autowired 
	private UserService userService;
	@Autowired
	private UserLogService userLogService;
	ManageLog manageLog=BackArticleController.manageLog;
	/**
	 * 
	* @Title: list_comment  
	* @Description: ��ת����ҳ��
	 */
	@RequestMapping("/comment")
	public ModelAndView list_comment()
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Comment> comments=commentService.listComment(0);
		modelAndView.addObject("comments", comments);
		modelAndView.addObject("commentCount", comments.size());
		modelAndView.setViewName("/admin/comment");
		return modelAndView;
		
	}
	/**
	 * 
	* @Title: delete_comment  
	* @Description: ɾ������
	 */
	@RequestMapping("/delete_comment")
	public String delete_comment(@RequestParam("cid")Integer cid,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		String originPath=commentService.getCommentByid(cid).getCommentAvatarPath();
		userLogService.insertLog(manageLog.insertLog("ɾ������",commentService.getContentByCid(cid)));//�����־
		if(commentService.deleteCommentByCid(cid)!=null)
		{
			UploadUtil.deleteImage(originPath, request);//ɾ��ԭ�ȵ�ͼƬ;
			jsonObject.put("success", true);
			jsonObject.put("msg", "����ɾ���ɹ�");
		}
		else
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "����ɾ��ʧ��");
		}
		ResponseUtil.write(response,jsonObject);
		return null;
	}
	/**
	 * 
	* @Title: jump_reply_comment  
	* @Description: ��ת���ظ�����ҳ��
	 */
	@RequestMapping("/jump_reply_comment")
	public ModelAndView jump_reply_comment(@RequestParam("cid")Integer cid)
	{
		ModelAndView modelAndView=new ModelAndView();
		Comment comment=commentService.getCommentByid(cid);
		modelAndView.addObject("comment", comment);
		modelAndView.setViewName("/admin/reply_comment");
		return modelAndView;
	}
	/**
	 * 
	* @Title: insert_reply  
	* @Description: �����ҵûظ�
	 */
	@RequestMapping("/insert_reply")
	@ResponseBody
	public String insert_reply(Comment comment,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		Date date=new Date();
		comment.setCommentCreateTime(date);
		comment.setCommentRole(1);
		comment.setCommentAuthorName(userService.getUser(1).getUserNickname());
		comment.setCommentLikeCount(0);
		comment.setCommentIp(request.getRemoteAddr());//����Ϊ����ip
		comment.setCommentAvatarPath("\\static\\pic\\comment\\default.jpg");

		if((commentService.insertComment(comment))!=null)		
			{
			userLogService.insertLog(manageLog.insertLog("�ظ�����",comment.getCommentContent()));//�����־
			jsonObject.put("success", true);
			jsonObject.put("msg","�ظ��ɹ�");
			}
		else
			jsonObject.put("success", false);
		ResponseUtil.write(response,jsonObject);
		return null;
	}
	/**
	 * 
	* @Title: my_comment  
	* @Description: ��ת�������лظ���ҳ��
	 */
	@RequestMapping("/my_comment")
	public ModelAndView my_comment()
	{
		ModelAndView modelAndView=new ModelAndView();
		List<Comment> comments=commentService.listComment(1);//��ʾ�����һظ�������
		for(Comment comment:comments)
		{
			comment.setOriginalContent(commentService.getContentByCid(comment.getCommentPid()));
		}
		modelAndView.addObject("comments", comments);
		modelAndView.addObject("commentCount", comments.size());
		modelAndView.setViewName("/admin/my_comment");
		return modelAndView;
		
	}
	/**
	 * 
	* @Title: delete_my_comment  
	* @Description: ɾ���ҵ�����
	 */
	@RequestMapping("/delete_my_comment")
	public String delete_my_comment(@RequestParam("cid")Integer cid,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		userLogService.insertLog(manageLog.insertLog("ɾ���ҵĻظ�",commentService.getContentByCid(cid)));
		if(commentService.deleteCommentByCid(cid)!=null)
		{
			jsonObject.put("success", true);
			jsonObject.put("msg", "����ɾ���ɹ�");
		}
		else
		{
			jsonObject.put("success", false);
			jsonObject.put("msg", "����ɾ��ʧ��");
		}
		ResponseUtil.write(response,jsonObject);
		return null;
	}
	/**
	 * 
	* @Title: jump_editor_comment  
	* @Description: ��ת���޸�����ҳ��
	 */
	@RequestMapping("/jump_editor_comment")
	public ModelAndView jump_editor_comment(@RequestParam("cid")Integer cid)
	{
		ModelAndView modelAndView=new ModelAndView();
		System.out.println(cid);
		Comment comment=commentService.getCommentByid(cid);
		modelAndView.addObject("comment", comment);
		modelAndView.setViewName("/admin/editor_my_reply");
		return modelAndView;
	}
	/**
	 * 
	* @Title: editor_my_reply  
	* @Description: �޸��ҵĻظ�
	 */
	@RequestMapping("/editor_my_reply")
	@ResponseBody
	public String editor_my_reply(Comment comment,HttpServletRequest request,HttpServletResponse response) throws Exception
	{

		JSONObject jsonObject=new JSONObject();
		if((commentService.updateMyContent(comment))!=null)		
			{
			userLogService.insertLog(manageLog.insertLog("�޸��ҵĻظ�",comment.getCommentContent()));
			jsonObject.put("success", true);
			jsonObject.put("msg","�ظ��ɹ�");
			}
		else
			jsonObject.put("success", false);
		ResponseUtil.write(response,jsonObject);
		return null;
	}
	

}
