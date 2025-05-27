(ns quickfit.service-nutritionix-api
  (:require
    [quickfit.service-oauth :refer [get-request-params]]
    [dotenv :refer [env]]
    [clj-http.client :as http-client]
    [cheshire.core :refer :all]
    )
  )

(def nutritionix-id (env :NUTRITIONIX_ID))
(def nutritionix-key (env :NUTRITIONIX_KEY))
(def nutritionix-url (env :NUTRITIONIX_URL))

(defn get-exercise-data [query]
  "Uses Nutritinix Exercise Api to process an natural language query, and return the amount of calories consumed
  during the exercise, the time duration (if not giving by the user) is also returned)
  "
  (let [

        ]

    )
  )

(defn -main []
  (let [
        response (http-client/post nutritionix-url {:headers {"x-app-key" nutritionix-key
                                                              "x-app-id" nutritionix-id
                                                              "x-remote-user-id" 0
                                                              }
                                                    :body (generate-string {"query" "running 10min"})
                                                    :content-type :json
                                                    }

                                   )
        response-body (parse-string (:body response))
        exercise (first (get response-body "exercises"))
        name (get exercise "name")
        calories (get exercise "nf_calories")
        duration (get exercise "duration_min")
        result {:name name :calories calories :duration duration}
        ]

    (println result)
    )
  )
