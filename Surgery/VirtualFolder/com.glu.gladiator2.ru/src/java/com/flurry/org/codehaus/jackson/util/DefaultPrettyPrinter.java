/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  com.flurry.org.codehaus.jackson.util.DefaultPrettyPrinter$FixedSpaceIndenter
 *  com.flurry.org.codehaus.jackson.util.DefaultPrettyPrinter$Lf2SpacesIndenter
 *  com.flurry.org.codehaus.jackson.util.DefaultPrettyPrinter$NopIndenter
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.codehaus.jackson.util;

import com.flurry.org.codehaus.jackson.JsonGenerationException;
import com.flurry.org.codehaus.jackson.JsonGenerator;
import com.flurry.org.codehaus.jackson.PrettyPrinter;
import com.flurry.org.codehaus.jackson.impl.Indenter;
import com.flurry.org.codehaus.jackson.util.DefaultPrettyPrinter;
import java.io.IOException;

/*
 * Exception performing whole class analysis.
 */
public class DefaultPrettyPrinter
implements PrettyPrinter {
    protected Indenter _arrayIndenter;
    protected int _nesting;
    protected Indenter _objectIndenter;
    protected boolean _spacesInObjectEntries;

    public DefaultPrettyPrinter() {
        this._arrayIndenter = new /* Unavailable Anonymous Inner Class!! */;
        this._objectIndenter = new /* Unavailable Anonymous Inner Class!! */;
        this._spacesInObjectEntries = true;
        this._nesting = 0;
    }

    @Override
    public void beforeArrayValues(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public void beforeObjectEntries(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    public void indentArraysWith(Indenter indenter) {
        if (indenter == null) {
            indenter = new /* Unavailable Anonymous Inner Class!! */;
        }
        this._arrayIndenter = indenter;
    }

    public void indentObjectsWith(Indenter indenter) {
        if (indenter == null) {
            indenter = new /* Unavailable Anonymous Inner Class!! */;
        }
        this._objectIndenter = indenter;
    }

    public void spacesInObjectEntries(boolean bl) {
        this._spacesInObjectEntries = bl;
    }

    @Override
    public void writeArrayValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(',');
        this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeEndArray(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
        if (!this._arrayIndenter.isInline()) {
            this._nesting = -1 + this._nesting;
        }
        if (n2 > 0) {
            this._arrayIndenter.writeIndentation(jsonGenerator, this._nesting);
        } else {
            jsonGenerator.writeRaw(' ');
        }
        jsonGenerator.writeRaw(']');
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeEndObject(JsonGenerator jsonGenerator, int n2) throws IOException, JsonGenerationException {
        if (!this._objectIndenter.isInline()) {
            this._nesting = -1 + this._nesting;
        }
        if (n2 > 0) {
            this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
        } else {
            jsonGenerator.writeRaw(' ');
        }
        jsonGenerator.writeRaw('}');
    }

    @Override
    public void writeObjectEntrySeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(',');
        this._objectIndenter.writeIndentation(jsonGenerator, this._nesting);
    }

    @Override
    public void writeObjectFieldValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        if (this._spacesInObjectEntries) {
            jsonGenerator.writeRaw(" : ");
            return;
        }
        jsonGenerator.writeRaw(':');
    }

    @Override
    public void writeRootValueSeparator(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw(' ');
    }

    @Override
    public void writeStartArray(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        if (!this._arrayIndenter.isInline()) {
            this._nesting = 1 + this._nesting;
        }
        jsonGenerator.writeRaw('[');
    }

    @Override
    public void writeStartObject(JsonGenerator jsonGenerator) throws IOException, JsonGenerationException {
        jsonGenerator.writeRaw('{');
        if (!this._objectIndenter.isInline()) {
            this._nesting = 1 + this._nesting;
        }
    }
}

