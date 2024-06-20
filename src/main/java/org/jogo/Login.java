package org.jogo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JButton btnLogin;

    public static void main(String[] args)
    {
        new Login();
    }

    public Login()
    {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        txtNome = new JTextField();
        txtEmail = new JTextField();
        btnLogin = new JButton("Login");

        add(new JLabel("Nome:"));
        add(txtNome);
        add(new JLabel("E-mail:"));
        add(txtEmail);
        add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nome = txtNome.getText();
                String email = txtEmail.getText();
                if (!nome.isEmpty() && !email.isEmpty()) {
                    JogoDaVelha jogo = new JogoDaVelha(nome, email);
                    jogo.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Nome e e-mail sao obrigatorios para jogar.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }
}