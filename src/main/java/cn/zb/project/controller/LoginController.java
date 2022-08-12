package cn.zb.project.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.zb.project.entity.User;
import cn.zb.project.service.UserService;
import cn.zb.project.vo.DataView;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    //跳转到登陆页面
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * 验证码逻辑
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("/login/getCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        //验证码对象
        /**
         * 创建圆圈干扰的验证码
         * Params:
         * width – 图片宽
         * height – 图片高
         * codeCount – 字符个数
         * circleCount – 干扰圆圈条数
         * Returns:
         * CircleCaptcha
         * Since:
         * 3.2.3
         */
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(116, 36, 4, 10);

        String code = captcha.getCode();//获取验证码

        //放入到session
        session.setAttribute("code",code);

        //输出验证码
        ServletOutputStream outputStream = response.getOutputStream();
        captcha.write(outputStream);

        //关闭输出流
        outputStream.close();
    }


    //登录逻辑
    @RequestMapping("/login/login")
    @ResponseBody
    public DataView login(String username, String password, String code, HttpSession session){
        DataView dataView = new DataView();
        //先判断验证码是否正确
        String sessionCode = (String) session.getAttribute("code");
        if(code != null && sessionCode.equals(code)){
            //session普通登录
            //User user = userService.login(username, password);

            //用shiro做token的登录
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            User user = (User) subject.getPrincipal();


            if (user != null){
                dataView.setCode(200);
                dataView.setMsg("登录成功！");
                //放入session
                session.setAttribute("user",user);
                return dataView;
            }else {
                dataView.setCode(100);
                dataView.setMsg("密码或用户名错误！");
            }
        }
        dataView.setCode(200);
        dataView.setMsg("验证码错误");
        return dataView;
    }

    /**
     * 退出登录
     * @return
     */
    @RequestMapping("/login/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

}
