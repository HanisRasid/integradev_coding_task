Please spend 3-4 hours reviewing the application and it's code.

# What does the application do?
This application is a simple spellchecker program that consists of several components such as the UI, database, business logic classes and a launcher. At launch, the app creates the GUI, initialises a database of words from the `dictionary.txt` file and constantly awaits for user input. Once the user inputs a word or phrase, it is then represented as either a `Text` or a `Word` object and checked against the database for spelling. If the word is found, it is spelt correctly and the app provides feedback in the GUI. If not found, the word is considered to be spelt wrong.
# Does it follow best coding practice? If not what would you change?
# How well does it display the Object Oriented design principles?
Although the code shows some adherence to the OOP design principles, there are definitely areas for improvement. Let us go through some examples of good OOP practices found within the code.

- Single Responsibility Principle
    - Although not perfect, all classes adhere to this principle. Each class carries out a single responsibility and promotes cohesiveness between the elements of each class. A good example of this is the `Database` class, which includes database-related operations only.
- Dependency Inversion Principle and Open-Closed Principle
    - The `CheckableText` abstract class and its subclasses `Word` and `Text` are examples of these principles. The subclasses depend on the abstract class and method, instead of concrete ones. Thus, through the use of an abstract class we open up the possibility of extension and closed to modification since `CheckableText` is well implemented.
- 
# Can you identify design patterns it is using? How well is it using them? Are there better suited patterns for this application?

To compile and run:

mv SpellChecker spellchecker
javac spellchecker/*.java spellchecker/models/*.java
java -classpath "spellchecker/sqlite/sqlite.jar";. spellchecker.Launcher
