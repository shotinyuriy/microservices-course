# Shopping Cart Microservice
/current - is referencing a shopping cart for the current session.

## GET /carts/current/v1
### Successful response
If Shopping Cart does not exist in the current session it should bew created a new
+ Response 200 OK
```json
{
  "id": "003",
  "commerceItems": [
  ],
  "priceInfo": {
    "commerceItemsTotal": 0.0,
    "taxes": 0.0,
    "total": 0.0
  }
}
```

## POST /carts/current/commerceItems/v1
skuId must exist in product-catalog
quantity must be an integer grater than zero
+ Request
```json
{
  "skuId": "skuId001",
  "quantity": 2
}
```
### Successful Response
+ Response 200 OK
```json
{
  "id": "003",
  "commerceItems": [
    {
      "id": "ci001",
      "skuId": "skuId001",
      "productId": "prodId001",
      "quantity": 2,
      "price": 25.25,
      "totalPrice": 50.50,
      "taxes": {
        "stateTax": 0.0
      }
    }
  ],
  "priceInfo": {
    "commerceItemsTotal": 50.50,
    "taxes": 0.0,
    "total": 50.50
  }
}
```

### Unsuccessful Response
+ Response 400 Bad Request
```json
{
  "errors": [
    {
      "errorCode": "error.skuId.invalid",
      "value": "skuId001"
    },
    {
      "errorCode": "error.quantity.toSmall",
      "value": 0
    }
  ]
}
```

### NOTE
+ For that we might need to create a new service in product-catalog
 ### GET /catalog/search/products?skuId={skuId}
+ 

## DELETE /carts/current/commerceItems/v1/{id}
+ Response 200 OK

## POST /carts/current/shippingAddress/v1

+ Request
```json
{
  "shippingAddress": {
    "state": "FL"
  }
}
```

### Successful Response
+ Response 200 OK (application/json)
```json
{
  "id": "003",
  "commerceItems": [
      {
        "id": "ci001",
        "skuId": "skuId001",
        "productId": "prodId001",
        "quantity": 2,
        "price": 25.25,
        "totalPrice": 50.50,
        "taxes": {
          "stateTax": 5.05
        }
      }
    ],
  "shippingAddress": {
    "state": "FL"
  },
  "priceInfo": {
    "commerceItemsTotal": 50.50,
    "taxes": 5.05,
    "total": 55.55
  }
}
```
### Unsuccessful Response
+ Response 400 Bad Request
```json
{
  "errors": [
    {
      "errorCode": "error.state.missing",
      "value": "skuId001"
    },
    {
      "errorCode": "error.state.invalid",
      "value": "skuId001"
    }
  ]
}
```