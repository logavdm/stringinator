package com.comcast.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheManagementController {

	@Autowired
	private CacheManager cacheManager;

	@GetMapping("/clear/cache")
	public String clearAllCache() {
		cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());
		return "Cache cleared";
	}

}
