package com.infoworks.lab.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.rest.models.ItemCount;
import com.infoworks.lab.services.iServices.iRiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rider")
public class RiderController {

    //private SimpleDataSource<String, Rider> dataSource;
    private iRiderService service;
    private ObjectMapper mapper;

    @Autowired
    public RiderController(iRiderService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> getHello() throws JsonProcessingException {
        ItemCount count = new ItemCount();
        count.setCount(12l);
        return ResponseEntity.ok(mapper.writeValueAsString(count));
    }

    @GetMapping("/rowCount")
    public ItemCount getRowCount(){
        ItemCount count = new ItemCount();
        count.setCount(service.totalCount());
        return count;
    }

    @GetMapping
    public List<Rider> query(@RequestParam("limit") Integer size
            , @RequestParam("offset") Integer page){
        //TODO: Test with RestExecutor
        List<Rider> riders = service.findAll(page, size);
        return riders;
    }

    @PostMapping
    public Rider insert(@Valid @RequestBody Rider rider){
        //TODO: Test with RestExecutor
        Rider nRider = service.add(rider);
        return nRider;
    }

    @PutMapping
    public Rider update(@Valid @RequestBody Rider rider){
        //TODO: Test with RestExecutor
        Rider old = service.update(rider);
        return old;
    }

    @DeleteMapping
    public Boolean delete(@RequestParam("userid") Integer userid){
        //TODO: Test with RestExecutor
        boolean deleted = service.remove(userid);
        return deleted;
    }

}
