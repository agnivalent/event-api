(ns event-api.core
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ring.util.response :refer [response]]
            ;; [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
            [event-api.db :as db]
            [clj-time.format :as f]))

(defroutes app-routes
  (GET "/events/exists" {:keys [params]}
       (response (str (db/exists? (params :user-id) (params :action) (params :time-stamp)))))
  (GET "/events/time-of-last-action" {:keys [params]}
       (response (str (db/time-of-last-action (params :user-id)))))
  (GET "/events/users-performed-something" {:keys [params]}
       (response (db/users-performed-something (f/parse (params :time-stamp)))))
  (GET "/events/time-last-performed" {:keys [params]}
       (response (str (db/time-last-performed (params :action)))))
  (POST "/events" {:keys [params]}
        (let [event (select-keys params [:user-id :action :time-stamp])]
          (response {:id (db/add-event! (assoc event :time-stamp (f/parse (:time-stamp event))))}))))

(def app
  (-> (site #'app-routes)
      ;; (wrap-reload)
      (wrap-json-params)
      (wrap-json-response)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty app {:port port :join? false})))
