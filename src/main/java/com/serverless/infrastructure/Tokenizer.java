package com.serverless.infrastructure;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.log4j.Logger;

import java.util.Collections;

public class Tokenizer implements RequestHandler<ApiGatewayRequestDTO, ApiGatewayResponseDTO> {

	private static final Logger LOG = Logger.getLogger(Tokenizer.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ApiGatewayResponseDTO handleRequest(ApiGatewayRequestDTO input, Context context) {
		LOG.info("received: " + input.getBody());
		TokenizerRequestDTO tokenizerRequestDTO = objectMapper.readValue(input.getBody(), TokenizerRequestDTO.class);
		LOG.info("received: " + tokenizerRequestDTO.getPan());
		return ApiGatewayResponseDTO.from(200, objectMapper.writeValueAsString(TokenizerResponseDTO.from("to implement")));
	}
}
