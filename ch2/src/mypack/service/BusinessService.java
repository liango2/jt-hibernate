
package mypack.service;

import javax.servlet.*;

import mypack.po.Customer;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.sql.Date;
import java.util.*;

public class BusinessService {
    public static SessionFactory sessionFactory;

    /** 初始化Hibernate，创建SessionFactory实例 */
    static {
        try {
            // 根据默认位置的Hibernate配置文件的配置信息，创建一个Configuration实例
            Configuration config = new Configuration();
            //加载Customer类的对象-关系映射文件
            config.addClass(Customer.class);
            // 创建SessionFactory实例 */
            sessionFactory = config.buildSessionFactory();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查询所有的Customer对象，然后调用printCustomer()方法打印Customer对象信息
     */
    public void findAllCustomers(ServletContext context, PrintWriter out) throws Exception {
        Session session = sessionFactory.openSession(); //创建一个会话
        Transaction tx = null;
        try {
            tx = session.beginTransaction(); //开始一个事务
            Query query = session.createQuery("from Customer as c order by c.name asc");
            List customers = query.list();
            for (Iterator it = customers.iterator(); it.hasNext(); ) {
                printCustomer(context, out, (Customer) it.next());
            }

            tx.commit(); //提交事务

        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * 持久化一个Customer对象
     */
    public void saveCustomer(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(customer);
            tx.commit();

        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }



    /**
     * 按照OID加载一个Customer对象，然后修改它的属性
     */
    public void loadAndUpdateCustomer(Long customer_id, String address) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Customer c = (Customer) session.get(Customer.class, customer_id);
            c.setAddress(address);
            tx.commit();

        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * 删除Customer对象
     */
    public void deleteCustomer(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(customer);
            tx.commit();

        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * 选择向控制台还是Web网页输出Customer对象的信息
     */
    private void printCustomer(ServletContext context, PrintWriter out, Customer customer) throws Exception {
        if (context != null)
            printCustomerInWeb(context, out, customer);
        else
            printCustomer(out, customer);
    }

    /**
     * 把Customer对象的信息输出到控制台，如DOS 控制台
     */
    private void printCustomer(PrintWriter out, Customer customer) throws Exception {
        byte[] buffer = customer.getImage();
        FileOutputStream fout = new FileOutputStream("photo_copy.gif");
        fout.write(buffer);
        fout.close();

        out.println("------以下是" + customer.getName() + "的个人信息------");
        out.println("ID: " + customer.getId());
        out.println("口令: " + customer.getPassword());
        out.println("E-Mail: " + customer.getEmail());
        out.println("电话: " + customer.getPhone());
        out.println("地址: " + customer.getAddress());
        String sex = customer.getSex() == 'M' ? "男" : "女";
        out.println("性别: " + sex);
        String marriedStatus = customer.isMarried() ? "已婚" : "未婚";
        out.println("婚姻状况: " + marriedStatus);
        out.println("生日: " + customer.getBirthday());
        out.println("注册时间: " + customer.getRegisteredTime());
        out.println("自我介绍: " + customer.getDescription());
        out.println("照片：" + customer.getImage());
    }

    /**
     * 把Customer对象的信息输出到动态网页
     */
    private void printCustomerInWeb(ServletContext context, PrintWriter out, Customer customer) throws Exception {
        //保存照片
        byte[] buffer = customer.getImage();
        String path = context.getRealPath("/");
        FileOutputStream fout = new FileOutputStream(path + "photo_copy.gif");
        fout.write(buffer);
        fout.close();

        out.println("------以下是" + customer.getName() + "的个人信息------" + "<br>");
        out.println("ID: " + customer.getId() + "<br>");
        out.println("口令: " + customer.getPassword() + "<br>");
        out.println("E-Mail: " + customer.getEmail() + "<br>");
        out.println("电话: " + customer.getPhone() + "<br>");
        out.println("地址: " + customer.getAddress() + "<br>");
        String sex = customer.getSex() == 'M' ? "男" : "女";
        out.println("性别: " + sex + "<br>");
        String marriedStatus = customer.isMarried() ? "已婚" : "未婚";
        out.println("婚姻状况: " + marriedStatus + "<br>");
        out.println("生日: " + customer.getBirthday() + "<br>");
        out.println("注册时间: " + customer.getRegisteredTime() + "<br>");
        out.println("自我介绍: " + customer.getDescription() + "<br>");
        out.println("<img src='photo_copy.gif' border=0><p>");
    }

    public void test(ServletContext context, PrintWriter out) throws Exception {

        Customer customer = new Customer();
        customer.setName("XiGuaZhu");
        customer.setEmail("xxx@qq.com");
        customer.setPassword("1234");
        customer.setPhone(55556666);
        customer.setAddress("Shanghai");
        customer.setSex('M');
        customer.setDescription("I am very honest.");

/*        //设置Customer对象的image属性，它是字节数组，存放photo.gif文件中的二进制数据
        //photo.gif文件和BusinessService.class文件位于同一个目录下
        InputStream in = this.getClass().getResourceAsStream("photo.gif");
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        customer.setImage(buffer);
        //设置Customer对象的birthday属性，它是java.sql.Date类型
        customer.setBirthday(Date.valueOf("1985-05-06"));
        customer.setId(1L);
        saveCustomer(customer); // 1. 保存
        findAllCustomers(context, out);

        loadAndUpdateCustomer(customer.getId(), "南京");
        findAllCustomers(context, out); // 2.更新
        /// deleteCustomer(customer);*/


        /**
         * 测试手动很配id的情况下savaOrUpdate方法的机制
         */
        customer.setId(1L);

        customer.setAddress("测试");
        testSaveOrUpdateCustomer(customer); // 1. 保存或更新
        /// findAllCustomers(context, out);

        customer.setAddress("测试2");
        testSaveOrUpdateCustomer(customer); // 1. 保存或更新

        customer.setId(2L);
        testSaveOrUpdateCustomer(customer); // 1. 保存或更新
    }

    /**
     * 持久化一个Customer对象
     */
    private void testSaveOrUpdateCustomer(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(customer);
            tx.commit();

        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }





    public static void main(String args[]) throws Exception {
        new BusinessService().test(null, new PrintWriter(System.out, true));
        sessionFactory.close();
    }
}





