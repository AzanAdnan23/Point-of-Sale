public class recipt {
    int ReceiptNo, SalesID, TotalSale, AmountPaid, RemainingAmount;

    public recipt(int receiptNo, int salesID, int totalSale, int amountPaid, int remainingAmount) {
        ReceiptNo = receiptNo;
        SalesID = salesID;
        TotalSale = totalSale;
        AmountPaid = amountPaid;
        RemainingAmount = remainingAmount;
    }

    public recipt(int receiptNo, int salesID, int totalSale) {
        ReceiptNo = receiptNo;
        SalesID = salesID;
        TotalSale = totalSale;
        AmountPaid = 0;
        RemainingAmount = 0;
    }

    public int getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(int receiptNo) {
        ReceiptNo = receiptNo;
    }

    public int getSalesID() {
        return SalesID;
    }

    public void setSalesID(int salesID) {
        SalesID = salesID;
    }

    public int getTotalSale() {
        return TotalSale;
    }

    public void setTotalSale(int totalSale) {
        TotalSale = totalSale;
    }

    public int getAmountPaid() {
        return AmountPaid;
    }

    public void setAmountPaid(int amountPaid) {
        AmountPaid = amountPaid;
    }

    public int getRemainingAmount() {
        return RemainingAmount;
    }

    public void setRemainingAmount(int remainingAmount) {
        RemainingAmount = remainingAmount;
    }

}
