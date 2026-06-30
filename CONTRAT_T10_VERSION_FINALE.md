
# CONTRAT

* Équipe : 10
* Contact : Erwan.Fontaine@etu.univ-grenoble-alpes.fr
* Jeu : Gradius
* Version du 17 juin 2026 15h


# 1. Point technique à expliquer durant la démo
Au choix
* [o] réalisation du scrolling
* [o] entité composite pour le Boss et les extensions du vaisseau

# 2. Contrat spécificique au jeu choisi

#### Entité composite

* [x] Bonus et extensions du vaisseau
   * change le tir (double tir, angle différent)
   * entité double : autre entité (slave) avec l'avatar vaisseau + copie la décision du vaisseau master
     => entité composite ?
  + **Démo : en mode debug on voit les deux hitbox du master et du slave**
     
* [x] Boss standard (non articulé) = entité composite
  - des entités slaves liées à une entité master qui prend les décisions
  + **Démo : quand l'entité master meurt elle fait disparaitre les slaves**

* [ ] Boss articulé ? 

* [x] 1 niveau avec un boss de fin.

* [?] Rester appuyer sur le bouton d'attaque, attaque continuellement.
  + Démo: on montre dans la vidéo quand restant appuyé le vaisseau attaque continuellement.

* Frames d'invincibilité pour le joueur si touché et pv>1.
* Des power-ups aléatoires accumulables.

#### 5 types d'ennemis

* [x] Un ennemis avec une attaque à téte-chercheuse (GAL : Closest(P,...))

* [x] Un ennemi qui tire un projectile qui attire le joueur si il est dans une zone autour de celui-ci-> fais faire des petite translation au joueurs (de plus en plus grande plus le joueur est proche).
  + par influence sur le vecteur vitesse du joueur
  + **Démo : en mode debug on voit la composante d'attraction du vecteur vitesse du player** 
  
* [x] Un type d'ennemi qui créé plus d'ennemis (EGG)
* [x] Un type d'ennemi qui marche sur les parois et tire sur le joueur
* [?] Un type d'ennemi qui se positionne en dessous ou au dessus du joueur puis tir vers le haut ou le bas (en fonction d'où est le joueur) : CLOSEST
* [x] Un type d'ennemi kamikaze

# 3. Contrat lié au moteur de jeu
 
## Partie Modèle

### Géometrie

* [x] **monde torique en X**
  + Explications: taille du modèle en X = plusieurs scenes
  + **Démo** 
    - [x] on voit l'effet du tore : sortie d'un côté, apparition de l'autre côté
    - [x] le viewport affiche tout le modèle qui peut se réduire à une seule scène 

* [x] **défilement / scrolling**
  + Explications: 
    - Défilement automatique vers la droite.
    - Les ennemis qui sortent du viewport par la gauche meurt (disparition)
  + **Démo** 
    - [x] Lancer le jeu sans toucher aux commandes : la caméra avance seule
    - [x] afficher les 4 vecteurs vitesse du player
      * vitesse propre donnée par le contrôleur
      * vitesse du scrolling
      * effet d'attraction 
      * la somme de ces vecteurs donne le vecteur vitesse du player
 
* [x] **bords du monde** 
  + Explications:
    - le bord gauche poussera le joueur mais pas les ennemis.
  + **Démo :** Le joueur arrête de se déplacer et se laisse rattraper par le bord gauche de l'écran : le vaisseau est "poussé" par la bordure sans pouvoir sortir de la vue.

### Génération de la map

* [x] **aléatoire**
  + Explications: 
    - on regénère la partie scène qui n'est pas dans le viewport
  + Principe:
    * La génération sera aléatoire mais devra respecter des règles pour éviter que le joueur soit bloqué.
      - obstacles : 
        * sol et plafond n'ont pas de restriction mais les montagne et les obstacles au milieu doivent être suffisament espacés.
        * ou bien utilisation de motifs prédéfinis
      - ennemis : 
        * les ennemis volants peuvent apparaitre dans les airs et les ennemis terrestres apparaissent sur les parois(sol,plafond,montagne).
        * Les ennemis sont générés à intervalle régulier en dehors du viewport et à une distance minimale les uns des autres (cela permet d'éviter qu'il y ai trop d'ennemis au même endroit).
  + **Démo** 
    - [x] Faire deux parties successives : montrer que l'agencement des obstacles change d'une partie à l'autre. [#MP: Cette proposition prouve l'aléatoire mais pas le principe de génération.]
    - [?] #MP: montrer un vue plus grande que le viewport pour voir la génération

