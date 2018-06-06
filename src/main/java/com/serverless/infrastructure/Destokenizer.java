package com.serverless.infrastructure;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.log4j.Logger;

public class Destokenizer implements RequestHandler<ApiGatewayRequestDTO, ApiGatewayResponseDTO> {

	private static final Logger LOG = Logger.getLogger(Destokenizer.class);
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ApiGatewayResponseDTO handleRequest(ApiGatewayRequestDTO input, Context context) {
		LOG.info("received: " + input.getBody());
		DestokenizerRequestDTO destokenizerRequestDTO = objectMapper.readValue(input.getBody(), DestokenizerRequestDTO.class);
		LOG.info("received: " + destokenizerRequestDTO.getToken());
		return ApiGatewayResponseDTO.from(200, objectMapper.writeValueAsString(DestokenizerResponseDTO.from("to implement")));
	}
}
