package pl.project.trylma.Models.pawn;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Field;

import java.util.List;

public class Pawn extends Field implements IPawn  {
  private List<Coord> avilableMoves;
}
