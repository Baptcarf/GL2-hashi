
-- Table des informations de l'utilisateur
CREATE TABLE Utilisateur(
        id_utilisateur INTEGER PRIMARY KEY AUTOINCREMENT,
        pseudo VARCHAR(8) NOT NULL UNIQUE, 
        couleur VARCHAR(7) NOT NULL, 		-- La couleur du compte
        id_avancement_tutoriel INT NOT NULL 	-- Le niveau de tutoriel atteint par l'utilisateur
);

-- Table des grilles présent dans le jeu
CREATE TABLE Grille(
        id_grille INTEGER PRIMARY KEY AUTOINCREMENT,
        grille Text NOT NULL, 			-- le nom de la grille
        nbIle INT NOT NULL,
        numeroGrille INT NOT NULL
);


-- Table des parties 
CREATE TABLE Partie(
        id_partie INTEGER PRIMARY KEY AUTOINCREMENT,
        id_utilisateur BIGINT,
        id_grille BIGINT,
        statut INTEGER NOT NULL, 		-- Le statut de la partie (0 pas commencé, 1 en cours, 2 terminé)
        score REAL, 				-- Le score de l'utilisateur si la partie est terminé
        maxScore REAL,
        finiUneFois boolean,
        FOREIGN KEY (id_utilisateur)
            REFERENCES Utilisateur(id_utilisateur)
            ON DELETE CASCADE,
        FOREIGN KEY (id_grille)
            REFERENCES Grille(id_grille)
            ON DELETE CASCADE
);


CREATE TABLE Coup(
        id_partie BIGINT,
        num_coup BIGINT, 		-- Le numéro du coûp
        node_dep INTEGER, 		-- le noeud de départ de l'arréte
        node_arr INTEGER, 		-- le noeud d'arrivé de l'arréte
        val_coup_avant INTEGER, 	-- la valeur de l'arréte (0 : vide, 1 : 1 arrêtes, 2 : 2 arrêtes)
        val_coup_apres INTEGER, 	-- la valeur de l'arréte (0 : vide, 1 : 1 arrêtes, 2 : 2 arrêtes)
        mode_coup INTEGER,
        erreur boolean,
        PRIMARY KEY(id_partie,num_coup),
        FOREIGN KEY (id_partie)
            REFERENCES Partie(id_partie)
            ON DELETE CASCADE
);
