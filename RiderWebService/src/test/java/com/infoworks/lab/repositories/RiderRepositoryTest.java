package com.infoworks.lab.repositories;

import com.infoworks.lab.domain.entities.Gender;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.domain.repositories.RiderRepository;
import com.infoworks.lab.webapp.config.TestJPAConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestJPAConfig.class})
@Transactional
@TestPropertySource(locations = {"classpath:h2-db.properties"})
public class RiderRepositoryTest {

    @Autowired
    RiderRepository repository;

    @Test
    public void insertRider(){
        Rider rider = new Rider("Sayed The Coder", Gender.MALE, 24);
        repository.save(rider);

        List<Rider> list = repository.findByName("Sayed The Coder");
        Assert.assertTrue(Objects.nonNull(list));

        if (list != null && list.size() > 0){
            Rider rider2 = list.get(0);
            Assert.assertTrue(Objects.equals(rider.getName(), rider2.getName()));
        }
    }

    @Test
    public void updateRider(){
        //TODO
    }

    @Test
    public void deleteRider(){
        //TODO
    }

    @Test
    public void countRider(){
        //
        Rider rider = new Rider("Sayed The Coder", Gender.MALE, 24);
        repository.save(rider);

        long count = repository.count();
        Assert.assertTrue(count == 1);
    }

    @Test
    public void fetchRider(){
        //
        repository.save(new Rider("Sayed The Coder", Gender.MALE, 24));
        repository.save(new Rider("Evan The Pankha Coder", Gender.MALE, 24));
        repository.save(new Rider("Razib The Pagla", Gender.MALE, 26));
        //
        Page<Rider> paged = repository.findAll(PageRequest.of(0
                , 10
                , Sort.by(Sort.Order.asc("name"))));
        paged.get().forEach(rider -> System.out.println(rider.getName()));
    }
}