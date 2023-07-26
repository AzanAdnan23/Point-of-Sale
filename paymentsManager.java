
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

public class paymentsManager {
    static ArrayList<recipt> receipts = new ArrayList<recipt>();

    public void makepayment() {

        ArrayList<saledata> salesR = saleManager.sales;
        ArrayList<customerdata> customerR = customerManager.customers;

        String input, input2, customername = "";
        int salesID, customerID = 0, amountPaid = 0, total = 0, remaningAmount = 0, amountToBePaid = 0;
        boolean salesFound = false, paidcheck = false;

        input = JOptionPane.showInputDialog("Enter the Sales Id: ");

        try {
            salesID = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Sales Id must be a number.");
            return;
        }
        // checking if payment is paid then return and show message
        for (int i = 0; i < salesR.size(); i++) {
            if (salesID == salesR.get(i).SalesId) {
                if (salesR.get(i).Status.equals("Paid")) {
                    JOptionPane.showMessageDialog(null, "Sales is already paid");
                    return;
                }
            }
        }
        // checking if sales id is valid
        for (int i = 0; i < salesR.size(); i++) {
            if (salesID == salesR.get(i).SalesId) {

                if (salesFound) {
                    break;
                }
                customerID = salesR.get(i).CustomerId;

                // find cusotmer name from customer id
                for (int j = 0; j < customerR.size(); j++) {
                    if (customerID == customerR.get(j).CustomerId) {
                        customername = customerR.get(j).Name;
                        break;
                    }
                }
                salesFound = true;
            }
        }
        if (!salesFound) {
            JOptionPane.showMessageDialog(null, "Sales not found");
            return;
        }
        // finding amount paid of given sales id in recipt arraylist
        for (int i = 0; i < receipts.size(); i++) {
            if (salesID == receipts.get(i).SalesID) {
                total = receipts.get(i).TotalSale;
                amountPaid = receipts.get(i).AmountPaid;
            }
        }
        // displaying recipt details
        input2 = JOptionPane.showInputDialog(" Sales ID: " + salesID + "\n Customer Name: " + customername
                + "\n Total Sale Amount: " + total + "\n Amount Paid: " + amountPaid + "\n Remaining Amount: "
                + (total - amountPaid) + "\n Enter the Amount to be Paid: ");

        

        try {
            amountToBePaid = Integer.parseInt(input2);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Amount To Be Paid must be a number.");
            return;
        }

        remaningAmount = total - amountPaid;

        if (amountToBePaid > remaningAmount) {
            JOptionPane.showMessageDialog(null, "Amount to be paid is greater than remaining amount");
            return;
        }
        // updating amount paid and remaining amount in recipt arraylist
        for (int i = 0; i < receipts.size(); i++) {
            if (salesID == receipts.get(i).SalesID) {

                receipts.get(i).AmountPaid = amountToBePaid + amountPaid;
                receipts.get(i).RemainingAmount = total - receipts.get(i).AmountPaid;
                if ((total - receipts.get(i).AmountPaid) == 0) {
                    paidcheck = true;
                }

            }
        }
        // updating customer amount payable
        for (int j = 0; j < customerR.size(); j++) {
            if (customerID == customerR.get(j).CustomerId) {
                customerR.get(j).AmountPayable = customerR.get(j).AmountPayable - amountToBePaid;
            }
        }
        // checking if customer has paid full amount or not. If paid full amount then
        // updating status of sales to paid
        if (paidcheck == true) {
            for (int j = 0; j < salesR.size(); j++) {
                if (salesID == salesR.get(j).SalesId) {
                    salesR.get(j).Status = "Paid";
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Payment Successful");
    }

    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter("recipt.txt");
            PrintWriter pw = new PrintWriter(fw);

            for (int i = 0; i < receipts.size(); i++) {
                String line = receipts.get(i).SalesID + ";" + receipts.get(i).SalesID + ";" + receipts.get(i).TotalSale
                        + ";" + receipts.get(i).AmountPaid + ";"
                        + receipts.get(i).RemainingAmount + ";";
                pw.println(line);
            }
            pw.flush();
            pw.close();
            fw.close();
            System.out.println("Data saved to recipt file successfully!");
        } catch (IOException ioEx) {
            System.out.println("An error occurred while saving data to recipt file: " + ioEx.getMessage());
        }
    }

    public void loadFromFile() {
        try {
            FileReader fr = new FileReader("recipt.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                int reciptNo = Integer.parseInt(data[0]);
                int salesID = Integer.parseInt(data[1]);
                int totalSale = Integer.parseInt(data[2]);
                int amountPaid = Integer.parseInt(data[3]);
                int remainingAmount = Integer.parseInt(data[4]);

                recipt r = new recipt(reciptNo, salesID, totalSale, amountPaid, remainingAmount);
                receipts.add(r);
            }
            br.close();
            fr.close();
            System.out.println("Data loaded from Recipt file successfully!");
        } catch (IOException ioEx) {
            System.out.println("An error occurred while loading data from Recipt file: " + ioEx.getMessage());
        }

    }

}
