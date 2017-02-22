(ns event-api.db
  (:require [datascript.core :as d]
            [clj-time.coerce :as c]))

;; datascript seems not to be supporting types other than :db.type/ref
(def schema {:event/action {;; :db/valueType :db.type/string
                            :db/cardinality :db.cardinality/one
                            :db/doc "Action performed"}
             :event/user-id {;; :db/valueType :db.type/long
                             :db/cardinality :db.cardinality/one
                             :db/doc "ID of the user who performed the action"}
             :event/time-stamp {;; :db/valueType :db.type/instant
                                :db/cardinality :db.cardinality/one
                                :db/doc "Date and time at which action was performed"}})

(def conn (d/create-conn schema))

(defn- restore-date [l]
  (if l
    (c/from-long l)
    "never"))

(defn add-event! [{:keys [user-id action time-stamp]}]
  "adds an event and returns id"
  (->
   (d/transact! conn [{:event/user-id user-id
                       :event/action action
                       :event/time-stamp time-stamp}])
   :tx-data
   ffirst))

(defn exists? [user-id action time-stamp]
  "does event with this data exist?"
  (-> (d/q '[:find ?e
             :in $ [?u ?a ?t]
             :where
             [?e :event/user-id ?u]
             [?e :event/action ?a]
             [?e :event/time-stamp ?t]]
           @conn [user-id action (c/to-long time-stamp)])
      empty?
      not))

(defn time-of-last-action [user-id]
  "when did this user performed an action last time?"
  (restore-date (ffirst (d/q '[:find (max ?t)
                              :in $ ?u
                              :where
                              [?e :event/user-id ?u]
                              [?e :event/time-stamp ?t]]
                            @conn user-id))))

(defn users-performed-something [time-stamp]
  "return a list of users that did something at that time"
  (map first (d/q '[:find ?u
                    :in $ ?t
                    :where
                    [?e :event/user-id ?u]
                    [?e :event/time-stamp ?t]]
                  @conn (c/to-long time-stamp))))

(defn time-last-performed [action]
  "when was this action performed last time?"
  (restore-date (ffirst (d/q '[:find (max ?t)
                              :in $ ?a
                              :where
                              [?e :event/action ?a]
                              [?e :event/time-stamp ?t]]
                            @conn action))))
