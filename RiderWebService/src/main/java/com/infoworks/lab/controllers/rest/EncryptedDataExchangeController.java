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
    public Response saveSecret(@PathVariable("alias") String alias
            , @PathVariable("secret") String secret) {
        //
        Response response = new Response()
                .setStatus(HttpStatus.OK.value())
                .setMessage("Successfully Saved InMemory! on next run data will not recovered.");
        dataService.saveSecret(alias, secret);
        return response;
    }

    @GetMapping("/{alias}")
    public Message encryptString(@PathVariable("alias") String alias){
        //
        return new Response()
                .setStatus(HttpStatus.OK.value())
                .setMessage("hi there!")
                .setPayload(dataService.encrypt(alias, "My return message from encrypted service call."));
    }

    @PostMapping("/message/{alias}")
    public ResponseEntity<Message> postResponse(@PathVariable("alias") String alias
                                    , @RequestBody Message msg){
        //
        String encryptedPayload = msg.getPayload();
        LOG.info("Encrypted Received: " + encryptedPayload);
        if (encryptedPayload != null && !encryptedPayload.isEmpty()) {
            String plainText = dataService.decrypt(alias, encryptedPayload);
            LOG.info("Decrypted Msg: " + plainText);
        }
        //
        Response response = new Response().setMessage("Successfully Decrypted")
                .setStatus(HttpStatus.OK.value());
        response.setPayload(dataService.encrypt(alias, "My return message from plain-message call."));
        return ResponseEntity.ok(response);
    }

}
