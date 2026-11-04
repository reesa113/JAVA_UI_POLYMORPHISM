/*
AREESA NABEELA BINTI BAMBANG SETIAWAN
A223381
2DipIT
*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface Edible 
{
    String howToEat();
}

class Fruit implements Edible 
{
    protected String name;
    
    Fruit(String name) 
    {
        this.name = name;
    }

    @Override
    public String howToEat() 
    {
        return "";
    }
}

class Animal implements Edible 
{
    private String name;
    
    Animal(String name) 
    {
        this.name = name;
    }
    
    @Override
    public String howToEat() 
    {
        return name + " needs to be cooked first before eating.";
    }

    @Override
    public String toString() 
    {
        return name + " (Animal)";
    }
}

class Orange extends Fruit 
{
    Orange(String name) 
    {
        super(name);
    }
    
    @Override
    public String howToEat() 
    {
        return name + " can make orange butter cake.";
    }

    @Override
    public String toString() 
    {
        return name + " (Orange)";
    }
}

class Apple extends Fruit 
{
    Apple(String name) 
    {
        super(name);
    }
    
    @Override
    public String howToEat() 
    {
        return name + " can make apple pie.";
    }

    @Override
    public String toString() 
    {
        return name + " (Apple)";
    }
}

class Person 
{
    private String name;

    public Person(String name) 
    {
        this.name = name;
    }
    
    public String eating(Edible eat) 
    {
        return name + " is eating " + eat.howToEat();
    }

    @Override
    public String toString() 
    {
        return name;
    }
}

public class EdibleApp extends JFrame 
{
    private JComboBox<Person> cmbPersons;
    private JComboBox<Edible> cmbEdibles;
    private JTextArea txtOutput;
    private DefaultTableModel tableModel;

    public EdibleApp() 
    {
        setTitle("Edible Polymorphism GUI - A223381");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("Edible Application", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        String[] columns = {"Object Name", "Category"};
        tableModel = new DefaultTableModel(columns, 0);
        tableModel.addRow(new Object[]{"Chicken", "Animal"});
        tableModel.addRow(new Object[]{"Red Apple", "Apple"});
        tableModel.addRow(new Object[]{"Navel Orange", "Orange"});

        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Default Edible Items"));
        centerPanel.add(tableScroll);

        JPanel interactivePanel = new JPanel();
        interactivePanel.setLayout(new BoxLayout(interactivePanel, BoxLayout.Y_AXIS));
        interactivePanel.setBorder(BorderFactory.createTitledBorder("Interactive Simulation"));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Select Person:"));
        Person p1 = new Person("Ali");
        Person p2 = new Person("Sarah");
        cmbPersons = new JComboBox<>(new Person[]{p1, p2});
        formPanel.add(cmbPersons);

        formPanel.add(new JLabel("Select Edible Item:"));
        Edible[] edibles = 
        {
            new Animal("Chicken"),
            new Apple("Red Apple"),
            new Orange("Navel Orange")
        };
        
        cmbEdibles = new JComboBox<>(edibles);
        formPanel.add(cmbEdibles);

        interactivePanel.add(formPanel);

        JButton btnEat = new JButton("Execute Eating Action");
        btnEat.setAlignmentX(Component.CENTER_ALIGNMENT);
        interactivePanel.add(Box.createVerticalStrut(10));
        interactivePanel.add(btnEat);

        centerPanel.add(interactivePanel);
        add(centerPanel, BorderLayout.CENTER);

        txtOutput = new JTextArea(5, 20);
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollOutput = new JScrollPane(txtOutput);
        scrollOutput.setBorder(BorderFactory.createTitledBorder("Output Console"));
        
        txtOutput.setText("=== Automatic Simulation Log ===\n");
        txtOutput.append(p1.eating(edibles[0]) + "\n");
        txtOutput.append(p2.eating(edibles[1]) + "\n");
        txtOutput.append(p2.eating(edibles[2]) + "\n");

        add(scrollOutput, BorderLayout.SOUTH);

        btnEat.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Person selectedPerson = (Person) cmbPersons.getSelectedItem();
                Edible selectedEdible = (Edible) cmbEdibles.getSelectedItem();

                if (selectedPerson != null && selectedEdible != null) 
                {
                    String result = selectedPerson.eating(selectedEdible);
                    txtOutput.append(result + "\n");
                }
            }
        });
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            new EdibleApp().setVisible(true);
        });
    }
}
