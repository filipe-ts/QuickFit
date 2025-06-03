(ns quickfit.service-fatsecret-api
  (:require
    [quickfit.service-oauth :refer [get-request-params]]
    [dotenv :refer [env]]
    [clj-http.client :as http-client]
    [cheshire.core :refer :all]
    )
  )

(def fatsecret-search-url (env :FATSECRET_SEARCH_URL))
(def fatsecret-byid-url (env :FATSECRET_BYID_URL))


(defn get-food-id
  "Gets the \"food_id\" for the best match for a giving \"search_term\"
   :param search-term: a string with the food or drink to get the information.
   "
  [search-term]
  (let [
        user-params {"search_expression" search-term}
        url fatsecret-search-url
        request-params (get-request-params "GET" url user-params)
        response (http-client/get url {:query-params request-params})
        response-body (parse-string (:body response))
        foods (get-in response-body ["foods" "food"])
        best-match (first foods)
        food_id (get best-match "food_id")
        ]
    food_id
    )
  )

(defn get-food-info
  "Gets the full food info of a food id with 100 g portion or 100 ml portion
  :param food-id: the food id in the fatsecret api
  :param unit: the portion unit: \"g\" or \"ml\"
  :return: a map :food_name, :serving (which has detailed food info)
  "
  [food-id unit]
  (let [
        user-params {"food_id" food-id}
        url fatsecret-byid-url
        request-params (get-request-params "GET" url user-params)
        response (http-client/get url {:query-params request-params})
        response-body (parse-string (:body response))
        food-name (get-in response-body ["food" "food_name"])
        servings (get-in response-body ["food" "servings" "serving"])
        serving (first (filter (fn [element] (= (str "100 " unit) (get element "serving_description"))) servings))
        ]
    {:food_name food-name
     :serving serving
     }
    )
  )

(defn extract-food
  "Return the food calories, the quantity is always 100 units, 100 g or 100 ml
  :param serving-map: the map returned by get-food-info
  :return: a map of :food_name, :calories, :unit.
  "
  [serving-map]
  (let [
        food-name (:food_name serving-map)
        serving (:serving serving-map)
        calories (get serving "calories")
        unit (get serving "measurement_description")
        ]
    {:food_name food-name
     :calories (Integer/parseInt calories)
     :unit unit
     }
    )
  )

(defn search
  "Gets the best match for a search, needs to specify if using \"g\" or \"ml\".
  Always responde with the 100g or 100ml portion.

  :param search-term: a string with the food or drink to get the information.
  :param unit: a string being mandatory \"g\" or \"ml\", if missing \"g\" is th assumed unit.
  "
  ([search-term unit]
   (let [
         food-id (get-food-id search-term)
         food-info (get-food-info food-id unit)
         food-map (extract-food food-info)
         ]
      food-map
     )
   )
  ([search-term]
   (search search-term "g")
   )
  )

(defn -main []
  (let [
        food (search "Egg" "g")
        ]
    (println food) ;5589
    )
  )