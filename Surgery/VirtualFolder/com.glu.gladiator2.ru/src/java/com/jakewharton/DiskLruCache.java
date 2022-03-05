/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedInputStream
 *  java.io.BufferedWriter
 *  java.io.Closeable
 *  java.io.EOFException
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.FileWriter
 *  java.io.FilterOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.PrintStream
 *  java.io.Reader
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.ArrayIndexOutOfBoundsException
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.Void
 *  java.lang.reflect.Array
 *  java.nio.charset.Charset
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.concurrent.BlockingQueue
 *  java.util.concurrent.Callable
 *  java.util.concurrent.ExecutorService
 *  java.util.concurrent.Future
 *  java.util.concurrent.LinkedBlockingQueue
 *  java.util.concurrent.ThreadPoolExecutor
 *  java.util.concurrent.TimeUnit
 */
package com.jakewharton;

import com.playhaven.src.common.PHCrashReport;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DiskLruCache
implements Closeable {
    static final long ANY_SEQUENCE_NUMBER = -1L;
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_TMP = "journal.tmp";
    static final String MAGIC = "libcore.io.DiskLruCache";
    private static final String READ = "READ";
    private static final String REMOVE = "REMOVE";
    private static final Charset UTF_8 = Charset.forName((String)"UTF-8");
    static final String VERSION_1 = "1";
    private static DiskLruCache sharedDiskCache;
    private final int appVersion;
    private final Callable<Void> cleanupCallable;
    private final File directory;
    private final ExecutorService executorService;
    private final File journalFile;
    private final File journalFileTmp;
    private Writer journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries;
    private final long maxSize;
    private long nextSequenceNumber;
    private int redundantOpCount;
    private long size;
    private final int valueCount;

    public DiskLruCache() {
        this.size = 0L;
        this.lruEntries = new LinkedHashMap(0, 0.75f, true);
        this.nextSequenceNumber = 0L;
        this.executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, (BlockingQueue)new LinkedBlockingQueue());
        this.cleanupCallable = new Callable<Void>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public Void call() throws Exception {
                DiskLruCache diskLruCache;
                DiskLruCache diskLruCache2 = diskLruCache = DiskLruCache.this;
                synchronized (diskLruCache2) {
                    if (DiskLruCache.this.journalWriter == null) {
                        return null;
                    }
                    DiskLruCache.this.trimToSize();
                    if (DiskLruCache.this.journalRebuildRequired()) {
                        DiskLruCache.this.rebuildJournal();
                        DiskLruCache.this.redundantOpCount = 0;
                    }
                    return null;
                }
            }
        };
        this.directory = null;
        this.appVersion = -1;
        this.journalFile = null;
        this.journalFileTmp = null;
        this.valueCount = 1;
        this.maxSize = 1024L;
    }

    public DiskLruCache(File file, int n, int n2, long l) {
        this.size = 0L;
        this.lruEntries = new LinkedHashMap(0, 0.75f, true);
        this.nextSequenceNumber = 0L;
        this.executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, (BlockingQueue)new LinkedBlockingQueue());
        this.cleanupCallable = new /* invalid duplicate definition of identical inner class */;
        this.directory = file;
        this.appVersion = n;
        this.journalFile = new File(file, JOURNAL_FILE);
        this.journalFileTmp = new File(file, JOURNAL_FILE_TMP);
        this.valueCount = n2;
        this.maxSize = l;
    }

    private void checkNotClosed() {
        if (this.journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static <T> T[] copyOfRange(T[] arrT, int n, int n2) {
        int n3 = arrT.length;
        if (n > n2) {
            throw new IllegalArgumentException();
        }
        if (n < 0 || n > n3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int n4 = n2 - n;
        int n5 = Math.min((int)n4, (int)(n3 - n));
        Object[] arrobject = (Object[])Array.newInstance((Class)arrT.getClass().getComponentType(), (int)n4);
        System.arraycopy(arrT, (int)n, (Object)arrobject, (int)0, (int)n5);
        return arrobject;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void createSharedDiskCache(File file, int n, int n2, long l) {
        Class<DiskLruCache> class_ = DiskLruCache.class;
        synchronized (DiskLruCache.class) {
            sharedDiskCache = new DiskLruCache(file, n, n2, l);
            try {
                sharedDiskCache.open();
                do {
                    return;
                    break;
                } while (true);
            }
            catch (Exception exception) {
                try {
                    PHCrashReport.reportCrash(exception, "DiskLruCache - createSharedDiskCache", PHCrashReport.Urgency.low);
                    return;
                }
                catch (Throwable throwable) {
                    throw throwable;
                }
                finally {
                    // ** MonitorExit[var8_4] (shouldn't be in output)
                }
            }
        }
    }

    private static void deleteContents(File file) throws IOException {
        File[] arrfile = file.listFiles();
        if (arrfile == null) {
            throw new IllegalArgumentException("not a directory: " + (Object)file);
        }
        for (File file2 : arrfile) {
            if (file2.isDirectory()) {
                DiskLruCache.deleteContents(file2);
            }
            if (file2.delete()) continue;
            throw new IOException("failed to delete file: " + (Object)file2);
        }
    }

    private static void deleteIfExists(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    public static DiskLruCache getSharedDiskCache() {
        Class<DiskLruCache> class_ = DiskLruCache.class;
        synchronized (DiskLruCache.class) {
            DiskLruCache diskLruCache = sharedDiskCache;
            // ** MonitorExit[var2] (shouldn't be in output)
            return diskLruCache;
        }
    }

    private static String inputStreamToString(InputStream inputStream) throws IOException {
        return DiskLruCache.readFully((Reader)new InputStreamReader(inputStream, UTF_8));
    }

    private boolean journalRebuildRequired() {
        return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
    }

    private static String readAsciiLine(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(80);
        do {
            int n;
            if ((n = inputStream.read()) == -1) {
                throw new EOFException();
            }
            if (n == 10) {
                int n2 = stringBuilder.length();
                if (n2 > 0 && stringBuilder.charAt(n2 - 1) == '\r') {
                    stringBuilder.setLength(n2 - 1);
                }
                return stringBuilder.toString();
            }
            stringBuilder.append((char)n);
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String readFully(Reader reader) throws IOException {
        try {
            int n;
            StringWriter stringWriter = new StringWriter();
            char[] arrc = new char[1024];
            while ((n = reader.read(arrc)) != -1) {
                stringWriter.write(arrc, 0, n);
            }
            String string2 = stringWriter.toString();
            return string2;
        }
        finally {
            reader.close();
        }
    }

    public static void setSharedDiskCache(DiskLruCache diskLruCache) {
        Class<DiskLruCache> class_ = DiskLruCache.class;
        synchronized (DiskLruCache.class) {
            sharedDiskCache = diskLruCache;
            // ** MonitorExit[var2_1] (shouldn't be in output)
            return;
        }
    }

    private void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            this.remove((String)((Map.Entry)this.lruEntries.entrySet().iterator().next()).getKey());
        }
    }

    private void validateKey(String string2) {
        if (string2.contains((CharSequence)" ") || string2.contains((CharSequence)"\n") || string2.contains((CharSequence)"\r")) {
            throw new IllegalArgumentException("keys must not contain spaces or newlines: \"" + string2 + "\"");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() throws IOException {
        DiskLruCache diskLruCache = this;
        synchronized (diskLruCache) {
            Writer writer = this.journalWriter;
            if (writer != null) {
                for (Entry entry : new ArrayList(this.lruEntries.values())) {
                    if (entry.currentEditor == null) continue;
                    entry.currentEditor.abort();
                }
                this.trimToSize();
                this.journalWriter.close();
                this.journalWriter = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void completeEdit(Editor editor, boolean bl) throws IOException {
        void var24_3 = this;
        synchronized (var24_3) {
            Entry entry = editor.entry;
            if (entry.currentEditor != editor) {
                throw new IllegalStateException();
            }
            int n = 0;
            if (bl) {
                boolean bl2 = entry.readable;
                n = 0;
                if (!bl2) {
                    int n2 = 0;
                    do {
                        int n3 = this.valueCount;
                        n = 0;
                        if (n2 >= n3) break;
                        if (!entry.getDirtyFile(n2).exists()) {
                            editor.abort();
                            throw new IllegalStateException("edit didn't create file " + n2);
                        }
                        ++n2;
                    } while (true);
                }
            }
            do {
                if (n < this.valueCount) {
                    File file = entry.getDirtyFile(n);
                    if (bl) {
                        if (file.exists()) {
                            long l;
                            File file2 = entry.getCleanFile(n);
                            file.renameTo(file2);
                            long l2 = entry.lengths[n];
                            Entry.access$700((Entry)entry)[n] = l = file2.length();
                            this.size = l + (this.size - l2);
                        }
                    } else {
                        DiskLruCache.deleteIfExists(file);
                    }
                } else {
                    this.redundantOpCount = 1 + this.redundantOpCount;
                    entry.currentEditor = null;
                    if (bl | entry.readable) {
                        entry.readable = true;
                        this.journalWriter.write("CLEAN " + entry.key + entry.getLengths() + '\n');
                        if (bl) {
                            long l = this.nextSequenceNumber;
                            this.nextSequenceNumber = 1L + l;
                            entry.sequenceNumber = l;
                        }
                    } else {
                        this.lruEntries.remove((Object)entry.key);
                        this.journalWriter.write("REMOVE " + entry.key + '\n');
                    }
                    if (this.size > this.maxSize || DiskLruCache.super.journalRebuildRequired()) {
                        this.executorService.submit(this.cleanupCallable);
                    }
                    return;
                }
                ++n;
            } while (true);
        }
    }

    public void delete() throws IOException {
        this.close();
        DiskLruCache.deleteContents(this.directory);
    }

    public Editor edit(String string2) throws IOException {
        return this.edit(string2, -1L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Editor edit(String string2, long l) throws IOException {
        void var14_3 = this;
        synchronized (var14_3) {
            Entry entry;
            DiskLruCache.super.checkNotClosed();
            DiskLruCache.super.validateKey(string2);
            Entry entry2 = (Entry)this.lruEntries.get((Object)string2);
            if (l != -1L) {
                if (entry2 == null) return null;
                long l2 = entry2.sequenceNumber;
                if (l2 != l) {
                    return null;
                }
            }
            if (entry2 == null) {
                Entry entry3 = (DiskLruCache)this.new Entry(string2);
                this.lruEntries.put((Object)string2, (Object)entry3);
                entry = entry3;
            } else {
                Editor editor = entry2.currentEditor;
                if (editor != null) {
                    return null;
                }
                entry = entry2;
            }
            Editor editor = (DiskLruCache)this.new Editor(entry);
            entry.currentEditor = editor;
            this.journalWriter.write("DIRTY " + string2 + '\n');
            this.journalWriter.flush();
            return editor;
        }
    }

    public void flush() throws IOException {
        DiskLruCache diskLruCache = this;
        synchronized (diskLruCache) {
            this.checkNotClosed();
            this.trimToSize();
            this.journalWriter.flush();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Snapshot get(String string2) throws IOException {
        void var11_2 = this;
        synchronized (var11_2) {
            DiskLruCache.super.checkNotClosed();
            DiskLruCache.super.validateKey(string2);
            Entry entry = (Entry)this.lruEntries.get((Object)string2);
            if (entry == null) {
                return null;
            }
            if (!entry.readable) {
                return null;
            }
            InputStream[] arrinputStream = new InputStream[this.valueCount];
            File[] arrfile = new File[this.valueCount];
            try {
                for (int i2 = 0; i2 < this.valueCount; ++i2) {
                    arrfile[i2] = entry.getCleanFile(i2);
                    arrinputStream[i2] = new FileInputStream(arrfile[i2]);
                }
            }
            catch (FileNotFoundException fileNotFoundException) {
                return null;
            }
            this.redundantOpCount = 1 + this.redundantOpCount;
            this.journalWriter.append((CharSequence)("READ " + string2 + '\n'));
            if (!DiskLruCache.super.journalRebuildRequired()) return (DiskLruCache)this.new Snapshot(string2, entry.sequenceNumber, arrinputStream, arrfile);
            this.executorService.submit(this.cleanupCallable);
            return (DiskLruCache)this.new Snapshot(string2, entry.sequenceNumber, arrinputStream, arrfile);
        }
    }

    public File getDirectory() {
        return this.directory;
    }

    public boolean isClosed() {
        return this.journalWriter == null;
    }

    public long maxSize() {
        return this.maxSize;
    }

    public void open() throws IOException {
        if (this.maxSize <= 0L) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (this.valueCount <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        }
        this.size = 0L;
        if (this.journalFile.exists()) {
            try {
                this.readJournal();
                this.processJournal();
                this.journalWriter = new BufferedWriter((Writer)new FileWriter(this.journalFile, true));
                return;
            }
            catch (IOException iOException) {
                System.out.println("DiskLruCache " + (Object)this.directory + " is corrupt: " + iOException.getMessage() + ", removing");
                this.delete();
            }
        }
        this.directory.mkdirs();
        this.rebuildJournal();
    }

    public void processJournal() throws IOException {
        DiskLruCache.deleteIfExists(this.journalFileTmp);
        Iterator iterator = this.lruEntries.values().iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry)iterator.next();
            if (entry.currentEditor == null) {
                for (int i2 = 0; i2 < this.valueCount; ++i2) {
                    this.size += entry.lengths[i2];
                }
                continue;
            }
            entry.currentEditor = null;
            for (int i3 = 0; i3 < this.valueCount; ++i3) {
                DiskLruCache.deleteIfExists(entry.getCleanFile(i3));
                DiskLruCache.deleteIfExists(entry.getDirtyFile(i3));
            }
            iterator.remove();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void readJournal() throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream((InputStream)new FileInputStream(this.journalFile));
        try {
            String string2 = DiskLruCache.readAsciiLine((InputStream)bufferedInputStream);
            String string3 = DiskLruCache.readAsciiLine((InputStream)bufferedInputStream);
            String string4 = DiskLruCache.readAsciiLine((InputStream)bufferedInputStream);
            String string5 = DiskLruCache.readAsciiLine((InputStream)bufferedInputStream);
            String string6 = DiskLruCache.readAsciiLine((InputStream)bufferedInputStream);
            if (!(MAGIC.equals((Object)string2) && VERSION_1.equals((Object)string3) && Integer.toString((int)this.appVersion).equals((Object)string4) && Integer.toString((int)this.valueCount).equals((Object)string5) && "".equals((Object)string6))) {
                throw new IOException("unexpected journal header: [" + string2 + ", " + string3 + ", " + string5 + ", " + string6 + "]");
            }
            do {
                this.readJournalLine(DiskLruCache.readAsciiLine((InputStream)bufferedInputStream));
            } while (true);
        }
        finally {
            DiskLruCache.closeQuietly((Closeable)bufferedInputStream);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void readJournalLine(String string2) throws IOException {
        String[] arrstring = string2.split(" ");
        if (arrstring.length < 2) {
            throw new IOException("unexpected journal line: " + string2);
        }
        String string3 = arrstring[1];
        if (arrstring[0].equals((Object)REMOVE) && arrstring.length == 2) {
            this.lruEntries.remove((Object)string3);
            return;
        } else {
            Entry entry;
            Entry entry2 = (Entry)this.lruEntries.get((Object)string3);
            if (entry2 == null) {
                Entry entry3 = (DiskLruCache)this.new Entry(string3);
                this.lruEntries.put((Object)string3, (Object)entry3);
                entry = entry3;
            } else {
                entry = entry2;
            }
            if (arrstring[0].equals((Object)CLEAN) && arrstring.length == 2 + this.valueCount) {
                entry.readable = true;
                entry.currentEditor = null;
                entry.setLengths(DiskLruCache.copyOfRange(arrstring, 2, arrstring.length));
                return;
            }
            if (arrstring[0].equals((Object)DIRTY) && arrstring.length == 2) {
                entry.currentEditor = (DiskLruCache)this.new Editor(entry);
                return;
            }
            if (arrstring[0].equals((Object)READ) && arrstring.length == 2) return;
            {
                throw new IOException("unexpected journal line: " + string2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void rebuildJournal() throws IOException {
        DiskLruCache diskLruCache = this;
        synchronized (diskLruCache) {
            if (this.journalWriter != null) {
                this.journalWriter.close();
            }
            BufferedWriter bufferedWriter = new BufferedWriter((Writer)new FileWriter(this.journalFileTmp));
            bufferedWriter.write(MAGIC);
            bufferedWriter.write("\n");
            bufferedWriter.write(VERSION_1);
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString((int)this.appVersion));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString((int)this.valueCount));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            Iterator iterator = this.lruEntries.values().iterator();
            do {
                if (!iterator.hasNext()) {
                    bufferedWriter.close();
                    this.journalFileTmp.renameTo(this.journalFile);
                    this.journalWriter = new BufferedWriter((Writer)new FileWriter(this.journalFile, true));
                    return;
                }
                Entry entry = (Entry)iterator.next();
                if (entry.currentEditor != null) {
                    bufferedWriter.write("DIRTY " + entry.key + '\n');
                    continue;
                }
                bufferedWriter.write("CLEAN " + entry.key + entry.getLengths() + '\n');
            } while (true);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public boolean remove(String var1) throws IOException {
        block6 : {
            block7 : {
                var11_2 = this;
                // MONITORENTER : var11_2
                DiskLruCache.super.checkNotClosed();
                DiskLruCache.super.validateKey(var1);
                var3_3 = (Entry)this.lruEntries.get((Object)var1);
                if (var3_3 == null) break block7;
                var4_4 = Entry.access$500(var3_3);
                var5_5 = 0;
                if (var4_4 == null) ** GOTO lbl17
            }
            var8_6 = false;
            // MONITOREXIT : var11_2
            return var8_6;
lbl-1000: // 1 sources:
            {
                this.size -= Entry.access$700(var3_3)[var5_5];
                Entry.access$700((Entry)var3_3)[var5_5] = 0L;
                ++var5_5;
lbl17: // 2 sources:
                if (var5_5 >= this.valueCount) break block6;
                ** while ((var10_7 = var3_3.getCleanFile((int)var5_5)).delete())
            }
lbl19: // 1 sources:
            throw new IOException("failed to delete " + (Object)var10_7);
        }
        this.redundantOpCount = 1 + this.redundantOpCount;
        this.journalWriter.append((CharSequence)("REMOVE " + var1 + '\n'));
        this.lruEntries.remove((Object)var1);
        if (DiskLruCache.super.journalRebuildRequired() == false) return true;
        this.executorService.submit(this.cleanupCallable);
        return true;
    }

    public long size() {
        DiskLruCache diskLruCache = this;
        synchronized (diskLruCache) {
            long l = this.size;
            return l;
        }
    }

    public class Editor {
        private final Entry entry;
        private boolean hasErrors;

        public Editor(Entry entry) {
            this.entry = entry;
        }

        public void abort() throws IOException {
            DiskLruCache.this.completeEdit(this, false);
        }

        public void commit() throws IOException {
            if (this.hasErrors) {
                DiskLruCache.this.completeEdit(this, false);
                DiskLruCache.this.remove(this.entry.key);
                return;
            }
            DiskLruCache.this.completeEdit(this, true);
        }

        public String getString(int n) throws IOException {
            InputStream inputStream = this.newInputStream(n);
            if (inputStream != null) {
                return DiskLruCache.inputStreamToString(inputStream);
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public InputStream newInputStream(int n) throws IOException {
            DiskLruCache diskLruCache;
            DiskLruCache diskLruCache2 = diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache2) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                }
                if (this.entry.readable) return new FileInputStream(this.entry.getCleanFile(n));
                return null;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public OutputStream newOutputStream(int n) throws IOException {
            DiskLruCache diskLruCache;
            DiskLruCache diskLruCache2 = diskLruCache = DiskLruCache.this;
            synchronized (diskLruCache2) {
                if (this.entry.currentEditor == this) return new FaultHidingOutputStream((Editor)this, (OutputStream)new FileOutputStream(this.entry.getDirtyFile(n)), null);
                throw new IllegalStateException();
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public void set(int n, String string2) throws IOException {
            OutputStreamWriter outputStreamWriter;
            try {
                outputStreamWriter = new OutputStreamWriter(this.newOutputStream(n), UTF_8);
            }
            catch (Throwable throwable) {
                void var4_5;
                outputStreamWriter = null;
                DiskLruCache.closeQuietly(outputStreamWriter);
                throw var4_5;
            }
            outputStreamWriter.write(string2);
            {
                catch (Throwable throwable) {}
            }
            DiskLruCache.closeQuietly((Closeable)outputStreamWriter);
        }

        private class FaultHidingOutputStream
        extends FilterOutputStream {
            final /* synthetic */ Editor this$1;

            private FaultHidingOutputStream(Editor editor, OutputStream outputStream) {
                this.this$1 = editor;
                super(outputStream);
            }

            /* synthetic */ FaultHidingOutputStream(Editor editor, OutputStream outputStream, 1 var3_2) {
                super(editor, outputStream);
            }

            public void close() {
                try {
                    this.out.close();
                    return;
                }
                catch (IOException iOException) {
                    this.this$1.hasErrors = true;
                    return;
                }
            }

            public void flush() {
                try {
                    this.out.flush();
                    return;
                }
                catch (IOException iOException) {
                    this.this$1.hasErrors = true;
                    return;
                }
            }

            public void write(int n) {
                try {
                    this.out.write(n);
                    return;
                }
                catch (IOException iOException) {
                    this.this$1.hasErrors = true;
                    return;
                }
            }

            public void write(byte[] arrby, int n, int n2) {
                try {
                    this.out.write(arrby, n, n2);
                    return;
                }
                catch (IOException iOException) {
                    this.this$1.hasErrors = true;
                    return;
                }
            }
        }

    }

    protected class Entry {
        private Editor currentEditor;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;

        public Entry(String string2) {
            this.key = string2;
            this.lengths = new long[DiskLruCache.this.valueCount];
        }

        private String cleanKey(String string2) {
            return string2.replace((CharSequence)File.separator, (CharSequence)"_");
        }

        private IOException invalidLengths(String[] arrstring) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString((Object[])arrstring));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void setLengths(String[] arrstring) throws IOException {
            if (arrstring.length != DiskLruCache.this.valueCount) {
                throw Entry.super.invalidLengths(arrstring);
            }
            try {
                for (int i2 = 0; i2 < arrstring.length; ++i2) {
                    this.lengths[i2] = Long.parseLong((String)arrstring[i2]);
                }
                return;
            }
            catch (NumberFormatException numberFormatException) {
                throw Entry.super.invalidLengths(arrstring);
            }
        }

        public File getCleanFile(int n) {
            return new File(DiskLruCache.this.directory, Entry.super.cleanKey(this.key) + "." + n);
        }

        public File getDirtyFile(int n) {
            return new File(DiskLruCache.this.directory, Entry.super.cleanKey(this.key) + "." + n + ".tmp");
        }

        public String getLengths() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            for (long l : this.lengths) {
                stringBuilder.append(' ').append(l);
            }
            return stringBuilder.toString();
        }
    }

    public class Snapshot
    implements Closeable {
        private final File[] files;
        private final InputStream[] ins;
        private final String key;
        private final long sequenceNumber;

        public Snapshot(String string2, long l, InputStream[] arrinputStream, File[] arrfile) {
            this.key = string2;
            this.sequenceNumber = l;
            this.ins = arrinputStream;
            this.files = arrfile;
        }

        public void close() {
            InputStream[] arrinputStream = this.ins;
            int n = arrinputStream.length;
            for (int i2 = 0; i2 < n; ++i2) {
                DiskLruCache.closeQuietly((Closeable)arrinputStream[i2]);
            }
        }

        public Editor edit() throws IOException {
            return DiskLruCache.this.edit(this.key, this.sequenceNumber);
        }

        public InputStream getInputStream(int n) {
            return this.ins[n];
        }

        public File getInputStreamFile(int n) {
            return this.files[n];
        }

        public String getString(int n) throws IOException {
            return DiskLruCache.inputStreamToString(this.getInputStream(n));
        }
    }

}

