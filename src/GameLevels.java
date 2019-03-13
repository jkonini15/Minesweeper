package mine;

public class GameLevels {
    private int height;
    private int width;
    private int mines;
        
        public GameLevels(String level){
            switch(level){
                case "SMALL": SmallLevel();
                break;
                case "MEDIUM": MediumLevel();
                break;
                case "LARGE": LargeLevel();
                break;
                default:
                    SmallLevel();
            }
        }

    private void SmallLevel() {
        this.width=10;
        this.height=10;
        this.mines=10;
    }

    private void MediumLevel() {
        this.width=15;
        this.height=15;
        this.mines=15;
    }

    private void LargeLevel() {
        this.width=25;
        this.height=25;
        this.mines=25;
    }
    
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public int getMines(){
        return mines;
    }

    public  GameLevels(int width,int height,int mines){ 
        this.height=height;
        this.width=width;
        this.mines=mines;
        
    }
    
    
}