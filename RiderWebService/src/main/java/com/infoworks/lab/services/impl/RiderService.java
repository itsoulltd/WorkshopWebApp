package com.infoworks.lab.services.impl;

import com.infoworks.lab.cryptor.definition.Cryptor;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.domain.repositories.RiderRepository;
import com.infoworks.lab.services.iServices.iRiderService;
import com.it.soul.lab.data.simple.SimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RiderService implements iRiderService {

    private RiderRepository repository;
    private SimpleDataSource<String, String> dataSource;
    private Cryptor cryptor;

    public RiderService(RiderRepository repository
            , @Qualifier("secretInMemDatasource") SimpleDataSource<String, String> dataSource
            , Cryptor cryptor) {
        this.repository = repository;
        this.dataSource = dataSource;
        this.cryptor = cryptor;
    }

    @Override
    public Rider add(Rider aRider) {
        return repository.save(aRider);
    }

    @Override
    public Rider update(Rider aRider) {
        return repository.save(aRider);
    }

    @Override
    public boolean remove(Integer userid) {
        if (repository.existsById(userid)){
            repository.deleteById(userid);
            return true;
        }
        return false;
    }

    @Override
    public Long totalCount() {
        return repository.count();
    }

    @Override
    public Rider findByUserID(Integer userid) {
        Optional<Rider> isFound = repository.findById(userid);
        if (isFound.isPresent()) return isFound.get();
        else return null;
    }

    @Override
    public List<Rider> findAllByUserID(List<Integer> userid) {
        return repository.findAllById(userid);
    }

    @Override
    public List<Rider> findAll(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public String encrypt(String secretKey, String base64Image) {
        String secret = dataSource.read(secretKey);
        String encrypted = cryptor.encrypt(secret, base64Image);
        return encrypted;
    }

    @Override
    public void saveSecret(String key, String value) {
        dataSource.put(key, value);
    }
}
