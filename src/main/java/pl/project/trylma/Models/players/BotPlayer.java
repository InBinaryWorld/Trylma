package pl.project.trylma.Models.players;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Move;
import pl.project.trylma.Models.Owner;
import pl.project.trylma.Models.players.AbstractPlayer;

import java.util.List;

public class BotPlayer extends AbstractPlayer {
  BotPlayer(Owner id) {
    super(id);
  }

  //TODO:
  //- bot gra;
  public Move makeMove() {
    return null;
  }

  public void sendMessage(String command) {}
}
