package pl.project.trylma;

import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.board.Board;
import pl.project.trylma.models.board.IBoard;
import pl.project.trylma.models.players.IPlayer;

import java.util.List;

class Trylma {
  private List<IPlayer> players;
  private IBoard board;
  private int curPlayer;
  private int numPlayers;

  Trylma(){
    board = Board.getInstance();
    curPlayer=0;
  }

  void startGame() {
    int winner;
    Movement movement;
    while (true) {
      movement = currentPlayerMove();
      if (movement!=null){
        sendMoveToPlayers(movement);
      }
      if((winner =hasWinner())!=-1){
        endGame(players.get(winner).getId());
        break;
      }
      curPlayer++;
      if(curPlayer>=numPlayers){
        curPlayer=0;
      }
    }
  }

  //null - jeśli pomija kolejkę, lub jeśli opuścił grę.
  private Movement currentPlayerMove(){
    return players.get(curPlayer).makeMove();
  }

  private void sendMoveToPlayers(Movement move) {
    for (IPlayer player : players) {
      player.sendMove(move);
    }
  }



  private int hasWinner(){
    //TODO: sprawdza czy jakis gracz z Player[] wygral.
    //Jżeli jest zwyciężca to sprawdza którym on jest na liście i go zwróć jego pozycje na niej;
    //NONE=Brak zwyciężcy
    //  - zwroc -1;
    return 0;
  }

  /**
   * gdy jest remis przekazuje NONE
   * gdy ktoś wygra przekazuje jego OwnerID
   * @param winner identyfikator zwyciężcy
   */
  private void endGame(Owner winner) {
    for (IPlayer player : players) {
      player.endGame(winner);
    }
  }

  void addPlayer(IPlayer player) {
    players.add(player);
  }

  void setNumPlayers(int numPlayers) {
    this.numPlayers = numPlayers;
  }

}
