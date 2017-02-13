package com.surperfluousfew.auktionsystem.controllers;

import com.surperfluousfew.auktionsystem.StageHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddLeverantorController {
    Stage stage;
    StageHandler stageHandler = new StageHandler();

    @FXML
    Parent root;

    private void addLeverantor(){

    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent homeScreen = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        stage = stageHandler.getParentStage(root);
        stage.setScene(new Scene(homeScreen));
    }

}
