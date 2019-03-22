package cbatl;

import cbatl.controller.CBatLController;
import cbatl.model.player.PlayerManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class App {
  public static void main(String[] args) {
    PlayerManager playerManager;
    File playerFile = new File("players.csv");
    try {
      playerFile.createNewFile();
      playerManager = PlayerManager.createFromFile(new FileReader(playerFile));
    } catch (IOException | IllegalStateException | IllegalArgumentException e) {
      System.out.println("Attention: la tentative de chargement du fichier de sauvegarde a échouée.");
      playerManager = new PlayerManager();
    }

    CBatLController controller = new CBatLController(playerManager);
    //controller.attachView();

    /*try {
      playerManager.saveToFile(new FileWriter(playerFile));
    } catch (IOException | IllegalStateException | IllegalArgumentException e) {
      System.out.println("Attention: la tentative de sauvegarde a échouée.");
    }*/
  }
}
