(ns solution.core
  (:require [digest])
  (:gen-class))

(defn solution [input] 
  (loop [val 100000] 
    (let [hash (digest/md5 (str input val))
          substring (subs hash 0 6)] 
      (if (= substring "000000") 
        val
        (recur (inc val))))))

(defn -main
  [& args]
  (println (solution "iwrupvqb"))) 
