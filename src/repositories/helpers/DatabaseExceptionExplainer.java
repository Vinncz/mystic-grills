package repositories.helpers;

import java.sql.SQLException;

import exceptions.DatabaseModificationPolicyViolatedException;

public class DatabaseExceptionExplainer {

    public static void explainParseFault (SQLException e) {
        System.out.println("Object parse fault!");
        e.printStackTrace();
        System.out.println(e.getCause());
        System.out.println(e.getLocalizedMessage());
        System.out.println(e.getSQLState());
        System.out.println(e.getErrorCode());

    }

    public static void explainMoldObjectIsNull (NullPointerException e) {
        System.out.println("Mold object is null");
        e.printStackTrace();
    }

    public static void explainEnumeralAssignmentFailure (IllegalArgumentException e) {
        System.out.println("There is no associated enumerals for given string");
        e.printStackTrace();
    }

    public static void explainQueryFault (SQLException e) {
        System.out.println("Query fault!");
        e.printStackTrace();
        System.out.println(e.getCause());
        System.out.println(e.getLocalizedMessage());
        System.out.println(e.getSQLState());
        System.out.println(e.getErrorCode());
    }

    public static void explainMaximumModifiableRowViolation (DatabaseModificationPolicyViolatedException e) {
        System.out.println("Database modification policy violated!");
        e.printStackTrace();
    }

    public static void explainCommitFault (SQLException e) {
        System.out.println("Database failed to commit!");
        e.printStackTrace();
        System.out.println(e.getCause());
        System.out.println(e.getLocalizedMessage());
        System.out.println(e.getSQLState());
        System.out.println(e.getErrorCode());
    }

    public static void explainRollbackFault (SQLException e) {
        System.out.println("Failed to initiate database rollback!");
        e.printStackTrace();
        System.out.println(e.getCause());
        System.out.println(e.getLocalizedMessage());
        System.out.println(e.getSQLState());
        System.out.println(e.getErrorCode());
    }

}
