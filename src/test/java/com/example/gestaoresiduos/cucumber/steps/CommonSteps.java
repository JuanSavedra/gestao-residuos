package com.example.gestaoresiduos.cucumber.steps;

import com.example.gestaoresiduos.cucumber.CucumberSpringConfiguration;
import com.example.gestaoresiduos.cucumber.TestContext;
import io.cucumber.java.pt.Então;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonSteps extends CucumberSpringConfiguration {

    @Autowired
    protected TestContext context;

    @Então("o status code deve ser {int}")
    public void validarStatusCode(int statusCode) {
        context.getResponse().then().statusCode(statusCode);
    }

    @Então("o corpo da resposta deve conter {string}")
    public void validarCorpoResposta(String conteudo) {
        context.getResponse().then().body(Matchers.containsString(conteudo));
    }

    @Então("o corpo da resposta deve validar o contrato {string}")
    public void validarContrato(String schemaPath) {
        context.getResponse().then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }
}
