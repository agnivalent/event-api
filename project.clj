(defproject event-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring/ring-jetty-adapter "1.5.1"]
                 ;;[http-kit "2.2.0"]
                 [compojure "1.5.2"]
                 [environ "1.1.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :min-lein-version "2.0.0"
  :main ^:skip-aot event-api.core
  :target-path "target/%s"
  :uberjar-name "event-api-standalone.jar"
  :profiles {:production {:env {:production true}}})
