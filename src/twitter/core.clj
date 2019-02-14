(ns twitter.core
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn bearer-token [api-key secret-key]
  (http/post "https://api.twitter.com/oauth2/token"
             {:basic-auth [api-key secret-key]
              :form-params {"grant_type" "client_credentials"}}))

