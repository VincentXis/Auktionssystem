package com.surperfluousfew.auktionsystem.controllers;

import com.surperfluousfew.auktionsystem.DatabaseLoader;
import com.surperfluousfew.auktionsystem.StageHandler;
import com.surperfluousfew.auktionsystem.models.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController extends VBox {

    @FXML
    private Parent root;
    @FXML
    private TextField txfAnstallninsnummer, txfPassword;
    @FXML
    private Text tInfo;

    private DatabaseLoader dbLoader;
    private StageHandler stageHandler = new StageHandler();

    public LogInController(DatabaseLoader dbLoader) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/logIn.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.dbLoader = dbLoader;
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        try {
            logIn(ae);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logIn(ActionEvent actionEvent) throws Exception {
        String loginAnstallningsnummer = txfAnstallninsnummer.getText();
        String loginLosenord = txfPassword.getText();
        dbLoader.loadAdmins();
        for (Admin admin : dbLoader.getAdmins()) {
            String adminAnstallningsnummer = String.valueOf(admin.getAnstallningsnummer());
            String inloggMisslyckadTomtFalt = "Inloggning misslyckades ange: ";
            if (adminAnstallningsnummer.equals(loginAnstallningsnummer) && admin.getLosenord().equals(loginLosenord)) {
                HomeController homeController = new HomeController(dbLoader);
                Stage oldStage = stageHandler.getParentStage(root);
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Auktionssystem");
                primaryStage.setHeight(600);
                primaryStage.setWidth(800);
                primaryStage.setScene(new Scene(homeController));
                primaryStage.setResizable(false);
                primaryStage.show();
                oldStage.close();
            } else if (adminAnstallningsnummer.equals(loginAnstallningsnummer) && (!loginLosenord.isEmpty() && !admin.getLosenord().equals(loginLosenord))) {
                tInfo.setText(inloggMisslyckadTomtFalt + "Felaktigt lösenord");
            } else if (loginAnstallningsnummer.isEmpty() || loginLosenord.isEmpty()) {

                if (loginAnstallningsnummer.isEmpty() && !loginLosenord.isEmpty()) {
                    tInfo.setText(inloggMisslyckadTomtFalt + "Anställningsnummer");
                } else if (!loginAnstallningsnummer.isEmpty() && loginLosenord.isEmpty()) {
                    tInfo.setText(inloggMisslyckadTomtFalt + "Lösenord");
                } else {
                    tInfo.setText("Inloggning misslyckades: Inloggningsuppgifter saknas");
                }
            } else {
                tInfo.setText("Felaktiga Inloggningsuppgifter");
            }
        }
    }
}
