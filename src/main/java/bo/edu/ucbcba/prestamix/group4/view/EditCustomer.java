package bo.edu.ucbcba.prestamix.group4.view;

import bo.edu.ucbcba.prestamix.group4.controller.CustomerController;
import bo.edu.ucbcba.prestamix.group4.model.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EditCustomer extends JDialog
{
    private JTextField ciField;
    private JTextField firstNameField;
    private JTextField lastNamePField;
    private JTextField lastNameFieldM;
    private JTextField numberPhoneField;
    private JPanel rootPanel;
    private JTextArea addressArea;
    private JButton okButton;
    private JButton cancelButton;

    public EditCustomer(JFrame owner, int id, Customer c, CustomerController cc)
    {
        super(owner, "-EDITAR CLIENTE-");
        setContentPane(rootPanel);
        setSize(300,300);

        load(c);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { cancel();
            }
        });
    }

    public void load(Customer c)
    {
        ciField.setText(String.valueOf(c.getCi()));
        firstNameField.setText(c.getFirtsName());
        lastNamePField.setText(c.getLastNameF());
        lastNameFieldM.setText(c.getLastNameM());
        addressArea.setText(c.getAddress());
        numberPhoneField.setText(String.valueOf(c.getNumberPhone()));
    }

    private void cancel() {
        setVisible(false);
        dispose();
    }

    public void prueba()
    {
        int n =0;
    }

}
