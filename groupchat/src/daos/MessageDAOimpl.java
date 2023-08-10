package daos;

import model.Message;
import utils.mySQLConnection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageDAOimpl implements MessageDAOInterface{
    mySQLConnection con = new mySQLConnection();
    @Override
    public boolean saveMessage(Message message) {
        String sql = "insert into message values (?,?,?)";

        try {
        PreparedStatement prepstmt = con.getConnection().prepareStatement(sql);
            prepstmt.setString(1,message.getName());
            prepstmt.setString(2, message.getMessage());
            prepstmt.setTimestamp(3,message.getTimeStamp());

            int rowAffected = prepstmt.executeUpdate();
            if(rowAffected > 0){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Message displayMessage(Message message) {
        String sql = "select * from message";

        try {
            PreparedStatement prepstmt = con.getConnection().prepareStatement(sql);
            ResultSet rs;
            rs = prepstmt.executeQuery();
            message = new Message(rs.getString(1),rs.getString(2));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public boolean closeConnection() {
        con.closeConnection();
        return true;
    }
}
