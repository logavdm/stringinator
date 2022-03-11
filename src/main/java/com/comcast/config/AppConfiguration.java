package com.comcast.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching
public class AppConfiguration {
	
	private static final Logger log=LoggerFactory.getLogger(AppConfiguration.class);
	
	@Value("${cacheManager.cacheName}")
	private List<String> cacheName;

	@Bean
    public CacheManager cacheManager() {		
		log.info("Cache List :: {}",cacheName);		
		CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
	    caffeineCacheManager.setCaffeine(Caffeine.newBuilder());
        if(cacheName.size()>0) {
        	caffeineCacheManager.setCacheNames(cacheName);
        }                   
        return caffeineCacheManager;
    }
}
