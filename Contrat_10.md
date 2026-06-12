
**# Équipe : 10**

**# Jeu : Gradius**



**# Contrat concernant le moteur de jeu**



**## Partie Modèle**



**### Géometrie**

* [X] **\_\_monde torique en X\_\_**

  + Explications: taille du modèle en X = plusieurs scenes

**  + Démo :  le viewport affiche tout le modèle ou 1 seul scène et on voit l'effet du tore**



* [X] **\_\_défilement / scrolling\_\_**

  + Explications: [défilement automatique vers la droite]

  + Démo : [Lancer le jeu sans toucher aux commandes : la caméra avance seule] 

 

* [ ] **\_\_taille de la map modifiable ?\_\_**

  + Explications: [...]

  + Démo : [...]



* [X] **\_\_bords du monde\_\_** 

  + Explications: [codage ? comportement: le bord gauche poussera le joueur mais pas les ennemie]

  + Démo : [Le joueur arrête de se déplacer et se laisse rattraper par le bord gauche de l'écran : le vaisseau est "poussé" par la bordure sans pouvoir sortir de la vue]





**### Génération de la map**



* [] **\_\_fixe\_\_** :

  + Explications: [ N scènes que vous enchainez] 

**  + Démo : 2 petites scènes et on voit qu'on retrouve la même.**



* [ ] **\_\_par configuration\_\_** :

  + Explications: [? sous quelle forme est-elle décrite ?] 

  + Démo : [...]



* [ X] **\_\_aléatoire\_\_**

  + Explications: [on regénère la scene qui n'est pas dans le viewport]

  + Démo : [ Faire deux runs successifs : montrer que l'agencement des obstacles change d'une partie à l'autre]



* [ ] **\_\_aléatoire avec mémorisation\_\_**

  + Explications: [? en cas de retour en arrière ?]

  + Démo : [...]



**### Actions**



* [] **\_\_annulation d'une action\_\_**

  + Explications: [Une attaque chargé annulable pendant le chargement]

  + Démo: [ ]



* [ ] **\_\_résolution de conflit lorsque deux entités veulent atteindre la même case\_\_**

  + Explications: [gestion des collisions par le moteur]

  + Démo: [...]



**### Affichage et animation **



* [X]  **\_\_barre de santé / points de vie de chaque personnage\_\_**

  + Explications: [3 vies pour le joueur]

  + Démo : [Affichage des pv du joueur en haut à gauche de l'écran]



* [ ] **\_\_animation lors de la création / apparition\_\_**

  + Explications: [...]

  + Démo : [...]



* [X] **\_\_animation lors de la destruction / disparition\_\_**

  + Explications: 

      - clignotement du joueur si touché + temps d'invicibilité 

   *       - champs booléen associé à une durée qui affecte la méthode coliision
      - une animation pour l'explosion d'une entité

      - explosions de toutes les entités lors de la mort

  + Démo : [ Perdre un point de vie : le sprite du joueur clignote et traverse les ennemis sans prendre de dégâts pendant un certain temps. Détruire un ennemi ou mourir : un sprite d'explosion s'affiche à l'emplacement de l'entité.]



* [ ] **\_\_animation en fonction de l'état du Bot (Waiting, Resting, ...)\_\_**

  + Explications: []

  + Démo : [...]



* [X] **\_\_animations en fonction des actions/ affichage spécifiques\_\_**

  + Explications: [Lorsque l'on monte ou descend, l'avion penche]

  + Démo : [Appuyer sur la flèche du HAUT : le sprite du vaisseau s'incline vers le haut. Appuyer sur la flèche du BAS : il s'incline vers le bas. Revenir au neutre : il se remet à plat. ]





**#### Adaptabilité = changement pendant le jeu**



* [x] changement de Stunt 

  + Explications: changement de la façon de tirer 

  + Démo: changement de façon de faire une action : [Ramasser un bonus de tir : le tir de base (simple ligne droite) se transforme en double tir ou en tir incliné ou autre] 



* [x] changement d'avatar 

  + Explications: 

  + Démo : changement d'image. Lorsque les slaves du boss meurt ils changent d'avatars 



* [x] changement dynamique de bot/FSM 

  + Explications:

   *       - lorsque le boss perd des entités slaves, il change de comportement
  + Démo : 

   *       - le canon de l'entité master tire aléatoirement en mode cool et vise spécifiquement le joueur en mode aggressif


      

**## Partie Vue**



  * [ ] view port centré sur le joueur

  * [ ] zoom

  * [X] scrolling

       * - le viewport avance à vitesse constante
       * - le joueur se déplace librement dans le viewport et se fait pousser s'il va à l'extermité gauche
   *      - gestion fine du vecteur vitesse du joueur qui a toujours un vecteur vitesse par défaut et auqule on ajoute un boost quand le joueur appuie sur les touches déplacements.
   * 

   *     
**## Mode debug**



  * [x] affichage du temps entre deux *\_tick\_* (min, max, moyenne)

  * [x] affichage du temps entre deux *\_paint\_* (min, max, moyenne)

  * [x] affichage du nombre de *\_FPS\_* (min, max, moyenne)

  * [x] gestion de la charge 

    + Démo: maximum d'entités actives : Spawner EGG  

      

  * [x] affichage de l'action et l'état au dessus du bot

  * [x] affichage des bounding box    



**## Partie Controlleur**



**#### Bot**

  * [ ] Bot en Java

  * [x] Bot en FSM Java

  * [?] Bot en GAL + parser

  * [ ] Démo : modification du comportement des entités 

      * [ ] en changeant de classe Bot 

      * [x] en changeant de FSM Java

      * [?] en changeant le fichier `.gal` 



**## Partie Stunt : Action unique ***\_versus\_*** actions multiples simultanées **



  * [ ] une action à la fois  

  * [x] actions multiples (déplacement  + tir) 

  * [ ] gestion des actions compatibles/incompatibles



**# Contrat spécificique au jeu choisi**

 

 [X] Bonus et extensions du vaisseau

   *  - change le tir (double tir, angle différent)
   * - entité double : autre entité (slave) avec l'avatar vaisseau + copie la décision du vaisseau master
   * => entité composite ?
   * + Démo : en mode debug on voit les deux hitbox du master et du slave
   *  
 [ ] Boss ultra simple

   *  - une seule entité avec une grosse image
   * 

[X] Boss standard (non articulé) = entité composite

   * - des entités slaves liées à une entité master qui prend les décisions
   * - quand l'entité master meurt elle fait disparaitre les slaves
   * 

[  ] Boss articulé ?

   * 

   * 

   * 

 1 niveau avec un boss de fin.



Rester appuyer sur le bouton d'attaque, attaque continuellement.

->Démo: on montre dans la vidéo quand restant appuyé le vaisseau attaque continuellement.

Frames d'invincibilité pour le joueur si touché et pv>1.

Des power-ups aléatoires accumulables.



Ennemis:

[x] Un ennemis avec une attaque à téte-chercheuse (GAL : Closest(P,...))

[x] Un ennemi qui tire un projectile qui attire le joueur si il est dans une zone autour de celui-ci-> fais faire des petite translation au joueurs (de plus en plus grande plus le joueur est proche).

  - par influence sur le vecteur vitesse du joueur

Démo : en mode debug on voit les vecteurs vitesses  

  

[X] Un type d'ennemi qui créé plus d'ennemis (EGG)

[X] Un type d'ennemi qui marche sur les parois et tire sur le joueur

[?] Un type d'ennemi qui se positionne en dessous ou au dessus du joueur puis tir vers le haut ou le bas (en fonction d'où est le joueur) : CLOSEST

[X] Un type d'ennemi kamikaze


