package com.brightgestures.droidorm;

import com.brightgestures.droidorm.annotation.Entity;

import java.io.Serializable;

public class Key<T> implements Serializable {

    private long mId;
    private String mKind;

    private Key() {}

    private Key(Class<? extends T> modelClass, long id) {
        mKind = getKind(modelClass);
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public String getKind() {
        return mKind;
    }

    public static <T> Key<T> create(Class<? extends T> modelClass, long id) {
        return new Key<T>(modelClass, id);
    }

    public static <T> Key<T> create(T model) {
        return Keys.keyOf(model);
    }

    public static String getKind(Class<?> clazz) {
        String kind = getKindRecursive(clazz);
        if(kind == null) {
            throw new IllegalArgumentException("Class hierarchy for " + clazz + " has no @Entity annotation!");
        } else {
            return kind;
        }
    }

    private static String getKindRecursive(Class<?> clazz) {
        if(clazz == Object.class) {
            return null;
        }

        String kind = getKindHere(clazz);
        if(kind != null) {
            return kind;
        } else {
            return getKindRecursive(clazz.getSuperclass());
        }
    }

    private static String getKindHere(Class<?> clazz) {
        Entity entity = clazz.getAnnotation(Entity.class);
        if(entity != null) {
            if(entity.name() != null && entity.name().length() !=0) {
                return entity.name();
            } else {
                return clazz.getSimpleName();
            }
        }
        return null;
    }
}
