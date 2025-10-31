package common;

import java.io.Serializable;

/**
 * Cette classe représente une opération de calcul à envoyer au serveur.
 * Elle est "Serializable" pour pouvoir être transmise via les Object Streams.
 */
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L; // Pour la sérialisation

    private final double operand1;
    private final double operand2;
    private final char operator; // '+', '-', '*', '/'

    public Operation(double operand1, double operand2, char operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    public double getOperand1() {
        return operand1;
    }

    public double getOperand2() {
        return operand2;
    }

    public char getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return operand1 + " " + operator + " " + operand2;
    }
}