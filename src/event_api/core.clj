(ns event-api.core
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ring.util.response :refer [response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]))

(defroutes app
  (GET "/" []
       (response {:keke "kdsadoko23"}))
  (POST "/events" {:keys [params]}
        (response params)))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (wrap-json-params (wrap-json-response (wrap-reload (site #'app)))) {:port port :join? false})))
