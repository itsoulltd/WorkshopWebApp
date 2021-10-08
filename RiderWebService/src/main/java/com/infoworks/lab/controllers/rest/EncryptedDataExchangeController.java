package com.infoworks.lab.controllers.rest;

import com.infoworks.lab.rest.models.Message;
import com.infoworks.lab.rest.models.Response;
import com.infoworks.lab.services.iServices.iEncryptedDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/encrypt")
public class EncryptedDataExchangeController {

    private static Logger LOG = Logger.getLogger(EncryptedDataExchangeController.class.getSimpleName());
    private iEncryptedDataService dataService;

    public EncryptedDataExchangeController(iEncryptedDataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/save/{alias}/{secret}")
    public String saveSecret(@PathVariable("alias") String alias
            , @PathVariable("secret") String secret) {
        //
        Response response = new Response()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Successfully Saved InMemory! on next run data will not recovered.");
        dataService.saveSecret(alias, secret);
        return response.toString();
    }

    @GetMapping("/getString/{alias}")
    public String encryptString(@PathVariable("alias") String alias){
        String secret = dataService.retrieveSecret(alias);
        return "";
    }

    @PostMapping("/postMessage/{alias}")
    public ResponseEntity<Response> postResponse(@PathVariable("alias") String alias
                                    , @RequestBody Message msg){
        //
        String secret = dataService.retrieveSecret(alias);
        String encryptedPayload = msg.getPayload();
        //
        Response response = new Response().setMessage("")
                .setStatus(HttpStatus.OK.value());
        //
        return ResponseEntity.ok(response);
    }

}
