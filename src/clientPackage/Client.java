package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "localhost"; // ou "127.0.0.1"
        int serverPort = 1234;

        // Pour les tests en réseau local (Étape 2, point 3)
        // Il suffit de changer l'adresse "localhost" par l'adresse IP de la machine serveur.
        // Exemple : String serverAddress = "192.168.1.50";

        try {
            // Pour les tests en réseau local, on peut utiliser InetAddress pour résoudre le nom
            InetAddress adr = InetAddress.getByName(serverAddress);
            Socket socket = new Socket(adr, serverPort);

            System.out.println("Connecté au serveur à l'adresse " + serverAddress + " sur le port " + serverPort);

            // Outil pour lire les messages envoyés par le serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Lire le message de bienvenue (numéro d'ordre) envoyé par le serveur
            String serverResponse = in.readLine();

            if (serverResponse != null) {
                System.out.println("Message reçu du serveur : " + serverResponse);
            }

            // Fermer la connexion
            // Pas besoin de le faire explicitement si on utilise un try-with-resources,
            // mais ici on ferme manuellement pour la clarté.
            socket.close();
            System.out.println("Connexion fermée.");

        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}