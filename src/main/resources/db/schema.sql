
-- Table des informations de l'utilisateur
CREATE TABLE Utilisateur(
        id_utilisateur INTEGER PRIMARY KEY AUTOINCREMENT,
        pseudo VARCHAR(50) NOT NULL UNIQUE, 
        couleur VARCHAR(7) NOT NULL -- La couleur du compte
);

-- Table des grilles présent dans le jeu
CREATE TABLE Grille(
        id_grille INTEGER PRIMARY KEY AUTOINCREMENT,
        niveau INT NOT NULL, -- Le niveau de difficulté de la grille
        grille_dep BLOB NOT NULL -- la grille de départ
);


-- Table des parties 
CREATE TABLE Partie(
        id_utilisateur BIGINT REFERENCES Utilisateur(id_utilisateur),
        id_grille BIGINT REFERENCES Grille(id_grille),
        statut INT NOT NULL, -- Le statut de la partie (0 pas commencé, 1 en cours, 2 terminé)
        score INT , -- Le score de l'utilisateur si la partie est terminé
        sauvegarde BLOB, -- le fichier contenant la sauvegarde de la partie du joueur
        PRIMARY KEY (id_utilisateur, id_grille)
);