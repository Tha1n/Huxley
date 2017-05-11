# Huxley

## Résumé
Huxley est un bot Discord qui vise à simplifier l'organisation des sessions de jeux. Il est développé en Java et se base sur l'API [Discord4J](https://github.com/austinv11/Discord4J/tree/master/src/test/java/sx/blah/discord/examples).
Huxley est également librement inspiré du travail de [Kaysoro](https://github.com/Kaysoro) sur [Kaelly](https://github.com/Kaysoro/KaellyBot).

## Commandes

Les commandes de Huxley sont préfixés de `!`. Le préfixe est suivi de l'abbréviation de la commande.

### Huxley
Cette commande permet d'obtenir de l'aide pour les commandes d'Huxley. La commande s'appelle de différentes façons:
- `!huxley` pour avoir la liste des commandes.
- `!huxley help {Nom de la commande}` pour une aide générale.
- `!huxley help++ {Nom de la commande}` pour une aide détaillée.

*Exemples*
```Java
!huxley
!huxley help GameList
!huxley help++ Huxley
```

### WannaPlay
Cette commande permet de dire à Huxley qu'on souhaite jouer à un jeu, de telle heure à telle heure, pour un jour donné (optionnel). La commande s'appelle de la façon suivante: `!wp {Alias du jeu} {Heure de début au format XX:XX} -> {Heure de fin au format XX:XX} {Date de fin au format JJ/MM/AAAA (optionnel)}`

*Exemples*
```Java
!wp bf1 13:00 -> 16:00
!wp mea 20:00 -> 01:00
!wp cod4 10:00 -> 16:00 26/12/2018
```

### ShowPlanning
En appelant cette commande, Huxley vous indiquera en MP le planning de jeu qu'il a enregistré pour vous. On l'utilise de la façon suivante: `!sp`

### GameList
Cette commande permet de connaître l'intégralité des jeux que Huxley connait mais également d'en ajouter ou d'en supprimer. La commande s'appelle de différentes façons:
- `!gl all` pour voir la liste des jeux.
- `!gl + "{Nom du jeu}" {Liste d'alias séparés par un espace}` pour ajouter un jeu.
- `!gl - {Alias du jeu}` pour supprimer un jeu de la liste.

*Exemples*
```Java
!gl all
!gl + "League of Legend" lol LoL
!gl - cod1
```