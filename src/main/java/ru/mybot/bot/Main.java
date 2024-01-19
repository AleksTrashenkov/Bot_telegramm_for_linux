package ru.mybot.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.sql.*;
import java.time.*;
import java.util.*;
import org.sputnikdev.bluetooth.URL;
import org.sputnikdev.bluetooth.manager.transport.Adapter;
import org.sputnikdev.bluetooth.manager.transport.Device;
import org.sputnikdev.bluetooth.manager.transport.Notification;
import tinyb.*;

public class Main {
    static BotSettings botSettings = new BotSettings();

    public static void main(String[] args) throws TelegramApiException, SQLException, IOException, InterruptedException {
        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setAllowedUpdates(List.of("message", "callback_query"));
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSettings.setBotSession(telegramBotsApi.registerBot(new BotJarvisTelegramm(botOptions)));
        System.out.println(LocalDateTime.now() + " : " + "Бот запущен");
/*        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.getDayOfWeek().getValue());
        System.out.println(localDateTime.getDayOfMonth());*/
}}
