package com.gui.gui.View;

import com.gui.gui.Controller.Controller;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Statements.IStmt;
import com.gui.gui.Domain.Values.Value;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class PrgRunController implements Initializable {

    Controller myController;
    @FXML
    Label nrPrgStates;
    @FXML
    Button runButton;
    @FXML
    TableView<HashMap.Entry<Integer, Value>> heapTable;
    @FXML
    TableColumn<HashMap.Entry<Integer, Value>, String> heapTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer, Value>, String> heapTableValue;
    @FXML
    ListView<String> outList;
    @FXML
    ListView<String> fileList;
    @FXML
    ListView<String> prgStateList;
    @FXML
    TableView<HashMap.Entry<String, Value>> symTable;
    @FXML
    TableColumn<HashMap.Entry<String, Value>, String> symTableVariable;
    @FXML
    TableColumn<HashMap.Entry<String, Value>, String> symTableValue;
    @FXML
    ListView<String> exeStackList;

    public PrgRunController(Controller myController) {
        System.out.println(myController.getRepository().getCrtPrg().getStk());
        this.myController = myController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialRun();
        prgStateList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSymTableAndExeStack();
            }
        });
        runButton.setOnAction(e -> {
            try {
                myController.oneStepGUI();
            } catch (MyException e1) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Toy Language - Current program finished");
                alert.setHeaderText(null);
                alert.setContentText("Program successfully finished!");
                Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                alert.showAndWait();
                Stage stage = (Stage) heapTable.getScene().getWindow();
                stage.close();
            }
            updateUIComponents();
        });
    }

    public void initialRun() {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        prgStateList.getSelectionModel().selectFirst();
        setSymTableAndExeStack();
    }

    public void updateUIComponents() {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        if (prgStateList.getSelectionModel().getSelectedItem() == null) {
            prgStateList.getSelectionModel().selectFirst();
        }
        setSymTableAndExeStack();
    }

    public void setNumberLabel() {
        nrPrgStates.setText("The number of PrgStates: " + myController.getRepository().getPrgList().size());
    }

    public void setHeapTable() {
        ObservableList<HashMap.Entry<Integer, Value>> heapTableList = FXCollections.observableArrayList();
        heapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        heapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        heapTableList.addAll(myController.getRepository().getPrgList().get(0).getHeap().getContent().entrySet());
        heapTable.setItems(heapTableList);
    }

    public void setOutList() {
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        for (Value e : myController.getRepository().getPrgList().get(0).getOut().getList()) {
            outObservableList.add(e.toString());
        }
        outList.setItems(outObservableList);
    }

    public void setFileTable() {
        ObservableList<String> fileTableList = FXCollections.observableArrayList();
        fileTableList.addAll(myController.getRepository().getPrgList().get(0).getFileTable().getContent().keySet());
        fileList.setItems(fileTableList);
    }

    public void setPrgStateList() {
        ObservableList<String> prgStateIdList = FXCollections.observableArrayList();
        for (PrgState e : myController.getRepository().getPrgList()) {
            prgStateIdList.add(Integer.toString(e.getId()));
        }
        prgStateList.setItems(prgStateIdList);
    }

    public void setSymTableAndExeStack() {
        ObservableList<HashMap.Entry<String, Value>> symTableList = FXCollections.observableArrayList();
        ObservableList<String> exeStackItemsList = FXCollections.observableArrayList();
        symTableVariable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        symTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        List<PrgState> allPrgs = myController.getRepository().getPrgList();
        PrgState prgResult = null;
        for (PrgState e : allPrgs) {
            if (e.getId() == Integer.parseInt(prgStateList.getSelectionModel().getSelectedItem())) {
                prgResult = e;
                break;
            }
        }
        if (prgResult != null) {
            symTableList.addAll(prgResult.getSymTable().getContent().entrySet());
            for (IStmt e : prgResult.getStk().getStack()) {
                exeStackItemsList.add(e.toString());
            }
            symTable.setItems(symTableList);
            exeStackList.setItems(exeStackItemsList);
        }
    }

}
