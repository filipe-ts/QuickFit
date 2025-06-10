(ns front-end.register
  (:require [front-end.api :as api]
            )
  )

(defn register-user
  "Registers a new user in the API.

   Parameters:
   - name: User's name
   - age: User's age
   - height: User's height
   - weight: User's weight

   Returns a parsed JSON response on success or an error message on failure."
  [name age height weight gender]
  (api/add-user name age height weight gender)
  )

(defn register-food
  ""
  [food_name quantity unit time]
  (api/add-food food_name quantity unit time)
  )

(defn register-exercise
  ""
  [query time]
  (api/add-exercise query time)
  )

(defn get-history
  ""
  [start-date end-date]
  (api/get-history start-date end-date)
  )

(defn get-balance
  ""
  [start-date end-date]
  (api/get-balance start-date end-date)
  )
