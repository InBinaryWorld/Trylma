package pl.project.trylma.Models.boards;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Field;

import java.util.List;

//Singleton
public final class Board {
  private static volatile Board instance;
  private Field fields[][];

  //TODO;
  public static Board getInstance(){return null;}

  //TODO;
  private boolean checkVal(Field from, Coord to) {
    return false;
  }

  //TODO;
  public boolean makeMove(Field from, Coord to) {
    return false;
  }

  //TODO;
  public List<Field> getAllPlayersFields() {
    return null;
  }

  //TODO;
  public Boolean isWinner() {
    return null;
  }
}
