package pl.project.trylma.models.board;

import pl.project.trylma.models.Coord;
import pl.project.trylma.models.Field;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;

import java.util.List;

public interface IBoard {
  void createBoard();
  List<Coord> getAvailableMoves(Field field);
  List<Coord> getFinalCoordsFor(Owner owner);
  Coord getOppositeTop(Owner owner);
  void makeMove(Movement movement);
  //new ones:
  static void resetBoard(){}
  boolean isMovementCorrect(Movement movement);
  void setPlayers(List<Owner> list);
  int[][] getFields();
  int hasWinner();
  //Dodana teraz:
  List<Coord> getOwnersPawns(Owner owner);
}
