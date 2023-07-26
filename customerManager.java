import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

public class customerManager {
    static ArrayList<customerdata> customers;

    public customerManager() {
        customers = new ArrayList<customerdata>();
    }

    public void addCustomer() {

        int maxCustomerId = 0;
        for (int i = 0; i < customers.size(); i++) {
            customerdata customer = customers.get(i);
            if (customer.CustomerId > maxCustomerId) {
                maxCustomerId = customer.CustomerId;
            }
        }
        int CustomerId = maxCustomerId + 1;
        int saleslimit = 0;
        String num;

        String Name = JOptionPane.showInputDialog("Enter Customer Name");
        String Address = JOptionPane.showInputDialog("Enter Customer Address");
        String Email = JOptionPane.showInputDialog("Enter Customer Email");
        String Phone = JOptionPane.showInputDialog("Enter Customer Phone");
        String SalesLimit = JOptionPane.showInputDialog("Enter Customer Sales Limit");

        try {
            saleslimit = Integer.parseInt(SalesLimit);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Salelimit must be a number.");
            return;
        }
        try {
            Long.parseLong(Phone);
            num = Phone;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Phone number must contain only digits");
            return;
        }
        customerdata customer = new customerdata(CustomerId, Name, Address, Email, num,
                saleslimit);

        customers.add(customer);

        System.out.println("Customer added successfully" + " Customer Id is: " + CustomerId);
    }

    public void updateCustomer() {

        String inputcustomerId;

        int customerId, salesLimit;
        boolean check = false;

        inputcustomerId = JOptionPane.showInputDialog("Enter Customer Id");

        try {
            customerId = Integer.parseInt(inputcustomerId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Id must be a number.");
            return;
        }

        for (int i = 0; i < customers.size(); i++) {
            customerdata customer = (customerdata) customers.get(i);

            if (customerId == customer.CustomerId) {
                customer.print(customerId);
                System.out.println("Customer Found \n Enter new details");

                String Name = JOptionPane.showInputDialog("Enter Customer Name");
                String Address = JOptionPane.showInputDialog("Enter Customer Address");
                String Email = JOptionPane.showInputDialog("Enter Customer Email");
                String Phone = JOptionPane.showInputDialog("Enter Customer Phone");
                String SalesLimit = JOptionPane.showInputDialog("Enter Customer Sales Limit");

                if (!Name.isEmpty()) {
                    customer.setName(Name);
                }
                if (!Address.isEmpty()) {
                    customer.setAddress(Address);
                }
                if (!Email.isEmpty()) {
                    customer.setEmail(Email);
                }
                if (!Phone.isEmpty()) {
                    try {
                        Long.parseLong(Phone);
                        customer.setPhone(Phone);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Phone number must contain only digits");
                        return;
                    }
                }
                if (!SalesLimit.isEmpty()) {
                    try {
                        salesLimit = Integer.parseInt(SalesLimit);
                        customer.setSalesLimit(salesLimit);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Error: Sales limit must be a number.");
                        return;
                    }
                }

                int confirm = JOptionPane.showConfirmDialog(null, "Do you want to save the changes?", "Confirm",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // save changes to file
                    // saveCustomerData(customers);
                    JOptionPane.showMessageDialog(null, "Customer information updated successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Changes not saved");
                }
                // customerdata customer
                // save in arraylist
                check = false;
                break;

            } else {
                check = true;
            }
        }
        if (check == true) {
            JOptionPane.showMessageDialog(null, "Customer not found");
        }
    }

    public void findCustomer() {
        String inputCustomerId = JOptionPane.showInputDialog("Enter Customer Id (leave blank if not required)");
        int customerId = -1;
        // -1 cuz when 0 is entered it will display all customers
        if (inputCustomerId != null && !inputCustomerId.isEmpty()) {
            try {
                customerId = Integer.parseInt(inputCustomerId);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Error: Customer Id must be a number.");
                return;
            }
        }
        String inputName = JOptionPane.showInputDialog("Enter Customer Name (leave blank if not required)");
        String inputAddress = JOptionPane.showInputDialog("Enter Customer Address (leave blank if not required)");
        String inputEmail = JOptionPane.showInputDialog("Enter Customer Email (leave blank if not required)");
        String inputPhone = JOptionPane.showInputDialog("Enter Customer Phone (leave blank if not required)");

        if (inputCustomerId.isEmpty() && inputName.isEmpty() && inputAddress.isEmpty() && inputEmail.isEmpty()
                && inputPhone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No search criteria entered. Returning to Customers Menu...");
            return;
        }

        boolean found = false;
        for (int i = 0; i < customers.size(); i++) {
            customerdata customer = customers.get(i);
            if ((customerId == -1 || customerId == customer.CustomerId)
                    && (inputName.isEmpty() || inputName.equals(customer.Name))
                    && (inputAddress.isEmpty() || inputAddress.equals(customer.Address))
                    && (inputEmail.isEmpty() || inputEmail.equals(customer.Email))
                    && (inputPhone.isEmpty() || inputPhone.equals(customer.Phone))) {
                customer.print(customer.CustomerId);
                found = true;
            }
        }
        if (found == false) {
            JOptionPane.showMessageDialog(null, "Customer not found");
        }
    }

    public void removeCustomer() {

        ArrayList<saledata> salesR = saleManager.sales;
        String inputCustomerId = JOptionPane.showInputDialog("Enter Customer Id");
        int customerId = 0;
        boolean check = false;

        try {
            customerId = Integer.parseInt(inputCustomerId);

            for (int i = 0; i < customers.size(); i++) {
                customerdata customer = customers.get(i);

                if (customerId == customer.CustomerId) {

                    // checkinng if customer has any sales
                    for (int j = 0; j < salesR.size(); j++) {
                        saledata sale = salesR.get(j);
                        if (customerId == sale.CustomerId) {
                            JOptionPane.showMessageDialog(null, "Customer has sales, cannot be removed");
                            return;
                        }
                    }
                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this customer?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        customers.remove(i);
                        JOptionPane.showMessageDialog(null, "Customer removed successfully");
                        check = false;
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, "Customer removal canceled");
                        return;
                    }
                } else {
                    check = true;
                }
            }
            if (check == true) {
                JOptionPane.showMessageDialog(null, "Customer not found");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Id must be a number.");
            return;
        }
    }

