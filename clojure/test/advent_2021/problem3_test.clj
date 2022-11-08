(ns advent-2021.problem3-test
  (:require [advent-2021.problem3 :as sut]
            [clojure.test :refer :all]))

(def example
  (map sut/->int-array ["00100"
                        "11110"
                        "10110"
                        "10111"
                        "10101"
                        "01111"
                        "00111"
                        "11100"
                        "10000"
                        "11001"
                        "00010"
                        "01010"]))

(deftest numeric-array->int-test
  (is (= 1503 (sut/numeric-array->int [0 1 0 1 1 1 0 1 1 1 1 1]))))

(deftest examples-test
  (is (= [1 0 1 1 1] (sut/iterative-filter sut/most-common-bits example)))
  (is (= [0 1 0 1 0] (sut/iterative-filter sut/least-common-bits example)))
  (is (= 230
        (sut/score [(partial sut/iterative-filter sut/most-common-bits)
                    (partial sut/iterative-filter sut/least-common-bits)]
          example))))
