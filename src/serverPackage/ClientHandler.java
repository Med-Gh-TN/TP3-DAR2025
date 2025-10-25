package serverPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

// Implémenter Runnable est une bonne pratique pour créer des Threads.
public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final int clientNumber;

    public ClientHandler(Socket socket, int clientNumber) {
        this.clientSocket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try {
            // b) Afficher, côté serveur, les informations du client connecté
            System.out.println("Client n°" + clientNumber + " connecté.");
            System.out.println("  - Adresse IP : " + clientSocket.getRemoteSocketAddress());

            // Outil pour envoyer du texte au client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // c) Envoyer au client son numéro d'ordre
            out.println("Bienvenue, vous êtes le client n°" + clientNumber);

            // La communication avec ce client est terminée pour cet exercice.
            // Dans une vraie application, il y aurait ici une boucle pour écouter les messages du client.

        } catch (IOException e) {
            System.err.println("Erreur de communication avec le client n°" + clientNumber + ": " + e.getMessage());
        } finally {
            // S'assurer que la connexion est bien fermée à la fin
            try {
                System.out.println("Déconnexion du client n°" + clientNumber);
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture du socket pour le client n°" + clientNumber);
            }
        }
    }
}