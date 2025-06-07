# Demo project - Microservices with Spring & Kafka + Docker

## Description
The project works with 3 microservices: \
`order-service` -  sends `Order` events to the Kafka topic and orchestrates the process of a distributed transaction \
`payment-service` - performs local transaction on customer account basing on the `Order` price \
`stock-service` - performs local transaction on store basing on number of products in the `Order`

Here's the diagram with the architecture:

``` mermaid
flowchart LR
 subgraph OrderServiceBox["order service"]
        orderNew["orders NEW"]
        orderConfirm["orders CONFIRM"]
  end
 subgraph Kafka["Kafka"]
    direction TB
        kafkaOrders["orders"]
        kafkaStock["stock-orders"]
        kafkaPayment["payment-orders"]
  end
 subgraph StockSvc["stock service"]
        stockResult["orders ACCEPT / REJECT"]
        stockSvcConsumer["stockSvcConsumer"]
  end
 subgraph PaymentSvc["payment service"]
        paymentResult["orders ACCEPT / REJECT"]
        paymentSvcConsumer["paymentSvcConsumer"]
  end
    stockSvcConsumer --> stockResult
    paymentSvcConsumer --> paymentResult
    orderNew --> kafkaOrders
    orderConfirm --> kafkaOrders
    kafkaOrders --> stockSvcConsumer & paymentSvcConsumer
    stockResult --> kafkaStock
    paymentResult --> kafkaPayment
    kafkaStock --> joinBlock["JOIN streams"]
    kafkaPayment --> joinBlock
    joinBlock --> OrderServiceBox
```

(1) `order-service` sends a new `Order` -> `status == NEW` \
(2) `payment-service` & `stock-service` receive `Order` and handles it by performing a local transaction on the data \
(3) `payment-service` & `stock-service` send a reponse `Order` -> `status == ACCEPT` or `status == REJECT` \
(4) `order-service` process incoming stream of orders from `payment-service` & `stock-service`, join them by `Order` id and sends Order with a new status -> `status == CONFIRMATION` or `status == ROLLBACK` or `status == REJECTED` \
(5) `payment-service` & `stock-service` receive `Order` with a final status and "commit" or "rollback" a local transaction make before

## Running on Docker locally
You can easily run all the apps on Docker with Spring Boot support for
(a) Testcontainers
(b) Docker Compose

(a) For Testcontainers
Go to the `order-service` directory and execute:
```shell
$ mvn clean spring-boot:test-run
```

Then go to the `payment-service` directory and execute:
```shell
$ mvn clean spring-boot:test-run
```

Finally go to the `stock-service` directory and execute:
```shell
$ mvn clean spring-boot:test-run
```

You will have three apps running with a single shared Kafka running on Testcontainers.

(b) For Docker Compose
First build the whole project and images with the following command:
```shell
$ mvn clean package -DskipTests -Pbuild-image
```

Then, you can go to the one of available directories: `order-service`, `payment-service` or `stock-service` and execute:
```shell
$ mvn spring-boot:run
```

You start your app and have Kafka and two other containers started with Docker Compose.