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

```
java -cp target/uberjar/event-api-standalone.jar clojure.main -m event-api.core
```

## Methods

- **add event**

  Add new event. Returns ID.

  method: `POST`

  path: `/events`

  input: `{"user-id": ID, "action": ACTION, "time-stamp": TIME-STAMP}`

  example:
  ```sh
  $ curl -H "Content-Type: application/json" -X POST -d '{"user-id": 1, "action": "Update", "time-stamp": "2016-02-12T00:14:41Z"}' https://event-api-staging.herokuapp.com/events
  {"id": 1}
  ```

- **exists**

  Check whether the input with this data exists.

  method: `GET`

  path: `/events/exists`

  input: `{"user-id": ID, "action": ACTION, "time-stamp": TIME-STAMP}`

  example:
  ```sh
  $ curl -H "Content-Type: application/json" -X GET -d '{"user-id": 1, "action": "Update", "time-stamp": "2016-02-12T00:15:41Z"}' https://event-api-staging.herokuapp.com/events/exists
  false
  ```

- **time-last-performed**

  When was this action performed last time?

  method: `GET`

  path: `/events/time-last-performed`

  input: `{"action": ACTION}`

  example:
  ```sh
  $ curl -H "Content-Type: application/json" -X GET -d '{"action": "Update"}' https://event-api-staging.herokuapp.com/events/time-last-performed
  2016-02-12T03:01:21.000Z
  ```

- **users-performed-something**

  Returns a list of users that did something at that time

  method: `GET`

  path: `/events/users-performed-something`

  input: `{"time-stamp": TIME-STAMP}`

  example:
  ```sh
  $ curl -H "Content-Type: application/json" -X GET -d '{"time-stamp": "2016-02-12T00:14:41Z"}' https://event-api-staging.herokuapp.com/events/users-performed-something
  [1]
  ```

- **time-of-last-action**

  When did this user performed an action last time?

  method: `GET`

  path: `/events/time-of-last-action`

  input: `{"user-id": USER-ID}`

  example:
  ```sh
  $ curl -H "Content-Type: application/json" -X GET -d '{"user-id": 1}' https://event-api-staging.herokuapp.com/events/time-of-last-action
  2016-02-12T00:14:41.000Z
  ```
