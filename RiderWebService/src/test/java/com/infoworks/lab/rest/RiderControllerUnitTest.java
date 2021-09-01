package com.infoworks.lab.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoworks.lab.controllers.rest.RiderController;
import com.infoworks.lab.domain.entities.Gender;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.services.iServices.iRiderService;
import com.infoworks.lab.webapp.WebApplicationTest;
import com.infoworks.lab.webapp.config.BeanConfig;
import com.infoworks.lab.webapp.config.TestJPAConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = {WebApplicationTest.class, TestJPAConfig.class, BeanConfig.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:h2-db.properties")
public class RiderControllerUnitTest {

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    iRiderService service;

    @InjectMocks
    RiderController controller;

    @Test
    public void happyPathTest() throws Exception {
        //Defining Mock Object:
        Rider aRider = new Rider("Tictoc", Gender.MALE, 36);

        when(service.add(any(Rider.class))).thenReturn(aRider);

        //Call controller to make the save:
        MvcResult result = mockMvc.perform(post("/rider")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(aRider))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andExpect(status().isOk())
                .andReturn();
        String str = "Status: " + result.getResponse().getStatus();
        System.out.println(str);
        //
    }

    @Test
    public void rowCountGetTest() throws Exception {
        //Call controller to make the save:
        MvcResult result = mockMvc.perform(get("/rider/rowCount")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                //.andDo(print())
                //.andExpect(status().isOk())
                .andReturn();
        String str = "Status: " + result.getResponse().getStatus();
        System.out.println(str);
        //
    }

}
