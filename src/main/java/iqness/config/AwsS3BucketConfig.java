package iqness.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class AwsS3BucketConfig {

    @Value("${cloud.aws.bucket.name}")
    private String bucketName;

}
