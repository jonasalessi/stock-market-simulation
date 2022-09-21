# Broker Service
Application that place the orders to buy/sell shares for an investor and manage the investor's account.

Below the use cases with the functional requirements.

# UC1 - Creating account
![Event Flow](docs/flow1.excalidraw.svg)

- A user should be able to create an investor account
    - Pre Conditions:
        - have an initial deposit of R$10 to consider the account active
        - be a valid CPF (Brazilian ID) ([How to validate it](http://clubes.obmep.org.br/blog/a-matematica-nos-documentos-cpf/))
        - have only one account by CPF
        - not accept under 18 years old
## Account Status Flow
![Account Status Flow](docs/accountStatusflow.excalidraw.svg)

# UC2 - Placing an order
![Event Flow](docs/flow2.excalidraw.svg)

- The investor should be able to place an order to buy or sell shares
    - Pre Conditions:
        - To buy they must have the balance equivalent to the total amount plus fees
        - Both operations are possible between 10 am and 5 pm (it's the period that the market is open)
        - For fractional shares the quantity must be minimum 1
        - For integral shares the quantity must be minimum 100 and in chunks of 100
        - It's possible place an order for only one share at time
    - Highlights:
        - The broker is only the agent that buys or sells shares for the investor and holds the shares. The investor can change the broker migrating your shares to the new one
        - Share are divided in 2 categories, fractional(minimum 1 share) and integral(chunks of 100)
        - The fee is fixed 2% of the total amount
    - Post Conditions
      - A order should be sent to the Stock Exchange where for them buy is bid and sell is ask

