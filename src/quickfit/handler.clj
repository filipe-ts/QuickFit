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
(def count-atom history/history-size)

(defroutes app-routes
  (GET "/" request (str "Hello," (get-in request [:query-params "name"])))
  (GET "/balance" request
    (let [
          start (get-in request [:query-params "start"])
          end (get-in request [:query-params "end"])
          history (quickfit.history/get-time-period start end history-atom)
          ]
      {
       :status 200
       :headers {"Content-Type" "application/json"}
       :body (cheshire/generate-string {:value (calc/cal-balance history)})
       }
      )
 )
  (POST "/add_food" request (
                         let [
                               time (get-in request [:query-params "time"])
                               food_name (get-in request [:query-params "food_name"])
                               unit (get-in request [:query-params "unit"])
                               quantity (get-in request [:query-params "quantity"])
                               food_data (quickfit.service-fatsecret-api/search food_name quantity unit)
                               success (quickfit.history/create-transaction food_data time history-atom count-atom)
                               ]
                            (if (true? success)
                              {
                               :status 201
                               :headers {"Content-Type" "application/json"}
                               :body (cheshire/generate-string {:message "Food added successfully"})
                               }
                              {
                               :status 400
                               :headers {"Content-Type" "application/json"}
                               :body (cheshire/generate-string {:message "Invalid food data"})
                               }
                              )
                           )
                         )
 (POST "/add_exercise" request (
                             let [
                                  time (get-in request [:query-params "time"])
                                  query (get-in request [:query-params "query"])
                                  exercise_data (quickfit.service-nutritionix-api/search query)
                                  success (quickfit.history/create-transaction exercise_data time history-atom count-atom)
                                  ]
                             (if (true? success)
                               {
                                :status 201
                                :headers {"Content-Type" "application/json"}
                                :body (cheshire/generate-string {:message "Exercise added successfully"})
                                }
                               {
                                :status 400
                                :headers {"Content-Type" "application/json"}
                                :body (cheshire/generate-string {:message "Exercise not valid or not found"})
                                }
                               )
                             )
                           )
  (GET "/history" request (let [
                                start (get-in request [:query-params "start"])
                                end (get-in request [:query-params "end"])
                                history (quickfit.history/get-time-period start end history-atom)
                                ]
                            {
                             :status 200
                             :headers {"Content-Type" "application/json"}
                             :body (cheshire/generate-string history)
                             }
                            )
                      )
 (GET "/user" [] {
                  :status 200
                  :headers {"Content-Type" "application/json"}
                  :body (cheshire/generate-string @quickfit.history/user-atom)
                  })
(POST "/user" request (let [
                            name (get-in request [:query-params "name"])
                            age (get-in request [:query-params "age"])
                            weight (get-in request [:query-params "weight"])
                            height (get-in request [:query-params "height"])
                            ]
                        (quickfit.history/update-user {:name name :age age :weight weight :height height})
                        {
                         :status 200
                         :headers {"Content-Type" "application/json"}
                         :body (cheshire/generate-string {:message "User updated successfully"})
                         }
                         ))

  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes (-> site-defaults
                                (assoc-in [:security :anti-forgery] false))))
