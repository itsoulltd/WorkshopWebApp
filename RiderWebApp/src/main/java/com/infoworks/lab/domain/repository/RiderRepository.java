package com.infoworks.lab.domain.repository;

import com.infoworks.lab.client.jersey.HttpTemplate;
import com.infoworks.lab.components.rest.RestRepository;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.exceptions.HttpInvocationException;
import com.infoworks.lab.rest.models.*;
import com.infoworks.lab.rest.template.Invocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RiderRepository extends HttpTemplate<Response, Message> implements RestRepository<Rider, Integer> {

    public RiderRepository() {
        super(Rider.class, Message.class);
    }

    @Override
    protected String schema() {
        return "http://";
    }

    @Override
    protected String host() {
        return System.getenv("app.rider.host");
    }

    @Override
    protected Integer port() {
        return Integer.valueOf(System.getenv("app.rider.port"));
    }

    @Override
    protected String api() {
        return System.getenv("app.rider.api");
    }

    @Override
    public String getPrimaryKeyName() {
        return "id";
    }

    @Override
    public Class<Rider> getEntityType() {
        return Rider.class;
    }

    public ItemCount rowCount() throws RuntimeException {
        try {
            javax.ws.rs.core.Response response = execute(null, Invocation.Method.GET, "rowCount");
            ItemCount iCount = inflate(response, ItemCount.class);
            return iCount;
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ItemCount();
    }

    public List<Rider> fetch(Integer page, Integer limit) throws RuntimeException {
        try {
            Response items = get(null, new QueryParam("page", page.toString()), new QueryParam("limit", limit.toString()));
            if (items instanceof ResponseList){
                List<Rider> collection = ((ResponseList)items).getCollections();
                return collection;
            }
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Rider insert(Rider rider) throws RuntimeException {
        try {
            Rider response = (Rider) post(rider);
            return response;
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Rider update(Rider rider, Integer userid) throws RuntimeException {
        try {
            rider.setId(userid);
            Rider response = (Rider) put(rider);
            return response;
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(Integer userId) throws RuntimeException {
        try {
            boolean isDeleted = delete(null, new QueryParam("userid", userId.toString()));
            return isDeleted;
        } catch (HttpInvocationException e) {
            e.printStackTrace();
        }
        return false;
    }

}
