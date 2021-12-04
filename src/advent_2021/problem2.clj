(ns advent-2021.problem2
  (:require [clojure.string :as string]
            [clojure.java.io :as io]))

(def inputs
  (slurp (io/resource "aoc-2.txt")))


(defn ->command
  [s]
  (let [[direction n] (string/split s #" ")]
    [direction (Integer/parseInt n)]))

(def commands
  (->> (string/split inputs #"\n")
    (map ->command)))

;; Part 1
(defn interpret-command
  [current-position [command n]]
  (case command
    "forward" (update current-position "horizontal" + n)
    "down"    (update current-position "depth" + n)
    "up"      (update current-position "depth" - n)))

(def command-total
  (reduce interpret-command
    {"depth"      0
     "horizontal" 0}
    commands))

(* (get command-total "horizontal")
  (get command-total "depth"));; => 1694130


;; Part 2
(defn interpret-command-2
  [current-position [command n]]
  (case command
    "forward" (-> current-position
                (update "horizontal" + n)
                (update "depth" + (* (get current-position "aim") n)))
    "down"    (update current-position "aim" + n)
    "up"      (update current-position "aim" - n)))

(def command-total-2
  (reduce interpret-command-2
    {"depth"      0
     "horizontal" 0
     "aim"        0}
    commands))

(* (get command-total-2 "horizontal")
  (get command-total-2 "depth"));; => 1698850445
