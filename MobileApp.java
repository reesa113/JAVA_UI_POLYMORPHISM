/*
AREESA NABEELA BINTI BAMBANG SETIAWAN
A223381
2DipIT
*/

import javax.swing.*;
import java.awt.*;

interface RadioTuner
{
    void setStation(String station);
    void setFrequency(double frequency);
}

class MobileDevice implements RadioTuner
{
    protected String manufacturer;
    protected String model;
    protected String station;
    protected double frequency;
    
    MobileDevice(String manufacturer, String model)
    {
        this.manufacturer = manufacturer;
        this.model = model;
    }
    
    public void printDetails()
    {
        System.out.println("Manufacturer: " + manufacturer);
        System.out.println("Model: " + model);
    }
    
    @Override
    public void setStation(String station)
    {
        this.station = station;
    }
    
    @Override
    public void setFrequency(double frequency)
    {
        this.frequency = frequency;
    }
}

class SmartPhone extends MobileDevice
{
    private String type;
    
    SmartPhone(String manufacturer, String model, String type)
    {
        super(manufacturer, model);
        this.type = type;
    }
    
    @Override
    public void printDetails()
    {
        System.out.println("SmartPhone Detail:");
        super.printDetails();
        System.out.println("Type: " + type);
    }
}

class BasicPhone extends MobileDevice
{
    BasicPhone(String manufacturer, String model)
    {
        super(manufacturer, model);
    }
    
    @Override
    public void printDetails()
    {
        System.out.println("\nBasic Phone Detail:");
        super.printDetails();
    }
}

public class MobileApp extends JFrame
{
    private JComboBox<MobileDevice> cmbDevices;
    private JTextField txtStation;
    private JTextField txtFrequency;
    private JTextArea txtOutput;
    
    public MobileApp()
    {
        setTitle("Mobile App Interface - A223381");
        setSize(650, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        SmartPhone m1 = new SmartPhone("Samsung", "S20", "Galaxy");
        BasicPhone m2 = new BasicPhone("Sonny Ericson", "Walkman");
        
        m1.setStation("Hot FM");
        m1.setFrequency(97.60);
        
        m2.setStation("Fly FM");
        m2.setFrequency(109.8);
        
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Mobile Device: "));
        cmbDevices = new JComboBox<>(new MobileDevice[]{m1, m2});
        topPanel.add(cmbDevices);
        add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Tune Radio Station"));
        
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        formPanel.add(new JLabel("Station:"));
        txtStation = new JTextField(10);
        formPanel.add(txtStation);
        formPanel.add(new JLabel("Frequency:"));
        txtFrequency = new JTextField(10);
        formPanel.add(txtFrequency);
        
        controlPanel.add(formPanel);
        
        JButton btnUpdate = new JButton("Apply Tuning");
        btnUpdate.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(btnUpdate);
        
        centerPanel.add(controlPanel);
        
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        txtOutput.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Device Information"));
        centerPanel.add(scrollPane);
        
        add(centerPanel, BorderLayout.CENTER);
        
        cmbDevices.addActionListener(e -> updateDisplay());
        
        btnUpdate.addActionListener(e -> 
        {
            MobileDevice selected = (MobileDevice) cmbDevices.getSelectedItem();
            try
            {
                if (selected != null)
                {
                    selected.setStation(txtStation.getText());
                    selected.setFrequency(Double.parseDouble(txtFrequency.getText()));
                    updateDisplay();
                    JOptionPane.showMessageDialog(this, "Station and frequency updated successfully!");
                }
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric frequency.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        updateDisplay();
    }
    
    private void updateDisplay()
    {
        MobileDevice selected = (MobileDevice) cmbDevices.getSelectedItem();
        if (selected != null)
        {
            txtStation.setText(selected.station);
            txtFrequency.setText(String.valueOf(selected.frequency));
            
            StringBuilder sb = new StringBuilder();
            if (selected instanceof SmartPhone)
            {
                sb.append("SmartPhone Detail:\n");
                sb.append("Manufacturer: " + selected.manufacturer + "\n");
                sb.append("Model: " + selected.model + "\n");
                sb.append("Type: Galaxy\n");
            }
            else
            {
                sb.append("Basic Phone Detail:\n");
                sb.append("Manufacturer: " + selected.manufacturer + "\n");
                sb.append("Model: " + selected.model + "\n");
            }
            sb.append("Station: " + selected.station + "\n");
            sb.append("Frequency: " + selected.frequency + "\n");
            
            txtOutput.setText(sb.toString());
        }
    }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> 
        {
            new MobileApp().setVisible(true);
        });
    }
}
