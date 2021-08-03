package timzmei;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.chart.LineChart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import timzmei.AlertUser;
import timzmei.entity.Transact;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Action {
    private AnchorPane rootPane;

    public Action(AnchorPane rootPane) {
        this.rootPane = rootPane;
        try {
            Files.createDirectories(Paths.get("data/"));
        } catch (IOException e) {
            AlertUser.show("Error", null, "Could not create folder to store user's data.");
        }
    }


    public void exportToTXT(ObservableList<Transact> list) {


        File file = chooseFile("Export to...", true, "myFinances.txt", new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        if (!validFile(file, "txt"))
            return;
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            for (Transact t : list) {
                pw.print(t + "\n");
            }
            AlertUser.show("Error", null, "Exception while exporting to TXT file");
        } catch (IOException e) {
            AlertUser.show("Error", null, "Exception while exporting to TXT file");
        }

    }

    public void exportToPNG(LineChart<Number, Number> chart) {

        WritableImage image = chart.snapshot(null, null);
        File file = chooseFile("Save image to...", true, "myFinances.png", new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"));
        if (!validFile(file, "png"))
            return;
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            AlertUser.show("Error", null, "Exception while saving PNG file");
        }

    }

    private File chooseFile(String chooserTitle, boolean toSave, String initialFileName, FileChooser.ExtensionFilter extensionFilter) {
        File file;
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(chooserTitle);
        fileChooser.setInitialFileName(initialFileName);
        fileChooser.getExtensionFilters().add(extensionFilter);
        if (toSave)
            file = fileChooser.showSaveDialog(rootPane.getScene().getWindow());
        else
            file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        return file;
    }

    private boolean validFile(File file, String extension) {
        if (file == null)
            return false;
        if (file.getName().endsWith("." + extension))
            return true;
        AlertUser.show("Error in choosing file", null, "Can only operate with " + extension.toUpperCase() + " files.");
        return false;
    }
}
