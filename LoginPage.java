import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin;

    public LoginPage() {

        setTitle("Login");
        setSize(400, 220);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 40, 100, 25);
        add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 40, 180, 25);
        add(txtUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 80, 100, 25);
        add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 80, 180, 25);
        add(txtPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 120, 100, 30);
        btnLogin.addActionListener(this);
        add(btnLogin);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());

        if (validateLogin(username, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password");
        }
    }

    private boolean validateLogin(String username, String password) {
        boolean isValid = false;

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                isValid = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}