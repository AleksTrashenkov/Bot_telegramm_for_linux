- Реализация телеграмм бота с функционалом (JDK 16):
1. API openweather - для получения погоды по наименованию населенного пункта;
2. API geocoder yandex - для получения наименования города по координатам;
3. API weather yandex - для получения погоды по координатам;
4. API openai - для взаимодействия с ChatGPT;
5. API VK - для публикации постов в группе в ВК и поиска пользователей по ФИО;
6. API kinopoisk unofficial - для получения информации о фильме по ключевым словам, фильмографии актера по имени;
7. API rasp yandex - для получения расписания поездов в ближайшей станции по координатам;
8. API developer yandex - для получения информации о квоте запросов к API yandex;
9. API invest tinkoff - для получения состояния инвестиционного счета инвестора.
- В бот включен функционал парсера торрент трекера, для получения фильмов и сериалов для скачивания.
Парсер реализван с использованием Jsoup и Selenium с применением WebDriver для браузера.
- Для подклюения к БД SQLite и запросам к ней, используется jdbc:sqlite.

В виду некоторых проблем с автозапуском бота в среде Linux, была удалена серверная часть, реализованная на JavaFX.
в проект включен Bash скрипт, для проверки состояния интернет соединения сервера, который запускается раз в 10 минут в cron 
(если интернета нет, скрипт отключает службу бота, если есть, то запускает службу), чтобы избежать ошибок подключения на стороне бота. 