package com.infoworks.lab.services.iServices;

import com.infoworks.lab.domain.entities.Rider;

import java.util.List;

public interface iRiderService {
    Rider add(Rider aRider);
    Rider update(Rider aRider);
    boolean remove(Integer userid);
    Long totalCount();
    Rider findByUserID(Integer userid);
    List<Rider> findAllByUserID(List<Integer> userid);
    List<Rider> findAll(Integer page, Integer size);
}
