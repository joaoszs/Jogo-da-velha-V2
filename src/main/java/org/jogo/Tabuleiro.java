package org.jogo;

public class Tabuleiro
{
    private String[][] matriz = {
            {"7", "8", "9"},
            {"4", "5", "6"},
            {"1", "2", "3"}
    };

    public void exibirTabuleiro()
    {
        System.out.println("\n" +
                matriz[0][0] + " " + matriz[0][1] + " " + matriz[0][2] + "\n" +
                matriz[1][0] + " " + matriz[1][1] + " " + matriz[1][2] + "\n" +
                matriz[2][0] + " " + matriz[2][1] + " " + matriz[2][2]);
    }

    public boolean definirPosicao(int posicao, String jogador)
    {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matriz[i][j].equals(String.valueOf(posicao))) {
                    matriz[i][j] = jogador;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean verificarVitoria(String jogador) {
        String linha1 = matriz[0][0] + matriz[0][1] + matriz[0][2];
        String linha2 = matriz[1][0] + matriz[1][1] + matriz[1][2];
        String linha3 = matriz[2][0] + matriz[2][1] + matriz[2][2];

        String coluna1 = matriz[0][0] + matriz[1][0] + matriz[2][0];
        String coluna2 = matriz[0][1] + matriz[1][1] + matriz[2][1];
        String coluna3 = matriz[0][2] + matriz[1][2] + matriz[2][2];

        String diagonal1 = matriz[0][0] + matriz[1][1] + matriz[2][2];
        String diagonal2 = matriz[0][2] + matriz[1][1] + matriz[2][0];

        return linha1.equals(jogador + jogador + jogador) ||
                linha2.equals(jogador + jogador + jogador) ||
                linha3.equals(jogador + jogador + jogador) ||
                coluna1.equals(jogador + jogador + jogador) ||
                coluna2.equals(jogador + jogador + jogador) ||
                coluna3.equals(jogador + jogador + jogador) ||
                diagonal1.equals(jogador + jogador + jogador) ||
                diagonal2.equals(jogador + jogador + jogador);
    }

    public boolean estaCheio()
    {
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (!matriz[i][j].equals("X") && !matriz[i][j].equals("O"))
                {
                    return false;
                }
            }
        }
        return true;
    }
}