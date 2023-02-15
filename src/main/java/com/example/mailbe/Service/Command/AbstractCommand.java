package com.example.mailbe.Service.Command;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractCommand<T> {
    public abstract T execute();
}
