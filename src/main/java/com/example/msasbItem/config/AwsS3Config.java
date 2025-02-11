package com.example.msasbItem.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AwsS3Config {
    @Value("${cloud.aws.credentials.accesskey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretkey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        log.info("Creating Amazon S3");
        // 차후 제거
        log.info("access-key: " + accessKey);
        log.info("secret-key: " + secretKey);
        log.info("region: " + region);
        // 인증 정보 구성
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }
}
