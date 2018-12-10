package pl.project.trylma;

import pl.project.trylma.models.DisconnectException;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.PlayerOptions;
import pl.project.trylma.models.players.BotPlayer;
import pl.project.trylma.models.players.RealPlayer;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  private static Owner currentOwner = Owner.FIRST;
  private static PlayerOptions playerOptions;

  public static void main(String[] args) {
    /*
    //zwraca instancje klasy Board
    IBoard board = Board.getInstance();
    //zwraca wierzcholek w zaleznosci od ownera
    ((Board) board).getFinalCoordsFor(Owner.SECOND);
    //drukuje mape z indeksami jakbys potrzebowal
    ((Board) board).printAr();
    //tworze obiekt movement, zeby wykonac instrukcje makeMove na mapie
    Movement movement = new Movement(new Coord(12, 0),
                                     new Coord(11, 13),
                                     Owner.SECOND);
    //Wykonuje ruch
    ((Board) board).makeMove(movement);
    //Zwraca mozliwe ruchy dla danego pola Field
    List<Coord> av = board.getAvailableMoves(new Field(new Coord(11, 13), Owner.SECOND));
    */
    ServerSocket listener = null;
    try {
      listener = new ServerSocket(8901);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    currentOwner = Owner.FIRST;
    RealPlayer player;
    log("Trylma Server is Running");
    while (true) {
      log("Creating new game");
      Trylma trylma = new Trylma();
      try {
        player = new RealPlayer(listener.accept(), currentOwner);
        playerOptions = player.getPlayerOptions();
        player.sendMessage("Waiting for other players...");
        trylma.addPlayer(player);
        log("Waiting for players...");
        for (int i = 1; i < playerOptions.getReal(); i++) {
          nextOwner();
          while (true) {
            try {
              player = new RealPlayer(listener.accept(), currentOwner);
              break;
            } catch (DisconnectException | IOException ignored) {
            }
          }
          player.sendMessage("Waiting for other players...");
          trylma.addPlayer(player);
        }
        log("Adding bots...");
        for (int i = 0; i < playerOptions.getBot(); i++) {
          nextOwner();
          trylma.addPlayer(new BotPlayer(currentOwner));
        }
        trylma.setNumPlayers(playerOptions.getNumOfPlayers());
        log("Start Game");
        trylma.startGame();
        log("End Game");
      } catch (DisconnectException | IOException e) {
        log("First Player Disconected, Game is Reset");
      }
      reset();
    }

  }

  private static void reset() {
    currentOwner = Owner.FIRST;
  }

  private static void nextOwner() {
    if (playerOptions.getNumOfPlayers() == 3) {
      switch (currentOwner) {
        case FIRST:
          currentOwner = Owner.THIRD;
          break;
        case THIRD:
          currentOwner = Owner.FIFTH;
          break;
        case FIFTH:
          currentOwner = Owner.FIRST;
          break;
      }
    } else {
      switch (currentOwner) {
        case FIRST:
          currentOwner = Owner.FOURTH;
          break;
        case SECOND:
          currentOwner = Owner.FIFTH;
          break;
        case THIRD:
          currentOwner = Owner.SIXTH;
          break;
        case FOURTH:
          currentOwner = Owner.SECOND;
          break;
        case FIFTH:
          currentOwner = Owner.THIRD;
          break;
        case SIXTH:
          currentOwner = Owner.FIRST;
          break;
      }
    }
  }

  private static void log(String message) {
    System.out.println(message);
  }

}
