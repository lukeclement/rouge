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
    public static int HEIGHT=640;
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
        final Maze mazy=new Maze(1024,1024);
        System.out.println("Made maze!");
        Image back=new Image("res/Background/back.png");
        Image floor = new Image("res/Background/fancywall.png");
        Image white = new Image("res/Background/white.png");

        new AnimationTimer(){
            public boolean generated=false;
            public List<Integer> visitedX=new ArrayList<>();
            public List<Integer> visitedY=new ArrayList<>();
            public int s=0;
            public long startNanoTime=System.nanoTime();
            int smallOffsetX = 0;
            int smallOffsetY = 0;
            int offsetX = WIDTH/2;
            int offsetY = HEIGHT/2;
            int[][] map = mazy.getGrid();

            public void drawBacking(){
                if(smallOffsetX>64){
                    smallOffsetX=0;
                    offsetX++;
                }else if(smallOffsetX<-64){
                    smallOffsetX=0;
                    offsetX--;
                }
                if(smallOffsetY>64){
                    smallOffsetY=0;
                    offsetY++;
                }else if(smallOffsetY<-64){
                    smallOffsetY=0;
                    offsetY--;
                }
                for(int i=-1;i<(WIDTH/64)+1;i++){
                    for(int j=-1;j<(HEIGHT/64)+1;j++){
                        //Draw new images
                        if(map[(offsetX+i)][offsetY+j]==1){
                            gc.drawImage(floor,i*64-smallOffsetX,j*64-smallOffsetY);

                        }
                        else{
                            gc.drawImage(white,i*64-smallOffsetX,j*64-smallOffsetY);
                        }
                    }
                }
                //Drawing character sprite
                gc.drawImage(new Image("res/Wizards/grey.png"),WIDTH/2,HEIGHT/2);
            }



            //Updating the scene at 60 times per second
            public void handle(long currentNanoTime){
                //gc.drawImage(back,0,0);
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                //if(t>0.1){
                  //  startNanoTime=System.nanoTime();
                    //mazy.generate(s++);
                //}
                /*if(input.contains("A")){
                    gc.drawImage(back,0,0);
                    mazy.generate(0);
                }*/
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


                if(input.contains("W")){
                    smallOffsetY -= 5;
                }
                if(input.contains("A")){
                    smallOffsetX -= 5;
                }
                if(input.contains("S")){
                    smallOffsetY += 5;
                }
                if(input.contains("D")){
                    smallOffsetX += 5;
                }
                //drawBacking();
            }
        }.start();
        stage.show();

    }
}
