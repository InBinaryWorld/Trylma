package pl.project.trylma;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.players.BotPlayer;

public class TrylmaTest{
  Trylma trylma;

  @Before
  public void init(){
    trylma = new Trylma();
  }

  @Test
  public void addPlayerTest(){
    trylma.addPlayer(new BotPlayer(Owner.THIRD));
    Assert.assertEquals(1, trylma.getPlayers().size());
    Assert.assertEquals(trylma.getPlayers().get(0).getId(), Owner.THIRD);
  }
  @Test
  public void areAllConnectedTest(){
    trylma.addPlayer(new BotPlayer(Owner.THIRD));
    Assert.assertTrue(trylma.areAllConnected());
  }
}
