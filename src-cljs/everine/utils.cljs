(ns everine.utils)

(defn update-by
  "Returns xs with f applied to elements with key = name"
  [key value f xs]
  (map #(if (= value (key %)) (f %) %) xs))
