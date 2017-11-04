(ns everine.elements
    (:require [everine.utils :as utils]))

(defn text-input [value class on-change]
  [:input {:type :text
           :value value
           :on-change #(on-change (utils/event-target-value %))
           :class class} ])

(defn submit-button [value class]
  [:input {:type :submit
           :value value
           :class class}])
