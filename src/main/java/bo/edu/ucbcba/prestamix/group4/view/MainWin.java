package bo.edu.ucbcba.prestamix.group4.view;

import bo.edu.ucbcba.prestamix.group4.controller.CustomerController;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWin extends JFrame
{
    private JTabbedPane tabbedMain;
    private JLabel firstNameLabel;
    private JLabel lastNameFLabel;
    private JLabel lastNameMLabel;
    private JLabel addressLabel;
    private JLabel numberPhoneLabel;
    private JTable tableCustomers;
    private JTextField firstNameField;
    private JTextField lastNameFField;
    private JTextField lastNameMField;
    private JTextArea addressArea;
    private JTextField numberPhoneField;
    private JPanel rootPanel;
    private JButton addCustomerButton;
    private JLabel ciLabel;
    private JTextField ciField;
    private JButton listCustomersButton;

    private CustomerController controllerCustomer;

    public MainWin()
    {
        super("-PRESTAMIX-");
        setContentPane(rootPanel);
        setSize(600,600);
        controllerCustomer = new CustomerController();

        addCustomerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        listCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateTable();}
        });
    }

    public void addCustomer()
    {
        controllerCustomer.create(ciField.getText(), firstNameField.getText(),
                lastNameFField.getText(), lastNameMField.getText(),
                addressArea.getText(), numberPhoneField.getText());
    }

    private void populateTable()
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


}
