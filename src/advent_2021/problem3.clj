(ns advent-2021.problem3
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(defn ->int-array
  [s]
  (vec (map #(Character/digit % 10) s)))



;; Prepare input data
(def example
  (map ->int-array ["00100"
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

(def inputs
  (slurp (io/resource "aoc-3.txt")))

(def parsed
  (string/split-lines inputs))

(def input-bit-arrays
  (map ->int-array parsed))




;; Helpers
(defn boolean-array->numeric-array
  [boolean-array]
  (vec (map #(if % 1 0) boolean-array)))

(defn numeric-array->int
  [numeric-array]
  (Integer/parseInt (apply str numeric-array) 2))

(defn boolean-array->int
  [boolean-array]
  (-> boolean-array
    boolean-array->numeric-array
    numeric-array->int))

(assert (= [0 1 0 1 1 1 0 1 1 1 1 1]
          (boolean-array->numeric-array '(false true false true true true false true true true true true))))
(assert (= 1503 (numeric-array->int [0 1 0 1 1 1 0 1 1 1 1 1])))
(assert (= 1503 (boolean-array->int '(false true false true true true false true true true true true))))





;; Problem 1
(defn sum-bits
  [bit-array-1 bit-array-2]
  (map + bit-array-1 bit-array-2))

(defn gamma-rate
  [bit-arrays]
  (let [thresh   (/ (count bit-arrays) 2)
        bit-sums (reduce sum-bits bit-arrays)]
    (map (partial <= thresh) bit-sums)))

(defn epsilon-rate
  [bit-arrays]
  (map not (gamma-rate bit-arrays)))


(gamma-rate input-bit-arrays)   ;; => (false true false true true true false true true true true true)
(epsilon-rate input-bit-arrays) ;; => (true false true false false false true false false false false false)

(boolean-array->int (gamma-rate input-bit-arrays));; => 1503
(boolean-array->int (epsilon-rate input-bit-arrays));; => 2592

(->> [gamma-rate epsilon-rate]
  (map (comp boolean-array->int
         (fn [f] (f input-bit-arrays))))
  (apply *)) ;; => 3895776






;; Problem 2
(defn bits-match-at-position?
  [array-1 i array-2]
  (= (get array-1 i)
    (get array-2 i)))

(defn most-common-bits
  [bit-arrays]
  (boolean-array->numeric-array (gamma-rate bit-arrays)))

(defn least-common-bits
  [bit-arrays]
  (boolean-array->numeric-array (epsilon-rate bit-arrays)))

(defn make-oxygen-generator-filter
  [i current-bit-arrays]
  (partial bits-match-at-position? (most-common-bits current-bit-arrays) i))

(defn make-co2-scrubber-filter
  [i current-bit-arrays]
  (partial bits-match-at-position? (least-common-bits current-bit-arrays) i))

(defn progressive-filter
  [initial-bit-arrays make-filter]
  (loop [i          0
         bit-arrays initial-bit-arrays]
    (let [matches (filter (make-filter i bit-arrays) bit-arrays)]
      (if (<= (count matches) 1)
        (first matches)
        (recur (inc i) matches)))))

(assert (= [1 0 1 1 1] (progressive-filter example make-oxygen-generator-filter)))
(assert (= [0 1 0 1 0] (progressive-filter example make-co2-scrubber-filter)))

(->> [make-oxygen-generator-filter
      make-co2-scrubber-filter]
  (map (comp numeric-array->int
         (partial progressive-filter example)))
  (apply *)) ;; => 230

(->> [make-oxygen-generator-filter
      make-co2-scrubber-filter]
  (map (comp numeric-array->int
         (partial progressive-filter input-bit-arrays)))
  (apply *));; => 7928162
