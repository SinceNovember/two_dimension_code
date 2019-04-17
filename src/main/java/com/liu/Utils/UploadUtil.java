package com.liu.Utils;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


 /** 
 * @ClassName: UploadUtil 
 * @author: lyd
 * @date: 2019��4��17�� ����3:17:33 
 * @describe:�ϴ�ͼƬ����
 */
public class UploadUtil {
	/**
	 * 
	* @Title: Upload  
	* @Description: �ϴ�ͼƬ��ָ��λ��
	 */
	public static void Upload(MultipartFile myfiles,String uploadPath,String fileName)
	{
		  File uploadRootDir = new File(uploadPath);
	        if(!uploadRootDir.exists())
	             uploadRootDir.mkdirs();
	        File targetFile = new File(uploadPath+fileName);
	           if(fileName!=null&&fileName.length()>0){
	               try {
//	                 ��Դ�ļ�ת�Ƶ�Ŀ���ļ���ʹ��transferTo����
	            	   myfiles.transferTo(targetFile);
	               } catch (IOException e) {
	                   e.printStackTrace();
	               }
	           }
	}
	/**
	 * 
	* @Title: UploadbgImage  
	* @Description: �ϴ����º�����ͷ��ͼƬ
	 */
	public static String UploadbgImage(MultipartFile myfiles,String lastPath,HttpServletRequest request)
	{
		   String servletPath = request.getSession().getServletContext().getRealPath("/");//��Ŀ·��
	        String uploadPath=servletPath+lastPath;//������λ��
	        String OriginName = myfiles.getOriginalFilename();
	        long startTime=System.currentTimeMillis();  
	        String suffix = OriginName.substring(OriginName.lastIndexOf(".") + 1);
	        String fileName=startTime+"."+suffix;
			UploadUtil.Upload(myfiles, uploadPath,fileName);
			String absolutePath=lastPath+fileName;
			return absolutePath;
	}
	/**
	 * 
	* @Title: deleteImage  
	* @Description: ɾ��ָ��·����ͼƬ
	 */
	public static void deleteImage(String imagePath,HttpServletRequest request)
	{
		String servletPath=request.getSession().getServletContext().getRealPath("/");//��Ŀ·��
		String filePath=servletPath+imagePath;
		File file=new File(filePath);
		if(file.exists())
		{
			file.delete();

		}
		
	}
	/**
	 * 
	* @Title: UploadPageImage  
	* @Description: �����ϴ�ҳ��ı���ͼ
	 */
	public static String UploadPageImage(MultipartFile myfiles,Integer pageFlag,HttpServletRequest request)
	{
		String servletPath=request.getSession().getServletContext().getRealPath("/");
		String fileName="";
		String filePath = "static/images/";
		if(pageFlag==1)
		{
			fileName="index.jpg";
		}else if(pageFlag==2)
		{
			fileName="detail.jpg";
		}else if(pageFlag==3)
		{
			fileName="archive.jpg";
		}else if(pageFlag==4)
		{
			fileName="category.jpg";
		}else if(pageFlag==5)
		{
			fileName="tag.jpg";
		}else 
		{
			fileName="about.jpg";
		}
		filePath+=fileName;
		UploadUtil.Upload(myfiles, servletPath,filePath);
		return filePath;
	}
}
