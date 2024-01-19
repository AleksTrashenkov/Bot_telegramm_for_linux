#!/bin/bash

# Запускаем dmesg и ищем указанную ошибку
if dmesg | grep -q "Disk /dev/sdb doesn't contain a valid partition table" || \
  journalctl --since "today" -n 50000 | grep -q "Disk /dev/sdb doesn't contain a valid partition table"; then
    echo "Обнаружена ошибка: Disk /dev/sdb doesn't contain a valid partition table"

    # После выполнения fsck можно выполнить перезагрузку, если это необходимо
    echo "Обнаружены ошибки, требуется перезагрузить сервер"
   # sudo reboot
else
    echo "Ошибок не обнаружено, сервер продолжает работу."
fi
