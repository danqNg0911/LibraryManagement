package com.example.library;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManagerBookController extends ManagerController{
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

    public void showAnimationMB(MouseEvent event) {
        return;
    }

    public void unshowAnimationMB(MouseEvent event) {
        return;
    }

    public void moveToManageBooks(ActionEvent actionEvent) throws IOException {
        return;
    }

    public void loadBooksAccounts() throws SQLException {
        List<Book> listOfBooks = BookJDBC.getAllBooksFromAllUser();

        for (Book book : listOfBooks) {
            Date borrowedDate = book.getDate();
            if (borrowedDate != null) {
                if (book.getSource().equals("borrowed")) {
                    LocalDate localDate = borrowedDate.toLocalDate();
                    if (localDate.minusDays(7).isAfter(LocalDate.now())) {
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

        ObservableList<Book> observableUserList = FXCollections.observableArrayList(listOfBooks);
        bookTable.setItems(observableUserList);
    }


    @FXML
    public void initialize() throws SQLException {
        // Hiển thị username
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Load dữ liệu
        loadBooksAccounts();
    }
}
