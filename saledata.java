import java.time.LocalDate;
public class saledata {
        
 String Status;
 int SalesId,CustomerId;
 LocalDate Date;

public saledata(int salesId, int customerId, LocalDate date, String status) {
    SalesId = salesId;
    CustomerId = customerId;
    Date = date;
    Status = status;
}

public int getSalesId() {
    return SalesId;
}


public int getCustomerId() {
    return CustomerId;
}

public void setCustomerId(int customerId) {
    CustomerId = customerId;
}

public LocalDate getDate() {
    return Date;
}

public void setDate(LocalDate date) {
    Date = date;
}

public String getStatus() {
    return Status;
}

public void setStatus(String status) {
    Status = status;
}
    
}
