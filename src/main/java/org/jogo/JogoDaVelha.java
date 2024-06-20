package org.jogo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class JogoDaVelha extends JFrame implements ActionListener
{
    private Tabuleiro tabuleiro;
    private Jogador jogadorX;
    private Jogador jogadorO;
    private Jogador jogadorAtual;
    private JButton[] botoes;
    private JLabel mensagem;
    private DatabaseManager dbManager;

    private String nomeJogador;
    private String emailJogador;

    public JogoDaVelha(String nomeJogador, String emailJogador)
    {
        this.nomeJogador = nomeJogador;
        this.emailJogador = emailJogador;

        tabuleiro = new Tabuleiro();
        jogadorX = new Jogador("X");
        jogadorO = new Jogador("O");
        jogadorAtual = jogadorX;
        botoes = new JButton[9];
        mensagem = new JLabel("Jogador 'X' faça sua jogada!");
        dbManager = new DatabaseManager();

        setTitle("Jogo da Velha");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel painelTabuleiro = new JPanel();
        painelTabuleiro.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++)
        {
            botoes[i] = new JButton();
            botoes[i].setFont(new Font("Arial", Font.PLAIN, 60));
            botoes[i].setFocusPainted(false);
            botoes[i].addActionListener(this);
            painelTabuleiro.add(botoes[i]);
        }

        add(painelTabuleiro, BorderLayout.CENTER);
        add(mensagem, BorderLayout.SOUTH);

        setVisible(true);

        registrarJogador(nomeJogador, emailJogador);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton botaoClicado = (JButton) e.getSource();
        int posicao = -1;

        for (int i = 0; i < 9; i++)
        {
            if (botaoClicado == botoes[i])
            {
                posicao = i + 1;
                break;
            }
        }

        if (posicao != -1 && tabuleiro.definirPosicao(posicao, jogadorAtual.getSimbolo()))
        {
            botaoClicado.setText(jogadorAtual.getSimbolo());
            if (tabuleiro.verificarVitoria(jogadorAtual.getSimbolo())) {
                mostrarMensagem("Jogador " + jogadorAtual.getSimbolo() + " VENCEU!!");
                registrarVitoria(jogadorAtual.getSimbolo());
                desativarBotoes();
                dbManager.closeConnection();
            } else if (tabuleiro.estaCheio()) {
                mostrarMensagem("!!!!! VELHA !!!!!");
                desativarBotoes();
                dbManager.closeConnection();
            } else {
                jogadorAtual = (jogadorAtual == jogadorX) ? jogadorO : jogadorX;
                mensagem.setText("Jogador '" + jogadorAtual.getSimbolo() + "' faça sua jogada!");
            }
        }
    }

    private void desativarBotoes()
    {
        for (JButton botao : botoes)
        {
            botao.setEnabled(false);
        }
    }

    private void mostrarMensagem(String mensagem)
    {
        JOptionPane.showMessageDialog(this, mensagem, "Resultado do Jogo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void registrarJogador(String nome, String email)
    {
        String sql = "INSERT INTO Jogador (Nome, Email) VALUES (?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registrarVitoria(String jogador)
    {
        String sql = "INSERT INTO Partida (DataHora, Simbolo) VALUES (?, ?)";
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, new Timestamp(new Date().getTime()));
            pstmt.setString(2, jogador);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
