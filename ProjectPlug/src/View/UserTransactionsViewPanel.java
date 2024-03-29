/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nicklas
 */
public class UserTransactionsViewPanel extends javax.swing.JPanel {

    /**
     * Creates new form UserTransactionsPanel
     */
    public UserTransactionsViewPanel() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransactions = new javax.swing.JTable();
        buttonBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));

        tableTransactions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableTransactions);

        buttonBack.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        buttonBack.setText("Back");
        buttonBack.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 687, Short.MAX_VALUE)
                        .addComponent(buttonBack)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(buttonBack)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    public void addTableModel(DefaultTableModel tableModel) {
        getTableTransactions().setModel(tableModel);
    }

    //Makes it possible to display an error in the view 
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    //Makes it possible for the controller to click the button
    public void addButtonBackListener(ActionListener buttonListener) {
        buttonBack.addActionListener(buttonListener);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBack;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableTransactions;
    // End of variables declaration//GEN-END:variables
    public javax.swing.JTable getTableTransactions() {
        return tableTransactions;
    }
}
