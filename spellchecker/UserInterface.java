package spellchecker;

import javax.swing.*;
import java.awt.GridLayout;
import java.lang.Thread;
import java.lang.NullPointerException;

public class UserInterface extends Thread {
  //singleton
  private static UserInterface instance;
  private JTextField field;
  private JLabel resultLabel;

  private UserInterface(){}

  public static synchronized UserInterface getInstance() {
    if (instance == null) {
      instance = new UserInterface();
    }
    return instance;
  }
  
  public JFrame createWindow() {
    JFrame window = new JFrame("Spell checker");
    JPanel content = new JPanel();
    JTextField textField = new JTextField("", JTextField.SOUTH);
    JLabel textLabel = new JLabel("Enter your text to check:");
    JLabel resultLabel = new JLabel("Pass");

    textField.setBounds(50, 100, 200, 400);

    content.add(textLabel);
    content.add(textField);
    content.add(resultLabel);
    content.setLayout(new GridLayout(0, 1));

    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    window.add(content);
    window.pack();
    window.setSize(1000, 1000);

    window.setVisible(true);

    this.field = textField;
    this.resultLabel = resultLabel;

    return window;
  }

  public void run() {
    this.createWindow();
  }

  public String getTextInput() {

    try {
      return this.field.getText();
    } catch (NullPointerException ex) {
      return "";
    }
  }

  public void updateResultLabel(boolean value) {
    if (value) {
      this.resultLabel.setText("Your spellling is correct!");
    } else {
      this.resultLabel.setText("Check your spelling.");
    }
  }

}
