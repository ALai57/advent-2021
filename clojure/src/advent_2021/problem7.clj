(ns advent-2021.problem7
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))


(def example-inputs
  [16 1 2 0 4 2 7 1 2 14])

(defn all-positions
  [v]
  (range (apply min v)
         (inc (apply max v))))

(sort (frequencies example-inputs))
;; => ([0 1] [1 2] [2 3] [4 1] [7 1] [14 1] [16 1])

(all-positions example-inputs)

(defn constant-cost
  [target-position [p v]]
  (Math/abs (* v (- p target-position))))

(defn calc-cost
  [cost-fn state target-position]
  (reduce + 0 (map (partial cost-fn target-position)
                   state)))

(def inputs'
  (let [current-state (sort (frequencies example-inputs))]
    (reduce (fn [acc hypothetical-pos]
              (assoc acc hypothetical-pos (calc-cost constant-cost current-state hypothetical-pos))
              )
            {}
            (all-positions example-inputs))))

(defn all-costs
  [inputs cost-fn]
  (let [current-state (sort (frequencies inputs))]
    (reduce (fn [acc hypothetical-pos]
              (assoc acc hypothetical-pos (calc-cost cost-fn current-state hypothetical-pos))
              )
            {}
            (all-positions inputs))))

(def example-costs
  (all-costs example-inputs constant-cost))

(apply min (map second example-costs));; => 37

(def raw-input
  (string/trim (slurp (io/resource "aoc-7-1.txt"))))

(def input
  (->> (string/split raw-input #",")
       (map #(Integer/parseInt %))))

(def problem-1-costs
  (all-costs input constant-cost))

(apply min (map second problem-1-costs));; => 351901

(defn variable-cost
  [target-position [p n]]
  (let [delta (Math/abs (- p target-position))
        c     (reduce + (range (inc delta)))]
    (* n c)))

(def example-variable
  (all-costs example-inputs variable-cost))

(apply min (map second example-variable));; => 168

(def problem-2-costs
  (all-costs input variable-cost))

(apply min (map second problem-2-costs));; => 101079875
()
