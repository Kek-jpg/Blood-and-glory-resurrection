/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.impl.ReaderBasedParser
 *  com.flurry.org.codehaus.jackson.impl.Utf8Generator
 *  com.flurry.org.codehaus.jackson.impl.WriterBasedGenerator
 *  com.flurry.org.codehaus.jackson.io.CharacterEscapes
 *  com.flurry.org.codehaus.jackson.io.IOContext
 *  com.flurry.org.codehaus.jackson.io.InputDecorator
 *  com.flurry.org.codehaus.jackson.io.OutputDecorator
 *  com.flurry.org.codehaus.jackson.io.UTF8Writer
 *  com.flurry.org.codehaus.jackson.sym.BytesToNameCanonicalizer
 *  com.flurry.org.codehaus.jackson.sym.CharsToNameCanonicalizer
 *  com.flurry.org.codehaus.jackson.util.BufferRecycler
 *  com.flurry.org.codehaus.jackson.util.VersionUtil
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.Reader
 *  java.io.StringReader
 *  java.io.Writer
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.ThreadLocal
 *  java.lang.ref.SoftReference
 *  java.net.URL
 */
package com.flurry.org.codehaus.jackson;

import com.flurry.org.codehaus.jackson.JsonEncoding;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.JsonParseException;
import com.flurry.org.codehaus.jackson.JsonParser;
import com.flurry.org.codehaus.jackson.ObjectCodec;
import com.flurry.org.codehaus.jackson.Version;
import com.flurry.org.codehaus.jackson.Versioned;
import com.flurry.org.codehaus.jackson.format.InputAccessor;
import com.flurry.org.codehaus.jackson.format.MatchStrength;
import com.flurry.org.codehaus.jackson.impl.ByteSourceBootstrapper;
import com.flurry.org.codehaus.jackson.impl.ReaderBasedParser;
import com.flurry.org.codehaus.jackson.impl.Utf8Generator;
import com.flurry.org.codehaus.jackson.impl.WriterBasedGenerator;
import com.flurry.org.codehaus.jackson.io.CharacterEscapes;
import com.flurry.org.codehaus.jackson.io.IOContext;
import com.flurry.org.codehaus.jackson.io.InputDecorator;
import com.flurry.org.codehaus.jackson.io.OutputDecorator;
import com.flurry.org.codehaus.jackson.io.UTF8Writer;
import com.flurry.org.codehaus.jackson.sym.BytesToNameCanonicalizer;
import com.flurry.org.codehaus.jackson.sym.CharsToNameCanonicalizer;
import com.flurry.org.codehaus.jackson.util.BufferRecycler;
import com.flurry.org.codehaus.jackson.util.VersionUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.net.URL;

