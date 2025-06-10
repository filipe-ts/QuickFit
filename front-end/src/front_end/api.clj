(ns front-end.api
  (:require
    [dotenv :refer [env]]
    [clj-http.client :as http-client]
    [cheshire.core :as json]
    )
  )

(def api-url (env "API_URL"))

(defn add-food
  "Retrieves food information from the API.

   Parameters:
   - food_name: Name of the food to search for
   - quantity: Amount of the food
   - unit: Unit of measurement ('g' or 'ml')
   - time: Time of consumption

   Returns a parsed JSON response on success or an error message on failure."
  [food_name quantity unit time]
  (let [
        response (http-client/post (str api-url "/add_food")
                                  {:query-params {:food_name food_name
                                                 :quantity quantity
                                                 :unit unit
                                                 :time time
                                                 }
                                   }
                                  )
        code (:status response)
        ]
    (if (or (= code 201) (= code 200))
      (json/parse-string (:body response) true)
      (str "Error: " code " " (:body response))
      )
  )
  )

(defn add-exercise
  "Adds exercise information to the API.

   Parameters:
   - query: Description of the exercise performed
   - time: Time when the exercise was performed

   Returns a parsed JSON response on success or an error message on failure."
  [query time]
  (let [
        response (http-client/post (str api-url "/add_exercise")
                                  {:query-params {:query query
                                                 :time time
                                                 }
                                   }
                                  )
        code (:status response)
        ]
    (if  (or (= code 201) (= code 200))
      (json/parse-string (:body response) true))
      (str "Error: " code " " (:body response))
    )
  )

(defn add-user
  "Creates a new user profile in the API.

   Parameters:
   - name: User's name
   - age: User's age
   - height: User's height
   - weight: User's weight

   Returns a parsed JSON response on success or an error message on failure."
  [name age height weight]
  (let [
        response (http-client/post (str api-url "/user")
                                  {:query-params {:name name
                                                 :age age
                                                 :height height
                                                 :weight weight
                                                 }
                                   }
                                  )
        code (:status response)
        ]
    (if  (or (= code 201) (= code 200))
      (json/parse-string (:body response))
      (str "Error: " code " " (:body response))
      )
  )
  )

(defn get-user
  "Retrieves the current user's profile information from the API.

   Returns a parsed JSON response on success or an error message on failure."
  []
  (let [
        response (http-client/get (str api-url "/user"))
        code (:status response)
        ]
    (if (= code 200)
      (json/parse-string (:body response))
      (str "Error: " code " " (:body response))
      )
  )
  )

(defn get-balance
  "Retrieves caloric balance information for a specified date range.

   Parameters:
   - start-date: Beginning date for the balance calculation
   - end-date: Ending date for the balance calculation

   Returns a parsed JSON response on success or an error message on failure."
  [start-date end-date]
  (let [
        response (http-client/get (str api-url "/balance")
                                  {:query-params {:start start-date
                                                 :end end-date
                                                 }
                                   }
                                  )
        code (:status response)
        ]
    (if (= code 200)
      (json/parse-string (:body response))
      (str "Error: " code " " (:body response))
      )
  )
  )

(defn get-history
  "Retrieves user's food and exercise history for a specified date range.

   Parameters:
   - start-date: Beginning date for the history query
   - end-date: Ending date for the history query

   Returns a parsed JSON response on success or an error message on failure."
  [start-date end-date]
  (let [
        response (http-client/get (str api-url "/history")
                                  {:query-params {:start start-date
                                                 :end end-date
                                                 }
                                   }
                                  )
        code (:status response)
        ]
    (if (= code 200)
      (json/parse-string (:body response))
      (str "Error: " code " " (:body response))
      )
  )
  )