package com.example.demo.introspector;

import com.example.demo.annotation.Mask;
import com.example.demo.annotation.ObjectInString;
import com.example.demo.deserializer.ObjectInStringDeserializer;
import com.example.demo.serializer.CustomOffsetDateTimeSerializer;
import com.example.demo.serializer.MaskSerializer;
import com.example.demo.serializer.ObjectInStringSerializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;

@Slf4j
@NoArgsConstructor
public class CustomAnnotationIntrospector extends JacksonAnnotationIntrospector {

    private boolean isMask;
    private boolean logConfidential;

    public CustomAnnotationIntrospector(boolean isMask, boolean logConfidential) {
        this.isMask = isMask;
        this.logConfidential = logConfidential;
    }

    @Override
    protected boolean _isIgnorable(Annotated a) {
        return a.hasAnnotation(AssertTrue.class) || a.hasAnnotation(AssertFalse.class) || super._isIgnorable(a);
    }

    @Override
    public Object findSerializer(Annotated am) {
        if (am.hasAnnotation(Mask.class) && isMask) {
            if (!am.getAnnotation(Mask.class).isAlwaysMask() && logConfidential) {
                return null;
            }
            return MaskSerializer.class;
        }

        if (OffsetDateTime.class.isAssignableFrom(am.getRawType()))
            return CustomOffsetDateTimeSerializer.class;

        if (am.hasAnnotation(ObjectInString.class))
            return ObjectInStringSerializer.class;
        return super.findSerializer(am);
    }
    @Override
    public Object findDeserializer(Annotated am) {
        if (am.hasAnnotation(ObjectInString.class))
            return ObjectInStringDeserializer.class;
        return super.findDeserializer(am);
    }

}
