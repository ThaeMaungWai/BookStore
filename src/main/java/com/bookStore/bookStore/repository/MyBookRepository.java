package com.bookStore.bookStore.repository;

import com.bookStore.bookStore.entity.MyBookList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBookRepository extends JpaRepository<MyBookList,Integer> {
}
