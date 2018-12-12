package pl.project.trylma.models.players;

import pl.project.trylma.models.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class RealPlayer extends AbstractPlayer {
  private Socket socket;
  private ObjectInputStream in;
  private ObjectOutputStream out;

  public RealPlayer(Socket socket, Owner id) throws DisconnectException {
    super(id);
    this.socket = socket;
    isConnected = true;
    try {
      out = new ObjectOutputStream(socket.getOutputStream());
      out.flush();
      in = new ObjectInputStream(socket.getInputStream());

    } catch (IOException e) {
      disconnectPlayer();
      throw new DisconnectException();
    }
    sendId();
  }

  public Movement makeMove() {
    Object object;
    Movement movement = null;
    if (isConnected) {
      sendMessage("Twój ruch");
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
        return null;
      }
      sendMessage("Czekaj na swój ruch...");
      return movement;
    } else {
      return null;
    }
  }

  public void sendMessage(String command) {
    if (isConnected) {
      try {
        out.writeObject("MESSAGE");
        out.writeObject(command);
      } catch (IOException e) {
        disconnectPlayer();
      }
    }
  }

  @Override
  public void sendMove(Movement movement) {
    System.out.println(id+"Real move!");
    if (isConnected) {
      try {
        out.writeObject("DO_MOVE");
        out.writeObject(movement);
      } catch (IOException e) {
        disconnectPlayer();
      }
    }
  }

  @Override
  public void endGame(Owner winner) {
    try {
      out.writeObject("END_GAME");
      if (winner.equals(Owner.NONE))
        out.writeObject(Result.Win);
      if (id.equals(winner))
        out.writeObject(Result.Win);
      out.writeObject(Result.Defeat);
    } catch (IOException ignored) {
    }
    disconnectPlayer();
  }

  @Override
  public void sendBoardTab() {

    try {
      out.writeObject("SET_BASEBOARD");
      out.writeObject(board.getFields());
    } catch (IOException ignored) {
      disconnectPlayer();
    }
  }

  private void disconnectPlayer() {
    isConnected = false;
    try {
      socket.close();
    } catch (IOException ignored) {
    }
    System.out.println("Player disconnected :" + id.getValue());
  }

  public PlayerOptions getPlayerOptions() throws DisconnectException {
    Object object = null;
    int numb;
    try {
      while (true) {
        out.writeObject("SET_SERVER_OPTIONS");
        object = in.readObject();
        if (object instanceof PlayerOptions) {
          numb = ((PlayerOptions) object).getNumOfPlayers();
          if (numb < 7 && numb > 1 && numb!=5)
            break;
        } else {
          throw new IOException();
        }
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      disconnectPlayer();

      throw new DisconnectException();
    }
    return (PlayerOptions)object;
  }

  private void sendId() throws DisconnectException {
    try {
      out.writeObject("SET_ID");
      out.writeObject(id);
    } catch (IOException e) {
      disconnectPlayer();
      throw new DisconnectException();
    }
  }
}
