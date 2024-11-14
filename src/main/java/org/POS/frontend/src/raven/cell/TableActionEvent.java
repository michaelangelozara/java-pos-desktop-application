package org.POS.frontend.src.raven.cell;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author RAVEN
 */
public interface TableActionEvent {

    public void onEdit(int row);

    public void onDelete(int row);

    public void onView(int row) throws ExecutionException, InterruptedException;
}
