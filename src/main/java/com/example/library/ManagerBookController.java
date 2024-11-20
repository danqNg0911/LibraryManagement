package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ManagerBookController extends ManagerController {

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, String> usernameColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> dateColumn;

    @FXML
    private TableColumn<Book, String> statusColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField categoryField;

    @FXML
    private RadioButton overdueButton;

    @FXML
    private RadioButton availableButton;

    @FXML
    private RadioButton addedButton;

    private ToggleGroup radioGroup;

    private List<Book> listOfBooks = new ArrayList<>();
    private List<Book> listBooksWithFilters = new ArrayList<>();
    private List<Book> listofOverdueBooks = new ArrayList<>();
    private List<Book> listofAvailableBooks = new ArrayList<>();
    private List<Book> listOfAddedBooks = new ArrayList<>();

    @FXML
    public void initialize() {
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        bookTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    showBookDetails(selectedBook, event);
                }
            }
        });
    }

    public void loadBooksAccounts() throws SQLException {
        listOfBooks.clear();
        listBooksWithFilters.clear();

        String username = usernameField.getText().trim();
        Date date = dateField.getValue() != null ? Date.valueOf(dateField.getValue()) : null;
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();

        if (username.isEmpty() && date == null && title.isEmpty() && author.isEmpty() && category.isEmpty()) {
            listOfBooks = BookJDBC.getAllBooksFromAllUser();
        } else {
            listOfBooks = BookJDBC.searchBooksFromDatabase(username, title, author, category, date);
        }

        // Phân loại sách theo trạng thái
        for (Book book : listOfBooks) {
            Date borrowedDate = book.getDate();
            if (borrowedDate != null) {
                if ("borrowed".equals(book.getSource())) {
                    LocalDate localDate = borrowedDate.toLocalDate();
                    if (localDate.plusDays(7).isBefore(LocalDate.now())) {
                        book.setStatus("Overdue");
                    } else {
                        book.setStatus("Available");
                    }
                } else {
                    book.setStatus("Owner");
                }
            } else {
                book.setStatus("Unknown");
            }
        }
    }

    public void bookFilters() {
        ObservableList<Book> observableBookList;
        if (!overdueButton.isSelected() && !availableButton.isSelected() && !addedButton.isSelected()) {
            observableBookList = FXCollections.observableArrayList(listOfBooks);
        } else {
            for (Book book : listOfBooks) {
                if (overdueButton.isSelected() && book.getStatus().equals("Overdue")) {
                    listBooksWithFilters.add(book);
                }
                else if (availableButton.isSelected() && book.getStatus().equals("Available")) {
                    listBooksWithFilters.add(book);
                }
                else if (addedButton.isSelected() && book.getStatus().equals("Owner")) {
                    listBooksWithFilters.add(book);
                }
            }
            observableBookList = FXCollections.observableArrayList(listBooksWithFilters);
        }
        bookTable.setItems(observableBookList);
    }

    private void showBookDetails(Book selectedBook, javafx.scene.input.MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/library/fxml/ViewBookManager.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        ViewBookManagerController controller = loader.getController();
        try {
            controller.setBookDetails(selectedBook);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 1200, 800);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.navigateTo(scene);
        stage.setScene(scene);
        stage.show();
    }

    public void showBooksData(ActionEvent actionEvent) throws SQLException {
        loadBooksAccounts(); // Tải dữ liệu sách
        bookFilters(); // Lọc dữ liệu hiển thị
    }
}
