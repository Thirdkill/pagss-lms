package com.pagss.lms.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "amazon-s3")
public class PropertiesConfig {

	@Getter @Setter private String accesskey;
	@Getter @Setter private String secretkey;
	@Getter @Setter private String bucketName;
	@Getter @Setter private String region;
	
}
