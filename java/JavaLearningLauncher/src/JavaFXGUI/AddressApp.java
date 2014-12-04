/*
 * The MIT License
 *
 * Copyright 2014 colbycheeze.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package JavaFXGUI;

import JavaFXGUI.model.Person;
import JavaFXGUI.view.PersonOverviewController;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author colbycheeze
 */
public class AddressApp extends Application {
    
    private Stage stage;
    private BorderPane rootLayout;
    
    /**
     * The data for an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    public AddressApp(){
        //Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        stage = primaryStage;
        stage.setTitle("Address App");
        
        initRootLayout();
        
        showPersonOverview();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Initializes the root layout
     */
    public void initRootLayout(){
        try {
            //Load the root layout from our fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AddressApp.class.getResource("view/RootLayout.fxml"));            
            rootLayout = (BorderPane) loader.load();            
            
            //Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void showPersonOverview(){
        try{
            //Load the personOverview
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AddressApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            //Set personOverview to display in center of root layout.
            rootLayout.setCenter(personOverview);
            
            //Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setAddressApp(this);
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @return Returns the data as an observable list of persons.
     */
    public ObservableList<Person> getPersonData(){
        return personData;
    }
    
    public Stage getStage(){
        return stage;
    }
}
