package com.xiaoqi.guaguaweb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoqi.guaguaweb.domain.Response;
import com.xiaoqi.guaguaweb.domain.User;
import com.xiaoqi.guaguaweb.service.UserService;
import com.xiaoqi.guaguaweb.util.HttpServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class UserController {

    /**
     * 自动注入UserService
     */
    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("userService") UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpServletUtil.setupHttpServlet(request, response);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.login(username, password);
        if (user != null) {
            Response res = new Response(0, "", user);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        } else {
            Response res = new Response(-1, "账号密码不匹配", null);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        }
        response.getWriter().close();
    }

    @RequestMapping(value = "login/token", method = RequestMethod.POST)
    public void loginWithToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpServletUtil.setupHttpServlet(request, response);
        String token = request.getParameter("token");
        User user = userService.loginWithToken(token);
        if (user != null) {
            Response res = new Response(0, "", user);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        } else {
            Response res = new Response(-1, "自动登录失败，请重新登录", null);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        }
        response.getWriter().close();
    }

    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpServletUtil.setupHttpServlet(request, response);
        String userId = request.getParameter("uid");
        User user = userService.logout(userId);
        if (user != null) {
            Response res = new Response(0, "", user);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        } else {
            Response res = new Response(-1, "参数错误", null);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        }
        response.getWriter().close();
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpServletUtil.setupHttpServlet(request, response);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.register(username, password);
        if (user != null) {
            Response res = new Response(0, "", user);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        } else {
            Response res = new Response(-1, "该用户名已被注册", null);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        }
        response.getWriter().close();
    }

    @RequestMapping(value = "/user/avatar", method = RequestMethod.POST)
    public void avatar(HttpServletRequest request, HttpServletResponse response, MultipartFile file) throws IOException{
        HttpServletUtil.setupHttpServlet(request, response);
        String username = request.getParameter("username"); // 获取
        String uid = request.getParameter("uid");
        /*
        文件名
         */
        String contentType = file.getContentType();
        contentType = contentType.substring(contentType.indexOf("/") + 1);
        String fileName = username + System.currentTimeMillis() + "." + contentType;
        /*
        文件路径
         */
        String filePath = request.getSession().getServletContext().getRealPath("/user/avatars");
        filePath = filePath + "/";
        /*
        存文件
         */
        File f = new File(filePath + fileName);
        if (!f.exists()) {
            boolean res = f.mkdirs();
            if (res) file.transferTo(f);
        } else {
            file.transferTo(f);
        }

        User user = userService.updateAvatar(uid, "user/avatars/" + fileName);
        if (user != null) {
            Response res = new Response(0, "", user);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        } else {
            Response res = new Response(-1, "上传头像失败", null);
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(res));
        }
        response.getWriter().close();
    }
}
