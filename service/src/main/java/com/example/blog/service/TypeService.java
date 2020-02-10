package com.example.blog.service;

import com.example.blog.modle.Type;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> listType();

    Page<Type> listType(Pageable pageable);

    List<Type> listType(Integer size);

    Type updateType(Long id, Type type) throws NotFoundException;

    void deleteType(Long id);
}
