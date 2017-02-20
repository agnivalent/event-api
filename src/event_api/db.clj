(ns event-api.db
  (:require [datascript.core :as d]))

;; (def schema {:event/action {:db/valueType :db.type/string
;;                             :db/cardinality :db.cardinality/one
;;                             :db/doc "Action performed"}
;;              :event/user-id {:db/valueType :db.type/integer
;;                              :db/cardinality :db.cardinality/one
;;                              :db/doc "ID of the user who performed the action"}
;;              :event/time-stamp {:db/valueType :db.type/datetime
;;                                 :db/cardinality :db.cardinality/one
;;                                 :db/doc "Date and time at which action was performed"}})

(def conn (d/create-conn {}))

(d/transact! conn [{:event/user-id 1
                    :event/action "Create"
                    :event/time-stamp "2016-02-12T00:14:41Z"}
                   {:event/user-id 2
                    :event/action "Create"
                    :event/time-stamp "2016-02-13T00:14:41Z"}
                   {:event/user-id 1
                    :event/action "Update"
                    :event/time-stamp "2016-02-13T00:50:41Z"}
                   {:event/user-id 3
                    :event/action "Update"
                    :event/time-stamp "2016-02-12T00:14:41Z"}])

(defn exists? [user-id action time-stamp]
  (-> (d/q '[:find ?e
             :in $ [?u ?a ?t]
             :where
             [?e :event/user-id ?u]
             [?e :event/action ?a]
             [?e :event/time-stamp ?t]]
           @conn [user-id action time-stamp])
      empty?
      not))

(defn time-of-last-action [user-id]
  (ffirst (d/q '[:find (max ?t)
                 :in $ ?u
                 :where
                 [?e :event/user-id ?u]

                 [?e :event/time-stamp ?t]]
               @conn user-id)))

(defn users-performed-something [time-stamp]
  (map first (d/q '[:find ?u
                    :in $ ?t
                    :where
                    [?e :event/user-id ?u]
                    [?e :event/time-stamp ?t]]
                  @conn time-stamp)))

(defn time-last-performed [action]
  (map first (d/q '[:find (max ?t)
                    :in $ ?a
                    :where
                    [?e :event/action ?a]
                    [?e :event/time-stamp ?t]]
                  @conn action)))
