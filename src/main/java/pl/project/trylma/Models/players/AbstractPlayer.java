package pl.project.trylma.Models.players;

import pl.project.trylma.Models.Owner;
import pl.project.trylma.Models.pawn.IPawn;

import java.util.List;

public abstract class AbstractPlayer implements IPlayer {
  Owner id;
  IPawn curPawn;
  List<IPawn> playerPawns;

}
