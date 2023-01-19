package alert.listener;

import entity.alert.Alert;

import java.util.List;

public interface IUserNotificationsListener {
    void update(Alert alert);

    List<Alert> getNotifications();

    List<Alert> getAllNotifications();
}
