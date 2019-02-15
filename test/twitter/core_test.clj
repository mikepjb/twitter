(ns twitter.core-test
  (:require [twitter.core :as twitter]
            [clojure.test :refer [deftest testing is]]))

(deftest timeline
  (testing "the last tweet id is returned to make a new request"
    (is (= 1 1))))
