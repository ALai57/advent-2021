(ns advent-2021.core
  (:require [clojure.string :as string]))

(defn split-on-whitespace [s]
  (string/split s #"\s+"))

(defn T
  "Transpose"
  [xs]
  (apply map vector xs))
