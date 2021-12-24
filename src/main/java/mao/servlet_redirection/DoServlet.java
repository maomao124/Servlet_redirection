package mao.servlet_redirection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Project name(项目名称)：Servlet_redirection
 * Package(包名): mao.servlet_redirection
 * Class(类名): DoServlet
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/12/24
 * Time(创建时间)： 13:13
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@WebServlet("/DoServlet")
public class DoServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //设置向页面输出内容格式
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String username = request.getParameter("username");
        // 获取密码
        String password = request.getParameter("password");
        // 获取性别
        String sex = request.getParameter("sex");
        // 获取城市
        String city = request.getParameter("city");
        // 获取爱好    返回是String数组
        String[] languages = request.getParameterValues("language");
        //获取验证码
        String code = request.getParameter("code");
        //设置是否成功标识
        boolean IsSuccess = true;
        //从上下文获取存储的验证码
        String code1 = (String) getServletContext().getAttribute("code");
        //账号密码为admin.且验证码(忽略大小写)输入正确，则跳转到登陆成功页面
        if (!"".equals(code) && code != null && code.equalsIgnoreCase(code1) && "admin".equals(username) && "admin".equals(password))
        {
            response.sendRedirect("/Servlet_redirection_war_exploded/Success");
            //账号密码不为admin,设置错误信息
        }
        else if (!"admin".equals(username) || !"admin".equals(password))
        {
            getServletContext().setAttribute("msg", "账号或密码不正确");
            IsSuccess = false;
            //验证码错误，设置错误信息
        }
        else if ("".equals(code) || code == null || !code.equalsIgnoreCase(code1))
        {
            getServletContext().setAttribute("msg", "验证码输入错误");
            IsSuccess = false;
        }
        if (!IsSuccess)
        {
            //设置自动跳转的时间，存储在上下文中
            getServletContext().setAttribute("time", 5);
            //向request对象中设置属性requestAttr,在重定向之后取值。
            request.setAttribute("requestAttr", "重定向中使用request域对象传递的数据");
            response.sendRedirect("/Servlet_redirection_war_exploded/RefreshServlet");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
