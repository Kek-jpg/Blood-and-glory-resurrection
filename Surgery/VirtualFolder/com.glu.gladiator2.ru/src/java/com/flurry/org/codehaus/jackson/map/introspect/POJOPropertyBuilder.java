/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Comparable
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.flurry.org.codehaus.jackson.map.introspect;

import com.flurry.org.codehaus.jackson.map.BeanPropertyDefinition;
import com.flurry.org.codehaus.jackson.map.introspect.Annotated;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedConstructor;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedField;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMethod;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedParameter;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedWithParams;
import com.flurry.org.codehaus.jackson.map.introspect.AnnotationMap;

public class POJOPropertyBuilder
extends BeanPropertyDefinition
implements Comparable<POJOPropertyBuilder> {
    protected Node<AnnotatedParameter> _ctorParameters;
    protected Node<AnnotatedField> _fields;
    protected Node<AnnotatedMethod> _getters;
    protected final String _internalName;
    protected final String _name;
    protected Node<AnnotatedMethod> _setters;

    public POJOPropertyBuilder(POJOPropertyBuilder pOJOPropertyBuilder, String string) {
        this._internalName = pOJOPropertyBuilder._internalName;
        this._name = string;
        this._fields = pOJOPropertyBuilder._fields;
        this._ctorParameters = pOJOPropertyBuilder._ctorParameters;
        this._getters = pOJOPropertyBuilder._getters;
        this._setters = pOJOPropertyBuilder._setters;
    }

    public POJOPropertyBuilder(String string) {
        this._internalName = string;
        this._name = string;
    }

    private <T> boolean _anyExplicitNames(Node<T> node) {
        while (node != null) {
            if (node.explicitName != null && node.explicitName.length() > 0) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private <T> boolean _anyIgnorals(Node<T> node) {
        while (node != null) {
            if (node.isMarkedIgnored) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private <T> boolean _anyVisible(Node<T> node) {
        while (node != null) {
            if (node.isVisible) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    private /* varargs */ AnnotationMap _mergeAnnotations(int n, Node<? extends AnnotatedMember> ... arrnode) {
        AnnotationMap annotationMap = ((AnnotatedMember)arrnode[n].value).getAllAnnotations();
        int n2 = n + 1;
        do {
            block4 : {
                block3 : {
                    if (n2 >= arrnode.length) break block3;
                    if (arrnode[n2] == null) break block4;
                    annotationMap = AnnotationMap.merge(annotationMap, POJOPropertyBuilder.super._mergeAnnotations(n2, arrnode));
                }
                return annotationMap;
            }
            ++n2;
        } while (true);
    }

    private <T> Node<T> _removeIgnored(Node<T> node) {
        if (node == null) {
            return node;
        }
        return node.withoutIgnored();
    }

    private <T> Node<T> _removeNonVisible(Node<T> node) {
        if (node == null) {
            return node;
        }
        return node.withoutNonVisible();
    }

    private <T> Node<T> _trimByVisibility(Node<T> node) {
        if (node == null) {
            return node;
        }
        return node.trimByVisibility();
    }

    /*
     * Enabled aggressive block sorting
     */
    private Node<? extends AnnotatedMember> findRenamed(Node<? extends AnnotatedMember> node, Node<? extends AnnotatedMember> node2) {
        void var2_4;
        void var1_1;
        while (var1_1 != null) {
            void var0_3;
            String string = var1_1.explicitName;
            if (string != null && !string.equals((Object)var0_3._name)) {
                if (var2_4 == null) {
                    var2_4 = var1_1;
                } else if (!string.equals((Object)var2_4.explicitName)) {
                    throw new IllegalStateException("Conflicting property name definitions: '" + var2_4.explicitName + "' (for " + var2_4.value + ") vs '" + var1_1.explicitName + "' (for " + var1_1.value + ")");
                }
            }
            Node node3 = var1_1.next;
        }
        return var2_4;
    }

    private static <T> Node<T> merge(Node<T> node, Node<T> node2) {
        if (node == null) {
            return node2;
        }
        if (node2 == null) {
            return node;
        }
        return node.append(node2);
    }

    public void addAll(POJOPropertyBuilder pOJOPropertyBuilder) {
        this._fields = POJOPropertyBuilder.merge(this._fields, pOJOPropertyBuilder._fields);
        this._ctorParameters = POJOPropertyBuilder.merge(this._ctorParameters, pOJOPropertyBuilder._ctorParameters);
        this._getters = POJOPropertyBuilder.merge(this._getters, pOJOPropertyBuilder._getters);
        this._setters = POJOPropertyBuilder.merge(this._setters, pOJOPropertyBuilder._setters);
    }

    public void addCtor(AnnotatedParameter annotatedParameter, String string, boolean bl, boolean bl2) {
        this._ctorParameters = new Node<AnnotatedParameter>(annotatedParameter, this._ctorParameters, string, bl, bl2);
    }

    public void addField(AnnotatedField annotatedField, String string, boolean bl, boolean bl2) {
        this._fields = new Node<AnnotatedField>(annotatedField, this._fields, string, bl, bl2);
    }

    public void addGetter(AnnotatedMethod annotatedMethod, String string, boolean bl, boolean bl2) {
        this._getters = new Node<AnnotatedMethod>(annotatedMethod, this._getters, string, bl, bl2);
    }

    public void addSetter(AnnotatedMethod annotatedMethod, String string, boolean bl, boolean bl2) {
        this._setters = new Node<AnnotatedMethod>(annotatedMethod, this._setters, string, bl, bl2);
    }

    public boolean anyDeserializeIgnorals() {
        return this._anyIgnorals(this._fields) || this._anyIgnorals(this._setters) || this._anyIgnorals(this._ctorParameters);
    }

    public boolean anyExplicitNames() {
        return this._anyExplicitNames(this._fields) || this._anyExplicitNames(this._getters) || this._anyExplicitNames(this._setters) || this._anyExplicitNames(this._ctorParameters);
    }

    public boolean anyIgnorals() {
        return this._anyIgnorals(this._fields) || this._anyIgnorals(this._getters) || this._anyIgnorals(this._setters) || this._anyIgnorals(this._ctorParameters);
    }

    public boolean anySerializeIgnorals() {
        return this._anyIgnorals(this._fields) || this._anyIgnorals(this._getters);
    }

    public boolean anyVisible() {
        return this._anyVisible(this._fields) || this._anyVisible(this._getters) || this._anyVisible(this._setters) || this._anyVisible(this._ctorParameters);
    }

    public int compareTo(POJOPropertyBuilder pOJOPropertyBuilder) {
        if (this._ctorParameters != null) {
            if (pOJOPropertyBuilder._ctorParameters == null) {
                return -1;
            }
        } else if (pOJOPropertyBuilder._ctorParameters != null) {
            return 1;
        }
        return this.getName().compareTo(pOJOPropertyBuilder.getName());
    }

    @Override
    public boolean couldSerialize() {
        return this._getters != null || this._fields != null;
    }

    public String findNewName() {
        Node<? extends AnnotatedMember> node = this.findRenamed(this._fields, null);
        Node<? extends AnnotatedMember> node2 = this.findRenamed(this._getters, node);
        Node<? extends AnnotatedMember> node3 = this.findRenamed(this._setters, node2);
        Node<? extends AnnotatedMember> node4 = this.findRenamed(this._ctorParameters, node3);
        if (node4 == null) {
            return null;
        }
        return node4.explicitName;
    }

    @Override
    public AnnotatedMember getAccessor() {
        AnnotatedMember annotatedMember = this.getGetter();
        if (annotatedMember == null) {
            annotatedMember = this.getField();
        }
        return annotatedMember;
    }

    @Override
    public AnnotatedParameter getConstructorParameter() {
        if (this._ctorParameters == null) {
            return null;
        }
        Node<AnnotatedParameter> node = this._ctorParameters;
        do {
            if (!(((AnnotatedParameter)node.value).getOwner() instanceof AnnotatedConstructor)) continue;
            return (AnnotatedParameter)node.value;
        } while ((node = node.next) != null);
        return (AnnotatedParameter)this._ctorParameters.value;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public AnnotatedField getField() {
        if (this._fields == null) {
            return null;
        }
        AnnotatedField annotatedField = (AnnotatedField)this._fields.value;
        Node node = this._fields.next;
        while (node != null) {
            Class<?> class_;
            AnnotatedField annotatedField2 = (AnnotatedField)node.value;
            Class<?> class_2 = annotatedField.getDeclaringClass();
            if (class_2 == (class_ = annotatedField2.getDeclaringClass())) throw new IllegalArgumentException("Multiple fields representing property \"" + this.getName() + "\": " + annotatedField.getFullName() + " vs " + annotatedField2.getFullName());
            if (class_2.isAssignableFrom(class_)) {
                annotatedField = annotatedField2;
            } else if (!class_.isAssignableFrom(class_2)) throw new IllegalArgumentException("Multiple fields representing property \"" + this.getName() + "\": " + annotatedField.getFullName() + " vs " + annotatedField2.getFullName());
            node = node.next;
        }
        return annotatedField;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public AnnotatedMethod getGetter() {
        if (this._getters == null) {
            return null;
        }
        AnnotatedMethod annotatedMethod = (AnnotatedMethod)this._getters.value;
        Node node = this._getters.next;
        while (node != null) {
            Class<?> class_;
            AnnotatedMethod annotatedMethod2 = (AnnotatedMethod)node.value;
            Class<?> class_2 = annotatedMethod.getDeclaringClass();
            if (class_2 == (class_ = annotatedMethod2.getDeclaringClass())) throw new IllegalArgumentException("Conflicting getter definitions for property \"" + this.getName() + "\": " + annotatedMethod.getFullName() + " vs " + annotatedMethod2.getFullName());
            if (class_2.isAssignableFrom(class_)) {
                annotatedMethod = annotatedMethod2;
            } else if (!class_.isAssignableFrom(class_2)) throw new IllegalArgumentException("Conflicting getter definitions for property \"" + this.getName() + "\": " + annotatedMethod.getFullName() + " vs " + annotatedMethod2.getFullName());
            node = node.next;
        }
        return annotatedMethod;
    }

    @Override
    public String getInternalName() {
        return this._internalName;
    }

    @Override
    public AnnotatedMember getMutator() {
        AnnotatedMember annotatedMember = this.getConstructorParameter();
        if (annotatedMember == null && (annotatedMember = this.getSetter()) == null) {
            annotatedMember = this.getField();
        }
        return annotatedMember;
    }

    @Override
    public String getName() {
        return this._name;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public AnnotatedMethod getSetter() {
        if (this._setters == null) {
            return null;
        }
        AnnotatedMethod annotatedMethod = (AnnotatedMethod)this._setters.value;
        Node node = this._setters.next;
        while (node != null) {
            Class<?> class_;
            AnnotatedMethod annotatedMethod2 = (AnnotatedMethod)node.value;
            Class<?> class_2 = annotatedMethod.getDeclaringClass();
            if (class_2 == (class_ = annotatedMethod2.getDeclaringClass())) throw new IllegalArgumentException("Conflicting setter definitions for property \"" + this.getName() + "\": " + annotatedMethod.getFullName() + " vs " + annotatedMethod2.getFullName());
            if (class_2.isAssignableFrom(class_)) {
                annotatedMethod = annotatedMethod2;
            } else if (!class_.isAssignableFrom(class_2)) throw new IllegalArgumentException("Conflicting setter definitions for property \"" + this.getName() + "\": " + annotatedMethod.getFullName() + " vs " + annotatedMethod2.getFullName());
            node = node.next;
        }
        return annotatedMethod;
    }

    @Override
    public boolean hasConstructorParameter() {
        return this._ctorParameters != null;
    }

    @Override
    public boolean hasField() {
        return this._fields != null;
    }

    @Override
    public boolean hasGetter() {
        return this._getters != null;
    }

    @Override
    public boolean hasSetter() {
        return this._setters != null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void mergeAnnotations(boolean bl) {
        if (bl) {
            if (this._getters != null) {
                Node[] arrnode = new Node[]{this._getters, this._fields, this._ctorParameters, this._setters};
                AnnotationMap annotationMap = POJOPropertyBuilder.super._mergeAnnotations(0, arrnode);
                this._getters = this._getters.withValue((AnnotatedMethod)((AnnotatedMethod)this._getters.value).withAnnotations(annotationMap));
                return;
            } else {
                if (this._fields == null) return;
                {
                    Node[] arrnode = new Node[]{this._fields, this._ctorParameters, this._setters};
                    AnnotationMap annotationMap = POJOPropertyBuilder.super._mergeAnnotations(0, arrnode);
                    this._fields = this._fields.withValue((AnnotatedField)((AnnotatedField)this._fields.value).withAnnotations(annotationMap));
                    return;
                }
            }
        } else {
            if (this._ctorParameters != null) {
                Node[] arrnode = new Node[]{this._ctorParameters, this._setters, this._fields, this._getters};
                AnnotationMap annotationMap = POJOPropertyBuilder.super._mergeAnnotations(0, arrnode);
                this._ctorParameters = this._ctorParameters.withValue((AnnotatedParameter)((AnnotatedParameter)this._ctorParameters.value).withAnnotations(annotationMap));
                return;
            }
            if (this._setters != null) {
                Node[] arrnode = new Node[]{this._setters, this._fields, this._getters};
                AnnotationMap annotationMap = POJOPropertyBuilder.super._mergeAnnotations(0, arrnode);
                this._setters = this._setters.withValue((AnnotatedMethod)((AnnotatedMethod)this._setters.value).withAnnotations(annotationMap));
                return;
            }
            if (this._fields == null) return;
            {
                Node[] arrnode = new Node[]{this._fields, this._getters};
                AnnotationMap annotationMap = POJOPropertyBuilder.super._mergeAnnotations(0, arrnode);
                this._fields = this._fields.withValue((AnnotatedField)((AnnotatedField)this._fields.value).withAnnotations(annotationMap));
                return;
            }
        }
    }

    public void removeIgnored() {
        this._fields = this._removeIgnored(this._fields);
        this._getters = this._removeIgnored(this._getters);
        this._setters = this._removeIgnored(this._setters);
        this._ctorParameters = this._removeIgnored(this._ctorParameters);
    }

    public void removeNonVisible() {
        this._getters = this._removeNonVisible(this._getters);
        this._ctorParameters = this._removeNonVisible(this._ctorParameters);
        if (this._getters == null) {
            this._fields = this._removeNonVisible(this._fields);
            this._setters = this._removeNonVisible(this._setters);
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Property '").append(this._name).append("'; ctors: ").append(this._ctorParameters).append(", field(s): ").append(this._fields).append(", getter(s): ").append(this._getters).append(", setter(s): ").append(this._setters);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void trimByVisibility() {
        this._fields = this._trimByVisibility(this._fields);
        this._getters = this._trimByVisibility(this._getters);
        this._setters = this._trimByVisibility(this._setters);
        this._ctorParameters = this._trimByVisibility(this._ctorParameters);
    }

    public POJOPropertyBuilder withName(String string) {
        return new POJOPropertyBuilder((POJOPropertyBuilder)this, string);
    }

    private static final class Node<T> {
        public final String explicitName;
        public final boolean isMarkedIgnored;
        public final boolean isVisible;
        public final Node<T> next;
        public final T value;

        /*
         * Enabled aggressive block sorting
         */
        public Node(T t, Node<T> node, String string, boolean bl, boolean bl2) {
            this.value = t;
            this.next = node;
            if (string == null) {
                this.explicitName = null;
            } else {
                if (string.length() == 0) {
                    string = null;
                }
                this.explicitName = string;
            }
            this.isVisible = bl;
            this.isMarkedIgnored = bl2;
        }

        private Node<T> append(Node<T> node) {
            if (this.next == null) {
                return this.withNext(node);
            }
            return this.withNext(Node.super.append(node));
        }

        public String toString() {
            String string = this.value.toString() + "[visible=" + this.isVisible + "]";
            if (this.next != null) {
                string = string + ", " + this.next.toString();
            }
            return string;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public Node<T> trimByVisibility() {
            if (this.next == null) {
                return this;
            }
            Node<T> node = this.next.trimByVisibility();
            if (this.explicitName != null) {
                if (node.explicitName != null) return this.withNext(node);
                return this.withNext(null);
            }
            if (node.explicitName != null) return node;
            if (this.isVisible == node.isVisible) {
                return this.withNext(node);
            }
            if (!this.isVisible) return node;
            return this.withNext(null);
        }

        public Node<T> withNext(Node<T> node) {
            if (node == this.next) {
                return this;
            }
            return new Node<T>(this.value, node, this.explicitName, this.isVisible, this.isMarkedIgnored);
        }

        public Node<T> withValue(T t) {
            if (t == this.value) {
                return this;
            }
            return new Node<T>(t, this.next, this.explicitName, this.isVisible, this.isMarkedIgnored);
        }

        public Node<T> withoutIgnored() {
            Node<T> node;
            if (this.isMarkedIgnored) {
                if (this.next == null) {
                    return null;
                }
                return this.next.withoutIgnored();
            }
            if (this.next != null && (node = this.next.withoutIgnored()) != this.next) {
                return this.withNext(node);
            }
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Node<T> withoutNonVisible() {
            Node<T> node = this.next == null ? null : this.next.withoutNonVisible();
            if (!this.isVisible) return node;
            return this.withNext(node);
        }
    }

}

