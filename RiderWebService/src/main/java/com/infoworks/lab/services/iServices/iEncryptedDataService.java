package com.infoworks.lab.services.iServices;

public interface iEncryptedDataService {
    String decrypt(String alias, String source);
    String encrypt(String alias, String source);
    void saveSecret(String alias, String secret);
    String retrieveSecret(String alias);
}
