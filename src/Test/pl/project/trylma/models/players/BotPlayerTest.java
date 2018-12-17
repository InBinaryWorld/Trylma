package pl.project.trylma.models.players;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.board.Board;
import pl.project.trylma.models.board.IBoard;

import java.util.ArrayList;
import java.util.Collections;

public class BotPlayerTest {
  BotPlayer botPlayer;
  IBoard board;

  @Before
  public void init(){
    board =  Board.getInstance();
    board.setPlayers(new ArrayList<Owner>(Collections.singleton(Owner.THIRD)));
    botPlayer = new BotPlayer(Owner.THIRD);
  }

  @Test
  public void makeMoveTest(){
    Assert.assertTrue(board.isMovementCorrect(botPlayer.makeMove()));
  }

}
