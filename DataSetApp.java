/*
AREESA NABEELA BINTI BAMBANG SETIAWAN
A223381
2DipIT
*/

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

interface Measurable 
{
    double getMeasure();
    String getName();
}

class BankAccount implements Measurable 
{
    private int accNumber;
    private double balance;
    private static int LastAssignedNumber = 0;
    
    BankAccount() 
    {
        LastAssignedNumber++;
        this.accNumber = LastAssignedNumber;
        this.balance = 0.0;
    }
    
    public BankAccount(double balance) 
    {
        LastAssignedNumber++;
        this.accNumber = LastAssignedNumber;
        this.balance = balance;
    }
    
    public int getAccountNumber() 
    {
        return accNumber;
    }

    @Override
    public double getMeasure() 
    {
        return balance;
    }

    @Override
    public String getName() 
    {
        return "Account " + accNumber;
    }
}

class Coin implements Measurable 
{
    private double value;
    private String name;
    
    Coin(double value, String name) 
    {
        this.value = value;
        this.name = name;
    }
    
    public double getValue() 
    {
        return value;
    }

    @Override
    public double getMeasure() 
    {
        return value;
    }

    @Override
    public String getName() 
    {
        return name;
    }
}

class Student implements Measurable 
{
    private String name;
    private double height;
    
    Student(String name, double height) 
    {
        this.name = name;
        this.height = height;
    }
    
    public double getHeight() 
    {
        return height;
    }

    @Override
    public double getMeasure() 
    {
        return height;
    }

    @Override
    public String getName() 
    {
        return name;
    }
}

class DataSet 
{
    private double sum;
    private Measurable max;
    private int count;
    
    DataSet() 
    {
        sum = 0;
        count = 0;
        max = null;
    }
    
    public void add(Measurable x) 
    {
        sum = sum + x.getMeasure();
        if (count == 0 || x.getMeasure() > max.getMeasure()) 
        {
            max = x;
        }
        count++;
    }
    
    public Measurable getMax() 
    {
        return max;
    }
    
    public double getAverage() 
    {
        if (count == 0) return 0;
        return sum / count;
    }
}

public class DataSetApp extends JFrame 
{
    private DataSet bankData, coinData, studentData;
    private ArrayList<String> bankItems, coinItems, studentItems;
    
    private JComboBox<String> cmbCategory;
    private DefaultListModel<String> listModel;
    private JLabel lblAverage, lblHighest;
    private JTextField txtField1, txtField2;
    private JLabel lblPrompt1, lblPrompt2;
    private JButton btnAdd;
    
    private DecimalFormat df = new DecimalFormat("0.00");

