package mypack;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.sql.Time;
import java.util.*;

public class BusinessService {
    public static SessionFactory sessionFactory;

    static {
        try {
            Configuration config = new Configuration();
            //加载hibernate.cfg.xml文件中配置的信息
            config.configure();
            sessionFactory = config.buildSessionFactory();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
    }

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

    public Customer loadCustomer(Long id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Customer customer = (Customer) session.get(Customer.class, id);
            tx.commit();
            return customer;
        } catch (RuntimeException e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    public void printCustomer(Customer customer) {
        System.out.println(customer);
    }

    public void test() {
        Customer customer = new Customer();
        customer.setName("Paul Butcher");
        customer.setAge(new Integer(21));
        customer.setSex(new Character('M'));
        customer.setMarried(new Boolean(false));
        customer.setDescription("Paul Butcher 资深程序员，涉猎广泛，从单片机编码到高级声明式编程无所不精。Paul是一位少年天才，8岁时就已经开始在8位机上编写游戏。最近几年他开始痴迷于赛车，想要去叫板汉密尔顿。除本书外，还著有在亚马逊获得全五星好评的《软件调试修炼之道》。");
        saveCustomer(customer);
        customer = loadCustomer(customer.getId());
        printCustomer(customer);
    }

    public static void main(String args[]) {
        new BusinessService().test();
        sessionFactory.close();
    }
}


/****************************************************
 * 作者：孙卫琴                                     *
 * 来源：<<精通Hibernate：Java对象持久化技术详解>>  *
 * 技术支持网址：www.javathinker.org                *
 ***************************************************/
