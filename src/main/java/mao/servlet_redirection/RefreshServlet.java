package mao.servlet_redirection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Project name(项目名称)：Servlet_redirection
 * Package(包名): mao.servlet_redirection
 * Class(类名): RefreshServlet
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/12/24
 * Time(创建时间)： 13:35
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@WebServlet("/RefreshServlet")
public class RefreshServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        Object requestAttr = request.getAttribute("requestAttr");
        //获取存在上下文中的跳转时间
        int times = (int) getServletContext().getAttribute("time");
        //获取存在上下文中的错误信息
        String msg = (String) getServletContext().getAttribute("msg");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        //
        response.setContentType("text/html;charset=UTF-8");
        //设置提示
        String title = msg + "，即将在" +
                times + "秒钟后跳转到登录页";
        //使用默认时区和语言环境获得一个日历
        Calendar calendar = Calendar.getInstance();
        //将Calendar类型转换成Date类型
        Date tasktime = calendar.getTime();
        //设置日期输出的格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //格式化输出
        String nowTime = simpleDateFormat.format(tasktime);
        //只要倒计时时间没有到0，就直至输出下面内容
        if (times != 0)
        {
            response.getWriter().write("<html>\n" +
                    "<head><title >" + title + "</title></head>\n" +
                    "<body bgcolor=\"#f0f0f0\">\n" +
                    "<h1 align=\"center\" style=\"font-family:arial;color:red;\">" + title + "</h1>\n" +
                    "<h1 align=\"center\">当前时间 是：" + nowTime + "</h1>\n" +
                    "<h1 align=\"center\">重定向通过request传递的数据为：" + requestAttr + "</h1>\n");
            //倒计时
            times--;
            //将倒计时的时间重新存入上下文中覆盖原来的值
            getServletContext().setAttribute("time", times);
            // 通过refresh头完成页面刷新
            response.setHeader("refresh", "1;url=/Servlet_redirection_war_exploded/RefreshServlet");
        }
        else
        {
            //倒计时归零，则跳转到登陆页面
            response.sendRedirect("/Servlet_redirection_war_exploded/login.html");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        doGet(request, response);
    }
}
