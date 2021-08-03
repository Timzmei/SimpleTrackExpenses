package timzmei.service;

import timzmei.entity.Transact;

import java.util.List;

public interface ServiceTransact {
    List<Transact> getTransact();

    void saveTransact(Transact transact);

    void updateTransact(Transact transact);

    void deleteTransact(Transact transact);
}
