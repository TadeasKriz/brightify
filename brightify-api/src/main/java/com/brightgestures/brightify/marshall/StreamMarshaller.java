package com.brightgestures.brightify.marshall;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author <a href="mailto:tadeas.kriz@brainwashstudio.com">Tadeas Kriz</a>
 */
public interface StreamMarshaller<INPUT_TYPE, OUTPUT_TYPE extends INPUT_TYPE> {

    void marshall(DataOutputStream outputStream, INPUT_TYPE value) throws Exception;

    OUTPUT_TYPE unmarshall(DataInputStream inputStream) throws Exception;

}
