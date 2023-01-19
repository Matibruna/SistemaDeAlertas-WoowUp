package entity;

import alert.managment.AlertManager;
import alert.managment.IAlertManager;
import alert.managment.SingleUserAlertManager;
import entity.alert.AlertBody;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class Topic {
    private final String name;
    private final IAlertManager alertManager;

    public Topic(String name) {
        this.name = name;
        this.alertManager = new AlertManager();
    }

    public void notify(AlertBody alertBody){
        alertManager.notify(alertBody);
    }

    public void notifySingleUser(User user, AlertBody alertBody){
        IAlertManager alertManager = new SingleUserAlertManager();
        alertManager.suscribe(user);
        alertManager.notify(alertBody);
    }

    public List<User> getSuscribers(){
        return this.alertManager.getSuscribers();
    }

    public void suscribeUser(User user){
        alertManager.suscribe(user);
    }

    public void unsuscribeUser(User user){
        alertManager.unsuscribe(user);
    }

}
