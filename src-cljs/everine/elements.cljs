(ns everine.elements
  (:require [clojure.string :as string]
            [everine.utils :as utils]))

(def default-button-class "mdl-button mdl-js-button mdl-button--raised mdl-button--colored")


(defn button-class
  "Generates string for `class=` attribute"
  [& additionals]
  (str default-button-class " " (string/join " " additionals)))

(defn text-input [label value class on-change]
  [:div.mdl-textfield.mdl-js-textfield
   [:label.mdl-textfield__label label]
   [:input {:type :text
            :value value
            :on-change #(on-change (utils/event-target-value %))
            :class (str "mdl-textfield__input " class)}]])

(defn submit-button [value class]
  [:input {:type :submit
           :value value
           :class (button-class class)}])
