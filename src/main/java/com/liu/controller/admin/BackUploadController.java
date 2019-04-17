package com.liu.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.liu.Utils.ResponseUtil;
import com.liu.Utils.UploadUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/upload")
public class BackUploadController {

	/**
	 * 
	* @Title: upload  
	* @Description: ���editor.md�༭��ͼƬ
	 */
	@RequestMapping("/article_image")
	public String upload(@RequestParam(value = "editormd-image-file", required = false) MultipartFile myfiles,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		JSONObject jsonObject=new JSONObject();
		String servletPath = request.getSession().getServletContext().getRealPath("/");//��Ŀ·��
	    String uploadPath = servletPath+"static\\pic\\";//ͼƬ���λ��
	    String OriginName = myfiles.getOriginalFilename();
	    long startTime=System.currentTimeMillis();  //��ȡ����ʱ�䣬������ΪͼƬ������
	    String suffix = OriginName.substring(OriginName.lastIndexOf(".") + 1);//��ȡͼƬ��׺��
	    String fileName=startTime+"."+suffix;//ͼƬ����
	    UploadUtil.Upload(myfiles, uploadPath,fileName);//�ϴ�ͼƬ
        jsonObject.put("success", 1);
        jsonObject.put("message", "�ϴ��ɹ�");
        jsonObject.put("url","/Blog/static/pic/"+fileName);
        ResponseUtil.write(response, jsonObject);
		return null;
		
	}
}
