# TP3 - Calculatrice Client/Serveur Multi-Thread en Java

## Description

Ce projet est une application de calculatrice simple implémentant une architecture client/serveur en Java. Le serveur est capable de gérer plusieurs clients simultanément grâce à l'utilisation du multi-threading. Les clients se connectent au serveur pour envoyer des opérations arithmétiques, et le serveur leur retourne le résultat calculé. La communication entre les entités se fait via des Sockets TCP et la sérialisation d'objets Java.

## Fonctionnalités Principales

*   **Architecture Client/Serveur** : Une séparation claire des responsabilités entre le client (interface utilisateur) et le serveur (logique de calcul).
*   **Gestion Multi-Clients** : Le serveur utilise des threads pour traiter les requêtes de plusieurs clients en parallèle, sans qu'ils n'interfèrent les uns avec les autres.
*   **Opérations Arithmétiques** : Support des calculs de base : addition (+), soustraction (-), multiplication (*), et division (/).
*   **Communication par Objets** : Utilise la sérialisation Java pour échanger des objets `Operation` et `Result` structurés, rendant la communication robuste.
*   **Gestion des Erreurs** : Le serveur gère les cas d'erreur comme la division par zéro ou l'utilisation d'un opérateur non valide, et en informe le client.

## Stack Technique

*   **Langage** : Java
*   **Réseau** : Java Sockets (TCP/IP)
*   **Concurrence** : Java Threads

## Prérequis

Pour compiler et exécuter ce projet, vous devez avoir installé :
*   Java Development Kit (JDK) version 8 ou supérieure.

## Installation et Lancement

Suivez ces étapes pour compiler et lancer l'application. Toutes les commandes doivent être exécutées depuis le répertoire racine du projet (`TP3/`).

#### 1. Compilation

Compilez l'ensemble des fichiers source Java. La commande suivante placera les fichiers `.class` compilés dans le répertoire `out/`.

```bash
# S'assurer que le répertoire 'out' existe
mkdir -p out

# Compiler les sources
javac -d out src/common/*.java src/clientPackage/*.java src/serverPackage/*.java
```

#### 2. Lancement du Serveur

Ouvrez un terminal et lancez le serveur. Il se mettra en écoute des connexions entrantes sur le port 1234.

```bash
java -cp out serverPackage.Server
```
Le terminal affichera un message indiquant que le serveur a démarré.

#### 3. Lancement du Client

Ouvrez un **nouveau terminal** pour chaque client que vous souhaitez connecter.

```bash
java -cp out clientPackage.Client
```
Le client se connectera au serveur. Vous pouvez alors commencer à entrer des opérations (ex: `10 * 5`) ou taper `exit` pour vous déconnecter.

## Structure du Projet

Le code source est organisé en packages pour séparer clairement les responsabilités.

```
TP3/
├── out/                  # Contient les fichiers .class compilés
└── src/                  # Contient les fichiers source .java
    ├── clientPackage/
    │   └── Client.java   # Point d'entrée et logique de l'application cliente
    ├── common/
    │   ├── Operation.java # DTO pour représenter une requête de calcul
    │   └── Result.java    # DTO pour représenter une réponse du serveur
    └── serverPackage/
        ├── Server.java        # Point d'entrée du serveur, gère les connexions
        └── ClientProcess.java # Logique de traitement pour un seul client (lancé dans un Thread)
```

*   **`clientPackage`**: Contient tout le code spécifique au client. `Client.java` gère l'interaction avec l'utilisateur, l'envoi des opérations et l'affichage des résultats.
*   **`serverPackage`**: Contient la logique du serveur. `Server.java` est le point d'entrée qui accepte les connexions et lance un nouveau `ClientProcess` pour chacune. `ClientProcess.java` gère le cycle de vie d'une connexion client unique.
*   **`common`**: Contient les classes partagées entre le client et le serveur. Ces classes (`Operation` et `Result`) servent de Data Transfer Objects (DTO) et doivent être sérialisables pour être envoyées sur le réseau.

## Stratégie de Branches

Ce dépôt utilise plusieurs branches pour organiser le développement et l'historique du projet.

*   **`main`** :
    C'est la branche principale et la plus stable. Elle contient la version finale et complète du projet, avec toutes les fonctionnalités implémentées et testées. C'est la version de référence.

*   **`Activite_3_1`** et **`Activite_3_2`** :
    Ces branches représentent des étapes intermédiaires du développement, probablement liées à des exercices ou des "Activités" spécifiques du cahier des charges. Elles permettent de visualiser l'évolution du code :
    *   `Activite_3_1` pourrait contenir une version initiale du projet, par exemple un serveur qui ne gère qu'un seul client à la fois (mono-thread).
    *   `Activite_3_2` pourrait représenter l'étape où le multi-threading a été introduit pour permettre la gestion de plusieurs clients simultanément.

Naviguer entre ces branches permet de comprendre la progression logique du développement, de la version la plus simple à la version finale.
