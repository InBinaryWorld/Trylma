package pl.project.trylma.models;


import java.awt.*;

public final class Logger extends TextArea {
  private static Logger logger= new Logger();
  private Logger() {
  }

  public static final Logger getInstance(){
    return logger;
  }
  public static final void log(String text){
    logger.setText(text+"\n"+logger.getText());
  }
}
