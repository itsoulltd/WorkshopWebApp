package com.infoworks.lab.webapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoworks.lab.cryptor.definition.Cryptor;
import com.infoworks.lab.cryptor.impl.AESCryptor;
import com.infoworks.lab.cryptor.util.CryptoAlgorithm;
import com.infoworks.lab.cryptor.util.HashKey;
import com.infoworks.lab.cryptor.util.Transformation;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.rest.models.Message;
import com.infoworks.lab.services.iServices.iEncryptedDataService;
import com.infoworks.lab.services.impl.EncryptedDataService;
import com.it.soul.lab.data.simple.SimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean("HelloBean")
    public String getHello(){
        return "Hi Spring Hello";
    }

    @Bean("riderInMemDatasource")
    public SimpleDataSource<String, Rider> getRiderDatasource(){
        return new SimpleDataSource<>();
    }

    @Bean("secretInMemDatasource")
    public SimpleDataSource<String, String> getSecretDatasource(){
        SimpleDataSource dataSource = new SimpleDataSource<>();
        //Adding default secretKey + secret
        dataSource.put("defaultKey", "my-country-man");
        //
        return dataSource;
    }

    @Bean
    ObjectMapper getMapper(){
        return Message.getJsonSerializer();
    }

    @Bean("ImageDataService")
    iEncryptedDataService getImageDataService(@Qualifier("secretInMemDatasource") SimpleDataSource dataSource){
        return new EncryptedDataService(
                new AESCryptor(HashKey.SHA_1, Transformation.AES_ECB_PKCS5Padding, CryptoAlgorithm.AES)
                , dataSource);
    }

    @Bean("MessageDataService")
    iEncryptedDataService getMessageDataService(@Qualifier("secretInMemDatasource") SimpleDataSource dataSource){
        return new EncryptedDataService(
                new AESCryptor(HashKey.SHA_256, Transformation.AES_ECB_PKCS5Padding, CryptoAlgorithm.AES)
                , dataSource);
    }

}
