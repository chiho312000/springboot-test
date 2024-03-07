package com.example.demo.conf;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.demo.model.mongoRepository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongodb.dbName}")
    private String dbName;

    @Value("${mongodb.connectionString}")
    private String connectionString;

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    public MongoClientSettings mongoClientSettings() {
        // Mapping the POJO codec for Object Class Mapping
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(new ConnectionString(connectionString)).build();
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(mongoClientSettings());
    }


    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
