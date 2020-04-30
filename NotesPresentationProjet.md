## Infos projet Java :

- Utiliser Map (tables de hash) plutot que les List (pour améliorer la complexité)
	- Redéfinir hashcode (ISBN / EAN pourrait être une bonne clé ?)

- Types/Categories : classe Livre, classe DVD, ... factoriser avec interface, superclasses...
	- > Pour le projet, nous avons décidé de préciser les catégories pour que vous soyez moins perdus.
	- > Nous garderons donc : Bande Dessinée, Carte, CD, Vinyle, Jeu de société, Jeu vidéo, Livre, Partition, Revue, Autre

- Comment identifer un document qui fait partie d'une série ?

- Structure du starter pack modifiable


### Interface console :
Demande à l'utilisateur l'action qu'il souhaite faire (action n°4 = faire ceci, ...)
- "1"/ Ajouter une nouvelle bibliothèque
- "2"/ Ajouter document dans réseau (ISBN unique ! EAN unique ! vérifier si existe déjà)
- "3"/ Ajouter nouvel utilisateur ... capacité d'emprunter/rendre doc
- "4"/ Afficher, trier documents...


> Pour qu’il l’emprunte, on choisit nous même le moyen de rechercher le livre à emprunter ?
> Par exemple pour réserver tel livre, l’utilisateur peut rechercher via l’ISBN ? Le nom du livre/auteur ?
Réponse : Oui, (réutiliser un moyen qui existe déjà)


### Rendu :
- Diagramme UML pour montrer la structure

- Justifier diff 1er UML et 2eme UML

- Code documenté !

!!!!!!!!!!! Oral après le rendu !!!!!!!!!!!
5 min prez + 5 min questions