(ns solution.core
  (:require [clojure.math.combinatorics :refer [permutations]]
            [clojure.string :as str])
  (:gen-class))

(defn hapiness [dir x] 
  (if (= dir "lose") 
    (- x)
    x))

(defn update-table [table] 
  (let [k (keys table) 
        t (reduce #(assoc-in %1 [%2 "me"] 0) table k)] 
    (assoc t "me" (reduce #(assoc %1 %2 0) {} k))))

(defn parse [line table] 
  (let [temp (str/join "" (drop-last line))
        [s _ dir num _ _ _ _ _ _ d] (str/split temp #" ")
        amount (Integer. num)] 
    (assoc-in table [s d] (hapiness dir amount))))

(defn get-table [path] 
  (with-open [rdr (clojure.java.io/reader path)] 
    (reduce #(parse %2 %1) {} (line-seq rdr))))

(defn sum-hapiness [config table]  
  (let [part (mapcat #(partition 2 1 % %) [config (reverse config)])]
        (reduce + (map #(get-in table %) part))))

(defn solve [path] 
  (let [table (update-table (get-table path))
        names (keys table)] 
    (->> 
      (permutations names)
      (map #(sum-hapiness % table))
      (apply max))))

(defn -main
  [& args]
    (time (println (solve "resources/input.txt"))))
