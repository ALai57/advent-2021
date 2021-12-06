(ns advent-2021.problem5
  (:require [clojure.string :as string]
            [clojure.set :as set]
            [clojure.java.io :as io]
            [clojure.test :refer :all]
            [advent-2021.core :as aoc]))

(def inputs
  (slurp (io/resource "aoc-5.txt")))

(defn parse-inputs
  [s]
  (let [flat-xs (map #(Integer/parseInt %)
                  (string/split s #" -> |,|\n"))]
    (->> flat-xs
      (partition 4)
      (map (fn [[x1 y1 x2 y2]]
             [{:x x1 :y y1} {:x x2 :y y2}])))))

(def lines
  (parse-inputs inputs))


;; Helpers
(defn abs
  [x]
  (max x (- x)))

(defn dx
  [p1 p2]
  (- (:x p2) (:x p1)))

(defn dy
  [p1 p2]
  (- (:y p2) (:y p1)))

(defn horizontal?
  [line]
  (zero? (apply dy line)))

(defn vertical?
  [line]
  (zero? (apply dx line)))

(defn unit-vector
  [line]
  (let [rise (apply dy line)
        run  (apply dx line)
        dy'  (/ rise (max (abs rise) (abs run)))
        dx'  (/ run (max (abs rise) (abs run)))]
    {:x dx' :y dy'}))

(defn points-on-line
  [[start end :as line]]
  (let [step (unit-vector line)]
    (loop [[last-point :as points] (list start)]
      (if (= end last-point)
        points
        (recur (conj points (merge-with + last-point step)))))))

(def result
  (->> lines
    (filter (fn [line]
              (or (horizontal? line)
                (vertical? line))))
    (mapcat points-on-line)
    (frequencies)
    (filter (fn [[_ v]] (< 1 v)))
    (count)))

(def result-2
  (->> lines
    (mapcat points-on-line)
    (frequencies)
    (filter (fn [[_ v]] (< 1 v)))
    (count)))
