package com.infoworks.lab.services.impl;

import com.infoworks.lab.domain.entities.Gender;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.domain.repositories.RiderRepository;
import com.infoworks.lab.webapp.config.TestJPAConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {TestJPAConfig.class})
public class RiderServiceTest {
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    RiderRepository repository;

    @InjectMocks
    RiderService service;

    @Test
    public void happyPathTest(){
        //Defining Mock Object:
        Rider aRider = new Rider("Tictok", Gender.MALE, 36);
        when(repository.save(any(Rider.class))).thenReturn(aRider);

        //Call controller to make the save:
        Rider nRider = service.add(aRider);

        //Verify:
        assertNotNull(nRider);
        assertNotNull(nRider.getId());
        assertEquals("Tictok", nRider.getName());
    }
}