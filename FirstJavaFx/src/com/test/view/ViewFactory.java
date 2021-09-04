package com.test.view;

import com.test.EmailManager;
import com.test.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private EmailManager emailManager;

    private ArrayList<Stage> activeStages;
    private boolean mainViewInitialized = false;

    public ViewFactory(EmailManager emailManager) {

        this.emailManager = emailManager;
        this.activeStages = new ArrayList<Stage>();
    }

    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public ColorTheme getColorTheme() {
        return this.colorTheme;
    }

    public FontSize getFontSize() {
        return this.fontSize;
    }


    public boolean isMainViewInitialized() {
        return mainViewInitialized;
    }

    public void showLoginWindow() {
        System.out.println(" Login page is being shown");

        BaseController controller = new LoginWindowController(emailManager, this, "loginWindow.fxml");
        initializeStage(controller);

    }

    public void showOptionsWindow() {
        System.out.println("options window called");
        BaseController controller = new OptionsWindowController(emailManager, this, "optionsWindow.fxml");
        initializeStage(controller);

    }

    public void showEmailDetailsWindow() {
        System.out.println("email details window called");
        BaseController controller = new EmailDetailsController(emailManager, this, "emailDetailWindow.fxml");
        initializeStage(controller);

    }

    public void showMessageCompose() {
        System.out.println("Message composer window called");
        BaseController controller = new MessageComposeController(emailManager, this, "MessageComposeWindow.fxml");
        initializeStage(controller);

    }

    public void showMainWindow() {
        System.out.println(" Main page is being shown");

        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
        initializeStage(controller);
        mainViewInitialized = true;
    }

    private void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(setStageTitle(controller.getFxmlName()));
        stage.show();
        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }


    public void updateStyles() {

        for (Stage stage : activeStages) {
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();

            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());

        }
    }

    public String setStageTitle(String fxlnm) {
        switch (fxlnm) {
            case "loginWindow.fxml":
                return "Login ";
            case "MainWindow.fxml":
                return "Main Page";
            case "optionsWindow.fxml":
                return "Options";
            default:
                return null;
        }
    }
}

