(ns solution.core
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn get-containers [path] 
  (with-open [rdr (io/reader path)] 
    (doall (map #(Integer. %) (line-seq rdr)))))

;; part 1
(def comb 
  (memoize 
    (fn [amount containers] 
      (cond 
        (= amount 0) 1
        (or (< amount 0) (empty? containers)) 0
        :else 
          (+ (comb amount (rest containers))
             (comb (- amount (first containers)) (rest containers)))))))



(defn -main
  [& args]
  (let [cont (get-containers "resources/input.txt")] 
    (time (println (comb 150 cont)))))
