package Ejercicio2_HQL.Vista;

import Ejercicio2_HQL.Vista.UIComponente;
import Ejercicio2_HQL.Modelo.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UIComputadora {
    private JPanel pComputerPanel;
    private JLabel letMarca;
    private JLabel letModelo;
    private JLabel letComponentes;
    private JLabel letNombre;
    private JLabel letNroSerie;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtNombre;
    private JTextField txtNroSerie;
    private JButton btnAgregar;
    private JButton btnQuitar;
    private JButton btnGuardar;
    private JTable tableComponentes;
    private JTextField txtCodigo;
    private DefaultTableModel tableModel;

    public UIComputadora() throws SQLException {
        createUIComponents();
        setupListeners();
    }

    private void createUIComponents() {
        // Agregar los componentes al panel
        pComputerPanel = new JPanel();
        pComputerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        //Etiqueta y campo código
        JLabel letCodigo = new JLabel("Código:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        letCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
        pComputerPanel.add(letCodigo, gbc);

        // ComboBox para códigos
        txtCodigo = new JTextField(15);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pComputerPanel.add(txtCodigo, gbc);


        // Etiqueta y campo de marca
        letMarca = new JLabel("Marca:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        letMarca.setHorizontalAlignment(SwingConstants.RIGHT);
        pComputerPanel.add(letMarca, gbc);

        txtMarca = new JTextField(15);
        gbc.gridx = 2;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pComputerPanel.add(txtMarca, gbc);

        // Etiqueta y campo de modelo
        letModelo = new JLabel("Modelo:");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        letModelo.setHorizontalAlignment(SwingConstants.RIGHT);
        pComputerPanel.add(letModelo, gbc);

        txtModelo = new JTextField(15);
        gbc.gridx = 2;
        gbc.gridwidth = 4;
        pComputerPanel.add(txtModelo, gbc);

        // Etiqueta y campo de Componente
        letComponentes = new JLabel("Componentes:");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        letComponentes.setHorizontalAlignment(SwingConstants.RIGHT);
        pComputerPanel.add(letComponentes, gbc);

        // Etiqueta y campo de nombre
        letNombre = new JLabel("Nombre:");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        pComputerPanel.add(letNombre, gbc);

        txtNombre = new JTextField(15);
        gbc.gridx = 3;
        pComputerPanel.add(txtNombre, gbc);

        // Etiqueta y campo de número de serie
        letNroSerie = new JLabel("Nro Serie:");
        gbc.gridx = 4;
        gbc.gridy = 4;
        pComputerPanel.add(letNroSerie, gbc);

        txtNroSerie = new JTextField(15);
        gbc.gridx = 5;
        pComputerPanel.add(txtNroSerie, gbc);

        // Botones para agregar y quitar componentes
        btnAgregar = new JButton("Agregar Componente");
        gbc.gridy = 5;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        pComputerPanel.add(btnAgregar, gbc);

        btnQuitar = new JButton("Quitar Componente");
        gbc.gridx = 4;
        gbc.gridwidth = 2;
        pComputerPanel.add(btnQuitar, gbc);

        // Tabla  componentes
        String[] columnNames = {"Nombre", "Nro Serie"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableComponentes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableComponentes);
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        pComputerPanel.add(scrollPane, gbc);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 5;
        pComputerPanel.add(btnGuardar, gbc);

    }

    private void setupListeners() {
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UIComponente dialogoComponente = new UIComponente((Frame) SwingUtilities.getWindowAncestor(pComputerPanel));
                dialogoComponente.setVisible(true);
                Componente componente = dialogoComponente.getComponente();
                boolean numeroSerieDuplicado = false;
                if (dialogoComponente.isAgregado()) {
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String nroSerieTabla = tableModel.getValueAt(i, 1).toString();
                        if (componente.getNroSerie().equals(nroSerieTabla)) {
                            numeroSerieDuplicado = true;
                            break;
                        }
                    }
                    if (!numeroSerieDuplicado) {
                        tableModel.addRow(new Object[]{componente.getNombre(), componente.getNroSerie()});
                    } else {
                        JOptionPane.showMessageDialog(pComputerPanel, "El número de serie ya ha sido agregado.", "Número de serie Existente", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        btnQuitar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitarComponente();
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDatos();
            }
        });
    }

    private void guardarDatos() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String codigo = txtCodigo.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();

        if (codigo.isEmpty() || marca.isEmpty() || modelo.isEmpty()|| tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(pComputerPanel, "Por favor, complete todos los campos antes de guardar.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
        }else{
            try {
                Computadora computadora = new Computadora();
                computadora.setCodigo(codigo);
                computadora.setMarca(marca);
                computadora.setModelo(modelo);

                List<Componente> listaComponentes = new ArrayList<>();
                for (int i = 0; i<tableModel.getRowCount(); i++) {
                    Componente componenteAgregado = new Componente();
                    componenteAgregado.setNombre(tableModel.getValueAt(i, 0).toString());
                    componenteAgregado.setNroSerie(tableModel.getValueAt(i, 1).toString());
                    componenteAgregado.setComputadora(computadora);
                    listaComponentes.add(componenteAgregado);
                }
                computadora.setComponentes(listaComponentes);

                session.save(computadora);
                transaction.commit();

                JOptionPane.showMessageDialog(pComputerPanel, "Datos guardados correctamente.");
                limpiarCampos();
            } catch (Exception e) {
                transaction.rollback();
                JOptionPane.showMessageDialog(pComputerPanel, "Error al guardar datos: " + e.getMessage());
            } finally {
                session.close();
            }
        }
    }

    private void quitarComponente() {
        int selectedRow = tableComponentes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(pComputerPanel, "Por favor, seleccione un componente para quitar.", "Seleccionar Componente", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.removeRow(selectedRow);
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtNombre.setText("");
        txtNroSerie.setText("");
        tableModel.setRowCount(0);
    }

    public static void createAndShowUI() throws SQLException {
        JFrame frame = new JFrame("Gestión de Computadoras");
        UIComputadora ui = new UIComputadora();
        frame.setContentPane(ui.pComputerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
