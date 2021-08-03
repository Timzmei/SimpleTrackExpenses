package timzmei.util;

import timzmei.entity.Transact;

import java.util.Comparator;

public class Comparators {

    public Comparator<Transact> compareByAscendingDate() {
        return new Comparator<Transact>() {
            @Override
            public int compare(Transact t0, Transact t1) {
                return t0.compareTo(t1);
            }
        };
    }

}
