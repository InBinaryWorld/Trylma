package pl.project.trylma;

import pl.project.trylma.Models.Owner;
import pl.project.trylma.Models.PlayerOptions;
import pl.project.trylma.Models.players.BotPlayer;
import pl.project.trylma.Models.players.RealPlayer;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
  private static Owner currentOwner;

  public static void main(String[] args) throws IOException {
    /*
    IBoard board = Board.getInstance();
    ((Board) board).printAr();
    Coord coord = ((Board) board).getOppositeTop(new Coord(12, 0));
    ((Board) board).setField(coord.getX(), coord.getY(), 1);
    ((Board) board).printAr();
    */

    ServerSocket listener = new ServerSocket(8901);
    PlayerOptions playerOptions;
    currentOwner=Owner.FIRST;
    RealPlayer player;

    log("Trylma Server is Running");
    try {
      while (true) {
        Trylma trylma = new Trylma();
        player = new RealPlayer(listener.accept(), Owner.FIRST);
         playerOptions = player.getPlayerOptions();
        player.sendMessage("Waiting for other players...");
        trylma.addPlayer(player);
        log("Waiting for players...");
        for (int i=1;i<playerOptions.getReal();i++) {
          player = new RealPlayer(listener.accept(),getNextOwner());
          player.sendMessage("Waiting for other players...");
          trylma.addPlayer(player);
        }
        log("Adding bots...");
        for (int i =0 ; i<playerOptions.getBot();i++)
          trylma.addPlayer(new BotPlayer(getNextOwner()));
        trylma.setNumPlayers(playerOptions.getNumOfPlayers());
        log("Start Game");
        trylma.startGame();
        log("End Game");
        reset();
      }
    } finally {
      listener.close();
    }
  }

  //TODO: Reset Board
  private static void reset() {
    currentOwner= Owner.FIRST;
  }

  private static Owner getNextOwner(){
    switch (currentOwner) {
      case FIRST:
        currentOwner=Owner.SECOND;
        break;
      case SECOND:
        currentOwner=Owner.THIRD;
        break;
      case THIRD:
        currentOwner=Owner.FOURTH;
        break;
      case FOURTH:
        currentOwner= Owner.FIVETH;
        break;
      case FIVETH:
        currentOwner= Owner.SIXTH;
        break;
      case SIXTH:
        currentOwner = Owner.FIRST;
        break;
    }
    return currentOwner;
  }

  private static void log(String message){
    System.out.println(message);
  }
}
