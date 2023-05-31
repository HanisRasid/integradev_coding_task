package spellchecker.models;

public class Word extends CheckableText {

  // constructor
  public Word(String text) {
    this.text = text;
  }

  public boolean check(Database database) {
    return database.checkWord(this.text);
  }

  public int length() {
    return this.text.length();
  }
}
