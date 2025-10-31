package serverPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import common.Operation;
import common.Result;

public class ClientProcess implements Runnable {

    private final Socket clientSocket;
    private final int clientNumber;

    public ClientProcess(Socket socket, int clientNumber) {
        this.clientSocket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try (
                // Important : initialiser ObjectOutputStream AVANT ObjectInputStream pour éviter un blocage
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            System.out.println("Client n°" + clientNumber + " connecté depuis " + clientSocket.getRemoteSocketAddress());
            out.writeObject("Bienvenue, vous êtes le client n°" + clientNumber); // Envoi du message de bienvenue

            while (true) {
                try {
                    // Attente de la réception d'un objet Operation
                    Operation op = (Operation) in.readObject();
                    System.out.println("Reçu du client n°" + clientNumber + " : " + op);

                    // Si l'opérateur est 'q', le client veut se déconnecter
                    if (op.getOperator() == 'q') {
                        break; // Sortir de la boucle pour fermer la connexion
                    }

                    // Calculer le résultat
                    Result result = calculate(op);

                    // Envoyer l'objet Result au client
                    out.writeObject(result);

                } catch (ClassNotFoundException e) {
                    System.err.println("Erreur de désérialisation pour le client n°" + clientNumber + ": " + e.getMessage());
                    break;
                }
            }
        } catch (IOException e) {
            // Cette exception se produit souvent lorsque le client se déconnecte brusquement
            System.out.println("Le client n°" + clientNumber + " s'est déconnecté.");
        } finally {
            try {
                clientSocket.close();
                System.out.println("Connexion fermée pour le client n°" + clientNumber);
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture du socket pour le client n°" + clientNumber);
            }
        }
    }

    /**
     * Effectue le calcul basé sur l'objet Operation.
     * @param op L'opération à effectuer.
     * @return Un objet Result contenant la réponse.
     */
    private Result calculate(Operation op) {
        double value;
        switch (op.getOperator()) {
            case '+':
                value = op.getOperand1() + op.getOperand2();
                break;
            case '-':
                value = op.getOperand1() - op.getOperand2();
                break;
            case '*':
                value = op.getOperand1() * op.getOperand2();
                break;
            case '/':
                if (op.getOperand2() == 0) {
                    return new Result("Erreur : Division par zéro !");
                }
                value = op.getOperand1() / op.getOperand2();
                break;
            default:
                return new Result("Erreur : Opérateur '" + op.getOperator() + "' non valide.");
        }

        // Si le calcul a réussi, on incrémente le compteur global (Étape 3)
        Server.incrementOperationCount();
        return new Result(value);
    }
}