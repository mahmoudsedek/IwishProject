/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwish_server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.OracleDriver;
import com.google.gson.Gson;
import java.io.InputStreamReader;
import java.net.BindException;
import java.sql.PreparedStatement;
import java.sql.SQLIntegrityConstraintViolationException;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author USERR
 */
public class IWish_Server {

    public ServerSocket serverSocket;
    static ResultSet rsSelect;
    static Connection con;
    static PreparedStatement pst = null;
    static PreparedStatement pstID = null;
    static ResultSet rsID = null;
    Thread th = null;

    public IWish_Server() {
        try {

            try {
                serverSocket = new ServerSocket(5005);
            } catch (BindException be) {
                JOptionPane.showMessageDialog(null, "the application is probably already started");

            }
            try {
                DriverManager.registerDriver(new OracleDriver());
            } catch (SQLException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                con = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe", "Iwish", "Iwish");
            } catch (SQLException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        Socket socket;
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket s;
                        s = serverSocket.accept();
                        new ServerHandler(s);
                    } catch (SocketException e) {
                        //System.out.println("Socket exception");
                        //break;
                    } catch (IOException ex) {
                        //System.out.println("IO exception");
                        //break;
                    } catch (Exception e) {
                        System.out.println("exception");

                        break;
                    }
                }
            }
        });
        th.start();
    }

    static public String itemList(String uname) {
        //Statement statement = null;
        int id = 0;
        try {
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    id = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            //statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst = con.prepareStatement("SELECT DISTINCT I.* from ITEM I,USERITEM U WHERE I.ITEMID = U.ITEMID AND U.USRID = ?");
            pst.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rsSelect = pst.executeQuery();
            //rsSelect = statement.executeQuery("select * from ITEM I,USERITEM U WHERE I.ITEMID = U.ITEMID AND U.USRID = 3");
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            while (rsSelect.next()) {
                //System.out.println(rsSelect.getInt("ITEMID"));
                JSONObject item = new JSONObject();
                item.put("ID", rsSelect.getInt("ITEMID"));
                item.put("Name", rsSelect.getString("NAME"));
                item.put("PRICE", rsSelect.getString("PRICE"));
                item.put("COMMENTS", rsSelect.getString("COMMENTS"));
                array.put(item);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        jsonObject.put("user", uname);
        jsonObject.put("itemList", array);

        return jsonObject.toString();

    }

    static public String itemAllList() {
        Statement statement = null;
        try {
            statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rsSelect = statement.executeQuery("select * from ITEM");
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            while (rsSelect.next()) {
                System.out.println(rsSelect.getInt("ITEMID"));
                JSONObject item = new JSONObject();
                item.put("ID", rsSelect.getInt("ITEMID"));
                item.put("Name", rsSelect.getString("NAME"));
                item.put("PRICE", rsSelect.getString("PRICE"));
                item.put("COMMENTS", rsSelect.getString("COMMENTS"));
                array.put(item);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        jsonObject.put("itemAllList", array);

        return jsonObject.toString();

    }

    static public void removeItem(JSONArray values, String uname) {

        int id = 0;
        try {
            try {
                pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            } catch (SQLException ex) {
                Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    id = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            String[] val = new String[values.length()];
            for (int i = 0; i < values.length(); i++) {
                val[i] = values.optString(i);
            }
            PreparedStatement pstdelete;
            for (int i = 0; i < val.length; i++) {
                try {
                    pstdelete = con.prepareStatement("delete from USERITEM where ITEMID = ? AND USRID = ?");
                    pstdelete.setString(1, val[i]);
                    pstdelete.setInt(2, id);
                    int row = pstdelete.executeUpdate();
                    System.out.println(row);
                    con.commit();
                } catch (SQLException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static public void addItem(JSONArray values, String uname) {

        int id = 0;
        int itemprice = 0;
        try {
            try {
                pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            } catch (SQLException ex) {
                Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    id = result.getInt("USRID");
                    System.out.println(id);//get the element in column "item_code"
                    //add each item to the model
                }
            }
            String[] val = new String[values.length()];
            for (int i = 0; i < values.length(); i++) {
                val[i] = values.optString(i);
            }
            PreparedStatement pstinsert;
            for (int i = 0; i < val.length; i++) {
                try {
                    pst = con.prepareStatement("select PRICE from ITEM where ITEMID = ?");
                    pst.setString(1, val[i]);
                    ResultSet returned = pst.executeQuery();

                    if (returned != null) {
                        while (returned.next()) //go through each row that your query returns
                        {
                            itemprice = returned.getInt("PRICE");
                            System.out.println(itemprice);//get the element in column "item_code"
                            //add each item to the model
                        }
                    }
                    pstinsert = con.prepareStatement("insert into USERITEM (ITEMID,USRID,CURRENT_PRICE)VALUES (?,?,?)");
                    pstinsert.setString(1, val[i]);
                    pstinsert.setInt(2, id);
                    pstinsert.setInt(3, itemprice);
                    int row = pstinsert.executeUpdate();
                    System.out.println(row);
                    con.commit();
                } catch (SQLException ex) {
                    Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static public String friendList(String uname) {

        int id = 0;

        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    id = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            //Statement statement;
            PreparedStatement pstselect = null;
            try {
                //statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                pst = con.prepareStatement("select * from USR U,FRIENDSHIP F WHERE U.USRID = F.FRID AND F.USRID = ?");
                pst.setInt(1, id);
                rsSelect = pst.executeQuery();
                // rsSelect = statement.executeQuery("select * from USR U,FRIENDSHIP F WHERE U.USRID = F.FRID AND F.USRID = 3");
            } catch (SQLException ex) {
                Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            //PreparedStatement pstselect = con.prepareStatement ("select * from EMPLOYEES");

            while (rsSelect.next()) //go through each row that your query returns
            {
                JSONObject item = new JSONObject();
                item.put("EMAIL", rsSelect.getString("EMAIL"));
                array.put(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        jsonObject.put("user", uname);
        jsonObject.put("friendList", array);
        return jsonObject.toString();

    }

    static public void removeFriend(String Email, String uname) {
        PreparedStatement pstdelete;
        PreparedStatement pstselect;
        int id = 0;
        int uid = 0;
        try {

            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    uid = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            pstselect = con.prepareStatement("select USRID from USR where EMAIL = ?");
            pstselect.setString(1, Email);
            ResultSet returned = pstselect.executeQuery();

            if (returned != null) {
                while (returned.next()) //go through each row that your query returns
                {
                    id = returned.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            System.out.println(id);
            pstdelete = con.prepareStatement("delete from FRIENDSHIP where FRID in (?,?) and USRID in (?,?)");
            pstdelete.setInt(1, id);
            pstdelete.setInt(2, uid);
            pstdelete.setInt(3, uid);
            pstdelete.setInt(4, id);
            int row = pstdelete.executeUpdate();
            System.out.println(row);
            con.commit();

        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static public String requestList(String uname) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        int uid = 0;
        try {

            //Statement statement;      
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    uid = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            try {
                //statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                //rsSelect = statement.executeQuery("select EMAIL from USR U, FRD_REQUESTS F WHERE U.USRID = F.FRID AND F.USRID = 3");
                pst = con.prepareStatement("select EMAIL from USR U, FRD_REQUESTS F WHERE U.USRID = F.FRID AND F.USRID = ?");
                pst.setInt(1, uid);
                rsSelect = pst.executeQuery();
            } catch (SQLException ex) {
                Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            //PreparedStatement pstselect = con.prepareStatement ("select * from EMPLOYEES");

            while (rsSelect.next()) //go through each row that your query returns
            {
                JSONObject item = new JSONObject();
                item.put("EMAIL", rsSelect.getString("EMAIL"));
                array.put(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        jsonObject.put("user", uname);
        jsonObject.put("requestList", array);
        return jsonObject.toString();
    }

    static public void removeRequest(String Email, String uname) {

        PreparedStatement pstdelete;
        PreparedStatement pstselect;
        int uid = 0;
        int id = 0;
        try {
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    uid = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            pstselect = con.prepareStatement("select USRID from USR where EMAIL = ?");
            pstselect.setString(1, Email);
            ResultSet returned = pstselect.executeQuery();

            if (returned != null) {
                while (returned.next()) //go through each row that your query returns
                {
                    id = returned.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            System.out.println(id);

            pstdelete = con.prepareStatement("delete from FRD_REQUESTS where FRID =? and USRID= ?");
            pstdelete.setInt(1, id);
            pstdelete.setInt(2, uid);
            int row = pstdelete.executeUpdate();
            System.out.println(row);
            con.commit();

        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static public void acceptRequest(String Email, String uname) {
        PreparedStatement pstdelete;
        PreparedStatement pstselect;
        PreparedStatement pstinsert;
        int uid = 0;
        int id = 0;
        try {
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    uid = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            pstselect = con.prepareStatement("select USRID from USR where EMAIL = ?");
            pstselect.setString(1, Email);
            ResultSet returned = pstselect.executeQuery();

            if (returned != null) {
                while (returned.next()) //go through each row that your query returns
                {
                    id = returned.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            System.out.println(id);
            try {
                pstinsert = con.prepareStatement("insert into FRIENDSHIP (USRID,FRID)VALUES (?,?)");
                pstinsert.setInt(1, uid);
                pstinsert.setInt(2, id);
                int results = pstinsert.executeUpdate();
                System.out.println(results);
                con.commit();
            } catch (SQLException ex) {
                Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                pstinsert = con.prepareStatement("insert into FRIENDSHIP (USRID,FRID)VALUES (?,?)");
                pstinsert.setInt(1, id);
                pstinsert.setInt(2, uid);
                int results = pstinsert.executeUpdate();
                System.out.println(results);
                con.commit();
            } catch (SQLException ex) {
                Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            pstdelete = con.prepareStatement("delete from FRD_REQUESTS where FRID =? and USRID = ?");
            pstdelete.setInt(1, id);
            pstdelete.setInt(2, uid);
            int row = pstdelete.executeUpdate();
            System.out.println(row);
            con.commit();

        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static public String addFriend(String Email, String uname) {

        PreparedStatement pstselect;
        PreparedStatement pstinsert;
        int id = 0;
        int uid = 0;
        int results = 0;
        JSONObject jsonObject = new JSONObject();
        try {
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, uname);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    uid = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
            pstselect = con.prepareStatement("select USRID from USR where EMAIL = ?");
            pstselect.setString(1, Email);
            ResultSet returned = pstselect.executeQuery();

            if (returned != null) {
                while (returned.next()) //go through each row that your query returns
                {
                    id = returned.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }

                System.out.println(id);
                try {
                    pstinsert = con.prepareStatement("insert into FRD_REQUESTS (USRID,FRID)VALUES (?,?)");
                    pstinsert.setInt(1, id);
                    pstinsert.setInt(2, uid);
                    try {
                        results = pstinsert.executeUpdate();
                    } catch (SQLIntegrityConstraintViolationException ex) {
                        jsonObject.put("add", "Please enter correct Email");
                        return jsonObject.toString();
                    }
                    System.out.println(results);
                    con.commit();
                } catch (SQLException ex) {
                    Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                jsonObject.put("add", "correct");
                return jsonObject.toString();
            } else {
                jsonObject.put("add", "Please enter correct Email");
                return jsonObject.toString();

            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        jsonObject.put("add", "correct");
        return jsonObject.toString();
    }

    static public String loginUser(JSONObject usrObj) {
        //int ID = 0;
        ResultSet rs = null;
        // UserDTO usrlgn = new UserDTO();
        String name = null;
        int usrID = 0;
        try {
            String qry = "select * from usr where usrname = ? and password = ?";
            pst = con.prepareStatement(qry);
            pst.setString(1, (String) usrObj.get("username"));
            pst.setString(2, (String) usrObj.get("password"));
            rs = pst.executeQuery();

            while (rs.next()) {
                usrID = rs.getInt(1);
                name = rs.getString(2);
            }
            System.out.println("User logged with ID:" + usrID + "and Username:" + name);

        } catch (SQLException ex) {

        }

        return name;
    }

    static public String insertUser(UserDTO usrObj) {
        int ID = 0; //-------------
        try {
            System.out.println("Enter Register");
            String qryID = ("select nvl(max(USRID),0)+1 from usr");
            pstID = con.prepareStatement(qryID, rsID.TYPE_SCROLL_INSENSITIVE, rsID.CONCUR_UPDATABLE);
            rsID = pstID.executeQuery();
            while (rsID.next()) {
                ID = rsID.getInt(1);
                System.out.println(ID);
            }
            String chk_Qry = "SELECT USRID FROM USR WHERE USRNAME = ? OR EMAIL = ?";
            PreparedStatement pstChk = con.prepareStatement(chk_Qry, rsID.TYPE_SCROLL_INSENSITIVE, rsID.CONCUR_UPDATABLE);

            pstChk.setString(1, usrObj.usrname);
            pstChk.setString(2, usrObj.email);
            ResultSet rsChk = pstChk.executeQuery();
            System.out.println("check result set" + rsChk);
            /*
            while (rsChk.next()) {
             int ChkID = rsChk.getInt(1);
             System.out.println("Result of check select ---------"+ChkID);
             
             }
             */
            if (!rsChk.next()) {
                String qry = "insert into usr(USRID,USRNAME,PASSWORD, EMAIL, FNAME, LNAME) values(?,?,?,?,?,?)";
                pst = con.prepareStatement(qry);

                pst.setInt(1, ID);
                pst.setString(2, usrObj.usrname.trim());
                pst.setString(3, usrObj.password);
                pst.setString(4, usrObj.email);
                pst.setString(5, usrObj.fname.trim());
                pst.setString(6, usrObj.lname.trim());

                pst.executeUpdate();
                usrObj.regResult = "sgewgweg";
                System.out.println("insertttttttttttttttttttttttttttttttttttttt");
                //usrObj.usrID = ID;
            } else {
                System.out.println("enter else");
                usrObj.regResult = "failded";

                //JOptionPane.showMessageDialog(null, "user already exist"+rsChk);
            }

        } catch (SQLException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        String usrObjnew = new Gson().toJson(usrObj);

        //return usrObj.usrname;
        System.out.println(usrObjnew);
        return usrObjnew;
    }

    static public String friendWishList(String email) throws SQLException {
        int uid = 0;
        try {
            pst = con.prepareStatement("select USRID from USR where EMAIL = ?");
            pst.setString(1, email);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    uid = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        PreparedStatement pstSelectFriendWishes = con.prepareStatement("SELECT DISTINCT IU.ITEMID , I.NAME , I.PRICE , I.COMMENTS , CURRENT_PRICE  FROM ITEM I , USERITEM IU WHERE I.ITEMID = IU.ITEMID AND USRID = ?");

        pstSelectFriendWishes.setInt(1, uid);
        ResultSet rsFriendWishes = pstSelectFriendWishes.executeQuery();
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            while (rsFriendWishes.next()) {
                //System.out.println(rsSelect.getInt("ITEMID"));
                JSONObject item = new JSONObject();
                item.put("ID", rsFriendWishes.getInt("ITEMID"));
                item.put("Name", rsFriendWishes.getString("NAME"));
                item.put("PRICE", rsFriendWishes.getString("PRICE"));
                item.put("COMMENTS", rsFriendWishes.getString("COMMENTS"));
                item.put("CURRENTPRICE", rsFriendWishes.getString("CURRENT_PRICE"));
                array.put(item);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        jsonObject.put("friendWishList", array);
        return jsonObject.toString();

    }

    static public void UpdatePrice(int itemid, int price, String owner, String friend) {
        int FRID = 0;
        try {
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, friend);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    FRID = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
                System.out.println(FRID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        int USRID = 0;
        try {
            pst = con.prepareStatement("select USRID from USR where EMAIL = ?");
            pst.setString(1, owner);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    USRID = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
                System.out.println(USRID);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            PreparedStatement pstChk = null;
            pstChk = con.prepareStatement("SELECT IU.ITEMID FROM USERITEM IU WHERE IU.USRID = ? AND IU.FRID = ? AND IU.ITEMID = ?");
            pstChk.setInt(1, USRID);
            pstChk.setInt(2, FRID);
            pstChk.setInt(3, itemid);
            ResultSet rsChk = pstChk.executeQuery();

            if (rsChk.next()) {
                System.out.println("inside if-----------");
                PreparedStatement pstupdate = null;
                //PreparedStatement pstselectPrice = null;
                //pstselectPrice = con.prepareStatement("SELECT CURRENT_PRICE FROM USERITEM WHERE ITEMID = ? AND USRID = ?");

                pstupdate = con.prepareStatement("UPDATE USERITEM SET CURRENT_PRICE = NVL(CURRENT_PRICE,0) - ?  WHERE  USRID = ? AND ITEMID = ?");
                pstupdate.setInt(1, price);
                pstupdate.setInt(2, USRID);
                pstupdate.setInt(3, itemid);
                pstupdate.executeUpdate();
                con.commit();
            } else {
                System.out.println("inside else------------");
                PreparedStatement pstpricechk = null;
                pstpricechk = con.prepareStatement("SELECT DISTINCT CURRENT_PRICE FROM USERITEM WHERE USRID = ? AND ITEMID = ?");
                pstpricechk.setInt(1, USRID);
                pstpricechk.setInt(2, itemid);
                ResultSet rspriceChk = pstpricechk.executeQuery();

                int vcurrentPrice = 0;
                while (rspriceChk.next()) {
                    vcurrentPrice = rspriceChk.getInt(1);
                }
                System.out.println(vcurrentPrice);
                int finalprice = vcurrentPrice - price;
                System.out.println(finalprice);
                
                PreparedStatement pstInsert = null;
                pstInsert = con.prepareStatement("INSERT INTO USERITEM (ITEMID , USRID , FRID , CURRENT_PRICE) VALUES (? , ? , ? , ?)");
                pstInsert.setInt(1, itemid);
                pstInsert.setInt(2, USRID);
                pstInsert.setInt(3, FRID);
                pstInsert.setInt(4, finalprice);
                pstInsert.executeUpdate();
                con.commit();
                
                PreparedStatement pstpostInsert = null;
                pstpostInsert = con.prepareStatement("UPDATE USERITEM SET CURRENT_PRICE =  ?  WHERE  USRID = ? AND ITEMID = ?");
                pstpostInsert.setInt(1, finalprice);
                pstpostInsert.setInt(2, USRID);
                pstpostInsert.setInt(3, itemid);
                pstpostInsert.executeUpdate();
                con.commit();
                
                
                PreparedStatement pstDelete = null;
                pstDelete = con.prepareStatement("DELETE FROM USERITEM WHERE ITEMID = ? AND USRID = ? AND FRID IS NULL");
                pstDelete.setInt(1, itemid);
                pstDelete.setInt(2, USRID);
                pstDelete.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static public String part_Noftification(String name) {

        int id = 0;
        try {
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, name);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    id = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject jsonObject = new JSONObject();
        //ArrayList<String> array = new ArrayList<>();
        JSONArray array = new JSONArray();
        try {
            PreparedStatement pstNotif = null;
            ResultSet rsNotif = null;
            pstNotif = con.prepareStatement("SELECT DISTINCT I.NAME FROM ITEM I , USERITEM UI WHERE I.ITEMID = UI.ITEMID AND UI.CURRENT_PRICE = 0 AND  UI.USRID = ?");
            pstNotif.setInt(1, id);
            rsNotif = pstNotif.executeQuery();

            while (rsNotif.next()) {
                JSONObject msg = new JSONObject();
                String notification = "Your " + rsNotif.getString("NAME") + " wish has completed";
                msg.put("notifi", notification);
                array.put(msg);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        jsonObject.put("user", name);
        jsonObject.put("notification", array);
        return jsonObject.toString();
    }

    static public String part_NoftificationFriend(String name) {

        int id = 0;
        try {
            pst = con.prepareStatement("select USRID from USR where USRNAME = ?");
            pst.setString(1, name);
            ResultSet result = pst.executeQuery();

            if (result != null) {
                while (result.next()) //go through each row that your query returns
                {
                    id = result.getInt("USRID"); //get the element in column "item_code"
                    //add each item to the model
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject jsonObject = new JSONObject();
        //ArrayList<String> array = new ArrayList<>();
        JSONArray array = new JSONArray();
        try {
            PreparedStatement pstNotif = null;
            ResultSet rsNotif = null;
            pstNotif = con.prepareStatement("SELECT I.NAME, U.USRNAME FROM ITEM I , USERITEM UI, USR U WHERE I.ITEMID = UI.ITEMID AND UI.CURRENT_PRICE = 0 AND  UI.FRID = ? AND U.USRID = UI.USRID");
            pstNotif.setInt(1, id);
            rsNotif = pstNotif.executeQuery();

            while (rsNotif.next()) {
                JSONObject msg = new JSONObject();
                String notification = "Your Friend " + rsNotif.getString("USRNAME") + " " + rsNotif.getString("NAME") + "\r\n" + " item wish has completed";
                msg.put("notifiFriend", notification);
                array.put(msg);
            }
        } catch (SQLException ex) {
            Logger.getLogger(IWish_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        jsonObject.put("user", name);
        jsonObject.put("notificationFriend", array);
        return jsonObject.toString();
    }

    public static void main(String[] args) {
        // TODO code application logic here
        new IWish_Server();
    }

}

class ServerHandler extends Thread {

    DataInputStream dis;
    PrintStream ps;
    Socket socket;
    String uname;
    String name;
    static Vector<ServerHandler> clientsVector = new Vector<ServerHandler>();

    public ServerHandler(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            ServerHandler.clientsVector.add(this);
            start();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void run() {
        while (true) {

            try {

                String str = dis.readLine();
                //System.out.println(dis.readLine() + "ay 7aga");
                //UserDTO userDto = new Gson().fromJson(str, UserDTO.class);
                if (str != null) {
                    JSONObject jsonObject = new JSONObject(str);

                    if (jsonObject.has("msg")) {
                        String msg = (String) jsonObject.get("msg");
                        System.out.println(msg);
                        if (msg.equals("itemList")) {
                            System.out.println(msg);
                            //System.out.println(uname);
                            name = (String) jsonObject.get("uname");
                            System.out.println(name);
                            String items = IWish_Server.itemList(name);
                            //System.out.println(uname);
                            System.out.println(items);
                            ps.println(items);
                            //SendItem(items);
                        } else if (msg.equals("itemAllList")) {
                            System.out.println(msg);
                            String all_items = IWish_Server.itemAllList();
                            ps.println(all_items);
                            //SendItem(all_items);
                        } else if (msg.equals("removeItem")) {
                            System.out.println("recieved");
                            //String replacedStr = str.replace("[","").replace("]", "");
                            System.out.println("in if");
                            JSONArray values;
                            values = (JSONArray) jsonObject.get("values");
                            //String name = (String) jsonObject.get("uname");
                            IWish_Server.removeItem(values, name);
                        } else if (msg.equals("addItem")) {
                            System.out.println("recieved");
                            //String replacedStr = str.replace("[","").replace("]", "");
                            System.out.println("in if");
                            JSONArray values;
                            values = (JSONArray) jsonObject.get("values");
                            String name = (String) jsonObject.get("uname");
                            IWish_Server.addItem(values, name);
                        } else if (msg.equals("friendList")) {
                            System.out.println(msg);
                            //String name = (String) jsonObject.get("uname");
                            String items = IWish_Server.friendList(name);
                            ps.println(items);
                            //SendItem(items);
                        } else if (msg.equals("removeFriend")) {
                            System.out.println("recieved");
                            //String replacedStr = str.replace("[","").replace("]", "");
                            System.out.println("in if");
                            String Email = (String) jsonObject.get("Email");
                            //String name = (String) jsonObject.get("uname");
                            IWish_Server.removeFriend(Email, name);
                        } else if (msg.equals("requestList")) {
                            System.out.println(msg);
                            //String name = (String) jsonObject.get("uname");
                            String requests = IWish_Server.requestList(name);
                            ps.println(requests);
                            //SendItem(requests);
                        } else if (msg.equals("removeRequest")) {
                            System.out.println("recieved");
                            //String replacedStr = str.replace("[","").replace("]", "");
                            System.out.println("in if");
                            String Email = (String) jsonObject.get("Email");
                            //String name = (String) jsonObject.get("uname");
                            IWish_Server.removeRequest(Email, name);
                        } else if (msg.equals("acceptRequest")) {
                            System.out.println("recieved");
                            //String replacedStr = str.replace("[","").replace("]", "");
                            System.out.println("in if");
                            String Email = (String) jsonObject.get("Email");
                            //String name = (String) jsonObject.get("uname");
                            IWish_Server.acceptRequest(Email, name);
                        } else if (msg.equals("addFriend")) {
                            System.out.println("recieved");
                            //String replacedStr = str.replace("[","").replace("]", "");
                            System.out.println("in if");
                            String Email = (String) jsonObject.get("Email");
                            //String name = (String) jsonObject.get("uname");
                            String returned = IWish_Server.addFriend(Email, name);
                            ps.println(returned);
                        } else if (msg.equals("login")) {
                            System.out.println("recieved");
                            uname = IWish_Server.loginUser(jsonObject);
                            System.out.println(uname);
                            // userDto.receiverId = IWish_Server.loginUser(userDto).usrID;
                            //String json = new Gson().toJson(userDto);
                            ps.println(uname);
                        } else if (msg.equals("register")) {
                            System.out.println("recieved");
                            UserDTO user = new UserDTO();
                            user.usrname = (String) jsonObject.get("username");
                            user.password = (String) jsonObject.get("password");
                            user.email = (String) jsonObject.get("email");
                            user.fname = (String) jsonObject.get("fname");
                            user.lname = (String) jsonObject.get("lname");
                            String returned = IWish_Server.insertUser(user);
                            System.out.println("registered");
                            ps.println(returned);
                            //sendMessageToAll(returned);

                        } else if (msg.equals("friendWishList")) {
                            System.out.println("recieved");
                            String Email = (String) jsonObject.get("email");
                            String friend_items = IWish_Server.friendWishList(Email);
                            ps.println(friend_items);
                            //SendItem(friend_items);

                        } else if (msg.equals("participate")) {
                            System.out.println("recieved");
                            int itemid = (int) jsonObject.get("itemid");
                            int price = (int) jsonObject.get("payed");
                            String owner = (String) jsonObject.get("owner");
                            String friend = (String) jsonObject.get("friend");
                            IWish_Server.UpdatePrice(itemid, price, owner, friend);

                        } else if (msg.equals("notification")) {
                            System.out.println("recieved");
                            String notification = IWish_Server.part_Noftification(name);
                            ps.println(notification);
                            //SendItem(notification);

                        } else if (msg.equals("notificationFriend")) {
                            System.out.println("recieved");
                            String notification = IWish_Server.part_NoftificationFriend(name);
                            ps.println(notification);
                            //SendItem(notification);

                        }

                    }
                }
            } catch (SocketException ex) {
                try {
                    dis.close();
                    ps.close();
                    clientsVector.remove(this);
                    stop();
                    clientsVector.remove(this);
                    stop();
                } catch (IOException ex1) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    void SendItem(String items) {
        System.out.println("in function" + items);
        for (ServerHandler sh : clientsVector) {
            sh.ps.println(items);
        }
    }

    void sendMessageToAll(String msg) {
        System.out.println("login");
        for (ServerHandler ch : clientsVector) {

            ch.ps.println(msg);
        }
    }

    public static void stopServer() {

        try {

            for (ServerHandler ch : clientsVector) {
                ch.stop();
                ch.socket.close();
                ch.dis.close();
                ch.ps.close();

            }

        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class
                    .getName()).log(Level.SEVERE, null, ex);

        }
    }
}

class UserDTO {

    public String key;
    public int senderId;
    public int receiverId;
    public int usrID;
    public String usrname;
    public String password;
    public String email;
    public String fname;
    public String lname;
    public String regResult;
}
