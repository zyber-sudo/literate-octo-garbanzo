package model;


import javafx.scene.control.Alert;

@FunctionalInterface
public interface AlertInterface {

    void AlertPopup(Alert alert, String title, String header, String content, boolean wait);

}
