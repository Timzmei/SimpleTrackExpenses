package timzmei;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import timzmei.dao.TransactionDAO;
import timzmei.entity.Transact;
import timzmei.util.Comparators;

public class PrimaryController {

    private XYChart.Series<Number, Number> series;
    private ObservableList<Transact> transactList = FXCollections.observableArrayList();
    private ObservableList<Transact> listCopy;
    private Action action;


    @FXML
    private AnchorPane rootPane;

    @FXML
    private TextField amount_field;

    @FXML
    private DatePicker date_picker;

    @FXML
    private TextArea title_area;

    @FXML
    private Button add_button;

    @FXML
    private TableView<Transact> expenses_table;

    @FXML
    private TableColumn<Transact, Integer> column_id;

    @FXML
    private TableColumn<Transact, Double> amount_column;

    @FXML
    private TableColumn<Transact, LocalDate> date_column;

    @FXML
    private TableColumn<Transact, String> title_column;

    @FXML
    private Button update_button;

    @FXML
    private Button delete_button;

    @FXML
    private Button report_button;

    @FXML
    private Button png_button;

    @FXML
    private Text totalReceivedText;

    @FXML
    private Text totalSpentText;

    @FXML
    private Text totalText;

    @FXML
    private LineChart<Number, Number> chart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    @FXML
    void initialize() throws IOException {
        action = new Action(rootPane);

        listCopy = FXCollections.observableArrayList();

        series = new XYChart.Series<>();
        chart.getData().addAll(series);


        setField("0", LocalDate.now(), "");

        TransactionDAO studentDao = new TransactionDAO();
        List<Transact> transacts = studentDao.getTransacts();

        column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        amount_column.setCellValueFactory(new PropertyValueFactory<>("amount"));
        date_column.setCellValueFactory(new PropertyValueFactory<>("date"));
        title_column.setCellValueFactory(new PropertyValueFactory<>("title"));

        transactList.addAll(transacts);
        expenses_table.setItems(transactList);

    }

    private void setField(String amount, LocalDate date, String title) {
        amount_field.setText(amount);
        date_picker.setValue(date);
        title_area.setText(title);
    }

    @FXML
    public void addTransact(ActionEvent actionEvent) throws IOException {
        TransactionDAO transactionDAO = new TransactionDAO();
        Transact transact = new Transact(Double.parseDouble(amount_field.getText()), date_picker.getValue(), title_area.getText());
        transactionDAO.saveTransaction(transact);
        showTable(transactionDAO, transact);
        setField("0", LocalDate.now(), "");

        populateChart();

    }

    @FXML
    public void handleMouseClicked(MouseEvent evt){
        Transact transact = expenses_table.getSelectionModel().getSelectedItem();
        setField(String.valueOf(transact.getAmount()), transact.getDate(), transact.getTitle());
    }

    @FXML
    public void updateTransact(ActionEvent actionEvent) throws IOException {
        TransactionDAO transactionDAO = new TransactionDAO();
        Transact transact = expenses_table.getSelectionModel().getSelectedItem();
        transact.setAmount(Double.parseDouble(amount_field.getText()));
        transact.setDate(date_picker.getValue());
        transact.setTitle(title_area.getText());
        transactionDAO.updateTransacts(transact);
        showTable(transactionDAO, transact);
        setField("0", LocalDate.now(), "");


    }

    @FXML
    public void deleteTransact(ActionEvent actionEvent) throws IOException {
        TransactionDAO transactionDAO = new TransactionDAO();
        Transact transact = expenses_table.getSelectionModel().getSelectedItem();
        transactionDAO.deleteTransacts(transact);
        showTable(transactionDAO, transact);
        setField("0", LocalDate.now(), "");

    }

    @FXML
    public void exportTXT(ActionEvent event) {
        new Thread(() -> {
            Platform.runLater(() -> {
                action.exportToTXT(transactList);
            });
        }).start();
    }


    @FXML
    public void exportPNG(ActionEvent event) {
        new Thread(() -> {
            Platform.runLater(() -> {
                action.exportToPNG(chart);
            });
        }).start();
    }


    private void showTable(TransactionDAO transactionDAO, Transact transact) {
        transactList.clear();
        List<Transact> transactions = transactionDAO.getTransacts();
        transactList.addAll(transactions);
        expenses_table.setItems(transactList);
    }

    private void populateChart() {
        listCopy.clear();
        listCopy.addAll(transactList);
        listCopy.sort(new Comparators().compareByAscendingDate());
        series.getData().clear();

        double spent = 0, gained = 0, total = 0;
        for (Transact t : listCopy) {
            if (t.getAmount() > 0)
                gained += t.getAmount();
            else
                spent -= t.getAmount();
            total += t.getAmount();
            int timeDiff = dateDifference(t.getDate(), LocalDate.now());

            series.getData().add(new XYChart.Data<Number, Number>(timeDiff, total));
        }

        totalReceivedText.setText("Руб. " + String.format("%.2f", gained));
        totalSpentText.setText("Руб. " + String.format("%.2f", spent));
        totalText.setText("Руб. " + String.format("%.2f", total));
    }

    private int dateDifference(LocalDate d1, LocalDate d2) {
        int dayDiff = d1.getDayOfYear() - d2.getDayOfYear();
        int yearDiff = d1.getYear() - d2.getYear();
        return (dayDiff + 365 * yearDiff);
    }


}
