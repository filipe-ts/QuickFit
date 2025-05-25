(ns quickfit.oauth
  (:import [java.net URLEncoder])
  (:import [java.net URLDecoder])
  (:import (javax.crypto Mac)
           (javax.crypto.spec SecretKeySpec)
           (java.util Base64)
           (java.util UUID))
  (:require
            [dotenv :refer [env]]
            [buddy.core.mac :as mac]
            [buddy.core.codecs :as codecs]
            [buddy.core.hash :as hash]
            [clj-http.client :as http-client]
            [cheshire.core :refer :all]
            )
  )

(def consumer-key (env :CONSUMER_KEY))
(def consumer-secret (env :CONSUMER_SECRET))
(def fatsecret-auth-url (env :FATSECRET_URL))
(def fatsecret-search-url (env :FATSECRET_SEARCH_URL))
(def fatsecret-byid-url (env :FATSECRET_BYID_URL))

(defn encode-url
  "Applies url encoding to a string."
  [url]
  (let [encoded-url
        ;(url-encode url)
        (URLEncoder/encode url "UTF-8")
        ] ;; Encode the URL
    (clojure.string/replace encoded-url "+" "%20")))

(defn generate-nonce
  "Generate a unique nonce value."
  []
  (str (UUID/randomUUID))
  ;"xb7patrXmum&"
  )

(defn generate-timestamp
  "Generate the current Unix timestamp in seconds."
  []
  (quot (System/currentTimeMillis) 1000)
  ;"1748144854"
  )

(defn create-param-string
  "Create a normalized parameter string from the parameters."
  [params]
  (let [param-list (map (fn [[k v]] [(encode-url k) (encode-url v)]) params) ; transforms from dict to string
        param-list-sorted (sort-by (juxt first second) param-list) ; sorts by key and value
        param-list-strs (map (fn [[k v]] (str k "=" v)) param-list-sorted) ; make a str list lik kay=value
        param-list-string (clojure.string/join "&" param-list-strs) ; join all elements by &
        ]
    param-list-string
    )
  )

(defn build-base-string
  "Construct the OAuth signature base string."
  [method url params]
  (let [param-string (encode-url (create-param-string params))
        url-encoded (encode-url url)
        method-encoded (clojure.string/upper-case method)
        ]
    (str method-encoded "&" url-encoded "&" param-string)
    )
  )

(defn add-mandatory-params
  "Adds mandatory param like 'oauth_nonce' and 'oauth_timestamp'"
  [dict-]
  (let [
        mandatory {"oauth_consumer_key" consumer-key
                   "oauth_nonce" (generate-nonce)
                   "oauth_timestamp" (str (generate-timestamp))
                   "oauth_version" "1.0"
                   "oauth_signature_method" "HMAC-SHA1"
                   "format" "json"
                   }
        ]
    (merge dict- mandatory)
    )

  )

(defn hmac-sha1 [message]
  (let [
        mac-bytes (mac/hash message {:key (str consumer-secret "&") :alg :hmac+sha1})
        mac-string (codecs/bytes->b64 mac-bytes)
        ]
    (codecs/bytes->str mac-string)
    )
  )

(defn -main []
  (let [
        url fatsecret-search-url
        user-params {"search_expression" "Apple Juice"}
        request-params (add-mandatory-params user-params)
        base-string (build-base-string "GET" url request-params)
        oauth_signature_dict {"oauth_signature"
                              ;"H%2Bmg4Gl2BwkTaQX71yIZZJSlp1E%3D"
                              (hmac-sha1 base-string)
                              }
        final-params (merge request-params oauth_signature_dict)
        response (http-client/get url {:query-params final-params
                                       })
        response-body (parse-string (:body response))
        ]
    (println response-body)
    )
  )