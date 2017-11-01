(ns everine.components.todo-item
  (:use [clojure.string :only [join]])
  (:require [rum.core :as rum]))

(defn- item-text-classes [item]
  (filter identity ["todo-item__text"
                    (and (:done item) "todo-item__text--done")]))

(rum/defc todo-item [item callbacks]
  (let [on-click (:on-click callbacks)
        on-delete (:on-delete callbacks)
        label (:label item)
        text-classes (item-text-classes item)]
    [:div.todo-item
     [:a.todo-item__delete {:on-click on-delete} "[X]"]
     [:span.todo-item__text {:on-click on-click :class text-classes} label]]))
