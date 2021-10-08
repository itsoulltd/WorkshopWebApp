package com.infoworks.lab.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.rest.models.ItemCount;
import com.infoworks.lab.services.iServices.iEncryptedDataService;
import com.infoworks.lab.services.iServices.iResourceService;
import com.infoworks.lab.services.iServices.iRiderService;
import com.infoworks.lab.services.impl.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/rider")
public class RiderController {

    private static Logger LOG = Logger.getLogger(RiderController.class.getSimpleName());
    private iRiderService service;
    private ObjectMapper mapper;
    private iResourceService resourceService;
    private iEncryptedDataService dataService;

    @Autowired
    public RiderController(iRiderService service
            , ObjectMapper mapper
            , iResourceService resourceService
            , @Qualifier("ImageDataService") iEncryptedDataService dataService) {
        this.service = service;
        this.mapper = mapper;
        this.resourceService = resourceService;
        this.dataService = dataService;
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
            , @RequestParam("page") Integer page){
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

    @GetMapping("/photo/{userid}")
    public String getAlbumItem(@PathVariable("userid") Integer userid
            , @RequestParam("imgPath") String imgPath) throws IOException {
        //
        LOG.info(String.format("Received: %s: %s", userid, imgPath));
        File imfFile = new File(imgPath);
        InputStream ios = resourceService.createStream(imfFile);
        //
        BufferedImage bufferedImage = resourceService.readAsImage(ios, BufferedImage.TYPE_INT_RGB);
        String base64Image = resourceService.readImageAsBase64(bufferedImage, ResourceService.Format.JPEG);
        String encrypted = dataService.encrypt("defaultKey", base64Image);
        //
        Map data = new HashMap();
        data.put("img", encrypted);
        return mapper.writeValueAsString(data);
    }

    @GetMapping("/photos/{userid}")
    public List<String> getAlbums(@PathVariable("userid") Integer userid){
        return Arrays.asList("sample/11812130661623646424584651857.jpg"
                , "sample/18781305151623645845496247515.jpg"
                , "sample/120979773116236458451800012549.jpg"
                , "sample/154657097816236464251755712367.jpg"
                , "sample/158286147416236458461735865194.jpg");
    }

}
