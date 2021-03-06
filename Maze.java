import java.util.*;

public class Maze{
  private int height;
  private int width;
  private int[][] grid;

  public Maze(int x,int y,String input){
    height=y;
    width=x;
    grid=new int[x][y];
    generate(0);
    int p=0;
    for(int i=0;i<x;i++){
      for(int j=0;j<y;j++){
        grid[i][j]=Integer.parseInt(input.split(p,p+1));
        p++;
      }
    }
  }

  public int rand(int a, int b){
    return a+(int)Math.floor(Math.random()*(b-a));
  }

  public int[][] getGrid(){
    return grid;
  }

  public void generate(int num){
    for(int i=0;i<width;i++){
      for(int j=0;j<width;j++){
        grid[i][j]=0;
      }
    }
    int numberOfRooms=rand(100,800);
    //numberOfRooms=num;
    List<Room> rooms=new ArrayList<>();
    System.out.println("Making rooms...");
    for(int i=0;i<numberOfRooms;i++){
      rooms.add(new Room(rand(5,30),rand(5,30),false));
    }
    List<Room> placedRooms=new ArrayList<>();
    System.out.println("Placing rooms...");
    for(Room room:rooms){
      boolean placed=false;
      while(!placed){
        placed=true;
        int placementX=rand(0,width-room.getWidth());
        int placementY=rand(0,height-room.getHeight());
        room.setPosition(placementX,placementY);
        for(Room placedRoom:placedRooms){
          for(int i=placedRoom.getStartY();i<placedRoom.getEndY();i++){
            for(int j=placedRoom.getStartX();j<placedRoom.getEndX();j++){
              if((i==room.getStartX()&&j==room.getStartY())||(i==room.getEndX()&&j==room.getStartY())||(i==room.getStartX()&&j==room.getEndY())||(i==room.getEndX()&&j==room.getEndY())){
                placed=false;
                //break;
              }
            }
          }
        }
      }
      placedRooms.add(room);
      System.out.println("Made room "+placedRooms.size());

    }
    System.out.println("Making tunnels..");
    List<Integer> roomsToRemove=new ArrayList<>();
    List<Room> roomsLinked = new ArrayList<>();
    for(int i=1;i<rooms.size();i++){
      roomsLinked.add(rooms.get(i-1));
      Room currentRoom=rooms.get(i);
      Room linkTo=currentRoom.getNearestRoom(roomsLinked);
      Tunnel nextTunnel=new Tunnel(linkTo,currentRoom,false);
      double dx=nextTunnel.getStartX()-nextTunnel.getEndX();
      double dy=nextTunnel.getStartY()-nextTunnel.getEndY();
      //System.out.println(nextTunnel.getStartX+" "+" "+i+" "+nextTunnel.getStartX());
      if(Math.abs(dy/dx)<1){
        double c=nextTunnel.getStartY()-(dy/dx)*nextTunnel.getStartX();
        if(nextTunnel.getStartX()<nextTunnel.getEndX()){
          for(int x=nextTunnel.getStartX();x<nextTunnel.getEndX();x++){
            grid[x][(int)((dy/dx)*x+c)]=1;
            grid[x][(int)((dy/dx)*x+c+1)]=1;
          }
        }else{
          for(int x=nextTunnel.getStartX();x>nextTunnel.getEndX();x--){
            grid[x][(int)((dy/dx)*x+c)]=1;
            grid[x][(int)((dy/dx)*x+c+1)]=1;
          }
        }
      }else{ // Steep gradient (treat var x as y)
        double c=nextTunnel.getStartX()-(dx/dy)*nextTunnel.getStartY();
        if(nextTunnel.getStartY()<nextTunnel.getEndY()){
          for(int y=nextTunnel.getStartY();y<nextTunnel.getEndY();y++){
            grid[(int)((dx/dy)*y+c)][y]=1;
            grid[(int)((dx/dy)*y+c+1)][y]=1;
          }
        }else{
          for(int y=nextTunnel.getStartY();y>nextTunnel.getEndY();y--){
            grid[(int)((dx/dy)*y+c)][y]=1;
            grid[(int)((dx/dy)*y+c+1)][y]=1;
          }
        }
        /*if(roomsToRemove.contains(i-1)){
          if(roomsToRemove.contains(i)){
            roomsToRemove.remove((Integer)(i));
          }
          roomsToRemove.remove((Integer)(i-1));
        }*/
      }
    }
    /*for(int i=roomsToRemove.size();i>0;i--){
      //rooms.remove(rooms.get(i-1));
    }*/
    for(Room room:rooms){
      for(int i=room.getStartY();i<room.getEndY();i++){
        for(int j=room.getStartX();j<room.getEndX();j++){
          grid[j][i]=1;
        }
      }
    }
  }

}
