package model;

import javafx.collections.ObservableList;

@FunctionalInterface
public interface GetAllInterface<Type> {
    ObservableList<Type> getAll();
}
