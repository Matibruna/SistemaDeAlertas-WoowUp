package service;

import entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserService {
    private final List<User> usuarios;

    public UserService() {
        usuarios = new ArrayList<>();
    }

    public void add(User user) throws Exception {
        if(findUserByUsername(user.getUsername()) != null){
            throw new Exception();
        }
        usuarios.add(user);
    }

    public User findUserByUsername(String username){

        for (User user : usuarios) {
            if(user.getUsername().equals(username)){
                return user;
            }
        }

        return null;
    }

    public boolean contains(User user){
        return usuarios.contains(user);
    }

}
