package grabarski.shoppingcartapp.dagger;

import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Provides;

/**
 * Created by Mateusz Grabarski on 19.09.2017.
 */

public class BusModul {

    @Provides
    @Singleton
    public Bus getBus() {
        return new Bus();
    }
}