    public void saveCustomerToFiles() {
        try {
            FileWriter fw = new FileWriter("customer.txt");
            PrintWriter pw = new PrintWriter(fw);

            for (int i = 0; i < customers.size(); i++) {
                String Line = customers.get(i).CustomerId + ";" + customers.get(i).Name + ";" + customers.get(i).Address
                        + ";" + customers.get(i).Email + ";" + customers.get(i).Phone + ";"
                        + customers.get(i).SalesLimit + ";" + customers.get(i).AmountPayable + ";";
                pw.println(Line);
            }

            pw.flush();
            pw.close();
            fw.close();
            System.out.println("Data saved to customer file successfully!");
        } catch (IOException ioEx) {
            System.out.println("An error occurred while saving data to file: " + ioEx.getMessage());
        }
    }

    public void loaadCustomerToFiles() {
        try {
            FileReader fr = new FileReader("customer.txt");
            BufferedReader br = new BufferedReader(fr);

            String Line;
            while ((Line = br.readLine()) != null) {
                String[] data = Line.split(";");
                int CustomerId = Integer.parseInt(data[0]);
                String Name = data[1];
                String Address = data[2];
                String Email = data[3];
                String Phone = data[4];
                int SalesLimit = Integer.parseInt(data[5]);
                int AmountPayable = Integer.parseInt(data[6]);

                customerdata customer = new customerdata(CustomerId, Name, Address, Email, Phone, SalesLimit,
                        AmountPayable);
                customers.add(customer);

            }
            br.close();
            fr.close();
            System.out.println("Data loaded from customer file successfully!");
        } catch (IOException ioEx) {
            System.out.println("An error occurred while loading data from file: " + ioEx.getMessage());
        }
    }

    public void CustomerMenu() {
        String inputcustomer;
        int cc;
        boolean check = false;

        for (;;) {
            if (check == true) {
                break;
            }

            inputcustomer = JOptionPane.showInputDialog(
                    " Enter 1 to Add new Customer" + "\n Enter 2 to Update Customer details"
                            + "\n Enter 3 to Find Customer"
                            + "\n Enter 4 to Remove Existing Customer" + "\n Enter 5 to go Back to Main Menu");
            try {
                cc = Integer.parseInt(inputcustomer);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 5.");
                continue; // restart the loop
            }
            switch (cc) {
                case 1: {
                    addCustomer();
                    break;
                }
                case 2: {

                    updateCustomer();
                    break;
                }
                case 3:
                    findCustomer();
                    break;
                case 4:
                    removeCustomer();
                    break;
                case 5:
                    check = true;
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number between 1 and 5.");
                    break;

            }
        }
    }

}
