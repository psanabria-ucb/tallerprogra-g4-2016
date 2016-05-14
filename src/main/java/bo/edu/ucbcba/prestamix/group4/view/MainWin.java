package bo.edu.ucbcba.prestamix.group4.view;

import bo.edu.ucbcba.prestamix.group4.controller.CustomerController;
import bo.edu.ucbcba.prestamix.group4.controller.PledgeController;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Customer;
import bo.edu.ucbcba.prestamix.group4.model.Pledge;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWin extends JFrame
{
    private JTabbedPane tabbedMain;
    private JPanel rootPanel;

    private JTextField ciField;
    private JTable tableCustomers;
    private JTextField firstNameField;
    private JTextField lastNameFField;
    private JTextField lastNameMField;
    private JTextArea addressArea;
    private JTextField numberPhoneField;
    private JButton addCustomerButton;
    private JButton listCustomersButton;
    private JPanel customersPanel;

    //PLEDGES
    private JPanel pledgesPanel;
    private JTextField codField;
    private JTextField nameField;
    private JTextField typeField;
    private JTextArea descriptionArea;
    private JTextField locationField;
    private JButton addPledgeButton;
    private JButton listPlegdesButton;
    private JTable tablePlegdes;

    //CONTROLLERS
    private CustomerController controllerCustomer;
    private PledgeController controllerPledge;

    public MainWin()
    {
        super("-PRESTAMIX-");
        setContentPane(rootPanel);
        setSize(600,600);
        controllerCustomer = new CustomerController();
        controllerPledge = new PledgeController();

        addCustomerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {addCustomer();}
        });

        listCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateTableCustomers();}
        });

        addPledgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { addPledge();}
        });

        listPlegdesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateTablePledges();}
        });
    }

    public void addCustomer()
    {
        controllerCustomer.create(ciField.getText(), firstNameField.getText(),
                lastNameFField.getText(), lastNameMField.getText(),
                addressArea.getText(), numberPhoneField.getText());
    }

    private void populateTableCustomers()
    {
        List<Customer> customers = controllerCustomer.show();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("CI");
        model.addColumn("NOMBRE");
        model.addColumn("APELLIDO PATERNO");
        model.addColumn("APELLIDO MATERNO");
        model.addColumn("DIRECCION");
        model.addColumn("TELEFONO");
        tableCustomers.setModel(model);

        for (Customer c : customers)
        {
            Object[] row = new Object[6];

            row[0] = c.getCi();
            row[1] = c.getFirtsName();
            row[2] = c.getLastNameF();
            row[3] = c.getLastNameM();
            row[4] = c.getAddress();
            row[5] = c.getNumberPhone();
            model.addRow(row);
        }
    }

    public void addPledge()
    {
        controllerPledge.create(codField.getText(), nameField.getText(),
                typeField.getText(), descriptionArea.getText(), locationField.getText());
    }

    public void populateTablePledges()
    {
        List<Pledge> pledges = controllerPledge.show();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("COD");
        model.addColumn("NOMBRE DE LA PRENDA");
        model.addColumn("TIPO");
        model.addColumn("DESCRIPCION");
        model.addColumn("UBICACION");
        tablePlegdes.setModel(model);

        for (Pledge p : pledges)
        {
            Object[] row = new Object[5];

            row[0] = p.getCod();
            row[1] = p.getName();
            row[2] = p.getType();
            row[3] = p.getDescription();
            row[4] = p.getLocation();
            model.addRow(row);
        }
    }
}
