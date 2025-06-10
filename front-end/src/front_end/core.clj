(ns front-end.core
  (:gen-class
    )
  (:require [front-end.menu :as menu])
  )

(defn -main
  [& args]
  (menu/-main)
)