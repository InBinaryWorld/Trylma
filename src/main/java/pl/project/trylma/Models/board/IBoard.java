package pl.project.trylma.Models.board;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Field;

import java.util.List;

public interface IBoard {
  void createBoard();
  List<Coord> getAvailableMoves(Field field);
}
