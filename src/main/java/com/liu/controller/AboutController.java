package com.liu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.liu.service.UserService;
  /** 
 * @ClassName: AboutController 
 * @author: lyd
 * @date: 2019��4��17�� ����1:32:20 
 * @describe:��ת����ҳ�������
 */
@Controller
public class AboutController {
	@Autowired
	private UserService userService;
	@RequestMapping("/about")
	public ModelAndView about()
	{
		ModelAndView modelAndView=new ModelAndView();
		String about=userService.getUser(1).getHtmlProfile();//��ȡ���˼�飬����չʾ
		modelAndView.addObject("about", about);
		modelAndView.setViewName("/home/about");
		return modelAndView;
	}
}
