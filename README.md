# CodeFest Gatling demo

A Gatling presentation for [speech](https://2017.codefest.ru/lecture/1218) at [CodeFest](https://2017.codefest.ru), 2017.


## Repository structure

- `app/` sample News REST API service based on [Play Framework](https://playframework.com/)
- `metrics/` InfluxDB configuration and Grafana dashboard
- `test/` performance tests
- `docker-compose.yml` local environment in separate Docker containers (app, PostgreSQL, InfluxDB and Grafana)


## Installation

You need Docker Engine (at least 17.03.0-ce version) and Docker Compose (at least 1.10.0 version) for deploy local environment, see [instructions](https://docs.docker.com/compose/install/) for installation. Then run:

```
$ docker-compose up -d

Creating network "codefestgatling_back-tier" with driver "bridge"
Pulling influxdb (influxdb:1.2.1-alpine)...
...
Creating codefestgatling_influxdb_1
Creating codefestgatling_db_1
Creating codefestgatling_app_1
Creating codefestgatling_grafana_1
```

Check:

```
$ docker-compose ps

Name                         Command                         State   Ports                      
-------------------------------------------------------------------------------------------------------------------
codefestgatling_app_1        bin/app                         Up      0.0.0.0:9000->9000/tcp                         
codefestgatling_db_1         docker-entrypoint.sh postgres   Up      0.0.0.0:6432->5432/tcp                         
codefestgatling_grafana_1    /run.sh                         Up      0.0.0.0:3000->3000/tcp                         
codefestgatling_influxdb_1   /entrypoint.sh influxd          Up      0.0.0.0:2003->2003/tcp, 0.0.0.0:8086->8086/tcp
```

Open [Grafana](http://localhost:3000) with `admin/admin` credentials and create InfluxDB data source:
- Name `InfluxDB`
- Type `InfluxDB`
- Url `http://influxdb:8086`
- Database `gatlingdb`

Import dashboard from `metrics/gatling.json` template (downloaded from [docs page](http://gatling.io/docs/current/realtime_monitoring/#grafana)).

Also you need [IntelliJ IDEA Community Edition](http://www.jetbrains.com/idea/#chooseYourEdition) with Scala plugin for tests. Open `test` directory in IDE and follow instructions for import SBT project.

For running tests from IDE create SBT Task with `"gatling:testOnly LoadTest"` command.

For clean environment run:

```
$ docker-compose down --volumes --rmi all
```

## Demo plan

1. App overview, see [docs](http://localhost:9000/docs/#/News)
1. Think about choosing tool for stress testing
  1. Protocols: stateful or stateless
  1. Workload models: open or closed
  1. Test models: hit-based or scenarios
1. Gatling base simulation
  1. Scala SBT project structure
  1. First HTTP request to fetch all news
  1. HTTP checks
  1. Test configuration and HTTP conf definition
  1. Scenario definition
  1. Load test with open workload model
1. Add request to fetch news by id
  1. Feeders for dynamic test data
  1. User's case based scenarios vs request decomposition
  1. Switches
1. Add request for update news
  1. User's session
  1. Debug information
  1. Templates
1. Add requests for create and delete news
  1. Extract data from response
  1. Conditions
1. Asserts
1. Capacity test with closed workload model
  1. Loops
1. Log request URLs, HTTP codes and response times
1. Export real-time metrics and create dashboard in [Grafana](http://localhost:3000/dashboard/db/gatling)


## Summary

- Gatling is good stress tool for HTTP services (also can WebSockets, SSE and JMS)
- Performance tests as a code
- Simple DSL and powerful Scala
- Debug and simulation log extending
- Real-time metrics by Graphite protocol and detailed HTML-reports


## What's next
- Integrate in CI/CD (if some asserts are fails tests will be complete with non zero exit code)
- Add other tests (capacity test with open workload model, stepped load, stability or volume test, etc)
- Add pagination in news list request
- Think about requests on production (it will be most popular news items and early pages in list instead of sequences in tests, also they may be in db cache, may be need download access logs from production and use file feeders)
- Split create and delete requests (we need prepare items for delete in test before tests)
- [Gatling's cheat sheet](http://gatling.io/docs/current/cheat-sheet/)
- [Gatling's full documentation](http://gatling.io/docs/current/)
