package pl.project.trylma.models.players;

import pl.project.trylma.models.Movement;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.PlayerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class RealPlayer extends AbstractPlayer {
  Socket socket;
  BufferedReader in;
  PrintWriter out;

  public RealPlayer(Socket socket, Owner id) {
    //TODO wywoluje konstruktor z klasy rodzica, ktory:
    //  -Ustawia board;
    //  -Ustawia id gracza;
    //  -Ustawia in i out;
    //  -Przekazuje Owner id do klienta
    super(id);
  }

  //TODO
  // -while (true)
  // -Wysyla do klienta informacje, ze czeka na ruch;
  // -Sprawdza czy poprawny, tzn czy znajduje sie na liscie availableMoves;     //isMoveCorrect();
  // -Jezeli poprawny
  //        break;
  // -Po pętli wykonuje ruch;
  public Movement makeMove() {
    return null;
  }

  public void sendMessage(String command) {
    //TODO wysyla do klienta informacje, tzn
    // out.println("MESSAGE");
    //out.printline(command);
  }

  @Override
  public void sendMove(Movement movement) {

  }

  @Override
  public void endGame(Owner winner) {

  }

  //TODO: pobrać playerOptions i sprawdzić czy >1 i <7
  public PlayerOptions getPlayerOptions() throws IOException {
    return null;
  }

  //TODO;
  void sendId(Owner id){

  }
}
