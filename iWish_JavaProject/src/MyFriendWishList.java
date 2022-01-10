
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import java.util.Objects;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import oracle.jdbc.OracleDriver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ahmed
 */
public class MyFriendWishList extends javax.swing.JFrame {

    int itemprice;
    String email;
    String username;
    Socket server;
    DataInputStream dis;
    PrintStream ps;
    Thread th;

    public MyFriendWishList(String email, String username) {
        try {
            initComponents();
            this.email = email;
            this.username = username;
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(HIDE_ON_CLOSE);
            TItemId.setVisible(false);
            tprevprice.setVisible(false);
            TPrice.setVisible(false);
            this.setTitle(email + " Wish List");
            //DriverManager.registerDriver(new OracleDriver());
            //con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "iwish", "iwish");

            try {
                server = new Socket("127.0.0.1", 5005);
                dis = new DataInputStream(server.getInputStream());
                ps = new PrintStream(server.getOutputStream());
            } catch (SocketException se) {
                dis.close();
                ps.close();
                if (th != null) {
                    th.stop();
                }
                JOptionPane.showMessageDialog(null, "Server is off now please try again Later");
                BtnParticipate.setEnabled(false);
                PriceField.setEditable(false);
            }
            try {
                JSONObject jsonobj = new JSONObject();
                jsonobj.put("msg", "friendWishList");
                jsonobj.put("email", email);
                ps.println(jsonobj.toString());
                ps.flush();
                System.out.println(jsonobj.toString());
                //jsonobj.put("uname", username);

            } catch (Exception ex) {
                System.out.println("Server is Closed....");
                TableWishFriend.setEnabled(false);
            }

            th = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String json = null;
                        JSONObject jsonObject = null;
                        try {
                            if (Objects.nonNull(server) && !server.isClosed()) {

                                try {
                                    json = dis.readLine();
                                } catch (SocketException e) {
                                    server.close();
                                    close_frame();
                                    break;
                                }
                                if (json != null) {
                                    try {
                                        jsonObject = new JSONObject(json);
                                    } catch (JSONException e) {
                                    }
                                    try {
                                        if (jsonObject.has("friendWishList")) {
                                            System.out.println("you are in the right class" + json);
                                            showTableData(json);
                                        }
                                    } catch (NullPointerException e) {
                                    }
                                    //System.out.println("ay 7aga");
                                } else {
                                    System.out.println("else");
                                    close_frame();
                                    //JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                                    break;
                                }
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(MyFriendWishList.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } //catch (IOException ex) {
                    //JOptionPane.showMessageDialog(null, "The Server is down now Please try Again Later");
                    //  }
                }
            });
            th.start();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server is off now please try again later");
            //Logger.getLogger(MyFriendWishList.class.getName()).log(Level.SEVERE, null, ex);
        }
        //showTableData();
    }

    /* public void showTableData() {
        try {
            //ItemDto obj = new ItemDto();

            pst = con.prepareStatement("SELECT IU.ITEMID , I.NAME , I.PRICE , I.COMMENTS , NVL(IU.CURRENT_PRICE,0) as \"CURRENT PRICE\"  FROM ITEM I , USERITEM IU WHERE I.ITEMID = IU.ITEMID AND USRID = 3");
            rs = pst.executeQuery();
            TableWish.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            Logger.getLogger(MyFriendWishList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    public void close_frame() {
        this.dispose();
    }

    public void showTableData(String wishes) {
        /*ShowFriendWishes friendwishes = new Gson().fromJson(wishes, ShowFriendWishes.class);
        System.out.println(wishes);
        System.out.println(friendwishes.friendwish);
        DefaultTableModel model = (DefaultTableModel) TableWish.getModel();
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.LEFT);
        TableWish.getColumnModel().getColumn(0).setCellRenderer(render);
        TableWish.getColumnModel().getColumn(2).setCellRenderer(render);
        System.out.println(friendwishes.friendwish);
        int i = 0;
        for (DemoFriendWishes fw : friendwishes.friendwish) {
            System.out.println(fw.ItemID);
            model.setValueAt(fw.ItemID, i, 0);
            model.setValueAt(fw.Name, i, 1);
            model.setValueAt(fw.price, i, 2);
            model.setValueAt(fw.comments, i, 3);
            model.setValueAt(fw.current_price, i, 4);
            i++;
        }*/
        System.out.println("welcome to function");
        JSONObject jsonObject = new JSONObject(wishes);
        JSONArray array = new JSONArray();

        if (jsonObject.has("friendWishList")) {
            System.out.println(wishes);
            array = (JSONArray) jsonObject.getJSONArray("friendWishList");
            System.out.println(array.toString());
            DefaultTableModel model = (DefaultTableModel) TableWishFriend.getModel();
            DefaultTableCellRenderer render = new DefaultTableCellRenderer();
            render.setHorizontalAlignment(SwingConstants.LEFT);
            TableWishFriend.getColumnModel().getColumn(0).setCellRenderer(render);
            TableWishFriend.getColumnModel().getColumn(2).setCellRenderer(render);

            Object[] row = new Object[5];

            for (int i = 0; i < array.length(); i++) {
                JSONObject rec = array.getJSONObject(i);
                for (int j = 0; j < row.length; j++) {
                    row[0] = rec.get("ID");
                    row[1] = rec.get("Name");
                    row[2] = rec.get("PRICE");
                    row[3] = rec.get("COMMENTS");
                    row[4] = rec.get("CURRENTPRICE");

                }
                model.addRow(row);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FooterPnl = new javax.swing.JPanel();
        TPrice = new javax.swing.JTextField();
        tprevprice = new javax.swing.JTextField();
        BtnParticipate = new javax.swing.JButton();
        PriceField = new javax.swing.JTextField();
        TItemId = new javax.swing.JTextField();
        BtnRefresh = new javax.swing.JButton();
        HeaderPnl = new javax.swing.JPanel();
        LFriendwishlist = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableWishFriend = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 710));

        FooterPnl.setPreferredSize(new java.awt.Dimension(400, 50));
        FooterPnl.add(TPrice);
        FooterPnl.add(tprevprice);

        BtnParticipate.setText("Participate");
        BtnParticipate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnParticipateActionPerformed(evt);
            }
        });
        FooterPnl.add(BtnParticipate);

        PriceField.setPreferredSize(new java.awt.Dimension(100, 25));
        FooterPnl.add(PriceField);
        FooterPnl.add(TItemId);

        BtnRefresh.setText("Refresh");
        BtnRefresh.setPreferredSize(new java.awt.Dimension(93, 25));
        BtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshActionPerformed(evt);
            }
        });
        FooterPnl.add(BtnRefresh);

        getContentPane().add(FooterPnl, java.awt.BorderLayout.PAGE_END);

        HeaderPnl.setPreferredSize(new java.awt.Dimension(400, 50));

        LFriendwishlist.setText("Friend Wish List");
        HeaderPnl.add(LFriendwishlist);

        getContentPane().add(HeaderPnl, java.awt.BorderLayout.PAGE_START);

        TableWishFriend.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEMID", "NAME", "PRICE", "COMMENTS", "CURRENTPRICE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableWishFriend.getTableHeader().setReorderingAllowed(false);
        TableWishFriend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableWishFriendMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableWishFriend);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnParticipateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnParticipateActionPerformed
        //String itemId 

        DefaultTableModel model = (DefaultTableModel) TableWishFriend.getModel();
        int price2 = 0;
        int i = TableWishFriend.getSelectedRow();
        ItemDto obj = new ItemDto();
        if (i >= 0) {
            String price = PriceField.getText();
            if (price.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter price to partcipate");
            } else {
                try {
                    price2 = Integer.parseInt(price.trim());
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Character is not allowed here please enter number");
                    PriceField.setText("");
                }

                String prevprice = tprevprice.getText();
                int pp = Integer.parseInt(prevprice.trim());
                if (pp != 0) {
                    if (price2 >= 0) {
                        String itemId = TItemId.getText();
                        int id = Integer.parseInt(itemId.trim());

                        int newprice = pp - price2;
                        System.out.println(price2);

                        String test = TPrice.getText();
                        int itemprice = Integer.parseInt(test.trim());

                        /*pstprice = con.prepareStatement("select PRICE FROM ITEM WHERE ITEMID = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pstprice.setString(1, itemId);
            rsprice = pstprice.executeQuery();
            while (rsprice.next()) {
            itemprice = rsprice.getInt(1);
            }*/
                        //int n = Integer.parseInt(price);
                        if (newprice >= 0) {
                            model.setValueAt(newprice, i, 4);
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("msg", "participate");
                            jsonObject.put("itemid", id);
                            jsonObject.put("payed", price2);
                            jsonObject.put("owner", email);
                            jsonObject.put("friend", username); //update Friends Wishes throw Json
                            ps.println(jsonObject.toString());
                            System.out.println(jsonObject.toString());
                            JSONObject jsonobj = new JSONObject();
                            jsonobj.put("msg", "friendWishList");
                            jsonobj.put("email", email);
                            ps.println(jsonobj.toString());
                            ps.flush();
                            //MyFriendWishList friendframe = new MyFriendWishList("", "");
                            //SwingUtilities.updateComponentTreeUI(friendframe);
                            //friendframe.setVisible(true);
                            //this.dispose();
                            //showTableData();

                            th = new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        String json = dis.readLine();
                                        System.out.println(json);

                                        /*ItemDto itemDto = new Gson().fromJson(json, ItemDto.class);
                                if (itemDto.key.equals("participate") && itemDto.receiverId == 3) {
                                    JOptionPane.showMessageDialog(null, "update Succesfully");
                                }*/
                                    } catch (IOException ex) {
                                        JOptionPane.showMessageDialog(null, "The Server is down now Please try Again Later");
                                    }

                                }
                            });
                            th.start();
                        } else {
                            JOptionPane.showMessageDialog(null, "The amount You enter is more than the remaining please enter  " + pp);
                        }

                        /*pst = con.prepareStatement("UPDATE USERITEM SET CURRENT_PRICE = ? , FRID = 2 WHERE USRID = 3 AND ITEMID = ?");
                pst.setInt(1, newprice);
                pst.setString(2, itemId);
                pst.executeUpdate();*/
                    } else {
                        JOptionPane.showMessageDialog(null, "Nagative number not allowed here please enter right number");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "This item is already completed please choose another item to partcipate");
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "Please choose Item First to participate");
        }
    }//GEN-LAST:event_BtnParticipateActionPerformed

    private void TableWishFriendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableWishFriendMouseClicked
        try {
            int selectedRow = TableWishFriend.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) TableWishFriend.getModel();

            PriceField.setText(model.getValueAt(selectedRow, 4).toString());
            TItemId.setText(model.getValueAt(selectedRow, 0).toString());
            tprevprice.setText(model.getValueAt(selectedRow, 4).toString());
            TPrice.setText(model.getValueAt(selectedRow, 2).toString());
        } catch (ArrayIndexOutOfBoundsException ae) {
            JOptionPane.showMessageDialog(null, "Server is down try again later");
        }
    }//GEN-LAST:event_TableWishFriendMouseClicked

    private void BtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) TableWishFriend.getModel();

        while (model.getRowCount() > 0) {
            for (int i = 0; i < model.getRowCount(); ++i) {
                model.removeRow(i);
            }
        }
        //TableWish.removeAll();
        JSONObject jsonobj = new JSONObject();
        jsonobj.put("msg", "friendWishList");
        jsonobj.put("email", email);
        ps.println(jsonobj.toString());
        ps.flush();
        System.out.println(jsonobj.toString());
    }//GEN-LAST:event_BtnRefreshActionPerformed

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
            java.util.logging.Logger.getLogger(MyFriendWishList.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyFriendWishList.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyFriendWishList.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyFriendWishList.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyFriendWishList("", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnParticipate;
    private javax.swing.JButton BtnRefresh;
    private javax.swing.JPanel FooterPnl;
    private javax.swing.JPanel HeaderPnl;
    private javax.swing.JLabel LFriendwishlist;
    private javax.swing.JTextField PriceField;
    private javax.swing.JTextField TItemId;
    private javax.swing.JTextField TPrice;
    private javax.swing.JTable TableWishFriend;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tprevprice;
    // End of variables declaration//GEN-END:variables
}

class ItemDto {

    public String key;
    public int senderid;
    public int receiverId;
    public int ItemID;
    public String Name;
    public String price;
    public String comments;
    public int current_price;

}

class ShowFriendWishes {

    public String key;
    public int senderid;
    public int receiverId;
    public Vector<DemoFriendWishes> friendwish = new Vector<DemoFriendWishes>();

}

class DemoFriendWishes {

    public int ItemID;
    public String Name;
    public String price;
    public String comments;
    public int current_price;
}
