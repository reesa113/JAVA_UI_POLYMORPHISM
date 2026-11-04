package tutopro;

/*
AREESA NABEELA BINTI BAMBANG SETIAWAN
A223381
2DipIT
*/

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.io.PrintStream;           
import java.io.ByteArrayOutputStream; 

interface Price
{
    void getPrice(Customer c);
}

class Product implements Price
{
    private String type;
    private Customer buy;
    private double priceP; 

    Product(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public void setPrice(double priceP)
    {
        this.priceP = priceP;
    }

    public double getProductPrice()
    {
        return priceP;
    }

    public String toString()
    {
        return "The product is " + type + ".";
    }

    @Override
    public void getPrice(Customer c)
    {
        this.buy = c;
        System.out.println(toString());
        if (c.getSalary() > 4000)
        {
            System.out.println("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " can buy this product.");
            System.out.println("Your salary is higher than the price's product.");
        }
        else
        {
            System.out.println("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " cannot buy this product.");
            System.out.println("The product price is more than your salary.");
        }
    }
}

class SmartPhone extends Product
{
    private String nameSP;
    private double priceSP;

    SmartPhone(String type, String nameSP, double priceSP)
    {
        super(type);
        this.nameSP = nameSP;
        this.priceSP = priceSP;
        setPrice(priceSP);
    }

    public String toString()
    {
        return "The product is " + nameSP + ".";
    }
}

class Laptop extends Product
{
    private String nameL;
    private double priceL;

    Laptop(String type, String nameL, double priceL)
    {
        super(type);
        this.nameL = nameL;
        this.priceL = priceL;
        setPrice(priceL);
    }

    public String toString()
    {
        return "The product is " + nameL + ".";
    }
}

class Car implements Price
{
    private String brand;
    private double priceCar;
    private Customer buy;

    Car(String brand, double priceCar)
    {
        this.brand = brand;
        this.priceCar = priceCar;
    }

    public String getBrand()
    {
        return brand;
    }

    public String toString()
    {
        return "The car brand is " + brand + " with price RM" + priceCar + ".";
    }

    @Override
    public void getPrice(Customer c)
    {
        this.buy = c;
        System.out.println(toString());
        if (c.getSalary() > 8000)
        {
            System.out.println("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " can buy this car. Your salary is higher than the price's car.");
        }
        else
        {
            System.out.println("The customer's name is " + c.getName() + " with salary RM" + c.getSalary() + " cannot buy this car.");
        }
    }
}

class Customer
{
    private String name;
    private double salary;

    Customer(String name, double salary)
    {
        this.name = name;
        this.salary = salary;
    }

    public String getName()
    {
        return name;
    }

    public double getSalary()
    {
        return salary;
    }

    public String toString()
    {
        return name + " (Salary: RM" + salary + ")";
    }
}

public class PriceApp extends JFrame
{
    private JComboBox<Price> cmbItems;
    private JComboBox<Customer> cmbCustomers;
    private JTextArea txtOutput;
    private DecimalFormat df = new DecimalFormat("0.00");

    public PriceApp()
    {
        setTitle("Price App Interface - A223381");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Price sp = new SmartPhone("SmartPhone", "Iphone 14 ProMax", 5500.00);
        Price lp = new Laptop("Laptop", "Lenovo Yoga", 6000.00);
        Price c = new Car("BMW", 200000);

        Customer c1 = new Customer("Sally", 3000.00);
        Customer c2 = new Customer("Aaron", 8000.00);
        Customer c3 = new Customer("Simon", 10000.00);

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(new JLabel("Select Item / Product:"));
        cmbItems = new JComboBox<>(new Price[]{sp, lp, c});
        topPanel.add(cmbItems);

        topPanel.add(new JLabel("Select Customer:"));
        cmbCustomers = new JComboBox<>(new Customer[]{c1, c2, c3});
        topPanel.add(cmbCustomers);

        add(topPanel, BorderLayout.NORTH);

        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Evaluation Result Console"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnCheck = new JButton("Check Affordability");
        bottomPanel.add(btnCheck);
        add(bottomPanel, BorderLayout.SOUTH);

        btnCheck.addActionListener(e -> 
        {
            Price selectedItem = (Price) cmbItems.getSelectedItem();
            Customer selectedCustomer = (Customer) cmbCustomers.getSelectedItem();

            if (selectedItem != null && selectedCustomer != null)
            {
                txtOutput.setText("");
                java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
                PrintStream originalOut = System.out;
                System.setOut(new PrintStream(outContent));

                selectedItem.getPrice(selectedCustomer);

                System.setOut(originalOut);
                txtOutput.setText(outContent.toString());
            }
        });
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            new PriceApp().setVisible(true);
        });
    }
}
