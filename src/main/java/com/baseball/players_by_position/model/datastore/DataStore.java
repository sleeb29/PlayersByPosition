package com.baseball.players_by_position.model.datastore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class DataStore {

    private EmbeddedDatabase dataStore;

    @Value("${create_schema_file_location}")
    private String createSchemaFileLocation;
    @Value("${dbType}")
    private String dbTypeName;

    public DataStore() {

        EmbeddedDatabaseType dbType = EmbeddedDatabaseType.valueOf(dbTypeName);

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        dataStore = builder
                .setType(dbType)
                .addScript(createSchemaFileLocation)
                .build();

    }

    public EmbeddedDatabase getDataStore() {
        return dataStore;
    }

}
