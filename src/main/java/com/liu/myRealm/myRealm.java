package com.liu.myRealm;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.liu.entity.User;
import com.liu.service.UserService;

 /** 
 * @ClassName: myRealm 
 * @author: lyd
 * @date: 2017��10��10�� ����9:56:17 
 * @describe:Shiro�Զ���Realm
 */
public class myRealm  extends AuthorizingRealm{
	@Resource 
	private UserService userService;

	@Override
//	��Ȩ��ѯ�ص�����
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
//	��֤�ص�����,���е�½��֤
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username=(String)token.getPrincipal();//��ȡ�û���
		User user=userService.getUserByUsername(username);
		if(user!=null)
		{
			SecurityUtils.getSubject().getSession().setAttribute("user", user);
			AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user.getUserName(),user.getUserPass(),"myRealm");
			return authenticationInfo;
		}
		return null;
	}
	
}
