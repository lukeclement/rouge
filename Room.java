public class Room{
  private int height;
  private int width;
  private int startX;
  private int endX;
  private int startY;
  private int endY;
  private boolean secret;

  public Room(int w, int h, boolean secret){
    height=h;
    width=w;
    this.secret=secret;
  }

  public void setPosition(int sx, int sy){
    startX=sx;
    startY=sy;
  }
  public int getStartX(){
    return startX;
  }
  public int getStartY(){
    return startY;
  }public int getEndX(){
    return startX+width;
  }
  public int getEndY(){
    return startY+height;
  }

  public int getHeight(){
    return height;
  }
  public int getWidth(){
    return width;
  }
}
