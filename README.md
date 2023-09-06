
# Calculator Java Swing Application

This is a simple calculator application developed in Java Swing. It provides basic arithmetic calculations and keeps a history of the calculations performed.<br/><br/>
![Calculator ScreenShot](https://github.com/DariushSeidy/calculator-java/assets/138073874/01f70891-d956-42db-acb4-384af8201fc0)

## Features

- Addition (+), subtraction (-), multiplication (x), and division (/) operations.
- Decimal point (.) support.
- Clear (C) button to clear the input field.
- Delete (Del) button to remove the last entered character.
- Negate (Neg) button to toggle the sign of the current input.
- History button to view a log of previous calculations.
- Calculation results are saved to both a text file and a MySQL database.<br/><br/>
![Database ScreenShot](https://github.com/DariushSeidy/calculator-java/assets/138073874/0184d9fc-6d20-4746-8f19-36d0cd907ea2)


## Usage

1. Compile and run the `Calculator.java` file to start the application.
2. Use the number buttons to input numbers.
3. Use the operator buttons (+, -, x, /) to select the operation.
4. Press the "=" button to calculate the result.
5. Use the Clear (C) button to clear the input field.
6. Use the Delete (Del) button to remove the last character.
7. Use the Negate (Neg) button to toggle the sign of the current input.
8. Click the History button to view the calculation history.

## Dependencies

- Java SE Development Kit (JDK)
- MySQL (for database storage)

## Installation

1. Clone this repository to your local machine:

   ```
   git clone https://github.com/DariushSeidy/calculator-java.git
   ```
2. Compile the Calculator.java file:
   ```
   javac Calculator.java
   ```
3. Run the application:
   ```
   java Calculator
   ```
## Database setup
1. Create a MySQL database named calculator_results.
2. Update the username and password variables in Calculator.java with your MySQL credentials.
   ```
   String url = "jdbc:mysql://localhost:3306/calculator_results";
   String username = "your-username";
   String password = "your-password";
   ```
## Contact me
   Feel free to reach out if you have any questions or need further assistance.
   <br/>
   seidydariush@gmail.com
