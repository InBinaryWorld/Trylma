package pl.project.trylma.models.players;

import pl.project.trylma.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class BotPlayer extends AbstractPlayer {
  private Coord destination;

  public BotPlayer(Owner id) {
    super(id);
    destination = board.getOppositeTop(id);
  }

  /**
   * Algorithm decide about bot's move.
   *
   * @return Movement object.
   */
  public Movement makeMove() {
    Logger.log(id + " Bot move!");
    List<Coord> pawnList = board.getOwnersPawns(id);
    List<Coord> availableMoves;
    List<Movement> tmp = new ArrayList<>();
    Coord from = null;
    double delta;
    double maxDelta = Double.NEGATIVE_INFINITY;
    double pawnDistance;
    double moveDistance;
    Random rand = new Random();
    Movement movement;

    if(board.getResult().contains(id))
      return null;

    try {
      sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    for (Coord pawn : pawnList) {
      pawnDistance = getDistance(pawn);
      availableMoves = board.getAvailableMoves(new Field(pawn, id));
      for (Coord move : availableMoves) {
        moveDistance = getDistance(move);
        delta = pawnDistance - moveDistance;
        if (from == null) {
          from = pawn;
          maxDelta = delta;
          tmp.add(new Movement(pawn, move, id));
        } else if (delta - maxDelta >= -0.15) {
          if (delta - maxDelta <= 0.15) {
            if (getDistance(pawn) - getDistance(from) > -0.02) {
              if (getDistance(pawn) - getDistance(from) < 0.02) {
                tmp.add(new Movement(pawn, move, id));
              } else {
                tmp.clear();
                from = pawn;
                maxDelta = delta;
                tmp.add(new Movement(pawn, move, id));
              }
            }
          } else {
            tmp.clear();
            from = pawn;
            maxDelta = delta;
            tmp.add(new Movement(pawn, move, id));
          }
        }
      }
    }
    if (tmp.size() != 0) {
      movement = tmp.get(rand.nextInt(tmp.size()));
      return movement;
    }
    return null;
  }

  /**
   * Counts distance between
   * 'cords' and destination coord.
   * @return distance.
   */
  private double getDistance(Coord coord) {
    double deltaX = destination.getX() - coord.getX();
    double deltaY = destination.getY() - coord.getY();
    double tmp = 0.25 * deltaX * deltaX + 0.75 * deltaY * deltaY;
    return Math.sqrt(tmp);
  }

  /**
   * Ignore
   */
  @Override
  public void sendMessage(String message) {
  }

  /**
   * Ignore
   */
  @Override
  public void sendMove(Movement movement) {
  }

  /**
   * Ignore
   */
  @Override
  public void endGame(Result result){

  }

  /**
   * Ignore
   */
  @Override
  public boolean isConnected() {
    return true;
  }

  /**
   * Ignore
   */
  @Override
  public void sendBoardTab() {
  }
}
