package com.wonju.work.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wonju.work.dto.UserDTO;

@Component
public class AuthInterceptor implements HandlerInterceptor  {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        
        UserDTO loginUser = (UserDTO) session.getAttribute("dto");
               
        if(ObjectUtils.isEmpty(loginUser)){
            response.sendRedirect("/");
            return false;
        }else{
            session.setMaxInactiveInterval(30*60);
            return true;
        }
        
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
