package pl.project.trylma.models.players;

import pl.project.trylma.models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RealPlayer extends AbstractPlayer {
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;

  /**
   * Create input and output streams
   * and send player's id to client.
   * @param socket player's socket.
   * @param id players id.
   * @throws DisconnectException
   */
  public RealPlayer(Socket socket, Owner id) throws DisconnectException {
    super(id);
    this.socket = socket;
    try {
      out = new ObjectOutputStream(socket.getOutputStream());
      out.flush();
      in = new ObjectInputStream(socket.getInputStream());
    } catch (IOException e) {
      disconnectPlayer();
    }
    sendId();
  }

  /**
   * Taking move from client.
   * @return player's move.
   * @throws DisconnectException
   */
  @Override
  public Movement makeMove() throws DisconnectException {
    Object object;
    Movement movement = null;
    sendMessage("Your turn");
    try {
      while (true) {
        out.writeObject("YOUR_MOVE");
        object = in.readObject();
        if (object instanceof Movement) {
          movement = (Movement) object;
          if (board.isMovementCorrect(movement))
            break;
        }
        if (object == null) {
          return null;
        }
      }
    } catch (IOException | ClassNotFoundException e) {
      disconnectPlayer();
    }
    sendMessage("Wait for your turn...");
    return movement;
  }

  /**
   * Send message to client.
   * @param message message content.
   * @throws DisconnectException
   */
  @Override
  public void sendMessage(String message) throws DisconnectException {
    try {
      out.writeObject("MESSAGE");
      out.writeObject(message);
    } catch (IOException e) {
      disconnectPlayer();
    }

  }

  /**
   * send
   * @param movement
   * @throws DisconnectException
   */
  @Override
  public void sendMove(Movement movement) throws DisconnectException {
    System.out.println(id + " Real move!");
    try {
      out.writeObject("DO_MOVE");
      out.writeObject(movement);
    } catch (IOException e) {
      disconnectPlayer();
    }
  }

  @Override
  public void endGame(Owner winner) {
    try {
      out.writeObject("END_GAME");
      if (winner == null) {
        out.writeObject(Result.PlayerDisconnected);
      } else {
        if (winner.equals(Owner.NONE))
          out.writeObject(Result.Tie);
        if (id.equals(winner))
          out.writeObject(Result.Win);
        out.writeObject(Result.Defeat);
      }
    } catch (IOException ignored) {
    }
    try {
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean isConnected() {
    try {
      out.writeObject("TEST");
      out.writeObject("TEST");
    } catch (IOException e) {
      return false;
    }
    return true;
  }

  @Override
  public void sendBoardTab() throws DisconnectException {
    try {
      out.writeObject("SET_BASEBOARD");
      out.writeObject(board.getFields());
    } catch (IOException ignored) {
      disconnectPlayer();
    }
  }

  /**
   * Closing player's socket.
   * @throws DisconnectException
   */
  private void disconnectPlayer() throws DisconnectException {
    try {
      socket.close();
    } catch (IOException ignored) {
    }
    System.out.println("Player disconnected :" + id.getValue());
    throw new DisconnectException();
  }

  /**
   * Take game settings from client.
   * @return informations about players in game.
   * @throws DisconnectException
   */
  public PlayerOptions getPlayerOptions() throws DisconnectException {
    Object object;
    int numb;
    while (true) {
      try {
        out.writeObject("SET_SERVER_OPTIONS");
        object = in.readObject();
        if (object instanceof PlayerOptions) {
          numb = ((PlayerOptions) object).getNumOfPlayers();
          if (numb < 7 && numb > 1 && numb != 5)
            break;
        }
      } catch (ClassNotFoundException | IOException e) {
        disconnectPlayer();
      }
    }
    sendMessage("Waiting for other players...");
    return (PlayerOptions) object;
  }

  /**
   * Send player's ID to client.
   * @throws DisconnectException
   */
  private void sendId() throws DisconnectException {
    try {
      out.writeObject("SET_ID");
      out.writeObject(id);
    } catch (IOException e) {
      disconnectPlayer();
    }
  }
}
