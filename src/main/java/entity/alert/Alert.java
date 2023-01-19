package entity.alert;

import entity.Topic;
import entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Alert {

    private boolean read;
    private final User target;
    private final AlertBody alertBody;
    private final boolean specificUserAlert;

    public Alert(User target, AlertBody alertBody, boolean specificUserAlert) {
        this.read = false;
        this.target = target;
        this.alertBody = alertBody;
        this.specificUserAlert = specificUserAlert;
    }

    public AlertType getAlertType(){
        return alertBody.getAlertType();
    }

    public LocalDateTime issuedAt(){
        return alertBody.getIssuedDate();
    }

    public boolean isExpired(){
        return this.alertBody.isExpired();
    }


    public void markAsRead() {
        this.read = true;
    }

    public boolean isFromTopic(Topic topic) {
        return alertBody.getSource().equals(topic);
    }

    public boolean isForSpecificUser(){
        return specificUserAlert;
    }

    public boolean hasBeenRead() {
        return read;
    }
}
