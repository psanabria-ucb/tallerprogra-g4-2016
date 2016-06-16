package bo.edu.ucbcba.prestamix.group4.view;

import bo.edu.ucbcba.prestamix.group4.controller.PawnController;
import bo.edu.ucbcba.prestamix.group4.controller.PaymentController;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.File;
import bo.edu.ucbcba.prestamix.group4.model.Pawn;
import bo.edu.ucbcba.prestamix.group4.model.Payment;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

public class Payments extends JDialog {

    private Object[] header = {"Número de Pago", "Interés", "Monto", "Fecha"};
    private JTextField customerField;
    private JTextField pledgeField;
    private JTextField amountField;
    private JTextField typeField;
    private JTextField dateField;
    private JTextField statusField;
    private JButton addButton;
    private JTextField addField;
    private JTextField interestField;
    private JPanel rootPane;
    private JTextField payField;
    private JTable tablePays = new JTable(null, header);
    private JButton deleteButton;
    private JButton printButton;
    private JButton exportButton;
    final private PaymentController paymentController;

    public Payments(JFrame pays, Pawn p) {
        super(pays, "-PAGOS-", true);
        setContentPane(rootPane);
        setSize(800, 600);
        paymentController = new PaymentController();
        populateTable();
        load(p);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save(p);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePay();
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excel(tablePays);
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utilJTablePrint(tablePays, customerField.getText(),
                        "FIN DE PLANILLA", true);
            }
        });


    }

    public void load(Pawn p) {
        customerField.setText(p.getNameCustomer());
        customerField.setEditable(false);
        pledgeField.setText(p.getCodPledge());
        pledgeField.setEditable(false);
        amountField.setText(String.valueOf(p.getAmount()));
        amountField.setEditable(false);
        typeField.setText(p.getType());
        typeField.setEditable(false);
        dateField.setText(p.getDate());
        dateField.setEditable(false);
        statusField.setText(p.getStatus());
        statusField.setEditable(false);

        interestField.setText("10");
    }

    public void save(Pawn p) {
        try {
            paymentController.create(interestField.getText(), payField.getText(), addField.getText());
            populateTable();
        } catch (ValidationException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void populateTable() {
        List<Payment> payments = paymentController.getAll();
        DefaultTableModel model = new DefaultTableModel(null, header);

        tablePays.setModel(model);
        for (Payment p : payments) {
            Object[] row = new Object[4];

            row[0] = p.getNumberPay();
            row[1] = p.getInterest();
            row[2] = p.getAmount();
            row[3] = p.getDate();
            model.addRow(row);
        }
    }

    public void deletePay() {
        DefaultTableModel tm = (DefaultTableModel) tablePays.getModel();
        if (tablePays.getSelectedRowCount() > 0) {
            int id = (Integer) tm.getValueAt(tablePays.getSelectedRow(), 0);
            paymentController.delete(id);
            populateTable();
        } else {
            try {
                paymentController.delete(0);
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de selección", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void excel(JTable table) {
        {
            JFileChooser fc = new JFileChooser();
            int option = fc.showSaveDialog(table);
            if (option == JFileChooser.APPROVE_OPTION) {
                String filename = fc.getSelectedFile().getName();
                String path = fc.getSelectedFile().getParentFile().getPath();
                int len = filename.length();
                String ext = "";
                String file = "";
                if (len > 4) {
                    ext = filename.substring(len - 4, len);
                }
                if (ext.equals(".xls")) {
                    file = path + "\\" + filename;
                } else {
                    file = path + "\\" + filename + ".xls";
                }
                try {
                    toExcel(table, new java.io.File(file));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    //Logger.getLogger(.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void toExcel(JTable tabla, java.io.File ficheroXLS) throws IOException {
        TableModel modelo = tabla.getModel();
        FileWriter fichero = new FileWriter(ficheroXLS);

        for (int i = 0; i < modelo.getColumnCount(); i++) {
            fichero.write(modelo.getColumnName(i) + "\t");
        }
        fichero.write("\n");
        for (int i = 0; i < modelo.getRowCount(); i++) {
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fichero.write(modelo.getValueAt(i, j).toString() + "\t");
            }
            fichero.write("\n");
        }
        fichero.close();
    }

    public void utilJTablePrint(JTable jTable, String header, String footer, boolean showPrintDialog) {
        boolean fitWidth = true;
        boolean interactive = true;
        // We define the print mode (Definimos el modo de impresión)
        JTable.PrintMode mode = fitWidth ? JTable.PrintMode.FIT_WIDTH : JTable.PrintMode.NORMAL;
        try {
            // Print the table (Imprimo la <span id="IL_AD1" class="IL_AD">tabla</span>)
            boolean complete = jTable.print(mode,
                    new MessageFormat(header),
                    new MessageFormat(footer),
                    showPrintDialog,
                    null,
                    interactive);
            if (complete) {
                // Mostramos el mensaje de impresión existosa
                JOptionPane.showMessageDialog(jTable,
                        "Print complete (Impresión completa)",
                        "Print result (Resultado de la impresión)",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Mostramos un mensaje indicando que la impresión fue cancelada
                JOptionPane.showMessageDialog(jTable,
                        "Print canceled (Impresión cancelada)",
                        "Print result (Resultado de la impresión)",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(jTable,
                    "Print fail (Fallo de impresión): " + pe.getMessage(),
                    "Print result (Resultado de la impresión)",
                    JOptionPane.ERROR_MESSAGE);
        }
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
        rootPane = new JPanel();
        rootPane.setLayout(new GridLayoutManager(12, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("Cliente: ");
        rootPane.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Prenda:");
        rootPane.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Monto: ");
        rootPane.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Tipo:");
        rootPane.add(label4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Fecha de Empeño: ");
        rootPane.add(label5, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Estado:");
        rootPane.add(label6, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customerField = new JTextField();
        rootPane.add(customerField, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        pledgeField = new JTextField();
        rootPane.add(pledgeField, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        amountField = new JTextField();
        rootPane.add(amountField, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        typeField = new JTextField();
        rootPane.add(typeField, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        dateField = new JTextField();
        rootPane.add(dateField, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        statusField = new JTextField();
        rootPane.add(statusField, new GridConstraints(5, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Interés/ Monto a Pagar:");
        rootPane.add(label7, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        interestField = new JTextField();
        rootPane.add(interestField, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addField = new JTextField();
        rootPane.add(addField, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Nuevo Registro de Pago:");
        rootPane.add(label8, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        payField = new JTextField();
        rootPane.add(payField, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addButton = new JButton();
        addButton.setIcon(new ImageIcon(getClass().getResource("/icons/addition-sign.png")));
        addButton.setText("Agregar");
        rootPane.add(addButton, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        rootPane.add(scrollPane1, new GridConstraints(8, 1, 4, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablePays = new JTable();
        scrollPane1.setViewportView(tablePays);
        deleteButton = new JButton();
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/icons/minus-sign.png")));
        deleteButton.setText("Eliminar");
        rootPane.add(deleteButton, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        printButton = new JButton();
        printButton.setIcon(new ImageIcon(getClass().getResource("/icons/printer.png")));
        printButton.setText("Imprimir ");
        rootPane.add(printButton, new GridConstraints(11, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exportButton = new JButton();
        exportButton.setIcon(new ImageIcon(getClass().getResource("/icons/spreadsheet-cell-row.png")));
        exportButton.setText("Exportar a Excel");
        rootPane.add(exportButton, new GridConstraints(10, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPane;
    }
}