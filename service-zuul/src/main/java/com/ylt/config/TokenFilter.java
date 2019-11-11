package com.ylt.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class TokenFilter extends ZuulFilter {
    //四种类型：pre,routing,error,post
    //pre：主要用在路由映射的阶段是寻找路由映射表的
    //routing:具体的路由转发过滤器是在routing路由器，具体的请求转发的时候会调用
    //error:一旦前面的过滤器出错了，会调用error过滤器。
    //post:当routing，error运行完后才会调用该过滤器，是在最后阶段的
    @Override
    public String filterType() {
        return "pre";
    }

    //自定义过滤器执行的顺序，数值越大越靠后执行，越小就越先执行
    @Override
    public int filterOrder() {
        return 0;
    }

    //控制过滤器生效不生效，可以在里面写一串逻辑来控制
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //执行过滤逻辑
    @Override
    public Object run() {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String servletPath1 = request.getServletPath();
        if (servletPath1.contains("."))
        {
            String[] servletPathArr = servletPath1.split("\\.");
            String servletPath = servletPathArr[servletPathArr.length-1];
            if ("js".equals(servletPath) || "css".equals(servletPath) || "jpg".equals(servletPath)
                    || "png".equals(servletPath) || "woff".equals(servletPath) || "woff2".equals(servletPath)
                    || "ico".equals(servletPath))
            {
                return null;
            }
        }

        /*
         * request获取路径
             1、request.getRequestURL() 返回的是完整的url，包括Http协议，端口号，servlet名字和映射路径，但它不包含请求参数。
             2、request.getRequestURI() 得到的是request URL的部分值，并且web容器没有decode过的
             3、request.getContextPath() 返回 the context of the request.
             4、request.getServletPath() 返回调用servlet的部分url.
             5、request.getQueryString() 返回url路径后面的查询字符串

         示例： 当前url：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp?action=idp.sptopn
             request.getRequestURL() ：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp
             request.getRequestURI() ：/CarsiLogCenter_new/idpstat.jsp
             request.getContextPath()：/CarsiLogCenter_new
             request.getServletPath()： /idpstat.jsp
             request.getQueryString()：action=idp.sptopn
         */
//        String token = request.getParameter("token");
//        if (token == null){
//            context.setSendZuulResponse(false);
//            context.setResponseStatusCode(401);
//            context.setResponseBody("unAuthrized");
//            return null;
//        }
        return null;
    }
}
