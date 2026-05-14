create table types(
    id int PRIMARY KEY generated always as IDENTITY,
    libeller TEXT not null unique
);
create table produits(
    id int PRIMARY key generated always as IDENTITY,
    nom_produit TEXT not NULL,
    type_id int not NULL check(type_id > 0),
    constraint fk_produit
    Foreign Key (type_id) REFERENCES types(id)
    on delete CASCADE
);
create table mouvements_stock(
    id int PRIMARY KEY generated always as IDENTITY,
    date_mouvement date not null,
    produit_id int not null check(produit_id  > 0),
    quantite NUMERIC(10,2) not NULL,
    prix_unitaire NUMERIC(10,2) not NULL check(prix_unitaire >= 0),
    type_id int not null,
    source int,
    constraint fk_mouvement
    Foreign Key (produit_id) REFERENCES produits(id) on delete CASCADE,
    Foreign Key (type_id) REFERENCES types(id) on delete CASCADE
);
create table etats_stock(
    id int primary key generated always as IDENTITY,
    produit_id int not NULL check(produit_id > 0),
    date_etat date not NULL,
    stock NUMERIC(10,2) not NULL check(stock >= 0),
    valeur_stock NUMERIC(10,2) not null check(valeur_stock >= 0),
    constraint fk_etat
    Foreign Key (produit_id) REFERENCES produits(id)
);