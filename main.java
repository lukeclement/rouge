import java.io.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.animation.*;
import javafx.event.*;

public class main extends Application{
  public static int HEIGHT=1024;
  public static int WIDTH=1024;


  public static void main(String[] args){
    launch(args);
  }

  public void start(Stage stage){
    stage.setTitle("Mappty Mazy Map");

    Group root=new Group();
    Scene scene=new Scene(root);

    stage.setScene(scene);

    Canvas canvas=new Canvas(WIDTH,HEIGHT);
    GraphicsContext gc=canvas.getGraphicsContext2D();

    root.getChildren().add(canvas);

    List<String> input=new ArrayList<>();

    scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      public void handle(KeyEvent e){
        String code=e.getCode().toString();
        if(!input.contains(code)){
          //System.out.print(code+";");
          input.add(code);
        }
      }
    });
    //Removing keys that have been released
    scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
      public void handle(KeyEvent e){
        String code=e.getCode().toString();
        input.remove(code);
      }
    });

    /*WritableImage wim = new WritableImage(width, height);
                      canvas.snapshot(null,wim);
                      try{
                        ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", new File("Maze.png"));
			                  System.out.println("Saved!");
                      }
                      catch(Exception ex){

                      }*/




    List<Frog> frogs=new ArrayList<>();
    frogs.add(new Frog(20,20));
    System.out.println("Making maze");
    final Maze mazy=new Maze(WIDTH,HEIGHT);
    System.out.println("Made maze!");
    Image back=new Image("back.png");



    new AnimationTimer(){
      public boolean generated=false;
      public List<Integer> visitedX=new ArrayList<>();
      public List<Integer> visitedY=new ArrayList<>();
      public int s=0;
      public long startNanoTime=System.nanoTime();
      //Updating the scene at 60 times per second
      public void handle(long currentNanoTime){
        gc.drawImage(back,0,0);
        double t = (currentNanoTime - startNanoTime) / 1000000000.0;
        if(t>0.1){
          startNanoTime=System.nanoTime();
          //mazy.generate(s++);
        }
        if(input.contains("A")){
          gc.drawImage(back,0,0);
          mazy.generate(0);
        }
        for(int i=0;i<WIDTH;i++){
          for(int j=0;j<HEIGHT;j++){
            if(mazy.getGrid()[i][j]==1){
              //gc.setFill(Color.rgb(0,0,0));
              gc.fillOval(i,j,1,1);
            }else{
              //gc.setFill(Color.rgb(0,0,255));
              //gc.fillOval(i,j,1,1);
            }
          }
        }
      }
    }.start();
    stage.show();
  }
}
