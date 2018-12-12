package pl.project.trylma;

import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.board.Board;
import pl.project.trylma.models.board.IBoard;
import pl.project.trylma.models.players.IPlayer;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

class Trylma {
  private List<IPlayer> players;
  private IBoard board;
  private int curPlayer;
  private int numPlayers;


  Trylma() {
    board = Board.getInstance();
    board.resetBoard();
    players = new ArrayList<>();
    curPlayer = 0;
  }

  void startGame() {
    int winner;
    Movement movement;
    setPlayersOnBoard();
    sendBaseBoardToPlayers();
    while (true) {

      if (numOfConnectedPlayers() < 1) {
        endGame(Owner.NONE);
        break;
      }
      movement = currentPlayerMove();
      if (movement != null) {
        board.makeMove(movement);
        sendMoveToPlayers(movement);
      }
//      if ((winner = hasWinner()) != -1) {
//        endGame(players.get(winner).getId());
//        break;
//      }
//      Zamienilem na:
            if ((winner = hasWinner()) != 0) {
        endGame(players.get(winner).getId());
        break;
      }
      curPlayer++;
      if (curPlayer >= numPlayers) {
        curPlayer = 0;
      }

    }
  }

  private void sendBaseBoardToPlayers() {


    for (IPlayer player : players) {
      player.sendBoardTab();
    }
  }

  private Movement currentPlayerMove() {
    return players.get(curPlayer).makeMove();
  }

  private void sendMoveToPlayers(Movement move) {
    for (IPlayer player : players) {
      player.sendMove(move);
    }
  }


  private int hasWinner() {
    return board.hasWinner();
  }

  /**
   * gdy jest remis przekazuje NONE
   * gdy ktoś wygra przekazuje jego OwnerID
   *
   * @param winner identyfikator zwyciężcy
   */
  private void endGame(Owner winner) {
    for (IPlayer player : players) {
      player.endGame(winner);
    }
  }

  private void setPlayersOnBoard(){
    List<Owner> list = new ArrayList<>();
    for (IPlayer player : players) {
      list.add(player.getId());
    }
    board.setPlayers(list);
  }

  void addPlayer(IPlayer player) {
    players.add(player);
  }

  void setNumPlayers(int numPlayers) {
    this.numPlayers = numPlayers;
  }

  private int numOfConnectedPlayers() {
    int count = 0;
    for (IPlayer player : players) {
      if (player.isConnected()) {
        count++;
      }
    }
    return count;
  }

}
