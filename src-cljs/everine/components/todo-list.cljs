(ns everine.components.todo-list
  (:use [everine.components.todo-item :only [todo-item]])
  (:require [rum.core :as rum]))

(defn add-item [item-coll]
  (let [max-id (apply max (map :id item-coll))]
    (conj item-coll {:id (if max-id (inc max-id) 1) :label "new-item"})))

(defn update-items-by-id [id f item-coll]
  (map #(if (= (:id %) id) (f %) %) item-coll))

(defn atom-toggle-items-by-id [id items-atom]
  (swap! items-atom
         (fn [coll]
           (update-items-by-id id
                               #(merge % {:done (not (:done %))})
                               coll))))

(defn atom-add-item [items-atom]
  (swap! items-atom add-item))

(defn render-items [items-atom]
  (map (fn [item]
         (-> item
             (todo-item {:on-click #(atom-toggle-items-by-id (:id item) items-atom)})
             (rum/with-key (:id item))))
       @items-atom))

(rum/defc todo-list [items-atom]
  [:div.todo-list
   (render-items items-atom)
   [:button {:on-click #(atom-add-item items-atom) :class "todo-list__add-item"} "Add item"]])
