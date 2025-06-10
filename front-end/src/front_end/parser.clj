(ns front-end.parser
  (:require [front-end.api :as api]

            )
  )

(defn parse-food
  ""
  []
  (let [
        _ (println "Enter: \"<Food name>\" <line break> <quantity> <line break> <unit> <line break> \"<date>\":")
        food_name (read)
        quantity (read)
        unit (read)
        time (read)
        ]
    (list food_name quantity unit time)
    )

  )

(defn parse-exercise
  ""
  []
  (let [
        _ (println "Enter: \"<Exercise name> <duration or length>\" <line break> \"<date>\"")
        query (read)
        time (read)
        ]
    (list query time)
    )

  )

(defn parse-user
  ""
  []
  (let [
        _ (println "Enter: \"<name>\" <line break> <age> <line break> <height> <line break> <weight>")
        name (read)
        age (read)
        height (read)
        weight (read)
        ]
    (list name age height weight)
    )

  )

(defn -main []
  (println (parse-food))
  )