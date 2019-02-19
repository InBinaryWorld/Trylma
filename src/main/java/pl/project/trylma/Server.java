package pl.project.trylma;

import pl.project.trylma.models.DisconnectException;
import pl.project.trylma.models.Logger;
import pl.project.trylma.models.Owner;
import pl.project.trylma.models.PlayerOptions;
import pl.project.trylma.models.board.Board;
import pl.project.trylma.models.players.BotPlayer;
import pl.project.trylma.models.players.RealPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Server {
  private static Owner currentOwner = Owner.FIRST;
  private static PlayerOptions playerOptions;



  public static void showDialog(TextArea textArea){
    JDialog jDialog = new JDialog();
    textArea.setEditable(false);
    jDialog.getContentPane().add(textArea);
    jDialog.setTitle("Server 1.0");
    jDialog.setSize(400,200);
    jDialog.addWindowListener(new WindowListener() {

      @Override
      public void windowOpened(WindowEvent e) {

      }

      @Override
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }

      @Override
      public void windowClosed(WindowEvent e) {

      }

      @Override
      public void windowIconified(WindowEvent e) {

      }

      @Override
      public void windowDeiconified(WindowEvent e) {

      }

      @Override
      public void windowActivated(WindowEvent e) {

      }

      @Override
      public void windowDeactivated(WindowEvent e) {

      }

    });
    jDialog.show();
  }

  public static void main(String[] args) {
    showDialog(Logger.getInstance());
    ServerSocket listener = null;
    try {
      listener = new ServerSocket(9001);
    } catch (IOException e) {
      log("Cannot create server on port 9001");
      System.exit(0);
    }
    loop(listener);
  }

  /**
   * Creates new game, and adds players.
   * At the end it starts game.
   * @param listener server socket.
   */
  private static void loop(ServerSocket listener) {
    RealPlayer player;
    Trylma trylma;
    log("Trylma Server is running");
    while (true) {
      log("Creating new game");
      trylma = new Trylma();
      try {
        player = new RealPlayer(listener.accept(), currentOwner);
        playerOptions = player.getPlayerOptions();
        trylma.addPlayer(player);
        log("Waiting for players...");
        for (int i = 1; i < playerOptions.getReal(); i++) {
          addRealPlayer(trylma,listener);
        }
        log("Adding bots...");
        for (int i = 0; i < playerOptions.getBot(); i++) {
          nextOwner();
          trylma.addPlayer(new BotPlayer(currentOwner));
        }
        trylma.setNumPlayers(playerOptions.getNumOfPlayers());
        log("Start Game");
        trylma.startGame();
        log("End Game");
      } catch (DisconnectException | IOException e) {
        log("Player Disconnect, Game Reset");
        trylma.endGame(null);
      }
      currentOwner = Owner.FIRST;
      Board.resetBoard();
    }
  }

  /**
   * Connect and adds RealPlayers to Trylma object.
   * @param trylma players are added to this Trylma object.
   * @param listener server socket.
   * @throws DisconnectException
   */
  private static void addRealPlayer(Trylma trylma, ServerSocket listener) throws DisconnectException {
    RealPlayer player;
    nextOwner();
    while (true) {
      if(!trylma.areAllConnected()){
        throw new DisconnectException();
      }
      try {
        player = new RealPlayer(listener.accept(), currentOwner);
        break;
      } catch (DisconnectException | IOException ignored) {
      }
    }
    player.sendMessage("Waiting for other players...");
    trylma.addPlayer(player);
  }

  /**
   * Set a next currentOwner value,
   * depending on number of players.
   */
  private static void nextOwner() {
    if (playerOptions.getNumOfPlayers() == 3) {
      switch (currentOwner) {
        case FIRST:
          currentOwner = Owner.FIFTH;
          break;
        case THIRD:
          currentOwner = Owner.FIFTH;
          break;
        case FIFTH:
          currentOwner = Owner.THIRD;
          break;
      }
    } else {
      switch (currentOwner) {
        case FIRST:
          currentOwner = Owner.SIXTH;
          break;
        case SECOND:
          currentOwner = Owner.FIFTH;
          break;
        case THIRD:
          currentOwner = Owner.FOURTH;
          break;
        case FOURTH:
          currentOwner = Owner.FIRST;
          break;
        case FIFTH:
          currentOwner = Owner.THIRD;
          break;
        case SIXTH:
          currentOwner = Owner.SECOND;
          break;
      }
    }
  }

  /**
   * Logs message, send them on standard output.
   * @param message String message.
   */
  private static void log(String message) {
    Logger.log(message);
  }

}
