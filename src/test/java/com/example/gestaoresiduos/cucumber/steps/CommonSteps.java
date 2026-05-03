package com.example.gestaoresiduos.cucumber.steps;

import com.example.gestaoresiduos.cucumber.CucumberSpringConfiguration;
import com.example.gestaoresiduos.cucumber.TestContext;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
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

    @Quando("eu estou autenticado com o usuário {string} e senha {string}")
    public void autenticar(String user, String password) {
        // Preserva o port e configurações base, apenas altera o objeto de request com auth
        context.setRequest(io.restassured.RestAssured.given()
                .port(context.port)
                .auth().basic(user, password));
    }

    @Quando("eu envio uma requisição POST para {string} com:")
    public void enviarPost(String path, io.cucumber.datatable.DataTable table) {
        java.util.Map<String, String> data = table.asMap(String.class, String.class);
        context.setResponse(
            context.getRequest()
                .contentType(io.restassured.http.ContentType.JSON)
                .body(data)
                .when()
                .post(path)
        );
    }
}
