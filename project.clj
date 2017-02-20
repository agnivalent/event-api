(defproject event-api "0.1.0-SNAPSHOT"
  :description "API for storing and querying statistics on events"
  :url "https://event-api-staging.herokuapp.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-jetty-adapter "1.5.1"]
                 [ring/ring-devel "1.5.1"]
                 [ring/ring-json "0.4.0"]
                 ;;[http-kit "2.2.0"]
                 [compojure "1.5.2"]
                 [environ "1.1.0"]
                 [datascript "0.15.5"]]
  :plugins [[lein-environ "1.1.0"]]
  :min-lein-version "2.0.0"
  :main ^:skip-aot event-api.core
  :target-path "target/%s"
  :uberjar-name "event-api-standalone.jar"
  :profiles {:production {:env {:production true}}})
