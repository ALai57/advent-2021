(ns advent-2021.problem2
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [advent-2021.core :as aoc]))

(defn ->numeric-magnitude [[direction magnitude]]
  [direction (Integer/parseInt magnitude)])

(def commands
  (->> "aoc-2.txt"
    io/resource
    slurp
    string/split-lines            ;; "forward 1\ndown 1"            -> ["forward 1" "down 1"]
    (map aoc/split-on-whitespace) ;; ["forward 1" "down 1"]         -> [["forward" "1"] ["down" "1"]]
    (map ->numeric-magnitude)     ;; [["forward" "1"] ["down" "1"]] -> [["forward" 1] ["down" 1]]
    ))

(defn score
  [{:strs [depth horizontal]}]
  (* horizontal depth))

(->> commands
  (reduce (fn [current-position [direction magnitude]]
            (case direction
              "forward" (update current-position "horizontal" + magnitude)
              "down"    (update current-position "depth" + magnitude)
              "up"      (update current-position "depth" - magnitude)))
    {"depth"      0
     "horizontal" 0})
  score);; => 1694130

;; Part 2
(->> commands
  (reduce (fn [current-position [direction magnitude]]
            (case direction
              "forward" (-> current-position
                          (update "horizontal" + magnitude)
                          (update "depth" + (* (get current-position "aim") magnitude)))
              "down"    (update current-position "aim" + magnitude)
              "up"      (update current-position "aim" - magnitude)))
    {"depth"      0
     "horizontal" 0
     "aim"        0})
  score);; => 1698850445
