package bo.edu.ucbcba.prestamix.group4.view;

import bo.edu.ucbcba.prestamix.group4.controller.CustomerController;
import bo.edu.ucbcba.prestamix.group4.controller.PawnController;
import bo.edu.ucbcba.prestamix.group4.controller.PledgeController;
import bo.edu.ucbcba.prestamix.group4.controller.StoreController;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.Customer;
import bo.edu.ucbcba.prestamix.group4.model.Pawn;
import bo.edu.ucbcba.prestamix.group4.model.Pledge;
import bo.edu.ucbcba.prestamix.group4.model.Store;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWin extends JFrame
{
    private JTabbedPane tabbedMain;
    private JPanel rootPanel;

    //CUSTOMERS
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
    private JButton deleteCustomerButton;
    private JButton searchCustomerButton;
    private JTextField searchCustomerField;
    private String customerCategory = "firstName";
    private JRadioButton searchFirstNameRadioButton;
    private JRadioButton searchFLastNameRadioButton;
    private JRadioButton searchCIRadioButton;
    private JRadioButton searchMLastNameRadioButton;

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
    private JButton deletePledgeButton;

    //STORES
    private JPanel storesPanel;
    private JTextField nameStoreField;
    private JTextArea descriptionStoreArea;
    private JTextField statusStoreField;
    private JButton addStoreButton;
    private JButton listStoreButton;
    private JTable tableStores;
    private JButton deleteStoreButton;

    //PAWNS
    private JPanel pawnsPanel;
    private JComboBox comboCustomers;
    private JComboBox comboPledges;
    private JTextField amountField;
    private JTextField DateField;
    private JComboBox comboStatus;
    private JButton addPawnButton;
    private JButton listPawnsButton;
    private JTable tablePawns;
    private JComboBox comboType;
    private JButton searchPledgeButton;
    private JTextField searchPledgeTextField;
    private JRadioButton searchPledgeByNameRadioButton;
    private JRadioButton searchPledgeTypeRadioButton;
    private JRadioButton searchPledgeCodRadioButton;


    //CONTROLLERS
    private CustomerController controllerCustomer;
    private PledgeController controllerPledge;
    private StoreController controllerStore;
    private PawnController controllerPawn;

    public MainWin()
    {
        super("-PRESTAMIX-");
        setContentPane(rootPanel);
        setSize(600,600);
        //pack();
        controllerCustomer = new CustomerController();
        controllerPledge = new PledgeController();
        controllerStore= new StoreController();
        controllerPawn = new PawnController();

        loadComboCustomers();
        loadComboPledges();
        loadComboStatus();
        loadComboType();

        addCustomerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {addCustomer();}
        });

        deleteCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { deleteCustomer();}
        });

        listCustomersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateTableCustomers(); loadComboCustomers();}
        });

        searchCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateSearchingTableCustomers();
            }
        });

        addPledgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { addPledge();}
        });

        listPlegdesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateTablePledges();}
        });

        deletePledgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { deletePledge();}
        });

        searchPledgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateSearchingTablePledges();

            }
        });


        addStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { addStore();}
        });

        listStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateTableStores();}
        });

        deleteStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { deleteStore();}
        });
        addPawnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { addPawn();}
        });

        listPawnsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { populateTablePawns();}
        });

    }

    public void addCustomer()
    {
        try {
            controllerCustomer.create(ciField.getText(), firstNameField.getText(),
                    lastNameFField.getText(), lastNameMField.getText(),
                    addressArea.getText(), numberPhoneField.getText());
        }catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
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

    private void populateSearchingTableCustomers()
    {
        List<Customer> customers=controllerCustomer.show();
        if (searchFirstNameRadioButton.isSelected()) {
             customers= controllerCustomer.searchCustomerByFirstName(searchCustomerField.getText());
        }
        if (searchFLastNameRadioButton.isSelected()) {
            customers= controllerCustomer.searchCustomerByFathersLastName(searchCustomerField.getText());
        }
        if (searchMLastNameRadioButton.isSelected()) {
            customers= controllerCustomer.searchCustomerByMothersLastName(searchCustomerField.getText());
        }
        if (searchCIRadioButton.isSelected()) {
            customers= controllerCustomer.searchCustomerByCI(searchCustomerField.getText());
        }
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


    public void deleteCustomer()
    {
        DefaultTableModel tm = (DefaultTableModel) tableCustomers.getModel();
        int ci = (Integer) tm.getValueAt(tableCustomers.getSelectedRow(),0);
        controllerCustomer.delete(ci);
    }

    public void addPledge()
    {
        try {
            controllerPledge.create(codField.getText(), nameField.getText(),
                    typeField.getText(), descriptionArea.getText(), locationField.getText());
        }catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }

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

    public void populateSearchingTablePledges()
    {
        List<Pledge> pledges = controllerPledge.show();
        if (searchPledgeByNameRadioButton.isSelected()) {
            pledges = controllerPledge.searchPledgeByName(searchPledgeTextField.getText());
        }
        if (searchPledgeCodRadioButton.isSelected()) {
            pledges = controllerPledge.searchPledgeByCode(searchPledgeTextField.getText());
        }
        if (searchPledgeTypeRadioButton.isSelected()) {
            pledges= controllerPledge.searchPledgeByType(searchPledgeTextField.getText());
        }
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



    public void deletePledge()
    {
        DefaultTableModel tm = (DefaultTableModel) tablePlegdes.getModel();
        String cod = (String) tm.getValueAt(tablePlegdes.getSelectedRow(),0);
        controllerPledge.delete(cod);
    }

    public void addStore()
    {
        try {
            controllerStore.create(nameStoreField.getText(),descriptionStoreArea.getText(),
                    statusStoreField.getText());
        }catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void populateTableStores()
    {
        List<Store> stores = controllerStore.show();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NOMBRE DE DEPOSITO");
        model.addColumn("DESCRIPCION");
        model.addColumn("ESTADO");
        tableStores.setModel(model);

        for (Store s : stores)
        {
            Object[] row = new Object[4];

            row[0] = s.getId();
            row[1] = s.getName();
            row[2] = s.getDescription();
            row[3] = s.getStatus();
            model.addRow(row);
        }

    }

    public void deleteStore()
    {
        DefaultTableModel tm = (DefaultTableModel) tableStores.getModel();
        int id = (Integer) tm.getValueAt(tableStores.getSelectedRow(),0);
        controllerStore.delete(id);
    }

    public void loadComboCustomers()
    {
        List<Customer> customers = controllerCustomer.show();
        for(Customer c: customers)
        {
            comboCustomers.addItem(c.getCi());
        }
    }

    public void loadComboPledges()
    {
        List<Pledge> pledges = controllerPledge.show();
        for(Pledge p: pledges)
        {
            comboPledges.addItem(p.getCod());
        }
    }

    public void loadComboStatus()
    {
        comboStatus.addItem("Vigente");
        comboStatus.addItem("Muerto");
        comboStatus.addItem("Vendido");
    }
    public void  loadComboType()
    {
        comboType.addItem("Bs.");
        comboType.addItem("$us.");
    }

    public void addPawn()
    {
        try {
            /*controllerPawn.create((String) comboCustomers.getSelectedItem(),
                    (String) comboPledges.getSelectedItem(),
                    amountField.getText(),(String) comboType.getSelectedItem(),
                            DateField.getText(), (String) comboStatus.getSelectedItem());*/ //--> Hay que corregir algo aqui

            controllerPawn.create(String.valueOf(comboCustomers.getSelectedIndex()),
                    String.valueOf(comboPledges.getSelectedIndex()),
                    amountField.getText(),String.valueOf(comboType.getSelectedIndex()),
                    DateField.getText(), String.valueOf(comboStatus.getSelectedItem()));

        }catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void populateTablePawns()
    {
        List<Pawn> pawns = controllerPawn.show();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("CI CLIENTE");
        model.addColumn("COD PRENDA");
        model.addColumn("MONTO");
        model.addColumn("TIPO");
        model.addColumn("FECHA");
        model.addColumn("ESTADO");
        tablePawns.setModel(model);

        for (Pawn p : pawns)
        {
            Object[] row = new Object[7];

            row[0] = p.getId();
            row[1] = p.getCiCustomer();
            row[2] = p.getCodPledge();
            row[3] = p.getAmount();
            row[4] = p.getType();
            row[5] = p.getDate();
            row[6] = p.getStatus();
            model.addRow(row);
        }
    }
}
