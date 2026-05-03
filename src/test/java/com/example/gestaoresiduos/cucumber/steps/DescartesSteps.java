package com.example.gestaoresiduos.cucumber.steps;

import com.example.gestaoresiduos.cucumber.CucumberSpringConfiguration;
import com.example.gestaoresiduos.cucumber.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class DescartesSteps extends CucumberSpringConfiguration {

    @Autowired
    private TestContext context;

    @Dado("que a API de descartes está disponível")
    public void apiDisponivel() {
        RestAssured.port = port;
        context.reset();
    }

    @Dado("que estou autenticado com o usuário {string} e senha {string}")
    public void autenticar(String user, String password) {
        context.setRequest(context.getRequest().auth().basic(user, password));
    }

    @Quando("eu envio uma requisição POST para {string} com:")
    public void enviarPost(String path, DataTable table) {
        Map<String, String> data = table.asMap(String.class, String.class);
        context.setResponse(
            context.getRequest()
                .contentType(ContentType.JSON)
                .body(data)
                .when()
                .post(path)
        );
    }

    @Quando("eu envio uma requisição POST para {string} sem autenticação")
    public void enviarPostSemAuth(String path) {
        context.setResponse(
            given()
                .contentType(ContentType.JSON)
                .when()
                .post(path)
        );
    }

    @Então("a quantidadeKg no corpo da resposta deve ser {string}")
    public void validarQuantidade(String qtd) {
        context.getResponse().then().body("quantidadeKg", Matchers.equalTo(Float.parseFloat(qtd)));
    }
}
