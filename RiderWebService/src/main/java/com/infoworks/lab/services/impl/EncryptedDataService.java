package com.infoworks.lab.services.impl;

import com.infoworks.lab.cryptor.definition.Cryptor;
import com.infoworks.lab.services.iServices.iEncryptedDataService;
import com.it.soul.lab.data.simple.SimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EncryptedDataService implements iEncryptedDataService {

    private SimpleDataSource<String, String> dataSource;
    private Cryptor cryptor;

    public EncryptedDataService( Cryptor cryptor
            , @Qualifier("secretInMemDatasource") SimpleDataSource<String, String> dataSource) {
        this.dataSource = dataSource;
        this.cryptor = cryptor;
    }

    @Override
    public String encrypt(String alias, String source) {
        String secret = retrieveSecret(alias);
        String encrypted = cryptor.encrypt(secret, source);
        return encrypted;
    }

    @Override
    public String decrypt(String alias, String source) {
        String secret = retrieveSecret(alias);
        String decrypted = cryptor.decrypt(secret, source);
        return decrypted;
    }

    @Override
    public void saveSecret(String alias, String secret) {
        dataSource.put(alias, secret);
    }

    @Override
    public String retrieveSecret(String alias) {
        return dataSource.read(alias);
    }
}
