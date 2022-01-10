package iwish_server;
public class itemsList extends javax.swing.JFrame {

   
    public itemsList() {
        initComponents();
        this.setLocationRelativeTo(null);

    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bodyPnl = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        itemsTbl = new javax.swing.JTable();
        headerPnl = new javax.swing.JPanel();
        srchTxtfield = new javax.swing.JTextField();
        srchBtn = new javax.swing.JButton();
        addItemsBtn = new javax.swing.JButton();
        dltItemsBtn = new javax.swing.JButton();
        footerPnl = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        bkBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(450, 410));

        itemsTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item ID", "Name", "Price", "Comments"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(itemsTbl);
        if (itemsTbl.getColumnModel().getColumnCount() > 0) {
            itemsTbl.getColumnModel().getColumn(0).setResizable(false);
        }

        srchTxtfield.setMinimumSize(new java.awt.Dimension(20, 20));
        srchTxtfield.setPreferredSize(new java.awt.Dimension(70, 25));
        srchTxtfield.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srchTxtfieldActionPerformed(evt);
            }
        });
        headerPnl.add(srchTxtfield);

        srchBtn.setText("Search");
        headerPnl.add(srchBtn);

        addItemsBtn.setText("Add New Item");
        headerPnl.add(addItemsBtn);

        dltItemsBtn.setText("Delete Selected Item");
        headerPnl.add(dltItemsBtn);

        saveBtn.setText("Save");
        footerPnl.add(saveBtn);

        bkBtn.setText("Back to Server");
        bkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bkBtnActionPerformed(evt);
            }
        });
        footerPnl.add(bkBtn);

        javax.swing.GroupLayout bodyPnlLayout = new javax.swing.GroupLayout(bodyPnl);
        bodyPnl.setLayout(bodyPnlLayout);
        bodyPnlLayout.setHorizontalGroup(
            bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(headerPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(bodyPnlLayout.createSequentialGroup()
                .addComponent(footerPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        bodyPnlLayout.setVerticalGroup(
            bodyPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPnlLayout.createSequentialGroup()
                .addComponent(headerPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footerPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void srchTxtfieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srchTxtfieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_srchTxtfieldActionPerformed

    private void bkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bkBtnActionPerformed
   Iwish iw = new Iwish();
    this.dispose();
    iw.setVisible(true);
    }//GEN-LAST:event_bkBtnActionPerformed

  
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
            java.util.logging.Logger.getLogger(itemsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(itemsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(itemsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(itemsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new itemsList().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemsBtn;
    private javax.swing.JButton bkBtn;
    private javax.swing.JPanel bodyPnl;
    private javax.swing.JButton dltItemsBtn;
    private javax.swing.JPanel footerPnl;
    private javax.swing.JPanel headerPnl;
    private javax.swing.JTable itemsTbl;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton srchBtn;
    private javax.swing.JTextField srchTxtfield;
    // End of variables declaration//GEN-END:variables
}
