package pl.project.trylma.models.board;

import pl.project.trylma.models.Coord;
import pl.project.trylma.models.Field;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;

import java.util.List;

public interface IBoard {
  /** returns available moves for pawn following game rules */
  List<Coord> getAvailableMoves(Field field);
  /** returns opposite top of star-shape map for specified player */
  Coord getOppositeTop(Owner owner);
  /** Makes move on board */
  void makeMove(Movement movement);
  static void resetBoard() {}
  /** check whether move is correct considering game rules */
  boolean isMovementCorrect(Movement movement);
  /** sets players on map */
  void setPlayers(List<Owner> list);
  /** returns two dimensional integer array representation of board */
  int[][] getFields();
  /** check if game has winner, returns value assigned to winner, 0 otherwise */
  int hasWinner();
  /** returns list of coords occupied by player. */
  List<Coord> getOwnersPawns(Owner owner);
}
