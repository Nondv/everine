(ns everine.utils)

(defn update-by
  "Returns xs with f applied to elements with key = name"
  [key value f xs]
  (map #(if (= value (key %)) (f %) %) xs))

(defn find-by
  "Returns first map of `maps` with `key` = `value`"
  [key value maps]
  (first (filter #(= value (key %)) maps)))

(defn log [data] (js/console.log (str data)))

(defn event-target-value [e]
  (-> e
      .-target
      .-value))
