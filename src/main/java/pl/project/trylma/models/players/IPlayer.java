package pl.project.trylma.models.players;

import pl.project.trylma.models.DisconnectException;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.Result;

public interface IPlayer {
  /**
   * Player do move.
   * @return player's move.
   * @throws DisconnectException
   */
  Movement makeMove() throws DisconnectException;

  /**
   * @return player's id.
   */
  Owner getId();

  /**
   * Send message to player.
   * @param message message content.
   * @throws DisconnectException
   */
  void sendMessage(String message) throws DisconnectException;

  /**
   * Sends move which one of players done.
   * @param movement
   * @throws DisconnectException
   */
  void sendMove(Movement movement) throws DisconnectException;

  /**
   * Inform player about the end of the game.
   * @param result
   */
  void endGame(Result result);

  /**
   * Check if player is connect to the server.
   * @return true if is connected;
   */
  boolean isConnected();

  /**
   * Send board to player.
   * @throws DisconnectException
   */
  void sendBoardTab() throws DisconnectException;
}
