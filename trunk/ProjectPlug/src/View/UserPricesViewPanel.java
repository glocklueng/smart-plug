/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Nicklas
 */
public class UserPricesViewPanel extends javax.swing.JPanel {

    /**
     * Creates new form UserSeePrices
     */
    public UserPricesViewPanel() {
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

        textFieldLocation = new javax.swing.JTextField();
        Location = new javax.swing.JLabel();
        buttonSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePrices = new javax.swing.JTable();
        buttonBack = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));

        textFieldLocation.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Location.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        Location.setForeground(new java.awt.Color(255, 255, 255));
        Location.setText("Location");

        buttonSearch.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        buttonSearch.setText("Search");
        buttonSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tablePrices.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablePrices);

        buttonBack.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        buttonBack.setText("Back");
        buttonBack.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Location)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonSearch)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonBack)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textFieldLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Location)
                    .addComponent(buttonSearch))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(buttonBack)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    public void addTableModel(DefaultTableModel tableModel) {
        getTablePrices().setModel(tableModel);
    }

    //Makes it possible to display an error in the view 
    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }
    
    //Makes it possible for the controller to click the button
    public void addButtonSearchListener(ActionListener buttonListener) {
        buttonSearch.addActionListener(buttonListener);
    }

    //Makes it possible for the controller to click the button
    public void addButtonBackListener(ActionListener buttonListener) {
        buttonBack.addActionListener(buttonListener);
    }

    public String getTextFieldLocation() {
        return textFieldLocation.getText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Location;
    private javax.swing.JButton buttonBack;
    private javax.swing.JButton buttonSearch;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablePrices;
    private javax.swing.JTextField textFieldLocation;
    // End of variables declaration//GEN-END:variables
    public javax.swing.JTable getTablePrices() {
        return tablePrices;
    }
}
