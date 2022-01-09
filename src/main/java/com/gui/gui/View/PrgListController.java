package com.gui.gui.View;

import com.gui.gui.Controller.Controller;
import com.gui.gui.Domain.ADT.*;
import com.gui.gui.Domain.Exceptions.MyException;
import com.gui.gui.Domain.Expressions.*;
import com.gui.gui.Domain.PrgState;
import com.gui.gui.Domain.Statements.*;
import com.gui.gui.Domain.Types.*;
import com.gui.gui.Domain.Values.BoolValue;
import com.gui.gui.Domain.Values.IntValue;
import com.gui.gui.Domain.Values.StringValue;
import com.gui.gui.Domain.Values.Value;
import com.gui.gui.MainInterpreter;
import com.gui.gui.Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrgListController implements Initializable {

    static Repository myFirstRepository, mySecondRepository, myThirdRepository, myFourthRepository, myLastRepository;
    static Controller myFirstController, mySecondController, myThirdController, myFourthController, myLastController;
    //static IStmt firstProgram, secondProgram, thirdProgram, fourthProgram, lastProgram;
    @FXML
    ListView<Controller> myPrgList;
    @FXML
    Button runButton;

    public void setUp() {
        myFirstRepository = new Repository("firstProgramLog.txt");
        myFirstController = new Controller(myFirstRepository, false);
        mySecondRepository = new Repository("secondProgramLog.txt");
        mySecondController = new Controller(mySecondRepository, false);
        myThirdRepository = new Repository("thirdProgramLog.txt");
        myThirdController = new Controller(myThirdRepository, false);
        myFourthRepository = new Repository("fourthProgramLog.txt");
        myFourthController = new Controller(myFourthRepository, false);
        myLastRepository = new Repository("lastProgramLog.txt");
        myLastController = new Controller(myLastRepository, false);

        IStmt firstProgram = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));

        IStmt secondProgram = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("v")),
                                                new ValueExp(new IntValue(5))))))));

        IStmt thirdProgram = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"),
                                new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"),
                                                new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        IStmt fourthProgram = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));

        IStmt lastProgram = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new
                                        IntValue(1)))),
                                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b")))))));

        MyIStack<IStmt> exeStack1 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable1 = new MyDictionary<String, Value>();
        MyIList<Value> out1 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable1 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap1 = new MyHeap();
        PrgState myPrgState1 = new PrgState(exeStack1, symTable1, out1, fileTable1, heap1, firstProgram);
        myFirstController.addProgram(myPrgState1);
        MyIStack<IStmt> exeStack2 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable2 = new MyDictionary<String, Value>();
        MyIList<Value> out2 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable2 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap2 = new MyHeap();
        PrgState myPrgState2 = new PrgState(exeStack2, symTable2, out2, fileTable2, heap2, secondProgram);
        mySecondController.addProgram(myPrgState2);
        MyIStack<IStmt> exeStack3 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable3 = new MyDictionary<String, Value>();
        MyIList<Value> out3 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable3 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap3 = new MyHeap();
        PrgState myPrgState3 = new PrgState(exeStack3, symTable3, out3, fileTable3, heap3, thirdProgram);
        myThirdController.addProgram(myPrgState3);
        MyIStack<IStmt> exeStack4 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable4 = new MyDictionary<String, Value>();
        MyIList<Value> out4 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable4 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap4 = new MyHeap();
        PrgState myPrgState4 = new PrgState(exeStack4, symTable4, out4, fileTable4, heap4, fourthProgram);
        myFourthController.addProgram(myPrgState4);
        MyIStack<IStmt> exeStack5 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable5 = new MyDictionary<String, Value>();
        MyIList<Value> out5 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable5 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap5 = new MyHeap();
        PrgState myLastPrgState = new PrgState(exeStack5, symTable5, out5, fileTable5, heap5, lastProgram);
        myLastController.addProgram(myLastPrgState);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUp();
        ObservableList<Controller> myData = FXCollections.observableArrayList();
        myData.add(myFirstController);
        myData.add(mySecondController);
        myData.add(myThirdController);
        myData.add(myFourthController);
        myData.add(myLastController);
        myPrgList.setItems(myData);
        myPrgList.getSelectionModel().selectFirst();
        runButton.setOnAction(e -> {
            try{
                myPrgList.getSelectionModel().getSelectedItem().getCurrent().getOriginalProgram()
                        .typecheck(new MyDictionary<String, Type>());
            }
            catch (MyException exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Program failed");
                alert.setContentText("Program did not pass the typecheck!");
                Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                alert.showAndWait();
                return;
            }
            Stage programStage = new Stage();
            Parent programRoot;

            Callback<Class<?>, Object> controllerFactory = type -> {
                if (type == PrgRunController.class) {
                    return new PrgRunController(myPrgList.getSelectionModel().getSelectedItem());
                } else {
                    try {
                        return type.newInstance() ;
                    } catch (Exception exc) {
                        System.err.println("Could not create controller for "+type.getName());
                        throw new RuntimeException(exc);
                    }
                }
            };

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainInterpreter.class.getResource("ProgramLayout.fxml"));
                fxmlLoader.setControllerFactory(controllerFactory);
                programRoot = fxmlLoader.load();
                Scene programScene = new Scene(programRoot);
                programStage.setTitle("Running Program");
                programStage.setScene(programScene);
                programStage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

}
