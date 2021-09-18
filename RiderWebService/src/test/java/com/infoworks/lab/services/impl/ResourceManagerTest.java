package com.infoworks.lab.services.impl;

import com.infoworks.lab.cryptor.definition.Cryptor;
import com.infoworks.lab.cryptor.impl.AESCryptor;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class ResourceManagerTest {

    @Test
    public void readJson(){
        ResourceManager manager = new ResourceManager();
        String json = manager.readAsString("data/rider-mock-data.json");
        System.out.println(json);

        List<Map<String, Object>> jObj = manager.readAsJsonObject(json);
        System.out.println(jObj.toString());
    }

    @Test
    public void imageAsString() throws URISyntaxException, IOException {
        String secret = "my-country-man";
        Cryptor cryptor = new AESCryptor();

        ResourceManager manager = new ResourceManager();
        File imfFile = new File("data/11812130661623646424584651857.jpg");
        InputStream ios = manager.createStream(imfFile);

        BufferedImage bufferedImage = manager.readAsImage(ios, BufferedImage.TYPE_INT_RGB);
        String base64Image = manager.readImageAsBase64(bufferedImage, ResourceManager.Format.JPEG);
        String encrypted = cryptor.encrypt(secret, base64Image);
        System.out.println("Encrypted Message: " + encrypted);

        String decryptedBase64 = cryptor.decrypt(secret, encrypted);
        BufferedImage decryptedImg = manager.readImageFromBase64(decryptedBase64);
        //
        Assert.assertEquals(base64Image, decryptedBase64);
    }

}