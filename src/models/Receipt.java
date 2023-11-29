package models;

public class Receipt {

    public enum ReceiptPaymentType {
        CASH,
        CREDIT,
        DEBIT,
    }

    private Integer receiptId;
    private Order receiptOrder;
    private Double receiptAmountPaid;
    private String receiptPaymentDate;
    private ReceiptPaymentType receiptPaymentType;

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
