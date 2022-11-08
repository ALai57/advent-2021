(ns advent-2021.problem3
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [advent-2021.core :as aoc]))

(defn ->int-array
  [s]
  (vec (map #(Character/digit % 10) s)))

(defn input-string->clj
  [s]
  (->> s
    string/split-lines ;; "1000\n0001"    -> ["1000" "0001"]
    (map ->int-array)  ;; ["1000" "0001"] -> [[1 0 0 0] [0 0 0 1]]
    ))

;; Prepare input data
(def input-bit-arrays
  (->> "aoc-3.txt"
    io/resource
    slurp
    input-string->clj))

;; Helpers
(defn numeric-array->int
  "[1 0 1] -> 5"
  [numeric-array]
  (Integer/parseInt (apply str numeric-array) 2))

;; Problem 1
(defn most-common-bit
  [bits]
  (let [half (/ (count bits) 2)]
    (if (<= half (reduce + bits))
      1
      0)))

(def least-common-bit
  (comp {0 1, 1 0}
    most-common-bit))

(defn most-common-bits
  [bit-arrays]
  (mapv most-common-bit (aoc/T bit-arrays)))

(defn least-common-bits
  [bit-arrays]
  (mapv least-common-bit (aoc/T bit-arrays)))

(defn score
  [fs inputs]
  (->> inputs
    ((apply juxt fs))
    (map numeric-array->int)
    (apply *)))

(score [most-common-bits least-common-bits] input-bit-arrays);; => 3895776

;; Problem 2
(defn bits-match-at-position?
  [array-1 i array-2]
  (= (get array-1 i) (get array-2 i)))

(defn iterative-filter
  [mask-fn initial-bit-arrays]
  (loop [i          0
         bit-arrays initial-bit-arrays]
    (let [bit-mask (mask-fn bit-arrays)
          matches  (filter (partial bits-match-at-position? bit-mask i) bit-arrays)]
      (if (<= (count matches) 1)
        (first matches)
        (recur (inc i) matches)))))

(score [(partial iterative-filter most-common-bits)
        (partial iterative-filter least-common-bits)]
  input-bit-arrays);; => 7928162
