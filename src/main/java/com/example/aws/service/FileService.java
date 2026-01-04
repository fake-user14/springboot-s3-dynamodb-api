package com.example.aws.service;

import com.example.aws.model.FileMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Client s3Client;
    private final DynamoDbClient dynamoDbClient;

    private final String bucket = "your-bucket-name";
    private final String tableName = "FileMetadata";

    public FileMetadata uploadFile(MultipartFile file) throws Exception {

        String id = UUID.randomUUID().toString();

        // Upload to S3
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(id + "_" + file.getOriginalFilename())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        String fileUrl = "https://" + bucket + ".s3.amazonaws.com/" + id + "_" + file.getOriginalFilename();

        FileMetadata metadata = new FileMetadata(
                id,
                file.getOriginalFilename(),
                fileUrl,
                file.getSize()
        );

        // Save metadata to DynamoDB
        Map<String, Object> item = new HashMap<>();
        item.put("id", metadata.getId());
        item.put("fileName", metadata.getFileName());
        item.put("url", metadata.getUrl());
        item.put("size", metadata.getSize());

        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName(tableName)
                .item(Map.of(
                        "id", software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS(metadata.getId()),
                        "fileName", software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS(metadata.getFileName()),
                        "url", software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromS(metadata.getUrl()),
                        "size", software.amazon.awssdk.services.dynamodb.model.AttributeValue.fromN(String.valueOf(metadata.getSize()))
                ))
                .build());

        return metadata;
    }
}
