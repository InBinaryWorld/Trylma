package pl.project.trylma;

import pl.project.trylma.models.DisconnectException;
import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.Result;
import pl.project.trylma.models.board.Board;
import pl.project.trylma.models.board.IBoard;
import pl.project.trylma.models.players.IPlayer;

import java.util.ArrayList;
import java.util.List;

public class Trylma {
  protected List<IPlayer> players;
  private IBoard board;
  private int curPlayer;
  private int numPlayers;
  private Result place;


  public Trylma() {
    place = Result.FIRST;
    board = Board.getInstance();
    players = new ArrayList<>();
    curPlayer = 0;
  }

  /**
   * There is loop which controls game.
   *
   * @throws DisconnectException
   */
  void startGame() throws DisconnectException {
    ArrayList<Owner> result;
    Movement movement;
    setPlayersOnBoard();
    sendBaseBoardToPlayers();
    while (true) {
      if (!areAllConnected()) {
        endGame(null);
        break;
      }
      movement = currentPlayerMove();
      if (movement != null) {
        board.makeMove(movement);
        sendMoveToPlayers(movement);
      }
      if ((result = hasWinner()).size() == numPlayers - 1) {
        endGame(result);
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
   *
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
   *
   * @throws DisconnectException
   */
  private Movement currentPlayerMove() throws DisconnectException {
    return players.get(curPlayer).makeMove();
  }

  /**
   * Sends Movement object to all player.
   *
   * @throws DisconnectException
   */
  private void sendMoveToPlayers(Movement move) throws DisconnectException {
    for (IPlayer player : players) {
      player.sendMove(move);
    }
  }

  /**
   * Return list of players (id)
   * who finished the game.
   */
  private ArrayList<Owner> hasWinner() {
    return board.hasWinner();
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
   */
  public void addPlayer(IPlayer player) {
    players.add(player);
  }

  /**
   * Set number of players.
   */
  public void setNumPlayers(int numPlayers) {
    this.numPlayers = numPlayers;
  }

  /**
   * Checks if all players are connected.
   */
  public boolean areAllConnected() {
    for (IPlayer player : players) {
      if (!player.isConnected())
        return false;
    }
    return true;
  }


  /**
   * Sends to players result
   * of this game.
   */
  public void endGame(ArrayList<Owner> result) {
    if (result == null) {
      for (IPlayer player : players) {
        player.endGame(Result.PlayerDisconnected);
      }
    } else {
      for (IPlayer player : players) {
        if (!result.contains(player.getId()))
          result.add(player.getId());
      }
      for (Owner owner : result) {
        for (IPlayer player : players) {
          if (owner.equals(player.getId())) {
            player.endGame(place);
            nextPlace();
          }
        }
      }
    }
  }

  /**
   * Iterate after Result values.
   */
  private void nextPlace() {
    switch (place) {
      case FIRST:
        place = Result.SECOND;
        break;
      case SECOND:
        place = Result.THIRD;
        break;
      case THIRD:
        place = Result.FOURTH;
        break;
      case FOURTH:
        place = Result.FIFTH;
        break;
      case FIFTH:
        place = Result.SIXTH;
        break;
      case SIXTH:
        place = Result.FIRST;
        break;
    }
  }

  /**
   * returns list of players in game
   */
  public List<IPlayer> getPlayers() {
    return players;
  }
}
