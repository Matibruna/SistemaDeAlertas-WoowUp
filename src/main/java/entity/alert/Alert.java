package entity.alert;

import entity.Topic;
import entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

// Se separó la alerta en 2 componentes donde uno tiene una relación de composición con el otro (Alert -> AlertBody)
// Ya que se le delega la responsabilidad de crear el cuerpo de la alerta a los temas,
// Pero la alerta propiamente dicha la crea un objeto IAlertManager que gestiona el envío de las alertas.

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
