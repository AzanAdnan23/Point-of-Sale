public class saleLineItem {
 
    int LineNo, SalesId, ItemId, Quantity, Amount ;
    String Description;

    public saleLineItem(int lineNo,int salesId, int itemId, String description,   int quantity, int amount) {
        LineNo = lineNo;
        SalesId = salesId;
        ItemId = itemId;
        Quantity = quantity;
        Amount = amount;
        Description = description;
    }
    public int getLineNo() {
        return LineNo;
    }

    public void setLineNo(int lineNo) {
        LineNo = lineNo;
    }

    public int getOrderId() {
        return SalesId;
    }

    public void setOrderId(int orderId) {
        SalesId = orderId;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

 

}
