package GameEngine.GameObjects;

import GameEngine.States.PlayState.PlayingState;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*! \class MapGenerator
    \brief Clasa ce genereaza hartile corespunzatoare nivelelor.
    Clasa are rolul de a citi dintr-un fisier informatii referitoare la tipul caramizilor generate,
    cere fabricii de obiecte sa creeze obiectul corespunzator, pe care mai apoi il adauga in
    lista cu obiecte detinuta de instanta clasei ObjectsManager.
 */

public class MapGenerator {
    private static final int row = 4;
    private static final int column = 8;
    private static final int width = 88;
    private static final int height = 44;
    public static void generateMap(PlayingState playingState, int level, int difficulty) {
        ObjectsFactory factory= ObjectsFactory.getInstance();
        try {
            File myObj = new File("./src/GameEngine/Levels/level" + level +"d"+difficulty+".txt");
            Scanner myReader = new Scanner(myObj);
            for(int i=0;i<row+difficulty-1;++i)
            {
                for(int j=0;j<column+difficulty-1;++j)
                {
                    if(myReader.hasNextInt()) {
                        int data = myReader.nextInt();
                        if (data != -1) {
                            playingState.getObjectsManager().addObject(((Brick) factory.getObject(playingState, "brick")).
                                    setDetails((j * (width - (difficulty - 1) * 12) + 14 + (difficulty - 1) * 8 + j * 5), i * (height - (difficulty - 1) * 6) + 60 + i * 5,
                                            data % (5 + (difficulty - 1) * 2), data / (5 + (difficulty - 1) * 2) + 1, difficulty));
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
