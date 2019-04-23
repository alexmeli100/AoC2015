(ns solution.core
  (:require [clojure.string :as str])
  (:import (java.io BufferedReader FileReader))
  (:gen-class))

(defn vowels-3? [line] 
  (boolean (re-find #"(.*[aeiou]){3}" line)))

(defn disallowed? [line] 
  (not (re-find #"ab|cd|pq|xy" line)))

(defn twice? [line pattern] 
  (boolean (re-find pattern line)))

(defn nice? [line] 
  (and (vowels-3? line)
       (disallowed? line)
       (twice? line #"(.)\1")))
    
;; part2 
(defn repeats? [line] 
  (boolean (re-find #"(.).\1" line)))

(defn new-nice? [line] 
  (and (twice? line #"(..).*\1")
       (repeats? line)))

(defn solve [path] 
  (with-open [rdr (BufferedReader. (FileReader. path))] 
    (count 
      (filter new-nice? (line-seq rdr)))))

(defn -main
  [& args]
  (time (println (solve "resources/input.txt"))))
