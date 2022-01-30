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

    static Repository repo1, repo2, repo3, repo4, repo5, repo6, repo7, repo8;
    static Controller ctrl1, ctrl2, ctrl3, ctrl4, ctrl5, ctrl6, ctrl7, ctrl8;
    //static IStmt firstProgram, secondProgram, thirdProgram, fourthProgram, lastProgram;
    @FXML
    ListView<Controller> myPrgList;
    @FXML
    Button runButton;

    public void setUp() {
        repo1 = new Repository("log1.txt");
        ctrl1 = new Controller(repo1, false);
        repo2 = new Repository("log2.txt");
        ctrl2 = new Controller(repo2, false);
        repo3 = new Repository("log3.txt");
        ctrl3 = new Controller(repo3, false);
        repo4 = new Repository("log4.txt");
        ctrl4 = new Controller(repo4, false);
        repo5 = new Repository("log5.txt");
        ctrl5 = new Controller(repo5, false);
        repo6 = new Repository("log6.txt");
        ctrl6 = new Controller(repo6, false);
        repo7 = new Repository("log7.txt");
        ctrl7 = new Controller(repo7, false);
        repo8 = new Repository("log8.txt");
        ctrl8 = new Controller(repo8, false);

        IStmt stmt1 = new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));

        IStmt stmt2 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                        new PrintStmt(new ArithExp('+', new ReadHeapExp(new VarExp("v")),
                                                new ValueExp(new IntValue(5))))))));

        IStmt stmt3 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"),
                                new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"),
                                                new ValueExp(new IntValue(1)))))),
                                new PrintStmt(new VarExp("v")))));

        IStmt stmt4 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                                        VarExp("v"))))));
        IStmt stmt5 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(
                                                new ForkStmt(new CompStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                                        new PrintStmt(new ReadHeapExp(new VarExp("a"))))))),
                                                new CompStmt(new PrintStmt(new VarExp("v")),
                                                        new PrintStmt(new ReadHeapExp(new VarExp("a")))))))));

        IStmt stmt6 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new
                                ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new
                                        IntValue(1)))),
                                        new CompStmt(new PrintStmt(new VarExp("a")), new PrintStmt(new VarExp("b")))))));

        IStmt stmt7 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                            new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                    new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(2))),
                                            new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(3))),
                                                    new CompStmt(new NewStmt("v3", new ValueExp(new IntValue(4))),
                                                            new CompStmt(new NewBarrierStmt("cnt", new ReadHeapExp(new VarExp("v2"))),
                 new CompStmt(new ForkStmt(
                         new CompStmt(new AwaitBarrierStmt("cnt"),
                                 new CompStmt(new WriteHeapStmt("v1", new ArithExp('*', new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                       new PrintStmt(new ReadHeapExp(new VarExp("v1")))
                                 )
                         )
                 ),
                    new CompStmt(new ForkStmt(
                            new CompStmt(new AwaitBarrierStmt("cnt"),
                                    new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                            new CompStmt(new WriteHeapStmt("v2", new ArithExp('*', new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                    new PrintStmt(new ReadHeapExp(new VarExp("v2")))
                                                )
                                            )
                                         )
                                    ),
                            new CompStmt(new AwaitBarrierStmt("cnt"), new PrintStmt(new ReadHeapExp(new VarExp("v3"))))
                 )
                                                                    )
                                                            )
                                                    )
                                    )
                               )
                            )
                        )
                    )
                );

        IStmt stmt8 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("x", new IntType()),
                        new CompStmt(new VarDeclStmt("y", new IntType()),
                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(0))),
                                        new CompStmt(
                                                new RepeatUntilStmt(
                                                        new CompStmt(
                                                                new ForkStmt(
                                                                    new CompStmt(
                                                                        new PrintStmt(new VarExp("v")),
                                                                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1))))
                                                            )
                                                        ),
                                                                new AssignStmt("v", new ArithExp('+', new VarExp("v"), new ValueExp(new IntValue(1))))
                                                        ),
                                                        new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(3)), "==")
                                                ),
                                                new CompStmt(new AssignStmt("x", new ValueExp(new IntValue(1))),
                                                        new CompStmt(new NopStmt(),
                                                                new CompStmt(new AssignStmt("y", new ValueExp(new IntValue(3))),
                                                                        new CompStmt(new NopStmt(),
                                                                                new PrintStmt(new ArithExp('*', new VarExp("v"), new ValueExp(new IntValue(10)))))
                                                                )
                                                        )
                                                )
                                        )))));

        MyIStack<IStmt> exeStack1 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable1 = new MyDictionary<String, Value>();
        MyIList<Value> out1 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable1 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap1 = new MyHeap();
        PrgState prg1 = new PrgState(exeStack1, symTable1, out1, fileTable1, heap1, stmt1);
        ctrl1.addProgram(prg1);

        MyIStack<IStmt> exeStack2 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable2 = new MyDictionary<String, Value>();
        MyIList<Value> out2 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable2 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap2 = new MyHeap();
        PrgState prg2 = new PrgState(exeStack2, symTable2, out2, fileTable2, heap2, stmt2);
        ctrl2.addProgram(prg2);

        MyIStack<IStmt> exeStack3 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable3 = new MyDictionary<String, Value>();
        MyIList<Value> out3 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable3 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap3 = new MyHeap();
        PrgState prg3 = new PrgState(exeStack3, symTable3, out3, fileTable3, heap3, stmt3);
        ctrl3.addProgram(prg3);

        MyIStack<IStmt> exeStack4 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable4 = new MyDictionary<String, Value>();
        MyIList<Value> out4 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable4 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap4 = new MyHeap();
        PrgState prg4 = new PrgState(exeStack4, symTable4, out4, fileTable4, heap4, stmt4);
        ctrl4.addProgram(prg4);

        MyIStack<IStmt> exeStack5 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable5 = new MyDictionary<String, Value>();
        MyIList<Value> out5 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable5 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap5 = new MyHeap();
        PrgState prg5 = new PrgState(exeStack5, symTable5, out5, fileTable5, heap5, stmt5);
        ctrl5.addProgram(prg5);

        MyIStack<IStmt> exeStack6 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable6 = new MyDictionary<String, Value>();
        MyIList<Value> out6 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable6 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap6 = new MyHeap();
        PrgState prg6 = new PrgState(exeStack6, symTable6, out6, fileTable6, heap6, stmt6);
        ctrl6.addProgram(prg6);

        MyIStack<IStmt> exeStack7 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable7 = new MyDictionary<String, Value>();
        MyIList<Value> out7 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable7 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap7 = new MyHeap();
        MyIBarrierTable barrierTable7 = new MyBarrierTable();
        PrgState prg7 = new PrgState(exeStack7, symTable7, out7, fileTable7, heap7, stmt7, barrierTable7);
        ctrl7.addProgram(prg7);

        MyIStack<IStmt> exeStack8 = new MyStack<IStmt>();
        MyIDictionary<String, Value> symTable8 = new MyDictionary<String, Value>();
        MyIList<Value> out8 = new MyList<Value>();
        MyIDictionary<String, BufferedReader> fileTable8 = new MyDictionary<String, BufferedReader>();
        MyIHeap heap8 = new MyHeap();
        PrgState prg8 = new PrgState(exeStack8, symTable8, out8, fileTable8, heap8, stmt8);
        ctrl8.addProgram(prg8);


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUp();
        ObservableList<Controller> myData = FXCollections.observableArrayList();
//        myData.add(ctrl1);
//        myData.add(ctrl2);
//        myData.add(ctrl3);
//        myData.add(ctrl4);
//        myData.add(ctrl5);
//        myData.add(ctrl6);
        myData.add(ctrl7);
//        myData.add(ctrl8);
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
