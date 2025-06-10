(ns quickfit.calculations
  (:require [quickfit.history :refer [history-atom history-size]]
            [quickfit.history :as his]
            )
  )

(defn cal-balance
  "
  Calculates the calories balance for a given list of transaction maps.

  :param transaction-list: list of transactions maps.
  :return: calories balance
  :rtype: double
  "
  [transaction-list]
  (if (empty? transaction-list)
    0
    (let [
          values (doall (map :value transaction-list))
          ]
      (reduce + values)
      )
    )
  )
