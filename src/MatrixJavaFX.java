package mine;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MatrixJavaFX extends Application {

    static GameLevels Gl;
    static int matrixNr[][];
    Button[][] matrix; //names the grid of buttons
    static  ComboBox comboBox;
    static int length;
    static int width;
    ArrayList<Integer> array;
    static Button ok;
    static GridPane gp;
    TextField h,w,m;
@Override
public void start(Stage primaryStage) {
   //COmbo Box for game Levels
    ObservableList<String> options = 
    FXCollections.observableArrayList(
        "SMALL",
        "MEDIUM",
        "LARGE",
        "COSTUM"
    );
     comboBox = new ComboBox(options);//create the combo box with the above options
     comboBox.getSelectionModel().selectFirst();//select for "SMALL" to be seen as default to the combobox
   
     //Buton Ok for Level Generator
       ok=new Button("ok");//okay button to set on action the given input level
    //set Small level as default
    generate("SMALL");
    generate1(primaryStage,"SMALL");
    
    
}  
public void generate(String level){
    h=new TextField();
    w=new TextField();
    m=new TextField();
    
    if(level.equals("SMALL") || level.equals("MEDIUM")|| level.equals("LARGE" )){
         Gl=new GameLevels(level);//set the level of the game
    }
    
    MatrixGeneration m=new MatrixGeneration(Gl.getWidth(),Gl.getHeight());
    boolean visited[][]=new boolean[Gl.getWidth()][Gl.getHeight()];
    
    int bombMatrix[][]=m.bombGenerator(Gl.getMines());//generate the bombs 
   
    //generate the matrix
    matrixNr=m.matrixGenerator(bombMatrix);
    
    //test the blank space counter
    BlankSpace bs=new BlankSpace(Gl.getWidth(),Gl.getHeight());
    System.out.println(bs.DFS(matrixNr, 6, 6, visited));
}

public void generate1(Stage primaryStage,String level){
    
    //Gl=new GameLevels(level);//set the level of the game
    gp = new GridPane();//gridpane to generate the matrix of the buttons 

    matrix = new Button[Gl.getWidth()][Gl.getHeight()]; 
    BlankSpace bs=new BlankSpace(Gl.getWidth(),Gl.getHeight());
    boolean visited[][]=new boolean[Gl.getWidth()][Gl.getHeight()];
    
    //ok button 
     VBox root=new VBox();
     VBox root1=new VBox();
    ok.setOnAction((ActionEvent event) -> {
        String level1 = comboBox.getValue()+""; //get the value from the combo box
        if (level1.equals("SMALL")) {
            generate(level1);
            generate1(primaryStage, level1);
        }
        if (level1.equals("MEDIUM")) {
            //if it is medium change the length and width of matrix to the medium level parameters
            generate(level1); //matrix generator
            generate1(primaryStage, level1); //gui generator
        }
        if (level1.equals("LARGE")) {
            //if it is large change the length and width of the button matrix to large
            generate(level1);
            generate1(primaryStage, level1);
        }
        if (level1.equals("COSTUM")) {
            //if it is large change the length and width of the button matrix to large
            
            HBox box=new HBox();
            Button s=new Button("Submit");
            box.getChildren().addAll(h,w,m);
            
            
            s.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                    String a=h.getText();
                    String b=w.getText();
                    String c=m.getText();
                    MatrixGeneration m1=new MatrixGeneration(Integer.parseInt(a),Integer.parseInt(b));
                    // Gl=new GameLevels(Integer.parseInt(a),Integer.parseInt(b),Integer.parseInt(c));
                    BlankSpace bs1=new BlankSpace(Integer.parseInt(a),Integer.parseInt(b));
                    int bombMatrix[][]=m1.bombGenerator(Integer.parseInt(c));//generate the bombs
                    
                    //generate the matrix
                    matrixNr=m1.matrixGenerator(bombMatrix);
                    Button matrix1[][] = new Button[Integer.parseInt(a)][Integer.parseInt(b)];
                    boolean visited1[][]=new boolean[Integer.parseInt(a)][Integer.parseInt(b)];
                    for(int y = 0; y < (Integer.parseInt(a)); y++)
                    {
                        for(int x = 0; x < Integer.parseInt(b); x++)
                        {
                            matrix1[x][y] = new Button();
                            
                            final Button b1 = matrix1[x][y];
                            b1.setText("");
                            matrix1[x][y].setMinWidth(30);
                            final int i=x;
                            final int j=y;
                            
                            b1.setOnAction(new EventHandler<ActionEvent>() {
                                
                                @Override
                                public void handle(ActionEvent event) {
                                    if(matrixNr[i][j]==0){
                                        array=bs1.DFS(matrixNr, i, j, visited1);
                                        for(int a=0;a<array.size();a+=2){
                                            matrix1[array.get(a)][array.get(a+1)].setText(""+matrixNr[array.get(a)][array.get(a+1)]);
                                        }
                                    }
                                    matrix1[i][j].setText(""+matrixNr[i][j]);
                                }                               
                            });
                            gp.add(matrix1[x][y],y,x);
                        }
                    }
                }
            });
            root1.getChildren().addAll(comboBox,ok,box,s,gp);
            Scene scene = new Scene(root1);
            primaryStage.setTitle("");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
    } //the okay button
    );
    //generate(level);
    for(int y = 0; y < Gl.getHeight(); y++)
    {
        for(int x = 0; x < Gl.getWidth(); x++)
        {
            matrix[x][y] = new Button();
                
                final Button b1 = matrix[x][y]; 
                b1.setText("");
                matrix[x][y].setMinWidth(30);
                final int i=x;
                final int j=y;
                
                b1.setOnAction(new EventHandler<ActionEvent>() {
                    
                    @Override
                 public void handle(ActionEvent event) {
                        if(matrixNr[i][j]==0){
                            array=bs.DFS(matrixNr, i, j, visited);
                            for(int a=0;a<array.size();a+=2){
                                matrix[array.get(a)][array.get(a+1)].setText(""+matrixNr[array.get(a)][array.get(a+1)]);
                            }
                        }
                        matrix[i][j].setText(""+matrixNr[i][j]);
                    }
                });
                gp.add(matrix[x][y],y,x);    
        }   
    }
    
    root.getChildren().addAll(comboBox,ok,gp);
    Scene scene = new Scene(root);
    primaryStage.setTitle("");
    primaryStage.setScene(scene);
    primaryStage.show();
}


public static void main(String[] args) {
    
    //Gui
    launch(args);

}
}

