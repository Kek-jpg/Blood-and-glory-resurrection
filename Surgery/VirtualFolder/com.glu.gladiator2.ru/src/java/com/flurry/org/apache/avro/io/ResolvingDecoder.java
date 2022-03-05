/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.AssertionError
 *  java.lang.Integer
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.String
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.io.Decoder;
import com.flurry.org.apache.avro.io.DecoderFactory;
import com.flurry.org.apache.avro.io.ValidatingDecoder;
import com.flurry.org.apache.avro.io.parsing.ResolvingGrammarGenerator;
import com.flurry.org.apache.avro.io.parsing.SkipParser;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import java.io.IOException;

public class ResolvingDecoder
extends ValidatingDecoder {
    static final /* synthetic */ boolean $assertionsDisabled;
    private Decoder backup;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ResolvingDecoder.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    ResolvingDecoder(Schema schema, Schema schema2, Decoder decoder) throws IOException {
        super(ResolvingDecoder.resolve(schema, schema2), decoder);
    }

    private ResolvingDecoder(Object object, Decoder decoder) throws IOException {
        super((Symbol)object, decoder);
    }

    public static Object resolve(Schema schema, Schema schema2) throws IOException {
        if (schema == null) {
            throw new NullPointerException("writer cannot be null!");
        }
        if (schema2 == null) {
            throw new NullPointerException("reader cannot be null!");
        }
        return new ResolvingGrammarGenerator().generate(schema, schema2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Symbol doAction(Symbol symbol, Symbol symbol2) throws IOException {
        if (symbol2 instanceof Symbol.FieldOrderAction) {
            if (symbol != Symbol.FIELD_ACTION) return null;
            return symbol2;
        }
        if (symbol2 instanceof Symbol.ResolvingAction) {
            Symbol.ResolvingAction resolvingAction = (Symbol.ResolvingAction)symbol2;
            if (resolvingAction.reader == symbol) return resolvingAction.writer;
            throw new AvroTypeException("Found " + resolvingAction.reader + " while looking for " + symbol);
        }
        if (symbol2 instanceof Symbol.SkipAction) {
            Symbol symbol3 = ((Symbol.SkipAction)symbol2).symToSkip;
            this.parser.skipSymbol(symbol3);
            do {
                return null;
                break;
            } while (true);
        }
        if (symbol2 instanceof Symbol.WriterUnionAction) {
            Symbol.Alternative alternative = (Symbol.Alternative)this.parser.popSymbol();
            this.parser.pushSymbol(alternative.getSymbol(this.in.readIndex()));
            return null;
        }
        if (symbol2 instanceof Symbol.ErrorAction) {
            throw new AvroTypeException(((Symbol.ErrorAction)symbol2).msg);
        }
        if (symbol2 instanceof Symbol.DefaultStartAction) {
            Symbol.DefaultStartAction defaultStartAction = (Symbol.DefaultStartAction)symbol2;
            this.backup = this.in;
            this.in = DecoderFactory.get().binaryDecoder(defaultStartAction.contents, null);
            return null;
        }
        if (symbol2 != Symbol.DEFAULT_END_ACTION) throw new AvroTypeException("Unknown action: " + symbol2);
        this.in = this.backup;
        return null;
    }

    public final void drain() throws IOException {
        this.parser.processImplicitActions();
    }

    @Override
    public double readDouble() throws IOException {
        Symbol symbol = this.parser.advance(Symbol.DOUBLE);
        if (symbol == Symbol.INT) {
            return this.in.readInt();
        }
        if (symbol == Symbol.LONG) {
            return this.in.readLong();
        }
        if (symbol == Symbol.FLOAT) {
            return this.in.readFloat();
        }
        if (!$assertionsDisabled && symbol != Symbol.DOUBLE) {
            throw new AssertionError();
        }
        return this.in.readDouble();
    }

    @Override
    public int readEnum() throws IOException {
        this.parser.advance(Symbol.ENUM);
        Symbol.EnumAdjustAction enumAdjustAction = (Symbol.EnumAdjustAction)this.parser.popSymbol();
        int n2 = this.in.readEnum();
        Object object = enumAdjustAction.adjustments[n2];
        if (object instanceof Integer) {
            return (Integer)object;
        }
        throw new AvroTypeException((String)object);
    }

    public final Schema.Field[] readFieldOrder() throws IOException {
        return ((Symbol.FieldOrderAction)this.parser.advance((Symbol)Symbol.FIELD_ACTION)).fields;
    }

    @Override
    public float readFloat() throws IOException {
        Symbol symbol = this.parser.advance(Symbol.FLOAT);
        if (symbol == Symbol.INT) {
            return this.in.readInt();
        }
        if (symbol == Symbol.LONG) {
            return this.in.readLong();
        }
        if (!$assertionsDisabled && symbol != Symbol.FLOAT) {
            throw new AssertionError();
        }
        return this.in.readFloat();
    }

    @Override
    public int readIndex() throws IOException {
        this.parser.advance(Symbol.UNION);
        Symbol.UnionAdjustAction unionAdjustAction = (Symbol.UnionAdjustAction)this.parser.popSymbol();
        this.parser.pushSymbol(unionAdjustAction.symToParse);
        return unionAdjustAction.rindex;
    }

    @Override
    public long readLong() throws IOException {
        Symbol symbol = this.parser.advance(Symbol.LONG);
        if (symbol == Symbol.INT) {
            return this.in.readInt();
        }
        if (symbol == Symbol.DOUBLE) {
            return (long)this.in.readDouble();
        }
        if (!$assertionsDisabled && symbol != Symbol.LONG) {
            throw new AssertionError();
        }
        return this.in.readLong();
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void skipAction() throws IOException {
        Symbol symbol = this.parser.popSymbol();
        if (symbol instanceof Symbol.ResolvingAction) {
            this.parser.pushSymbol(((Symbol.ResolvingAction)symbol).writer);
            return;
        } else {
            if (symbol instanceof Symbol.SkipAction) {
                this.parser.pushSymbol(((Symbol.SkipAction)symbol).symToSkip);
                return;
            }
            if (symbol instanceof Symbol.WriterUnionAction) {
                Symbol.Alternative alternative = (Symbol.Alternative)this.parser.popSymbol();
                this.parser.pushSymbol(alternative.getSymbol(this.in.readIndex()));
                return;
            }
            if (symbol instanceof Symbol.ErrorAction) {
                throw new AvroTypeException(((Symbol.ErrorAction)symbol).msg);
            }
            if (symbol instanceof Symbol.DefaultStartAction) {
                Symbol.DefaultStartAction defaultStartAction = (Symbol.DefaultStartAction)symbol;
                this.backup = this.in;
                this.in = DecoderFactory.get().binaryDecoder(defaultStartAction.contents, null);
                return;
            }
            if (symbol != Symbol.DEFAULT_END_ACTION) return;
            {
                this.in = this.backup;
                return;
            }
        }
    }
}

