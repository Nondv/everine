(ns everine.core
  (:use [everine.components.todo-list :only [todo-list]])
  (:require [rum.core :as rum]
            [everine.components.todo-list-select :refer [todo-list-select]]
            [everine.utils :as utils]))

(enable-console-print!)

(println "Hello World!")
(println "This is ClojureScript!")

(def items [{:id 1 :label "item1"} {:id 2 :label "item2"} {:id 3 :label "item3"}])
(def lists [{:name "test-list"  :items items}
            {:name "empty-list" :items []}])

(def update-by-name (partial utils/update-by :name))
(defn replace-by-name [name data maps]
  (update-by-name name (fn [_] data) maps))
(def find-by-name (partial utils/find-by :name))

(defn replace-items [data list] (assoc list :items data))

(rum/defcs app <
  (rum/local lists ::lists)
  (rum/local "test-list" ::current-list-name)
  [state]

  (let [lists (::lists state)
        current-list-name (::current-list-name state)
        current-list (find-by-name @current-list-name @lists)
        replace-current-list-items #(replace-by-name @current-list-name (replace-items % current-list) @lists)
        dispatch #(reset! lists (replace-current-list-items %))
        change-current-name! #(reset! current-list-name %)]
    [:div.app
     (todo-list-select (map :name @lists) @current-list-name change-current-name!)
     (todo-list (:items current-list) dispatch)]))

(rum/mount (app) (js/document.getElementById "app" ))
