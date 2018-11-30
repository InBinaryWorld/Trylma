package pl.project.trylma.Models.boards;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Field;
import pl.project.trylma.Models.Owner;
import pl.project.trylma.Models.pawn.IPawn;

import java.util.List;

//Singleton
public final class MainBoard {
  private static volatile MainBoard instance;
  private Field fields[][];

  //TODO;
  public static MainBoard getInstance(){return null;}

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
