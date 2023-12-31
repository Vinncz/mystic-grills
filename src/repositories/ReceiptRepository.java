package repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import exceptions.DatabaseModificationPolicyViolatedException;
import models.Order;
import models.Receipt;
import repositories.helpers.DatabaseExceptionExplainer;
import repositories.interfaces.BaseRepository;
import values.SYSTEM_PROPERTIES;

public class ReceiptRepository extends BaseRepository<Receipt> {

    private OrderRepository orderRepo;

    public ReceiptRepository () {
        super (
            SYSTEM_PROPERTIES.DATABASE_RECEIPT_TABLE.value,

            String.format("SELECT * FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_RECEIPT_TABLE.value),
            String.format("SELECT * FROM %s", SYSTEM_PROPERTIES.DATABASE_RECEIPT_TABLE.value),
            String.format("INSERT INTO %s (order_id, payment_amount, payment_date, payment_type) VALUES (?, ?, ?, ?)", SYSTEM_PROPERTIES.DATABASE_RECEIPT_TABLE.value),
            String.format("UPDATE %s SET order_id = ?, payment_amount = ?, payment_date = ?, payment_type = ? WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_RECEIPT_TABLE.value),
            String.format("DELETE FROM %s WHERE id = ?", SYSTEM_PROPERTIES.DATABASE_RECEIPT_TABLE.value)
        );

        orderRepo = new OrderRepository();
    }

    /**
     * Deletes any Receipt whose {@code orderId} matched the {@code order_id} from the given parameter
     *
     * @param _orderId •
     * @return Confirmation of whether the deletion operation is successful or not
     */
    public Boolean deleteByOrderId (Integer _orderId) {
        final String query = String.format("DELETE FROM %s WHERE order_id = ?", TABLE_NAME);
        try {
            BaseRepository<Receipt>.executeUpdateReturnDatatypes updateReport = executeUpdate(
                db,
                query,

                _orderId
            );

            Integer rowsAffected = updateReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) )
                return save(db);

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);
            rollback(db);

        } catch (DatabaseModificationPolicyViolatedException _modificationDidNotFollowDatabasePolicy) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_modificationDidNotFollowDatabasePolicy);
            rollback(db);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return false;
    }

    /**
     * Deletes any Receipt whose {@code orderId} matched the {@code order_id} from the given parameter. <br></br>
     * This variation will not commit by itself, so you'll have to explicitly call {@code commit(db)} in order for changes to take effect.
     *
     * @param _orderId •
     * @return Confirmation of whether the deletion operation is successful or not
     */
    public Boolean deleteByOrderIdWithoutCommit (Integer _orderId) {
        final String query = String.format("DELETE FROM %s WHERE order_id = ?", TABLE_NAME);
        try {
            BaseRepository<Receipt>.executeUpdateReturnDatatypes updateReport = executeUpdate(
                db,
                query,

                _orderId
            );

            Integer rowsAffected = updateReport.getRowsAffected();
            if ( modificationFollowsDatabasePolicy(rowsAffected) )
                return true;

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainQueryFault(_problemDuringQueryExecution);

        } catch (DatabaseModificationPolicyViolatedException _modificationDidNotFollowDatabasePolicy) {
            DatabaseExceptionExplainer.explainMaximumModifiableRowViolation(_modificationDidNotFollowDatabasePolicy);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return false;
    }

    @Override
    protected Integer getObjectId(Receipt _object) {
        return _object.getReceiptId();
    }

    @Override
    protected Receipt setObjectId(Receipt _object, Integer _id) {
        _object.setReceiptId(_id);
        return _object;
    }

    private Receipt attachAttribute (Integer _id, Integer _orderId, Double _paymentAmount, String _paymentDate, String _paymentType) throws IllegalArgumentException {
        Receipt moldObject = new Receipt();

        moldObject.setReceiptId         (_id);
        moldObject.setReceiptAmountPaid (_paymentAmount);
        moldObject.setReceiptPaymentDate(_paymentDate);
        moldObject.setReceiptPaymentType(Receipt.ReceiptPaymentType.valueOf(_paymentType));

        Optional<Order> o = orderRepo.getById(_orderId);
        if ( o.isPresent() ) {
            moldObject.setReceiptOrder(o.get());

        } else {
            moldObject.setReceiptOrder(null);

        }

        return moldObject;
    }

    @Override
    public Optional<Receipt> parse(ResultSet _resultSetThatMayContainObject) {
        Optional<Receipt> parsedObject = Optional.empty();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            if ( res.next() ) {
                Receipt moldObject = attachAttribute(
                                            res.getInt("id"),
                                            res.getInt("order_id"),
                                            res.getDouble("payment_amount"),
                                            res.getDate("payment_date").toString(),
                                            res.getString("payment_type")
                                       );

                parsedObject = Optional.of(moldObject);
            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (IllegalArgumentException _noMatchingEnumeralValue) {
            DatabaseExceptionExplainer.explainEnumeralAssignmentFailure(_noMatchingEnumeralValue);

        } catch (NullPointerException _moldObjectIsNull) {
            DatabaseExceptionExplainer.explainMoldObjectIsNull(_moldObjectIsNull);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return parsedObject;
    }

    @Override
    public ArrayList<Receipt> parseMany(ResultSet _resultSetThatMayContainObject) {
        ArrayList<Receipt> parsedObjects = new ArrayList<>();
        ResultSet res = _resultSetThatMayContainObject;

        try {
            while ( res.next() ) {
                Receipt moldObject = attachAttribute(
                                            res.getInt("id"),
                                            res.getInt("order_id"),
                                            res.getDouble("payment_amount"),
                                            res.getDate("payment_date").toString(),
                                            res.getString("payment_type")
                                       );

                parsedObjects.add(moldObject);
            }

        } catch (SQLException _problemDuringQueryExecution) {
            DatabaseExceptionExplainer.explainParseFault(_problemDuringQueryExecution);

        } catch (IllegalArgumentException _noMatchingEnumeralValue) {
            DatabaseExceptionExplainer.explainEnumeralAssignmentFailure(_noMatchingEnumeralValue);

        } catch (Exception _unanticipatedProblem) {
            _unanticipatedProblem.printStackTrace();
            throw new RuntimeException(_unanticipatedProblem.getMessage());

        }

        return parsedObjects;
    }

    @Override
    public Object[] unparseAttributes(Receipt _object) {
        Object [] attributes = {
                                    _object.getReceiptOrder().getOrderId(),
                                    _object.getReceiptAmountPaid(),
                                    _object.getReceiptPaymentDate(),
                                    _object.getReceiptPaymentType().toString()
                               };

        return attributes;
    }

}
