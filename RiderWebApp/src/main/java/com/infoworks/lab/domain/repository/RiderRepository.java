package com.infoworks.lab.domain.repository;

import com.infoworks.lab.client.jersey.HttpTemplate;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.exceptions.HttpInvocationException;
import com.infoworks.lab.jsql.DataSourceKey;
import com.infoworks.lab.rest.models.*;
import com.infoworks.lab.rest.template.Invocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RiderRepository extends HttpTemplate<Response, Message> {

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

    public static DataSourceKey getKeyContainer(){
        //
        DataSourceKey container = new DataSourceKey();
        container.set(DataSourceKey.Keys.SCHEMA, "http://");
        //
        String host = System.getenv("app.rider.host");
        container.set(DataSourceKey.Keys.HOST, host);
        //
        String port = System.getenv("app.rider.port");
        container.set(DataSourceKey.Keys.PORT, port);
        //
        String name = System.getenv("app.rider.api");
        container.set(DataSourceKey.Keys.NAME, name);
        //
        return container;
    }

    public ItemCount rowCount() throws IOException, HttpInvocationException {
        javax.ws.rs.core.Response response = execute(null, Invocation.Method.GET, "rowCount");
        ItemCount iCount = inflate(response, ItemCount.class);
        return iCount;
    }

    public List<Rider> fetch(Integer page, Integer limit) throws HttpInvocationException {
        Response items = get(null, new QueryParam("page", page.toString()), new QueryParam("limit", limit.toString()));
        if (items instanceof ResponseList){
            List<Rider> collection = ((ResponseList)items).getCollections();
            return collection;
        }
        return new ArrayList<>();
    }

    public Rider insert(Rider rider) throws HttpInvocationException {
        Rider response = (Rider) post(rider);
        return response;
    }

    public Rider update(Rider rider) throws HttpInvocationException {
        Rider response = (Rider) put(rider);
        return response;
    }

    public boolean delete(Integer userId) throws HttpInvocationException {
        boolean isDeleted = delete(null, new QueryParam("userid", userId.toString()));
        return isDeleted;
    }

}
