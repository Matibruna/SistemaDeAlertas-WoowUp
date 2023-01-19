package alert.listener;

import entity.Topic;
import entity.User;
import entity.alert.Alert;
import entity.alert.AlertBody;
import entity.alert.AlertType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class UserNotificationsListenerTest {

    private Topic topic;
    private User user;

    private Alert alert;
    private Alert alert1;
    private Alert alert2;
    private Alert alert3;
    private Alert alert4;

    @Before
    public void setUp() throws Exception {
        user = new User("testing", "password");

        // Creamos los topics
        topic = new Topic("Tedx Talks");
        Topic topic1 = new Topic("Toughtworks podcast");

        // Fechas: creacion / expiracion
        // La mitad de las alertas estaran expiradas y la otra mitad, no. (Una no expirada, y otra que no expira)

        LocalDateTime expirationDateExpired = LocalDateTime.of(2023, 1, 18, 20, 0);
        LocalDateTime expirationDateNonExpired = LocalDateTime.of(2023, 1, 23, 16, 40);
        LocalDateTime farestIssuedDate = LocalDateTime.of(2023, 1, 15, 20, 0);
        LocalDateTime recentIssuedDate = LocalDateTime.of(2023, 1, 17, 20, 0);

        // Creación de alertas
        AlertBody alertBody = new AlertBody("Esta es una alerta predeterminada, mira nuestras ofertas!", topic1, AlertType.INFORMATIVA, farestIssuedDate, true, expirationDateExpired);
        alert = new Alert(user, alertBody, false);

        AlertBody alertBody1 = new AlertBody("¡Hemos visto que nos quedan pocas unidades de un producto que te interesó! Vuelve ahora y finaliza tu compra antes que se acaben.", topic1, AlertType.INFORMATIVA, recentIssuedDate,true, expirationDateNonExpired);
        alert1 = new Alert(user, alertBody1, false);

        AlertBody alertBody2 = new AlertBody("Estos son los estrenos del último mes!", topic, AlertType.INFORMATIVA, farestIssuedDate, true, expirationDateExpired);
        alert2 = new Alert(user, alertBody2, false);

        AlertBody alertBody3 = new AlertBody("Oferta de último momento!", topic, AlertType.URGENTE, recentIssuedDate);
        alert3 = new Alert(user, alertBody3, false);

        AlertBody alertBody4 = new AlertBody("Lamentamos informar que su suscripcion a sido dada de baja debido a un inconveniente.", topic, AlertType.URGENTE, farestIssuedDate, true, expirationDateNonExpired);
        alert4 = new Alert(user, alertBody4, true);

        user.getAlertListener().update(alert);
        user.getAlertListener().update(alert1);
        user.getAlertListener().update(alert2);
        user.getAlertListener().update(alert3);
        user.getAlertListener().update(alert4);
    }

    @Test
    public void should_update_alerts(){

        User user = Mockito.mock(User.class);
        AlertBody alertBody = Mockito.mock(AlertBody.class);

        Alert alert = new Alert(user, alertBody, false);

        UserNotificationsListener notificationsListener = new UserNotificationsListener();

        notificationsListener.update(alert);

        assertEquals(
                List.of(new Alert(user, alertBody, false)),
                notificationsListener.getNotifications());
    }

    @Test
    public void should_update_alerts_to_multiple_users(){

        User user1 = Mockito.mock(User.class);
        User user2 = new User("matias", "password");
        User user3 = new User("juan", "password");

        AlertBody alertBody = Mockito.mock(AlertBody.class);

        // Creamos 3 alertas de prueba para actualizar el componente
        Alert alert = new Alert(user1, alertBody, false);
        Alert alert1 = new Alert(user2, alertBody, false);
        Alert alert2 = new Alert(user3, alertBody, false);

        UserNotificationsListener notificationsListener = new UserNotificationsListener();

        // Notificar al usuario.
        notificationsListener.update(alert);
        notificationsListener.update(alert1);
        notificationsListener.update(alert2);

        assertEquals(
                List.of(
                        new Alert(user1, alertBody, false),
                        new Alert(new User("matias", "password"), alertBody, false),
                        new Alert(new User("juan", "password"), alertBody, false)
                ),
                notificationsListener.getNotifications()
        );
    }
    @Test
    public void should_not_return_seen_notifications(){

        // Marcar alertas como leidas
        for (Alert notification : user.getNotifications()) {
            notification.markAsRead();
        }

        assertEquals(
                List.of(),
                user.getNotifications()
        );
    }

    @Test
    public void should_return_all_notifications(){

        // Todas las notificaciones son:
        // AlertBody alertBody = new AlertBody("Esta es una alerta predeterminada, mira nuestras ofertas!", topic1, AlertType.INFORMATIVA, farestIssuedDate, true, expirationDateExpired);
        // AlertBody alertBody1 = new AlertBody("Hemos visto que nos quedan pocas unidades de un producto que te interesó! Vuelve ahora y finaliza tu compra antes que se acaben.", topic1, AlertType.INFORMATIVA, recentIssuedDate,true, expirationDateNonExpired);
        // AlertBody alertBody2 = new AlertBody("Estos son los estrenos del último mes!", topic, AlertType.INFORMATIVA, farestIssuedDate, true, expirationDateExpired);
        // AlertBody alertBody3 = new AlertBody("Oferta de último momento!", topic, AlertType.URGENTE, recentIssuedDate);
        // AlertBody alertBody4 = new AlertBody("Lamentamos informar que su suscripcion a sido dada de baja debido a un inconveniente.", topic, AlertType.URGENTE, farestIssuedDate, true, expirationDateNonExpired);

        // Estas mismas deberia responder el usuario a getNotifications()
        assertEquals(
                List.of(
                        alert, alert1, alert2, alert3, alert4
                ),
                user.getAllNotifications()
        );
    }
    @Test
    public void should_not_return_expired_notifications(){

        // Las alertas expiradas son 2:
        // AlertBody alertBody = new AlertBody("Esta es una alerta predeterminada, mira nuestras ofertas!", topic1, AlertType.INFORMATIVA, farestIssuedDate, true, expirationDateExpired);
        // AlertBody alertBody2 = new AlertBody("Estos son los estrenos del último mes!", topic, AlertType.INFORMATIVA, farestIssuedDate, true, expirationDateExpired);

        // Y las alertas no expiradas son 3:
        // AlertBody alertBody1 = new AlertBody("Hemos visto que nos quedan pocas unidades de un producto que te interesó! Vuelve ahora y finaliza tu compra antes que se acaben.", topic1, AlertType.INFORMATIVA, recentIssuedDate,true, expirationDateNonExpired);
        // AlertBody alertBody3 = new AlertBody("Oferta de último momento!", topic, AlertType.URGENTE, recentIssuedDate);
        // AlertBody alertBody4 = new AlertBody("Lamentamos informar que su suscripcion a sido dada de baja debido a un inconveniente.", topic, AlertType.URGENTE, farestIssuedDate, true, expirationDateNonExpired);

        // Estas mismas deberia responder el usuario a getNotifications()
        assertEquals(
                List.of(
                        alert1,
                        alert3,
                        alert4
                        ),
                user.getNotifications()
        );
    }

    @Test
    public void should_prioritize_notifications(){

        // Deberia retornar las alertas no expiradas o vistas ordenandolas por tipo de alerta y fecha

        // Tenemos 5 Alertas, ninguna fue marcada como vista, de las cuales:
        // 2 Estan expiradas
        // 2 No expiraron aun
        // 1 No expira

        // De las 3 Alertas a tener en cuenta hay: 2 Urgentes y 1 Informativa

        // AlertBody alertBody1 = new AlertBody("Hemos visto que nos quedan pocas unidades de un producto que te interesó! Vuelve ahora y finaliza tu compra antes que se acaben.", topic1, AlertType.INFORMATIVA, recentIssuedDate,true, expirationDateNonExpired);
        // AlertBody alertBody3 = new AlertBody("Oferta de último momento!", topic, AlertType.URGENTE, recentIssuedDate);
        // AlertBody alertBody4 = new AlertBody("Lamentamos informar que su suscripcion a sido dada de baja debido a un inconveniente.", topic, AlertType.URGENTE, farestIssuedDate, true, expirationDateNonExpired);

        assertEquals(
                // Ordenando estas ultimas 3 alertas nos queda:
                List.of(
                        alert3,
                        alert4,
                        alert1
                ),
                user.getPrioritizedNotifications()
        );
    }

    @Test
    public void should_return_non_expired_alerts_by_topic(){

        assertEquals(
                // La lista nos devolvera las alertas no expiradas del Tema Tedx Talks, este tema envió 3 alertas de las cuales 1 expiro:
                // Los cuerpos de estas alertas son:
                //  AlertBody alertBody3 = new AlertBody("Oferta de último momento!", topic, AlertType.URGENTE, recentIssuedDate);
                //  AlertBody alertBody4 = new AlertBody("Lamentamos informar que su suscripcion a sido dada de baja debido a un inconveniente.", topic, AlertType.URGENTE, farestIssuedDate, true, expirationDateNonExpired);
                List.of(
                        alert3,
                        alert4
                ),
                user.getNonExpiredNotificationsByTopic(topic)
        );
    }

    @Test
    public void should_return_non_expired_alerts_by_topic_even_if_they_are_seen(){

        for (Alert notification:user.getNotifications()) {
            notification.markAsRead();
        }

        assertEquals(
                List.of(
                        alert3,
                        alert4
                ),
                user.getNonExpiredNotificationsByTopic(topic)
        );
    }

    @Test
    public void should_return_if_expired_notifications_by_topic_are_for_specific_user(){
        assertEquals(
                List.of(
                        alert3.isForSpecificUser(),
                        alert4.isForSpecificUser()
                ),
                user.getNonExpiredNotificationsByTopic(topic).stream().map(Alert::isForSpecificUser).collect(Collectors.toList())
        );
    }

    @Test
    public void should_return_if_all_notifications_are_for_specific_user(){
        assertEquals(
                List.of(
                        alert.isForSpecificUser(),
                        alert1.isForSpecificUser(),
                        alert2.isForSpecificUser(),
                        alert3.isForSpecificUser(),
                        alert4.isForSpecificUser()
                ),
                user.getAllNotifications().stream().map(Alert::isForSpecificUser).collect(Collectors.toList())
        );
    }

}
