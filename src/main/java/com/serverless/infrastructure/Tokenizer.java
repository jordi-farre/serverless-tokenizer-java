package com.serverless.infrastructure;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.serverless.domain.Pan;
import com.serverless.domain.Token;
import org.apache.log4j.Logger;

public class Tokenizer implements RequestHandler<ApiGatewayRequestDTO, ApiGatewayResponseDTO> {

	private static final Logger LOG = Logger.getLogger(Tokenizer.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	private AWSKMS awskms = AWSKMSClientBuilder.defaultClient();
	private AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();

	@Override
	public ApiGatewayResponseDTO handleRequest(ApiGatewayRequestDTO input, Context context) {
		TokenizerRequestDTO tokenizerRequestDTO = objectMapper.readValue(input.getBody(), TokenizerRequestDTO.class);
		Token token = Pan.from(tokenizerRequestDTO.getPan())
				.encrypt(awskms, amazonS3);
		return ApiGatewayResponseDTO.from(200, objectMapper.writeValueAsString(TokenizerResponseDTO.from(token.getValue())));
	}
}
