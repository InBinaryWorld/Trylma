package pl.project.trylma;

import pl.project.trylma.Models.Move;
import pl.project.trylma.Models.Owner;
import pl.project.trylma.Models.boards.MainBoard;
import pl.project.trylma.Models.players.IPlayer;

import java.util.List;

public class Trylma {
  private List<IPlayer> players;
  private MainBoard mainBoard;
  private int curPlayer;

  //TODO;
  public Trylma(){}

  //TODO: Jakaś nieskończona pętla z logiką gry przerywana zakończeniem rozgrywki.
  void startGame() {
  }

  //TODO;
  void currentPlayerMove(){
  }

  //TODO;
  synchronized void makeMoveOnBoard(Move move) {
  }

  //TODO;
  void sendMoveToPlayers(Move move) {
  }

  //TODO;
  void sendMessageToPlayers(String message) {
  }

  //TODO;
  void playerLeftGame() {
  }

  // TODO;
  void callEndGame(int winner) {
  }

  //TODO;
  void addPlayer(IPlayer player) {
  }

  //TODO;
  void removePlayer(Owner id) {
  }


}
