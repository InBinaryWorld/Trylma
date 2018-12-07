package pl.project.trylma;

import pl.project.trylma.Models.Movement;
import pl.project.trylma.Models.board.Board;
import pl.project.trylma.Models.players.IPlayer;

import java.util.List;

public class Trylma {
  private List<IPlayer> players;
  private Board board;
  private int curPlayer;
  private int numPlayers;

  Trylma(){
    //TODO getInstance();
  }

  void startGame() {
    while (true) {
      //TODO:
      // - CurPlayer wykonuje ruch i zwraca wykonany,              //currentPlayerMove();
      //    Jezeli curPlayer opuscil grę
      //    to zwraca NULL. Tak samo w przypadku
      //    gdy gracz chce ominac kolejkę.
      // - sprawdz czy zwrocony ruch jest NULL. Jesli nie to:
      //          - przechowaj ten ruch,
      //          - poinformuj graczy o wykonanym,                //sendMoveToPlayer();
      // - Jezeli ktos wygral                                     //hasWinner();
      //          - zakoncz gre                                   //end();
      // - przejdź do kolejnego gracza
    }
  }

  Movement currentPlayerMove(){
    //TODO: zwraca wynik ruchu playera. Czyli np:
    // return Player[curPlayer].makeMove();
    return null;
  }

  void sendMoveToPlayers(Movement move) {
    //TODO: kazdy gracz wysyla informacje do swojego klienta o wykonanym ruchu move;
  }

  private int hasWinner(){
    //TODO: sprawdza czy jakis gracz z Player[] wygral.
    //Jżeli jest zwyciężca to sprawdza którym on jest na liście i go zwróć jego pozycje na niej;
    //NONE=Brak zwyciężcy
    //  - zwroc -1;
    return 0;
  }

  void end(int winner) {
    //TODO: wysilij do klientow informacje o koncu gry;
  }

  void addPlayer(IPlayer player) {
    //TODO: do tablicy dodaj gracza;
  }

  public List<IPlayer> getPlayers() {
    return players;
  }

  public void setPlayers(List<IPlayer> players) {
    this.players = players;
  }

  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public int getCurPlayer() {
    return curPlayer;
  }

  public void setCurPlayer(int curPlayer) {
    this.curPlayer = curPlayer;
  }

  public int getNumPlayers() {
    return numPlayers;
  }

  void setNumPlayers(int numPlayers) {
    this.numPlayers = numPlayers;
  }
}
