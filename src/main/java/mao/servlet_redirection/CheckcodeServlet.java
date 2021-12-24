package mao.servlet_redirection;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Project name(项目名称)：Servlet_redirection
 * Package(包名): mao.servlet_redirection
 * Class(类名): CheckcodeServlet
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2021/12/24
 * Time(创建时间)： 12:58
 * Version(版本): 1.0
 * Description(描述)： 重定向属于客户端行为。
 * 服务器在收到客户端请求后，会通知客户端浏览器重新向另外一个 URL 发送请求，这称为请求重定向。它本质上是两次 HTTP 请求，
 * 对应两个 request 对象和两个 response 对象。
 * 用户在浏览器中输入 URL，请求访问服务器端的 Web 资源。
 * 服务器端的 Web 资源返回一个状态码为302的响应信息,该响应的含义为：通知浏览器再次发送请求,访问另一个Web资源（在响应信息中提供了另一个资源的 URL）。
 * 当浏览器接收到响应后，立即自动访问另一个指定的 Web 资源。
 * 另一 Web 资源将请求处理完成后，由容器把响应信息返回给浏览器进行展示。
 * 转发和重定向的区别
 * 转发和重定向都能实现页面的跳转，但是两者也存在以下区别。
 * 区别	                                     转发    重定向
 * 浏览器地址栏 URL 是否发生改变	              否	是
 * 是否支持跨域跳转	                          否	是
 * 请求与响应的次数	                  一次请求和一次响应	两次请求和两次响应
 * 是否共享 request 对象和 response 对象	        是	否
 * 是否能通过 request 域对象传递数据	            是	否
 * 速度	                                     相对要快   相对要慢
 * 行为类型	                                服务器行为	客户端行为
 * <p>
 * response.sendRedirect()
 * HttpServletResponse 接口中的 sendRedirect() 方法用于实现重定向。
 * 返回值类型	方法	描述
 * void	sendRedirect(String location)	向浏览器返回状态码为 302 的响应结果，让浏览器访问新的 URL。
 * 若指定的 URL 是相对路径，Servlet 容器会将相对路径转换为绝对路径。参数 location 表示重定向的URL。
 */

@WebServlet("/CheckcodeServlet")
public class CheckcodeServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int width = 120;
        int height = 30;
        // 在内存中生成图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 先获取画笔对象
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        // 设置灰色
        graphics.setColor(Color.GRAY);
        // 画填充的矩形
        graphics.fillRect(0, 0, width, height);
        // 设置颜色
        graphics.setColor(Color.BLUE);
        // 画边框
        graphics.drawRect(0, 0, width - 1, height - 1);
        // 准备数据，随机获取4个字符
        String words = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        // 设置颜色
        graphics.setColor(Color.YELLOW);
        // 设置字体
        graphics.setFont(new Font("隶书", Font.BOLD, 20));
        StringBuilder stringBuilder = new StringBuilder();
        //构造存储字符的数组
        char[] a = {};
        //构造存储字符串的集合
        ArrayList<String> list = new ArrayList<String>();
        Random random = new Random();
        int x = 20;
        int y = 20;
        for (int i = 0; i < 4; i++)
        {
            // void rotate(double theta, double x, double y)
            // theta 弧度
            // hudu = jiaodu * Math.PI / 180;
            // 获取正负30之间的角度
            int jiaodu = random.nextInt(60) - 30;
            double hudu = jiaodu * Math.PI / 180;
            graphics.rotate(hudu, x, y);
            // 获取下标
            int index = random.nextInt(words.length());
            // 返回指定下标位置的字符，随机获取下标
            char ch = words.charAt(index);
            //将字符存入字符数组中
            char[] chc = {ch};
            //使用字符数组构造字符串
            String string = new String(chc);
            //将构造好的字符串添加进list集合中
            list.add(string);
            // 写字符串
            graphics.drawString("" + ch, x, y);
            graphics.rotate(-hudu, x, y);
            x += 20;
        }
        for (int i = 0; i < list.size(); i++)
        {
            stringBuilder.append(list.get(i));
        }
        //将验证码存入上下文中
        getServletContext().setAttribute("code", stringBuilder.toString());
        // 设置颜色
        graphics.setColor(Color.GREEN);
        int x1, x2, y1, y2;
        // 画干扰线
        for (int i = 0; i < 4; i++)
        {
            x1 = random.nextInt(width);
            y1 = random.nextInt(height);
            x2 = random.nextInt(width);
            y2 = random.nextInt(height);
            graphics.drawLine(x1, y1, x2, y2);
        }
        // 输出到客户端
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