### Actions

* [x] __collisions entre entités__
  + Explications: gérées par le moteur

### Affichage et animation 

* [x]  **barre de santé / points de vie de chaque personnage**
  + Explications: 3 vies pour le joueur
  + **Démo : Affichage des pv du joueur en haut à gauche de l'écran**


* [x] **animation lors de la destruction / disparition**
  + Explications: 
    - une animation pour l'explosion d'une entité
    - explosions de toutes les entités lors de la mort? = fin du jeu ?  : Lorsque le joueur meurt,fin du jeu mais pas d'explosion de toutes les entités
  + **Démo**
    - Détruire un ennemi ou mourir : animation pour l'explosion

* [x] **animation + temps d'invicibilité**
  + Explications
    - clignotement du joueur si touché 
    - temps d'invicibilité : champs booléen associé à une durée qui affecte la méthode coliision
  + **Démo** 
    - Perdre un point de vie
    - le sprite du joueur clignote et traverse les ennemis sans prendre de dégâts pendant un certain temps. 

* [x] **animations en fonction des actions**
  + Explications: Lorsque l'on monte ou descend, l'avion penche
  + **Démo** 
    - Appuyer sur la flèche du HAUT : le sprite du vaisseau s'incline vers le haut. 
    - Appuyer sur la flèche du BAS : il s'incline vers le bas. 
    - Revenir au neutre : il se remet à plat. 

#### Adaptabilité = changement pendant le jeu

* [x] changement de Stunt 
  + Explications: changement de façon de faire une action. Typiquement, changement de la façon de tirer 
  + **Démo**
    - Ramasser un bonus de tir : le tir de base (simple ligne droite) se transforme en double tir ou en tir incliné ou autre 

* [x] changement d'avatar 
  + **Démo : Lorsque les slaves du boss meurt ils changent d'avatars**

* [x] changement dynamique de FSM 
  + Explications:
    - lorsque le boss perd des entités slaves, il change de comportement
  + **Démo**
    - le canon de l'entité master tire aléatoirement en mode cool et vise spécifiquement le joueur en mode aggressif

## Partie Vue

* [x] scrolling
  + Explications:
    - le viewport avance à vitesse constante
    - le joueur se déplace librement dans le viewport et se fait pousser s'il va à l'extermité gauche
  + Solution technique:  
    - gestion fine du vecteur vitesse du joueur qui a toujours un vecteur vitesse par défaut et auquel on ajoute un boost quand le joueur appuie sur les touches déplacements.
  + **Démo: afficher les 4 vecteurs vitesses du player (scrolling, boost, attraction + somme des 3 vecteurs)**


## Mode debug

* **Démo : affichage des données de performances dans la barre du haut du jeu**
  * [x] affichage du temps entre deux *\_tick\_* (min, max, moyenne)
  * [x] affichage du temps entre deux *\_paint\_* (min, max, moyenne)
  * [x] affichage du nombre de *\_FPS\_* (min, max, moyenne)  

* [x] gestion de la charge 
  + **Démo: un spawner qui fait Egg pour créer un maximum d'entités actives**  

* [x] affichage de l'action et l'état au dessus du bot
* [x] affichage des bounding box    
   

## Partie Controlleur

* [x] actions multiples indépendantes pour le joueur
  + Explications:
    - déplacement + tir
  + **Démo: déplacement et tir simultanés**


#### Bot

* [ ] Bot en Java
* [x] Bot en FSM Java
* [?] Bot en GAL + parser
* Démo : modification du comportement des entités 
  - [ ] en changeant de classe Bot 
  - [x] en changeant de FSM Java
  - [?] en changeant le fichier `.gal` 

### Action unique _versus_ actions multiples simultanées 

* [x] une action à la fois pour les bots 
  + Explications: déplacement ou tir
  + **Démo: comportement en boucle de déplacement puis tir**

* [?] actions multiples indépendantes pour les bots
  + Explications: déplacement et tir
  + Réalisation technique: ?
  + **Démo: déplacement et tir simultanés**