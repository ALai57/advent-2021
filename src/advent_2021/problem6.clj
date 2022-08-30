(ns advent-2021.problem6
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

(def example-input
  (frequencies [3 4 3 1 2]))

example-input
;; => {3 2, 4 1, 1 1, 2 1}

(defn step
  [state]
  (reduce-kv (fn [acc k v]
               (if (zero? k)
                 (merge-with +' acc {6 v, 8 v})
                 (merge-with +' acc {(dec k) v})))
    {}
    state))

(defn count-fish
  [state]
  (reduce +' (vals state)))

(count-fish (nth (iterate step example-input) 2))
(count-fish (nth (iterate step example-input) 18))
(count-fish (nth (iterate step example-input) 80))
(count-fish (nth (iterate step example-input) 256))

;; Each day, a 0 becomes a 6 and adds a new 8 to the end of the list, while each
;; other number decreases by 1 if it was present at the start of the day.
;;
;; In this example, after 18 days, there are a total of 26 fish. After 80 days,
;; there would be a total of 5934.
;;
;; Find a way to simulate lanternfish. How many lanternfish would there be after 80
;; days?

(def raw-input
  (string/trim (slurp (io/resource "aoc-6-1.txt"))))

(def input
  (->> (string/split raw-input #",")
    (map #(Integer/parseInt %))
    frequencies))

(count-fish (nth (iterate step input) 80));; => 366057
(count-fish (nth (iterate step input) 256));; => 1653559299811
