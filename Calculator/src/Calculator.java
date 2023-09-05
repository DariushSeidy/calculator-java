import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class Calculator implements ActionListener{
    private JFrame frame;
    private JTextField textField;
    private Font font;
    private Font smallFont;
    private JButton[] numberButtons = new JButton[10];
    private JButton[] functionalButtons = new JButton[9];
    private JButton addButton, subButton, mulButton, divButton, historyButton,
                    decimalButton, equalButton, deleteButton, clearButton, negativeButton;
    private JPanel panel;
    private double input1, input2;
    private char operator;
    private FileWriter writer;
    private ImageIcon icon = new ImageIcon("calculator-2017-10-10.png");
    
    Calculator(){
        // Opens the file
        try {
            writer = new FileWriter("data.txt", true); // Open the file in append mode
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Initiates the frame and sets up some properties
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(65, 63, 74));
        frame.setResizable(false);
        frame.setSize(420, 650);
        frame.setLayout(null);
        frame.setIconImage(icon.getImage());
        
        // Initiates the text field and sets up some properties
        textField = new JTextField();
        textField.setBounds(50, 25, 300, 50);
        font = new Font("Monospaced", Font.BOLD, 28);
        smallFont = new Font("Monospaced", Font.BOLD, 20);
        textField.setFont(font);
        textField.setForeground(Color.GREEN);
        textField.setBackground(Color.BLACK);
        textField.setEditable(false);
        // Creates buttons
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("/");
        decimalButton = new JButton(".");
        equalButton = new JButton("=");
        deleteButton = new JButton("Del");
        clearButton = new JButton("C");
        negativeButton = new JButton("Neg");
        historyButton = new JButton("History");
        historyButton.setFont(font);
        historyButton.setForeground(Color.WHITE);
        historyButton.setBackground(Color.BLACK);

        // Adds the buttons to an array to itirate over them
        functionalButtons[0] = addButton;
        functionalButtons[1] = subButton;
        functionalButtons[2] = mulButton;
        functionalButtons[3] = divButton;
        functionalButtons[4] = decimalButton;
        functionalButtons[5] = equalButton;
        functionalButtons[6] = deleteButton;
        functionalButtons[7] = clearButton;
        functionalButtons[8] = negativeButton;
        
        // Iterates and gives them some properties
        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(font);
            numberButtons[i].setForeground(Color.GREEN);
            numberButtons[i].setBackground(Color.BLACK);
            numberButtons[i].setFocusable(false);
        }

        // Iterates and gives them some properties
        for (int i = 0; i < functionalButtons.length; i++) {

            functionalButtons[i].addActionListener(this);
            functionalButtons[i].setFont(font);
            functionalButtons[i].setForeground(Color.RED);
            functionalButtons[i].setBackground(Color.BLACK);
            functionalButtons[i].setFocusable(false);
        }

        // Sets up some buttons
        deleteButton.setBounds(50, 430, 90, 50);
        deleteButton.setFont(smallFont);
        negativeButton.setBounds(155, 430, 90, 50);
        negativeButton.setFont(smallFont);
        clearButton.setBounds(260, 430, 90, 50);
        clearButton.setFont(smallFont);
        historyButton.setBounds(50,500, 300, 50);

        // Initiates the panel and gives it some properties
        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        panel.setBackground(new Color(65, 63, 74));

        // Adds the buttons to the panel
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(addButton);       
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(subButton);       
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(mulButton); 
        panel.add(decimalButton); 
        panel.add(numberButtons[0]);
        panel.add(equalButton); 
        panel.add(divButton); 

        // Adds properties to the frame
        frame.add(panel);
        frame.add(deleteButton);
        frame.add(clearButton);
        frame.add(negativeButton);
        frame.add(historyButton);
        frame.add(textField);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Close the file when the application is closed
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("data.txt");
                    if (file.exists() && Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(file);
                    } else {
                        System.out.println("File not found or unsupported operation.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        frame.setVisible(true); 
    }

    // Overrides the actionPerformed to give the buttons functionality
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source instanceof JButton) {
            JButton button = (JButton) source;
            String buttonText = button.getText();
    
            if (Character.isDigit(buttonText.charAt(0))) {
                // If the button pressed is a digit, add it to the text field.
                textField.setText(textField.getText() + buttonText);
            } else if (buttonText.equals("+") || buttonText.equals("-") || buttonText.equals("x") || buttonText.equals("/")) {
                // If the button pressed is an operator, set it as the current operator.
                operator = buttonText.charAt(0);
                input1 = Double.parseDouble(textField.getText());
                textField.setText("");
            } else if (buttonText.equals("=")) {
                // If the button pressed is "=", perform the calculation.
                input2 = Double.parseDouble(textField.getText());
                double result = performCalculation(input1, input2, operator);
                textField.setText(String.valueOf(result));
                saveToFile(result);
                saveToDatabase(result);
            } else if (buttonText.equals("C")) {
                // If the button pressed is "C", clear the text field.
                textField.setText("");
            } else if (buttonText.equals("Del")) {
                // If the button pressed is "Delete", remove the last character from the text field.
                String currentText = textField.getText();
                if (!currentText.isEmpty()) {
                    textField.setText(currentText.substring(0, currentText.length() - 1));
                }
            } else if (buttonText.equals(".")) {
                // If the button pressed is ".", add it to the text field if not already present.
                String currentText = textField.getText();
                if (!currentText.contains(".")) {
                    textField.setText(currentText + buttonText);
                }
            } else if (buttonText.equals("Neg")) {
                // Toggle the sign of the current input.
                String currentText = textField.getText();
                if (!currentText.isEmpty()) {
                    if (currentText.charAt(0) == '-') {
                        textField.setText(currentText.substring(1));
                    } else {
                        textField.setText("-" + currentText);
                    }
                }
            }
        }
    }
    
    // Calcutes
    private double performCalculation(double num1, double num2, char operator) {
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case 'x':
                return num1 * num2;
            case '/':
                if (num2 != 0) 
                    return num1 / num2;
                 else 
                return Double.NaN; // Handles division by zero case    
        default:
            return num2;
    }
}

    // Saves to file
    private void saveToFile(double result){
        Date thisDate = new Date();
        SimpleDateFormat dateForm = new SimpleDateFormat("Y/MM/dd HH:mm");
        try {
            if (writer != null) {
                writer.append("" + result + " : " + dateForm.format(thisDate) + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Saves to database
    private void saveToDatabase(double result) {
        String url = "jdbc:mysql://localhost:3306/calculator_results";
        String username = "root";
        String password = "Taj25352535";
    
        String insertSQL = "INSERT INTO calculator_results (result, time_stamp) VALUES (?, ?)";
    
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time_stamp = dateFormat.format(new Date());
    
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setDouble(1, result);
                preparedStatement.setString(2, time_stamp);
                preparedStatement.executeUpdate();
                System.out.println("Result saved to the database.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error executing SQL statement.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Database connection error.");
        }
    }
    }
    
