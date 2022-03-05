/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.Closeable
 *  java.io.File
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.Writer
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Type
 *  java.text.DateFormat
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.FormatSchema;
import com.flurry.org.codehaus.jackson.JsonEncoding;
import com.flurry.org.codehaus.jackson.JsonFactory;
import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.PrettyPrinter;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.Versioned;
import com.flurry.org.codehaus.jackson.io.SegmentedStringWriter;
import com.flurry.org.codehaus.jackson.map.JsonMappingException;
import com.flurry.org.codehaus.jackson.map.MapperConfig;
import com.flurry.org.codehaus.jackson.map.ObjectMapper;
import com.flurry.org.codehaus.jackson.map.SerializationConfig;
import com.flurry.org.codehaus.jackson.map.SerializerFactory;
import com.flurry.org.codehaus.jackson.map.SerializerProvider;
import com.flurry.org.codehaus.jackson.map.ser.FilterProvider;
import com.flurry.org.codehaus.jackson.map.type.TypeFactory;
import com.flurry.org.codehaus.jackson.type.JavaType;
import com.flurry.org.codehaus.jackson.type.TypeReference;
import com.flurry.org.codehaus.jackson.util.BufferRecycler;
import com.flurry.org.codehaus.jackson.util.ByteArrayBuilder;
import com.flurry.org.codehaus.jackson.util.DefaultPrettyPrinter;
import com.flurry.org.codehaus.jackson.util.MinimalPrettyPrinter;
import com.flurry.org.codehaus.jackson.util.VersionUtil;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Type;
import java.text.DateFormat;

