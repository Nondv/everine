(ns everine.core
  (:use [everine.components.todo-list :only [todo-list]])
  (:require [rum.core :as rum]
            [ajax.core :as ajax]
            [everine.components.todo-list-select :refer [todo-list-select]]
            [everine.components.todo-list-form :refer [todo-list-form]]
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


(defn submit-data [state]
  (ajax/POST "/lists.json"
             {:response-format (ajax/json-response-format {:keywords? true})
              :format (ajax/json-request-format)
              :params  {:current @(::current-list-name state)
                        :lists   @(::lists state)}
              :handler #(js/console.log "saved")}))

(defn- dispatch-current-list-items [items state]
  (let [current-list-name @(::current-list-name state)
        lists-atom (::lists state)
        lists @lists-atom
        current-list (find-by-name current-list-name lists)
        updated-current-list (replace-items items current-list)
        updated-lists (replace-by-name current-list-name
                                       updated-current-list
                                       lists)]
    (reset! lists-atom updated-lists)
    (submit-data state)))

(defn- add-list [list state]
  (let [lists-atom (::lists state)
        lists @lists-atom]
    ;; should I throw exception if found?
    (when-not (find-by-name (:name list) lists)
      (reset! lists-atom
              (conj lists list)))))

(defn- change-current-list-name! [new-name state]
  (reset! (::current-list-name state) new-name)
  (submit-data state))

(rum/defcs app <
  (rum/local [] ::lists)
  (rum/local "" ::current-list-name)
  {:did-mount load-lists}
  [state]

  (let [lists (::lists state)
        current-list-name (::current-list-name state)
        current-list (find-by-name @current-list-name @lists)
        replace-current-list-items #(replace-by-name @current-list-name (replace-items % current-list) @lists)]
    [:div.app
     (todo-list-form #(add-list % state))
     (todo-list-select (map :name @lists) @current-list-name #(change-current-list-name! % state))
     (todo-list (:items current-list) #(dispatch-current-list-items % state))]))

(rum/mount (app) (js/document.getElementById "app" ))
