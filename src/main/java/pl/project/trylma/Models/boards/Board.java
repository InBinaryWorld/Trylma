package pl.project.trylma.Models.boards;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Field;
import pl.project.trylma.Models.Move;

import java.util.List;

//Singleton
public final class Board {
  private static volatile Board instance;
  private Field fields[][];

  //TODO;
  public static Board getInstance(){
    return null;
  }

  //TODO;
  private boolean checkMove(Move move) {
    // TODO: - Wywołaj metode getAvailableMoves()
    //       - Sprawdź czy move.to jest na liście.
    return false;
  }

  public List<Coord> getAvailableMoves(Coord coord){
    //TODO: Pobiera możliwe ruchy dla danych Coord.
    return null;
  }

  //TODO;
  public boolean editBoard(Field from, Coord to) {
    //TODO: Wykonaj ruch.
    return false;
  }

  //TODO;
  public int hasWinner() {
    //TODO: Sprawdza czy któryś gracz wygrał
    //      -Jeżeli tak :
    //            -Zwraca Owner gracza
    //      -Jeżeli nie :
    //            -zwraca Owner 'NONE'
    return -1;
  }
}
