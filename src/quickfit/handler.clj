(ns quickfit.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [dotenv :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  )


(defroutes app-routes
  (GET "/" request (str "Hello," (get-in request [:query-params "name"])))
           ;(GET "/" request (str request))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
