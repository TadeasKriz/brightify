package org.brightify.torch.compile.marshall;

import com.google.inject.Inject;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JExpression;
import com.sun.codemodel.JOp;
import com.sun.codemodel.JVar;
import org.brightify.torch.Key;
import org.brightify.torch.compile.EntityContext;
import org.brightify.torch.compile.PropertyMirror;
import org.brightify.torch.compile.generate.EntityMetadataGenerator;
import org.brightify.torch.compile.util.CodeModelTypes;
import org.brightify.torch.compile.util.TypeHelper;
import org.brightify.torch.sql.TypeAffinity;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;

/**
 * @author <a href="mailto:tadeas@brightify.org">Tadeas Kriz</a>
 */
public class KeyMarshaller extends AbstractMarshaller {

    @Inject
    private EntityContext entityContext;

    @Inject
    private TypeHelper typeHelper;

    @Inject
    private Types types;

    @Inject
    private Elements elements;

    public KeyMarshaller() {
        super(Key.class);
    }

    @Override
    public boolean accepts(TypeMirror type) {
        DeclaredType key = types.getDeclaredType(typeHelper.elementOf(Key.class), types.getWildcardType(null, null));

        if (!types.isAssignable(typeHelper.getWrappedType(type), key)) {
            return false;
        }

        List<? extends TypeMirror> parameters = typeHelper.genericParameters(type);

        if (parameters.size() != 1) {
            return false;
        }

        TypeMirror keyType = parameters.iterator().next();

        return entityContext.containsEntity(keyType.toString());
    }

    @Override
    protected JExpression marshallValue(EntityMetadataGenerator.ToContentValuesHolder holder, PropertyMirror propertyMirror) {
        JExpression getValue = super.marshallValue(holder, propertyMirror);
        return JOp.cond(getValue.ne(JExpr._null()), getValue.invoke("getId"), JExpr._null());
    }

    @Override
    protected JExpression fromCursor(EntityMetadataGenerator.CreateFromCursorHolder holder, JVar index,
                                     PropertyMirror propertyMirror) {
        TypeMirror keyType = typeHelper.singleGenericParameter(propertyMirror.getType());
        return CodeModelTypes.KEY
                .staticInvoke("create")
                .arg(CodeModelTypes.ref(keyType.toString()).dotclass())
                .arg(holder.cursor.invoke("getLong").arg(index));
    }

    @Override
    protected boolean nullable(PropertyMirror propertyMirror) {
        return true;
    }

    @Override
    protected TypeAffinity getAffinity() {
        return null;
    }

    @Override
    protected JClass columnClass(PropertyMirror propertyMirror) {
        return CodeModelTypes.NUMBER_PROPERTY.narrow(CodeModelTypes.LONG);
    }

    @Override
    protected JClass columnClassImpl(PropertyMirror propertyMirror) {
        return CodeModelTypes.NUMBER_PROPERTY_IMPL.narrow(CodeModelTypes.LONG);
    }
}