package entity.alert;

import entity.Topic;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AlertBody {
    private final String message;
    private final Topic source;
    private final AlertType alertType;
    private LocalDateTime issuedDate;
    private boolean expires = false;
    private LocalDateTime expirationDate;

    public AlertBody(String message, Topic topic, AlertType alertType, LocalDateTime issuedDate) {
        this.message = message;
        this.alertType = alertType;
        this.source = topic;
        this.issuedDate = issuedDate;
    }

    public AlertBody(String message, Topic topic, AlertType alertType, LocalDateTime issuedDate, boolean expires, LocalDateTime expirationDate) {
        this.message = message;
        this.source = topic;
        this.alertType = alertType;
        this.issuedDate = issuedDate;

        if(expires){
            this.expires = true;
            this.expirationDate = expirationDate;
        }
    }

    public boolean isExpired(){
        return this.expires && expirationDate.isBefore(LocalDateTime.now());
    }
}
