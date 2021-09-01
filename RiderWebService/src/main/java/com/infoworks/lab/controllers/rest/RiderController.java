package com.infoworks.lab.controllers.rest;

import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.rest.models.ItemCount;
import com.it.soul.lab.data.simple.SimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rider")
public class RiderController {

    private SimpleDataSource<String, Rider> dataSource;

    @Autowired
    public RiderController(@Qualifier("riderInMemDatasource") SimpleDataSource<String, Rider> dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/rowCount")
    public ItemCount getRowCount(){
        ItemCount count = new ItemCount();
        count.setCount(Integer.valueOf(dataSource.size()).longValue());
        return count;
    }

    @GetMapping
    public List<Rider> query(@RequestParam("limit") Integer limit
            , @RequestParam("offset") Integer offset){
        //TODO: Test with RestExecutor
        List<Rider> riders = Arrays.asList(dataSource.readSync(offset, limit));
        return riders;
    }

    @PostMapping @SuppressWarnings("Duplicates")
    public ItemCount insert(@Valid @RequestBody Rider rider){
        //TODO: Test with RestExecutor
        dataSource.put(rider.getName(), rider);
        ItemCount count = new ItemCount();
        count.setCount(Integer.valueOf(dataSource.size()).longValue());
        return count;
    }

    @PutMapping @SuppressWarnings("Duplicates")
    public ItemCount update(@Valid @RequestBody Rider rider){
        //TODO: Test with RestExecutor
        Rider old = dataSource.replace(rider.getName(), rider);
        ItemCount count = new ItemCount();
        if (old != null)
            count.setCount(Integer.valueOf(dataSource.size()).longValue());
        return count;
    }

    @DeleteMapping
    public Boolean delete(@RequestParam("name") String name){
        //TODO: Test with RestExecutor
        Rider deleted = dataSource.remove(name);
        return deleted != null;
    }

}
