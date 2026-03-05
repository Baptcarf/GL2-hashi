
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
        grille String NOT NULL, -- le nom de la grille
        nbIle INT NOT NULL
);


-- Table des parties 
CREATE TABLE Partie(
        id_partie INTEGER PRIMARY KEY AUTOINCREMENT,
        id_utilisateur BIGINT,
        id_grille BIGINT,
        statut INT NOT NULL, -- Le statut de la partie (0 pas commencé, 1 en cours, 2 terminé)
        score INT , -- Le score de l'utilisateur si la partie est terminé
        sauvegarde BLOB, -- le fichier contenant la sauvegarde de la partie du joueur
        FOREIGN KEY (id_utilisateur)
            REFERENCES Utilisateur(id_utilisateur)
            ON DELETE CASCADE,
        FOREIGN KEY (id_grille)
            REFERENCES Grille(id_grille)
            ON DELETE CASCADE
);


CREATE TABLE Coup(
        id_partie BIGINT,
        num_coup BIGINT, -- Le numéro du coûp
        node_dep Int, -- le noeud de départ de l'arréte
        node_arr Int, -- le noeud d'arrivé de l'arréte
        val_coup_avant INTEGER, -- la valeur de l'arréte (0 : vide, 1 : 1 arrêtes, 2 : 2 arrêtes)
        val_coup_apres INTEGER, -- la valeur de l'arréte (0 : vide, 1 : 1 arrêtes, 2 : 2 arrêtes)
        PRIMARY KEY(id_partie,num_coup),
        FOREIGN KEY (id_partie)
            REFERENCES Partie(id_partie)
            ON DELETE CASCADE
);
