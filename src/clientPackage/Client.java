package clientPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import common.Operation;
import common.Result;

public class Client {

    public static void main(String[] args) {
        // Pour les tests en local
        String serverAddress = "localhost";

        // Pour les tests en réseau local (Étape 2, point 4)
        // Remplacez "localhost" par l'adresse IP de la machine serveur.
        // Exemple : String serverAddress = "192.168.1.50";

        int serverPort = 1234;

        try (
                Socket socket = new Socket(serverAddress, serverPort);
                // Important : initialiser ObjectOutputStream AVANT ObjectInputStream
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connecté au serveur de calculatrice sur " + serverAddress + ":" + serverPort);

            // Lire le message de bienvenue du serveur
            String welcomeMessage = (String) in.readObject();
            System.out.println("Serveur : " + welcomeMessage);

            while (true) {
                System.out.println("\nEntrez une opération (ex: 5 + 3) ou 'exit' pour quitter :");
                String userInput = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userInput)) {
                    // Envoie une opération spéciale pour indiquer la déconnexion
                    out.writeObject(new Operation(0, 0, 'q'));
                    break;
                }

                // Parser l'entrée utilisateur
                String[] parts = userInput.split(" ");
                if (parts.length != 3) {
                    System.out.println("Format invalide. Veuillez respecter le format : nombre operateur nombre");
                    continue;
                }

                try {
                    double operand1 = Double.parseDouble(parts[0]);
                    char operator = parts[1].charAt(0);
                    double operand2 = Double.parseDouble(parts[2]);

                    // Créer et envoyer l'objet Operation
                    Operation op = new Operation(operand1, operand2, operator);
                    out.writeObject(op);
                    System.out.println("Opération envoyée au serveur...");

                    // Attendre et lire l'objet Result
                    Result result = (Result) in.readObject();
                    System.out.println(result); // Utilise la méthode toString() de Result

                } catch (NumberFormatException e) {
                    System.out.println("Erreur : les opérandes doivent être des nombres valides.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur de communication avec le serveur : " + e.getMessage());
        } finally {
            System.out.println("Déconnexion du client.");
        }
    }
}