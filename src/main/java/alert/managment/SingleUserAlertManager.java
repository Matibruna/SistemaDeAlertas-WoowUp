package alert.managment;

import entity.User;
import entity.alert.Alert;
import entity.alert.AlertBody;

import java.util.List;

public class SingleUserAlertManager implements IAlertManager {

    private User target;

    @Override
    public void suscribe(User user) {
        target = user;
    }

    @Override
    public void unsuscribe(User user) {
        if(this.target.equals(user)){
            this.target = null;
        }
    }

    @Override
    public void notify(AlertBody alertBody) {

        Alert alert = new Alert(target, alertBody, true);

        target.getAlertListener().update(alert);
    }

    @Override
    public List<User> getSuscribers() {
        return target == null ? List.of() : List.of(target);
    }
}
