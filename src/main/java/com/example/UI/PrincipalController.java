package com.example.UI;

import com.example.router.Router;

import javafx.fxml.FXML;

public class PrincipalController {

  @FXML
  public void initialize() {
    System.out.println("Pantalla principal cargada correctamente");
  }

  @FXML
  private void goToCesar() {
    //Router.goToCesar();
  }

  @FXML
  private void goToPigPen() {
    Router.goToPigPen();
  }

  @FXML
  private void goToRailFence() {
    Router.goToRailFence();
  }

  @FXML
  private void goToVigenere() {
    Router.goToVigenere();
  }

  @FXML
  private void goToAES() {
    //Router.goToAES();
  }
}
