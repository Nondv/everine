(ns everine.components.todo-list
  (:use [everine.components.todo-item :only [todo-item]])
  (:require [rum.core :as rum]
            [everine.components.new-todo-item-form :refer [new-todo-item-form]]
            [everine.utils :as utils]))

(defn add-item [item item-coll]
  (let [max-id (apply max (map :id item-coll))]
    (conj item-coll (assoc item :id (if max-id (inc max-id) 1)))))

(def update-by-id (partial utils/update-by :id))

(defn toggle-item [item]
  (assoc item :done (not (:done item))))

(defn toggle-items-by-id
  "Returns vector of items with toggled :done key (true/false) at elements with :id = `id`"
  [id items]
  (vec (update-by-id id toggle-item items)))

(defn remove-items-by [key val items]
  (vec (filter #(not= val (key %)) items)))

(defn render-items [items dispatch]
  (map (fn [item]
         (let [id (:id item)
               on-click #(dispatch (toggle-items-by-id id items))
               on-delete #(dispatch (remove-items-by :id id items))]
           (-> item
               (todo-item {:on-click on-click :on-delete on-delete})
               (rum/with-key (:id item)))))
       items))


(rum/defc todo-list
  [items dispatch]
  [:div.todo-list
   (render-items items dispatch)
   (new-todo-item-form #(dispatch (add-item % items)))])
