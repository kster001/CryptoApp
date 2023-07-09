# CryptoApp

### API Endpoint Description:

#### 1. Trading endpoint:
    - url => /api/trade
    - Post Request
    - Require the following JSON request body
      {
          "username": [crypto application user's username],
          "size": [stock size],
          "cryptoPricingId": [crypto pricing id],
          "transactionType": "[transaction type - buy or sell]"
      }


#### 2. View wallet balance endpoint:
    - url => /api/wallets/{username}
    - Get Request
    - url parameter => crypto application user's username


#### 3. View latest pricing endpoint:
    - url => /api/cryptoPricings
    - Get Request


#### 4. View Transaction history endpoint:
    - url => /api/transactionHistory/{username}
    - Get Request
    - url parameter => crypto application user's username
