/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.ThreadLocal
 *  java.lang.Throwable
 *  java.util.Iterator
 *  java.util.List
 */
package com.flurry.org.apache.avro.io;

import com.flurry.org.apache.avro.AvroRuntimeException;
import com.flurry.org.apache.avro.Schema;
import com.flurry.org.apache.avro.generic.GenericDatumReader;
import com.flurry.org.apache.avro.io.BinaryDecoder;
import com.flurry.org.apache.avro.io.Decoder;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class BinaryData {
    private static final ThreadLocal<Decoders> DECODERS = new ThreadLocal<Decoders>(){

        protected Decoders initialValue() {
            return new Decoders();
        }
    };
    private static final ThreadLocal<HashData> HASH_DATA = new ThreadLocal<HashData>(){

        protected HashData initialValue() {
            return new HashData();
        }
    };

    private BinaryData() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static int compare(Decoders var0_1, Schema var1) throws IOException {
        var2_2 = Decoders.access$000(var0_1);
        var3_3 = Decoders.access$100(var0_1);
        switch (3.$SwitchMap$org$apache$avro$Schema$Type[var1.getType().ordinal()]) {
            default: {
                throw new AvroRuntimeException("Unexpected schema to compare!");
            }
            case 1: {
                var41_4 = var1.getFields().iterator();
                while (var41_4.hasNext() != false) {
                    var42_6 = (Schema.Field)var41_4.next();
                    if (var42_6.order() == Schema.Field.Order.IGNORE) {
                        GenericDatumReader.skip(var42_6.schema(), var2_2);
                        GenericDatumReader.skip(var42_6.schema(), var3_3);
                        continue;
                    }
                    var43_5 = BinaryData.compare(var0_1, var42_6.schema());
                    if (var43_5 == 0) {
                        continue;
                    }
                    ** GOTO lbl19
                }
                return 0;
lbl19: // 1 sources:
                if (var42_6.order() == Schema.Field.Order.DESCENDING) return -var43_5;
                return var43_5;
            }
            case 2: 
            case 3: {
                var39_7 = var2_2.readInt();
                var40_8 = var3_3.readInt();
                if (var39_7 == var40_8) {
                    return 0;
                }
                if (var39_7 <= var40_8) return -1;
                return 1;
            }
            case 4: {
                var35_9 = var2_2.readLong();
                var37_10 = var3_3.readLong();
                if (var35_9 == var37_10) {
                    return 0;
                }
                if (var35_9 <= var37_10) return -1;
                return 1;
            }
            case 5: {
                var18_11 = 0L;
                var20_12 = 0L;
                var22_13 = 0L;
                var24_14 = 0L;
                var26_15 = 0L;
                block15 : do {
                    if (var20_12 == 0L) {
                        var20_12 = var2_2.readLong();
                        if (var20_12 < 0L) {
                            var20_12 = -var20_12;
                            var2_2.readLong();
                        }
                        var24_14 += var20_12;
                    }
                    if (var22_13 == 0L) {
                        var22_13 = var3_3.readLong();
                        if (var22_13 < 0L) {
                            var22_13 = -var22_13;
                            var3_3.readLong();
                        }
                        var26_15 += var22_13;
                    }
                    if (var20_12 == 0L || var22_13 == 0L) {
                        if (var24_14 == var26_15) {
                            return 0;
                        }
                        if (var24_14 <= var26_15) return -1;
                        return 1;
                    }
                    var28_17 = Math.min((long)var24_14, (long)var26_15);
                    do {
                        if (var18_11 >= var28_17) continue block15;
                        var30_16 = BinaryData.compare(var0_1, var1.getElementType());
                        if (var30_16 != 0) {
                            return var30_16;
                        }
                        ++var18_11;
                        --var20_12;
                        --var22_13;
                    } while (true);
                    break;
                } while (true);
            }
            case 6: {
                throw new AvroRuntimeException("Can't compare maps!");
            }
            case 7: {
                var16_18 = var2_2.readInt();
                var17_19 = var3_3.readInt();
                if (var16_18 != var17_19) return var16_18 - var17_19;
                return BinaryData.compare(var0_1, (Schema)var1.getTypes().get(var16_18));
            }
            case 8: {
                var14_20 = var1.getFixedSize();
                var15_21 = BinaryData.compareBytes(Decoders.access$200(var0_1).getBuf(), Decoders.access$200(var0_1).getPos(), var14_20, Decoders.access$300(var0_1).getBuf(), Decoders.access$300(var0_1).getPos(), var14_20);
                Decoders.access$000(var0_1).skipFixed(var14_20);
                Decoders.access$100(var0_1).skipFixed(var14_20);
                return var15_21;
            }
            case 9: 
            case 10: {
                var11_22 = var2_2.readInt();
                var12_23 = var3_3.readInt();
                var13_24 = BinaryData.compareBytes(Decoders.access$200(var0_1).getBuf(), Decoders.access$200(var0_1).getPos(), var11_22, Decoders.access$300(var0_1).getBuf(), Decoders.access$300(var0_1).getPos(), var12_23);
                Decoders.access$000(var0_1).skipFixed(var11_22);
                Decoders.access$100(var0_1).skipFixed(var12_23);
                return var13_24;
            }
            case 11: {
                var9_25 = var2_2.readFloat();
                var10_26 = var3_3.readFloat();
                if (var9_25 == var10_26) {
                    return 0;
                }
                if (!(var9_25 > var10_26)) return -1;
                return 1;
            }
            case 12: {
                var5_27 = var2_2.readDouble();
                var7_28 = var3_3.readDouble();
                if (var5_27 == var7_28) {
                    return 0;
                }
                if (!(var5_27 > var7_28)) return -1;
                return 1;
            }
            case 13: {
                var4_29 = var2_2.readBoolean();
                if (var4_29 == var3_3.readBoolean()) {
                    return 0;
                }
                if (var4_29 == false) return -1;
                return 1;
            }
            case 14: 
        }
        return 0;
    }

    public static int compare(byte[] arrby, int n2, int n3, byte[] arrby2, int n4, int n5, Schema schema) {
        Decoders decoders = (Decoders)DECODERS.get();
        decoders.set(arrby, n2, n3, arrby2, n4, n5);
        try {
            int n6 = BinaryData.compare(decoders, schema);
            return n6;
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
    }

    public static int compare(byte[] arrby, int n2, byte[] arrby2, int n3, Schema schema) {
        return BinaryData.compare(arrby, n2, arrby.length - n2, arrby2, n3, arrby2.length - n3, schema);
    }

    public static int compareBytes(byte[] arrby, int n2, int n3, byte[] arrby2, int n4, int n5) {
        int n6 = n2 + n3;
        int n7 = n4 + n5;
        int n8 = n2;
        for (int i2 = n4; n8 < n6 && i2 < n7; ++n8, ++i2) {
            int n9 = 255 & arrby[n8];
            int n10 = 255 & arrby2[i2];
            if (n9 == n10) continue;
            return n9 - n10;
        }
        return n3 - n5;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int encodeBoolean(boolean bl, byte[] arrby, int n2) {
        byte by = bl ? (byte)1 : 0;
        arrby[n2] = by;
        return 1;
    }

    public static int encodeDouble(double d2, byte[] arrby, int n2) {
        long l2 = Double.doubleToRawLongBits((double)d2);
        int n3 = (int)(l2 & -1L);
        int n4 = (int)(-1L & l2 >>> 32);
        arrby[n2] = (byte)(n3 & 255);
        arrby[n2 + 4] = (byte)(n4 & 255);
        arrby[n2 + 5] = (byte)(255 & n4 >>> 8);
        arrby[n2 + 1] = (byte)(255 & n3 >>> 8);
        arrby[n2 + 2] = (byte)(255 & n3 >>> 16);
        arrby[n2 + 6] = (byte)(255 & n4 >>> 16);
        arrby[n2 + 7] = (byte)(255 & n4 >>> 24);
        arrby[n2 + 3] = (byte)(255 & n3 >>> 24);
        return 8;
    }

    public static int encodeFloat(float f2, byte[] arrby, int n2) {
        int n3 = Float.floatToRawIntBits((float)f2);
        arrby[n2] = (byte)(n3 & 255);
        int n4 = 1 + 1;
        arrby[n2 + 1] = (byte)(255 & n3 >>> 8);
        int n5 = n4 + 1;
        arrby[n2 + 2] = (byte)(255 & n3 >>> 16);
        n5 + 1;
        arrby[n2 + 3] = (byte)(255 & n3 >>> 24);
        return 4;
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int encodeInt(int var0_1, byte[] var1, int var2_2) {
        block4 : {
            block3 : {
                var3_3 = var0_1 << 1 ^ var0_1 >> 31;
                var4_4 = var2_2;
                if ((var3_3 & -128) == 0) break block3;
                var6_5 = var2_2 + 1;
                var1[var2_2] = (byte)(255 & (var3_3 | 128));
                if ((var3_3 >>>= 7) <= 127) break block4;
                var2_2 = var6_5 + 1;
                var1[var6_5] = (byte)(255 & (var3_3 | 128));
                if ((var3_3 >>>= 7) <= 127) break block3;
                var6_5 = var2_2 + 1;
                var1[var2_2] = (byte)(255 & (var3_3 | 128));
                if ((var3_3 >>>= 7) <= 127) break block4;
                var2_2 = var6_5 + 1;
                var1[var6_5] = (byte)(255 & (var3_3 | 128));
                var3_3 >>>= 7;
            }
lbl17: // 2 sources:
            do {
                var5_6 = var2_2 + 1;
                var1[var2_2] = (byte)var3_3;
                return var5_6 - var4_4;
                break;
            } while (true);
        }
        var2_2 = var6_5;
        ** while (true)
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static int encodeLong(long var0, byte[] var2_2, int var3_1) {
        block4 : {
            block5 : {
                var4_3 = var0 << 1 ^ var0 >> 63;
                var6_4 = var3_1;
                if ((-128L & var4_3) == 0L) break block4;
                var8_5 = var3_1 + 1;
                var2_2[var3_1] = (byte)(255L & (128L | var4_3));
                if ((var4_3 >>>= 7) <= 127L) break block5;
                var3_1 = var8_5 + 1;
                var2_2[var8_5] = (byte)(255L & (128L | var4_3));
                if ((var4_3 >>>= 7) <= 127L) break block4;
                var8_5 = var3_1 + 1;
                var2_2[var3_1] = (byte)(255L & (128L | var4_3));
                if ((var4_3 >>>= 7) <= 127L) break block5;
                var3_1 = var8_5 + 1;
                var2_2[var8_5] = (byte)(255L & (128L | var4_3));
                if ((var4_3 >>>= 7) <= 127L) break block4;
                var8_5 = var3_1 + 1;
                var2_2[var3_1] = (byte)(255L & (128L | var4_3));
                if ((var4_3 >>>= 7) > 127L) {
                    var3_1 = var8_5 + 1;
                    var2_2[var8_5] = (byte)(255L & (128L | var4_3));
                    if ((var4_3 >>>= 7) > 127L) {
                        var8_5 = var3_1 + 1;
                        var2_2[var3_1] = (byte)(255L & (128L | var4_3));
                        if ((var4_3 >>>= 7) > 127L) {
                            var3_1 = var8_5 + 1;
                            var2_2[var8_5] = (byte)(255L & (128L | var4_3));
                            if ((var4_3 >>>= 7) > 127L) {
                                var9_6 = var3_1 + 1;
                                var2_2[var3_1] = (byte)(255L & (128L | var4_3));
                                var4_3 >>>= 7;
                                var3_1 = var9_6;
                                ** GOTO lbl39
                            } else {
                                ** GOTO lbl36
                            }
                        } else {
                            ** GOTO lbl35
                        }
                    } else {
                        ** GOTO lbl34
                    }
                }
                break block5;
lbl34: // 2 sources:
                break block4;
lbl35: // 2 sources:
                break block5;
lbl36: // 2 sources:
                break block4;
            }
            var3_1 = var8_5;
        }
        var7_7 = var3_1 + 1;
        var2_2[var3_1] = (byte)var4_3;
        return var7_7 - var6_4;
    }

    private static int hashBytes(int n2, HashData hashData, int n3, boolean bl) throws IOException {
        int n4 = n2;
        byte[] arrby = hashData.bytes.getBuf();
        int n5 = hashData.bytes.getPos();
        int n6 = n5 + n3;
        if (bl) {
            for (int i2 = n6 - 1; i2 >= n5; --i2) {
                n4 = n4 * 31 + arrby[i2];
            }
        } else {
            for (int i3 = n5; i3 < n6; ++i3) {
                n4 = n4 * 31 + arrby[i3];
            }
        }
        hashData.decoder.skipFixed(n3);
        return n4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int hashCode(HashData hashData, Schema schema) throws IOException {
        BinaryDecoder binaryDecoder = hashData.decoder;
        int n2 = 3.$SwitchMap$org$apache$avro$Schema$Type[schema.getType().ordinal()];
        int n3 = 0;
        switch (n2) {
            default: {
                throw new AvroRuntimeException("Unexpected schema to hashCode!");
            }
            case 1: {
                n3 = 1;
                Iterator iterator = schema.getFields().iterator();
                while (iterator.hasNext()) {
                    Schema.Field field = (Schema.Field)iterator.next();
                    if (field.order() == Schema.Field.Order.IGNORE) {
                        GenericDatumReader.skip(field.schema(), binaryDecoder);
                        continue;
                    }
                    n3 = n3 * 31 + BinaryData.hashCode(hashData, field.schema());
                }
                return n3;
            }
            case 2: 
            case 3: {
                n3 = ((Decoder)binaryDecoder).readInt();
            }
            case 14: {
                return n3;
            }
            case 11: {
                return Float.floatToIntBits((float)((Decoder)binaryDecoder).readFloat());
            }
            case 4: {
                long l2 = ((Decoder)binaryDecoder).readLong();
                return (int)(l2 ^ l2 >>> 32);
            }
            case 12: {
                long l3 = Double.doubleToLongBits((double)((Decoder)binaryDecoder).readDouble());
                return (int)(l3 ^ l3 >>> 32);
            }
            case 5: {
                Schema schema2 = schema.getElementType();
                n3 = 1;
                long l4 = ((Decoder)binaryDecoder).readArrayStart();
                while (l4 != 0L) {
                    for (long i2 = 0L; i2 < l4; ++i2) {
                        n3 = n3 * 31 + BinaryData.hashCode(hashData, schema2);
                    }
                    l4 = ((Decoder)binaryDecoder).arrayNext();
                }
                return n3;
            }
            case 6: {
                throw new AvroRuntimeException("Can't hashCode maps!");
            }
            case 7: {
                return BinaryData.hashCode(hashData, (Schema)schema.getTypes().get(((Decoder)binaryDecoder).readInt()));
            }
            case 8: {
                return BinaryData.hashBytes(1, hashData, schema.getFixedSize(), false);
            }
            case 9: {
                return BinaryData.hashBytes(0, hashData, ((Decoder)binaryDecoder).readInt(), false);
            }
            case 10: {
                return BinaryData.hashBytes(1, hashData, ((Decoder)binaryDecoder).readInt(), true);
            }
            case 13: 
        }
        if (!((Decoder)binaryDecoder).readBoolean()) return 1237;
        return 1231;
    }

    public static int hashCode(byte[] arrby, int n2, int n3, Schema schema) {
        HashData hashData = (HashData)HASH_DATA.get();
        hashData.set(arrby, n2, n3);
        try {
            int n4 = BinaryData.hashCode(hashData, schema);
            return n4;
        }
        catch (IOException iOException) {
            throw new AvroRuntimeException(iOException);
        }
    }

    public static int skipLong(byte[] arrby, int n2) {
        int n3 = n2 + 1;
        byte by = arrby[n2];
        while ((by & 128) != 0) {
            int n4 = n3 + 1;
            by = arrby[n3];
            n3 = n4;
        }
        return n3;
    }

    private static class Decoders {
        private final BinaryDecoder.BufferAccessor b1 = this.d1.getBufferAccessor();
        private final BinaryDecoder.BufferAccessor b2 = this.d2.getBufferAccessor();
        private final BinaryDecoder d1 = new BinaryDecoder(new byte[0], 0, 0);
        private final BinaryDecoder d2 = new BinaryDecoder(new byte[0], 0, 0);

        static /* synthetic */ BinaryDecoder access$000(Decoders decoders) {
            return decoders.d1;
        }

        static /* synthetic */ BinaryDecoder access$100(Decoders decoders) {
            return decoders.d2;
        }

        static /* synthetic */ BinaryDecoder.BufferAccessor access$200(Decoders decoders) {
            return decoders.b1;
        }

        static /* synthetic */ BinaryDecoder.BufferAccessor access$300(Decoders decoders) {
            return decoders.b2;
        }

        public void set(byte[] arrby, int n2, int n3, byte[] arrby2, int n4, int n5) {
            this.d1.configure(arrby, n2, n3);
            this.d2.configure(arrby2, n4, n5);
        }
    }

    private static class HashData {
        private final BinaryDecoder.BufferAccessor bytes = this.decoder.getBufferAccessor();
        private final BinaryDecoder decoder = new BinaryDecoder(new byte[0], 0, 0);

        public void set(byte[] arrby, int n2, int n3) {
            this.decoder.configure(arrby, n2, n3);
        }
    }

}

