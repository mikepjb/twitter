(ns twitter.core
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn- token-request [api-key secret-key]
  (http/post "https://api.twitter.com/oauth2/token"
             {:basic-auth [api-key secret-key]
              :form-params {"grant_type" "client_credentials"}}))

(defn bearer-token [api-key secret-key]
  (let [request (token-request api-key secret-key)]
    (if (= 200 (:status request))
      (json/decode (:body request) keyword)
      (println "wrong."))))

(defn user-statuses [uname n btok]
  (http/get "https://api.twitter.com/1.1/statuses/user_timeline.json"
            {:query-params {"screen_name" uname
                            "count" n}
             :oauth-token btok}))

(defn tweets [uname n btok]
  "limit of n is 200 as a default"
  (let [tweets-response (user-statuses uname n btok)]
    (if (= 200 (:status tweets-response))
      (json/decode (:body tweets-response) keyword))))

(def select-relevant-fields
  (map #(select-keys % [:retweet_count :favourite_count :text :created_at])))

;; (def oauth (bearer-token "x" "y"))
;; (def tweet-list (tweets "mikepjb" "200" (:access_token oauth)))
;; (into [] select-relevant-fields tweet-list)
;; (take 10 (reverse (sort-by :retweet_count (into [] select-relevant-fields ;tweet-list))))

