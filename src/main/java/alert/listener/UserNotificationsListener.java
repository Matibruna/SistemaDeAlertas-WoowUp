package alert.listener;

import entity.Topic;
import entity.alert.Alert;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@EqualsAndHashCode
public class UserNotificationsListener implements IUserNotificationsListener {

    private final List<Alert> notifications;

    public UserNotificationsListener() {
        this.notifications = new ArrayList<>();
    }

    @Override
    public void update(Alert alert) {
        notifications.add(alert);
    }

    public List<Alert> getNotifications() {
        // Se filtran las notificaciones, si una alerta fue vista o expiro, no se incluye en el arreglo retornado.
        return notifications.stream().filter(alert -> !alert.hasBeenRead() && !alert.isExpired()).collect(Collectors.toList());
    }

    public List<Alert> getAllNotifications(){
        return notifications;
    }

    public List<Alert> getPrioritizedNotifications(){
        return getNotifications().stream().sorted(comparator).collect(Collectors.toList());
    }

    Comparator<Alert> comparator = Comparator.comparing(Alert::getAlertType).thenComparing(Alert::issuedAt).reversed();

    public List<Alert> getNonExpiredNotificationsByTopic(Topic topic) {
        return notifications.stream()
                .filter(alert -> alert.isFromTopic(topic))
                .filter(alert -> !alert.isExpired())
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
