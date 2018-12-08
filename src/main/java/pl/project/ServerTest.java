package pl.project;

import org.junit.Test;
import pl.project.trylma.Server;
import pl.project.trylma.models.Owner;

import static org.junit.Assert.*;

public class ServerTest {
  Server server =new Server();

  @Test
  public void getNextOwnerTest(){
    assertEquals(Server.getNextOwner(), Owner.SECOND);
  }


}