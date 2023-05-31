package spellchecker.models;

import java.util.ArrayList;
import java.util.Arrays;

public class Text extends CheckableText {
  private ArrayList<String> words;

  // constructor
  public Text(String text) {
    this.text = text;
    // split the text into individual words to be tested
    this.words = new ArrayList<String>(Arrays.asList(this.text.split(" ")));
  }

  public boolean check(Database database) {
    
    Word word;

    for(String i: this.words) {
      word = new Word(i);
      if(!word.check(database)) {
        return false;
      }
    }

    return true;
  }

  public int length() {
    return this.text.length();
  }

}
