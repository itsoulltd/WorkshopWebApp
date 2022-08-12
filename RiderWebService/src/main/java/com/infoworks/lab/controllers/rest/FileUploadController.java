package com.infoworks.lab.controllers.rest;

import com.infoworks.lab.rest.models.ItemCount;
import com.infoworks.lab.rest.models.SearchQuery;
import com.infoworks.lab.services.iServices.iResourceService;
import com.infoworks.lab.services.impl.LocalStorageService;
import com.infoworks.lab.services.impl.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/files")
public class FileUploadController {

    private LocalStorageService storageService;
    private iResourceService resourceService;
    private static Logger LOG = Logger.getLogger(FileUploadController.class.getSimpleName());

    @Autowired
    public FileUploadController(@Qualifier("local") LocalStorageService storageService
                , iResourceService resourceService) {
        this.storageService = storageService;
        this.resourceService = resourceService;
    }

    @GetMapping("/rowCount")
    public ItemCount getRowCount(){
        ItemCount count = new ItemCount();
        count.setCount(Integer.valueOf(storageService.size()).longValue());
        return count;
    }

    @GetMapping
    public ResponseEntity<List<String>> query(@RequestParam("limit") Integer limit
            , @RequestParam("offset") Integer offset){
        List<String> names = Arrays.asList(storageService.readKeys());
        return ResponseEntity.ok(names);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadContent(
            @RequestParam("content") MultipartFile content,
            RedirectAttributes redirectAttributes) throws IOException {
        //Store-InMemory First:
        storageService.put(content.getOriginalFilename(), content.getInputStream());
        //storageService.save(false);
        return ResponseEntity.ok("Content Received: " + content.getOriginalFilename());
    }

    @PostMapping("/upload/image/base64")
    public ResponseEntity<Map> uploadStringContent(@RequestBody SearchQuery content) throws IOException {
        //
        Map<String,String> document = new HashMap<>();
        document.put("name", content.get("name", String.class));
        document.put("contentType", content.get("contentType", String.class));
        document.put("description", content.get("description", String.class));
        String base64Str = content.get("content", String.class);
        if (base64Str != null && !base64Str.isEmpty()) {
            document.put("content", base64Str);
            //Save into temp-store:
            String imageName = document.get("name");
            String formatName = imageName.substring(imageName.lastIndexOf(".") + 1);
            BufferedImage img = resourceService.readImageFromBase64(base64Str);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, formatName, os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            //Store-InMemory First:
            storageService.put(document.get("name"), is);
            //storageService.save(false);
            is.close();
            os.close();
            //
            Map mp = new HashMap();
            mp.put("uuid", document.get("name"));
            mp.put("name", document.get("name"));
            mp.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.ok(mp);
        }
        Map<String, String> data = new HashMap<>();
        data.put("error", "content is empty or null.");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadContent(@RequestParam("fileName") String fileName) throws IOException {
        /*String fileName = "11812130661623646424584651857.jpg";
        File file = new File("sample/" + fileName);
        LOG.info("File path: " + file.getAbsolutePath());
        //
        InputStream ios = resourceService.createStream(file);
        int contentLength = file.length();
        */
        InputStream ios = storageService.read(fileName);
        int contentLength = ios.available();
        ByteArrayResource resource = new ByteArrayResource(resourceService.readAsBytes(ios));
        ios.close();
        //
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", fileName));
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        //
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(contentLength)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/download/image/base64")
    public ResponseEntity<Map> downloadBase64Image(@RequestParam("fileName") String fileName)
            throws IOException {
        //
        InputStream ios = storageService.read(fileName);
        int contentLength = ios.available();
        BufferedImage bufferedImage = resourceService.readAsImage(ios, BufferedImage.TYPE_INT_RGB);
        String base64Image = resourceService.readImageAsBase64(bufferedImage, ResourceService.Format.JPEG);
        ios.close();
        //
        Map data = new HashMap();
        data.put("content", base64Image);
        data.put("name", fileName);
        data.put("length", contentLength);
        return ResponseEntity.ok(data);
    }

}
