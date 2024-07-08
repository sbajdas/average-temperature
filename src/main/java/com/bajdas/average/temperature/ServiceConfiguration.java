package com.bajdas.average.temperature;

import org.apache.spark.sql.SparkSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableCaching
public class ServiceConfiguration {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    @ConditionalOnProperty(
        value = "spark.ingestion",
        havingValue = "true")
    public SparkSession sparkSession() {
        return SparkSession
            .builder()
            .appName("Java Spark SQL basic example")
            .config("spark.master", "local[*]")
            .config("spark.executor.cores", 4)
            .config("spark.sql.legacy.timeParserPolicy", "LEGACY")
            .config("spark.sql.files.maxPartitionBytes", "128MB")
            .getOrCreate();
    }
}
