SET search_path TO contacts;


-- Schéma

DROP SCHEMA IF EXISTS contacts CASCADE;
CREATE SCHEMA contacts;


-- Tables


CREATE TABLE compte (
	IdCompte		INT			 	NOT NULL	GENERATED BY DEFAULT AS IDENTITY,
	Pseudo			VARCHAR(25)		NOT NULL,
	MotDePasse		VARCHAR(25) 	NOT NULL,
	Email			VARCHAR(100)	NOT NULL,
	PRIMARY KEY (IdCompte)
);
CREATE UNIQUE INDEX idx_compte_pseudo ON compte (Pseudo ASC);

CREATE TABLE role (
	IdCompte		INT				NOT NULL,
	Role			VARCHAR(20)		NOT NULL,
	CHECK( Role IN ('ADMINISTRATEUR','UTILISATEUR') ),	
	FOREIGN KEY (IdCompte) REFERENCES compte (IdCompte),
	PRIMARY KEY (IdCompte, Role)
);

CREATE TABLE lettre (
	IdLettre		INT				NOT NULL	GENERATED BY DEFAULT AS IDENTITY,
	Titre		VARCHAR(25)		NOT NULL,
	IdCompte     INT           NOT NULL,
	FOREIGN KEY (IdCompte) REFERENCES compte (IdCompte),
	PRIMARY KEY (IdLettre)
);



CREATE TABLE categorie (
	IdCategorie		INT				NOT NULL	GENERATED BY DEFAULT AS IDENTITY,
	Libelle			VARCHAR(25)		NOT NULL,
	PRIMARY KEY (IdCategorie)
);

CREATE TABLE personne (
	IdPersonne		INT				NOT NULL	GENERATED BY DEFAULT AS IDENTITY,
	IdCategorie		INT				NOT NULL,
	Nom				VARCHAR(25)  	NOT NULL,
	Prenom			VARCHAR(25) 	NOT NULL,
	FOREIGN KEY (IdCategorie) REFERENCES categorie (IdCategorie),
	PRIMARY KEY (IdPersonne)
);

CREATE TABLE telephone (
	IdTelephone		INT				NOT NULL	GENERATED BY DEFAULT AS IDENTITY,
	IdPersonne		INT			 	NOT NULL,
	Libelle			VARCHAR(25)		NOT NULL,
	Numero			VARCHAR(25)		NOT NULL,
	FOREIGN KEY (IdPersonne) REFERENCES personne (IdPersonne),
	PRIMARY KEY (IdTelephone)
);

   CREATE TABLE abonner (
    IdLettre        INT             NOT NULL,
    IdCategorie     INT             NOT NULL,
    
    FOREIGN KEY (IdLettre) REFERENCES lettre (IdLettre),
    FOREIGN KEY (IdCategorie) REFERENCES categorie (IdCategorie),
    
    PRIMARY KEY (IdLettre, IdCategorie)
);



