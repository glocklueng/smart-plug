/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import java.awt.event.ActionListener;

/**
 *
 * @author Dan
 */
public class AdminViewPanel extends javax.swing.JPanel {

    /**
     * Creates new form AdminView
     */
    public AdminViewPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    public void addButtonCreateCustomerListener(ActionListener buttonListener) {
        buttonCreateCustomer.addActionListener(buttonListener);
    }
    
    public void addButtonEditCustomerListener(ActionListener buttonListener) {
        buttonEditCustomer.addActionListener(buttonListener);
    }
    
     public void addButtonCreatePriceListener(ActionListener buttonListener) {
        buttonCreatePrice.addActionListener(buttonListener);
    }
     
      public void addButtonEditPricesListener(ActionListener buttonListener) {
        buttonEditPrices.addActionListener(buttonListener);
    }
      
       public void addButtonSeeTransactionsListener(ActionListener buttonListener) {
        buttonSeeTransactions.addActionListener(buttonListener);
    }
       
       public void addButtonLogOffListener(ActionListener buttonListener) {
        buttonLogOff.addActionListener(buttonListener);
    }
       
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonLogOff = new javax.swing.JButton();
        buttonCreateCustomer = new javax.swing.JButton();
        buttonEditCustomer = new javax.swing.JButton();
        buttonCreatePrice = new javax.swing.JButton();
        buttonEditPrices = new javax.swing.JButton();
        buttonSeeTransactions = new javax.swing.JButton();

        buttonLogOff.setText("Log off");

        buttonCreateCustomer.setText("Create customer");

        buttonEditCustomer.setText("Edit customers");

        buttonCreatePrice.setText("Create price");

        buttonEditPrices.setText("Edit prices");

        buttonSeeTransactions.setText("See transactions");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonLogOff))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonCreateCustomer)
                                .addGap(18, 18, 18)
                                .addComponent(buttonEditCustomer))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonCreatePrice)
                                .addGap(18, 18, 18)
                                .addComponent(buttonEditPrices))
                            .addComponent(buttonSeeTransactions))
                        .addGap(0, 483, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonLogOff)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCreateCustomer)
                    .addComponent(buttonEditCustomer))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCreatePrice)
                    .addComponent(buttonEditPrices))
                .addGap(18, 18, 18)
                .addComponent(buttonSeeTransactions)
                .addContainerGap(283, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCreateCustomer;
    private javax.swing.JButton buttonCreatePrice;
    private javax.swing.JButton buttonEditCustomer;
    private javax.swing.JButton buttonEditPrices;
    private javax.swing.JButton buttonLogOff;
    private javax.swing.JButton buttonSeeTransactions;
    // End of variables declaration//GEN-END:variables
}
