package Ejercicio1_SQL.Vista;

import Ejercicio1_SQL.Controladores.GestorComponente;
import Ejercicio1_SQL.Controladores.GestorComputadora;
import Ejercicio1_SQL.Modelo.Componente;
import Ejercicio1_SQL.Modelo.Computadora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UIComputadora {
    private JPanel pComputadoras;
    private JLabel letCodigo;
    private JLabel letMarca;
    private JLabel letModelo;
    private JLabel letComponentes;
    private JLabel letNombre;
    private JLabel letNroSerie;
    private JButton btnBuscar;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtNombre;
    private JTextField txtNroSerie;
    private JButton btnAgregar;
    private JButton btnQuitar;
    private JButton btnGuardar;
    private JButton btnlimpiar;
    private JButton btnEliminar;
    private JTable tableComponentes;
    private JTextField txtCodigo;
    private DefaultTableModel tableModel;
    private GestorComputadora gestorComputadora;
    private GestorComponente gestorComponente;

    public UIComputadora() throws SQLException {
        // Inicializar controladores
        gestorComputadora = new GestorComputadora();
        gestorComponente = new GestorComponente();

        // Inicializar componentes
        createUIComponents();
        setupListeners();
    }

    private void createUIComponents() {
        // Agregar los componentes al panel
        pComputadoras = new JPanel();
        pComputadoras.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        //Etiqueta y campo código
        letCodigo = new JLabel("Código:");
        gbc.gridx = 1;
        gbc.gridy = 0;
        letCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
        pComputadoras.add(letCodigo, gbc);

        // ComboBox para códigos
        txtCodigo = new JTextField(15);
        gbc.gridx = 2;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pComputadoras.add(txtCodigo, gbc);


        // Botón buscar
        btnBuscar = new JButton("Buscar");
        gbc.gridx = 6;
        gbc.gridwidth = 1;
        pComputadoras.add(btnBuscar, gbc);

        // Botón limpiar
        btnlimpiar = new JButton("Limpiar");
        gbc.gridx = 7;
        gbc.gridwidth = 1;
        pComputadoras.add(btnlimpiar, gbc);

        // Etiqueta y campo de marca
        letMarca = new JLabel("Marca:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        letMarca.setHorizontalAlignment(SwingConstants.RIGHT);
        pComputadoras.add(letMarca, gbc);

        txtMarca = new JTextField(15);
        gbc.gridx = 2;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pComputadoras.add(txtMarca, gbc);

        // Etiqueta y campo de modelo
        letModelo = new JLabel("Modelo:");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        letModelo.setHorizontalAlignment(SwingConstants.RIGHT);
        pComputadoras.add(letModelo, gbc);

        txtModelo = new JTextField(15);
        gbc.gridx = 2;
        gbc.gridwidth = 4;
        pComputadoras.add(txtModelo, gbc);

        // Etiqueta y campo de Componente
        letComponentes = new JLabel("Componentes:");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        letComponentes.setHorizontalAlignment(SwingConstants.RIGHT);
        pComputadoras.add(letComponentes, gbc);

        // Etiqueta y campo de nombre
        letNombre = new JLabel("Nombre:");
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        pComputadoras.add(letNombre, gbc);

        txtNombre = new JTextField(15);
        gbc.gridx = 3;
        pComputadoras.add(txtNombre, gbc);

        // Etiqueta y campo de número de serie
        letNroSerie = new JLabel("Nro Serie:");
        gbc.gridx = 4;
        gbc.gridy = 4;
        pComputadoras.add(letNroSerie, gbc);

        txtNroSerie = new JTextField(15);
        gbc.gridx = 5;
        pComputadoras.add(txtNroSerie, gbc);

        // Botones para agregar y quitar componentes
        btnAgregar = new JButton("Agregar Componente");
        gbc.gridy = 5;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        pComputadoras.add(btnAgregar, gbc);

        btnQuitar = new JButton("Quitar Componente");
        gbc.gridx = 4;
        gbc.gridwidth = 2;
        pComputadoras.add(btnQuitar, gbc);

        // Tabla para mostrar componentes
        String[] columnNames = {"Nombre", "Nro Serie"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableComponentes = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableComponentes);
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        pComputadoras.add(scrollPane, gbc);

        // Botones para guardar y eliminar
        btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        pComputadoras.add(btnGuardar, gbc);

        btnEliminar = new JButton("Eliminar");
        gbc.gridx = 4;
        gbc.gridwidth = 3;
        pComputadoras.add(btnEliminar, gbc);
    }

    private void setupListeners() {
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarComputadora();
            }
        });
        btnlimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarComponente();
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

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarDatos();
            }
        });
    }

    private void buscarComputadora() {
        String codigo = txtCodigo.getText();
        gestorComputadora = new GestorComputadora();
        try {
            Computadora computadora = gestorComputadora.obtenerComputadora(codigo);
            if (computadora != null) {
                Long idComputadora = computadora.getId();
                txtMarca.setText(computadora.getMarca());
                txtModelo.setText(computadora.getModelo());
                cargarComponentes(gestorComputadora.obtenerComponentes(idComputadora));
            } else {
                JOptionPane.showMessageDialog(pComputadoras, "No se encontró una computadora con el código proporcionado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pComputadoras, "Error al buscar computadora: " + e.getMessage());
        }
    }

    private void cargarComponentes(List<Componente> componentes) {
        tableModel.setRowCount(0);
        for (Componente componente : componentes) {
            tableModel.addRow(new Object[]{componente.getNombre(), componente.getNroSerie()});
        }
    }

    private void agregarComponente() {
        String nombre = txtNombre.getText().trim();
        String nroSerie = txtNroSerie.getText().trim();
        String codigoComputadora = txtCodigo.getText().trim();

        if (nombre.isEmpty() || nroSerie.isEmpty()) {
            JOptionPane.showMessageDialog(pComputadoras, "Por favor, complete los campos de Nombre y Nro Serie.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        } else{
            try {
                Computadora computadoraAsociada = gestorComputadora.obtenerComputadora(codigoComputadora);
                if (computadoraAsociada == null) {
                    guardarDatos();
                }
                List<Componente> listaComponentes=gestorComputadora.obtenerComponentes(computadoraAsociada.getId());
                boolean agregar = true;
                for (Componente c : listaComponentes) {
                    if (c.getNroSerie().toString().compareTo(nroSerie) == 0) {
                        JOptionPane.showMessageDialog(pComputadoras, "Este componente ya se encuentra agregado a la computadora.", "Componente existente", JOptionPane.WARNING_MESSAGE);
                        agregar = false;
                        break;
                    }
                }
                if(agregar) {
                    Componente nuevoComponente = new Componente();
                    nuevoComponente.setNombre(nombre);
                    nuevoComponente.setNroSerie(nroSerie);
                    nuevoComponente.setComputadora(computadoraAsociada);
                    gestorComponente.agregarComponente(nuevoComponente);
                    tableModel.addRow(new Object[]{nombre, nroSerie});
                    JOptionPane.showMessageDialog(pComputadoras, "Componente agregado correctamente.");
                    txtNombre.setText("");
                    txtNroSerie.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(pComputadoras, "Error al agregar componente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void quitarComponente() {
        int selectedRow = tableComponentes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(pComputadoras, "Por favor, seleccione un componente para quitar.", "Seleccionar Componente", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String nroSerie = (String) tableModel.getValueAt(selectedRow, 1);
        try {
            Componente componenteAEliminar = gestorComponente.obtenerComponente(nroSerie);
            if (componenteAEliminar != null) {
                gestorComponente.borrarComponente(componenteAEliminar.getId().toString());
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(pComputadoras, "Componente eliminado correctamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pComputadoras, "Error al quitar componente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void guardarDatos() {
        String codigo = txtCodigo.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String nroSerie = txtNroSerie.getText().trim();
        if (codigo.isEmpty() || marca.isEmpty() || modelo.isEmpty()) {
            JOptionPane.showMessageDialog(pComputadoras, "Por favor, complete todos los campos (Código, Marca, Modelo) antes de guardar.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                Computadora computadora = new Computadora();
                computadora.setCodigo(codigo);
                computadora.setMarca(marca);
                computadora.setModelo(modelo);
                gestorComputadora.agregarComputadora(computadora);
                if(!nombre.isEmpty() || !nroSerie.isEmpty()) {
                    agregarComponente();
                }
                JOptionPane.showMessageDialog(pComputadoras, "Datos guardados correctamente.");
                limpiarCampos();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(pComputadoras, "Error al guardar datos: " + e.getMessage());
            }
        }
    }

    private void eliminarDatos() {
        String codigo = txtCodigo.getText();
        try {
            gestorComputadora.borrarComputadora(codigo);
            limpiarCampos();
            JOptionPane.showMessageDialog(pComputadoras, "Datos eliminados correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pComputadoras, "Error al eliminar datos: " + e.getMessage());
        }
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
        frame.setContentPane(ui.pComputadoras);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
