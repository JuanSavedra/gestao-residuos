package com.example.gestaoresiduos.cucumber.steps;

import com.example.gestaoresiduos.cucumber.CucumberSpringConfiguration;
import com.example.gestaoresiduos.cucumber.TestContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;

public class ResiduosSteps extends CucumberSpringConfiguration {

    @Autowired
    private TestContext context;

    private final String BASE_PATH = "/api/residuos";

    @Dado("que a API de resíduos está disponível")
    public void apiDisponivel() {
        RestAssured.port = port;
        context.reset();
    }

    @Quando("eu solicito as instruções para o resíduo com ID {int}")
    public void solicitarInstrucoes(int id) {
        context.setResponse(
            given()
                .when()
                .get(BASE_PATH + "/" + id + "/instrucoes")
        );
    }
}
