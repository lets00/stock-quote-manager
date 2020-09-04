# Stock-quote-manager

This project implements the stock-quote manager service passed as challenge by Inatel.

The stock-manager (the second service) can be found on [this](https://github.com/lets00/stock-manager) link.

## How to run
* JAVA Version = 11
First, you must have installed on your machine `docker` and `docker-compose`.So executes the **stock-manager** service first:

``` sh
$ git clone https://github.com/lets00/stock-manager.git
$ cd stock-manager/
$ docker-compose up -d
```

After that, execute:

```sh
$ ./mvnw package -DskipTests
$ java -jar target/stock-quote-manager-0.0.1-SNAPSHOT.jar

```

## Routes

### GET

* http://localhost:8081/stock-quotes/
returns all stock-quotes stored on server.

* http://localhost:8081/stock-quotes/:id
returns a specific stock-quotes (id: name of stock).

### POST 
* http://localhost:8081/stock-quotes
Store a new quote on server. The body must contains:
```js
{
	"marketId": "PET10",
	"date": "2019-01-08",
	"quote": 10
}
```
* marketId: Name of market(stock)
* date: Must be: YYYY-MM-DD
* quote: Value of quote

