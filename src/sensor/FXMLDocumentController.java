/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author matheus
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private JFXTextField serverAddress;
    
    @FXML
    private JFXTextField serverPort;
    
    @FXML
    private JFXButton ok;
    
    @FXML
    private JFXRadioButton running;

    @FXML
    private JFXTextField systolic;

    @FXML
    private JFXTextField diastolic;

    @FXML
    private JFXRadioButton resting;

    @FXML
    private JFXTextField heart_rate;

    @FXML
    private Label info;
    
    @FXML
    private ToggleGroup movement;
   
    private int id;
    private boolean connected;
    private String name;
    private String address;
    private int port;
    private boolean serverConfigured;
    
    private void selectRadio(int i) {
        switch(i) {
            case 0:
                resting.setSelected(true);
                break;
            default:
                running.setSelected(true);
        }
    }
    
    private boolean getSelectedRatio() {
        if(resting.isSelected())
            return false;
        
        return true;
    }
    
    private void sendData() throws IOException, ClassNotFoundException {
        HashMap<String, Object> message = new HashMap<>();
        
        message.put("source", "sensor");
        message.put("action", "send");
        message.put("id", id);
        
        HashMap<String, Object> data = new HashMap<>();
        data.put("movement", getSelectedRatio());
        data.put("heart_rate", Double.valueOf(heart_rate.getText()));
        data.put("pressure", new double[] {Double.valueOf(systolic.getText()), 
            Double.valueOf(diastolic.getText())});
        data.put("time", LocalDateTime.now());
        
        message.put("payload", data);
        
        HashMap<String, Object> response = Client.send(message, address, port);
        System.out.println(response.get("message"));
    }
    
    private void registerToServer() throws IOException, ClassNotFoundException {
        HashMap<String, Object> message = new HashMap<>();
        message.put("source", "sensor");
        message.put("action", "register");
        message.put("name", name);
        
        HashMap<String, Object> response = Client.send(message, address, port);
        System.out.println(response.get("message"));
        this.id = (int) response.get("id");
        connected = true;
    }
    
    private void setInitialValues() {
        Random random = new Random();
        name = Utils.getName(random.nextInt(Utils.getNamesListLength()));
        
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Platform.runLater(() -> {
                    info.setText(name);
                    selectRadio(random.nextInt(3));
                    heart_rate.setText(String.valueOf(random.nextInt(130)));
                    systolic.setText(String.valueOf(random.nextInt(100) + 50));
                    diastolic.setText(String.valueOf(random.nextInt(40) + 60));
                });
                return null;
            }
        };
        new Thread(task).start();
    }
    
    @FXML
    void configureConnection(ActionEvent event) {
        this.address = this.serverAddress.getText();
        this.port = Integer.valueOf(this.serverPort.getText());
        serverConfigured = true;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            new Thread(() -> {
                try { 
                    while(serverConfigured == false) {
                        System.out.println("waiting server configuration");
                        Thread.sleep(500);
                    }
                    
                    setInitialValues();
                    registerToServer();

                    System.out.println("initialized");

                    while(connected) {
                        Thread.sleep(3000);
                        sendData();
                    }
                } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
    }    
    
}
