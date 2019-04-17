package com.liu.controller;

import java.util.HashMap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liu.Utils.ManageLog;
import com.liu.controller.admin.BackArticleController;
import com.liu.entity.Article;
import com.liu.entity.Comment;
import com.liu.entity.User;
import com.liu.entity.UserLog;
import com.liu.service.ArticleService;
import com.liu.service.CommentService;
import com.liu.service.UserLogService;

 /** 
 * @ClassName: LoginController 
 * @author: lyd
 * @date: 2019��4��17�� ����1:56:32 
 * @describe:��½������
 */
@Controller
public class LoginController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentServie;
	@Autowired
	private UserLogService userLogService;
	ManageLog manageLog=BackArticleController.manageLog;//����ͳ����־
	public static String LoginIp="";
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest request){
	Subject subject=SecurityUtils.getSubject();//��ȡshiro����
	UsernamePasswordToken token=new UsernamePasswordToken(user.getUserName(),user.getUserPass());//���˺��������token
	try
	{
		subject.login(token);//���Ե�½
		HashMap<String, Object>map=new HashMap<String, Object>();
		map.put("start", 0);
		map.put("pageSize", 8);
		List<Comment> comments=commentServie.listRecentComment(map);//�������������
		List<Article> articles=articleService.listAdminArticle(map);//���·���������
		List<UserLog> logs=userLogService.listLog(map);//���µ���־
		LoginIp=request.getRemoteAddr();
		userLogService.insertLog(manageLog.insertLog("��½","�û���½"));
		request.getSession().getServletContext().setAttribute("recentArticles",articles);
		request.getSession().getServletContext().setAttribute("comments", comments);
		request.getSession().getServletContext().setAttribute("commentCount", comments.size());
		request.getSession().getServletContext().setAttribute("logs", logs);
		return "redirect:/admin/main.jsp";
	}catch (Exception e) 
		{
		e.printStackTrace();
		request.setAttribute("errorInfo", "�û����������");
		return "login";
		}
    }
}
