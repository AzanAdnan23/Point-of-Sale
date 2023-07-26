import javax.swing.JOptionPane;

public class customerdata {

    String Name, Address, Email, Phone;
    int CustomerId, AmountPayable, SalesLimit;

    public customerdata(int customerId, String name, String address, String email, String phone, int salesLimit) {
        CustomerId = customerId;
        Name = name;
        Address = address;
        Email = email;
        Phone = phone;
        AmountPayable = 0;
        SalesLimit = salesLimit;
    }

    public customerdata(int customerId, String name, String address, String email, String phone, int salesLimit,
            int amountPayable) {
        CustomerId = customerId;
        Name = name;
        Address = address;
        Email = email;
        Phone = phone;
        SalesLimit = salesLimit;
        AmountPayable = amountPayable;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getAmountPayable() {
        return AmountPayable;
    }

    public void setAmountPayable(int amountPayable) {
        AmountPayable = amountPayable;
    }

    public int getSalesLimit() {
        return SalesLimit;
    }

    public void setSalesLimit(int salesLimit) {
        SalesLimit = salesLimit;
    }

    public void print(int cid) {
        JOptionPane.showMessageDialog(null,
                " Customer Id: " + cid + "\n Customer Name: " + Name + "\n Customer Address: " + Address
                        + "\n Customer Email: " + Email + "\n Customer Phone: " + Phone + "\n Customer Amount Payable: "
                        + AmountPayable + "\n Customer Sales Limit: " + SalesLimit);

    }

}
