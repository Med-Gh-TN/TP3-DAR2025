package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Étape 3: Compteur global partagé pour le nombre total d'opérations
    private static int totalOperations = 0;
    // Un objet de verrouillage pour la synchronisation
    private static final Object lock = new Object();

    public static void main(String[] args) {
        int port = 1234;
        int clientNumber = 0;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Le serveur de calculatrice multi-thread est démarré sur le port " + port);

            // Boucle infinie pour accepter les connexions
            while (true) {
                // Accepte une nouvelle connexion client
                Socket clientSocket = serverSocket.accept();
                clientNumber++;

                // Crée un nouveau thread pour gérer la communication avec ce client
                ClientProcess clientProcess = new ClientProcess(clientSocket, clientNumber);
                new Thread(clientProcess).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur sur le serveur : " + e.getMessage());
        }
    }

    /**
     * Étape 3: Méthode synchronisée pour incrémenter le compteur d'opérations.
     * L'utilisation de 'synchronized' garantit qu'un seul thread peut exécuter
     * cette méthode à la fois, évitant ainsi les conflits d'accès concurrents.
     */
    public static void incrementOperationCount() {
        synchronized (lock) {
            totalOperations++;
            System.out.println("Nombre total d'opérations traitées : " + totalOperations);
        }
    }
}