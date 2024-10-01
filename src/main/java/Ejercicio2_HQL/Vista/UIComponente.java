package Ejercicio2_HQL.Vista;

import Ejercicio2_HQL.Modelo.Componente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIComponente extends JDialog {
    private JPanel panel1;
    private JLabel letComponente;
    private JLabel letNombre;
    private JLabel letNroSerie;
    private JTextField txtNombre;
    private JTextField txtNroSerie;
    private JButton agregarButton;
    private JTextField textField1;
    private JTextField textField2;
    private Componente componente;  // Componente a agregar
    private boolean agregado;  // Indica si se agregó un componente

    public UIComponente(Frame parent) {
        super(parent, "Agregar Componente", true);
        componente = new Componente();
        agregado = false;
        createUIComponents();
        setupListeners();
        setSize(500, 200);
        setLocationRelativeTo(parent);
    }

    private void createUIComponents() {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        letComponente = new JLabel("COMPONENTE:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        letComponente.setHorizontalAlignment(SwingConstants.RIGHT);
        panel1.add(letComponente, gbc);

        letNombre = new JLabel("Nombre:");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel1.add(letNombre, gbc);

        txtNombre = new JTextField(15);
        gbc.gridx = 2;
        gbc.gridwidth = 3;
        panel1.add(txtNombre, gbc);

        letNroSerie = new JLabel("Nro Serie:");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel1.add(letNroSerie, gbc);

        txtNroSerie = new JTextField(15);
        gbc.gridx = 2;
        gbc.gridwidth = 3;
        panel1.add(txtNroSerie, gbc);

        agregarButton = new JButton("Agregar Componente");
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel1.add(agregarButton, gbc);

        getContentPane().add(panel1);
    }

    private void setupListeners() {
        agregarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String nroSerie = txtNroSerie.getText().trim();

                if (nombre.isEmpty() || nroSerie.isEmpty()) {
                    JOptionPane.showMessageDialog(panel1, "Por favor, complete los campos de Nombre y Nro Serie.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
                } else {
                    componente.setNombre(nombre);
                    componente.setNroSerie(nroSerie);
                    agregado = true;
                    dispose();
                }
            }
        });
    }

    public Componente getComponente() {
        return componente;
    }

    public boolean isAgregado() {
        return agregado;
    }
}
