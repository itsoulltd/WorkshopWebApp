package com.infoworks.lab.domain.executor;

import com.infoworks.lab.components.rest.RestExecutor;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.domain.repository.RiderRepository;
import com.infoworks.lab.exceptions.HttpInvocationException;
import com.infoworks.lab.rest.models.ItemCount;
import com.infoworks.lab.rest.template.Interactor;
import com.it.soul.lab.sql.query.*;
import com.it.soul.lab.sql.query.models.Row;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RiderExecutor extends RestExecutor {

    private RiderRepository repository;
    private int maxItemCount;

    public RiderExecutor() {
        super(Rider.class, RiderRepository.getKeyContainer());
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

    @Override
    public Integer getScalarValue(SQLScalarQuery query) throws SQLException {
        try {
            ItemCount count = getRepository().rowCount();
            maxItemCount = count.getCount().intValue();
            return maxItemCount;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return super.getScalarValue(query);
    }

    @Override
    public List executeSelect(SQLSelectQuery query, Class aClass, Map map) throws SQLException, IllegalArgumentException, IllegalAccessException, InstantiationException {
        int limit = query.getLimit();
        int offset = query.getOffset();
        int page = offset / limit;
        if (offset > maxItemCount) return new ArrayList();
        System.out.println(String.format("Offset:%s, Limit:%s, Page:%s", offset, limit, page));
        try {
            List returned = getRepository().fetch(page, limit);
            return returned;
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }

    @Override
    public Integer executeInsert(boolean b, SQLInsertQuery query) throws SQLException, IllegalArgumentException {
        try {
            Rider request = query.getRow().inflate(Rider.class);
            Rider updated = getRepository().insert(request);
            return updated.getId();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer executeUpdate(SQLUpdateQuery query) throws SQLException {
        try {
            Rider request = query.getRow().inflate(Rider.class);
            Row row = query.getWhereProperties();
            Map kvo = row.keyObjectMap();
            String id = kvo.get("id").toString();
            request.setId(Integer.valueOf(id));
            //
            Rider updated = getRepository().update(request);
            return updated.getId();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer executeDelete(SQLDeleteQuery query) throws SQLException {
        Row row = query.getWhereProperties();
        Map kvo = row.keyObjectMap();
        String id = kvo.get("id").toString();
        try {
            boolean isDeleted = getRepository().delete(Integer.valueOf(id));
            System.out.println("Is Deleted: " + isDeleted);
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
