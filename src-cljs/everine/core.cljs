(ns everine.core
  (:use [everine.components.todo-list :only [todo-list]])
  (:require [rum.core :as rum]
            [ajax.core :as ajax]
            [everine.components.todo-list-select :refer [todo-list-select]]
            [everine.utils :as utils]))

(enable-console-print!)

(println "Hello World!")
(println "This is ClojureScript!")

(def update-by-name (partial utils/update-by :name))
(defn replace-by-name [name data maps]
  (update-by-name name (fn [_] data) maps))
(def find-by-name (partial utils/find-by :name))

(defn replace-items [data list] (assoc list :items data))

(defn load-lists [state]
  (ajax/GET "/lists.json"
            {:response-format (ajax/json-response-format {:keywords? true})
             :handler (fn [{current :current lists :lists}]
                        (reset! (::lists state) lists)
                        (reset! (::current-list-name state) current))})
  state)


(rum/defcs app <
  (rum/local [] ::lists)
  (rum/local "" ::current-list-name)
  {:did-mount load-lists}
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