    public DataSetApp() 
    {
        setTitle("DataSet App - A223381");
        setSize(550, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        bankData = new DataSet();
        bankItems = new ArrayList<>();
        addBank(0);
        addBank(10000);
        addBank(2000);

        coinData = new DataSet();
        coinItems = new ArrayList<>();
        addCoin("Quarter", 0.25);
        addCoin("Dime", 0.1);
        addCoin("Nickel", 0.05);

        studentData = new DataSet();
        studentItems = new ArrayList<>();
        addStudent("Ali", 157.50);
        addStudent("Alex", 165.00);
        addStudent("Maria", 147.00);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Select Dataset Category: "));
        cmbCategory = new JComboBox<>(new String[]{"Bank Accounts", "Coins", "Students"});
        topPanel.add(cmbCategory);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        listModel = new DefaultListModel<>();
        JList<String> listView = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listView);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Items in Dataset"));
        centerPanel.add(scrollPane);

        JPanel statsPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Results"));
        lblAverage = new JLabel();
        lblHighest = new JLabel();
        statsPanel.add(lblAverage);
        statsPanel.add(lblHighest);
        centerPanel.add(statsPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Add New Item"));

        lblPrompt1 = new JLabel();
        txtField1 = new JTextField(8);
        lblPrompt2 = new JLabel();
        txtField2 = new JTextField(8);
        btnAdd = new JButton("Add");

        bottomPanel.add(lblPrompt1);
        bottomPanel.add(txtField1);
        bottomPanel.add(lblPrompt2);
        bottomPanel.add(txtField2);
        bottomPanel.add(btnAdd);

        add(bottomPanel, BorderLayout.SOUTH);

        cmbCategory.addActionListener(e -> updateView());

        btnAdd.addActionListener(e -> 
        {
            try 
            {
                String selected = (String) cmbCategory.getSelectedItem();
                
                if (selected.equals("Bank Accounts")) 
                {
                    double balance = Double.parseDouble(txtField1.getText());
                    addBank(balance);
                    txtField1.setText("");
                } 
                
                else if (selected.equals("Coins")) 
                {
                    String name = txtField1.getText();
                    double val = Double.parseDouble(txtField2.getText());
                    addCoin(name, val);
                    txtField1.setText("");
                    txtField2.setText("");
                } 
                
                else if (selected.equals("Students")) 
                {
                    String name = txtField1.getText();
                    double height = Double.parseDouble(txtField2.getText());
                    addStudent(name, height);
                    txtField1.setText("");
                    txtField2.setText("");
                }
                updateView();
            } 
            
            catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateView();
    }

    private void addBank(double balance) 
    {
        BankAccount acc = new BankAccount(balance);
        bankData.add(acc);
        bankItems.add(acc.getName() + " -> Balance: $" + df.format(acc.getMeasure()));
    }

    private void addCoin(String name, double val) 
    {
        Coin c = new Coin(val, name);
        coinData.add(c);
        coinItems.add(c.getName() + " -> Value: $" + df.format(c.getMeasure()));
    }

    private void addStudent(String name, double height) 
    {
        Student s = new Student(name, height);
        studentData.add(s);
        studentItems.add(s.getName() + " -> Height: " + df.format(s.getMeasure()) + " cm");
    }

    private void updateView() 
    {
        String selected = (String) cmbCategory.getSelectedItem();
        listModel.clear();

        DataSet currentDataSet = null;
        ArrayList<String> currentItems = null;

        if (selected.equals("Bank Accounts")) 
        {
            currentDataSet = bankData;
            currentItems = bankItems;
            lblPrompt1.setText("Initial Balance ($):");
            lblPrompt1.setVisible(true);
            txtField1.setVisible(true);
            lblPrompt2.setVisible(false);
            txtField2.setVisible(false);
        }
        
        else if (selected.equals("Coins")) 
        {
            currentDataSet = coinData;
            currentItems = coinItems;
            lblPrompt1.setText("Name:");
            lblPrompt1.setVisible(true);
            txtField1.setVisible(true);
            lblPrompt2.setText("Value ($):");
            lblPrompt2.setVisible(true);
            txtField2.setVisible(true);
        } 
        
        else if (selected.equals("Students")) 
        {
            currentDataSet = studentData;
            currentItems = studentItems;
            lblPrompt1.setText("Name:");
            lblPrompt1.setVisible(true);
            txtField1.setVisible(true);
            lblPrompt2.setText("Height (cm):");
            lblPrompt2.setVisible(true);
            txtField2.setVisible(true);
        }

        for (String item : currentItems) 
        {
            listModel.addElement(item);
        }

        lblAverage.setText("<html><b>Average:</b> " + df.format(currentDataSet.getAverage()) + "</html>");
        Measurable max = currentDataSet.getMax();
        
        if (max != null) 
        {
            lblHighest.setText("<html><b>Highest:</b> " + max.getName() + " (" + df.format(max.getMeasure()) + ")</html>");
        } 
        
        else 
        {
            lblHighest.setText("<html><b>Highest:</b> None</html>");
        }
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            new DataSetApp().setVisible(true);
        });
    }
}
