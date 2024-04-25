package tn.esprit.test.services;

import tn.esprit.test.models.DemAn;

import java.sql.SQLException;
import java.util.List;

public interface IService2<T> {

    void add(T t) throws SQLException;



    void update(T t) throws SQLException;




    void delete(int id) throws SQLException;



    List<DemAn> getAll() throws SQLException;




    DemAn getById(int id) throws SQLException;


}