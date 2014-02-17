package org.brightify.torch.action.load;

import org.brightify.torch.util.Callback;

import java.util.List;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
public interface ListLoader<ENTITY> extends Iterable<ENTITY> {

    List<ENTITY> list();

    ENTITY single();

    void async(Callback<List<ENTITY>> callback);
}