#!/bin/bash

# Здесь указываем IP-адрес или доменное имя, которое вы хотите проверять (например, google.com)
target_host="google.com"

# Пингуем целевой хост
ping -c 1 $target_host > /dev/null

# Проверяем код завершения пинга
if [ $? -eq 0 ]; then
  echo "Интернет доступен"

  # Проверяем состояние службы my_bot_java
  if systemctl is-active --quiet my_bot_java; then
    echo "Служба my_bot_java запущена. Проверка на ошибки..."
    
    # Проверяем вывод команды systemctl status my_bot_java на наличие указанных строк
    if systemctl status my_bot_java | grep -q "org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:236)\|org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:186)\|org.apache.http.impl.execchain.RetryExec.execute(RetryExec.java:89)\|org.apache.http.impl.execchain.RedirectExec.execute(RedirectExec.java:110)\|org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)\|org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)\|org.telegram.telegrambots.bots.DefaultAbsSender.sendHttpPostRequest(DefaultAbsSender.java:1079)\|org.telegram.telegrambots.bots.DefaultAbsSender.sendMethodRequest(DefaultAbsSender.java:1075)\|org.telegram.telegrambots.bots.DefaultAbsSender.sendApiMethod(DefaultAbsSender.java:1044)"; then
      echo "Обнаружены ошибки. Выполняем рестарт my_bot_java."
      systemctl restart my_bot_java
    else
      echo "Служба работает без ошибок."
    fi
  else
    echo "Служба my_bot_java не запущена."
    systemctl start my_bot_java
  fi
else
  echo "Интернет недоступен"

  # Останавливаем ваше Java-приложение, если интернет недоступен
  systemctl stop my_bot_java
fi
