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
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWin extends JFrame {
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
    private JPanel customersPanel;
    private JButton deleteCustomerButton;
    private JButton searchCustomerButton;
    private JTextField searchCustomerField;
    private JRadioButton searchFirstNameRadioButton;
    private JRadioButton searchFLastNameRadioButton;
    private JRadioButton searchCIRadioButton;
    private JRadioButton searchMLastNameRadioButton;

    //PLEDGES
    private JPanel pledgesPanel;
    private JTextField codField;
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JButton addPledgeButton;
    private JTable tablePlegdes;
    private JButton deletePledgeButton;
    private JComboBox comboLocation;
    private JComboBox comboTypePledge;
    private JButton searchPledgeButton;
    private JTextField searchPledgeTextField;
    private JRadioButton searchPledgeByNameRadioButton;
    private JRadioButton searchPledgeTypeRadioButton;
    private JRadioButton searchPledgeCodRadioButton;
    private JRadioButton searchPledgeLocationRadioButton;

    //STORES
    private JPanel storesPanel;
    private JTextField nameStoreField;
    private JTextArea descriptionStoreArea;
    private JTextField statusStoreField;
    private JButton addStoreButton;
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
    private JTable tablePawns;
    private JComboBox comboType;

    private JButton deletePawnButton;
    private JButton searchPawnsButton;
    private JTextField searchPawnsTextField;
    private JRadioButton PawnNameClientRadioButton;
    private JRadioButton PawnAmountRadioButton;
    private JRadioButton PawnTipoDeMonedaRadioButton;
    private JRadioButton PawnPrendaRadioButton;
    private JRadioButton PawnEstadoRadioButton;
    private JRadioButton PawnFechaRadioButton;


    //CONTROLLERS
    private CustomerController controllerCustomer;
    private PledgeController controllerPledge;
    private StoreController controllerStore;
    private PawnController controllerPawn;

    public MainWin() {
        super("-PRESTAMIX-");
        setContentPane(rootPanel);
        setSize(800, 600);
        //pack();
        controllerCustomer = new CustomerController();
        controllerPledge = new PledgeController();
        controllerStore = new StoreController();
        controllerPawn = new PawnController();

        loadComboTypesPledges();
        loadComboLocation();
        loadComboCustomers();
        loadComboPledges();
        loadComboStatus();
        loadComboType();

        populateTableCustomers();
        populateTablePawns();
        populateTablePledges();
        populateTableStores();


        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
                populateTableCustomers();
            }
        });

        deleteCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
                populateTableCustomers();
            }
        });


        searchCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateSearchingTableCustomers();
            }
        });

        addPledgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPledge();
                populateTablePledges();
            }
        });


        deletePledgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePledge();
                populateTablePledges();
            }
        });

        searchPledgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateSearchingTablePledges();

            }
        });


        addStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStore();
                populateTableStores();
            }
        });


        deleteStoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStore();
                populateTableStores();
            }
        });
        addPawnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPawn();
                populateTablePawns();
            }
        });


        deletePawnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePawn();
            }
        });

        searchPawnsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateSearchingTablePawns();
            }
        });


    }

    public void addCustomer() {
        try {
            controllerCustomer.create(ciField.getText(), firstNameField.getText(),
                    lastNameFField.getText(), lastNameMField.getText(),
                    addressArea.getText(), numberPhoneField.getText());
            comboCustomers.removeAllItems();
            loadComboCustomers();
            populateTableCustomers();
            clearCustomerFields();
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateTableCustomers() {
        List<Customer> customers = controllerCustomer.show();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("CI");
        model.addColumn("NOMBRE");
        model.addColumn("APELLIDO PATERNO");
        model.addColumn("APELLIDO MATERNO");
        model.addColumn("DIRECCION");
        model.addColumn("TELEFONO");
        tableCustomers.setModel(model);

        for (Customer c : customers) {
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

    private void populateSearchingTableCustomers() {
        List<Customer> customers = controllerCustomer.show();
        if (searchFirstNameRadioButton.isSelected()) {
            customers = controllerCustomer.searchCustomerByFirstName(searchCustomerField.getText());
        }
        if (searchFLastNameRadioButton.isSelected()) {
            customers = controllerCustomer.searchCustomerByFathersLastName(searchCustomerField.getText());
        }
        if (searchMLastNameRadioButton.isSelected()) {
            customers = controllerCustomer.searchCustomerByMothersLastName(searchCustomerField.getText());
        }
        if (searchCIRadioButton.isSelected()) {
            try {
                customers = controllerCustomer.searchCustomerByCI(searchCustomerField.getText());
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }

        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("CI");
        model.addColumn("NOMBRE");
        model.addColumn("APELLIDO PATERNO");
        model.addColumn("APELLIDO MATERNO");
        model.addColumn("DIRECCION");
        model.addColumn("TELEFONO");
        tableCustomers.setModel(model);

        for (Customer c : customers) {
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
        if (tableCustomers.getSelectedRowCount() > 0)
        {
            int ci=(Integer) tm.getValueAt(tableCustomers.getSelectedRow(), 0);
            controllerCustomer.delete(ci);
            comboCustomers.removeAllItems();
            loadComboCustomers();
            populateTableCustomers();
        }
        else
        {
            try {
                controllerCustomer.delete(0);
            }catch (ValidationException ex)
            {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No hay nada seleccionado", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void clearCustomerFields() {
        ciField.setText("");
        firstNameField.setText("");
        lastNameFField.setText("");
        lastNameMField.setText("");
        addressArea.setText("");
        numberPhoneField.setText("");

    }


    public void addPledge() {
        try {
            controllerPledge.create(codField.getText(), nameField.getText(),
                    String.valueOf(comboTypePledge.getSelectedItem()), descriptionArea.getText(),
                    String.valueOf(comboLocation.getSelectedItem()));
            comboPledges.removeAllItems();
            loadComboPledges();
            populateTablePledges();
            clearPledgeFields();

        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void populateTablePledges() {
        List<Pledge> pledges = controllerPledge.show();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("COD");
        model.addColumn("NOMBRE DE LA PRENDA");
        model.addColumn("TIPO");
        model.addColumn("DESCRIPCION");
        model.addColumn("UBICACION");
        tablePlegdes.setModel(model);

        for (Pledge p : pledges) {
            Object[] row = new Object[5];

            row[0] = p.getCod();
            row[1] = p.getName();
            row[2] = p.getType();
            row[3] = p.getDescription();
            row[4] = p.getLocation();
            model.addRow(row);
        }
    }

    public void populateSearchingTablePledges() {
        List<Pledge> pledges = controllerPledge.show();
        if (searchPledgeByNameRadioButton.isSelected()) {
            pledges = controllerPledge.searchPledgeByName(searchPledgeTextField.getText());
        }
        if (searchPledgeCodRadioButton.isSelected()) {
            pledges = controllerPledge.searchPledgeByCode(searchPledgeTextField.getText());
        }
        if (searchPledgeTypeRadioButton.isSelected()) {
            pledges = controllerPledge.searchPledgeByType(searchPledgeTextField.getText());
        }
        if (searchPledgeLocationRadioButton.isSelected()) {
            pledges = controllerPledge.searchPledgeByLocation(searchPledgeTextField.getText());
        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("COD");
        model.addColumn("NOMBRE DE LA PRENDA");
        model.addColumn("TIPO");
        model.addColumn("DESCRIPCION");
        model.addColumn("UBICACION");
        tablePlegdes.setModel(model);

        for (Pledge p : pledges) {
            Object[] row = new Object[5];

            row[0] = p.getCod();
            row[1] = p.getName();
            row[2] = p.getType();
            row[3] = p.getDescription();
            row[4] = p.getLocation();
            model.addRow(row);
        }
    }


    public void deletePledge() {

        DefaultTableModel tm = (DefaultTableModel) tablePlegdes.getModel();
        if (tablePlegdes.getSelectedRowCount() > 0)
        {
            String cod = (String) tm.getValueAt(tablePlegdes.getSelectedRow(), 0);
            controllerPledge.delete(cod);
            comboPledges.removeAllItems();
            loadComboPledges();
            populateTablePledges();
        }
        else
        {
            try{
                controllerPledge.delete(" ");

            }catch (ValidationException ex)
            {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No selecciono ningun elemento", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearPledgeFields() {
        codField.setText("");
        nameField.setText("");
        descriptionArea.setText("");
    }


    public void addStore() {
        try {
            controllerStore.create(nameStoreField.getText(), descriptionStoreArea.getText(),
                    statusStoreField.getText());
            comboLocation.removeAllItems();
            loadComboLocation();
            populateTableStores();
            clearStoreFields();
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void populateTableStores() {
        List<Store> stores = controllerStore.show();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NOMBRE DE DEPOSITO");
        model.addColumn("DESCRIPCION");
        model.addColumn("ESTADO");
        tableStores.setModel(model);

        for (Store s : stores) {
            Object[] row = new Object[4];

            row[0] = s.getId();
            row[1] = s.getName();
            row[2] = s.getDescription();
            row[3] = s.getStatus();
            model.addRow(row);
        }

    }

    public void deleteStore() {
        DefaultTableModel tm = (DefaultTableModel) tableStores.getModel();
        if (tableStores.getSelectedRowCount() > 0) {
            int id = (Integer) tm.getValueAt(tableStores.getSelectedRow(), 0);
            controllerStore.delete(id);
            populateTableStores();
        }
        else
        {
            try{
                controllerStore.delete(0);
            }
            catch (ValidationException ex)
            {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No selecciono ningun elemento", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearStoreFields() {
        nameStoreField.setText("");
        descriptionStoreArea.setText("");
        statusStoreField.setText("");
    }

    public void loadComboTypesPledges() {
        comboTypePledge.addItem("Artículo Digital");
        comboTypePledge.addItem("Consola");
        comboTypePledge.addItem("Electrodoméstico");
        comboTypePledge.addItem("Joya");
        comboTypePledge.addItem("Vehículo");
    }

    public void loadComboLocation() {
        List<Store> stores = controllerStore.show();
        for (Store s : stores) {
            comboLocation.addItem(s);
        }

    }

    public void loadComboCustomers() {
        List<Customer> customers = controllerCustomer.show();
        for (Customer c : customers) {
            comboCustomers.addItem(c);
        }
    }

    public void loadComboPledges() {
        List<Pledge> pledges = controllerPledge.getAllPledges();
        for (Pledge p : pledges) {
            comboPledges.addItem(p);
        }
    }

    public void loadComboStatus() {
        comboStatus.addItem("Vigente");
        comboStatus.addItem("Muerto");
        comboStatus.addItem("Vendido");
    }

    public void loadComboType() {
        comboType.addItem("Bs.");
        comboType.addItem("$us.");
    }

    public void addPawn() {
        try {
            controllerPawn.create(String.valueOf(comboCustomers.getSelectedItem()),
                    String.valueOf(comboPledges.getSelectedItem()),
                    amountField.getText(), String.valueOf(comboType.getSelectedItem()),
                    DateField.getText(), String.valueOf(comboStatus.getSelectedItem()));
            populateTablePawns();
            clearPawnFields();

        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void populateTablePawns() {
        List<Pawn> pawns = controllerPawn.show();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NOMBRE CLIENTE");
        model.addColumn("COD PRENDA");
        model.addColumn("MONTO");
        model.addColumn("TIPO");
        model.addColumn("FECHA");
        model.addColumn("ESTADO");
        tablePawns.setModel(model);

        for (Pawn p : pawns) {
            Object[] row = new Object[7];

            row[0] = p.getId();
            row[1] = p.getNameCustomer();
            row[2] = p.getCodPledge();
            row[3] = p.getAmount();
            row[4] = p.getType();
            row[5] = p.getDate();
            row[6] = p.getStatus();
            model.addRow(row);
        }
    }

    public void populateSearchingTablePawns() {
        List<Pawn> pawns = controllerPawn.show();
        if (PawnNameClientRadioButton.isSelected())
            pawns = controllerPawn.searchPawnsByCustomer(searchPawnsTextField.getText());
        if (PawnPrendaRadioButton.isSelected())
            pawns = controllerPawn.searchPawnsByPledge(searchPawnsTextField.getText());
        if (PawnAmountRadioButton.isSelected()) {
            try {
                pawns = controllerPawn.searchPawnsByAmount(searchPawnsTextField.getText());
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (PawnTipoDeMonedaRadioButton.isSelected())
            pawns = controllerPawn.searchPawnsByType(searchPawnsTextField.getText());
        if (PawnEstadoRadioButton.isSelected())
            pawns = controllerPawn.searchPawnsByStatus(searchPawnsTextField.getText());
        if (PawnFechaRadioButton.isSelected()) {
            try {
                pawns = controllerPawn.searchPawnByDate(searchPawnsTextField.getText());
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }

        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("NOMBRE CLIENTE");
        model.addColumn("COD PRENDA");
        model.addColumn("MONTO");
        model.addColumn("TIPO");
        model.addColumn("FECHA");
        model.addColumn("ESTADO");
        tablePawns.setModel(model);

        for (Pawn p : pawns) {
            Object[] row = new Object[7];

            row[0] = p.getId();
            row[1] = p.getNameCustomer();
            row[2] = p.getCodPledge();
            row[3] = p.getAmount();
            row[4] = p.getType();
            row[5] = p.getDate();
            row[6] = p.getStatus();
            model.addRow(row);
        }

    }


    public void deletePawn() {
        DefaultTableModel tm = (DefaultTableModel) tablePawns.getModel();
        if(tablePawns.getSelectedRowCount() > 0) {
            int id = (Integer) tm.getValueAt(tablePawns.getSelectedRow(), 0);
            controllerPawn.delete(id);
            populateTablePawns();
        }
        else
        {
            try{
                controllerPawn.delete(0);
            }
            catch (ValidationException ex)
            {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "No se selecciono ningun elemento", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void clearPawnFields() {

        amountField.setText("");
        DateField.setText("");
        searchPledgeTextField.setText("");

    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedMain = new JTabbedPane();
        rootPanel.add(tabbedMain, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        customersPanel = new JPanel();
        customersPanel.setLayout(new GridLayoutManager(11, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedMain.addTab("Clientes", customersPanel);
        final JLabel label1 = new JLabel();
        label1.setText("Nombre:");
        customersPanel.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Apellido Paterno:");
        customersPanel.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Dirección:");
        customersPanel.add(label3, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Apellido Materno:");
        customersPanel.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Teléfono:");
        customersPanel.add(label5, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        firstNameField = new JTextField();
        customersPanel.add(firstNameField, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lastNameFField = new JTextField();
        customersPanel.add(lastNameFField, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lastNameMField = new JTextField();
        customersPanel.add(lastNameMField, new GridConstraints(3, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addressArea = new JTextArea();
        addressArea.setLineWrap(true);
        customersPanel.add(addressArea, new GridConstraints(4, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        numberPhoneField = new JTextField();
        customersPanel.add(numberPhoneField, new GridConstraints(5, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("CI:");
        customersPanel.add(label6, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ciField = new JTextField();
        customersPanel.add(ciField, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addCustomerButton = new JButton();
        addCustomerButton.setText("Agregar");
        customersPanel.add(addCustomerButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tableCustomers = new JTable();
        customersPanel.add(tableCustomers, new GridConstraints(6, 1, 3, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        searchCustomerButton = new JButton();
        searchCustomerButton.setText("Buscar");
        customersPanel.add(searchCustomerButton, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchCustomerField = new JTextField();
        customersPanel.add(searchCustomerField, new GridConstraints(9, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchFirstNameRadioButton = new JRadioButton();
        searchFirstNameRadioButton.setSelected(true);
        searchFirstNameRadioButton.setText("Nombre");
        customersPanel.add(searchFirstNameRadioButton, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchFLastNameRadioButton = new JRadioButton();
        searchFLastNameRadioButton.setText("Apellido Paterno");
        customersPanel.add(searchFLastNameRadioButton, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchCIRadioButton = new JRadioButton();
        searchCIRadioButton.setText("C. I.");
        customersPanel.add(searchCIRadioButton, new GridConstraints(10, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchMLastNameRadioButton = new JRadioButton();
        searchMLastNameRadioButton.setText("Apellido Materno");
        customersPanel.add(searchMLastNameRadioButton, new GridConstraints(10, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteCustomerButton = new JButton();
        deleteCustomerButton.setText("Eliminar");
        customersPanel.add(deleteCustomerButton, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pledgesPanel = new JPanel();
        pledgesPanel.setLayout(new GridLayoutManager(10, 5, new Insets(0, 0, 0, 0), -1, -1));
        tabbedMain.addTab("Prendas", pledgesPanel);
        final JLabel label7 = new JLabel();
        label7.setText("Código:");
        pledgesPanel.add(label7, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Nombre de la Prenda:");
        pledgesPanel.add(label8, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Tipo:");
        pledgesPanel.add(label9, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Descripción:");
        pledgesPanel.add(label10, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Ubicación:");
        pledgesPanel.add(label11, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        codField = new JTextField();
        pledgesPanel.add(codField, new GridConstraints(0, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nameField = new JTextField();
        pledgesPanel.add(nameField, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        pledgesPanel.add(descriptionArea, new GridConstraints(3, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        addPledgeButton = new JButton();
        addPledgeButton.setText("Agregar");
        pledgesPanel.add(addPledgeButton, new GridConstraints(5, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tablePlegdes = new JTable();
        pledgesPanel.add(tablePlegdes, new GridConstraints(5, 2, 3, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        deletePledgeButton = new JButton();
        deletePledgeButton.setText("Eliminar");
        pledgesPanel.add(deletePledgeButton, new GridConstraints(6, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPledgeButton = new JButton();
        searchPledgeButton.setText("Buscar");
        pledgesPanel.add(searchPledgeButton, new GridConstraints(8, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPledgeTextField = new JTextField();
        pledgesPanel.add(searchPledgeTextField, new GridConstraints(8, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        searchPledgeCodRadioButton = new JRadioButton();
        searchPledgeCodRadioButton.setText("Código");
        pledgesPanel.add(searchPledgeCodRadioButton, new GridConstraints(9, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPledgeByNameRadioButton = new JRadioButton();
        searchPledgeByNameRadioButton.setSelected(true);
        searchPledgeByNameRadioButton.setText("Nombre");
        pledgesPanel.add(searchPledgeByNameRadioButton, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPledgeTypeRadioButton = new JRadioButton();
        searchPledgeTypeRadioButton.setText("Tipo");
        pledgesPanel.add(searchPledgeTypeRadioButton, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboLocation = new JComboBox();
        pledgesPanel.add(comboLocation, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboTypePledge = new JComboBox();
        pledgesPanel.add(comboTypePledge, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPledgeLocationRadioButton = new JRadioButton();
        searchPledgeLocationRadioButton.setText("Ubicación");
        pledgesPanel.add(searchPledgeLocationRadioButton, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pawnsPanel = new JPanel();
        pawnsPanel.setLayout(new GridLayoutManager(10, 6, new Insets(0, 0, 0, 0), -1, -1));
        tabbedMain.addTab("Empeños", pawnsPanel);
        final JLabel label12 = new JLabel();
        label12.setText("Cliente:");
        pawnsPanel.add(label12, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Prenda:");
        pawnsPanel.add(label13, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Monto:");
        pawnsPanel.add(label14, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Fecha (mes/día/año):");
        pawnsPanel.add(label15, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label16 = new JLabel();
        label16.setText("Estado:");
        pawnsPanel.add(label16, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboCustomers = new JComboBox();
        pawnsPanel.add(comboCustomers, new GridConstraints(0, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboPledges = new JComboBox();
        pawnsPanel.add(comboPledges, new GridConstraints(1, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        amountField = new JTextField();
        pawnsPanel.add(amountField, new GridConstraints(2, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        DateField = new JTextField();
        pawnsPanel.add(DateField, new GridConstraints(4, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        comboStatus = new JComboBox();
        pawnsPanel.add(comboStatus, new GridConstraints(5, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addPawnButton = new JButton();
        addPawnButton.setText("Agregar");
        pawnsPanel.add(addPawnButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tablePawns = new JTable();
        pawnsPanel.add(tablePawns, new GridConstraints(6, 1, 2, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Tipo: ");
        pawnsPanel.add(label17, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboType = new JComboBox();
        pawnsPanel.add(comboType, new GridConstraints(3, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deletePawnButton = new JButton();
        deletePawnButton.setText("Eliminar");
        pawnsPanel.add(deletePawnButton, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPawnsButton = new JButton();
        searchPawnsButton.setText("Buscar");
        pawnsPanel.add(searchPawnsButton, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        searchPawnsTextField = new JTextField();
        pawnsPanel.add(searchPawnsTextField, new GridConstraints(8, 1, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        PawnNameClientRadioButton = new JRadioButton();
        PawnNameClientRadioButton.setSelected(true);
        PawnNameClientRadioButton.setText("Nombre de cliente");
        pawnsPanel.add(PawnNameClientRadioButton, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PawnPrendaRadioButton = new JRadioButton();
        PawnPrendaRadioButton.setText("Prenda");
        pawnsPanel.add(PawnPrendaRadioButton, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PawnAmountRadioButton = new JRadioButton();
        PawnAmountRadioButton.setText("Monto");
        pawnsPanel.add(PawnAmountRadioButton, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PawnTipoDeMonedaRadioButton = new JRadioButton();
        PawnTipoDeMonedaRadioButton.setText("Tipo de Moneda");
        pawnsPanel.add(PawnTipoDeMonedaRadioButton, new GridConstraints(9, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PawnEstadoRadioButton = new JRadioButton();
        PawnEstadoRadioButton.setText("Estado");
        pawnsPanel.add(PawnEstadoRadioButton, new GridConstraints(9, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        PawnFechaRadioButton = new JRadioButton();
        PawnFechaRadioButton.setText("Fecha");
        pawnsPanel.add(PawnFechaRadioButton, new GridConstraints(9, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        storesPanel = new JPanel();
        storesPanel.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedMain.addTab("Depósitos", storesPanel);
        final JLabel label18 = new JLabel();
        label18.setText("Nombre:");
        storesPanel.add(label18, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("Descripción:");
        storesPanel.add(label19, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("Estado:");
        storesPanel.add(label20, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameStoreField = new JTextField();
        storesPanel.add(nameStoreField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        descriptionStoreArea = new JTextArea();
        descriptionStoreArea.setLineWrap(true);
        storesPanel.add(descriptionStoreArea, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        statusStoreField = new JTextField();
        storesPanel.add(statusStoreField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addStoreButton = new JButton();
        addStoreButton.setText("Agregar");
        storesPanel.add(addStoreButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tableStores = new JTable();
        storesPanel.add(tableStores, new GridConstraints(3, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        deleteStoreButton = new JButton();
        deleteStoreButton.setText("Eliminar");
        storesPanel.add(deleteStoreButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        rootPanel.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(searchFirstNameRadioButton);
        buttonGroup.add(searchFLastNameRadioButton);
        buttonGroup.add(searchCIRadioButton);
        buttonGroup.add(searchMLastNameRadioButton);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(searchPledgeByNameRadioButton);
        buttonGroup.add(searchPledgeTypeRadioButton);
        buttonGroup.add(searchPledgeCodRadioButton);
        buttonGroup.add(searchPledgeLocationRadioButton);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(PawnNameClientRadioButton);
        buttonGroup.add(PawnPrendaRadioButton);
        buttonGroup.add(PawnAmountRadioButton);
        buttonGroup.add(PawnTipoDeMonedaRadioButton);
        buttonGroup.add(PawnEstadoRadioButton);
        buttonGroup.add(PawnFechaRadioButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
