(ns everine.components.todo-list-select
  (:require [rum.core :as rum]
            [everine.utils :as utils]))

(defn- single-option [name]
  [:option.todo-list-select__item {:key name :value name} name])

(defn- options [names current on-change]
  (map single-option names))

(rum/defc todo-list-select [names current on-change]
  (into [:select {:value current
                  :on-change #(on-change (utils/event-target-value %))}]
        (options names current on-change)))
