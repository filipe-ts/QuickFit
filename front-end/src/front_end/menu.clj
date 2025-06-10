(ns front-end.menu
  (:require
           [front-end.menu-comands :as commands]
           )
  )

(defn hi []
  (println "Hi! This is QuickFit 0.0.1--alpha.\n\n")
  )

(defn goodbye []
  (println "Bye!")
  (System/exit 0)
  )

(defn main-menu []
  (println "Please, select an option:")
  (println "1. Register user")
  (println "2. Add food")
  (println "3. Add exercise")
  (println "4. Get history")
  (println "5. Get balance")
  (println "6. Exit")
  (let [
        option (read)
        ]
    (case option
      1 (commands/command-user)
      2 (commands/command-add-food)
      3 (commands/command-add-exercise)
      4 (commands/command-get-history)
      5 (commands/command-get-balance)
      6 (goodbye)
      )
    (println "press enter to continue...")
    (read-line)
    (read-line)
    (recur)
    )
  )

(defn -main []
  (hi)
  (commands/command-user)
  (main-menu)
  )