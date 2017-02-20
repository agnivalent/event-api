# Event API

This is a JSON API for storing and querying statistics on events.
It uses non-persistent storage (datascript), so the data is lost if the app is being reset.

## Run locally

To run locally, execute `lein run` (requires `leiningen`)

## Staging

You can use staging on heroku: https://event-api-staging.herokuapp.com/

## Deployment

To deploy to heroku, create a new instance and push to heroku remote, as usual.

To deploy as a jar, create uberjar (`lein uberjar`), then run:
    java -cp target/uberjar/event-api-standalone.jar clojure.main -m event-api.core

## Methods

- exists
  Check whether the input with this data exists.
  path: `/events/exists`
  input: `{"user-id": ID, "action": ACTION, "time-stamp": TIME-STAMP}`
  example:
      $ curl -H "Content-Type: application/json" -X GET -d '{"user-id": 1, "action": "koko", "time-stamp": "2016-12-12"}' https://event-api-staging.herokuapp.com/events/exists
      false
