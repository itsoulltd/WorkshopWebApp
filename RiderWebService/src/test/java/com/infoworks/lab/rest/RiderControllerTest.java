package com.infoworks.lab.rest;

import com.infoworks.lab.controllers.rest.RiderController;
import com.infoworks.lab.domain.entities.Gender;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.rest.models.ItemCount;
import com.infoworks.lab.webapp.WebApplicationTest;
import com.infoworks.lab.webapp.config.BeanConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebApplicationTest.class, BeanConfig.class, RiderController.class})
@TestPropertySource(locations = {"classpath:h2-db.properties"})
public class RiderControllerTest {

    @Value("${app.db.name}")
    private String dbName;

    @Rule
    public final EnvironmentVariables env = new EnvironmentVariables();

    @Before
    public void before() {
        env.set("my.system.env", "my-env");
        env.set("app.db.name", dbName);
    }

    @Test
    public void envTest(){
        Assert.assertTrue(System.getenv("my.system.env").equalsIgnoreCase("my-env"));
    }

    @Autowired
    private RiderController controller;

    @Test
    public void initiateTest(){
        System.out.println("Integration Tests");
    }

    @Test
    public void count(){
        //
        controller.insert(new Rider("Sayed The Coder", Gender.MALE, 24));
        //
        ItemCount count = controller.getRowCount();
        System.out.println(count.getCount());
    }

    @Test
    public void query(){
        //
        controller.insert(new Rider("Sayed The Coder", Gender.MALE, 24));
        controller.insert(new Rider("Evan The Pankha Coder", Gender.MALE, 24));
        controller.insert(new Rider("Razib The Pagla", Gender.MALE, 26));
        //
        int size = Long.valueOf(controller.getRowCount().getCount()).intValue();
        List<Rider> items = controller.query(size, 0);
        items.stream().forEach(rider -> System.out.println(rider.getName()));
    }

}