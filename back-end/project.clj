(defproject quickfit "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [buddy/buddy-core "1.10.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-codec "1.1.3"]
                 [lynxeyes/dotenv "1.1.0"]
                 [clj-http "3.9.1"]
                 [cheshire "5.8.1"]
                 ]
  :plugins [[lein-ring "0.12.5"]]

  :ring {:handler quickfit.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
