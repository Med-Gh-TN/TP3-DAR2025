package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        // Le port d'écoute du serveur
        int port = 1234;
        // Compteur pour le numéro d'ordre des clients
        int clientNumber = 0;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Le serveur multi-thread est démarré et écoute sur le port " + port);

            // Boucle infinie pour accepter les connexions des clients en continu
            while (true) {
                // La méthode accept() bloque l'exécution jusqu'à ce qu'un client se connecte
                Socket clientSocket = serverSocket.accept();

                // Incrémenter le numéro de client pour chaque nouvelle connexion
                clientNumber++;

                // a) Gérer plusieurs clients simultanément -> en créant un Thread pour chaque client
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientNumber);
                new Thread(clientHandler).start(); // Démarrer le Thread
            }
        } catch (IOException e) {
            System.err.println("Erreur sur le serveur : " + e.getMessage());
        }
    }
}