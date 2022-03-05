/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.annotation.Annotation
 */
package com.flurry.org.codehaus.jackson.map;

import com.flurry.org.codehaus.jackson.map.introspect.AnnotatedMember;
import com.flurry.org.codehaus.jackson.map.util.Annotations;
import com.flurry.org.codehaus.jackson.map.util.Named;
import com.flurry.org.codehaus.jackson.type.JavaType;
import java.lang.annotation.Annotation;

public interface BeanProperty
extends Named {
    public <A extends Annotation> A getAnnotation(Class<A> var1);

    public <A extends Annotation> A getContextAnnotation(Class<A> var1);

    public AnnotatedMember getMember();

    @Override
    public String getName();

    public JavaType getType();

    public static class Std
    implements BeanProperty {
        protected final Annotations _contextAnnotations;
        protected final AnnotatedMember _member;
        protected final String _name;
        protected final JavaType _type;

        public Std(String string, JavaType javaType, Annotations annotations, AnnotatedMember annotatedMember) {
            this._name = string;
            this._type = javaType;
            this._member = annotatedMember;
            this._contextAnnotations = annotations;
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> class_) {
            return this._member.getAnnotation(class_);
        }

        @Override
        public <A extends Annotation> A getContextAnnotation(Class<A> class_) {
            if (this._contextAnnotations == null) {
                return null;
            }
            return this._contextAnnotations.get(class_);
        }

        @Override
        public AnnotatedMember getMember() {
            return this._member;
        }

        @Override
        public String getName() {
            return this._name;
        }

        @Override
        public JavaType getType() {
            return this._type;
        }

        public Std withType(JavaType javaType) {
            return new Std(this._name, javaType, this._contextAnnotations, this._member);
        }
    }

}

