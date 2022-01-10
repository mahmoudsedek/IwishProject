
import com.google.gson.Gson;
import java.awt.Dialog;
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
//setmodal
// JOptionPane.showXxx

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author USERR
 */
public class UserProfile extends javax.swing.JFrame {

    /**
     * Creates new form UserProfile
     */
    Socket server;
    DataInputStream dis;
    PrintStream ps;
    String userName;
    Thread th = null;
    ResultSet rsSelect;
    Connection con;
    DefaultListModel model;
    MyFriendWishList friendWish;

    public String getname() {
        return userName;
    }

    public void setname(String name) {
        this.userName = name;
    }

    public UserProfile(String name) {
        initComponents();
        //LoginClient object = new LoginClient();
        this.userName = name;
        System.out.println(userName);
        this.setLocationRelativeTo(null);
        this.setTitle(userName);
        try {
            server = new Socket("127.0.0.1", 5005);
            ps = new PrintStream(server.getOutputStream());
            dis = new DataInputStream(server.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "itemList");
        jsonObject.put("uname", userName);
        System.out.println(userName);
        System.out.println(getname());
        //jsonObject.put("uname",userName);
        ps.println(jsonObject.toString());
        ps.flush();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("msg", "friendList");
        jsonObject.put("uname", userName);
        ps.println(jsonObject1.toString());
        ps.flush();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("msg", "requestList");
        jsonObject.put("uname", userName);
        ps.println(jsonObject2.toString());
        ps.flush();
        JSONObject jsonObject3 = new JSONObject();
        jsonObject2.put("msg", "notification");
        jsonObject.put("uname", userName);
        ps.println(jsonObject2.toString());
        ps.flush();
        JSONObject jsonObject4 = new JSONObject();
        jsonObject2.put("msg", "notificationFriend");
        jsonObject.put("uname", userName);
        ps.println(jsonObject2.toString());
        ps.flush();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String items =null;
                    try {
                        //if (dis.readLine()!=null){

                        try{items = dis.readLine();}
                         catch(SocketException e) {
                            server.close();
                            close_frame();
                            break;
                            }
                        if (items != null) {
                            System.out.println(items);
                            JSONObject jsonObject = new JSONObject(items);
                            if (jsonObject.has("itemList")) {
                                //String name = (String) jsonObject.get("user");

                                show_item(items);
                            } else if (jsonObject.has("friendList")) {
                                //String name = (String) jsonObject.get("user");
                                show_friend(items);
                            } else if (jsonObject.has("requestList")) {
                                //String name = (String) jsonObject.get("user");

                                show_requests(items);

                            } else if (jsonObject.has("notification")) {
                                //String name = (String) jsonObject.get("user");

                                show_notification(items);
                            } else if (jsonObject.has("notificationFriend")) {
                                //String name = (String) jsonObject.get("user");

                                show_notificationFriend(items);
                            } else if (jsonObject.has("add")) {
                                //String name = (String) jsonObject.get("user");
                                if (jsonObject.get("add").equals("correct")) {
                                } else {
                                    JOptionPane.showMessageDialog(null, jsonObject.get("add"));
                                }
                            }
                        } //}
                        else {
                            //  System.out.println("else user");
                            //JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                            close_frame();
                            break;
                        }
                    } catch (SocketException ex) {
                        try {
                            ex.printStackTrace();
                            dis.close();
                            ps.close();
                            if (th != null) {
                                th.stop();
                            }
                            JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                        } catch (IOException ex1) {

                            JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex1);
                        }

                    } catch (IOException ex) {
                        try {
                            dis.close();
                        } catch (IOException ex1) {
                            Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                        ps.close();
                        if (th != null) {
                            th.stop();
                        }
                        JOptionPane.showMessageDialog(null, "Server is off now please try again later");
                        Logger.getLogger(UserProfile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        );
        th.start();
    }

    public void close_frame() {
        this.dispose();
        try {
            friendWish.dispose();
        } catch (NullPointerException ex) {
        }
    }

    public void show_item(String items) {
        JSONObject jsonObject = new JSONObject(items);
        JSONArray array = new JSONArray();
        if (jsonObject.has("itemList")) {
            array = (JSONArray) jsonObject.getJSONArray("itemList");
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

    public void show_friend(String items) {
        JSONObject jsonObject = new JSONObject(items);
        JSONArray array = new JSONArray();
        if (jsonObject.has("friendList")) {
            array = (JSONArray) jsonObject.getJSONArray("friendList");
            DefaultListModel model = new DefaultListModel();
            for (int j = 0; j < array.length(); j++) {
                JSONObject rec = array.getJSONObject(j);
                String item = (String) rec.get("EMAIL");
                model.addElement(item);
            }
            List2.setModel(model);
        }
    }

    public void show_requests(String items) {
        JSONObject jsonObject = new JSONObject(items);
        JSONArray array = new JSONArray();
        if (jsonObject.has("requestList")) {
            array = (JSONArray) jsonObject.getJSONArray("requestList");
            DefaultListModel model = new DefaultListModel();
            for (int j = 0; j < array.length(); j++) {
                JSONObject rec = array.getJSONObject(j);
                String item = (String) rec.get("EMAIL");
                model.addElement(item);
            }
            List3.setModel(model);
        }
    }

    public void show_notification(String notifi) {
        System.out.println(notifi);
        DefaultListModel mList = new DefaultListModel();
        JSONObject jsonObject = new JSONObject(notifi);
        JSONArray array = new JSONArray();
        if (jsonObject.has("notification")) {
            array = (JSONArray) jsonObject.getJSONArray("notification");
            for (int j = 0; j < array.length(); j++) {
                JSONObject msg = array.getJSONObject(j);
                String item = (String) msg.get("notifi");
                mList.addElement(item);
            }

            partNotifList.setModel(mList);
        }
    }

    public void show_notificationFriend(String notifi) {
        System.out.println(notifi);
        DefaultListModel mList = new DefaultListModel();
        JSONObject jsonObject = new JSONObject(notifi);
        JSONArray array = new JSONArray();
        if (jsonObject.has("notificationFriend")) {
            array = (JSONArray) jsonObject.getJSONArray("notificationFriend");
            for (int j = 0; j < array.length(); j++) {
                JSONObject msg = array.getJSONObject(j);
                String item = (String) msg.get("notifiFriend");
                mList.addElement(item);
            }

            partNotifListFriend.setModel(mList);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        MyWishListPanel = new javax.swing.JPanel();
        LabelWish = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableWish = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        BtnAdd = new javax.swing.JButton();
        BtnRemove = new javax.swing.JButton();
        MyFriendsPanel = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        MyFriendsPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        List2 = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnViewWishList = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnAddfriend = new javax.swing.JButton();
        txtfieldAddFriend = new javax.swing.JTextField();
        FriendsRequestsPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        List3 = new javax.swing.JList<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnAcceptFriend = new javax.swing.JButton();
        btnIgnoreFriend = new javax.swing.JButton();
        NotificationsPanel = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        partNotifListFriend = new javax.swing.JList<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        partNotifList = new javax.swing.JList<>();
        BtnRefresh = new javax.swing.JButton();
        BtnLogout = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 710));
        getContentPane().setLayout(new java.awt.FlowLayout());

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(0, 0));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(600, 600));

        MyWishListPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        MyWishListPanel.setPreferredSize(new java.awt.Dimension(560, 560));
        MyWishListPanel.setLayout(new java.awt.BorderLayout());

        LabelWish.setText("      My Wish List");
        LabelWish.setPreferredSize(new java.awt.Dimension(41, 41));
        MyWishListPanel.add(LabelWish, java.awt.BorderLayout.PAGE_START);

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

        MyWishListPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        BtnAdd.setText("Add");
        BtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAddActionPerformed(evt);
            }
        });
        jPanel1.add(BtnAdd);

        BtnRemove.setText("Remove");
        BtnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRemoveActionPerformed(evt);
            }
        });
        jPanel1.add(BtnRemove);

        MyWishListPanel.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("My Wish List", MyWishListPanel);

        MyFriendsPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        MyFriendsPanel.setPreferredSize(new java.awt.Dimension(650, 650));

        MyFriendsPanel1.setPreferredSize(new java.awt.Dimension(560, 560));
        MyFriendsPanel1.setLayout(new java.awt.BorderLayout(0, 25));

        List2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(List2);

        MyFriendsPanel1.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jLabel3.setText("     My Friends List");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel3.setPreferredSize(new java.awt.Dimension(115, 30));
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        MyFriendsPanel1.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        btnViewWishList.setText("View Wish List ");
        btnViewWishList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewWishListActionPerformed(evt);
            }
        });
        jPanel2.add(btnViewWishList);

        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });
        jPanel2.add(btnRemove);

        btnAddfriend.setText("Add");
        btnAddfriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddfriendActionPerformed(evt);
            }
        });
        jPanel2.add(btnAddfriend);

        txtfieldAddFriend.setPreferredSize(new java.awt.Dimension(190, 25));
        jPanel2.add(txtfieldAddFriend);

        MyFriendsPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jTabbedPane2.addTab("My friends List", MyFriendsPanel1);

        FriendsRequestsPanel.setPreferredSize(new java.awt.Dimension(560, 560));
        FriendsRequestsPanel.setLayout(new java.awt.BorderLayout(0, 25));

        List3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(List3);

        FriendsRequestsPanel.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jLabel4.setText("    Friend Requests List");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel4.setPreferredSize(new java.awt.Dimension(115, 30));
        jLabel4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        FriendsRequestsPanel.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        btnAcceptFriend.setText("Accept");
        btnAcceptFriend.setPreferredSize(new java.awt.Dimension(80, 25));
        btnAcceptFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptFriendActionPerformed(evt);
            }
        });
        jPanel3.add(btnAcceptFriend);

        btnIgnoreFriend.setText("Ignore");
        btnIgnoreFriend.setPreferredSize(new java.awt.Dimension(80, 25));
        btnIgnoreFriend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIgnoreFriendActionPerformed(evt);
            }
        });
        jPanel3.add(btnIgnoreFriend);

        FriendsRequestsPanel.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jTabbedPane2.addTab("Friend Requests", FriendsRequestsPanel);

        javax.swing.GroupLayout MyFriendsPanelLayout = new javax.swing.GroupLayout(MyFriendsPanel);
        MyFriendsPanel.setLayout(MyFriendsPanelLayout);
        MyFriendsPanelLayout.setHorizontalGroup(
            MyFriendsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
        );
        MyFriendsPanelLayout.setVerticalGroup(
            MyFriendsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyFriendsPanelLayout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addGap(45, 45, 45))
        );

        jTabbedPane1.addTab("My Friends", MyFriendsPanel);

        NotificationsPanel.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        NotificationsPanel.setPreferredSize(new java.awt.Dimension(560, 560));
        NotificationsPanel.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(275);

        jPanel4.setLayout(new java.awt.BorderLayout(0, 20));

        jLabel2.setText("     Your Friends Wish List Notification");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel2.setPreferredSize(new java.awt.Dimension(206, 25));
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        partNotifListFriend.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(partNotifListFriend);

        jPanel4.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout(0, 20));

        jLabel1.setText("     Your Wish List Notification");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jLabel1.setPreferredSize(new java.awt.Dimension(156, 25));
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        partNotifList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(partNotifList);

        jPanel5.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel5);

        NotificationsPanel.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Notifications", NotificationsPanel);

        getContentPane().add(jTabbedPane1);

        BtnRefresh.setText("Refresh");
        BtnRefresh.setPreferredSize(new java.awt.Dimension(90, 40));
        BtnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshActionPerformed(evt);
            }
        });
        getContentPane().add(BtnRefresh);

        BtnLogout.setText("Log Out");
        BtnLogout.setPreferredSize(new java.awt.Dimension(90, 40));
        BtnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLogoutActionPerformed(evt);
            }
        });
        getContentPane().add(BtnLogout);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRemoveActionPerformed
        // TODO add your handling code here:
        int[] row = TableWish.getSelectedRows();
        ArrayList<String> values = new ArrayList<>();
        if (row.length > 0) {
            for (int i = row.length - 1; i >= 0; i--) {
                DefaultTableModel model = (DefaultTableModel) TableWish.getModel();
                String selected1 = model.getValueAt(row[i], 0).toString();
                values.add(selected1);
                model.removeRow(row[i]);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "removeItem");
        jsonObject.put("uname", userName);
        jsonObject.put("values", values);
        String msg = jsonObject.toString();
        ps.println(msg);
        ps.flush();
        System.out.print("send");


    }//GEN-LAST:event_BtnRemoveActionPerformed

    private void BtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAddActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        System.out.println(userName);
        AddItem itemFrame = new AddItem(userName);
        itemFrame.setVisible(true);
    }//GEN-LAST:event_BtnAddActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        String Email = List2.getSelectedValue();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "removeFriend");
        jsonObject.put("uname", userName);
        jsonObject.put("Email", Email);
        String msg = jsonObject.toString();
        ps.println(msg);
        ps.flush();
        System.out.print("send");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("msg", "friendList");
        ps.println(jsonObject1.toString());
        ps.flush();

    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnAcceptFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptFriendActionPerformed
        String Email = List3.getSelectedValue();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "acceptRequest");
        jsonObject.put("uname", userName);
        jsonObject.put("Email", Email);
        String msg = jsonObject.toString();
        ps.println(msg);
        ps.flush();
        System.out.print("send");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("msg", "requestList");
        jsonObject.put("uname", userName);
        ps.println(jsonObject1.toString());
        ps.flush();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("msg", "friendList");
        jsonObject.put("uname", userName);
        ps.println(jsonObject2.toString());
        ps.flush();
    }//GEN-LAST:event_btnAcceptFriendActionPerformed

    private void btnIgnoreFriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIgnoreFriendActionPerformed
        String Email = List3.getSelectedValue();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "removeRequest");
        jsonObject.put("uname", userName);
        jsonObject.put("Email", Email);
        String msg = jsonObject.toString();
        ps.println(msg);
        ps.flush();
        System.out.print("send");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("msg", "requestList");
        jsonObject.put("uname", userName);
        ps.println(jsonObject1.toString());
        ps.flush();

    }//GEN-LAST:event_btnIgnoreFriendActionPerformed

    private void btnAddfriendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddfriendActionPerformed
        String Email = txtfieldAddFriend.getText();
        if (Email != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "addFriend");
            jsonObject.put("uname", userName);
            jsonObject.put("Email", Email);
            String msg = jsonObject.toString();
            ps.println(msg);
            ps.flush();
            txtfieldAddFriend.setText("");
        }
    }//GEN-LAST:event_btnAddfriendActionPerformed

    private void btnViewWishListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewWishListActionPerformed
        // TODO add your handling code here:
        String selected = List2.getSelectedValue();
        if (selected != null) {
            friendWish = new MyFriendWishList(selected, userName);
            //this.setVisible(false);
            friendWish.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Please select one of your friends");
        }
    }//GEN-LAST:event_btnViewWishListActionPerformed

    private void BtnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) TableWish.getModel();

        while (model.getRowCount() > 0) {
            for (int i = 0; i < model.getRowCount(); ++i) {
                model.removeRow(i);
            }
        }
        //TableWish.removeAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "itemList");
        jsonObject.put("uname", userName);
        System.out.println(userName);
        System.out.println(getname());
        //jsonObject.put("uname",userName);
        ps.println(jsonObject.toString());
        ps.flush();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("msg", "friendList");
        jsonObject.put("uname", userName);
        ps.println(jsonObject1.toString());
        ps.flush();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("msg", "requestList");
        jsonObject.put("uname", userName);
        ps.println(jsonObject2.toString());
        ps.flush();
        JSONObject jsonObject3 = new JSONObject();
        jsonObject2.put("msg", "notification");
        jsonObject.put("uname", userName);
        ps.println(jsonObject2.toString());
        ps.flush();
        JSONObject jsonObject4 = new JSONObject();
        jsonObject2.put("msg", "notificationFriend");
        jsonObject.put("uname", userName);
        ps.println(jsonObject2.toString());
        ps.flush();
    }//GEN-LAST:event_BtnRefreshActionPerformed

    private void BtnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLogoutActionPerformed
        try {
            // TODO add your handling code here:
            ps.close();
            dis.close();
            server.close();
            th.stop();
            this.setVisible(false);
            LoginClient login = new LoginClient();
            login.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(UserProfile.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_BtnLogoutActionPerformed

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
            java.util.logging.Logger.getLogger(UserProfile.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserProfile.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserProfile.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserProfile.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserProfile("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnAdd;
    private javax.swing.JButton BtnLogout;
    private javax.swing.JButton BtnRefresh;
    private javax.swing.JButton BtnRemove;
    private javax.swing.JPanel FriendsRequestsPanel;
    private javax.swing.JLabel LabelWish;
    private javax.swing.JList<String> List2;
    private javax.swing.JList<String> List3;
    private javax.swing.JPanel MyFriendsPanel;
    private javax.swing.JPanel MyFriendsPanel1;
    private javax.swing.JPanel MyWishListPanel;
    private javax.swing.JPanel NotificationsPanel;
    private javax.swing.JTable TableWish;
    private javax.swing.JButton btnAcceptFriend;
    private javax.swing.JButton btnAddfriend;
    private javax.swing.JButton btnIgnoreFriend;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnViewWishList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JList<String> partNotifList;
    private javax.swing.JList<String> partNotifListFriend;
    private javax.swing.JTextField txtfieldAddFriend;
    // End of variables declaration//GEN-END:variables
}
