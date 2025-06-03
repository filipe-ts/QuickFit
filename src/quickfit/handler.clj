(ns quickfit.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [quickfit.history :as history]
            [quickfit.calculations :as calc]
            [cheshire.core :as cheshire]
            )
  )

(def history-atom history/history-atom)

(defroutes app-routes
  (GET "/" request (str "Hello," (get-in request [:query-params "name"])))
  (GET "/balance" [] {
                      :status 200
                      :headers {"Content-Type" "application/json"}
                      :body (cheshire/generate-string {:value (calc/cal-balance @history-atom)})
                      }
                     )
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
