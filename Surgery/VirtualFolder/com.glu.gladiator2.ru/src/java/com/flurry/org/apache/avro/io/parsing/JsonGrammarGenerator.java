/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 */
package com.flurry.org.apache.avro.io.parsing;

import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.parsing.Symbol;
import com.flurry.org.apache.avro.io.parsing.ValidatingGrammarGenerator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonGrammarGenerator
extends ValidatingGrammarGenerator {
    @Override
    public Symbol generate(Schema schema) {
        Symbol[] arrsymbol = new Symbol[]{this.generate(schema, (Map<ValidatingGrammarGenerator.LitS, Symbol>)new HashMap())};
        return Symbol.root(arrsymbol);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Symbol generate(Schema schema, Map<ValidatingGrammarGenerator.LitS, Symbol> map) {
        switch (1.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()]) {
            default: {
                throw new RuntimeException("Unexpected schema type");
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
            case 7: 
            case 8: 
            case 9: 
            case 10: {
                return super.generate(schema, map);
            }
            case 11: {
                Symbol[] arrsymbol = new Symbol[]{new Symbol.EnumLabelsAction(schema.getEnumSymbols()), Symbol.ENUM};
                return Symbol.seq(arrsymbol);
            }
            case 12: {
                Symbol[] arrsymbol = new Symbol[2];
                Symbol symbol = Symbol.ARRAY_END;
                Symbol[] arrsymbol2 = new Symbol[]{Symbol.ITEM_END, this.generate(schema.getElementType(), map)};
                arrsymbol[0] = Symbol.repeat(symbol, arrsymbol2);
                arrsymbol[1] = Symbol.ARRAY_START;
                return Symbol.seq(arrsymbol);
            }
            case 13: {
                Symbol[] arrsymbol = new Symbol[2];
                Symbol symbol = Symbol.MAP_END;
                Symbol[] arrsymbol3 = new Symbol[]{Symbol.ITEM_END, this.generate(schema.getValueType(), map), Symbol.MAP_KEY_MARKER, Symbol.STRING};
                arrsymbol[0] = Symbol.repeat(symbol, arrsymbol3);
                arrsymbol[1] = Symbol.MAP_START;
                return Symbol.seq(arrsymbol);
            }
            case 14: 
        }
        ValidatingGrammarGenerator.LitS litS = new ValidatingGrammarGenerator.LitS(schema);
        Symbol symbol = (Symbol)map.get((Object)litS);
        if (symbol != null) return symbol;
        Symbol[] arrsymbol = new Symbol[2 + 3 * schema.getFields().size()];
        Symbol symbol2 = Symbol.seq(arrsymbol);
        map.put((Object)litS, (Object)symbol2);
        int n2 = arrsymbol.length;
        int n3 = 0;
        int n4 = n2 - 1;
        arrsymbol[n4] = Symbol.RECORD_START;
        for (Schema.Field field : schema.getFields()) {
            int n5 = n4 - 1;
            arrsymbol[n5] = new Symbol.FieldAdjustAction(n3, field.name());
            int n6 = n5 - 1;
            arrsymbol[n6] = this.generate(field.schema(), map);
            n4 = n6 - 1;
            arrsymbol[n4] = Symbol.FIELD_END;
            ++n3;
        }
        arrsymbol[n4 - 1] = Symbol.RECORD_END;
        return symbol2;
    }

}

