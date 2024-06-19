package org.example;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Order {

    private List<String> ingredients;
    public Order(List<String> ingredients){
        this.ingredients = ingredients;
    }
    public Order(){}
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
