(ns advent-2021.problem5-test
  (:require [advent-2021.problem5 :as sut]
            [clojure.test :refer :all]))

(def example-lines
  (sut/parse-inputs "0,9 -> 5,9
8,0 -> 0,8
9,4 -> 3,4
2,2 -> 2,1
7,0 -> 7,4
6,4 -> 2,0
0,9 -> 2,9
3,4 -> 1,4
0,0 -> 8,8
5,5 -> 8,2"))

(deftest example-result-test
  (is (= 5 (->> example-lines
             (filter (fn [line]
                       (or (sut/horizontal? line)
                         (sut/vertical? line))))
             (mapcat sut/points-on-line)
             (frequencies)
             (filter (fn [[_ v]] (< 1 v)))
             (count)))))
