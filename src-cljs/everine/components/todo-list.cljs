(ns everine.components.todo-list
  (:use [everine.components.todo-item :only [todo-item]])
  (:require [rum.core :as rum]))

(defn add-item [items]
  (conj items {:label "new-item"}))

(defn update-items-by-id [id f item-coll]
  (map #(if (= (:id %) id) (f %) %) item-coll))

(defn atom-toggle-items-by-id [id items-atom]
  (swap! items-atom
         (fn [coll]
           (update-items-by-id id
                               #(merge % {:done (not (:done %))})
                               coll))))

(defn render-items [items-atom]
  (map (fn [item]
         (-> item
             (todo-item {:on-click #(atom-toggle-items-by-id (:id item) items-atom)})
             (rum/with-key (:id item))))
       @items-atom))

(rum/defc todo-list [items-atom]
  [:div.todo-list (render-items items-atom)])
