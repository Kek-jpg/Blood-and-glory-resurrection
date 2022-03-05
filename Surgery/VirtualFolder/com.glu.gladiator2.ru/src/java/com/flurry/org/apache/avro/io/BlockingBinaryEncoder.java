/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.AssertionError
 *  java.lang.Enum
 *  java.lang.IllegalStateException
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Arrays
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroTypeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.io.BinaryData;
import com.flurry.org.apache.avro.io.BufferedBinaryEncoder;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class BlockingBinaryEncoder
extends BufferedBinaryEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int STACK_STEP = 10;
    private BlockedValue[] blockStack;
    private byte[] buf;
    private byte[] headerBuffer = new byte[12];
    private int pos;
    private int stackTop = -1;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !BlockingBinaryEncoder.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    BlockingBinaryEncoder(OutputStream outputStream, int n2, int n3) {
        int n4;
        super(outputStream, n3);
        this.buf = new byte[n2];
        this.pos = 0;
        this.blockStack = new BlockedValue[0];
        BlockingBinaryEncoder.super.expandStack();
        BlockedValue[] arrblockedValue = this.blockStack;
        this.stackTop = n4 = 1 + this.stackTop;
        BlockedValue blockedValue = arrblockedValue[n4];
        blockedValue.type = null;
        blockedValue.state = BlockedValue.State.ROOT;
        blockedValue.lastFullItem = 0;
        blockedValue.start = 0;
        blockedValue.items = 1;
        if (!$assertionsDisabled && !BlockingBinaryEncoder.super.check()) {
            throw new AssertionError();
        }
    }

    private boolean check() {
        if (!$assertionsDisabled && this.buf == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.pos < 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.pos > this.buf.length) {
            throw new AssertionError((Object)(this.pos + " " + this.buf.length));
        }
        if (!$assertionsDisabled && this.blockStack == null) {
            throw new AssertionError();
        }
        BlockedValue blockedValue = null;
        for (int i2 = 0; i2 <= this.stackTop; ++i2) {
            BlockedValue blockedValue2 = this.blockStack[i2];
            blockedValue2.check(blockedValue, this.pos);
            blockedValue = blockedValue2;
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void compact() throws IOException {
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
        BlockedValue blockedValue = null;
        int n2 = 1;
        do {
            block11 : {
                block10 : {
                    if (n2 > this.stackTop) break block10;
                    blockedValue = this.blockStack[n2];
                    if (blockedValue.state != BlockedValue.State.REGULAR) break block11;
                }
                if (!$assertionsDisabled && blockedValue == null) {
                    throw new AssertionError();
                }
                break;
            }
            ++n2;
        } while (true);
        super.writeFixed(this.buf, 0, blockedValue.start);
        if (1 < blockedValue.items) {
            super.writeInt(-(-1 + blockedValue.items));
            super.writeInt(blockedValue.lastFullItem - blockedValue.start);
            super.writeFixed(this.buf, blockedValue.start, blockedValue.lastFullItem - blockedValue.start);
            blockedValue.start = blockedValue.lastFullItem;
            blockedValue.items = 1;
        }
        super.writeInt(1);
        BlockedValue blockedValue2 = n2 + 1 <= this.stackTop ? this.blockStack[n2 + 1] : null;
        int n3 = blockedValue2 == null ? this.pos : blockedValue2.start;
        super.writeFixed(this.buf, blockedValue.lastFullItem, n3 - blockedValue.lastFullItem);
        System.arraycopy((Object)this.buf, (int)n3, (Object)this.buf, (int)0, (int)(this.pos - n3));
        for (int i2 = n2 + 1; i2 <= this.stackTop; blockedValue3.start -= n3, blockedValue3.lastFullItem -= n3, ++i2) {
            BlockedValue blockedValue3 = this.blockStack[i2];
        }
        this.pos -= n3;
        if (!$assertionsDisabled && blockedValue.items != 1) {
            throw new AssertionError();
        }
        blockedValue.lastFullItem = 0;
        blockedValue.start = 0;
        blockedValue.state = BlockedValue.State.OVERFLOW;
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
    }

    private void doWriteBytes(byte[] arrby, int n2, int n3) throws IOException {
        if (n3 < this.buf.length) {
            BlockingBinaryEncoder.super.ensureBounds(n3);
            System.arraycopy((Object)arrby, (int)n2, (Object)this.buf, (int)this.pos, (int)n3);
            this.pos = n3 + this.pos;
            return;
        }
        BlockingBinaryEncoder.super.ensureBounds(this.buf.length);
        if (!$assertionsDisabled && this.blockStack[this.stackTop].state != BlockedValue.State.ROOT && this.blockStack[this.stackTop].state != BlockedValue.State.OVERFLOW) {
            throw new AssertionError();
        }
        BlockingBinaryEncoder.super.write(arrby, n2, n3);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void endBlockedValue() throws IOException {
        do {
            block12 : {
                block10 : {
                    BlockedValue blockedValue;
                    int n2;
                    block11 : {
                        if (!$assertionsDisabled && !this.check()) {
                            throw new AssertionError();
                        }
                        blockedValue = this.blockStack[this.stackTop];
                        if (!$assertionsDisabled && blockedValue.state == BlockedValue.State.ROOT) {
                            throw new AssertionError();
                        }
                        if (blockedValue.state == BlockedValue.State.OVERFLOW) {
                            this.finishOverflow();
                        }
                        if (!$assertionsDisabled && blockedValue.state != BlockedValue.State.REGULAR) {
                            throw new AssertionError();
                        }
                        if (blockedValue.items <= 0) break block10;
                        n2 = this.pos - blockedValue.start;
                        if (blockedValue.start != 0 || this.blockStack[-1 + this.stackTop].state == BlockedValue.State.REGULAR) break block11;
                        super.writeInt(-blockedValue.items);
                        super.writeInt(n2);
                        break block10;
                    }
                    int n3 = 0 + BinaryData.encodeInt(-blockedValue.items, this.headerBuffer, 0);
                    int n4 = n3 + BinaryData.encodeInt(n2, this.headerBuffer, n3);
                    if (this.buf.length < n4 + this.pos) break block12;
                    this.pos = n4 + this.pos;
                    int n5 = blockedValue.start;
                    System.arraycopy((Object)this.buf, (int)n5, (Object)this.buf, (int)(n5 + n4), (int)n2);
                    System.arraycopy((Object)this.headerBuffer, (int)0, (Object)this.buf, (int)n5, (int)n4);
                }
                this.stackTop = -1 + this.stackTop;
                this.ensureBounds(1);
                byte[] arrby = this.buf;
                int n6 = this.pos;
                this.pos = n6 + 1;
                arrby[n6] = 0;
                if (!$assertionsDisabled && !this.check()) {
                    throw new AssertionError();
                }
                break;
            }
            this.compact();
        } while (true);
        if (this.blockStack[this.stackTop].state == BlockedValue.State.ROOT) {
            this.flush();
        }
    }

    private void ensureBounds(int n2) throws IOException {
        while (this.buf.length < n2 + this.pos) {
            if (this.blockStack[this.stackTop].state == BlockedValue.State.REGULAR) {
                BlockingBinaryEncoder.super.compact();
                continue;
            }
            super.writeFixed(this.buf, 0, this.pos);
            this.pos = 0;
        }
    }

    private void expandStack() {
        int n2 = this.blockStack.length;
        this.blockStack = (BlockedValue[])Arrays.copyOf((Object[])this.blockStack, (int)(10 + this.blockStack.length));
        for (int i2 = n2; i2 < this.blockStack.length; ++i2) {
            this.blockStack[i2] = new BlockedValue();
        }
    }

    private void finishOverflow() throws IOException {
        BlockedValue blockedValue = this.blockStack[this.stackTop];
        if (blockedValue.state != BlockedValue.State.OVERFLOW) {
            throw new IllegalStateException("Not an overflow block");
        }
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
        super.writeFixed(this.buf, 0, this.pos);
        this.pos = 0;
        blockedValue.state = BlockedValue.State.REGULAR;
        blockedValue.lastFullItem = 0;
        blockedValue.start = 0;
        blockedValue.items = 0;
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void write(byte[] var1, int var2_3, int var3_2) throws IOException {
        if (this.blockStack[this.stackTop].state != BlockedValue.State.ROOT) {
            if (!BlockingBinaryEncoder.$assertionsDisabled && !BlockingBinaryEncoder.super.check()) {
                throw new AssertionError();
            }
        } else {
            super.writeFixed(var1, var2_3, var3_2);
lbl6: // 2 sources:
            do {
                if (BlockingBinaryEncoder.$assertionsDisabled != false) return;
                if (BlockingBinaryEncoder.super.check() != false) return;
                throw new AssertionError();
                break;
            } while (true);
        }
        while (this.buf.length < var3_2 + this.pos) {
            if (this.blockStack[this.stackTop].state == BlockedValue.State.REGULAR) {
                BlockingBinaryEncoder.super.compact();
                continue;
            }
            super.writeFixed(this.buf, 0, this.pos);
            this.pos = 0;
            if (this.buf.length > var3_2) continue;
            super.writeFixed(var1, var2_3, var3_2);
            var3_2 = 0;
        }
        System.arraycopy((Object)var1, (int)var2_3, (Object)this.buf, (int)this.pos, (int)var3_2);
        this.pos = var3_2 + this.pos;
        ** while (true)
    }

    @Override
    public int bytesBuffered() {
        return this.pos + super.bytesBuffered();
    }

    BlockingBinaryEncoder configure(OutputStream outputStream, int n2, int n3) {
        super.configure(outputStream, n3);
        this.pos = 0;
        this.stackTop = 0;
        if (this.buf == null || this.buf.length != n2) {
            this.buf = new byte[n2];
        }
        if (!$assertionsDisabled && !BlockingBinaryEncoder.super.check()) {
            throw new AssertionError();
        }
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void flush() throws IOException {
        BlockedValue blockedValue = this.blockStack[this.stackTop];
        if (blockedValue.state == BlockedValue.State.ROOT) {
            super.writeFixed(this.buf, 0, this.pos);
            this.pos = 0;
        } else {
            while (blockedValue.state != BlockedValue.State.OVERFLOW) {
                this.compact();
            }
        }
        super.flush();
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
    }

    @Override
    public void setItemCount(long l2) throws IOException {
        BlockedValue blockedValue = this.blockStack[this.stackTop];
        if (!$assertionsDisabled && blockedValue.type != Schema.Type.ARRAY && blockedValue.type != Schema.Type.MAP) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && blockedValue.itemsLeftToWrite != 0L) {
            throw new AssertionError();
        }
        blockedValue.itemsLeftToWrite = l2;
        if (!$assertionsDisabled && !BlockingBinaryEncoder.super.check()) {
            throw new AssertionError();
        }
    }

    @Override
    public void startItem() throws IOException {
        if (this.blockStack[this.stackTop].state == BlockedValue.State.OVERFLOW) {
            this.finishOverflow();
        }
        BlockedValue blockedValue = this.blockStack[this.stackTop];
        blockedValue.items = 1 + blockedValue.items;
        blockedValue.lastFullItem = this.pos;
        --blockedValue.itemsLeftToWrite;
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
    }

    @Override
    public void writeArrayEnd() throws IOException {
        BlockedValue blockedValue = this.blockStack[this.stackTop];
        if (blockedValue.type != Schema.Type.ARRAY) {
            throw new AvroTypeException("Called writeArrayEnd outside of an array.");
        }
        if (blockedValue.itemsLeftToWrite != 0L) {
            throw new AvroTypeException("Failed to write expected number of array elements.");
        }
        this.endBlockedValue();
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
    }

    @Override
    public void writeArrayStart() throws IOException {
        int n2;
        int n3;
        if (1 + this.stackTop == this.blockStack.length) {
            this.expandStack();
        }
        BlockedValue[] arrblockedValue = this.blockStack;
        this.stackTop = n2 = 1 + this.stackTop;
        BlockedValue blockedValue = arrblockedValue[n2];
        blockedValue.type = Schema.Type.ARRAY;
        blockedValue.state = BlockedValue.State.REGULAR;
        blockedValue.lastFullItem = n3 = this.pos;
        blockedValue.start = n3;
        blockedValue.items = 0;
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
    }

    @Override
    public void writeBoolean(boolean bl) throws IOException {
        BlockingBinaryEncoder.super.ensureBounds(1);
        this.pos += BinaryData.encodeBoolean(bl, this.buf, this.pos);
    }

    @Override
    public void writeDouble(double d2) throws IOException {
        BlockingBinaryEncoder.super.ensureBounds(8);
        this.pos += BinaryData.encodeDouble(d2, this.buf, this.pos);
    }

    @Override
    public void writeFixed(byte[] arrby, int n2, int n3) throws IOException {
        BlockingBinaryEncoder.super.doWriteBytes(arrby, n2, n3);
    }

    @Override
    public void writeFloat(float f2) throws IOException {
        BlockingBinaryEncoder.super.ensureBounds(4);
        this.pos += BinaryData.encodeFloat(f2, this.buf, this.pos);
    }

    @Override
    public void writeIndex(int n2) throws IOException {
        BlockingBinaryEncoder.super.ensureBounds(5);
        this.pos += BinaryData.encodeInt(n2, this.buf, this.pos);
    }

    @Override
    public void writeInt(int n2) throws IOException {
        BlockingBinaryEncoder.super.ensureBounds(5);
        this.pos += BinaryData.encodeInt(n2, this.buf, this.pos);
    }

    @Override
    public void writeLong(long l2) throws IOException {
        BlockingBinaryEncoder.super.ensureBounds(10);
        this.pos += BinaryData.encodeLong(l2, this.buf, this.pos);
    }

    @Override
    public void writeMapEnd() throws IOException {
        BlockedValue blockedValue = this.blockStack[this.stackTop];
        if (blockedValue.type != Schema.Type.MAP) {
            throw new AvroTypeException("Called writeMapEnd outside of a map.");
        }
        if (blockedValue.itemsLeftToWrite != 0L) {
            throw new AvroTypeException("Failed to read write expected number of array elements.");
        }
        this.endBlockedValue();
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
    }

    @Override
    public void writeMapStart() throws IOException {
        int n2;
        int n3;
        if (1 + this.stackTop == this.blockStack.length) {
            this.expandStack();
        }
        BlockedValue[] arrblockedValue = this.blockStack;
        this.stackTop = n2 = 1 + this.stackTop;
        BlockedValue blockedValue = arrblockedValue[n2];
        blockedValue.type = Schema.Type.MAP;
        blockedValue.state = BlockedValue.State.REGULAR;
        blockedValue.lastFullItem = n3 = this.pos;
        blockedValue.start = n3;
        blockedValue.items = 0;
        if (!$assertionsDisabled && !this.check()) {
            throw new AssertionError();
        }
    }

    @Override
    protected void writeZero() throws IOException {
        this.ensureBounds(1);
        byte[] arrby = this.buf;
        int n2 = this.pos;
        this.pos = n2 + 1;
        arrby[n2] = 0;
    }

    private static class BlockedValue {
        static final /* synthetic */ boolean $assertionsDisabled;
        public int items = 1;
        public long itemsLeftToWrite;
        public int lastFullItem = 0;
        public int start = 0;
        public State state = State.ROOT;
        public Schema.Type type = null;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !BlockingBinaryEncoder.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean check(BlockedValue blockedValue, int n2) {
            if (!$assertionsDisabled && this.state == State.ROOT && this.type != null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.state != State.ROOT && this.type != Schema.Type.ARRAY && this.type != Schema.Type.MAP) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.items < 0) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.items == 0 && this.start != n2) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && 1 >= this.items && this.start != this.lastFullItem) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.items > 1 && this.start > this.lastFullItem) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.lastFullItem > n2) {
                throw new AssertionError();
            }
            switch (1.$SwitchMap$org$apache$avro$io$BlockingBinaryEncoder$BlockedValue$State[this.state.ordinal()]) {
                case 1: {
                    if (!$assertionsDisabled && this.start != 0) {
                        throw new AssertionError();
                    }
                    if ($assertionsDisabled || blockedValue == null) return false;
                    {
                        throw new AssertionError();
                    }
                }
                case 2: {
                    if (!$assertionsDisabled && this.start < 0) {
                        throw new AssertionError();
                    }
                    if (!$assertionsDisabled && blockedValue.lastFullItem > this.start) {
                        throw new AssertionError();
                    }
                    if ($assertionsDisabled || 1 <= blockedValue.items) return false;
                    {
                        throw new AssertionError();
                    }
                }
                default: {
                    return false;
                }
                case 3: 
            }
            if (!$assertionsDisabled && this.start != 0) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.items != 1) {
                throw new AssertionError();
            }
            if ($assertionsDisabled || blockedValue.state == State.ROOT || blockedValue.state == State.OVERFLOW) return false;
            {
                throw new AssertionError();
            }
        }

        public static final class State
        extends Enum<State> {
            private static final /* synthetic */ State[] $VALUES;
            public static final /* enum */ State OVERFLOW;
            public static final /* enum */ State REGULAR;
            public static final /* enum */ State ROOT;

            static {
                ROOT = new State();
                REGULAR = new State();
                OVERFLOW = new State();
                State[] arrstate = new State[]{ROOT, REGULAR, OVERFLOW};
                $VALUES = arrstate;
            }

            public static State valueOf(String string) {
                return (State)Enum.valueOf(State.class, (String)string);
            }

            public static State[] values() {
                return (State[])$VALUES.clone();
            }
        }

    }

}

