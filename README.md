# Stock Market
This application creates a fake process between brokers and a stock exchange by applying microservices using Clean Architecture, DDD, CQRS, and not defined technologies =) 

*Right now I'm still focused on solving the business process. Initially, my idea was to create using stacks/concepts like SAGA, Outbox, and Kafka, but I will not focus on it now. First, business logic then further I will concentrate on details.*

PS. Please don't take the domain concept seriously because it's much more complicated than this =D

## Bounded Context

![Context Diagram](documentation/boundaries.excalidraw.svg)

**Stock Exchange** will be responsible for holding the shares, company, and shareholders' information

**Auction Exchange** will execute the bid and ask (buy and sell) and define the price

**Broker** will manage the accounts of the investors holding their shares, and their money and send their orders to the auction

## Requirements
[Broker](broker)
