# Bout de rapport à inclure en Latex

## Tests
Exécution en mode console
```sh
gradle run --args="-c" --console plain -q
```

```
Chess game...
Start new game
8 |R N B Q K B N R 
7 |P P P P P P P P 
6 |                
5 |                
4 |                
3 |                
2 |P P P P P P P P 
1 |R N B Q K B N R 
-------------------
   A B C D E F G H 
Next move?
e2e3
e2e3
8 |R N B Q K B N R 
7 |P P P P P P P P 
6 |                
5 |                
4 |                
3 |        P       
2 |P P P P   P P P 
1 |R N B Q K B N R 
-------------------
   A B C D E F G H 
Next move?
a2a4
a2a4
C'est au tour de l'autre joueur...
Invalid move
8 |R N B Q K B N R 
7 |P P P P P P P P 
6 |                
5 |                
4 |                
3 |        P       
2 |P P P P   P P P 
1 |R N B Q K B N R 
-------------------
   A B C D E F G H 
Next move?
a8a6
a8a6
Invalid move
8 |R N B Q K B N R 
7 |P P P P P P P P 
6 |                
5 |                
4 |                
3 |        P       
2 |P P P P   P P P 
1 |R N B Q K B N R 
-------------------
   A B C D E F G H 
Next move?
a7a5
a7a5
8 |R N B Q K B N R 
7 |  P P P P P P P 
6 |                
5 |P               
4 |                
3 |        P       
2 |P P P P   P P P 
1 |R N B Q K B N R 
-------------------
   A B C D E F G H 
Next move?
```

Note: nous n'imprimons pas les tests, car cela n'était pas utile et par économie de papiers. Il est bien sûr possible de voir le détail dans le code des tests si besoin dans le rendu Cyberlearn.

Tests sur `Move.corresponds()`

| Pièce                                  | Peut aller vers        | et pas vers                                     |
| -------------------------------------- | ---------------------- | ----------------------------------------------- |
| Move de Pion Blanc (1, 1)              | (1, 2)                 | (1, 4), (0, 3), (0, 1), (0, 2), (2, 2), (2, 1), |
| Move de Pion Noir (1, 6),              | (1, 5),                | (2, 5), (2, 4), (3, 4), (1, 3), (0, 5), (1, 7)  |
| new Move(new Point(2, 1), 3) de (0, 0) | (2, 1), (4, 2), (6, 3) |                                                 |
| new Move(new Point(0, 1), 1)           |                        | (0, 2),(0, 3),(0, 4)                            |
| Move de Knight Blanc (6,0)             | (7, 2)                 | (7, 1), (7, 3)                                  |

Tests sur `CastleMove` 
| Nom                                                                                  | OK     |
| ------------------------------------------------------------------------------------ | ------ |
| Test du Petit Roque sur roi blanc                                                    | **OK** |
| Test du Grand Roque sur roi blanc                                                    | **OK** |
| Test du Petit Roque sur roi noir                                                     | **OK** |
| Test du Grand Roque sur roi noir                                                     | **OK** |
| Test roque avec des pions qui bloquent le chemin -> invalide                         | **OK** |
| Test roque ne se produit pas quand le roi a déjà bougé                               | **OK** |
| Test roque ne se produit pas quand la tour a déjà bougé                              | **OK** |
| Test grand roque blanc ne se produit pas si on tente de bouger le roi de 3 cases     | **OK** |
| Test de faire un roque avec le roi en échec -> invalide                              | **OK** |
| Test de faire un roque avec la case intermédiaire menaçée -> invalide                | **OK** |
| Test de faire un roque alors que le roi est menacé sur la case d'arrivée -> invalide | **OK** |

Les positions internes des pièces ont également été testées à différents moments.

Tests sur `EnPassant`
| Nom                                                                                    | OK     |
| -------------------------------------------------------------------------------------- | ------ |
| En passant à droite avec pion attaquant blanc                                          | **OK** |
| En passant à gauche avec pion attaquant blanc                                          | **OK** |
| En passant à droite avec pion attaquant noir                                           | **OK** |
| En passant à gauche avec pion attaquant noir                                           | **OK** |
| En passant avec la victime qui a bougé d'une case -> invalide                          | **OK** |
| En passant avec l'attaquant qui n'a pas fait son En passant tout de suite. -> invalide | **OK** |

TODO: finish other tests and manuals tests...