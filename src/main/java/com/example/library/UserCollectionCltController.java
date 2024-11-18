package com.example.library;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserCollectionCltController {

    public void handleConfirmButton(ActionEvent actionEvent) throws IOException, SQLException;

    public void showCollectionData(ActionEvent actionEvent) throws IOException;

    public Map<Character, List<Book>> sortByTitle(List<Book> books);

    public void handleAllCollectionButton(ActionEvent actionEvent) throws IOException;

    public void handleGameButton(ActionEvent event) throws IOException;

}
