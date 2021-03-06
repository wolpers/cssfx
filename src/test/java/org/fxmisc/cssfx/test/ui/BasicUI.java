package org.fxmisc.cssfx.test.ui;

/*
 * #%L
 * CSSFX
 * %%
 * Copyright (C) 2014 CSSFX by Matthieu Brouillard
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */



import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.fxmisc.cssfx.CSSFX;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BasicUI extends Application implements TestableUI {
    @Override
    public Parent getRootNode() {
        VBox container = new VBox();
        container.getStyleClass().add("container");
        
        Label lblWelcome = new Label("Welcome");
        Label lblCSSFX = new Label("CSSFX");
        lblCSSFX.setId("cssfx");
    
        container.getChildren().addAll(lblWelcome, lblCSSFX);
        
        String basicURI = BasicUI.class.getResource("basic.css").toExternalForm();
        container.getStylesheets().add(basicURI);
        
        return container;
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent p = getRootNode();
        Scene s = new Scene(p, 800, 600);
        stage.setScene(s);
        stage.show();
        
        // The CSS used by the UI
        URI basicCSS = BasicUI.class.getResource("basic.css").toURI();
        // a resource containing the required changes we want to apply to the CSS 
        URI changedBasicCSS = BasicUI.class.getResource("basic-cssfx.css").toURI();
        
        // start CSSFX
        Runnable stopper = CSSFX.onlyFor(p)
                .noDefaultConverters()
                .addConverter((uri) -> {
                    try {
                        if (basicCSS.toURL().toExternalForm().equals(uri)) {
                            return Paths.get(changedBasicCSS);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }).start();

    }
}
