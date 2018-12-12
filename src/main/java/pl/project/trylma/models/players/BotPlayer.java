package pl.project.trylma.models.players;

import pl.project.trylma.models.Coord;
import pl.project.trylma.models.Field;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;

import java.util.List;

public class BotPlayer extends AbstractPlayer {
  private Coord destination;

  public BotPlayer(Owner id) {
    super(id);
    destination = board.getOppositeTop(id);
  }

  public Movement makeMove() {
    System.out.println("Bot move!");
    List<Coord> pawnList = board.getFinalCoordsFor(id);
    List<Coord> availableMoves;
    Coord from = null;
    Coord to = null;
    double delta;
    double maxDelta = Double.MIN_VALUE;
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
    //System.out.println("Bot move!"+from.getX()+","+from.getY()+" to "+to.getX()+","+to.getY()+ "OWner "+getId());
    if (from != null)
      return new Movement(from, to, id);
    System.out.println("Bot move =null");
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
