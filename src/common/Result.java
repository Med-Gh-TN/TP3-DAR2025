package common;

import java.io.Serializable;

/**
 * Cette classe représente le résultat d'un calcul renvoyé par le serveur.
 * Elle est "Serializable" pour pouvoir être transmise via les Object Streams.
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 2L; // Pour la sérialisation

    private final double value;
    private final String errorMessage;

    // Constructeur pour un résultat réussi
    public Result(double value) {
        this.value = value;
        this.errorMessage = null;
    }

    // Constructeur pour un résultat en erreur
    public Result(String errorMessage) {
        this.value = Double.NaN; // Not a Number, pour indiquer une valeur invalide
        this.errorMessage = errorMessage;
    }

    public double getValue() {
        return value;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return errorMessage == null;
    }

    @Override
    public String toString() {
        if (isSuccess()) {
            return "Résultat : " + value;
        } else {
            return "Erreur : " + errorMessage;
        }
    }
}