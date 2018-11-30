package pl.project.trylma.Models.players;

import pl.project.trylma.Models.Coord;
import pl.project.trylma.Models.Move;
import pl.project.trylma.Models.Owner;
import pl.project.trylma.Models.players.AbstractPlayer;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class RealPlayer extends AbstractPlayer {
  Socket socket;
  BufferedReader in;
  PrintWriter out;

  RealPlayer(Socket socket, Owner id) {
    //TODO wywoluje konstruktor z klasy rodzica, ktory:
    //  -Ustawia board;
    //  -Ustawia id gracza;
    //  -Ustawia in i out;
    super(id);
  }

  //TODO
  // -while (true)
  // -Wysyla do klienta informacje, ze czeka na ruch;
  // -Sprawdza czy poprawny, tzn czy znajduje sie na liscie availableMoves;     //isMoveCorrect();
  // -Jezeli poprawny
  //        break;
  public Move makeMove() {
    return null;
  }

  public void sendMessage(String command) {
    //TODO wysyla do klienta informacje, tzn
    //out.printline(command);
  }
}
