package iwish_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleDriver;
import net.proteanit.sql.DbUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Mahmoud
 */
public class ManageItems extends javax.swing.JFrame {

    // Iwish iw = new Iwish();
    Connection con;
    Statement stmt;
    int flag = 0;
    String queryString;
    ResultSet rs;
    PreparedStatement pst;
    ResultSet rsid;
    PreparedStatement pstid;
    int id = 0;

    public ManageItems() {
        try {
            initComponents();
            showTableData();
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(HIDE_ON_CLOSE);
            this.setTitle("Manage Items");
            NAME.setEditable(false);
            PRICE.setEditable(false);
            COMMENTS.setEditable(false);

            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "Iwish", "Iwish");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            queryString = new String("select * FROM ITEM");
            rs = stmt.executeQuery(queryString);

            if (rs.next()) {
                NAME.setText(rs.getString("NAME"));
                PRICE.setText(rs.getString("PRICE"));
                COMMENTS.setText(rs.getString("COMMENTS"));

            } else {

                NAME.setEditable(false);
                PRICE.setEditable(false);
                COMMENTS.setEditable(false);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        ID = new javax.swing.JTextField();
        btn2Pnl = new javax.swing.JPanel();
        addBtn = new javax.swing.JButton();
        rmvBtn = new javax.swing.JButton();
        nxtBtn = new javax.swing.JButton();
        prvBtn = new javax.swing.JButton();
        wlc2Pnl = new javax.swing.JPanel();
        mngLbl = new javax.swing.JLabel();
        mngitemsPnl = new javax.swing.JPanel();
        itemnameLbl = new javax.swing.JLabel();
        NAME = new javax.swing.JTextField();
        priceLbl = new javax.swing.JLabel();
        PRICE = new javax.swing.JTextField();
        cmntLbl = new javax.swing.JLabel();
        COMMENTS = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        ID.setText("jTextField2");
        ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        addBtn.setText("Add Item");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addBtnMouseClicked(evt);
            }
        });
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        btn2Pnl.add(addBtn);

        rmvBtn.setText("Remove Item");
        rmvBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmvBtnActionPerformed(evt);
            }
        });
        btn2Pnl.add(rmvBtn);

        nxtBtn.setText("Next");
        nxtBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nxtBtnActionPerformed(evt);
            }
        });
        btn2Pnl.add(nxtBtn);

        prvBtn.setText("Previous");
        prvBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prvBtnActionPerformed(evt);
            }
        });
        btn2Pnl.add(prvBtn);

        mngLbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        mngLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mngLbl.setText("Manage Items");

        javax.swing.GroupLayout wlc2PnlLayout = new javax.swing.GroupLayout(wlc2Pnl);
        wlc2Pnl.setLayout(wlc2PnlLayout);
        wlc2PnlLayout.setHorizontalGroup(
            wlc2PnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wlc2PnlLayout.createSequentialGroup()
                .addGap(359, 359, 359)
                .addComponent(mngLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(420, Short.MAX_VALUE))
        );
        wlc2PnlLayout.setVerticalGroup(
            wlc2PnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wlc2PnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mngLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mngitemsPnl.setLayout(new java.awt.GridBagLayout());

        itemnameLbl.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        itemnameLbl.setText("Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 10, 0, 0);
        mngitemsPnl.add(itemnameLbl, gridBagConstraints);

        NAME.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NAMEActionPerformed(evt);
            }
        });
        NAME.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NAMEKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 4, 0, 121);
        mngitemsPnl.add(NAME, gridBagConstraints);

        priceLbl.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        priceLbl.setText("Price");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 0, 0);
        mngitemsPnl.add(priceLbl, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 121);
        mngitemsPnl.add(PRICE, gridBagConstraints);

        cmntLbl.setFont(new java.awt.Font("Adobe Hebrew", 1, 18)); // NOI18N
        cmntLbl.setText("Comments");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(31, 10, 0, 0);
        mngitemsPnl.add(cmntLbl, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 244;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 4, 50, 121);
        mngitemsPnl.add(COMMENTS, gridBagConstraints);

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Name", "Price", "Comments"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(wlc2Pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mngitemsPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn2Pnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(wlc2Pnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mngitemsPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn2Pnl, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NAMEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NAMEActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_NAMEActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        if (flag == 0) {
            addBtn.setText("Insert");

            nxtBtn.setEnabled(false);
            prvBtn.setEnabled(false);
            rmvBtn.setEnabled(false);

            NAME.setEditable(true);
            PRICE.setEditable(true);
            COMMENTS.setEditable(true);

            NAME.setText("");
            PRICE.setText("");
            COMMENTS.setText("");

            flag = 1;

        } else {

            try {

                PreparedStatement pstChk;
                ResultSet rschk;

                pstChk = con.prepareStatement("SELECT ITEMID FROM ITEM WHERE NAME = ? AND PRICE = ? AND COMMENTS = ? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                pstChk.setString(1, NAME.getText());
                pstChk.setString(2, PRICE.getText());
                pstChk.setString(3, COMMENTS.getText());
                rschk = pstChk.executeQuery();

                if (!rschk.next()) {
                    pst = con.prepareStatement("Insert Into ITEM VALUES(?,?,?,?)");

                    pstid = con.prepareStatement("select NVL(MAX(ITEMID),0)+1 FROM ITEM", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                    rsid = pstid.executeQuery();
                    while (rsid.next()) {
                        id = rsid.getInt(1);
                    }

                    try {

                        pst.setInt(1, id);
                        pst.setString(2, NAME.getText());
                        pst.setString(3, PRICE.getText());
                        pst.setString(4, COMMENTS.getText());
                        int ins = pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Successfuly Inserted Data in Database");

                    } catch (SQLException sv) {
                        JOptionPane.showMessageDialog(null, "Please enter all data \n"
                                + "     (Price should be number)");
                        PRICE.setText("");
                        NAME.setText("");
                        COMMENTS.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "This item already exist");
                }

                NAME.setEditable(false);
                PRICE.setEditable(false);
                COMMENTS.setEditable(false);

                nxtBtn.setEnabled(true);
                prvBtn.setEnabled(true);
                rmvBtn.setEnabled(true);

                //JOptionPane.showMessageDialog(null, "Successfuly Inserted Data in Database");
                addBtn.setText("Add Item");
                flag = 0;
            } catch (SQLException ex) {
                Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        showTableData();
    }//GEN-LAST:event_addBtnActionPerformed
    public void showTableData() {
        try {
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "Iwish", "Iwish");
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            queryString = new String("select * FROM ITEM");
            rs = stmt.executeQuery(queryString);
            jTable.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "ENTER ITEM NAME");
            //Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private void rmvBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmvBtnActionPerformed
try {
            
            pst = con.prepareStatement("DELETE FROM ITEM WHERE NAME=? AND COMMENTS=? AND PRICE=?");
            pst.setString(1, NAME.getText());
            pst.setString(2, COMMENTS.getText());
            pst.setString(3, PRICE.getText());
            int del = pst.executeUpdate();
            if(NAME.getText().length()==0&&COMMENTS.getText().length()==0&&PRICE.getText().length()==0){
            JOptionPane.showMessageDialog(null, "You must choose item first to delete");
            }
            else{
            JOptionPane.showMessageDialog(null, "Successfuly Deleted Data From Database");
            NAME.setText("");
            PRICE.setText("");
            COMMENTS.setText("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        showTableData();
    }//GEN-LAST:event_rmvBtnActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        //TODO add your handling code here:
        boolean a = jTable.isEditing();
        if (a == false) {
            JOptionPane.showMessageDialog(null, "YOU CAN NOT EDIT THIS TABLE");
        }
        // int selectedRow = jTable.getSelectedRow();
        // DefaultTableModel model = (DefaultTableModel)jTable.getModel();

        //NAME.setText(model.getValueAt(selectedRow, 1).toString());
        //PRICE.setText(model.getValueAt(selectedRow, 2).toString());
        //COMMENTS.setText(model.getValueAt(selectedRow, 3).toString());

    }//GEN-LAST:event_jTableMouseClicked

    private void IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDActionPerformed

    private void prvBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prvBtnActionPerformed
        try {
            // TODO add your handling code here:
            if (rs.previous()) {

                NAME.setText(rs.getString("NAME"));
                PRICE.setText(rs.getString("PRICE"));
                COMMENTS.setText(rs.getString("COMMENTS"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_prvBtnActionPerformed

    private void nxtBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nxtBtnActionPerformed
        try {
            // TODO add your handling code here:
            if (rs.next()) {

                NAME.setText(rs.getString("NAME"));
                PRICE.setText(rs.getString("PRICE"));
                COMMENTS.setText(rs.getString("COMMENTS"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ManageItems.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_nxtBtnActionPerformed

    private void addBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_addBtnMouseClicked

    private void NAMEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NAMEKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_NAMEKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ManageItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManageItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManageItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManageItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ManageItems().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField COMMENTS;
    private javax.swing.JTextField ID;
    private javax.swing.JTextField NAME;
    private javax.swing.JTextField PRICE;
    private javax.swing.JButton addBtn;
    private javax.swing.JPanel btn2Pnl;
    private javax.swing.JLabel cmntLbl;
    private javax.swing.JLabel itemnameLbl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel mngLbl;
    private javax.swing.JPanel mngitemsPnl;
    private javax.swing.JButton nxtBtn;
    private javax.swing.JLabel priceLbl;
    private javax.swing.JButton prvBtn;
    private javax.swing.JButton rmvBtn;
    private javax.swing.JPanel wlc2Pnl;
    // End of variables declaration//GEN-END:variables

    private void setEditable(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
