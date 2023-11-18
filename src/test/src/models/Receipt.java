package test.src.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

import database_access.ConnectionMaster;
import models.helpers.ConnectsWithDatabase;

public class Receipt extends ConnectsWithDatabase{

    public enum ReceiptPaymentType {
        CASH,
        CREDIT,
        DEBIT,
    }

    private static Connection db = ConnectionMaster.getConnection();
    private static final String TABLE_NAME = "receipts";
    // private static Integer cumulativeReceiptId = 0;

    private Integer receiptId;
    private Order receiptOrder;
    private Double receiptAmountPaid;
    private String receiptPaymentDate;
    private ReceiptPaymentType receiptPaymentType;

    /*
     * ==========================================================================
     *
     * (!)
     * DATABASE METHODS
     *
     * The following should be static.
     *
     * ==========================================================================
     */

    /*
     * GET
     */
    public static Receipt getReceiptById (Integer _receiptId) {

        final String query = "SELECT * FROM " + TABLE_NAME + " WHERE id = ?";
        Receipt target = null;
        PreparedStatement ps = null;
        ResultSet res;

        try {
            ps = db.prepareStatement(query);
            ps.setInt(1, _receiptId);
            res = ps.executeQuery();

            while(res.next()){
                Order o = Order.getOrderByOrderId(res.getInt("order_id"));
                ReceiptPaymentType type = ReceiptPaymentType.valueOf(res.getString("payment_type"));
                target = new Receipt();

                target.receiptId = res.getInt("id");
                target.setReceiptOrder(o);
                target.setReceiptAmountPaid(res.getDouble("payment_amount"));
                target.setReceiptPaymentDate(res.getTimestamp("payment_date").toString());
                target.setReceiptPaymentType(type);
            }

            res.close();
            ps.close();
        } catch (SQLException e) {
            /* When query operations encountered an exception, it will be handled here */
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            /* When enumerals attribute encountered an exception, it will be handled here */
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            /* When there are an exception we didn't foresee, it will be handled here */
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return target;
    }

    public static ArrayList<Receipt> getAllReceipts () {

        final String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<Receipt> target = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet res;

        try {
            ps = db.prepareStatement(query);
            res = ps.executeQuery();

            while(res.next()){
                Receipt r = new Receipt();

                Order o = Order.getOrderByOrderId(res.getInt("order_id"));
                ReceiptPaymentType type = ReceiptPaymentType.valueOf(res.getString("payment_type"));

                r.receiptId = res.getInt("id");
                r.setReceiptOrder(o);
                r.setReceiptAmountPaid(res.getDouble("payment_amount"));
                r.setReceiptPaymentDate(res.getTimestamp("payment_date").toString());
                r.setReceiptPaymentType(type);

                target.add(r);
            }

            res.close();
            ps.close();
        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return target;
    }

    /*
     * POST
     */
    public static Receipt createReceipt (Order _order, ReceiptPaymentType _paymentType, Double _amountPaid, String _paymentDate) {

        /* Insert the newly created object into database */

        final String query = "INSERT INTO " + TABLE_NAME + " (order_id, payment_amount, payment_date, payment_type) VALUES (?, ?, ?, ?)";
        Receipt r = null;
        PreparedStatement ps = null;

        try {
            ps = db.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, _order.getOrderId());
            ps.setDouble(2, _amountPaid);
            ps.setString(3, _paymentDate);
            ps.setString(4, _paymentType.toString());

            int rowsAffected = ps.executeUpdate();

            SummarizeDatabaseExecution("createReceipt", ps.toString(), rowsAffected);

            if(!Commit(db, ps.toString())){
                throw new Exception();
            }

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if(generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);

                r = new Receipt();

                r.setReceiptId(generatedId);
                r.receiptOrder = _order;
                r.receiptAmountPaid = _amountPaid;
                r.receiptPaymentDate = _paymentDate;
                r.receiptPaymentType = _paymentType;
            }

            ps.close();
        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return r;
    }

    /*
     * PATCH
     */
    public static Boolean updateReceipt (Integer _orderId, ReceiptPaymentType _paymentType, Double _amountPaid, String _paymentDate) {
        /* Call the getReceiptById(_orderId) */
        Receipt r = Receipt.getReceiptById(_orderId);

        /* Update the object's value  with the new one */
        final String query = "UPDATE " + TABLE_NAME + " SET order_id = ?, payment_amount = ?, payment_date = ?, payment_type = ? WHERE id = ?";
        PreparedStatement ps = null;
        Boolean successful = false;

        try {
            ps = db.prepareStatement(query);

            ps.setInt(1, _orderId);
            ps.setDouble(2, _amountPaid);
            ps.setString(3, _paymentDate);
            ps.setString(4, _paymentType.toString());
            ps.setInt(5, r.getReceiptId());

            int rowsAffected = ps.executeUpdate();

            SummarizeDatabaseExecution("updateReceipt", ps.toString(), rowsAffected);

            if(!Commit(db, ps.toString())){
                throw new Exception();
            }

            successful = true;
            ps.close();

        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return successful;
    }

    /*
     * DELETE
     */
    public static Boolean deleteReceipt (Integer _orderId) {
        /* Call the getReceiptById(_orderId) */
        Receipt r = Receipt.getReceiptById(_orderId);

        /* Delete the object from the database */
        final String query = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
        PreparedStatement ps = null;
        Boolean successful = false;

        try {
            ps = db.prepareStatement(query);
            ps.setInt(1, r.getReceiptId());

            int rowsAffected = ps.executeUpdate();

            SummarizeDatabaseExecution("deleteReceipt", ps.toString(), rowsAffected);

            if(!Commit(db, ps.toString())){
                throw new Exception();
            }

            successful = true;
            ps.close();

        } catch (SQLException e) {
            ExplainSQLError(e, (ps != null) ? ps.toString() : SQLEXCEPTION_GENERIC_MESSAGE);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() != null ? e.getMessage() : ILLEGALARGUMENTEXCEPTION_GENERIC_MESSAGE);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() != null ? e.getMessage() : EXCEPTION_GENERIC_MESSAGE);

        }

        return successful;
    }


    /*
     * ==========================================================================
     *
     * (!)
     * GETTERS & SETTERS
     *
     * The following should NOT be static.
     *
     * ==========================================================================
     */
    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer _id) {
        this.receiptId = _id;
    }

    public Order getReceiptOrder() {
        return receiptOrder;
    }

    public void setReceiptOrder(Order receiptOrder) {
        this.receiptOrder = receiptOrder;
    }

    public Double getReceiptAmountPaid() {
        return receiptAmountPaid;
    }

    public void setReceiptAmountPaid(Double receiptAmountPaid) {
        this.receiptAmountPaid = receiptAmountPaid;
    }

    public String getReceiptPaymentDate() {
        return receiptPaymentDate;
    }

    public void setReceiptPaymentDate(String receiptPaymentDate) {
        this.receiptPaymentDate = receiptPaymentDate;
    }

    public ReceiptPaymentType getReceiptPaymentType() {
        return receiptPaymentType;
    }

    public void setReceiptPaymentType(ReceiptPaymentType receiptPaymentType) {
        this.receiptPaymentType = receiptPaymentType;
    }

}
