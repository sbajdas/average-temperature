# average-temperature
Calculates average yearly temperature for given cities. Big file ingestion, exposing data with REST endpoint.

## Usage

File is ingested automatically on application start. It should detect file changes automatically after saving new file version- no restart needed. File with
data is located in `src/main/resources/example_file.csv`

REST API endpoint address: `[GET]http://localhost:8080/city/{cityName}`

Sample response:

```
[
    {
        "year": 2018,
        "averageTemperature": 13.5
    },
    {
        "year": 2019,
        "averageTemperature": 13.9
    },
    {
        "year": 2020,
        "averageTemperature": 16.2
    },
    {
        "year": 2021,
        "averageTemperature": 15.7
    },
    {
        "year": 2022,
        "averageTemperature": 14.6
    },
    {
        "year": 2023,
        "averageTemperature": 15.4
    }
]
```

## File ingestion with Spark

I've experimented to ingest file data with Apache Spark. The results were surprisingly slower than plain Java solution (tested for a 4 GB file), so it's just an
option. To enable processing with Apache Spark one should change properties:
`spark.ingestion=true` and launch application with following JVM flags:

```
--add-exports
java.base/sun.nio.ch=ALL-UNNAMED
--add-opens
java.base/java.nio=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.util=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent=ALL-UNNAMED
--add-opens=java.base/sun.util.calendar=ALL-UNNAMED
```
