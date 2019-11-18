package Logic;
import java.nio.charset.StandardCharsets; 
import java.nio.file.*;
import java.util.*; 

public class Game {
  private static List<String> words;
  static {
    try {
      words = Files.readAllLines(Paths.get("./words.txt"), StandardCharsets.UTF_8); 
    } catch (Exception e) {
      System.out.println("welp");
    }
  }

  private String word_ = "";
  private char[] guessed_;
  private int attempts_ = 0;
  private int score_ = 0;
  
  public Game() {
    this.NewWord();   
  }
  
  /* Command parser. */
  private static String[] ParseCMD(String request) {
    String[] full_str = request.replace("\r", "")
                               .replace("\n", "")
                               .split(" ");

    return full_str;
  }

  public int Process(String data) {
    String[] player_cmd = this.ParseCMD(new String(data));
    String cmd = player_cmd[0];
    int state = -100;

    if (cmd.equals("guess")) {
      /* Result of command. */

      /* Get sent argument. */
      String arg = player_cmd[1];

      int result = this.Guess(arg);
      System.out.println("Client guessing: " + arg);
      
      switch (result) {
        case -1: {
          /* Player lost, output full word and request new one. */
          state = -1;
          break;
        }
        case 0: {
          /* Player guessed incorrectly. */
          state = 0;
          break;
        }
        case 1: {
          /* Player guessed a letter correctly. Output unmasked letters. */
          state = 1; 
          break;
        }
        case 2: {
          /* Player won, output full word and request new one. */
          state = 2;
          break;
        }
      }
    } else if (cmd.equals("quit")) { 
      /* Client requesting a quit. */
      System.out.println("Socket closed!");
      state = -50;
    }
    return state;
  }

  public void NewWord() {
    this.word_ = this.words.get((int)(Math.random() * this.words.size()));
    this.guessed_ = new char[this.word_.length()];
    this.attempts_ = this.word_.length();
    Arrays.fill(this.guessed_, '_');
    System.out.println(this.word_);
  }
  
  public String RevealWord() {
    return this.word_;
  }

  public char[] GetWord() {
    return this.guessed_;
  }
  
  private int SetStats(int state) {
    int return_state = state;
    switch(state) {
      /* None guessed correctly. */
      case 0: {
        this.attempts_ -= 1;
        break;
      }
      /* Letter guessed correctly. */
      case 1: {
        break;
      }
      /* Entire word guessed correctly. */
      case 2: {
        this.score_ += 1;
        break; 
      }
    }

    /*
     * If attempts at 0, then tell player he sucks and start new word.
     */ 
    if (this.attempts_ <= 0) {
      this.score_ -= 1;
      return_state = -1;
    }
    return return_state;
  }

  public int GetAttempts() {
    return this.attempts_;
  }

  public int Guess(String arg) {
    int state = 0;
    if (arg.length() == 1) {
      if (this.word_.indexOf(arg) != -1) { 
        /* Find all occurances. */
        int i = this.word_.indexOf(arg);
        while(i != -1) {
          this.guessed_[i] = arg.charAt(0);
          i = this.word_.indexOf(arg, i+1);
        }
        state = 1;  
      }
    }
    
    System.out.println(new String(this.guessed_));
    if (this.word_.equals(arg) || new String(this.guessed_).equals(this.word_)) {
      this.guessed_ = this.word_.toCharArray();
      state = 2;
    }
    /* Set all of our stats based on state. */
    return this.SetStats(state);
  }
}
