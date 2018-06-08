package com.serverless.tokenizer.infrastructure;

public class ApiGatewayResponseDTO {

	private final int statusCode;
	private final String body;

	private ApiGatewayResponseDTO(int statusCode, String body) {
		this.statusCode = statusCode;
		this.body = body;
	}

	private ApiGatewayResponseDTO(int statusCode) {
		this.statusCode = statusCode;
		this.body = null;
	}

	public static ApiGatewayResponseDTO from(int statusCode, String body) {
		return new ApiGatewayResponseDTO(statusCode, body);
	}

	public static ApiGatewayResponseDTO from(int statusCode) {
		return new ApiGatewayResponseDTO(statusCode);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getBody() {
		return body;
	}
}
