package pl.project.trylma.models.players;

import pl.project.trylma.models.Coord;
import pl.project.trylma.models.Field;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;

import java.util.List;

import static java.lang.Thread.sleep;

public class BotPlayer extends AbstractPlayer {
  private Coord destination;

  public BotPlayer(Owner id) {
    super(id);
    destination = board.getOppositeTop(id);
  }

  public Movement makeMove() {
   try {
      sleep(1500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println(id +" Bot move!");
    List<Coord> pawnList = board.getOwnersPawns(id);
    List<Coord> availableMoves;
    Coord from = null;
    Coord to = null;
    double delta;
    double maxDelta = Double.NEGATIVE_INFINITY;
    double pawnDistance;
    double moveDistance;

    for (Coord pawn : pawnList) {
      pawnDistance = getDistance(pawn);
      availableMoves = board.getAvailableMoves(new Field(pawn, id));
      for (Coord move : availableMoves) {
        moveDistance = getDistance(move);
        delta = pawnDistance - moveDistance;
        if (delta > maxDelta) {
          maxDelta = delta;
          from = pawn;
          to = move;
        }
      }
    }
    if (from != null)
      return new Movement(from, to, id);
    return null;
  }

  private double getDistance(Coord coord) {
    double deltaX = destination.getX() - coord.getX();
    double deltaY = destination.getY() - coord.getY();
    double tmp = 0.25 * deltaX * deltaX + 0.75 * deltaY * deltaY;
    return Math.sqrt(tmp);
  }

  public void sendMessage(String command) {
  }

  @Override
  public void sendMove(Movement movement) {
  }

  @Override
  public void endGame(Owner winner) {

  }

  @Override
  public void sendBoardTab() {

  }
}
