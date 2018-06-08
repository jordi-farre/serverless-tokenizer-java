package com.serverless.tokenizer.domain;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Pan {

    private String value;

    private Pan(String value) {
        this.value = value;
    }

    public static Pan from(String value) {
        return new Pan(value);
    }

    public String getValue() {
        return value;
    }

    public Token encrypt(AWSKMS awskms, AmazonS3 amazonS3) {
        String token = createTokenFrom();
        ByteBuffer encryptedPan = encrypt(awskms);
        save(amazonS3, token, encryptedPan);
        return Token.from(token);
    }

    private String createTokenFrom() {
        return UUID.randomUUID().toString();
    }

    private ByteBuffer encrypt(AWSKMS awskms) {
        EncryptRequest encryptRequest = new EncryptRequest();
        encryptRequest.setKeyId(System.getenv("KMS_ALIAS_ARN"));
        encryptRequest.setPlaintext(ByteBuffer.wrap(this.value.getBytes(UTF_8)));
        Map<String, String> encryptionContext = new HashMap<>();
        encryptionContext.put("UserName", System.getenv("ENCRYPTION_CONTEXT_USER_NAME"));
        encryptRequest.setEncryptionContext(encryptionContext);
        EncryptResult encryptResult = awskms.encrypt(encryptRequest);
        return encryptResult.getCiphertextBlob();
    }

    private void save(AmazonS3 amazonS3, String token, ByteBuffer encryptedPan) {
        InputStream inputStream = new ByteArrayInputStream(encryptedPan.array());
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setSSEAlgorithm("AES256");
        PutObjectRequest putObjectRequest = new PutObjectRequest(System.getenv("PCI_BUCKET"), token, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);
    }
}
