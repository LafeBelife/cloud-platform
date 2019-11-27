package cn.wbw.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统拦截器
 * @author wbw
 */
public class SystemInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res,Object handler) throws Exception{
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView mav) throws Exception{
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest req,HttpServletResponse res,Object handler,Exception ex) throws Exception{
        System.out.println("afterCompletion");
    }
}
