Version 1.2
===========

- evolutions:
- [X] Remplacer les fleches par une zone de texte pour le choix de frequence.
- [X] Limiter la frequence max a 999.
- [ ] voir pour un craft un plus cher/complique (le DimCore doit etre entoure de iron pour faire un SolidDimCore qui
est lui meme a entourer de DimContainer pour former un DimChest).
- [ ] si le joueur ne peut pas acceder a un storage, afficher un message dans le chat et ne pas essayer d'ouvrir l'interface.
- [ ] permettre de reduire la fenetre de config du chest (un peu comme Thermal Expension mais pas exactement la meme).
- [ ] ameliorer un peu la texture (effet d'ombre / indicateur visuel => vert public, bleu prive, red locked)
- [ ] integration de l'API ComputerCraft

- bugs:
- [ ] PRIORITAIRE: lance une partie, ouvre un coffre, change la frequence: si un stack est identique au precedent stack sur le meme slot alors rien ne s'affiche
- [ ] PRIORITAIRE: quitter et relancer rapidement ne sauvegarde pas les changements sur le DimChest
- [ ] il semble y avoir une baisse de FPS en ouvrant le coffre, peut etre du a l'animation d'ouverture du coffre
- [ ] En jouant sur un serveur, si on met a jour le DimChest (freq ou owner) via un ordinateur, l'inventaire est bien mis a jour
mais l'interface continue d'afficher les anciennes infos.
- [ ] Toujours sur un serveur, a la connexion du client les coffres sont tout noir (bug de lumiere je suppose)
- [ ] en serveur, ouvrir plusieurs fois le coffre alors qu'il est locked fait crash le client (une erreur est affichee sur le serveur.

Version 1.3
===========

- [ ] objet d'acces a distance d'un DimChest (ender poutch like) (a reflechir)
- [ ] DimTank :D 
