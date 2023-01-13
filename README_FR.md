# Conso-Scan

Cette application Android permet aux consommateurs de visualiser la fiche descriptive d'un produit (nom, conso-score, taxe conso-score, empreinte carbone, ...) et d’ainsi connaître la taxe qui sera appliqué au produit.

## Technologies

L'application est développée en Java Android.

Elle utilise la librairie zxing pour le scanneur de codebar et retrofit2 pour la communication avec le back office.

L'application communique avec le backoffice sur le chemin /datas/product/{codebar}/

## Utilisation

L'utilisateur scanne le codebar de l'article, ensuite la fiche du produit est directement affichée sur le téléphone.

## Configuration

Avant de compiler l'application, il faut configurer l'adresse du back office.

Pour le faire, il suffit de spécifier l'adresse dans le fichier ConsoScore/app/build.gradle à la ligne 17.

## Comment compiler

Il suffit d'ouvrir le dossier ConsoScore dans Android Studio.

Si vous voulez compiler l'application depuis le terminal, il suffit de suivre les indications disponible sur le [Wiki Developpeur Android](
https://developer.android.com/studio/build/building-cmdline)
