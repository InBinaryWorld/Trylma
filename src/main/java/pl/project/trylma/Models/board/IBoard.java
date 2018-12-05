package pl.project.trylma.Models.board;

import pl.project.trylma.Models.Coord;

import java.util.List;

public interface IBoard {
  void createBoard();
  List<Coord> getAvailableMoves(Coord coord, int lookFor);
}
