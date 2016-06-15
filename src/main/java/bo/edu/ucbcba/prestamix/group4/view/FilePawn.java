package bo.edu.ucbcba.prestamix.group4.view;

import bo.edu.ucbcba.prestamix.group4.controller.FileController;
import bo.edu.ucbcba.prestamix.group4.exceptions.ValidationException;
import bo.edu.ucbcba.prestamix.group4.model.File;
import bo.edu.ucbcba.prestamix.group4.model.Pawn;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import com.sun.prism.paint.*;
import com.sun.prism.paint.Color;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Vector;

public class FilePawn extends JDialog {
    private Object[] header = {"Id", "Nombre de Cliente", "Código de prenda", "Monto", "Tipo", "Fecha", "Estado"};
    private JPanel rootPanel;
    private JTable fileTable = new JTable(null, header);
    private JButton SearchFileButton;
    private JTextField SearchFileTextField;
    private JRadioButton idRadioButton;
    private JRadioButton nombreClienteRadioButton;
    private JRadioButton codigoPrendaRadioButton;
    private JRadioButton montoRadioButton;
    private JRadioButton tipoRadioButton;
    private JRadioButton fechaRadioButton;
    private JRadioButton estadoRadioButton;
    private JButton printButton;
    private JButton exportButton;
    private final FileController fileController;


    public FilePawn(JFrame edit, Pawn p) {
        super(edit, "-ARCHIVADOS-", true);
        setContentPane(rootPanel);
        pack();
        setSize(800, 600);
        fileController = new FileController();
        save(p);


        SearchFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateSearchingTableFiles();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excel(fileTable);
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utilJTablePrint(fileTable,
                        "ARCHIVADOS (HISTORIAL)", "FIN DE PLANILLA", true);
            }
        });
    }

    public void save(Pawn p) {
        if (p != null) {
            fileController.create(p.getId(), p.getNameCustomer(), p.getCodPledge(),
                    String.valueOf(p.getAmount()), p.getType(), p.getDate(), p.getStatus());
            populateTable();
        } else {
            populateTable();
        }
    }


    public void populateSearchingTableFiles() {
        List<File> files = fileController.show();
        if (idRadioButton.isSelected()) {

            try {
                files = fileController.searchFilesById(SearchFileTextField.getText());
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (nombreClienteRadioButton.isSelected()) {
            files = fileController.searchFilesByClientName(SearchFileTextField.getText());
        }
        if (codigoPrendaRadioButton.isSelected()) {
            files = fileController.searchFilesByPledge(SearchFileTextField.getText());
        }
        if (montoRadioButton.isSelected()) {
            try {
                files = fileController.searchFilesByAmount(SearchFileTextField.getText());
            } catch (ValidationException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }

        }
        if (tipoRadioButton.isSelected()) {
            files = fileController.searchFilesByType(SearchFileTextField.getText());
        }
        if (estadoRadioButton.isSelected()) {
            files = fileController.searchFilesByStatus(SearchFileTextField.getText());
        }
        if (fechaRadioButton.isSelected()) {
            files = fileController.searchFilesByDate(SearchFileTextField.getText());
        }
        DefaultTableModel model = new DefaultTableModel(null, header);

        fileTable.setModel(model);
        for (File f : files) {
            Object[] row = new Object[7];

            row[0] = f.getId();
            row[1] = f.getNameCustomer();
            row[2] = f.getCodPledge();
            row[3] = f.getAmount();
            row[4] = f.getType();
            row[5] = f.getDate();
            row[6] = f.getStatus();
            model.addRow(row);
        }

    }


    public void populateTable() {
        List<File> files = fileController.show();
        DefaultTableModel model = new DefaultTableModel(null, header);

        fileTable.setModel(model);
        for (File f : files) {
            Object[] row = new Object[7];

            row[0] = f.getId();
            row[1] = f.getNameCustomer();
            row[2] = f.getCodPledge();
            row[3] = f.getAmount();
            row[4] = f.getType();
            row[5] = f.getDate();
            row[6] = f.getStatus();
            model.addRow(row);
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
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(3, 9, new Insets(0, 0, 0, 0), -1, -1));
        idRadioButton = new JRadioButton();
        idRadioButton.setSelected(true);
        idRadioButton.setText("Id");
        rootPanel.add(idRadioButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SearchFileTextField = new JTextField();
        rootPanel.add(SearchFileTextField, new GridConstraints(1, 3, 1, 6, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        nombreClienteRadioButton = new JRadioButton();
        nombreClienteRadioButton.setText("Nombre Cliente");
        rootPanel.add(nombreClienteRadioButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        codigoPrendaRadioButton = new JRadioButton();
        codigoPrendaRadioButton.setText("Codigo prenda");
        rootPanel.add(codigoPrendaRadioButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        montoRadioButton = new JRadioButton();
        montoRadioButton.setText("Monto");
        rootPanel.add(montoRadioButton, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tipoRadioButton = new JRadioButton();
        tipoRadioButton.setText("Tipo");
        rootPanel.add(tipoRadioButton, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        fechaRadioButton = new JRadioButton();
        fechaRadioButton.setText("Fecha");
        rootPanel.add(fechaRadioButton, new GridConstraints(2, 7, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        estadoRadioButton = new JRadioButton();
        estadoRadioButton.setText("Estado");
        rootPanel.add(estadoRadioButton, new GridConstraints(2, 8, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        rootPanel.add(scrollPane1, new GridConstraints(0, 0, 1, 9, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        fileTable = new JTable();
        fileTable.setShowHorizontalLines(false);
        scrollPane1.setViewportView(fileTable);
        SearchFileButton = new JButton();
        SearchFileButton.setText("Buscar");
        rootPanel.add(SearchFileButton, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exportButton = new JButton();
        exportButton.setText("Exportar a Excel");
        rootPanel.add(exportButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        printButton = new JButton();
        printButton.setText("Imprimir");
        rootPanel.add(printButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(idRadioButton);
        buttonGroup.add(nombreClienteRadioButton);
        buttonGroup.add(codigoPrendaRadioButton);
        buttonGroup.add(montoRadioButton);
        buttonGroup.add(tipoRadioButton);
        buttonGroup.add(fechaRadioButton);
        buttonGroup.add(estadoRadioButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}
