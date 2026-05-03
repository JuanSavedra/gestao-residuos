package com.example.gestaoresiduos.cucumber;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.stereotype.Component;

import static io.restassured.RestAssured.given;

@Component
public class TestContext {
    public int port;
    private Response response;
    private RequestSpecification request = given();

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public RequestSpecification getRequest() {
        return request;
    }

    public void setRequest(RequestSpecification request) {
        this.request = request;
    }

    public void reset() {
        response = null;
        request = given().port(port);
    }
}
