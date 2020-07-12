package GameEngine;

public class Main {
    public static void main(String []args){
        GameEngine gameEngine =GameEngine.getInstance("Breakout", 768, 640);
        gameEngine.start();
    }
}
