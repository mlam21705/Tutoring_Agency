/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import models.Users;

/**
 *
 * @author admin
 */
public class Dao extends DBcontext {
     private Connection con;
      private String status = "Ok";
      public Dao() {
        con = new DBcontext().connection;
    }

    public Users login(String email, String password) {
        String sql = "SELECT id, email, password, role "
                + "FROM users WHERE email = ? AND password = ?";
        if (connection == null) {
            throw new IllegalStateException(
                    "Không kết nối được database. Kiểm tra DBcontext.");
        }
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                Users u = new Users();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
                return u;
            }
        } catch (Exception e) {
           status= "Error at insert book" + e.getMessage();
        }
        return null;
    }
}
