package com.example.demo.conf;

import com.example.demo.converter.MongoConverter;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Slf4j
@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableMongoRepositories(basePackages = "com.example.demo.model.mongoRepository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${mongodb.dbName}")
    private String dbName;

    @Value("${mongodb.connectionString}")
    private String connectionString;

    @Autowired
    private List<MongoConverter.MongoConverterInterface> mongoConverters;

    @Override
    protected @NonNull String getDatabaseName() {
        return dbName;
    }

    public @NonNull MongoClientSettings mongoClientSettings() {
        // Mapping the POJO codec for Object Class Mapping
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        return MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(new ConnectionString(connectionString)).build();
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        log.info("converters: {}", mongoConverters);
        return new MongoCustomConversions(mongoConverters);
    }

    @Bean
    @NonNull
    public MongoClient mongoClient() {
        return MongoClients.create(mongoClientSettings());
    }

    @Override
    @NonNull
    public MongoTemplate mongoTemplate(@NonNull MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {
        converter.setCustomConversions(mongoCustomConversions());
        converter.afterPropertiesSet();

        return super.mongoTemplate(databaseFactory, converter);
    }

    @Bean
    @Primary
    @Override
    public @NonNull MappingMongoConverter mappingMongoConverter(@NonNull MongoDatabaseFactory databaseFactory, @NonNull MongoCustomConversions customConversions, @NonNull MongoMappingContext mappingContext) {
        return super.mappingMongoConverter(databaseFactory, customConversions, mappingContext);
    }


    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
