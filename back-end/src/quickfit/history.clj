(ns quickfit.history
  (:require [quickfit.service-fatsecret-api :as fatsecret]
            [quickfit.service-nutritionix-api :as nutrix]
            )
  )

(def history-atom (atom ()))
(def history-size (atom 0))
(def user-atom (atom {:name "" :age 0 :weight 0 :height 0 :gender "male"}))

(defn has-required-keys? [m required-keys]
  (every? #(contains? m %) required-keys))

(defn food?
  "Checks if and given map qualifies as food map.
  It should have the fields :food_name, :calories and :unit.

  :param given-map: A map to validated.
  :return: true or false
  :rtype: boolean
  "
  [given-map]
  (has-required-keys? given-map [:food_name :calories :unit])
  )


(defn exercise?
  "Checks if and given map qualifies as exercise map.
  It should have the fields :name, :calories and :duration.

  :param given-map: A map to validated.
  :return: true or false
  :rtype: boolean
  "
  [given-map]
  (has-required-keys? given-map [:name :calories :duration])
  )

(defn create-transaction
  "Creates a transaction to be stored in the transaction atom.

  :param given-map: a food or exercise-map.
  :param time: Timestamp in YYYY-MM-DD string format.
  :param atom: atom in which the transaction will be stored.
  :return: true if stored successfully, false otherwise.
  :rtype: boolean
  "
  [given-map time- atom-history atom-length]
  (let [
        factor (cond (food? given-map) 1 (exercise? given-map) -1 :else 0)
        should-write (or (= 1 factor) (= -1 factor))
        ]
    (swap! atom-history conj {:element given-map :value (* factor (:calories given-map)) :time time-})
    (if should-write
      (swap! atom-length inc)
      )
      should-write
    )
  )

(defn get-time-period
  "
  Gets the list of entries for a giving time interval inside a history-atom.

  :param start: Start date as string in YYYY-MM-DD
  :param end: End date as string in YYYY-MM-DD
  :param atom-: The history atom to be modified
  :return: A list containing the transaction object inside the time window given.
  :rtype: list
  "
  [start end atom-]
  (let [
        sorted-atom-list (sort-by :time @atom-)
        starts-in-interval? #(>= (compare % start) 0)
        ends-in-interval? #(<= (compare % end) 0)
        in-interval? #(and (starts-in-interval? %) (ends-in-interval? %))
        get-interval-and-compare #(in-interval? (:time %))
        interval (doall (filter get-interval-and-compare sorted-atom-list))
        ]
    interval
    )
  )

(defn update-user
  "Updates the user information.

  :param user-map: A map containing the user information {:name :age :weight :height}.
  :return: true if stored successfully, false otherwise.
  :rtype: boolean
  "
  [user-map]
  (reset! user-atom user-map)
  )

