class Frog{
    private int positionx=0;
    private int positiony=0;
    private int o=0;
    public Frog(int posx, int posy){
        positionx=posx;
        positiony=posy;
    }

    public void random(){
      int num=(int)Math.ceil(Math.random()*4);
      switch(num){
        case 1: left();
        break;
        case 2: right();
        break;
        case 3: up();
        break;
        case 4: down();
        break;
      }
    }

    public void left(){
        positionx--;
        o=0;
        return;
    }
    public void right(){
        positionx++;
        o=1;
        return;
    }
    public void up(){
        positiony--;
        o=2;
        return;
    }
    public void down(){
        positiony++;
        o=3;
        return;
    }
    public int getPositionx(){
        return positionx;
    }
    public int getPositiony(){
        return positiony;
    }
    public int getOrientation(){
        return o;
    }
    public void setPosition(int positionX,int positionY){
        positionx=positionX;
        positiony=positionY;
        return;
    }
}
