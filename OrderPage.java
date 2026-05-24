import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OrderPage extends JFrame implements ActionListener {

    JCheckBox idli, coffee, sandwich, dosa, tea;
    JTextField q1, q2, q3, q4, q5;
    JLabel totalLabel;
    JButton orderBtn;

    double total = 0;

    OrderPage() {
        setTitle("Order Page");
        setSize(400,400);
        setLayout(new GridLayout(7,3));

        idli = new JCheckBox("Idli");
        coffee = new JCheckBox("Coffee");
        sandwich = new JCheckBox("Sandwich");
        dosa = new JCheckBox("Dosa");
        tea = new JCheckBox("Tea");

        q1 = new JTextField("0");
        q2 = new JTextField("0");
        q3 = new JTextField("0");
        q4 = new JTextField("0");
        q5 = new JTextField("0");

        add(idli); add(new JLabel("Qty")); add(q1);
        add(coffee); add(new JLabel("Qty")); add(q2);
        add(sandwich); add(new JLabel("Qty")); add(q3);
        add(dosa); add(new JLabel("Qty")); add(q4);
        add(tea); add(new JLabel("Qty")); add(q5);

        totalLabel = new JLabel("Total: 0");
        add(totalLabel);

        orderBtn = new JButton("Place Order");
        add(orderBtn);

        orderBtn.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        total = 0;

        try {
            Connection con = DBConnection.getConnection();

            if (idli.isSelected()) total += 30 * Integer.parseInt(q1.getText());
            if (coffee.isSelected()) total += 20 * Integer.parseInt(q2.getText());
            if (sandwich.isSelected()) total += 50 * Integer.parseInt(q3.getText());
            if (dosa.isSelected()) total += 60 * Integer.parseInt(q4.getText());
            if (tea.isSelected()) total += 15 * Integer.parseInt(q5.getText());

            double tax = total * 0.05;
            double finalTotal = total + tax;

            // Save transaction
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO transactions(student_id,total) VALUES(?,?)"
            );
            ps.setString(1, "ST101");
            ps.setDouble(2, finalTotal);
            ps.executeUpdate();

            // Update inventory
            updateStock(con, "Idli", q1);
            updateStock(con, "Coffee", q2);
            updateStock(con, "Sandwich", q3);
            updateStock(con, "Dosa", q4);
            updateStock(con, "Tea", q5);

            JOptionPane.showMessageDialog(this,
                "Total: " + total +
                "\nTax: " + tax +
                "\nFinal: " + finalTotal
            );

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    void updateStock(Connection con, String item, JTextField qty) {
        try {
            int q = Integer.parseInt(qty.getText());
            PreparedStatement ps = con.prepareStatement(
                "UPDATE inventory SET stock = stock - ? WHERE item_name=?"
            );
            ps.setInt(1, q);
            ps.setString(2, item);
            ps.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}