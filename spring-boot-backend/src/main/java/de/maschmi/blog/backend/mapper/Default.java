package de.maschmi.blog.backend.mapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// See https://mapstruct.org/documentation/stable/reference/html/#non-shipped-annotations
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.CLASS)
public @interface Default {
}
