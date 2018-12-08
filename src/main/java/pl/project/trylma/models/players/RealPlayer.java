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

  public RealPlayer(Socket socket, Owner id) throws DisconnectException {
    super(id);
    isConnected = true;
    sendId();
    try {
      in = new ObjectInputStream(socket.getInputStream());
      out = new ObjectOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      disconnectPlayer();
      throw new DisconnectException();
    }
    this.socket = socket;
  }

  //TODO
  // -Jeżeli isConnected==false to przyjmi ze pominął ruch
  // -while (true)
  // -Wysyla do klienta informacje, ze czeka na ruch;
  // -Sprawdza czy poprawny, tzn czy znajduje sie na liscie availableMoves;     //isMoveCorrect();
  // -Jezeli poprawny
  //        break;
  public Movement makeMove() {
    Object object;
    Movement movement = null;
    if (isConnected) {
      try {
        while (true) {
          out.writeObject("YOUR_MOVE");
          object = in.readObject();
          if (object instanceof Movement) {
            movement = (Movement) object;
            if (board.isMovementCorrect(movement))
              break;
          }else if(object == null)
            return null;
        }
      } catch (IOException | ClassNotFoundException e) {
        disconnectPlayer();
        return null;
      }
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

  private void disconnectPlayer() {
    isConnected = false;
    try {
      socket.close();
    } catch (IOException e) {
      System.out.println("Player disconnected :" + id.getValue());
    }
  }

  //Rzuca IOException gdy jest problem z komunikacją z klientem
  public PlayerOptions getPlayerOptions() throws DisconnectException {
    PlayerOptions plOp = null;
    try {
      while (true) {
        out.writeObject("SET_SERVER_OPTIONS");
        Object object = in.readObject();
        if (object instanceof PlayerOptions) {
          plOp = (PlayerOptions) object;
          if (plOp.getNumOfPlayers() < 7 && plOp.getNumOfPlayers() > 1)
            break;
        } else {
          throw new IOException();
        }
      }
    } catch (IOException | ClassNotFoundException e) {
      disconnectPlayer();
      throw new DisconnectException();
    }
    return plOp;
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
