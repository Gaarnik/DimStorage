Portage 1.7.2
=============

- [X] ajout et craft de l'item DimCore (texture, lang)
- [X] ajout et craft du bloc DimChest (texture, model, lang)
- [ ] fonctionnement solo du DimStorageManager
- [ ] fonctionnement en multi
- [ ] integration de l'API ComputerCraft

AUCUNE MODIFICATION NE DOIT ETRE FAITE AVANT LE PORTAGE 1.7 !
=============================================================

evolutions
==========

- [ ] Remplacer les fleches par une zone de texte pour le choix de frequence.
- [ ] voir pour un craft un peu plus cher (diamand ?)
- [ ] permettre de reduire la fenetre de config du chest (un peu comme Thermal Expension mais pas exactement la meme).
- [ ] objet d'acces a distance d'un DimChest (ender poutch like) (a reflechir)

bugs
====

- [ ] il semble y avoir une baisse de FPS en ouvrant le coffre, peut etre du a l'animation d'ouverture du coffre
- [ ] En jouant sur un serveur, si on met a jour le DimChest (freq ou owner) via un ordinateur, l'inventaire est bien mise a jour
mais l'interface continue d'afficher les anciennes infos.
- [ ] Toujours sur un serveur, a la connexion du client les coffres sont tout noir (bug de lumiere je suppose)
- [ ] en serveur, ouvrir plusieurs fois le coffre alors qu'il est locked fait crash le client (une erreur est affichee sur le serveur.
