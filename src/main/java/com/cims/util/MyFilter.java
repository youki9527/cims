package com.cims.util;

import com.cims.pojo.AdminInfo;
import com.cims.pojo.ManagerInfo;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        //转换成request对象
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //获取session对象
        HttpSession session = request.getSession();

        //获取登录客户经理信息
        ManagerInfo manager = (ManagerInfo) session.getAttribute("manager");
//        System.out.println("经理信息id"+manager.getMId());
        //获取登录管理员信息
        AdminInfo adminInfo = (AdminInfo) session.getAttribute("admin");
        //  System.out.println("管理员id"+adminInfo.getAName());

        //获取当前的url
        String url = request.getRequestURI() + "";

        System.out.println("url=" + url);

        //所有页面url
        List<String> listUrlAll = new ArrayList<>();
        //管理员权限页面
        listUrlAll.add("/indexA/indexAdmin");
        listUrlAll.add("/indexA/welcome");
        listUrlAll.add("/client/list");
        listUrlAll.add("/client/zhufu");
        listUrlAll.add("/client/excelWrite");
        listUrlAll.add("/client/addPage");
        listUrlAll.add("/client/editPage");
        listUrlAll.add("/manager/list");
        listUrlAll.add("/manager/editPage");
        listUrlAll.add("/manager/excelWrite");
        listUrlAll.add("/manager/add");
        //客户经理权限页面
        listUrlAll.add("/indexM/indexManager");
        listUrlAll.add("/indexM/welcome");
        listUrlAll.add("/indexM/managerInfo");
        listUrlAll.add("/indexM/updatePwd");
        listUrlAll.add("/contact/list");
        listUrlAll.add("/contact/zhufu");
        listUrlAll.add("/contact/editPage");
        listUrlAll.add("/contact/add");
        listUrlAll.add("/schedule/scheduleList");
        listUrlAll.add("/schedule/scheduleAdd");
        listUrlAll.add("/schedule/scheduleUpdate");
        listUrlAll.add("/serviceInfoManage/serviceList");
        listUrlAll.add("/serviceInfoManage/serviceAdd");
        listUrlAll.add("/serviceInfoManage/serviceUpdate");
        listUrlAll.add("/serviceInfoManage//exportServiceInfo");


        //客户经理权限页面url
        List<String> listUrlManager = new ArrayList<>();
        listUrlManager.add("/indexM/welcome");
        listUrlManager.add("/indexM/indexManager");
        listUrlManager.add("/indexM/managerInfo");
        listUrlManager.add("/indexM/updatePwd");
        listUrlManager.add("/contact/list");
        listUrlManager.add("/contact/zhufu");
        listUrlManager.add("/contact/editPage");
        listUrlManager.add("/contact/add");
        listUrlManager.add("/schedule/scheduleList");
        listUrlManager.add("/schedule/scheduleAdd");
        listUrlManager.add("/schedule/scheduleUpdate");
        listUrlManager.add("/serviceInfoManage/serviceList");
        listUrlManager.add("/serviceInfoManage/serviceAdd");
        listUrlManager.add("/serviceInfoManage/serviceUpdate");
        listUrlManager.add("/serviceInfoManage//exportServiceInfo");

        //管理员权限所有url
        List<String> listUrlAdmin = new ArrayList<>();
        listUrlAdmin.add("/indexA/indexAdmin");
        listUrlAdmin.add("/indexA/welcome");
        listUrlAdmin.add("/client/list");
        listUrlAdmin.add("/client/zhufu");
        listUrlAdmin.add("/client/excelWrite");
        listUrlAdmin.add("/client/addPage");
        listUrlAdmin.add("/client/editPage");
        listUrlAdmin.add("/manager/list");
        listUrlAdmin.add("/manager/editPage");
        listUrlAdmin.add("/manager/excelWrite");
        listUrlAdmin.add("/manager/add");


        //静态资源放行
        if (url.endsWith("js") || url.endsWith("css") ||
                url.endsWith("jpg") || url.endsWith("png") || url.endsWith("gif") ||
                url.endsWith("svg") || url.endsWith("eot") || url.endsWith("ttf") || url.endsWith("woff")) {
            //不拦截，放行
            filterChain.doFilter(servletRequest, servletResponse);
        }


        //所有ajax请求放行
        if (request.getHeader("x-requested-with") != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
            //不拦截，放行
            filterChain.doFilter(servletRequest, servletResponse);

        }

        //客户反馈页面放行
        else if (url.equals("/serviceInfoManage/serviceFeedback")){
            filterChain.doFilter(servletRequest, servletResponse);
        }

        //所有页面请求(不包括登录页面)处理拦截
        else {
            //不是客户经理和管理员 或者客户经理和管理员第一次登录前
            if (manager == null && adminInfo == null) {

                for (int i = 0; i < listUrlAll.size(); i++) {
                    if (url.equals(listUrlAll.get(i))) {
                        //所有页面拦截,回到登录页面
                        request.getRequestDispatcher("/login/loginPage").forward(servletRequest, servletResponse);
                        break;
                    }
                }
                //回到登录页面
                request.getRequestDispatcher("/login/loginPage").forward(servletRequest, servletResponse);
            }
            //客户经理登录
            else if (manager != null&&adminInfo == null) {
                int i = 0;
                for (; i < listUrlAdmin.size(); i++) {
                    if (url.equals(listUrlAdmin.get(i))) {
                        //所有管理员权限页面拦截，回到登录页面
                        request.getRequestDispatcher("/login/loginPage").forward(servletRequest, servletResponse);
                        break;
                    }
                }
                //不拦截，放行
                if (i == listUrlAdmin.size())
                    filterChain.doFilter(servletRequest, servletResponse);
            }
            //管理员登录
            else if (adminInfo != null&&manager == null) {
                int i = 0;
                for (; i < listUrlManager.size(); i++) {
                    if (url.equals(listUrlManager.get(i))) {
                        //所有客户经理权限页面拦截，回到登录页面
                        request.getRequestDispatcher("/login/loginPage").forward(servletRequest, servletResponse);
                        break;
                    }
                }
                if (i == listUrlManager.size())
                    //不拦截，放行
                    filterChain.doFilter(servletRequest, servletResponse);
            }
            //管理员和客户经理都登录过
            else {
                //不拦截，放行
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }

    }

    @Override
    public void destroy() {

    }
}