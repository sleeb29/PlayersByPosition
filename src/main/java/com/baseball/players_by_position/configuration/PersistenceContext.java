package com.baseball.players_by_position.configuration;

import com.baseball.players_by_position.datastore.DataStore;
import com.baseball.players_by_position.repository.PlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class PersistenceContext {

    @Bean
    EmbeddedDatabase dataSource() throws IOException {
        String dbTypeAsString = "H2";
        EmbeddedDatabaseType dbType = EmbeddedDatabaseType.valueOf(dbTypeAsString);
        String schemaLocation = "datastore/create-temp-position-table.sql";

        DataStore dataStore = new DataStore(dbType, schemaLocation);

        return dataStore.getDataStore();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IOException {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(PlayerRepository.class.getPackage().getName());
        entityManagerFactoryBean.setJpaProperties(new Properties() {{
            put("hibernate.current_session_context_class", SpringSessionContext.class.getName());
        }});
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {{
            setDatabase(Database.H2);
        }});

        return entityManagerFactoryBean;
    }

    @Bean
    PlatformTransactionManager transactionManager() throws IOException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}