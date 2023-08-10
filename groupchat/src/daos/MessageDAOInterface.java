package daos;

import model.Message;

public interface MessageDAOInterface {
boolean saveMessage(Message message);
Message displayMessage(Message message);
boolean closeConnection();
}
