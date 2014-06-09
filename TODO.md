Gaarnik
=======

DimChest UI:
- [X] terminer la synchronisation des actions entre l'interface et la TileEntity
- [X] implementer le "lock" du DimChest

DimChest TE:
- [X] Integration de l'API ComputerCraft pour gerer les DimChest
- [X] implementer les fonctions pour interagir avec le DimChest

Gaarnik ou autre
================

mcmod.info:
- [X] voir pourquoi le numero de version ne se met pas quand on build

DimChest:
- [X] Bug: synchro de l'ouverture du coffre en mode server (packet envoye par le server au lieu du client ?)
- [X] Ajouter les raccourcis clavier pour gerer l'inventaire (shitf+clic ...)
- [X] Bug: en mode release la texture du chest ne s'affiche pas sur le model

2 bugs avant la release:
- [ ] En jouant sur un serveur, si on met a jour le DimChest (freq ou owner) via un ordinateur, l'inventaire est bien mise à jour
mais l'interface continue d'afficher les anciennes infos.
- [ ] Toujours sur un serveur, a la connexion du client les coffres sont tout noir (bug de lumiere je suppose)