package com.comcast.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

@Component
public class RateLimitingFilter implements Filter {

	private int MAX_REQUESTS_PER_SECOND = 5;

	private LoadingCache<String, Integer> requestCountsPerIpAddress;

	private static final Logger log = LoggerFactory.getLogger(RateLimitingFilter.class);

	public RateLimitingFilter() {
		requestCountsPerIpAddress = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.SECONDS)
				.build(new CacheLoader<String, Integer>() {
					public Integer load(String key) {
						return 0;
					}
				});
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String clientIpAddress = getClientIP((HttpServletRequest) request);
		if (isMaximumRequestsPerSecondExceeded(clientIpAddress)) {
			return;
		}
		chain.doFilter(request, response);
	}

	private boolean isMaximumRequestsPerSecondExceeded(String clientIpAddress) {
		Integer requests = 0;
		requests = requestCountsPerIpAddress.get(clientIpAddress);
		log.info("Request count available -> {}",requests);
		if (requests != null) {
			if (requests > MAX_REQUESTS_PER_SECOND) {
				requestCountsPerIpAddress.asMap().remove(clientIpAddress);
				requestCountsPerIpAddress.put(clientIpAddress, requests);
				return true;
			}

		} else {
			requests = 0;
		}
		requests++;
		requestCountsPerIpAddress.put(clientIpAddress, requests);
		return false;
	}

	public String getClientIP(HttpServletRequest request) {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0]; 
	}

}
