package com.serverless.tokenizer.domain;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Token {

    private String value;

    private Token(String value) {
        this.value = value;
    }

    public static Token from(String value) {
        return new Token(value);
    }

    public String getValue() {
        return value;
    }

    public Optional<Pan> decrypt(AWSKMS awskms, AmazonS3 amazonS3) {
        return get(amazonS3).map(byteBuffer -> {
            String pan = decrypt(awskms, byteBuffer);
            return Pan.from(pan);
        });
    }

    private Optional<ByteBuffer> get(AmazonS3 amazonS3) {
        S3Object s3Object = amazonS3.getObject(System.getenv("PCI_BUCKET"), value);
        S3ObjectInputStream s3ObjectInputStream = s3Object.getObjectContent();
        try {
            return Optional.of(ByteBuffer.wrap(IOUtils.toByteArray(s3ObjectInputStream)));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    private String decrypt(AWSKMS awskms, ByteBuffer byteBuffer) {
        DecryptRequest decryptRequest = new DecryptRequest();
        decryptRequest.setCiphertextBlob(byteBuffer);
        Map<String, String> encryptionContext = new HashMap<>();
        encryptionContext.put("UserName", System.getenv("ENCRYPTION_CONTEXT_USER_NAME"));
        decryptRequest.setEncryptionContext(encryptionContext);
        DecryptResult decryptResult = awskms.decrypt(decryptRequest);
        return new String (decryptResult.getPlaintext().array(), UTF_8);
    }


}
