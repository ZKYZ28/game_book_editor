package org.helmo.gbeditor.infrastructures;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class qui permet de faire des transactions la base de données
 * @author Monsieur Ludewig
 *
 */
public class Transaction {
    private final Connection con;
    private ExceptionHandle rollbackAction;
    private ActionThrowingException commitAction;

    /**
     * Méthode qui permet d'indiquer la connexion depuis la transaction doit être 
     * exécutée
     * @param con Connection qui est la Transaction
     * @return Transaction
     */
    public static Transaction from(Connection con) {
        try {
            con.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new TransactionNotSupportedException(ex);
        }
        return new Transaction(con);
    }

    /**
     * Constructeur de la transaction
     * @param con Connection qui est la connection à la base de données
     */
    public Transaction(Connection con) {
        this.con = con;
    }

    /**
     * Méthode qui permet de renseigner l’enregistrement effectif de la  transaction
     * @param sequence  ActionThrowingException
     * @return Transaction
     */
    public Transaction commit(ActionThrowingException sequence) {
        this.commitAction = sequence;
        return this;
    }

    /**
     * Méthode qui permet de faire un rollBack en cas d'erreur dans la transaction
     * @param sequence L'exception que l'on veut renvoyer
     * @return Transaction
     */
    public Transaction onRollback(ExceptionHandle sequence) {
        this.rollbackAction = sequence;
        return this;
    }

    /**
     * Méthode qui permet d'exécuter la transaction
     */
    public void execute() {
        try {
            commitAction.execute(con);
            con.commit();
        } catch (Exception ex) {
            try {
                con.rollback();
                rollbackAction.handle(ex);
            } catch (SQLException e) {
            	throw new TransactionNotSupportedException(e);
            }
        } 
            try {
                con.setAutoCommit(true); //Active la gestion automatique des transactions
            } catch(SQLException ex) {
                throw new TransactionNotSupportedException(ex);
            }
    }
}

	/**
	 * Exception qui est lancée en cas d'erreur avec la Transaction
	 * @author Monsieur Ludewig
	 *
	 */
	class TransactionNotSupportedException extends RuntimeException {
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	
		/**
		 * Constructeur de TransactionNotSupportedException
		 * @param ex SQLException exception lancée à cause d'un erreur sql
		 */
		public TransactionNotSupportedException(SQLException ex) {
	        super("Transaction are not supported by this DBMS or this driver", ex);
	    }
	}
	
	/**
	 * 
	 * @author Monsieur Ludewig
	 *
	 */
	@FunctionalInterface
	interface ActionThrowingException {
		/**
		 * 
		 * @param con
		 * @throws Exception
		 */
	    void execute(Connection con) throws Exception;
	}
	
	/**
	 * 
	 * @author Monsieur Ludewig
	 *
	 */
	@FunctionalInterface
	interface ExceptionHandle {
		/**
		 * 
		 * @param ex
		 */
	    void handle(Exception ex);
	}

