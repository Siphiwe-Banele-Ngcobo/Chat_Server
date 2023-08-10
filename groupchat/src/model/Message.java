package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Message {
    String name,message;
    Date timeSent;
    Timestamp timeStamp;


    public Message(String name, String message) {
        this.name = name;
        this.message = message;
        long millis =System.currentTimeMillis();
//        timeSent = new Date(millis);
        timeStamp = new Timestamp(millis);
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    @Override
    public String toString() {
        return "" +
                 name + ": "+
                 message +
                "  [" + timeStamp +
                "]";
    }

}
