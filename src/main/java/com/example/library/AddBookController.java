package com.example.library;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

import java.io.IOException;

public class AddBookController extends UserCollectionCltController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField categoryField;

    @FXML
    private TextArea descriptionField;

    public void initialize() {
        baseInitialize();
    }

    public void addYourBook(ActionEvent event) {
        // Lấy dữ liệu từ các ô nhập liệu và loại bỏ khoảng trắng dư thừa
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();
        String description = descriptionField.getText().trim();

        // Kiểm tra nếu bất kỳ trường nào bị để trống
        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || description.isEmpty()) {
            // Hiển thị cảnh báo lỗi yêu cầu nhập đầy đủ thông tin
            WindowManager.alertWindow(
                    Alert.AlertType.ERROR,
                    "Alert",
                    "Please complete all information to create your book",
                    "stylesheet (css)/login_alert.css"
            );
        } else {
            // Thêm sách vào cơ sở dữ liệu thông qua lớp BookJDBC
            BookJDBC.addBookToDatabase(
                    user.getUsername(), // Tên người dùng
                    "",                 // Giá trị isbn rỗng
                    title,              // Tên sách
                    author,             // Tên tác giả
                    category,           // Thể loại
                    null,               // Không có hình ảnh
                    description,        // Mô tả sách
                    "create"            // Loại hành động (ở đây là "create")
            );

            // Hiển thị thông báo thành công
            WindowManager.alertWindow(
                    Alert.AlertType.INFORMATION,
                    "Announcement",
                    "This book has been added to your library",
                    "stylesheet (css)/login_alert.css"
            );
        }
    }

    // Phương thức xử lý khi người dùng nhấn nút "Reset"
    public void resetInfo(ActionEvent event) {
        // Xóa nội dung trong tất cả các ô nhập liệu
        titleField.clear();       // Xóa nội dung ô nhập tên sách
        authorField.clear();      // Xóa nội dung ô nhập tên tác giả
        categoryField.clear();    // Xóa nội dung ô nhập thể loại
        descriptionField.clear(); // Xóa nội dung ô nhập mô tả
    }

    // Phương thức xử lý khi người dùng nhấn nút "Back"
    public void backToPreviousStage(ActionEvent event) {
        WindowManager.goBack();
    }
}
