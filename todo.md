# Gestion de stock
1. Persistence
   1. Base (Postgres) [x]
      1. Schemas[x]
        * produits (generaliser) => Fifo, Lifo ou CUMP [x]
        * mouvements_stock [x]
        * type [x]
        * etat_stocks [x]
      2. Views(facultatif)
2. Acces Donnee(Data Access)
   1. Entite/Models [x]
       * type [x]
       * mouvements_stocks [x]
       * etat_stocks [x]
       * produit(object) [x]
   2. Repositories
       * repositoryGeneraliser 
3. Metier
   1. Services
      1. Un calcule de l'etat_stocks
      2. Un detail pour les sorties 
      3. Calcule du Fifo
      4. Calcule du Lifo
      5. Calcule du CUMP
   2. Regles
   3. Validation
4. Presentation
   1. Affichage
      1. Formulaire d'insertion generaliser
   2. Controllers
      1. Controller generaliser