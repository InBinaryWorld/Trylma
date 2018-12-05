package pl.project.trylma;

import pl.project.trylma.Models.Move;
import pl.project.trylma.Models.board.Board;
import pl.project.trylma.Models.players.IPlayer;

import java.util.List;

public class Trylma {
  private List<IPlayer> players;
  private Board board;
  private int curPlayer;
  private int numPlayers;

  public Trylma(){
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

  Move currentPlayerMove(){
    //TODO: zwraca wynik ruchu playera. Czyli np:
    // return Player[curPlayer].makeMove();
    return null;
  }

  void sendMoveToPlayers(Move move) {
    //TODO: kazdy gracz wysyla informacje do swojego klienta o wykonanym ruchu move;
  }

  private int hasWinner(){
    //TODO: sprawdza czy jakis gracz z Player[] wygral.
    //Jezeli ktos wygral to zwroc int danego gracza,
    //w przeciwnym wypadku zwroc -1;
    return 0;
  }

  void end(int winner) {
    //TODO: wysilij do klientow informacje o koncu gry;
  }

  void addPlayer(IPlayer player) {
    //TODO: do tablicy dodaj gracza;
  }
}
