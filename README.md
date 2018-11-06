# CodeFest Gatling demo

Презентация инструмента Gatling для [доклада](https://2017.codefest.ru/lecture/1218) на [CodeFest](https://2017.codefest.ru), 2017.


## Структура проекта

- `app/` тестовое приложение News REST API на [Play Framework](https://playframework.com/)
- `metrics/` конфигурация InfluxDB и шаблон дашборда Grafana
- `slides/` слайды доклада
- `test/` нагрузочные тесты Gatling
- `docker-compose.yml` описание локального окружения в Docker-контейнерах (приложение, PostgreSQL, InfluxDB и Grafana)


## Установка

Вам необходим Docker Engine (минимум версии 17.03.0-ce) и Docker Compose (минимум версии 1.10.0) для локального окружения, см. [инструкции](https://docs.docker.com/compose/install/) для их установки. Затем запустите:

```
$ docker-compose up -d

Creating network "codefestgatling_default" with driver "bridge"
Pulling influxdb (influxdb:1.2.1-alpine)...
...
Creating codefestgatling_influxdb_1
Creating codefestgatling_db_1
Creating codefestgatling_app_1
Creating codefestgatling_grafana_1
```

Проверка:

```
$ docker-compose ps

Name                         Command                         State   Ports                      
-------------------------------------------------------------------------------------------------------------------
codefestgatling_app_1        bin/app                         Up      0.0.0.0:9000->9000/tcp                         
codefestgatling_db_1         docker-entrypoint.sh postgres   Up      0.0.0.0:6433->5432/tcp                         
codefestgatling_grafana_1    /run.sh                         Up      0.0.0.0:3000->3000/tcp                         
codefestgatling_influxdb_1   /entrypoint.sh influxd          Up      0.0.0.0:2003->2003/tcp, 0.0.0.0:8086->8086/tcp
```

Откройте [Grafana](http://localhost:3000) с логином и паролем `admin/admin` и создайте InfluxDB источник:
- Name `InfluxDB`
- Type `InfluxDB`
- Url `http://influxdb:8086`
- Database `gatlingdb`

Импортируйте дашборд из шаблона `metrics/gatling.json` (оригинал доступен на странице [документации](http://gatling.io/docs/current/realtime_monitoring/#grafana)).

Также понадобится [IntelliJ IDEA Community Edition](http://www.jetbrains.com/idea/#chooseYourEdition) с плагином Scala для работы с тестами. Откройте директорию `test` в IDE и следуйте инструкциям для импорта SBT проекта.

Для запуска тестов в IDE создайте SBT Task с командой `"gatling:testOnly LoadTest"`.

Для очистки окружения:

```
$ docker-compose down --volumes --rmi all
```