public class JsonFactory
implements Versioned {
    static final int DEFAULT_GENERATOR_FEATURE_FLAGS = 0;
    static final int DEFAULT_PARSER_FEATURE_FLAGS = 0;
    public static final String FORMAT_NAME_JSON = "JSON";
    protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;
    protected CharacterEscapes _characterEscapes;
    protected int _generatorFeatures = DEFAULT_GENERATOR_FEATURE_FLAGS;
    protected InputDecorator _inputDecorator;
    protected ObjectCodec _objectCodec;
    protected OutputDecorator _outputDecorator;
    protected int _parserFeatures = DEFAULT_PARSER_FEATURE_FLAGS;
    protected BytesToNameCanonicalizer _rootByteSymbols = BytesToNameCanonicalizer.createRoot();
    protected CharsToNameCanonicalizer _rootCharSymbols = CharsToNameCanonicalizer.createRoot();

    static {
        DEFAULT_PARSER_FEATURE_FLAGS = JsonParser.Feature.collectDefaults();
        DEFAULT_GENERATOR_FEATURE_FLAGS = JsonGenerator.Feature.collectDefaults();
        _recyclerRef = new ThreadLocal();
    }

    public JsonFactory() {
        this(null);
    }

    public JsonFactory(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
    }

    protected IOContext _createContext(Object object, boolean bl) {
        return new IOContext(this._getBufferRecycler(), object, bl);
    }

    protected JsonGenerator _createJsonGenerator(Writer writer, IOContext iOContext) throws IOException {
        WriterBasedGenerator writerBasedGenerator = new WriterBasedGenerator(iOContext, this._generatorFeatures, this._objectCodec, writer);
        if (this._characterEscapes != null) {
            writerBasedGenerator.setCharacterEscapes(this._characterEscapes);
        }
        return writerBasedGenerator;
    }

    protected JsonParser _createJsonParser(InputStream inputStream, IOContext iOContext) throws IOException, JsonParseException {
        return new ByteSourceBootstrapper(iOContext, inputStream).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols);
    }

    protected JsonParser _createJsonParser(Reader reader, IOContext iOContext) throws IOException, JsonParseException {
        return new ReaderBasedParser(iOContext, this._parserFeatures, reader, this._objectCodec, this._rootCharSymbols.makeChild(this.isEnabled(JsonParser.Feature.CANONICALIZE_FIELD_NAMES), this.isEnabled(JsonParser.Feature.INTERN_FIELD_NAMES)));
    }

    protected JsonParser _createJsonParser(byte[] arrby, int n2, int n3, IOContext iOContext) throws IOException, JsonParseException {
        return new ByteSourceBootstrapper(iOContext, arrby, n2, n3).constructParser(this._parserFeatures, this._objectCodec, this._rootByteSymbols, this._rootCharSymbols);
    }

    protected JsonGenerator _createUTF8JsonGenerator(OutputStream outputStream, IOContext iOContext) throws IOException {
        Utf8Generator utf8Generator = new Utf8Generator(iOContext, this._generatorFeatures, this._objectCodec, outputStream);
        if (this._characterEscapes != null) {
            utf8Generator.setCharacterEscapes(this._characterEscapes);
        }
        return utf8Generator;
    }

    protected Writer _createWriter(OutputStream outputStream, JsonEncoding jsonEncoding, IOContext iOContext) throws IOException {
        if (jsonEncoding == JsonEncoding.UTF8) {
            return new UTF8Writer(iOContext, outputStream);
        }
        return new OutputStreamWriter(outputStream, jsonEncoding.getJavaName());
    }

    /*
     * Enabled aggressive block sorting
     */
    public BufferRecycler _getBufferRecycler() {
        SoftReference softReference = (SoftReference)_recyclerRef.get();
        BufferRecycler bufferRecycler = softReference == null ? null : (BufferRecycler)softReference.get();
        if (bufferRecycler == null) {
            bufferRecycler = new BufferRecycler();
            _recyclerRef.set((Object)new SoftReference((Object)bufferRecycler));
        }
        return bufferRecycler;
    }

    protected InputStream _optimizedStreamFromURL(URL uRL) throws IOException {
        String string;
        if ("file".equals((Object)uRL.getProtocol()) && ((string = uRL.getHost()) == null || string.length() == 0)) {
            return new FileInputStream(uRL.getPath());
        }
        return uRL.openStream();
    }

    public final JsonFactory configure(JsonGenerator.Feature feature, boolean bl) {
        if (bl) {
            this.enable(feature);
            return this;
        }
        this.disable(feature);
        return this;
    }

    public final JsonFactory configure(JsonParser.Feature feature, boolean bl) {
        if (bl) {
            this.enable(feature);
            return this;
        }
        this.disable(feature);
        return this;
    }

    public JsonGenerator createJsonGenerator(File file, JsonEncoding jsonEncoding) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        IOContext iOContext = this._createContext((Object)fileOutputStream, true);
        iOContext.setEncoding(jsonEncoding);
        if (jsonEncoding == JsonEncoding.UTF8) {
            if (this._outputDecorator != null) {
                fileOutputStream = this._outputDecorator.decorate(iOContext, (OutputStream)fileOutputStream);
            }
            return this._createUTF8JsonGenerator((OutputStream)fileOutputStream, iOContext);
        }
        Writer writer = this._createWriter((OutputStream)fileOutputStream, jsonEncoding, iOContext);
        if (this._outputDecorator != null) {
            writer = this._outputDecorator.decorate(iOContext, writer);
        }
        return this._createJsonGenerator(writer, iOContext);
    }

    public JsonGenerator createJsonGenerator(OutputStream outputStream) throws IOException {
        return this.createJsonGenerator(outputStream, JsonEncoding.UTF8);
    }

    public JsonGenerator createJsonGenerator(OutputStream outputStream, JsonEncoding jsonEncoding) throws IOException {
        IOContext iOContext = this._createContext((Object)outputStream, false);
        iOContext.setEncoding(jsonEncoding);
        if (jsonEncoding == JsonEncoding.UTF8) {
            if (this._outputDecorator != null) {
                outputStream = this._outputDecorator.decorate(iOContext, outputStream);
            }
            return this._createUTF8JsonGenerator(outputStream, iOContext);
        }
        Writer writer = this._createWriter(outputStream, jsonEncoding, iOContext);
        if (this._outputDecorator != null) {
            writer = this._outputDecorator.decorate(iOContext, writer);
        }
        return this._createJsonGenerator(writer, iOContext);
    }

    public JsonGenerator createJsonGenerator(Writer writer) throws IOException {
        IOContext iOContext = this._createContext((Object)writer, false);
        if (this._outputDecorator != null) {
            writer = this._outputDecorator.decorate(iOContext, writer);
        }
        return this._createJsonGenerator(writer, iOContext);
    }

    public JsonParser createJsonParser(File file) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext((Object)file, true);
        FileInputStream fileInputStream = new FileInputStream(file);
        if (this._inputDecorator != null) {
            fileInputStream = this._inputDecorator.decorate(iOContext, (InputStream)fileInputStream);
        }
        return this._createJsonParser((InputStream)fileInputStream, iOContext);
    }

    public JsonParser createJsonParser(InputStream inputStream) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext((Object)inputStream, false);
        if (this._inputDecorator != null) {
            inputStream = this._inputDecorator.decorate(iOContext, inputStream);
        }
        return this._createJsonParser(inputStream, iOContext);
    }

    public JsonParser createJsonParser(Reader reader) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext((Object)reader, false);
        if (this._inputDecorator != null) {
            reader = this._inputDecorator.decorate(iOContext, reader);
        }
        return this._createJsonParser(reader, iOContext);
    }

    public JsonParser createJsonParser(String string) throws IOException, JsonParseException {
        StringReader stringReader = new StringReader(string);
        IOContext iOContext = this._createContext((Object)stringReader, true);
        if (this._inputDecorator != null) {
            stringReader = this._inputDecorator.decorate(iOContext, (Reader)stringReader);
        }
        return this._createJsonParser((Reader)stringReader, iOContext);
    }

    public JsonParser createJsonParser(URL uRL) throws IOException, JsonParseException {
        IOContext iOContext = this._createContext((Object)uRL, true);
        InputStream inputStream = this._optimizedStreamFromURL(uRL);
        if (this._inputDecorator != null) {
            inputStream = this._inputDecorator.decorate(iOContext, inputStream);
        }
        return this._createJsonParser(inputStream, iOContext);
    }

    public JsonParser createJsonParser(byte[] arrby) throws IOException, JsonParseException {
        InputStream inputStream;
        IOContext iOContext = this._createContext(arrby, true);
        if (this._inputDecorator != null && (inputStream = this._inputDecorator.decorate(iOContext, arrby, 0, arrby.length)) != null) {
            return this._createJsonParser(inputStream, iOContext);
        }
        return this._createJsonParser(arrby, 0, arrby.length, iOContext);
    }

    public JsonParser createJsonParser(byte[] arrby, int n2, int n3) throws IOException, JsonParseException {
        InputStream inputStream;
        IOContext iOContext = this._createContext(arrby, true);
        if (this._inputDecorator != null && (inputStream = this._inputDecorator.decorate(iOContext, arrby, n2, n3)) != null) {
            return this._createJsonParser(inputStream, iOContext);
        }
        return this._createJsonParser(arrby, n2, n3, iOContext);
    }

    public JsonFactory disable(JsonGenerator.Feature feature) {
        this._generatorFeatures &= -1 ^ feature.getMask();
        return this;
    }

    public JsonFactory disable(JsonParser.Feature feature) {
        this._parserFeatures &= -1 ^ feature.getMask();
        return this;
    }

    @Deprecated
    public final void disableGeneratorFeature(JsonGenerator.Feature feature) {
        this.disable(feature);
    }

    public final void disableParserFeature(JsonParser.Feature feature) {
        this.disable(feature);
    }

    public JsonFactory enable(JsonGenerator.Feature feature) {
        this._generatorFeatures |= feature.getMask();
        return this;
    }

    public JsonFactory enable(JsonParser.Feature feature) {
        this._parserFeatures |= feature.getMask();
        return this;
    }

    @Deprecated
    public final void enableGeneratorFeature(JsonGenerator.Feature feature) {
        this.enable(feature);
    }

    public final void enableParserFeature(JsonParser.Feature feature) {
        this.enable(feature);
    }

    public CharacterEscapes getCharacterEscapes() {
        return this._characterEscapes;
    }

    public ObjectCodec getCodec() {
        return this._objectCodec;
    }

    public String getFormatName() {
        if (this.getClass() == JsonFactory.class) {
            return FORMAT_NAME_JSON;
        }
        return null;
    }

    public InputDecorator getInputDecorator() {
        return this._inputDecorator;
    }

    public OutputDecorator getOutputDecorator() {
        return this._outputDecorator;
    }

    public MatchStrength hasFormat(InputAccessor inputAccessor) throws IOException {
        if (this.getClass() == JsonFactory.class) {
            return this.hasJSONFormat(inputAccessor);
        }
        return null;
    }

    protected MatchStrength hasJSONFormat(InputAccessor inputAccessor) throws IOException {
        return ByteSourceBootstrapper.hasJSONFormat(inputAccessor);
    }

    public final boolean isEnabled(JsonGenerator.Feature feature) {
        return (this._generatorFeatures & feature.getMask()) != 0;
    }

    public final boolean isEnabled(JsonParser.Feature feature) {
        return (this._parserFeatures & feature.getMask()) != 0;
    }

    @Deprecated
    public final boolean isGeneratorFeatureEnabled(JsonGenerator.Feature feature) {
        return this.isEnabled(feature);
    }

    public final boolean isParserFeatureEnabled(JsonParser.Feature feature) {
        return (this._parserFeatures & feature.getMask()) != 0;
    }

    public JsonFactory setCharacterEscapes(CharacterEscapes characterEscapes) {
        this._characterEscapes = characterEscapes;
        return this;
    }

    public JsonFactory setCodec(ObjectCodec objectCodec) {
        this._objectCodec = objectCodec;
        return this;
    }

    @Deprecated
    public final void setGeneratorFeature(JsonGenerator.Feature feature, boolean bl) {
        this.configure(feature, bl);
    }

    public JsonFactory setInputDecorator(InputDecorator inputDecorator) {
        this._inputDecorator = inputDecorator;
        return this;
    }

    public JsonFactory setOutputDecorator(OutputDecorator outputDecorator) {
        this._outputDecorator = outputDecorator;
        return this;
    }

    public final void setParserFeature(JsonParser.Feature feature, boolean bl) {
        this.configure(feature, bl);
    }

    @Override
    public Version version() {
        return VersionUtil.versionFor(Utf8Generator.class);
    }
}

