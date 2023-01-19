package alert.managment;

import entity.alert.AlertBody;
import entity.User;

import java.util.List;

public interface IAlertManager {


    // Agrega un usuario que decidio recibir alertas
    void suscribe(User user);

    // Elimina un usuario que no quiere recibir mas alertas
    void unsuscribe(User user);

    // Envia una alerta a los usuarios suscritos
    void notify(AlertBody alertBody);

    // Devuelve la lista de usuarios que estan suscritos
    List<User> getSuscribers();


}
