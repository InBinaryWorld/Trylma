package pl.project.trylma.models;

import java.io.Serializable;

public class PlayerOptions implements Serializable {
  static final long serialVersionUID = 2588980448693010399L;
  //        1 < bot+real < 7
  private int bot;
  private int real;

  public int getReal() {
    return real;
  }

  public void setReal(int real) {
    this.real = real;
  }

  public int getBot() {
    return bot;
  }

  public void setBot(int bot) {
    this.bot = bot;
  }

  public int getNumOfPlayers(){
    return bot+real;
  }
}
