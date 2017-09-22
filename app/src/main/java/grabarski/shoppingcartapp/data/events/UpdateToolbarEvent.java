package grabarski.shoppingcartapp.data.events;

import java.util.List;

import grabarski.shoppingcartapp.data.model.LineItem;

/**
 * Created by Mateusz Grabarski on 20.09.2017.
 */

public class UpdateToolbarEvent {
    private List<LineItem> lineItems;

    public UpdateToolbarEvent(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }
}
