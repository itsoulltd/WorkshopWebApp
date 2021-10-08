package com.infoworks.lab.services.iServices;

public interface iEncryptedDataService {
    String encrypt(String secretKey, String base64Image);
    void saveSecret(String alias, String secret);
    String retrieveSecret(String alias);
}
