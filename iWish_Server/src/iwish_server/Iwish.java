package iwish_server;

import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleDriver;

public class Iwish extends javax.swing.JFrame {

    IWish_Server server;
    ServerHandler sh;
    int flag = 0;
    Connection con;
    Statement stmt;
    ResultSet rs;
    PreparedStatement pst;
    
    public Iwish() {
        try {
            initComponents();
            this.setLocationRelativeTo(null);
              this.setTitle("Iwish Server");
            DriverManager.registerDriver(new OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "Iwish", "Iwish");
            pst = con.prepareStatement("select * from USR ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = pst.executeQuery();
        }
        catch (SQLException ex) {
            Logger.getLogger(Iwish.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wlcPnl = new javax.swing.JPanel();
        btnPnl = new javax.swing.JPanel();
        connBtn = new javax.swing.JButton();
        mngItemsBtn = new javax.swing.JButton();
        bodyPnl = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        wlcLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 400));

        wlcPnl.setLayout(new java.awt.BorderLayout());

        connBtn.setText("Start");
        connBtn.setPreferredSize(new java.awt.Dimension(85, 30));
        connBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connBtnActionPerformed(evt);
            }
        });
        btnPnl.add(connBtn);

        mngItemsBtn.setText("Manage Items");
        mngItemsBtn.setPreferredSize(new java.awt.Dimension(120, 30));
        mngItemsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mngItemsBtnActionPerformed(evt);
            }
        });
        btnPnl.add(mngItemsBtn);

        bodyPnl.setLayout(new java.awt.BorderLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/images.png"))); // NOI18N
        jLabel3.setAlignmentY(5.0F);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setPreferredSize(new java.awt.Dimension(1000, 200));
        bodyPnl.add(jLabel3, java.awt.BorderLayout.CENTER);

        wlcLbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        wlcLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        wlcLbl.setText("Welcome To Server");
        bodyPnl.add(wlcLbl, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            .addComponent(bodyPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
            .addComponent(wlcPnl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(wlcPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bodyPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPnl, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mngItemsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mngItemsBtnActionPerformed
        ManageItems il = new ManageItems();
        //mngItemsBtn.setEnabled(false);
   // this.setVisible(false);
        //this.dispose();
        il.setVisible(true);

    }//GEN-LAST:event_mngItemsBtnActionPerformed

    private void connBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connBtnActionPerformed
        if (flag == 0) {
            connBtn.setText("Stop");
            server = new IWish_Server();
            
 
            flag = 1;
        } else {
            try {
                connBtn.setText("Start");
                try{
                server.serverSocket.close();
                //server.th.stop();
                ServerHandler.stopServer();
                }
                catch(Exception e){
                    System.out.println("closing second Server...");
                    this.dispose();
                }
                

                //System.out.println("test test");
                flag = 0;
            } catch (Exception ex) {
                Logger.getLogger(Iwish.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }//GEN-LAST:event_connBtnActionPerformed

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
            java.util.logging.Logger.getLogger(Iwish.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Iwish.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Iwish.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Iwish.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Iwish().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bodyPnl;
    private javax.swing.JPanel btnPnl;
    private javax.swing.JButton connBtn;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton mngItemsBtn;
    private javax.swing.JLabel wlcLbl;
    private javax.swing.JPanel wlcPnl;
    // End of variables declaration//GEN-END:variables
}
