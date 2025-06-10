(ns front-end.parser
  )

(defn parse-food
  ""
  []
  (let [
        _ (println "Enter: \"<Food name>\" (e.g. \"Apple\", \"Banana Split\", etc.)")
        food_name (read)
        _ (println "Enter: <quantity> (e.g. 100, 150, etc.")
        quantity (read)
        _ (println "Enter: unit (g or ml)")
        unit (read)
        _ (println "Enter: \"<date>\" (e.g. \"2025-01-01\")")
        time (read)
        ]
    (list food_name quantity unit time)
    )

  )

(defn parse-exercise
  ""
  []
  (let [
        _ (println "Enter: \"<Exercise name> <duration or length>\" (e.g. \"Running 30 minutes\", \"Cycling 10 km\", etc.)")
        query (read)
        _ (println "Enter: \"<date>\" (e.g. \"2025-01-01\")")
        time (read)
        ]
    (list query time)
    )

  )

(defn parse-user
  ""
  []
  (let [
        _ (println "Enter: \"<name>\" ")
        name (read)
        _ (println "Enter: <age> (in years)")
        age (read)
        _ (println "Enter: <height> (in cm)")
        height (read)
        _ (println "Enter: <weight> (in kg)")
        weight (read)
        _ (println "Enter: <gender> (e.g. male or female)")
        gender (read)
        ]
    (list name age height weight gender)
    )

  )

(defn parse-period
  ""
  []
  (let [
        _ (println "Enter: \"<start date>\" (e.g. \"2025-01-01\")")
        start (read)
        _ (println "Enter: \"<end date>\" (e.g. \"2025-01-01\")")
        end (read)
        ]
    (list start end)
    )
  )

(defn -main []
  (println (parse-food))
  )