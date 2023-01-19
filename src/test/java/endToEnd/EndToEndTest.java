package endToEnd;

import entity.Topic;
import entity.User;
import entity.alert.Alert;
import entity.alert.AlertBody;
import entity.alert.AlertType;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EndToEndTest {

    @Test
    public void update_alerts(){

        // Creamos un usuario

        User user = new User("matias", "miContraseña");

        User user1 = new User("testUser", "suContraseña");

        // Creamos un tema

        Topic topic = new Topic("Tecnologias de desarrollo");

        Topic topic1 = new Topic("Metodologias de desarrollo");

        // Suscribimos los usuarios a uno o varios temas

        topic.suscribeUser(user);

        topic1.suscribeUser(user);
        topic1.suscribeUser(user1);

        // Generamos las alertas

        // Alerta para topic - Tecnologias de desarrollo
        AlertBody alertBody = new AlertBody("Tecnologias de desarrollo ha sacado su nuevo modulo!", topic, AlertType.INFORMATIVA, LocalDateTime.now());

        // Alerta para topic1 - Metodologias de desarrollo
        AlertBody alertBody1 = new AlertBody("Metodologias de desarrollo ha publicado un nuevo articulo: Scrum y equipos agiles.", topic, AlertType.INFORMATIVA, LocalDateTime.now());

        // Notificamos a los usuarios suscritos

        topic.notify(alertBody);

        topic1.notify(alertBody1);

        // Verificamos que los usuarios hayan recibido la alerta

        // Se verificaran los cuerpos de las alertas ya que desde esta implementacion, no tenemos todos los datos del objeto Alerta generado.
        assertEquals(
                List.of(
                        alertBody,
                        alertBody1
                ),
                user.getNotifications().stream().map(Alert::getAlertBody).collect(Collectors.toList())
        );

        // Y se verifica lo mismo para el user1, que en este caso, solo estaba suscrito a topi1 - Metodologias de desarrollo.
        assertEquals(
                List.of(
                        alertBody1
                ),
                user1.getNotifications().stream().map(Alert::getAlertBody).collect(Collectors.toList())
        );

        // Validamos tambien si un usuario marca una alerta como leida, no volvera a aparecer cuando obtengamos las notificaciones.

        // Marcamos la segunda Alerta de user como leida (Podria haber sido la primera o ambas)
        user.getNotifications().get(1).markAsRead();

        // Y verificamos para ambos usuarios que ahora solo tendran como notificacion la alerta de topic1 - Metodologias de desarrollo
        assertEquals(
                List.of(
                        alertBody
                ),
                user.getNotifications().stream().map(Alert::getAlertBody).collect(Collectors.toList())
        );

        assertEquals(
                List.of(
                        alertBody1
                ),
                user1.getNotifications().stream().map(Alert::getAlertBody).collect(Collectors.toList())
        );

    }
}
