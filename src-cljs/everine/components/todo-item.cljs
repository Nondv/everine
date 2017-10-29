(ns everine.components.todo-item
  (:use [clojure.string :only [join]])
  (:require [rum.core :as rum]))

(defn item-classes [item]
  (filter identity ["todo-item"
                    (and (:done item) "todo-item--done")]))

(rum/defc todo-item [item callbacks]
  [:div (merge callbacks {:class (join " " (item-classes item))}) (:label item)])
