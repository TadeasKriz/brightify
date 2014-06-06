package org.brightify.torch.compile;

import org.brightify.torch.annotation.Entity;
import org.brightify.torch.compile.migration.MigrationMethod;
import org.brightify.torch.compile.migration.MigrationPath;

import javax.lang.model.element.Element;
import java.util.List;

/**
 * @author <a href="mailto:tadeas@brightify.org">Tadeas Kriz</a>
 */
public interface EntityMirror {

    Element getElement();

    String getSimpleName();

    String getPackageName();

    String getFullName();

    String getTableName();

    boolean isDelete();

    boolean isIgnored();

    String getVersion();

    Entity.MigrationType getMigrationType();

    List<MigrationMethod> getMigrationMethods();

    List<MigrationPath> getMigrationPaths();

    List<PropertyMirror> getProperties();

    PropertyMirror getIdPropertyMirror();


}