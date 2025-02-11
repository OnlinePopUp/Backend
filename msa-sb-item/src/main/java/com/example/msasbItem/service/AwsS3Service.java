package com.example.msasbItem.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public class AwsS3Service {
    @Autowired
    private AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @SneakyThrows
    public String upload(MultipartFile file) {
        if(file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new Exception("파일이 누락되었거나 비어있습니다.");
        }
        return checkAndUpload(file);
    }

    private String checkAndUpload(MultipartFile file) {
        // 원본 파일명 획득
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename: " + originalFilename); // 체크용
        
        // .를 중심으로 확장자 획득
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        System.out.println("ext: " + ext); // 체크용
        
        // 이름+UUID로 구성하여 고유 값으로 파일명 구성
        String s3UploadName = UUID.randomUUID().toString().substring(0, 12) + "-" + originalFilename;
        System.out.println("s3UploadName: " + s3UploadName); // 체크용
    
        String url = "";
        try {
            // 스트림 구성
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            // 마임 타입 구성
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/" + ext);
            metadata.setContentLength(bytes.length);

            // S3에 업로드
            PutObjectRequest obj = new PutObjectRequest(
                    bucketName,             // 버킷 이름
                    originalFilename,       // 업로드된 파일명
                    byteArrayInputStream,   // 실 데이터를 전달할 스트림, 업로드된 파일과 연결
                    metadata
            );
            amazonS3.putObject(obj); // 업로드
            
            // 스트림 닫기
            byteArrayInputStream.close();
            
            // public URL 획득
            url = amazonS3.getUrl(bucketName, s3UploadName).toString();

        } catch (Exception e) {
            System.out.println("파일 업로드간 오류" + e.getMessage());
        }
        return url;
    }
}