public class ObjectWriter
implements Versioned {
    protected static final PrettyPrinter NULL_PRETTY_PRINTER = new MinimalPrettyPrinter();
    protected final SerializationConfig _config;
    protected final JsonFactory _jsonFactory;
    protected final PrettyPrinter _prettyPrinter;
    protected final SerializerProvider _provider;
    protected final JavaType _rootType;
    protected final FormatSchema _schema;
    protected final SerializerFactory _serializerFactory;

    protected ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig) {
        this._config = serializationConfig;
        this._provider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._jsonFactory = objectMapper._jsonFactory;
        this._rootType = null;
        this._prettyPrinter = null;
        this._schema = null;
    }

    protected ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig, FormatSchema formatSchema) {
        this._config = serializationConfig;
        this._provider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._jsonFactory = objectMapper._jsonFactory;
        this._rootType = null;
        this._prettyPrinter = null;
        this._schema = formatSchema;
    }

    protected ObjectWriter(ObjectMapper objectMapper, SerializationConfig serializationConfig, JavaType javaType, PrettyPrinter prettyPrinter) {
        this._config = serializationConfig;
        this._provider = objectMapper._serializerProvider;
        this._serializerFactory = objectMapper._serializerFactory;
        this._jsonFactory = objectMapper._jsonFactory;
        this._rootType = javaType;
        this._prettyPrinter = prettyPrinter;
        this._schema = null;
    }

    protected ObjectWriter(ObjectWriter objectWriter, SerializationConfig serializationConfig) {
        this._config = serializationConfig;
        this._provider = objectWriter._provider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._jsonFactory = objectWriter._jsonFactory;
        this._schema = objectWriter._schema;
        this._rootType = objectWriter._rootType;
        this._prettyPrinter = objectWriter._prettyPrinter;
    }

    protected ObjectWriter(ObjectWriter objectWriter, SerializationConfig serializationConfig, JavaType javaType, PrettyPrinter prettyPrinter, FormatSchema formatSchema) {
        this._config = serializationConfig;
        this._provider = objectWriter._provider;
        this._serializerFactory = objectWriter._serializerFactory;
        this._jsonFactory = objectWriter._jsonFactory;
        this._rootType = javaType;
        this._prettyPrinter = prettyPrinter;
        this._schema = formatSchema;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private final void _configAndWriteCloseable(JsonGenerator var1, Object var2_3, SerializationConfig var3_2) throws IOException, JsonGenerationException, JsonMappingException {
        var4_4 = (Closeable)var2_3;
        try {
            if (this._rootType == null) {
                this._provider.serializeValue(var3_2, var1, var2_3, this._serializerFactory);
            } else {
                var8_7 = this._provider;
                var9_8 = this._rootType;
                var10_9 = this._serializerFactory;
                var8_7.serializeValue(var3_2, var1, var2_3, var9_8, var10_9);
            }
            if (this._schema != null) {
                var1.setSchema(this._schema);
            }
            var11_5 = var1;
            var1 = null;
            var11_5.close();
            var12_6 = var4_4;
            var4_4 = null;
            var12_6.close();
            ** if (!false) goto lbl21
        }
        catch (Throwable var5_10) {
            block16 : {
                if (var1 != null) {
                    var1.close();
                }
                break block16;
                catch (IOException var14_11) {}
lbl28: // 2 sources:
                if (false == false) return;
                try {
                    null.close();
                    return;
                }
                catch (IOException var13_12) {
                    return;
                }
                catch (IOException var7_13) {}
            }
            if (var4_4 == null) throw var5_10;
            try {
                var4_4.close();
            }
            catch (IOException var6_14) {
                throw var5_10;
            }
            throw var5_10;
        }
lbl-1000: // 1 sources:
        {
            null.close();
        }
lbl21: // 2 sources:
        ** GOTO lbl28
    }

    /*
     * Exception decompiling
     */
    private final void _writeCloseableValue(JsonGenerator jsonGenerator, Object object, SerializationConfig serializationConfig) throws IOException, JsonGenerationException, JsonMappingException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.NullPointerException: Attempt to invoke interface method 'org.benf.cfr.reader.b.a.e.u org.benf.cfr.reader.b.a.e.q.m()' on a null object reference
        // org.benf.cfr.reader.b.a.e.b.b$d.a(InferredJavaType.java:471)
        // org.benf.cfr.reader.b.a.e.b.b.g(InferredJavaType.java:1173)
        // org.benf.cfr.reader.b.a.b.a.t.<init>(CastExpression.java:29)
        // org.benf.cfr.reader.b.a.a.c.c.h.a(NakedNullCaster.java:44)
        // org.benf.cfr.reader.b.a.d.b.o.a(StructuredExpressionStatement.java:63)
        // org.benf.cfr.reader.b.a.a.c.c.h.a(NakedNullCaster.java:31)
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:680)
        // org.benf.cfr.reader.b.a.d.b.e.a(Block.java:407)
        // org.benf.cfr.reader.b.a.a.c.c.h.a(NakedNullCaster.java:30)
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:680)
        // org.benf.cfr.reader.b.a.a.c.c.h.a(NakedNullCaster.java:25)
        // org.benf.cfr.reader.b.a.a.j.l(Op04StructuredStatement.java:763)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:772)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1112)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:587)
        // java.lang.Thread.run(Thread.java:818)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void _configAndWriteValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        if (this._prettyPrinter != null) {
            PrettyPrinter prettyPrinter = this._prettyPrinter;
            if (prettyPrinter == NULL_PRETTY_PRINTER) {
                prettyPrinter = null;
            }
            jsonGenerator.setPrettyPrinter(prettyPrinter);
        } else if (this._config.isEnabled(SerializationConfig.Feature.INDENT_OUTPUT)) {
            jsonGenerator.useDefaultPrettyPrinter();
        }
        if (this._schema != null) {
            jsonGenerator.setSchema(this._schema);
        }
        if (this._config.isEnabled(SerializationConfig.Feature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            ObjectWriter.super._configAndWriteCloseable(jsonGenerator, object, this._config);
            return;
        }
        boolean bl = false;
        try {
            JavaType javaType = this._rootType;
            bl = false;
            if (javaType == null) {
                this._provider.serializeValue(this._config, jsonGenerator, object, this._serializerFactory);
            } else {
                this._provider.serializeValue(this._config, jsonGenerator, object, this._rootType, this._serializerFactory);
            }
            bl = true;
            jsonGenerator.close();
            if (bl) return;
        }
        catch (Throwable throwable) {
            if (bl) throw throwable;
            try {
                jsonGenerator.close();
            }
            catch (IOException iOException) {
                throw throwable;
            }
            throw throwable;
        }
        try {
            jsonGenerator.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public boolean canSerialize(Class<?> class_) {
        return this._provider.hasSerializerFor(this._config, class_, this._serializerFactory);
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor(this.getClass());
    }

    public ObjectWriter withDateFormat(DateFormat dateFormat) {
        MapperConfig mapperConfig = this._config.withDateFormat(dateFormat);
        if (mapperConfig == this._config) {
            return this;
        }
        return new ObjectWriter((ObjectWriter)this, (SerializationConfig)mapperConfig);
    }

    public ObjectWriter withDefaultPrettyPrinter() {
        return this.withPrettyPrinter(new DefaultPrettyPrinter());
    }

    public ObjectWriter withFilters(FilterProvider filterProvider) {
        if (filterProvider == this._config.getFilterProvider()) {
            return this;
        }
        return new ObjectWriter((ObjectWriter)this, this._config.withFilters(filterProvider));
    }

    public ObjectWriter withPrettyPrinter(PrettyPrinter prettyPrinter) {
        if (prettyPrinter == this._prettyPrinter) {
            return this;
        }
        if (prettyPrinter == null) {
            prettyPrinter = NULL_PRETTY_PRINTER;
        }
        SerializationConfig serializationConfig = this._config;
        JavaType javaType = this._rootType;
        FormatSchema formatSchema = this._schema;
        return new ObjectWriter((ObjectWriter)this, serializationConfig, javaType, prettyPrinter, formatSchema);
    }

    public ObjectWriter withSchema(FormatSchema formatSchema) {
        if (this._schema == formatSchema) {
            return this;
        }
        return new ObjectWriter((ObjectWriter)this, this._config, this._rootType, this._prettyPrinter, formatSchema);
    }

    public ObjectWriter withType(JavaType javaType) {
        if (javaType == this._rootType) {
            return this;
        }
        return new ObjectWriter((ObjectWriter)this, this._config, javaType, this._prettyPrinter, this._schema);
    }

    public ObjectWriter withType(TypeReference<?> typeReference) {
        return this.withType(this._config.getTypeFactory().constructType(typeReference.getType()));
    }

    public ObjectWriter withType(Class<?> class_) {
        return this.withType(this._config.constructType(class_));
    }

    public ObjectWriter withView(Class<?> class_) {
        if (class_ == this._config.getSerializationView()) {
            return this;
        }
        return new ObjectWriter((ObjectWriter)this, this._config.withView(class_));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeValue(JsonGenerator jsonGenerator, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        if (this._config.isEnabled(SerializationConfig.Feature.CLOSE_CLOSEABLE) && object instanceof Closeable) {
            ObjectWriter.super._writeCloseableValue(jsonGenerator, object, this._config);
            return;
        } else {
            if (this._rootType == null) {
                this._provider.serializeValue(this._config, jsonGenerator, object, this._serializerFactory);
            } else {
                this._provider.serializeValue(this._config, jsonGenerator, object, this._rootType, this._serializerFactory);
            }
            if (!this._config.isEnabled(SerializationConfig.Feature.FLUSH_AFTER_WRITE_VALUE)) return;
            {
                jsonGenerator.flush();
                return;
            }
        }
    }

    public void writeValue(File file, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(file, JsonEncoding.UTF8), object);
    }

    public void writeValue(OutputStream outputStream, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(outputStream, JsonEncoding.UTF8), object);
    }

    public void writeValue(Writer writer, Object object) throws IOException, JsonGenerationException, JsonMappingException {
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(writer), object);
    }

    public byte[] writeValueAsBytes(Object object) throws IOException, JsonGenerationException, JsonMappingException {
        ByteArrayBuilder byteArrayBuilder = new ByteArrayBuilder(this._jsonFactory._getBufferRecycler());
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(byteArrayBuilder, JsonEncoding.UTF8), object);
        byte[] arrby = byteArrayBuilder.toByteArray();
        byteArrayBuilder.release();
        return arrby;
    }

    public String writeValueAsString(Object object) throws IOException, JsonGenerationException, JsonMappingException {
        SegmentedStringWriter segmentedStringWriter = new SegmentedStringWriter(this._jsonFactory._getBufferRecycler());
        this._configAndWriteValue(this._jsonFactory.createJsonGenerator(segmentedStringWriter), object);
        return segmentedStringWriter.getAndClear();
    }
}

