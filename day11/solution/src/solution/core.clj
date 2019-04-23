(ns solution.core
  (:require [clojure.string :as str])
  (:gen-class))

(defn cons-pattern [] 
  (->> 
    (map char (range 97 123))
    (partition 3 1)
    (map #(apply str))
    (str/join "|")
    (re-pattern)))

(defn )

(defn -main
  [& args]
  (println "Hello, World!"))
