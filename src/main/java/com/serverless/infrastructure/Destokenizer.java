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

public class Destokenizer implements RequestHandler<ApiGatewayRequestDTO, ApiGatewayResponseDTO> {

	private static final Logger LOG = Logger.getLogger(Destokenizer.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	private AWSKMS awskms = AWSKMSClientBuilder.defaultClient();
	private AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();

	@Override
	public ApiGatewayResponseDTO handleRequest(ApiGatewayRequestDTO input, Context context) {
		DestokenizerRequestDTO destokenizerRequestDTO = objectMapper.readValue(input.getBody(), DestokenizerRequestDTO.class);
		Token token = Token.from(destokenizerRequestDTO.getToken());
		Pan pan = token.decrypt(awskms, amazonS3);
		return ApiGatewayResponseDTO.from(200, objectMapper.writeValueAsString(DestokenizerResponseDTO.from(pan.getValue())));
	}
}
