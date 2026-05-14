# Gestion de stock
1. Persistence
   1. Base (Postgres)
      1. Schemas
        * produits (generaliser) => Fifo, Lifo ou CUMP
        * mouvements_stocks
        * type
        * etats_stocks
      2. Views
2. Acces Donnee(Data Access)
   1. Entite/Models
       * type
       * mouvements_stocks
       * etat_stokcks
       * produits(object) 
   2. Repositories
3. Metier
   1. Services
   2. Regles
   3. Validation
4. Presentation
   1. Controllers
   2. Affichage