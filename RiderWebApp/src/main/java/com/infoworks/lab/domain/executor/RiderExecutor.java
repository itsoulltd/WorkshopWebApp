package com.infoworks.lab.domain.executor;

import com.infoworks.lab.components.rest.RestRepositoryExecutor;
import com.infoworks.lab.domain.repository.RiderRepository;
import com.infoworks.lab.rest.template.Interactor;

public class RiderExecutor extends RestRepositoryExecutor {

    private RiderRepository repository;

    public RiderExecutor() {
        super(null);
    }

    public RiderRepository getRepository() {
        if (repository == null){
            try {
                repository = Interactor.create(RiderRepository.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return repository;
    }
}
