(ns front-end.menu-comands
  (:require
            [front-end.parser :as parser]
            [front-end.register :as register]
            [front-end.api :as api]
            )
  )

(defn command-user []
  (println "Please, enter your user information.")
  (let [
        info (parser/parse-user)
        ]
    (apply register/register-user info)
    )
  )

(defn get-user []
  (println "This is your info:")
  (api/get-user)
  )

(defn command-add-food []
  (println "Please, enter your food information.")
  (let [
        info (parser/parse-food)
        ]
    (apply register/register-food info)
    )
  )

(defn command-add-exercise []
  (println "Please, enter your exercise information.")
  (let [
        info (parser/parse-exercise)
        ]
    (apply register/register-exercise info)
    )
  )

(defn command-get-history []
  (println "Please, enter the start and end dates for your history query.")
  (let [
        info (parser/parse-period)
        ]
    (apply register/get-history info)
    )
  )

(defn command-get-balance []
  (println "Please, enter the start and end dates for your balance query.")
  (let [
        info (parser/parse-period)
        ]
    (apply register/get-balance info)
    )
  )
