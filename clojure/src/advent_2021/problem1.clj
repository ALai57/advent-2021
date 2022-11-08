(ns advent-2021.problem1
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))


(def inputs
  (slurp (io/resource "aoc-1-1.txt")))

(def depths
  (->> (string/split inputs #"\n")
    (map #(Integer/parseInt %))))

(defn compare-neighbors
  [xs op]
  (map (fn [x1 x2] (op x1 x2)) xs (drop 1 xs)))

(defn n-increasing [xs]
  (->> (compare-neighbors xs <)
    (filter identity)
    count))

(def example
  [199 200 208 210 200
   207 240 269 260 263])

(def smoothed-depths
  (map (fn [args]
         (apply + args))
    (partition 3 1 depths)))

(n-increasing example);; => 7
(n-increasing depths);; => 1390
(n-increasing smoothed-depths);; => 1457
