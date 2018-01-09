package com.cxn.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		System.out.println(">>>>>>>>>进入登陆拦截器<<<<<<<<<<");
		System.out.println(request.getRequestURI());
		// TODO Auto-generated method stub
		if( request.getRequestURI().contains("/swagger-resources/")||request.getRequestURI().contains("/login")){
			// 如果是登陆请求 或 满足条件请求的话，直接放行，
			System.out.println(">>>>>>>>>请求满足条件<<<<<<<<<<");
			return true;
		}
		System.out.println(">>>>>>>>>请求不满足条件<<<<<<<<<<");
		
		String key = request.getHeader("coffee-oa-token");
		/*if(key ==null||"null".equals(key)){
			response.setCharacterEncoding("utf-8");
			response.setStatus(org.apache.http.HttpStatus.SC_FORBIDDEN);
			ErrorMessage error=new ErrorMessage();
			error.setErrorMsg("请求头部必须包含token信息");			
			ObjectMapper om=new ObjectMapper();
			String str=om.writer().writeValueAsString(error);
			response.getWriter().write(str);
			return false;
		}
		 */
		
		if ("demo".equals(key)) {
			System.out.println("do something~");
		}else{
			System.out.println("do other things~");
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}