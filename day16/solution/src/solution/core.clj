(ns solution.core
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn parse-line [line] 
  (let [[_ sue-num params] (re-find #"Sue (\d+): (.+)" line)] 
    (->> 
      params 
      (re-seq #"(\w+): (\d+)")
      (reduce #(assoc %1 (keyword (get %2 1)) (Integer. (get %2 2)))
              {:number (Integer. sue-num)}))))

(defn read-file [path] 
  (with-open [rdr (io/reader path)] 
    (doall (map parse-line (line-seq rdr)))))

(def correct 
  {:children 3 :cats 7 :samoyeds 2 :pomeranians 3 :akitas 0 
   :vizslas 0 :goldfish 5 :trees 3 :cars 2 :perfumes 1})

(defn eq-compound? [[k v]] 
  (= v (get correct k)))

(defn similar-aunt? [pred? aunt] 
  (->> 
    (dissoc aunt :number)
    (every? pred?)))

(defn cnd-pred [k] 
  (condp #(contains? %1 %2) k 
    #{:cats :trees} >
    #{:pomeranians :goldfish} <
    =))

(defn mod-compound? [[k v]] 
  ((cnd-pred k) v (get correct k)))

;; for part 1, change mod-compound? to eq-compound?
(defn solve [aunts] 
  (->> 
    (some #(when (similar-aunt? mod-compound? %) %) aunts)
    :number))

(defn -main
  [& args]
  (let [aunts (read-file "resources/input.txt")] 
    (println (solve aunts))))
