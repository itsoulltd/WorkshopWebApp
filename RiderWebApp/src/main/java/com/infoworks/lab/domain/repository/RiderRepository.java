package com.infoworks.lab.domain.repository;

import com.infoworks.lab.client.jersey.HttpTemplate;
import com.infoworks.lab.domain.entities.Rider;
import com.infoworks.lab.jsql.DataSourceKey;
import com.infoworks.lab.rest.models.Message;

public class RiderRepository extends HttpTemplate<Rider, Message> {

    public RiderRepository() {
        super(Rider.class, Message.class);
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
        return System.getenv("app.rider.host");
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

}
