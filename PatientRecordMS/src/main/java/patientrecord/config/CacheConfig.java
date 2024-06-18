package patientrecord.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;

@Configuration
@EnableCaching
public class CacheConfig {
	
	 @Bean
	  public net.sf.ehcache.CacheManager ehCacheManager() {
	    CacheConfiguration cacheConfiguration = new CacheConfiguration();
	    cacheConfiguration.setName("patientRecord");
	    cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
	    cacheConfiguration.setMaxEntriesLocalHeap(1000);

	    net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
	    config.addCache(cacheConfiguration);

	    return net.sf.ehcache.CacheManager.newInstance(config);
	  }

	 @Bean
	 public CacheManager cacheManager() {
	   return new EhCacheCacheManager(ehCacheManager());
	 }

}
