(ns quickfit.service-nutritionix-api
  (:require
    [dotenv :refer [env]]
    [clj-http.client :as http-client]
    [cheshire.core :refer :all]
    )
  )

(def nutritionix-id (env :NUTRITIONIX_ID))
(def nutritionix-key (env :NUTRITIONIX_KEY))
(def nutritionix-url (env :NUTRITIONIX_URL))

(defn get-exercise-data
  "Uses Nutritinix Exercise Api to process a natural language query, and return the full details of the exercised.
  :param query: An string that says the exercise and duration/distance, e.g. \"running 5km\" or \"jogging 40min\"
  :return: the body of http response as a map.
  "
  [query]
  (let [
        response (http-client/post nutritionix-url {:headers {"x-app-key" nutritionix-key
                                                              "x-app-id" nutritionix-id
                                                              "x-remote-user-id" 0
                                                              }
                                                    :body (generate-string {"query" query})
                                                    :content-type :json
                                                    }

                                   )
        ]
        (parse-string (:body response))
    )
  )

(defn process-exercise-data
  "Used for extracting the name, calories and duration of a Nutritionix Exercise API response.
  :param exercise-data: the response of (get-exercise-data query), which is the body of a http response.
  :return: If the response has valid values, the return is a map {:name , :calories, :duration}, otherwise it is nil.
  :rtype: nil | map
  "
  [exercise-data]
  (let [
        exercise (first (get exercise-data "exercises"))
        name (get exercise "name")
        calories (get exercise "nf_calories")
        duration (get exercise "duration_min")
        ]
    (if (nil? calories)
      nil
      {:name name :calories calories :duration duration}
      )
    )
  )

(defn search
  "
  Uses Nutritinix Exercise Api to process a natural language query, and return the details of the exercise.
  This function is sort for (process-exercise-data (get-exercise-data query)).
  :param query: An string that says the exercise and duration/distance, e.g. \"running 5km\" or \"jogging 40min\"
  :return: If the response has valid values, the return is a map {:name , :calories, :duration}, otherwise it is nil.\n
  :rtype: nil | map
  "
  [query]
  (let [
        exercise-data (get-exercise-data query)
        result (process-exercise-data exercise-data)
        ]
    result
    )
  )

(defn -main []
  (let [
        ]
    (println (search "swimming 1hour"))
    )
  )
