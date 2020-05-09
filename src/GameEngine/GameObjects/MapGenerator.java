package GameEngine.GameObjects;

import GameEngine.Game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MapGenerator {
    private static final int row = 4, column = 8, width = 88, height = 44;
    public static void generateMap(Game game, int level) {
        ObjectFactory factory=ObjectFactory.getInstance();
        int i = 0, j = 0;
        try {
            File myObj = new File("./src/GameEngine/Levels/level" + level + ".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextInt()) {
                int data = myReader.nextInt();
                game.getObjectsManager().getObjects().add(factory.getBrick(game, (j * width + 14 + j * 5), i * height + 60 + i * 5, data % 5, data / 5 + 1));
                j++;
                if (j == column) {
                    j = 0;
                    ++i;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
