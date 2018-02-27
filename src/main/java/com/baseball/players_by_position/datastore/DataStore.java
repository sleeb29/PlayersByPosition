package com.baseball.players_by_position.datastore;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DataStore {

    private EmbeddedDatabase dataStore;

    public DataStore(EmbeddedDatabaseType dbType, String schemaLocation) throws IOException {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        dataStore = builder
                .setType(dbType)
                .addScript(schemaLocation)
                .build();

    }

    public EmbeddedDatabase getDataStore() {
        return dataStore;
    }

    public void setDataStore(EmbeddedDatabase dataStore) {
        this.dataStore = dataStore;
    }

}
