(ns everine.elements
  (:require [clojure.string :as string]
            [everine.utils :as utils]))

(defn text-input [value class on-change]
  [:input {:type :text
           :value value
           :on-change #(on-change (utils/event-target-value %))
           :class class} ])
(def default-button-class "mdl-button mdl-js-button mdl-button--raised mdl-button--colored")


(defn button-class
  "Generates string for `class=` attribute"
  [& additionals]
  (str default-button-class " " (string/join " " additionals)))


(defn submit-button [value class]
  [:input {:type :submit
           :value value
           :class (button-class class)}])
