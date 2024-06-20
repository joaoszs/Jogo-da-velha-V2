CREATE TABLE IF NOT EXISTS Jogador (
    IdJogador INTEGER PRIMARY KEY,
    Nome TEXT,
    Email TEXT
);

CREATE TABLE IF NOT EXISTS Partida (
    IdPartida INTEGER PRIMARY KEY,
    DataHora text,
    Simbolo TEXT
);

CREATE TABLE IF NOT EXISTS Partida_Jogador (
    fk_Partida_IdPartida INTEGER,
    fk_Jogador_IdJogador INTEGER,
    Vencedor BOOLEAN,
    IdStatus INTEGER PRIMARY KEY,
    FOREIGN KEY (fk_Partida_IdPartida) REFERENCES Partida (IdPartida) ON DELETE RESTRICT,
    FOREIGN KEY (fk_Jogador_IdJogador) REFERENCES Jogador (IdJogador) ON DELETE RESTRICT
);