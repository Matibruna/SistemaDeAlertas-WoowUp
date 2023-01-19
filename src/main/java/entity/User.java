package entity;

import alert.listener.UserNotificationsListener;
import entity.alert.Alert;
import lombok.*;

import java.util.List;
import java.util.Objects;


/*
*
*  Se interpreto a la clase usuario como una clase que representa a un usuario de una aplicación como
*  podria ser facebook o instagram, claramente en estas aplicaciones un usuario no es unicamente un
*  username y contraseña pero se simplifico a estos atributos para la facilitar el uso del sistema.
*
* */
@Getter
@Setter
public class User {
    private String username;
    private String password;

    private UserNotificationsListener alertListener;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        alertListener = new UserNotificationsListener();
    }

    public List<Alert> getNotifications() {
        return alertListener.getNotifications();
    }

    public List<Alert> getAllNotifications() {
        return alertListener.getAllNotifications();
    }

    public List<Alert> getPrioritizedNotifications() {
        return alertListener.getPrioritizedNotifications();
    }

    public List<Alert> getNonExpiredNotificationsByTopic(Topic topic){
        return alertListener.getNonExpiredNotificationsByTopic(topic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }
    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
