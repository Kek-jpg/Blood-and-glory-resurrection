/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 */
package com.flurry.org.apache.avro.file;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.file.Codec;
import com.flurry.org.apache.avro.file.DeflateCodec;
import com.flurry.org.apache.avro.file.NullCodec;
import com.flurry.org.apache.avro.file.SnappyCodec;
import java.util.HashMap;
import java.util.Map;

public abstract class CodecFactory {
    private static final int DEFAULT_DEFLATE_LEVEL = -1;
    private static final Map<String, CodecFactory> REGISTERED = new HashMap();

    static {
        CodecFactory.addCodec("null", CodecFactory.nullCodec());
        CodecFactory.addCodec("deflate", CodecFactory.deflateCodec(-1));
        CodecFactory.addCodec("snappy", CodecFactory.snappyCodec());
    }

    public static CodecFactory addCodec(String string, CodecFactory codecFactory) {
        return (CodecFactory)REGISTERED.put((Object)string, (Object)codecFactory);
    }

    public static CodecFactory deflateCodec(int n2) {
        return new DeflateCodec.Option(n2);
    }

    public static CodecFactory fromString(String string) {
        CodecFactory codecFactory = (CodecFactory)REGISTERED.get((Object)string);
        if (codecFactory == null) {
            throw new AvroRuntimeException("Unrecognized codec: " + string);
        }
        return codecFactory;
    }

    public static CodecFactory nullCodec() {
        return NullCodec.OPTION;
    }

    public static CodecFactory snappyCodec() {
        return new SnappyCodec.Option();
    }

    protected abstract Codec createInstance();

    public String toString() {
        return this.createInstance().toString();
    }
}

