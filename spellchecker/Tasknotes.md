Please spend 3-4 hours reviewing the application and it's code.

# What does the application do?
This application is a simple spellchecker program that consists of several components such as the UI, database, business logic classes and a launcher. At launch, the app creates the GUI, initialises a database of words from the `dictionary.txt` file and constantly awaits for user input. Once the user inputs a word or phrase, it is then represented as either a `Text` or a `Word` object and checked against the database for spelling. If the word is found, it is spelt correctly and the app provides feedback in the GUI. If not found, the word is considered to be spelt wrong.
# Does it follow best coding practice? If not what would you change?
The code has some issues and thus does not follow the best coding practices. Some code smells and issues that I found and should be changed are as follows:
- Comments
    - There are a lot of unnecessary comments that don't help with explaining the code. It is best practice to minise the nubmer of comments and instead use good naming conventions to explain code.
- Print statements for debugging
    - Not needed for the code to run. Deployable code should not have print debugging.
- Dead code
    - Unused fields and imports just add clutter and are not needed.
- Consolidate conditional expressions
    - There are some overly convoluted conditional expressions that could be simplified. In doing so, it improves the readability of the code.
# How well does it display the Object Oriented design principles?
Although the code shows some adherence to the OOP design principles, there are definitely areas for improvement. Let us go through some examples of good OOP practices found within the code.

- Single Responsibility Principle
    - Although not perfect, all classes adhere to this principle. Each class carries out a single responsibility and promotes cohesiveness between the elements of each class. A good example of this is the `Database` class, which includes database-related operations only.
- Dependency Inversion Principle and Open-Closed Principle
    - The `CheckableText` abstract class and its subclasses `Word` and `Text` are examples of these principles. The subclasses depend on the abstract class and method, instead of concrete ones. Thus, through the use of an abstract class we open up the possibility of extension and closed to modification since `CheckableText` is well implemented.
# Can you identify design patterns it is using? How well is it using them? Are there better suited patterns for this application?
The code follows a Model-View-Controller design pattern. Although the naming conventions don't make it explicitly clear, the separation of responsibilites follow that of the MVC design pattern. The `UserInterface` class acts as the view, `Database` as the model and the `CheckableText` can be seen as part of the controller since it is used for spell checking.

Patterns that could be used are as follows:
- Singleton
    - A singleton pattern can be implemented in the `Database` class since there can only be one instance of the database connection in the application. Same goes for the `UserInterface` class.
## To compile and run:
1. mv SpellChecker spellchecker
2. javac spellchecker/*.java spellchecker/models/*.java
3. java -classpath "spellchecker/sqlite/sqlite.jar";. spellchecker.Launcher
