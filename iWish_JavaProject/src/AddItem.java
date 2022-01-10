
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import oracle.jdbc.OracleDriver;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USERR
 */
public class AddItem extends javax.swing.JFrame {

    /**
     * Creates new form AddItem
     */
    Socket server;
    DataInputStream dis;
    PrintStream ps;
    String userName;
    Thread th = null;
    ResultSet rsSelect;
    Connection con;
    DefaultListModel model;

    public AddItem(String userName) {
        initComponents();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.userName = userName;
        this.setTitle(userName);

        this.setLocationRelativeTo(null);
        try {
            server = new Socket("127.0.0.1", 5005);
        } catch (IOException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dis = new DataInputStream(server.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ps = new PrintStream(server.getOutputStream());

        } catch (IOException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "itemAllList");
        ps.println(jsonObject.toString());
        ps.flush();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String items =null;
                    try {
                        
                        try {items = dis.readLine();}
                         catch(SocketException e) {
                            server.close();
                            close_frame();
                            break;
                            }
                        if (items != null){
                        System.out.println(items);
                        show_item(items);
                    }
                         else {
                            System.out.println("else");
                           // JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                           close_frame();
                            break;
                        }
                    } catch (SocketException ex) {

                        try {
                            dis.close();
                        } catch (IOException ex1) {
                            JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                            Logger.getLogger(AddItem.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                        ps.close();
                        if (th != null) {
                            th.stop();
                        }
                        JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                        Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        th.start();
    }

    public String getname() {
        return userName;
    }

    public void setname(String name) {
        this.userName = name;
    }
    public void close_frame(){
    this.dispose();
    }

    public void show_item(String items) {
        JSONObject jsonObject = new JSONObject(items);
        JSONArray array = new JSONArray();
        if (jsonObject.has("itemAllList")) {
            array = (JSONArray) jsonObject.getJSONArray("itemAllList");
            DefaultTableModel model = (DefaultTableModel) TableWish.getModel();
            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(SwingConstants.LEFT);
            TableWish.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
            TableWish.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            Object[] row = new Object[4];
            for (int i = 0; i < array.length(); i++) {
                JSONObject rec = array.getJSONObject(i);
                for (int j = 0; j < row.length; j++) {
                    row[0] = rec.get("ID");
                    row[1] = rec.get("Name");
                    row[2] = rec.get("PRICE");
                    row[3] = rec.get("COMMENTS");
                }
                model.addRow(row);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LabelWish = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        BtnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableWish = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 710));

        LabelWish.setText("      All Items");
        LabelWish.setPreferredSize(new java.awt.Dimension(41, 41));
        getContentPane().add(LabelWish, java.awt.BorderLayout.PAGE_START);

        BtnSave.setText("Save");
        BtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(BtnSave);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        TableWish.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Item Name", "Item Price", "Description"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TableWish);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSaveActionPerformed
        // TODO add your handling code here:
        int[] row = TableWish.getSelectedRows();
        ArrayList<String> values = new ArrayList<>();
        if (row.length > 0) {
            for (int i = row.length - 1; i >= 0; i--) {
                DefaultTableModel model = (DefaultTableModel) TableWish.getModel();
                String selected1 = model.getValueAt(row[i], 0).toString();
                values.add(selected1);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "addItem");
            System.out.print(userName);
            jsonObject.put("uname", userName);
            jsonObject.put("values", values);
            String msg = jsonObject.toString();
            ps.println(msg);
            ps.flush();
            System.out.print("send");
            this.setVisible(false);
            UserProfile userProfile = new UserProfile(userName);
            userProfile.setVisible(true);
        } else {
            this.setVisible(false);
            UserProfile userProfile = new UserProfile(userName);
            userProfile.setVisible(true);
        }
    }//GEN-LAST:event_BtnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(AddItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddItem("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnSave;
    private javax.swing.JLabel LabelWish;
    private javax.swing.JTable TableWish;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
