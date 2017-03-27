package org.live.common.web.controller;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *  全局的url映射
 *
 *  @author Mr.wang
 */
@Controller
public class IndexController {

	private  static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class) ;

	/**
	 * 跳转到主界面
	 *
	 * @return
	 */
	@RequestMapping(value = {"/","/main"})
	public String main(HttpServletRequest request) {

		return "common/main";
	}

	/**
	 *  跳转到登录页面
	 * @return
	 */
	@RequestMapping("/tologin")
	public String tologin() {
		return "redirect:/login" ;
	}

	/**
	 * 跳转到登录页面
	 * shiro的表单认证过滤器认证失败之后，跳转到这里，进行错误信息处理
	 * @return
	 */
	@RequestMapping(value={"/login","/index"})
	public String shiroFilterLogin(HttpServletRequest request, Model model) {
        //保存认证失败异常的全限命名
		final String shiroLoginFailure = (String) request.getAttribute("shiroLoginFailure") ;
		if(shiroLoginFailure != null) {
			String errorMessage = null ;
			if(StringUtils.equals(UnknownAccountException.class.getName(), shiroLoginFailure)) {	//用户不存在
				errorMessage = "用户名/密码错误" ;
			} else if(StringUtils.equals(IncorrectCredentialsException.class.getName(), shiroLoginFailure)) {	//密码错误
				errorMessage = "用户名/密码错误" ;
			} else if(StringUtils.equals(LockedAccountException.class.getName(), shiroLoginFailure)) {
				errorMessage = "账号被锁定" ;
			} else if(StringUtils.equals(AuthenticationException.class.getName(), shiroLoginFailure)) {
				errorMessage = "用户名/密码错误" ;
			}
			else {
				errorMessage = "认证用户错误" ;
			}
			model.addAttribute("errorMessage", errorMessage) ;
		}
		return "login" ;
	}

	/**
	 * 以ajax的方式登录
	 * @param request
	 * @return
	 */
	@RequestMapping("/ajaxlogin")
	@ResponseBody
	public ResponseModel<String> ajaxlogin(HttpServletRequest request, String username, String password) {
		ResponseModel<String> model = new SimpleResponseModel<String>() ;
		UsernamePasswordToken token =  new UsernamePasswordToken(StringUtils.trim(username),StringUtils.trim(password)) ;
		try {

			model.success("登录成功") ;
		} catch(UnknownAccountException e) { //用户不存在
			LOGGER.debug(e.getMessage(),e);
			model.error("用户名/密码错误") ;
		} catch(IncorrectCredentialsException e) {	//密码错误
			LOGGER.debug(e.getMessage(),e);
			model.error("用户名/密码错误") ;
		} catch(ExcessiveAttemptsException e) {	//matcher记录信息
			model.error(e.getMessage()) ;
		} catch(LockedAccountException e) {
			LOGGER.debug(e.getMessage(),e) ;
			model.error("账号被锁定") ;
		} catch(AuthenticationException e) {
			LOGGER.debug(e.getMessage(),e);
			model.error("登录认证错误") ;
		} catch(Exception e) {
			LOGGER.error(e.getMessage(),e);
			model.error("服务器忙") ;
		}
		return model ;
	}

    /**
     * 登出操作。
     * @return
     */
    @RequestMapping("/logout")
    public String shiroLogout() {
        SecurityUtils.getSubject().logout() ;	//登出
        return "redirect:/login" ;
    }

    @RequestMapping("/demo")
    public String demo() {

    	return "module/demo/demo" ;
	}

	@RequestMapping("/demo02")
	public String demo2() {

    	return "module/demo/demo02" ;
	}


}
