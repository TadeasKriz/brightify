package com.brightgestures.brightify.action.load;

import com.brightgestures.brightify.Key;
import com.brightgestures.brightify.Result;

import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
public interface Loader {

    Loader group(Class<?> loadGroup);
    Loader groups(Class<?>... loadGroups);

    <ENTITY> TypedFilterOrderLimitListLoader<ENTITY> type(Class<ENTITY> entityClass);

    <ENTITY> Result<ENTITY> key(Key<ENTITY> key);
    <ENTITY> Result<List<ENTITY>> keys(Key<ENTITY>... keys);
    <ENTITY> Result<List<ENTITY>> keys(Collection<Key<ENTITY>> keys);

}
