package ru.tinkoff.edu.java.bot.telegram.interfaces;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.util.List;

public interface Bot extends AutoCloseable, UpdatesListener {
    void start();

    @Override
    void close() throws Exception;

    @Override
    int process(List<Update> list);
}
