package timzmei;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class AlertUser {
    public static void show(String title, String headerText, String contentText) {
        Alert alert = new Alert(AlertType.ERROR, title, ButtonType.OK);
        if (headerText != null)
            alert.setHeaderText(headerText);
        if (contentText != null)
            alert.setContentText(contentText);
        alert.showAndWait();
    }
}
