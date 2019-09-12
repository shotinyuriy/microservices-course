# Taxes Calculation Rest Resource V2

# GET /taxes/stateRules/v1
+ Response 200 Ok (application/json)
```json
{
  "stateRules": [
    {
      "id": "0123456789",
      "state": "PA",
      "stateRules": [
        {
          "categoryName": "books",
          "taxRule": 0
        },
        {
          "categoryName": "clothing",
          "taxRule": 0.00
        },
        {
          "categoryName": "electronic devices",
          "taxRule": 0.12
        }
      ]
    },
    {
      "id": "0123456788",
      "state": "CA",
      "taxRules": [
        {
          "categoryName": "books",
          "taxRule": 0.15
        },
        {
          "categoryName": "clothing",
          "taxRule": 0.11
        },
        {
          "categoryName": "electronic devices",
          "taxRule": 0.22
        }
      ]
    }
  ]
}
```

# GET /taxes/stateRules/v1/{state}
+ Response 200 Ok (application/json)
```json
{
  "id": "0123456789",
  "state": "PA",
  "taxRules": [
    {
      "categoryName": "books",
      "taxRule": 0
    },
    {
      "categoryName": "clothing",
      "taxRule": 0
    },
    {
      "categoryName": "electronic devices",
      "taxRule": 0.12
    }
  ]
}
```
1. if one state code does not exist in the DB return
+ Response 404 Not Found
```json
{
  "errors": [
    {
      "errorCode": "error.state.notFound",
      "value": "notFoundStateCode"
    }
  ]
}
```

# POST /taxes/stateRules/v1/PA
+ Request
```json
{
    "taxRules": [
      {
        "categoryName": "clothing",
        "taxRule": 0
      },
      {
        "categoryName": "books",
        "taxRule": 0.11
      },
      {
        "categoryName": "electronic devices",
        "taxRule": 0.12
      }
    ]
}
```

1. if one or more categories do not exist in the DB return
2. if rules is less than 0 or if it is more than 1
+ Response 400
```json
{
  "errors": [
    {
      "errorCode": "error.category.notFound",
      "value": "invalidCategoryName1"
    },
    {
      "errorCode": "error.category.notFound",
      "value": "invalidCategoryName2"
    },
    {
      "errorCode": "error.taxRule.tooSmall",
      "value": -0.5
    },
    {
      "errorCode": "error.taxRule.tooBig",
      "value": 1.01
    }
  ]
}
```