package com.infoworks.lab.domain.repository;

import com.infoworks.lab.domain.entities.Gender;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.exceptions.HttpInvocationException;
import com.infoworks.lab.rest.models.ItemCount;
import com.infoworks.lab.rest.template.Interactor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import java.io.IOException;
import java.util.List;

public class RiderRepositoryTest {

    private RiderRepository repository;

    public RiderRepository getRepository() {
        if (repository == null){
            try {
                repository = Interactor.create(RiderRepository.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return repository;
    }

    @Rule
    public final EnvironmentVariables env = new EnvironmentVariables();

    @Before
    public void before() {
        env.set("app.rider.host", "localhost");
        env.set("app.rider.port", "8080");
        env.set("app.rider.api", "rider");
    }

    @Test
    public void envTest(){
        Assert.assertTrue(System.getenv("app.rider.host").equalsIgnoreCase("localhost"));
        Assert.assertTrue(System.getenv("app.rider.port").equalsIgnoreCase("8080"));
        Assert.assertTrue(System.getenv("app.rider.api").equalsIgnoreCase("rider"));
    }

    @Test
    public void rowCount() throws IOException, HttpInvocationException {
        ItemCount count = getRepository().rowCount();
        System.out.println(count.getCount());
    }

    @Test
    public void fetch() throws HttpInvocationException, IOException {
        ItemCount count = getRepository().rowCount();
        int max = count.getCount().intValue();
        int limit = 5;
        int page = 0;
        int numOfPage = (max / limit) + 1;
        while (page < numOfPage){
            List<Rider> riders = getRepository().fetch(page, limit);
            riders.forEach(rider -> System.out.println(rider.getName()));
            page++;
        }
    }

    @Test
    public void doa() throws HttpInvocationException {
        //Create & Insert:
        Rider created = getRepository()
                .insert(new Rider("Tictoc", Gender.NONE, 18));
        System.out.println("Created: " + created.getName());
        //Update:
        created.setName("Tictoc-up");
        Rider update = getRepository().update(created);
        System.out.println("Updated: " + update.getName());
        //Delete:
        boolean isDeleted = getRepository().delete(update.getId());
        System.out.println("Is Deleted : " + isDeleted);
    }

}