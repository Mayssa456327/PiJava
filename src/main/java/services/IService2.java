package services;

import java.util.List;

public interface IService2<T> {
    void ajouter(T t);

    void modifier(T t);

    void supprimer(int id);

    List<T> afficher();
}
