package pl.project.trylma;

import pl.project.trylma.models.DisconnectException;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.board.Board;
import pl.project.trylma.models.board.IBoard;
import pl.project.trylma.models.players.IPlayer;

import java.util.ArrayList;
import java.util.List;

class Trylma {
  private List<IPlayer> players;
  private IBoard board;
  private int curPlayer;
  private int numPlayers;


  Trylma() {
    board = Board.getInstance();
    players = new ArrayList<>();
    curPlayer = 0;
  }

  /**
   * There is loop which controls game.
   * @throws DisconnectException
   */
  void startGame() throws DisconnectException {
    int winner;
    Movement movement;
    setPlayersOnBoard();
    sendBaseBoardToPlayers();
    while (true) {
      if (!areAllConnected()) {
        endGame(Owner.NONE);
        break;
      }
      movement = currentPlayerMove();
      if (movement != null) {
        board.makeMove(movement);
        sendMoveToPlayers(movement);
      }
      if ((winner = hasWinner()) != 0) {
        for (IPlayer player : players) {
          if (player.getId().getValue() == winner)
            endGame(player.getId());
        }
        break;
      }
      curPlayer++;
      if (curPlayer >= numPlayers) {
        curPlayer = 0;
      }
    }
  }

  /**
   * Send board to all players.
   * @throws DisconnectException
   */
  private void sendBaseBoardToPlayers() throws DisconnectException {
    for (IPlayer player : players) {
      player.sendBoardTab();
    }
  }

  /**
   * Player which id on 'curPlayer' position
   * on list 'players' makes move.
   * @return
   * @throws DisconnectException
   */
  private Movement currentPlayerMove() throws DisconnectException {
    return players.get(curPlayer).makeMove();
  }

  /**
   * Sends Movement object to all player.
   * @param move
   * @throws DisconnectException
   */
  private void sendMoveToPlayers(Movement move) throws DisconnectException {
    for (IPlayer player : players) {
      player.sendMove(move);
    }
  }

  /**
   * Check if there is any winner.
   * @return
   */
  private int hasWinner() {
    return board.hasWinner();
  }

  /**
   * Sends to players result
   * of this game.
   */
  void endGame(Owner winner) {
    for (IPlayer player : players) {
      player.endGame(winner);
    }
  }

  /**
   * Set players on board.
   */
  private void setPlayersOnBoard() {
    List<Owner> list = new ArrayList<>();
    for (IPlayer player : players) {
      list.add(player.getId());
    }
    board.setPlayers(list);
  }

  /**
   * Add player.
   * @param player
   */
  void addPlayer(IPlayer player) {
    players.add(player);
  }

  /**
   * Set number of players.
   * @param numPlayers
   */
  void setNumPlayers(int numPlayers) {
    this.numPlayers = numPlayers;
  }

  /**
   * Checks if all players are connected.
   * @return
   */
  boolean areAllConnected() {
    for (IPlayer player : players) {
      if (!player.isConnected())
        return false;
    }
    return true;
  }
}
