package com.example.gestaoresiduos.cucumber.steps;

import com.example.gestaoresiduos.cucumber.CucumberSpringConfiguration;
import com.example.gestaoresiduos.cucumber.TestContext;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;

public class PontosColetaSteps extends CucumberSpringConfiguration {

    @Autowired
    private TestContext context;

    private final String BASE_PATH = "/api/pontos-coleta";

    @Dado("que a API de pontos de coleta está disponível")
    public void apiDisponivel() {
        RestAssured.port = port;
        context.reset();
    }

    @Quando("eu solicito a lista de alertas")
    public void solicitarAlertas() {
        context.setResponse(
            context.getRequest()
                .when()
                .get(BASE_PATH + "/alertas")
        );
    }

    @Quando("eu solicito para esvaziar o ponto com ID {int}")
    public void esvaziarPonto(int id) {
        context.setResponse(
            context.getRequest()
                .when()
                .put(BASE_PATH + "/" + id + "/esvaziar")
        );
    }

    @Então("a resposta deve ser uma lista")
    public void validarLista() {
        context.getResponse().then().body("$", Matchers.instanceOf(java.util.List.class));
    }

    @Então("o nivelAtualKg do ponto deve ser {string}")
    public void validarNivel(String nivel) {
        context.getResponse().then().body("nivelAtualKg", Matchers.equalTo(Float.parseFloat(nivel)));
    }
}
