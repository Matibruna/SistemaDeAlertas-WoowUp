package alert.managment;

import entity.User;
import entity.alert.Alert;
import entity.alert.AlertBody;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@EqualsAndHashCode
public class AlertManager implements IAlertManager {

    private final List<User> suscribers = new ArrayList<>();

    @Override
    public void suscribe(User user) {
        if(!suscribers.contains(user)){
            suscribers.add(user);
        }
    }

    @Override
    public void unsuscribe(User user) {
        suscribers.remove(user);
    }

    @Override
    public void notify(AlertBody alertBody) {
        for (User user : suscribers) {
            Alert alert = new Alert(user, alertBody, false);
            user.getAlertListener().update(alert);
        }
    }


    public List<User> getSuscribers() {
        return suscribers;
    }
}
