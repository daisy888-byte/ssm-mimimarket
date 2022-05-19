package com.bjpn.listen;

import com.bjpn.pojo.ProductType;
import com.bjpn.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext_*.xml");
        ProductTypeService service = context.getBean(ProductTypeService.class);
        List<ProductType> typeList = service.selectTypeAll();
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
