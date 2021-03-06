package org.brightify.torch;

import org.brightify.torch.annotation.Entity;
import org.brightify.torch.filter.NumberProperty;
import org.brightify.torch.filter.Property;
import org.brightify.torch.util.Helper;
import org.brightify.torch.util.MigrationAssistant;

import java.util.Set;

/**
 * @author <a href="mailto:tadeas@brightify.org">Tadeas Kriz</a>
 */
public interface EntityDescription<ENTITY> {
    /**
     * Returns the ID property.
     *
     * @return ID property of the target entity.
     */
    NumberProperty<Long> getIdProperty();

    /**
     * Returns an array of properties.
     *
     * @return An array of properties in the target entity.
     */
    Property<?>[] getProperties();

    /**
     * Returns a safe class name.
     *
     * @see Helper#safeNameFromClass(Class) for meaning of the safety.
     *
     * @return Safe name of the entity.
     */
    String getSafeName();

    long getRevision();

    Entity.MigrationType getMigrationType();

    Long getEntityId(ENTITY entity);

    void setEntityId(ENTITY entity, Long id);

    Class<ENTITY> getEntityClass();

    void setFromRawEntity(TorchFactory torchFactory, ReadableRawEntity rawEntity, ENTITY entity,
                            Set<Class<?>> loadGroups) throws Exception;

    ENTITY createEmpty();

    void toRawEntity(TorchFactory torchFactory, ENTITY entity, WritableRawEntity rawEntity) throws Exception;

    void migrate(MigrationAssistant<ENTITY> assistant, long sourceRevision, long targetRevision) throws Exception;

}
