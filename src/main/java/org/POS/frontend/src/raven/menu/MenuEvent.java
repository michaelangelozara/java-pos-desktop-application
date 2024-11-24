package org.POS.frontend.src.raven.menu;

import java.io.IOException;

/**
 *
 * @author Raven
 */
public interface MenuEvent {

    public void menuSelected(int index, int subIndex, MenuAction action) throws IOException;
}
