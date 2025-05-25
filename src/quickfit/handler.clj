(ns quickfit.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [dotenv :refer [env]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def fatsecret-auth-url (env :FATSECRET_URL))
(def fatsecret-search-url (env :FATSECRET_SEARCH_URL))
(def fatsecret-byid-url (env :FATSECRET_BYID_URL))

(defroutes app-routes
  (GET "/" request (str "Hello," (get-in request [:query-params "name"])))
           ;(GET "/" request (str request))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
