package mypack;
import mypack.service.BusinessService;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class DBServlet extends HttpServlet {
  public void doGet(HttpServletRequest request,
    HttpServletResponse response)
    throws ServletException, IOException {

    doPost(request, response);

  }

  public void doPost(HttpServletRequest request,
    HttpServletResponse response)throws ServletException, IOException {
    try{
      response.setContentType("text/html;charset=GBK");
      new BusinessService().test(this.getServletContext(),response.getWriter());
    }catch(Exception e){e.printStackTrace();}
  }
}




/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<精通Hibernate：Java对象持久化技术详解>>  *
 * 技术支持网址：www.javathinker.org                *
 ***************************************************/
