package com.serverless.infrastructure;

public class ApiGatewayResponseDTO {

	private final int statusCode;
	private final String body;

	private ApiGatewayResponseDTO(int statusCode, String body) {
		this.statusCode = statusCode;
		this.body = body;
	}

	public static ApiGatewayResponseDTO from(int statusCode, String body) {
		return new ApiGatewayResponseDTO(statusCode, body);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getBody() {
		return body;
	}
}
