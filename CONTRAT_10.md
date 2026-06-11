# Équipe : 10
# Jeu :Gradius

# Contrat concernant le moteur de jeu

## Partie Modèle

### Géometrie
* [ ] __monde torique__
  + Explications: [...]
  + Démo : [...] 

* [X] __défilement / scrolling__
  + Explications: [défilement automatique vers la droite]
  + Démo : [...] 
 
* [ ] __taille de la map modifiable ?__
  + Explications: [...]
  + Démo : [...]

* [X] __bords du monde__ 
  + Explications: [codage ? comportement: le bord gauche poussera le joueur mais pas les ennemie]
  + Démo : [...]


### Génération de la map

* [X] __fixe__ :
  + Explications: [? sous quelle forme est-elle décrite ?] 
  + Démo : [...]

* [ ] __par configuration__ :
  + Explications: [? sous quelle forme est-elle décrite ?] 
  + Démo : [...]

* [ ] __aléatoire__
  + Explications: [? déclenchée par ?]
  + Démo : [...]

* [ ] __aléatoire avec mémorisation__
  + Explications: [? en cas de retour en arrière ?]
  + Démo : [...]


### Actions

* [X] __annulation d'une action__
  + Explications: [Une attaque chargé annulable pendant le chargement]
  + Démo: [...]

* [ ] __résolution de conflit lorsque deux entités veulent atteindre la même case__
  + Explications: [...]
  + Démo: [...]

### Affichage et animation 

* [X]  __barre de santé / points de vie de chaque personnage__
  + Explications: [3 vies pour le joueur]
  + Démo : [...]

* [ ] __animation lors de la création / apparition__
  + Explications: [...]
  + Démo : [...]

* [X] __animation lors de la destruction / disparition__
  + Explications: [clignotement du joueur si touché et explosions de toutes les entités lors de la mort]
  + Démo : [...]

* [X] __animation en fonction de l'état du Bot (Waiting, Resting, ...)__
  + Explications: []
  + Démo : [...]

* [X] __animations / affichage spécifiques__
  + Explications: [Lorsque l'on monte ou descend, l'avion penche]
  + Démo : [...]


#### Adaptabilité = changement pendant le jeu
* [x] changement de Stunt 
  + Explications: [...]
  + Démo: changement de façon de faire une action : [...] 

* [x] changement d'avatar 
  + Explications: [...]
  + Démo : changement d'image : [...] 

* [x] changement de bot/FSM 
  + Explications: [...]
  + Démo : changement de comportement : [...]

      
## Partie Vue

  * [] view port centré sur le joueur
    * [ ] zoom
    * [X] scrolling
  

## Mode debug

  * [x] affichage du temps entre deux _tick_ (min, max, moyenne)
  * [x] affichage du temps entre deux _paint_ (min, max, moyenne)
  * [x] affichage du nombre de _FPS_ (min, max, moyenne)
  * [x] gestion de la charge 
    + Démo: maximum d'entités actives : [...]  
      
  * [x] affichage de l'action au dessus du personnage
  * [x] affichage des bounding box    

## Partie Controlleur

#### Bot
  * [ ] Bot en Java
  * [ ] Bot en FSM Java
  * [ ] Bot en GAL + parser
  * [ ] Démo : modification du comportement des entités 
      * [ ] en changeant de classe Bot 
      * [ ] en changeant de FSM Java
      * [ ] en changeant le fichier `.gal` 

## Partie Stunt : Action unique _versus_ actions multiples simultanées 

  * [x] une action à la fois  
  * [x] actions multiples (ex: sauter+tourner, avancer+tourner) 
  * [] gestion des actions compatibles/incompatibles

# Contrat spécificique au jeu choisi
 
 1 niveau avec un boss de fin.

Rester appuyer sur le bouton d'attaque, attaque continuellement.
Frames d'invincibilité pour le joueur si touché et pv>1.
Une attaque chargée annulable.
Des power-ups aléatoires accumulables.

Ennemis:
-Un ennemis avec une attaque à téte-chercheuse
-Un ennemi qui tire un projectile qui attire le joueur si il est dans une zone autour de celui-ci-> fais faire des petite translation au joueurs (de plus en plus grande plus le joueur est proche).
-Un type d'ennemi qui créé plus d'ennemis.
-Un type d'ennemi qui marche sur les parois et tire sur le joueur
-Un type d'ennemi qui se positionne en dessous ou au dessus du joueur puis tir vers le haut ou le bas (en fonction d'où est le joueur).
-Un type d'ennemi kamikaze
