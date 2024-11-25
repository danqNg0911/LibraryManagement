package com.example.library;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerLoanBooksController extends ManagerController {
    @FXML
    private TableView<Book> loanBooksTable;

    @FXML
    private TableColumn<Book, String> usernameColumn;

    @FXML
    private TableColumn<Book, String> titleColumn;

    @FXML
    private TableColumn<Book, Date> borrowedDateColumn;

    @FXML
    private TableColumn<Book, Date> returnedDateColumn;

    @FXML
    private TableColumn<Book, String> statusColumn;

    @FXML
    private TableColumn<Book, Double> fineAmountColumn;

    @FXML
    private Button searchButton;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField titleField;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label fineLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private Button finedButton;

    @FXML
    private ImageView coverImage;

    private List<Book> listOfBooks = new ArrayList<>();
    private List<Book> listBooksWithFilters = new ArrayList<>();

    public void showAnimationLoan(MouseEvent event) {
        return;
    }

    public void unshowAnimationLoan(MouseEvent event) {
        return;
    }

    @FXML
    public void initialize() {
        accountName.setText(manager.getName(manager.getUsername()));
        accountName.setPrefWidth(Region.USE_COMPUTED_SIZE);

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        returnedDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        fineAmountColumn.setCellValueFactory(new PropertyValueFactory<>("fineAmount"));

        loanBooksTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Book selectedBook = loanBooksTable.getSelectionModel().getSelectedItem();
                if (selectedBook != null) {
                    showBookDetails(selectedBook, event);
                }
            }
        });
    }

    public int dateDiff(Date borrowedDate, Date returnedDate) {
        long borrowedMillis = borrowedDate.getTime();
        long returnedMillis = returnedDate.getTime();

        long differenceMillis = returnedMillis - borrowedMillis;

        return (int) (differenceMillis / (24 * 60 * 60 * 1000));
    }

    public void bookFilters() throws SQLException {
        listBooksWithFilters.clear();
        listOfBooks.clear();

        listOfBooks = BookJDBC.getReturnedBooksFromUsers();
        for (Book book : listOfBooks) {
            int borrowedDays = dateDiff(book.getDate(), book.getReturnedDate());
            if (borrowedDays <= allowedBorrowTime) {
                book.setStatus("on time");
            } else {
                book.setStatus("late");
            }
        }

        ObservableList<Book> observableBookList;
        String username = usernameField.getText().trim();
        String title = titleField.getText().trim();

        if (username.isEmpty() && title.isEmpty()) {
            observableBookList = FXCollections.observableList(listOfBooks);
        } else {
            if (title.isEmpty()) {
                for (Book book : listOfBooks) {
                    if (book.getUsername().toLowerCase().contains(username.toLowerCase())) {
                        listBooksWithFilters.add(book);
                    }
                }
            } else if (username.isEmpty()) {
                for (Book book : listOfBooks) {
                    if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                        listBooksWithFilters.add(book);
                    }
                }
            } else {
                for (Book book : listOfBooks) {
                    if (book.getUsername().toLowerCase().contains(username.toLowerCase())
                            && book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                        listBooksWithFilters.add(book);
                    }
                }
            }
            observableBookList = FXCollections.observableList(listBooksWithFilters);
        }
        loanBooksTable.setItems(observableBookList);
    }

    private void showBookDetails(Book selectedBook, MouseEvent event) {
        usernameLabel.setText("Username: " + selectedBook.getUsername());
        titleLabel.setText("Title: " + selectedBook.getTitle());
        authorLabel.setText("Author: " + selectedBook.getAuthor());
        categoryLabel.setText("Category: " + selectedBook.getCategory());

        if (selectedBook.getImageUrl() != null && !selectedBook.getImageUrl().isEmpty()) {
            Image image = new Image(selectedBook.getImageUrl());
            coverImage.setImage(image);
        } else {
            if (selectedBook.getSource() != null && selectedBook.getSource().equals("create")) {
                Image defaultImage = new Image(getClass().getResource(LinkSetting.DEFAULT_COVER_IMAGE.getLink()).toExternalForm());
                coverImage.setImage(defaultImage);
            } else {
                Image nullImage = new Image(getClass().getResource(LinkSetting.IMAGE_NULL.getLink()).toExternalForm());
                coverImage.setImage(nullImage);
            }
        }

        if (selectedBook.getStatus().equals("on time")) {
            finedButton.setVisible(false);
            fineLabel.setText("Fine Amount: 0" );
            noteLabel.setText("Note: This book was returned on time");
        } else {
            finedButton.setVisible(selectedBook.getFineAmount() == 0);

            fineLabel.setText("Fine Amount: " + (int) fineAmountCalculate(selectedBook.getDate(), selectedBook.getReturnedDate()));
            noteLabel.setText("Note: This book was returned "
                    + (dateDiff(selectedBook.getDate(), selectedBook.getReturnedDate()) - allowedBorrowTime + 1) + " day(s) late");
        }
    }

    public void fineUser(ActionEvent actionEvent) throws SQLException {
        Book selectedBook = loanBooksTable.getSelectionModel().getSelectedItem();
        if (selectedBook != null) {
            double fineAmount = fineAmountCalculate(selectedBook.getDate(), selectedBook.getReturnedDate());
            if (selectedBook.getStatus().equals("late") && selectedBook.getFineAmount() == 0) {
                BookJDBC.updateFineAmount(selectedBook.getId(), fineAmount);

                listOfBooks = BookJDBC.getReturnedBooksFromUsers();
                bookFilters();

                System.out.println("Fine applied to  selected book");
            } else {
                System.out.println("Book has already been fined.");
            }
        } else {
            System.out.println("No book selected.");
        }
    }

    public void showBooksData(ActionEvent actionEvent) throws SQLException {
        bookFilters();
    }

    public double fineAmountCalculate(Date borrowedDate, Date returnedDate) {
        int overdueDays = dateDiff(borrowedDate, returnedDate) - allowedBorrowTime + 1;
        if (overdueDays > 0) {
            if (overdueDays <= 2 ) {
                return 10000;
            } else if (overdueDays <= 5 ) {
                return 20000;
            } else {
                int fine = 20000;
                for (int i = 5; i<overdueDays; i++) {
                    fine += 5000;
                }
                return fine;
            }
        }
        return 0;
    }
}
