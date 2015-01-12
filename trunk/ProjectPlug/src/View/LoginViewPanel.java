/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Nicklas
 */
public class LoginViewPanel extends javax.swing.JPanel {

    /**
     * Creates new form LoginViewPanel
     */
    public LoginViewPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        textFieldUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        buttonLogin = new javax.swing.JButton();
        textFieldPassword = new javax.swing.JPasswordField();

        jLabel1.setText("Username");

        jLabel2.setText("Password");

        buttonLogin.setText("Login");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttonLogin)
                    .addComponent(textFieldUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(textFieldPassword))
                .addContainerGap(139, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(buttonLogin)
                .addContainerGap(157, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //Makes it possible to display an error in the view 
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    //Makes it possible for the controller to click the button
    public void addButtonLoginListener(ActionListener buttonListener) {
        buttonLogin.addActionListener(buttonListener);
    }

    public void setTextFieldPassword(String textFieldPassword) {
        this.textFieldPassword.setText(textFieldPassword);
    }

    public void setTextFieldUsername(String textFieldUsername) {
        this.textFieldUsername.setText(textFieldUsername);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField textFieldPassword;
    private javax.swing.JTextField textFieldUsername;
    // End of variables declaration//GEN-END:variables

    public String getPassword() {
        return textFieldPassword.getText();
    }

    public String getUsername() {
        return textFieldUsername.getText();
    }
}
