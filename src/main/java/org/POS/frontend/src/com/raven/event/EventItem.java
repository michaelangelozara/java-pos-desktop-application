package org.POS.frontend.src.com.raven.event;

import org.POS.frontend.src.com.raven.model.ModelItem;
import java.awt.Component;

public interface EventItem {

    public void itemClick(Component com, ModelItem item);
}
